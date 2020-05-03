package com.example.musin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Priyam Seth
 * Date - May 1st, 2020
 * Class for Downloading songs
 */
public class DownloadPage extends AppCompatActivity {

    TextView sTitle, sRatings, sLength;
    Button downloadeButton;
    private String downloadUrl, strName, strTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_page);

        sTitle = (TextView) findViewById(R.id.s_title);
        sRatings = (TextView) findViewById(R.id.s_ratings);
        sLength = (TextView) findViewById(R.id.s_length);

        downloadeButton = (Button) findViewById(R.id.btn_download);
        //Get the bundle
        final Bundle bundle = getIntent().getExtras();

        // Setting the parameters
        sTitle.setText("Title: "+bundle.getString("name"));
        sRatings.setText("Ratings: "+bundle.getString("ratings"));
        sLength.setText("Length: "+bundle.getString("length"+" seconds"));

        // for downloading
        strName = bundle.getString("name");
        strTitle = bundle.getString("title");
        downloadUrl = bundle.getString("musicUrl");
        Log.d("URL USING",downloadUrl);

        downloadeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Download */
                if(haveStoragePermission()) {  // If the user has granted user permission
                    downloadFile(downloadUrl, strTitle, strName);
                }
            }
        });

        findViewById(R.id.s_another).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Here it is going back, clearing the stack of activities
            }
        });

    }

    // Function to check the File Storage
    public boolean haveStoragePermission(){
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.e("Permission error","You have permission");
            return true;
        } else {
            Log.e("Permission error","You have asked for permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
    }

    // Function which is called after haveStoragePermission false
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            //you have the permission now.
            downloadFile(downloadUrl, strTitle, strName);
        } else{ // Permission denied show toast
            Context context = getApplicationContext();
            CharSequence text = "Permission Denied!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * Function to download the file
     * Before calling first check if the user has granted permission for storage or else it will give error
     * @param url - URL of the file
     * @param title
     * @param name
     */
    public void downloadFile(String url, String title, String name){
        Log.e("URL given", url);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Musin Dowload");
        request.setTitle(name);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name+".mp3");
        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        assert manager != null;
        manager.enqueue(request);

        Context context = getApplicationContext();
        CharSequence text = "Downloading";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
