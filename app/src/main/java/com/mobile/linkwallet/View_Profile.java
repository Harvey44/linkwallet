package com.mobile.linkwallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobile.linkwallet.storage.SharedPrefManager;

public class View_Profile extends AppCompatActivity {
    TextView FN, EA, CT;
    ImageView Close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__profile);

        FN = findViewById(R.id.fn);
        EA = findViewById(R.id.ea);
        CT = findViewById(R.id.ct);
        Close = findViewById(R.id.image);
        profile();
    }
    private void profile(){


        FN.setText(SharedPrefManager.getInstance(View_Profile.this).getUser().getFirstname() + " "+ SharedPrefManager.getInstance(View_Profile.this).getUser().getLastname());
        EA.setText(SharedPrefManager.getInstance(View_Profile.this).getUser().getEmail());
        CT.setText(SharedPrefManager.getInstance(View_Profile.this).getUser().getCountry());
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                Animatoo.animateSlideDown(View_Profile.this);
            }
        });



    }
}
