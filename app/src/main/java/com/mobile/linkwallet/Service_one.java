package com.mobile.linkwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Service_one extends AppCompatActivity {

    private Button cont;
    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp == 320 || configuration.smallestScreenWidthDp == 400 || configuration.smallestScreenWidthDp == 420 ||
                configuration.smallestScreenWidthDp == 480){
            setContentView(R.layout.service_one_small);
        }
        else {
            setContentView(R.layout.activity_service_one);
        }

        cont = findViewById(R.id.btn);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Service_one.this, Service_two.class));
                Animatoo.animateZoom(Service_one.this);
            }
        });
    }

    public boolean onTouchEvent(MotionEvent touchEvent){

        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;

            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1>x2){
                    Intent c = new Intent(this, Service_two.class);
                    startActivity(c);
                    Animatoo.animateZoom(Service_one.this);
                }
                break;
        }
        return false;
    }
}
