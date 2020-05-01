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

    EditText songName;
    String songTitle, songRatings, songLength, songUrl;
    Button searchButton;
    private static int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songName = (EditText) findViewById(R.id.et_name);
        searchButton = (Button) findViewById(R.id.btn_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songTitle = songName.getText().toString();
                if(songTitle.equals("")) {
                    // For empty
                    Context context = getApplicationContext();
                    CharSequence text = "Song Name is Empty";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }else{
                    songRatings = "4.7";
                    songLength = "232 seconds";
                    songUrl = "DJ Snake - Taki Taki ft Selena Gomez Ozuna Cardi B (Official Music Video).mp3";

                    Intent i = new Intent(MainActivity.this, SecondPage.class);
                    //Create the bundle
                    Bundle bundle = new Bundle();

                    //Add your data to bundle
                    bundle.putString("title", songTitle);
                    bundle.putString("ratings", songRatings);
                    bundle.putString("length", songLength);
                    bundle.putString("url", songUrl);
                    bundle.putString("host", "http://la-musica.herokuapp.com/");

                    //Add the bundle to the intent
                    i.putExtras(bundle);

                    //Fire that second activity
                    startActivity(i);
                }
            }
        });

    }
}
