package com.example.musin.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.musin.R;

public class HomeFragmentSearch extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceBundle) {
        super.onCreateView(inflater, container, savedInstanceBundle);
        View root = inflater.inflate(R.layout.fragment_home_search, container, false);

        return root;
    }
}
