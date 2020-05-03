package com.example.musin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musin.data.APIService;
import com.example.musin.data.ApiUtils;
import com.example.musin.data.model.Post;
import com.example.musin.data.model.PostUrlYt;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This has become share activity and download after youtube activity
 * Not the main activity
 * Date - May 3rd, 2020
 */

public class MainActivity extends AppCompatActivity {

    Button downloadButton;
    ImageView sImage;
    TextView textTitle, textRatings, textLength, textAnother;
    ProgressBar isLoading;
    APIService mApiService;
    String gotUrl, strImageUrl;

    String downloadUrl, strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_page);

        // Getting from the layout
        isLoading = (ProgressBar) findViewById(R.id.download_page_progress);
        textTitle = (TextView) findViewById(R.id.s_title);
        textLength = (TextView) findViewById(R.id.s_length);
        textRatings = (TextView) findViewById(R.id.s_ratings);
        textAnother = (TextView) findViewById(R.id.s_another);
        downloadButton = (Button) findViewById(R.id.btn_download);
        sImage = (ImageView) findViewById(R.id.thumbnail_image);
        // Setting some views
        textAnother.setVisibility(View.GONE);
        downloadButton.setText("Please Wait"); // So that button can not be pressed
        // Making progress bar visible
        isLoading.setVisibility(View.VISIBLE);

        // Get the Url from the share intent
        // This also handles the call from Youtube Downlaoder page (Gallery Fragment)
        gotUrl = onSharedIntent();

        // Getting the Rotrofit object
        mApiService = ApiUtils.getAPIService();

        // Sending the info for download and all
        sendPost(gotUrl);

    }

    private String onSharedIntent() {
        Intent receiverdIntent = getIntent();
        String receivedAction = receiverdIntent.getAction();
        String receivedType = receiverdIntent.getType();

        if (Objects.equals(receivedAction, Intent.ACTION_SEND)) {

            // check mime type
            if (receivedType.startsWith("text/")) {

                String receivedText = receiverdIntent
                        .getStringExtra(Intent.EXTRA_TEXT);
                if (receivedText != null) {
                    Log.d("Recieved Text", receivedText);
                    return receivedText;
                }
            }

        } else if (Objects.equals(receivedAction, Intent.ACTION_MAIN)) {
            Log.e("Priyam Error", "onSharedIntent: nothing shared" );
            return "None";
        } else{
            Log.e("Priyam Start", "Successfully called from GalleryFragment");
            final Bundle bundle = getIntent().getExtras();
            return bundle.getString("yturl");  // Don't forget to give this or else it will give Null error
        }
    return "error";
    }

    private void sendPost(String yturl){
        mApiService.saveUrl(new PostUrlYt(yturl)).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    Log.i("POST SUCCESS", "post submitted to API." + response.body().toString());
                    showResponse(response.body());
                }
                else{
                    Log.e("Errorhere", response.message());
                    downloadButton.setText("Back!");
                    downloadButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goBack();
                        }
                    });

                    isLoading.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(getApplicationContext(), "Error: Ask Priyam", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                downloadButton.setText("Try Again");
                isLoading.setVisibility(View.GONE);

                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();

                downloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tryAgain();
                    }
                });

            }
        });
    }

    private void tryAgain(){
        isLoading.setVisibility(View.VISIBLE);
        sendPost(gotUrl);
    }

    @SuppressLint("SetTextI18n")
    private void showResponse(Post response){
        Log.d("Yes", response.toString());

        try{  // This block gives an error when the error is null so in case of null  pointer exception it tires to download
            if(response.getError().equals("null")){
                Log.d("Result Priyam","Just for giving an error");
            } else{

                // If an data['error'] on the server side
                Log.d("Result Priyam",response.getError());
                isLoading.setVisibility(View.GONE);
                downloadButton.setText("Back");
                downloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goBack();
                    }
                });
                textTitle.setText("Process Failed");
                textLength.setText("Error");
                textRatings.setText(response.getError());
            }
        } catch(Exception e){
            // This means the link generation on server is successful

            // For the ImageView
            // Image View has been set, now set the Image
            strImageUrl = response.getImage();
            // Starting thread to get the image from  strImageUrl
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //Log.d("Priyam check", "This thread is running! .......................................");
                    URL urlImageUrl = null;
                    try {
                        urlImageUrl = new URL(strImageUrl); //strImageUrl must be an instance variable
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        final Bitmap mIcon_val;
                        mIcon_val = BitmapFactory.decodeStream(urlImageUrl.openConnection() .getInputStream());

                        // This is needed as another thread can not access the UI thread
                        sImage.post(new Runnable() {
                            @Override
                            public void run() {
                                sImage.setImageBitmap(mIcon_val);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Start the thread
            thread.start();

            textTitle.setText("Title: " + response.getTitle());
            textRatings.setText("Ratings: " + response.getRating());
            textLength.setText("Length: " + response.getLength() + " seconds");
            downloadButton.setText("Download");
            isLoading.setVisibility(View.GONE);

            // Setting up the values
            strName = response.getTitle();
            downloadUrl = response.getUrl();

            downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* Download */
                    if(haveStoragePermission()) {  // If the user has granted user permission
                        downloadFile(downloadUrl, strName);
                    }
                }
            });

        }
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
            downloadFile(downloadUrl, strName);
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
     * @param name
     */
    public void downloadFile(String url, String name){
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

        goBack();
    }

    public void goBack(){
        finish();
    }
}
