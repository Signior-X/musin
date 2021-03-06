package com.example.musin.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.musin.DownloadPage;
import com.example.musin.R;
import com.example.musin.data.APIService;
import com.example.musin.data.ApiUtils;
import com.example.musin.data.model.Post;
import com.example.musin.data.model.PostRequest;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Priyam Seth
 * Date - May 2nd, 2020
 * used root.findViewById instead
 * used getContext instead of getApplicationContext
 * Also, in the intent one used getContext()
 */
public class HomeFragment extends Fragment {

    // Variable Declarations
    private APIService mApiService;
    private EditText songNameEdit;
    private Button searchButton;
    private static int REQUEST_CODE=1;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Setting the View
        root = inflater.inflate(R.layout.fragment_home, container, false);

        // Getting the mApiService to use (object)
        mApiService = ApiUtils.getAPIService();

        // Calling the API for checking if it is working '/api/check'
        firstGet();

        // Should cast for API 26 lower android versions
        songNameEdit = (EditText) root.findViewById(R.id.et_name);
        searchButton = (Button) root.findViewById(R.id.btn_search);

        // for keyboard search button clicked IME
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The name with which the song will be downloaded
                actionOnSearch(songNameEdit.getText().toString().trim());
            }
        });

        // for search button pressed
        songNameEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    actionOnSearch(v.getText().toString().trim());
                }
                return false;
            }
        });

        return root;
    }

    private void actionOnSearch(String searchText){
        if(searchText.equals("")) {
            // For empty value
            Toast toast = Toast.makeText(getContext(), "Song Name is Empty", Toast.LENGTH_SHORT);
            toast.show();

        }else{
            root.findViewById(R.id.loading_round_animation).setVisibility(View.VISIBLE);
            searchButton.setEnabled(false);
            // String body = "{ \"name\":\"Despacito\" }";
            Toast toast = Toast.makeText(getContext(), "Searching", Toast.LENGTH_SHORT);
            toast.show();
            sendPost(searchText);
        }
    }

    /**
     * Function to call so that server wakes up if sleeping
     */
    private void firstGet(){
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
                Log.e("Priyam start", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    /* This sends the response and after things are then taken in consideration */

    /**
     * Function to send the post request using retrofit at the API Service Class in data package
     * @param name Song Name
     */
    private void sendPost(String name){
        //Log.d("body",name);
        mApiService.savePost(new PostRequest(name)).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    //Log.i("POST SUCCESS", "post submitted to API." + response.body().toString());
                    searchButton.setEnabled(true);
                    root.findViewById(R.id.loading_round_animation).setVisibility(View.GONE);
                    showResponse(response.body());
                }
                else{
                    Log.e("Errorhere", response.message());
                    searchButton.setEnabled(true);
                    root.findViewById(R.id.loading_round_animation).setVisibility(View.GONE);
                    try {
                        Toast toast = Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT);
                        toast.show();
                    } catch(Exception e){
                        Toast toast = Toast.makeText(getContext(), "Error: Ask Priyam", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                //Log.e("POST ERROR", Objects.requireNonNull(t.getMessage()));
                searchButton.setEnabled(true);
                root.findViewById(R.id.loading_round_animation).setVisibility(View.GONE);
                Toast toast = Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    /**
     * Function useful while debugging
     * @param response - Response got from the server from /api/getdata
     */
    private void showResponse(Post response){
        // Showing the post result
        Log.d("Yes", response.toString());
        startDownloadActivity(response.getTitle(), response.getRating(), response.getLength(), response.getUrl(), response.getImage());
    }

    /**
     * Start the DownloadActivity - Moves to DownloadPage
     * @param songName Pass the song name
     * @param songRatings Pass the song ratings
     * @param songLength Pass the song length
     * @param songUrl Pass the songUrl
     * @param imageUrl Pass the thumbnail url of the image
     */
    private void startDownloadActivity(String songName, String songRatings, String songLength, String songUrl, String imageUrl){

        /*
        songName = "Taki Taki";
        songRatings = "4.7";
        songLength = "232 seconds";
        songUrl = "https://r1---sn-0aug5oxu-qxae.googlevideo.com/videoplayback?expire=1588341009&ei=sNSrXrKxOMKRmgeO1Z2QBA&ip=103.105.152.148&id=o-AHa_-bWGeoHkG0Llb7kk9DJPEWkGjBVpGzv5_Q8xn1mn&itag=140&source=youtube&requiressl=yes&mh=sB&mm=31%2C29&mn=sn-0aug5oxu-qxae%2Csn-qxa7snel&ms=au%2Crdu&mv=m&mvi=0&pcm2cms=yes&pl=24&gcr=in&initcwndbps=520000&vprv=1&mime=audio%2Fmp4&gir=yes&clen=3749252&dur=231.619&lmt=1582752398925146&mt=1588319307&fvip=1&keepalive=yes&fexp=23882513&c=WEB&txp=5531432&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cgcr%2Cvprv%2Cmime%2Cgir%2Cclen%2Cdur%2Clmt&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpcm2cms%2Cpl%2Cinitcwndbps&lsig=ALrAebAwRQIgVkSpvrwggTxFRuGX40veTWOZ9G2SBfU2bQUbN3vlymcCIQCyrKV4DCoaV_Je43QS1VtsoESnFlwjdOSHwojfDxzrcA%3D%3D&sig=AJpPlLswRQIhANRZ0n-co_lut3ss4SZzHFEJuLKfxEZgczILc7jwWq8KAiBXXQ4XhZURcbYnNn0JlSogce9rm3EusMPIYwpgjBksxg==";
        */

        Intent i = new Intent(getContext() ,DownloadPage.class);
        //Create the bundle
        Bundle bundle = new Bundle();

        //Add your data to bundle
        bundle.putString("title", songName);  // Taking from the instance variable
        bundle.putString("name", songName);
        bundle.putString("ratings", songRatings);
        bundle.putString("length", songLength);
        bundle.putString("musicUrl", songUrl);
        bundle.putString("imageUrl", imageUrl);

        //Add the bundle to the intent
        i.putExtras(bundle);

        // Clearing the text field
        songNameEdit.setText("");

        //Fire that second activity
        startActivity(i);
    }
}
