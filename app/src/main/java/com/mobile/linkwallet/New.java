package com.mobile.linkwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class New extends AppCompatActivity {

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        loadLocale();

        //displayFragment(new Home());



        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(New.this, Profile.class));
                Animatoo.animateCard(New.this);
                finish();
            }
        }, 1000);
    }


    private void displayFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.relativeLayout, fragment)
                .commit();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();


    }

    private void loadLocale(){
        SharedPreferences sharedPreferences = getSharedPreferences("Lang", Activity.MODE_PRIVATE);
        String language = sharedPreferences.getString("My_Lang", "en");
        setLocale(language);

    }

    @Override
    protected void onStart() {
        super.onStart();
        startActivity(new Intent(New.this, Profile.class));
    }
}
