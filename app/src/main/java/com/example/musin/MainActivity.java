package com.example.musin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText testBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testBox = (EditText) findViewById(R.id.test_box);

        testBox.setText(onSharedIntent());

    }

    private String onSharedIntent() {
        Intent receiverdIntent = getIntent();
        String receivedAction = receiverdIntent.getAction();
        String receivedType = receiverdIntent.getType();

        if (receivedAction.equals(Intent.ACTION_SEND)) {

            // check mime type
            if (receivedType.startsWith("text/")) {

                String receivedText = receiverdIntent
                        .getStringExtra(Intent.EXTRA_TEXT);
                if (receivedText != null) {
                    Log.d("Pecieved Text", receivedText);
                    return receivedText;
                }
            }

        } else if (receivedAction.equals(Intent.ACTION_MAIN)) {

            Log.e("Priyam Error", "onSharedIntent: nothing shared" );
        }
    return "error";
    }
}
