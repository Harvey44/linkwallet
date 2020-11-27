package com.mobile.linkwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Floating extends AppCompatActivity {

    EditText edt;
    MyKeyboard keyboard;
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, delete, enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating);
        initViews();
        edt = findViewById(R.id.edt);
        route();

//        edt.setRawInputType(InputType.TYPE_CLASS_TEXT);
//        edt.setTextIsSelectable(true);

//        InputConnection ic = edt.onCreateInputConnection(new EditorInfo());
//        keyboard.setInputConnection(ic);

    }

    private void initViews(){
        b1 = findViewById(R.id.btn);
        b2 = findViewById(R.id.btn2);
        b3 = findViewById(R.id.btn3);
        b4 = findViewById(R.id.btn4);
        b5 = findViewById(R.id.btn5);
        b6 = findViewById(R.id.btn6);
        b7 = findViewById(R.id.btn7);
        b8= findViewById(R.id.btn8);
        b9 = findViewById(R.id.btn9);
        b0 = findViewById(R.id.btn10);
        delete = findViewById(R.id.del);
        enter = findViewById(R.id.ent);
    }

    private void route(){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText(edt.getText().insert(edt.getText().length(), "1"));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText(edt.getText().insert(edt.getText().length(), "2"));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText(edt.getText().insert(edt.getText().length(), "3"));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText(edt.getText().insert(edt.getText().length(), "4"));
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText(edt.getText().insert(edt.getText().length(), "5"));
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText(edt.getText().insert(edt.getText().length(), "6"));
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText(edt.getText().insert(edt.getText().length(), "7"));
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText(edt.getText().insert(edt.getText().length(), "8"));
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText(edt.getText().insert(edt.getText().length(), "9"));
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.setText(edt.getText().insert(edt.getText().length(), "0"));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Floating.this, edt.getText(), Toast.LENGTH_LONG).show();
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt.getText().length() == 0){
                    Toast.makeText(Floating.this, "Enter Amount", Toast.LENGTH_LONG).show();
                }
                else{
                    edt.setText(edt.getText().delete(edt.getText().length()-1, edt.getText().length()));
                }

            }
        });
    }
}
