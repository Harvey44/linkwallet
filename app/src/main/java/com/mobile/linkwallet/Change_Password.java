 package com.mobile.linkwallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.models.DefaultResponse;
import com.mobile.linkwallet.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class Change_Password extends AppCompatActivity {

     EditText ppwd, npwd, cnpwd;
     Button Submit;
     ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__password);

        ppwd = findViewById(R.id.ppwd);
        npwd = findViewById(R.id.npwd);
        cnpwd = findViewById(R.id.cnpwd);
        Submit = findViewById(R.id.editpwd);
        image = findViewById(R.id.image);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_password();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                Animatoo.animateSwipeLeft(Change_Password.this);
            }
        });
    }

     private void edit_password(){

         int Id = SharedPrefManager.getInstance(Change_Password.this).getUser().getId();
         String Email = SharedPrefManager.getInstance(Change_Password.this).getUser().getEmail();
         String Ppwd = ppwd.getText().toString();
         String Npwd = npwd.getText().toString();
         String Cnpwd = cnpwd.getText().toString();
         String Cmd = "change_password";
         SharedPreferences sharedPreference = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
         final String authToken = "Bearer " + sharedPreference.getString("token", null);

         Call<DefaultResponse> call = ApiClient.getInstance().getApi().edit_password(Email, Id, Ppwd,Npwd, Cnpwd, Cmd, authToken);

         call.enqueue(new Callback<DefaultResponse>() {
             @Override
             public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                 DefaultResponse defaultResponse = response.body();

                 if(!defaultResponse.isErr()){
                     Toast.makeText(Change_Password.this, defaultResponse.getMsg(), Toast.LENGTH_SHORT).show();
                     logout();
                 }
                 else if(defaultResponse.isErr() && defaultResponse.getErrorMessage().equals("Invalid Token Request")){
                     logout();

                 }
                 else{
                     Toast.makeText(Change_Password.this, defaultResponse.getMsg(), Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<DefaultResponse> call, Throwable t) {

             }
         });








     }

     private void logout() {
         SharedPrefManager.getInstance(Change_Password.this).clear();
         SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
         editor.putBoolean("value", false);
         editor.apply();

         Intent intent = new Intent(Change_Password.this, Login.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
         startActivity(intent);
     }
     }

