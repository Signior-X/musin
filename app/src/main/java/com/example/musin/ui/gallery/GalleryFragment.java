package com.example.musin.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.musin.MainActivity;
import com.example.musin.R;

public class GalleryFragment extends Fragment {

    private EditText ytUrlEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        // keyboard action
        ytUrlEditText = (EditText) root.findViewById(R.id.et_name);
        ytUrlEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO){

                    actionOnSearch(v.getText().toString().trim());
                }
                return false;
            }
        });
        // Button action
        root.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                actionOnSearch(ytUrlEditText.getText().toString().trim());
            }
        });

        return root;
    }

    private void actionOnSearch(String uri){
        if(uri.equals("")) {
            Toast toast = Toast.makeText(getContext(), "Url is Empty", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            Intent i = new Intent(getContext(), MainActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("yturl", uri);

            i.putExtras(bundle);
            startActivity(i);
        }
    }
}
