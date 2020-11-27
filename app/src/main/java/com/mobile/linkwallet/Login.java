package com.mobile.linkwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.models.LoginResponse;
import com.mobile.linkwallet.storage.SharedPrefManager;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private TextInputEditText editTextEmail;
    private EditText editTextPassword;
    private Button forgot, login;
    private TextView reg;
    private TextInputLayout input, input2;

    Animation uptodown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        navigator();
    }

    private void initViews(){
        input = findViewById(R.id.input);
        input2 = findViewById(R.id.input2);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.editTextPassword);
        forgot = findViewById(R.id.forgot);
        login = findViewById(R.id.buttonLogin);
        reg = findViewById(R.id.text5);


    }

    private void userLogin() {

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("Lang", Activity.MODE_PRIVATE);
        String language = sharedPreferences.getString("My_Lang", "en");
        String cmd = "login";

        if (email.isEmpty()) {
            //editTextEmail.setError("Email is required");
            input.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }



        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            input2.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            input2.setError("Password should be atleast 6 characters long");
            editTextPassword.requestFocus();
            return;
        }

        Call<LoginResponse> call = ApiClient
                .getInstance().getApi().userLogin(email, password, language, cmd);


        final AlertDialog waitingDialog = new SpotsDialog(Login.this);
        waitingDialog.show();
        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                final LoginResponse loginResponse = response.body();

                if (!loginResponse.isError()) {

                    Toast.makeText(Login.this, loginResponse.getMessage() , Toast.LENGTH_LONG).show();


                    if(!loginResponse.getSetting().isGcode()){
                        SharedPrefManager.getInstance(Login.this)
                                .saveUser(loginResponse.getUser());
                        SharedPrefManager.getInstance(Login.this).saveSettings(loginResponse.getSetting());
                        waitingDialog.dismiss();
                        Intent intent = new Intent(Login.this, Profile.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Animatoo.animateSlideUp(Login.this);

                    }
//                    else if (loginResponse.getSetting().isGcode()){
//                        Toast.makeText(Login.this, "Please verify 2FA before logging in", Toast.LENGTH_LONG).show();
//                        final View views  = LayoutInflater.from(Login.this).inflate(R.layout.verify_2fa, null);
//                        final EditText gcode = views.findViewById(R.id.gcode);
//                        Button gsubmit = views.findViewById(R.id.gcancel);
//                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Login.this);
//                        builder
//                                .setView(views).setCancelable(true);
//                        waitingDialog.dismiss();
//
//
//                        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
//                        gsubmit.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                final String Google_code = gcode.getText().toString().trim();
//                                final String Email = loginResponse.getUser().getEmail();
//                                final int id = loginResponse.getUser().getId();
//                                String Cmd = "verify_2fa";
//                                SharedPreferences sharedPreferences = getSharedPreferences("Lang", Activity.MODE_PRIVATE);
//                                String language = sharedPreferences.getString("My_Lang", "en");
//                                final String authToken = "Bearer " + SharedPrefManager.getInstance(Login.this).getUser().getLogin_token();
//
//                                if (Google_code.isEmpty()) {
//                                    gcode.setError("Authenticator code  is required");
//                                    //code.requestFocus();
//                                    return;
//                                }
//                                Call<LoginResponse> call1 = ApiClient.getInstance().getApi().verify_2fa(id, Email,Google_code, language, Cmd, authToken);
//                                call1.enqueue(new Callback<LoginResponse>() {
//                                    @Override
//                                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                                        LoginResponse loginResponse1 = response.body();
//                                        if(!loginResponse1.isError()){
//
//                                            waitingDialog.dismiss();
//                                            SharedPrefManager.getInstance(Login.this)
//                                                    .saveUser(loginResponse.getUser());
//                                            SharedPrefManager.getInstance(Login.this).saveSettings(loginResponse.getSetting());
//                                            Intent intent = new Intent(Login.this, Profile.class);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                            startActivity(intent);
//                                            Animatoo.animateSlideUp(Login.this);
//                                            Toast.makeText(Login.this, loginResponse1.getMessage(), Toast.LENGTH_SHORT).show();
//
//
//                                        }
//
//                                        else{
//                                            Toast.makeText(Login.this, loginResponse1.getMessage(), Toast.LENGTH_SHORT).show();
//                                            // alertDialog.dismiss();
//                                            waitingDialog.dismiss();
//                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<LoginResponse> call, Throwable t) {
//                                        Toast.makeText(Login.this, "Check Network Connection", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                            }
//                        });
//
//                        alertDialog.show();
//
//                    }

               /*     if(!SharedPrefManager.getInstance(Login.this).getSettings().isGcode()){
                        final View views  = LayoutInflater.from(Login.this).inflate(R.layout.verify_2fa, null);
                        final EditText gcode = views.findViewById(R.id.gcode);
                        Button gsubmit = views.findViewById(R.id.gcancel);
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Login.this);
                        builder
                                .setView(views).setCancelable(true);


                        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                        gsubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String Google_code = gcode.getText().toString().trim();
                                final String Email = SharedPrefManager.getInstance(Login.this).getUser().getEmail();
                                final int ID = SharedPrefManager.getInstance(Login.this).getUser().getId();
                                String Cmd = "verify_2fa";
                                if (Google_code.isEmpty()) {
                                    gcode.setError("Authenticator code  is required");
                                    //code.requestFocus();
                                    return;
                                }
                                Call<DefaultResponse> call = ApiClient.getInstance().getApi().verify_2fa(ID, Email, Google_code, Cmd);
                                call.enqueue(new Callback<DefaultResponse>() {
                                    @Override
                                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                        DefaultResponse defaultResponse = response.body();
                                        if (!defaultResponse.isErr()) {
                                            Toast.makeText(Login.this, defaultResponse.getMsg(), Toast.LENGTH_LONG).show();
                                            alertDialog.dismiss();
                                            Intent intent = new Intent(Login.this, Profile.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            Animatoo.animateSlideUp(Login.this);

                                        }
                                        else{
                                            Toast.makeText(Login.this, defaultResponse.getMsg(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                        Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });

                        alertDialog.show();

                    }*/

                    else{
                        Toast.makeText(Login.this, loginResponse.getMessage() , Toast.LENGTH_LONG).show();
                        waitingDialog.dismiss();
                        Intent intent = new Intent(Login.this, Profile.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Animatoo.animateSlideUp(Login.this);
                    }


                } else {
                    waitingDialog.dismiss();
                    Toast.makeText(Login.this, loginResponse.getMessage() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                waitingDialog.dismiss();
//                Toast.makeText(Login.this,  "Check Network Connection", Toast.LENGTH_LONG).show();
                Toast.makeText(Login.this,  t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void navigator(){
       login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Recover_Password.class));
                Animatoo.animateInAndOut(Login.this);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Signup.class));
                Animatoo.animateInAndOut(Login.this);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, Profile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Animatoo.animateInAndOut(Login.this);

        }
    }
}
