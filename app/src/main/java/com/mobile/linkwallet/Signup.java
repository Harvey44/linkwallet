package com.mobile.linkwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hbb20.CountryCodePicker;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.models.DefaultResponse;
import com.mobile.linkwallet.storage.SharedPrefManager;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {

    EditText fname, email, lname, pwd1, pwd2;
    Button btn;
    TextView reg_log;
    CountryCodePicker cp;
    CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
        navigator();

       // Toast.makeText(this, cp.getSelectedCountryName().toString(), Toast.LENGTH_LONG).show();

    }

    private void initViews(){
        fname = findViewById(R.id.fname);
        email =findViewById(R.id.email);
        lname = findViewById(R.id.lname);
        pwd1 = findViewById(R.id.pwd1);
        pwd2 = findViewById(R.id.pwd2);
        btn = findViewById(R.id.reg_btn);
       reg_log = findViewById(R.id.text5);
       cp = findViewById(R.id.cp);
       cb = findViewById(R.id.cb);
    }

    private void navigator(){
        reg_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this, Login.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegistration();
            }
        });
    }

    private void performRegistration() {
        String first = fname.getText().toString();
        String last = lname.getText().toString();
        String emails = email.getText().toString();
        final String country = cp.getSelectedCountryName();
        String password = pwd1.getText().toString();
        String password2 = pwd2.getText().toString();
        String cmd = "register";

        if (emails.isEmpty())  {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            pwd1.setError("Password required");
            pwd1.requestFocus();
            return;
        }

        if (password.length() < 6) {
            pwd1.setError("Password should be atleast 6 character long");
            pwd1.requestFocus();
            return;
        }

        if (first.isEmpty()) {
            fname.setError("First Name required");
            fname.requestFocus();
            return;
        }
        if (last.isEmpty()) {
            lname.setError(" Last Name required");
            lname.requestFocus();
            return;
        }

        if(!cb.isChecked()){
            Toast.makeText(Signup.this, getResources().getString(R.string.cb), Toast.LENGTH_LONG).show();
            return;
        }

      
        SharedPreferences sharedPreferences = getSharedPreferences("Lang", Activity.MODE_PRIVATE);
        String language = sharedPreferences.getString("My_Lang", "en");


        Call<DefaultResponse> call = ApiClient.getInstance().getApi().createUser(cmd, first, last, emails, country, language, password, password2);
        final AlertDialog waitingDialog = new SpotsDialog(Signup.this);
        waitingDialog.show();
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.body() != null) {
                    if (!response.body().isErr()) {
                        DefaultResponse dr = response.body();
                        Toast.makeText(Signup.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                        SharedPrefManager.getInstance(Signup.this)
                                .saveUser(dr.getUser());
                        SharedPrefManager.getInstance(Signup.this).saveSettings(dr.getSettings());
                        SharedPreferences sharedPreferences = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("gcode", false);
                        editor.apply();

                        Intent intent = new Intent(Signup.this, Email_Ver.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Animatoo.animateSlideUp(Signup.this);


                    }
                    else {
                        {
                            DefaultResponse dr = response.body();
                            Toast.makeText(Signup.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                            waitingDialog.dismiss();
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(Signup.this, "Check Network Connection", Toast.LENGTH_LONG).show();
                waitingDialog.dismiss();
            }
        });
    }
}
