package com.example.musin.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.musin.ui.home.HomeFragmentDownload;
import com.example.musin.ui.home.HomeFragmentSearch;

/**
 * The Adapter for the home fragment
 * Uses FragmentPagerAdapter and it will automatically help in creating it
 */

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0:
                return new HomeFragmentSearch(); // The main home page as it is for searching the songs -  Position 0
            case 1:
                return new HomeFragmentDownload(); // The new page which will show the downloads here -  Position 1
        }
        return null; // does not happen
    }

    @Override
    public int getItemCount() {
        return 2;  // Currently there are only two pages
    }
}
