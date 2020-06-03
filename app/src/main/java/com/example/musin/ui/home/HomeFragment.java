package com.example.musin.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.musin.DownloadPage;
import com.example.musin.R;
import com.example.musin.adapters.ViewPagerAdapter;
import com.example.musin.data.APIService;
import com.example.musin.data.ApiUtils;
import com.example.musin.data.model.Post;
import com.example.musin.data.model.PostRequest;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

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

    private View root;

    // tab titles
    private String[] tabTitles = new String[]{"Movies", "Events"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Setting the View
        root = inflater.inflate(R.layout.fragment_home, container, false);

        // Very Very Important - This removes the problem of resizing on soft keyboard
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        // Getting the mApiService to use (object)
        mApiService = ApiUtils.getAPIService();

        // Calling the API for checking if it is working '/api/check'
        firstGet();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find views by id
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager2 viewPager2 = view.findViewById(R.id.home_view_pager2);

        viewPager2.setAdapter(new ViewPagerAdapter(this));

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        });
        tabLayoutMediator.attach();

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

}
