package com.mobile.linkwallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.models.CreateResponse;
import com.mobile.linkwallet.models.LoginResponse;
import com.mobile.linkwallet.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Create_2fa extends AppCompatActivity {

    private TextView aname, scode;
    ImageView imageView, copy3, close;
    private EditText code;
    private Button code_btn;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_2fa);
        initViews();
        create_2fa();
    }
    
    private void initViews(){
        imageView = findViewById(R.id.qrcode);
        scode = findViewById(R.id.scode);
        copy3 = findViewById(R.id.copy3);
        close = findViewById(R.id.image);
        code = findViewById(R.id.code2fa);
        code_btn = findViewById(R.id.code2fa_btn);
        copy3.setVisibility(View.INVISIBLE);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                Animatoo.animateSwipeLeft(Create_2fa.this);
            }
        });
        
    }


    private void create_2fa(){
        int id = SharedPrefManager.getInstance(Create_2fa.this).getUser().getId();
        String Email = SharedPrefManager.getInstance(Create_2fa.this).getUser().getEmail();
        String Cmd = "create_2fa";
        // SharedPreferences sharedPreference = Create_2fa.this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(Create_2fa.this).getUser().getLogin_token();

        Call<CreateResponse> call = ApiClient.getInstance().getApi().create2fa(id, Email, Cmd, authToken);
        call.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                CreateResponse createResponse = response.body();
                if(!createResponse.isError()){
                    Picasso.get().load(createResponse.getResult().getSecret_qrcode()).into(imageView);
                    scode.setText(createResponse.getResult().getSecret());
                    copy3.setVisibility(View.VISIBLE);
                    copy3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager clipboardManager = (ClipboardManager) Create_2fa.this.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText("Secret Code", scode.getText().toString());
                            clipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(Create_2fa.this, "Secret Code copied to Clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });

                    code_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String Google_code = code.getText().toString().trim();
                            final String Email = SharedPrefManager.getInstance(Create_2fa.this).getUser().getEmail();
                            final int id = SharedPrefManager.getInstance(Create_2fa.this).getUser().getId();
                            String Cmd = "create_2fa";
                            SharedPreferences sharedPreference = Create_2fa.this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                            final String authToken = "Bearer " + SharedPrefManager.getInstance(Create_2fa.this).getUser().getLogin_token();
                            if (Google_code.isEmpty()) {
                                code.setError("Authenticator code  is required");
                                //code.requestFocus();
                                return;
                            }
                            Call<LoginResponse> call1 = ApiClient.getInstance().getApi().activate_2fa(id, Email, Google_code, Cmd, authToken);
                            call1.enqueue(new Callback<LoginResponse>() {
                                @Override
                                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                    LoginResponse loginResponse = response.body();
                                    if(!loginResponse.isError()){
                                        Toast.makeText(Create_2fa.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                                        SharedPreferences sharedPreferences = Create_2fa.this.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("gcode", true);
                                        editor.apply();


                                    }
                                    else if(loginResponse.isError() && loginResponse.getErrorMessage().equals("Invalid Token Request")){
                                        logout();

                                    }
                                    else{
                                        Toast.makeText(Create_2fa.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginResponse> call, Throwable t) {

                                }
                            });
                        }
                    });



                }
                else if(createResponse.isError() && createResponse.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }
                else {
                    Toast.makeText(Create_2fa.this, createResponse.getMessage(), Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {

            }
        });
    }

    private void logout() {
        SharedPrefManager.getInstance(Create_2fa.this).clear();
        SharedPreferences.Editor editor = Create_2fa.this.getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(Create_2fa.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
