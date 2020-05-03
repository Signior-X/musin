package com.example.musin.ui.slideshow;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.musin.ChannelDetails;
import com.example.musin.PrefManager;
import com.example.musin.R;

public class SlideshowFragment extends Fragment {

    private PrefManager prefManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        // Android sharesheet for sharing the text
        shareApp();

        // Thanks Notification
        sendNotification(1);

        // Button
        root.findViewById(R.id.btn_share_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });

        return root;
    }

    // Sample how to send a notification
    public void sendNotification(int notificationId){

        prefManager = new PrefManager(getContext());

        //Creating notification channel
        createNotificationChannel();

        NotificationCompat.Builder noti = new NotificationCompat.Builder(requireContext(), ChannelDetails.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_smile)
                .setContentTitle("Musin Share!")
                .setContentText("Thanks for sharing!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Setting Notification compat
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, noti.build());

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Priyamchannel";
            String description = "Priyamchanneldescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ChannelDetails.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void shareApp(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://cloud.iitmandi.ac.in/f/44b470c13a/?raw=1");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Share Download Link");
        startActivity(shareIntent);
    }
}
