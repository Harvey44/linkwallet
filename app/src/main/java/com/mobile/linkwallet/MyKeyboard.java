package com.mobile.linkwallet;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View;

public class MyKeyboard extends LinearLayout implements View.OnClickListener {

    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, delete, enter;
    private SparseArray<String> keyValues = new SparseArray<>();
    InputConnection inputConnection;
    public MyKeyboard(Context context){
        this(context, null, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, 0);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);
        b1 = findViewById(R.id.btn);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn2);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn6);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn7);
        b7.setOnClickListener(this);
        b8= findViewById(R.id.btn8);
        b8.setOnClickListener(this);
        b9 = findViewById(R.id.btn9);
        b9.setOnClickListener(this);
        b0 = findViewById(R.id.btn10);
        b0.setOnClickListener(this);
        delete = findViewById(R.id.del);
        delete.setOnClickListener(this);
        enter = findViewById(R.id.ent);
        enter.setOnClickListener(this);

        keyValues.put(R.id.btn, "1");
        keyValues.put(R.id.btn2, "2");
        keyValues.put(R.id.btn3, "3");
        keyValues.put(R.id.btn4, "4");
        keyValues.put(R.id.btn5, "5");
        keyValues.put(R.id.btn6, "6");
        keyValues.put(R.id.btn7, "7");
        keyValues.put(R.id.btn8, "8");
        keyValues.put(R.id.btn9, "9");
        keyValues.put(R.id.btn10, "0");
        keyValues.put(R.id.ent, "\n");
    }
    @Override
    public void onClick(View v) {
        if(inputConnection == null)
            return;

        if(v.getId() == R.id.del){
            CharSequence sequence = inputConnection.getSelectedText(0);

            if(TextUtils.isEmpty(sequence)){
                inputConnection.deleteSurroundingText(1,0);
            }
            else{
                inputConnection.commitText("", 1);
            }
        }
        else{
            String value = keyValues.get(v.getId());
            inputConnection.commitText(value, 2 );
        }
    }

    public void setInputConnection(InputConnection ic){
        inputConnection = ic;
    }
}
