package com.mobile.linkwallet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobile.linkwallet.storage.SharedPrefManager;

import java.util.Locale;

public class New_lang extends AppCompatActivity {
    View v;
    TextView lang;
    Button cont;
    String sel_lang;
    Animation downtoup;
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__lang);

        v = findViewById(R.id.view76);
        lang = findViewById(R.id.text);
        cont = findViewById(R.id.lang_btn);
        rl = findViewById(R.id.rl);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        rl.setAnimation(downtoup);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_lang();
            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(New_lang.this, Service_one.class));
            }
        });
    }
    private void select_lang(){
        View views  = LayoutInflater.from(New_lang.this).inflate(R.layout.select_lang, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(New_lang.this);
        builder
                .setView(views).setCancelable(true);
        final AlertDialog alertDialog = builder.create();
        TextView tv = views.findViewById(R.id.textView139);

        RadioGroup rg = views.findViewById(R.id.rg);
        final RadioButton rb1 = views.findViewById(R.id.rb1);
        final RadioButton rb2 = views.findViewById(R.id.rb2);
        final RadioButton rb3 = views.findViewById(R.id.rb3);
        final RadioButton rb4 = views.findViewById(R.id.rb4);
        final RadioButton rb5 = views.findViewById(R.id.rb5);
        final RadioButton rb6 = views.findViewById(R.id.rb6);
        final RadioButton rb7 = views.findViewById(R.id.rb7);
        final RadioButton rb8 = views.findViewById(R.id.rb8);
        final RadioButton rb9 = views.findViewById(R.id.rb9);
        final RadioButton rb10 = views.findViewById(R.id.rb10);
        final  RadioButton rb11 = views.findViewById(R.id.rb11);
        final RadioButton rb12 = views.findViewById(R.id.rb12);
        final RadioButton selected;

        SharedPreferences sharedPreferences = getSharedPreferences("Lang", Activity.MODE_PRIVATE);
        int language = sharedPreferences.getInt("checkedid", 6);
        int id = rg.getChildAt(language).getId();


        rg.check(id);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(rb1.isChecked()){
                    setLocale("ar");
                    lang.setText(rb1.getText());

                    SharedPreferences.Editor editor = getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 0);
                    editor.apply();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb2.isChecked()){
                    lang.setText(rb2.getText());
                    SharedPreferences.Editor editor = getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 2);
                    editor.apply();
                    setLocale("zh");
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb3.isChecked()){
                    lang.setText(rb3.getText());
                    SharedPreferences.Editor editor = getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 4);
                    editor.apply();
                    setLocale("nl");
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb4.isChecked()){
                    lang.setText(rb4.getText());
                    SharedPreferences.Editor editor = getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 6);
                    editor.apply();
                    setLocale("en");
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb5.isChecked()){
                    lang.setText(rb5.getText());
                    SharedPreferences.Editor editor = getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 8);
                    editor.apply();
                    setLocale("fr");
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb6.isChecked()){
                    lang.setText(rb6.getText());
                    SharedPreferences.Editor editor = getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 10);
                    editor.apply();
                    setLocale("de");
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb7.isChecked()){
                    lang.setText(rb7.getText());
                    SharedPreferences.Editor editor = getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 12);
                    editor.apply();
                    setLocale("it");
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb8.isChecked()){
                    lang.setText(rb8.getText());
                    SharedPreferences.Editor editor = getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 14);
                    editor.apply();
                    setLocale("ja");
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb9.isChecked()){
                    lang.setText(rb9.getText());
                    SharedPreferences.Editor editor = getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 16);
                    editor.apply();
                    setLocale("ko");
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb10.isChecked()){
                    lang.setText(rb10.getText());
                    SharedPreferences.Editor editor = getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 18);
                    editor.apply();
                    setLocale("pt");
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb11.isChecked()){
                    lang.setText(rb11.getText());
                    SharedPreferences.Editor editor = getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 20);
                    editor.apply();
                    setLocale("ru");
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb12.isChecked()){
                    lang.setText(rb12.getText());
                    SharedPreferences.Editor editor = getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 22);
                    editor.apply();
                    setLocale("es");
                    changeFragment();
                    alertDialog.dismiss();
                }
            }

        });








        alertDialog.show();
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

    private  void changeFragment() {
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, New.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //displayFragment(new BlankFragment());
            Animatoo.animateSwipeRight(New_lang.this);

        }

    }

    private void displayFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.relativeLayout, fragment)
                .commit();
    }

}
