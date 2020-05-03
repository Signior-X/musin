package com.example.musin.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.musin.MainActivity;
import com.example.musin.R;

public class GalleryFragment extends Fragment {

    private EditText ytUrl;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        ytUrl = (EditText) root.findViewById(R.id.et_name);

        root.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputUrl = ytUrl.getText().toString().trim();

                if(inputUrl.equals("")) {
                    Toast toast = Toast.makeText(getContext(), "Url is Empty", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                    Intent i = new Intent(getContext(), MainActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("yturl", inputUrl);

                    i.putExtras(bundle);
                    startActivity(i);
                }
            }
        });

        return root;
    }
}
