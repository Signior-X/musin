package com.example.musin.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.musin.R;

public class AboutFragment extends Fragment {

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Setting the View
        root = inflater.inflate(R.layout.fragment_about, container, false);

        return root;
    }
}

