package com.example.musin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText songNameEdit;
    String songTitle, songRatings, songLength, songUrl, songName;
    Button searchButton;
    private static int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songNameEdit = (EditText) findViewById(R.id.et_name);
        searchButton = (Button) findViewById(R.id.btn_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songTitle = songNameEdit.getText().toString();  // The name with which the song will be downloaded
                if(songTitle.equals("")) {
                    // For empty
                    Context context = getApplicationContext();
                    CharSequence text = "Song Name is Empty";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }else{
                    songName = "Taki Taki";
                    songRatings = "4.7";
                    songLength = "232 seconds";
                    songUrl = "https://r1---sn-0aug5oxu-qxae.googlevideo.com/videoplayback?expire=1588341009&ei=sNSrXrKxOMKRmgeO1Z2QBA&ip=103.105.152.148&id=o-AHa_-bWGeoHkG0Llb7kk9DJPEWkGjBVpGzv5_Q8xn1mn&itag=140&source=youtube&requiressl=yes&mh=sB&mm=31%2C29&mn=sn-0aug5oxu-qxae%2Csn-qxa7snel&ms=au%2Crdu&mv=m&mvi=0&pcm2cms=yes&pl=24&gcr=in&initcwndbps=520000&vprv=1&mime=audio%2Fmp4&gir=yes&clen=3749252&dur=231.619&lmt=1582752398925146&mt=1588319307&fvip=1&keepalive=yes&fexp=23882513&c=WEB&txp=5531432&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cgcr%2Cvprv%2Cmime%2Cgir%2Cclen%2Cdur%2Clmt&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpcm2cms%2Cpl%2Cinitcwndbps&lsig=ALrAebAwRQIgVkSpvrwggTxFRuGX40veTWOZ9G2SBfU2bQUbN3vlymcCIQCyrKV4DCoaV_Je43QS1VtsoESnFlwjdOSHwojfDxzrcA%3D%3D&sig=AJpPlLswRQIhANRZ0n-co_lut3ss4SZzHFEJuLKfxEZgczILc7jwWq8KAiBXXQ4XhZURcbYnNn0JlSogce9rm3EusMPIYwpgjBksxg==";


                    Intent i = new Intent(MainActivity.this, SecondPage.class);
                    //Create the bundle
                    Bundle bundle = new Bundle();

                    //Add your data to bundle
                    bundle.putString("title", songTitle);
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
        });

    }
}
