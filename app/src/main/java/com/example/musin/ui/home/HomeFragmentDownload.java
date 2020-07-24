package com.example.musin.ui.home;

import android.Manifest;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.musin.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class HomeFragmentDownload extends Fragment {

    // For Permissions
    private static final int REQUEST_PERMISSIONS = 12345;
    private static final int PERMISSIONS_COUNT = 1;

    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE

    };

    // For Music Player
    private boolean isMusicPlayerInit;
    private List<String> musicFilesList;

    private MediaPlayer mp;
    private int songPosition;
    private volatile boolean isSongPlaying;
    private int mposition;
    private TextView songPositionTextView;
    private TextView songDurationTextView;
    private SeekBar seekBar;
    private View playBackControls;
    private Button pauseButton;
    private boolean toPlaySecondSong = true;

    Thread songRunnerThread;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_home_download, container, false);

        mp = new MediaPlayer();

        return rootView;
    }


    /* preetam code */

    private boolean arePermissionsDenied(){
        for (int i = 0; i< PERMISSIONS_COUNT; i++){
            if(requireActivity().checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(arePermissionsDenied()){
            ((ActivityManager) (requireActivity().getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
            requireActivity().recreate();
        }else {
            onResume();
        }
    }

    /* For Music Players */
    private void addMusicFilesFrom(String dirPath){
        final File musicDir = new File(dirPath);
        if(!musicDir.exists()){
            musicDir.mkdir();
            return;
        }
        final File[] files = musicDir.listFiles();
        for(File file : files){
            final String path = file.getAbsolutePath();
            if(path.endsWith(".mp3")){
                musicFilesList.add(path);
            }
        }
    }

    private void fillMusicList(){
        musicFilesList.clear();
        addMusicFilesFrom(String.valueOf(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS)));
    }


    private void playSong(){

        final String musicFilePath = musicFilesList.get(mposition);
        if(mp.isPlaying()) {
            mp.stop();
        }

        try{
            mp = new MediaPlayer();
            mp.setDataSource(musicFilePath);
            mp.prepare();
            mp.start();
            isSongPlaying = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        rootView.findViewById(R.id.playBackButtons).setVisibility(View.VISIBLE);

    }

    @Override
    public void onResume(){
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionsDenied()){
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }
        if(true){
            final ListView listView = rootView.findViewById(R.id.listView);
            final TextAdapter textAdapter = new TextAdapter();
            musicFilesList = new ArrayList<>();
            fillMusicList();
            textAdapter.setData(musicFilesList);
            listView.setAdapter(textAdapter);
            seekBar = rootView.findViewById(R.id.seekBar);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int songProgress;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    songProgress = progress;
                    mp.seekTo(songProgress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    songPosition = songProgress;
                    mp.seekTo(songProgress);
                }
            });

            songPositionTextView = rootView.findViewById(R.id.currentPosition);
            songDurationTextView = rootView.findViewById(R.id.songDuration);
            pauseButton = rootView.findViewById(R.id.pauseButton);
            playBackControls = rootView.findViewById(R.id.playBackButtons);

            pauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSongPlaying){
                        mp.pause();
                        pauseButton.setText("Play");
                    }else {
                        if(songPosition==0){
                            playSong();
                        }else {
                            mp.start();
                        }
                        pauseButton.setText("Pause");
                    }
                    isSongPlaying = !isSongPlaying;
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    mposition = position;
                    playSong();
                }
            });
            isMusicPlayerInit = true;
        }
    }


    class TextAdapter extends BaseAdapter {
        private List<String> data = new ArrayList<>();
        void setData(List<String> mData){
            data.clear();
            data.addAll(mData);
            notifyDataSetChanged();
        }

        @Override
        public int getCount(){
            return data.size();
        }

        @Override
        public String getItem(int position){
            return null;
        }

        @Override
        public long getItemId(int position){
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.fragment_home_download_list_item, parent, false);
                convertView.setTag(new ViewHolder((TextView) convertView.findViewById(R.id.myItem)));
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            final String item = data.get(position);
            holder.info.setText(item.substring(item.lastIndexOf('/') +1));
            return convertView;
        }
        class ViewHolder{
            TextView info;

            ViewHolder(TextView mInfo){
                info = mInfo;
            }
        }
    }


}
