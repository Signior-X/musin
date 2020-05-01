package com.example.musin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musin.data.APIService;
import com.example.musin.data.ApiUtils;
import com.example.musin.data.model.Post;
import com.example.musin.data.model.PostRequest;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private APIService mApiService;
    EditText songNameEdit;
    String songTitle;
    Button searchButton;
    private static int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting the mApiService to use (object)
        mApiService = ApiUtils.getAPIService();

        // Calling the API for checking if it is working '/api/check'
        firstGet();

        songNameEdit = (EditText) findViewById(R.id.et_name);
        searchButton = (Button) findViewById(R.id.btn_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songTitle = songNameEdit.getText().toString();  // The name with which the song will be downloaded
                if(songTitle.equals("")) {
                    // For empty value
                    Toast toast = Toast.makeText(getApplicationContext(), "Song Name is Empty", Toast.LENGTH_SHORT);
                    toast.show();

                }else{

                    findViewById(R.id.loading_round_animation).setVisibility(View.VISIBLE);
                    searchButton.setEnabled(false);
                    // String body = "{ \"name\":\"despacito\" }";
                    Toast toast = Toast.makeText(getApplicationContext(), "Searching", Toast.LENGTH_SHORT);
                    toast.show();
                    sendPost(songTitle);

                }
            }
        });

    }

    public void firstGet(){
        Log.d("Priyam", "Start");
        mApiService.firstGet().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    // This Proves connection is working
                    Log.d("Priyam Success", response.body().string());
                } catch (IOException e) {
                    Log.e("Priyam start",e.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Priyam start", t.getMessage());
            }
        });
    }
    /* This sends the response and after things are then taken in consideration */
    public void sendPost(String name){
        Log.d("body",name);
        mApiService.savePost(new PostRequest(name)).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    Log.i("POST SUCCESS", "post submitted to API." + response.body().toString());
                    searchButton.setEnabled(true);
                    findViewById(R.id.loading_round_animation).setVisibility(View.GONE);
                    showResponse(response.body());
                }
                else{
                    Log.e("Errorhere", response.message());
                    searchButton.setEnabled(true);
                    findViewById(R.id.loading_round_animation).setVisibility(View.GONE);
                    Toast toast = Toast.makeText(getApplicationContext(), "Error: Ask Priyam", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e("POST ERROR", t.getMessage());
                searchButton.setEnabled(true);
                findViewById(R.id.loading_round_animation).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void showResponse(Post response){
        // Showing the post result
        Log.d("Yes", response.toString());
        startNextActivity(response.getTitle(), response.getRating(), response.getLength(), response.getUrl());
    }

    public void startNextActivity(String songName, String songRatings, String songLength, String songUrl){

        /*
        songName = "Taki Taki";
        songRatings = "4.7";
        songLength = "232 seconds";
        songUrl = "https://r1---sn-0aug5oxu-qxae.googlevideo.com/videoplayback?expire=1588341009&ei=sNSrXrKxOMKRmgeO1Z2QBA&ip=103.105.152.148&id=o-AHa_-bWGeoHkG0Llb7kk9DJPEWkGjBVpGzv5_Q8xn1mn&itag=140&source=youtube&requiressl=yes&mh=sB&mm=31%2C29&mn=sn-0aug5oxu-qxae%2Csn-qxa7snel&ms=au%2Crdu&mv=m&mvi=0&pcm2cms=yes&pl=24&gcr=in&initcwndbps=520000&vprv=1&mime=audio%2Fmp4&gir=yes&clen=3749252&dur=231.619&lmt=1582752398925146&mt=1588319307&fvip=1&keepalive=yes&fexp=23882513&c=WEB&txp=5531432&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cgcr%2Cvprv%2Cmime%2Cgir%2Cclen%2Cdur%2Clmt&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpcm2cms%2Cpl%2Cinitcwndbps&lsig=ALrAebAwRQIgVkSpvrwggTxFRuGX40veTWOZ9G2SBfU2bQUbN3vlymcCIQCyrKV4DCoaV_Je43QS1VtsoESnFlwjdOSHwojfDxzrcA%3D%3D&sig=AJpPlLswRQIhANRZ0n-co_lut3ss4SZzHFEJuLKfxEZgczILc7jwWq8KAiBXXQ4XhZURcbYnNn0JlSogce9rm3EusMPIYwpgjBksxg==";
        */

        Intent i = new Intent(MainActivity.this, SecondPage.class);
        //Create the bundle
        Bundle bundle = new Bundle();

        //Add your data to bundle
        bundle.putString("title", songTitle);  // Taking from the instance variable
        bundle.putString("name", songName);
        bundle.putString("ratings", songRatings);
        bundle.putString("length", songLength);
        bundle.putString("musicUrl", songUrl);

        //Add the bundle to the intent
        i.putExtras(bundle);

        //Fire that second activity
        startActivity(i);
    }
}
