 package com.mobile.linkwallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.models.RecoverResponse;
import com.mobile.linkwallet.models.Recovercode_Response;
import com.mobile.linkwallet.models.ResetResponse;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class Recover_Password extends AppCompatActivity {

     TextView register;
     Button submit, login;
     EditText recover_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover__password);
        initViews();
        navigator();
    }
    
    private void initViews(){
        recover_pwd = findViewById(R.id.recover_pwd);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        submit = findViewById(R.id.submit);


    }
    
    private void navigator(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Recover_Password.this, Signup.class));
                Animatoo.animateFade(Recover_Password.this);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Recover_Password.this, Login.class));
                Animatoo.animateFade(Recover_Password.this);
            }
        });
       submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverpassword();
            }
        });
    }

     public void recoverpassword() {
         final String remail = recover_pwd.getText().toString();
         SharedPreferences sharedPreferences = Recover_Password.this.getSharedPreferences("Lang", Activity.MODE_PRIVATE);
         String language = sharedPreferences.getString("My_Lang", "en");
         String cmd = "recover";

         if (remail.isEmpty()) {
             recover_pwd.setError("Email is required");
             recover_pwd.requestFocus();
             return;
         }

         if (!Patterns.EMAIL_ADDRESS.matcher(remail).matches()) {
             recover_pwd.setError("Enter a valid email");
             recover_pwd.requestFocus();
             return;
         }

         Call<RecoverResponse> call = ApiClient
                 .getInstance().getApi().recover(remail, language, cmd);
         final android.app.AlertDialog waitingDialog = new SpotsDialog(Recover_Password.this);
         waitingDialog.show();
         call.enqueue(new Callback<RecoverResponse>() {
             @Override
             public void onResponse(Call<RecoverResponse> call, Response<RecoverResponse> response) {

                 RecoverResponse recoverResponse = response.body();
                 if (!recoverResponse.isError()) {
                     waitingDialog.dismiss();

                     Toast.makeText(Recover_Password.this, recoverResponse.getMessage(), Toast.LENGTH_LONG).show();

                     final View view  = LayoutInflater.from(Recover_Password.this).inflate(R.layout.alertdialog, null);
                     final EditText code =  view.findViewById(R.id.code);
                     final Button resend =  view.findViewById(R.id.resend);
                     resend.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             startActivity(new Intent(Recover_Password.this, Recover_Password.class));
                             Animatoo.animateFade(Recover_Password.this);
                         }
                     });
                     Button submitcode =  view.findViewById(R.id.submitcode);
                     submitcode.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             final String Code = code.getText().toString().trim();
                             final String p_email = remail;
                             SharedPreferences sharedPreferences = getSharedPreferences("Lang", Activity.MODE_PRIVATE);
                             final String language = sharedPreferences.getString("My_Lang", "en");
                             String Cmd = "recover_code";
                             if (Code.isEmpty()) {
                                 code.setError("Reset Code is required");
                                 //code.requestFocus();
                                 return;
                             }
                             Call<Recovercode_Response> call = ApiClient
                                     .getInstance().getApi().recover_code(p_email, Code, language, Cmd);
                             call.enqueue(new Callback<Recovercode_Response>() {
                                 @Override
                                 public void onResponse(Call<Recovercode_Response> call, Response<Recovercode_Response> response) {
                                     Recovercode_Response recovercode_response = response.body();
                                     if (!recovercode_response.isError()) {
                                         Toast.makeText(Recover_Password.this, recovercode_response.getMessage(), Toast.LENGTH_LONG).show();
                                         View views  = LayoutInflater.from(Recover_Password.this).inflate(R.layout.setpassword, null);
                                         final EditText password1 =  views.findViewById(R.id.pwd3);
                                         final EditText password2 =  views.findViewById(R.id.pwd4);
                                         Button resetpass =  views.findViewById(R.id.resetpass);
                                         resetpass.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 String Password1 = password1.getText().toString();
                                                 String Password2 = password2.getText().toString();
                                                 String Resetemail = p_email;
                                                 String Rcode = Code;
                                                 String Rcmd = "reset";

                                                 if (Password1.isEmpty()) {
                                                     password1.setError("Password required");
                                                     password1.requestFocus();
                                                     return;
                                                 }

                                                 if (Password1.length() < 6) {
                                                     password1.setError("Password should be 6 characters long");
                                                     password1.requestFocus();
                                                     return;
                                                 }
                                                 if (Password2.isEmpty()) {
                                                     password2.setError("Password required");
                                                     password2.requestFocus();
                                                     return;
                                                 }

                                                 if (Password2.length() < 6) {
                                                     password2.setError("Password should be 6 characters long");
                                                     password2.requestFocus();
                                                     return;
                                                 }
                                                 Call<ResetResponse> call2 = ApiClient.getInstance().getApi().reset(Resetemail, Rcode, Password1, Password2, language, Rcmd);
                                                 call2.enqueue(new Callback<ResetResponse>() {
                                                     @Override
                                                     public void onResponse(Call<ResetResponse> call, Response<ResetResponse> response) {
                                                         ResetResponse resetResponse = response.body();

                                                         if (!resetResponse.isError()) {
                                                             Toast.makeText(Recover_Password.this, resetResponse.getMessage(), Toast.LENGTH_LONG).show();
                                                             startActivity(new Intent(Recover_Password.this, Login.class));
                                                         }
                                                         else {
                                                             Toast.makeText(Recover_Password.this, resetResponse.getMessage(), Toast.LENGTH_LONG).show();
                                                         }
                                                     }

                                                     @Override
                                                     public void onFailure(Call<ResetResponse> call, Throwable t) {
                                                         Toast.makeText(Recover_Password.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                                     }
                                                 });
                                             }
                                         });
                                         AlertDialog.Builder builder = new AlertDialog.Builder(Recover_Password.this);
                                         builder.setView(views).setCancelable(false);
                                         AlertDialog alertDialog = builder.create();
                                         alertDialog.show();
                                     }
                                     else {
                                         Toast.makeText(Recover_Password.this, recovercode_response.getMessage(), Toast.LENGTH_LONG).show();
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<Recovercode_Response> call, Throwable t) {
                                     Toast.makeText(Recover_Password.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                 }
                             });


                         }
                     });
                     AlertDialog.Builder builder = new AlertDialog.Builder(Recover_Password.this);
                     builder
                             .setView(view).setCancelable(false);


                     AlertDialog alertDialog = builder.create();
                     alertDialog.show();



                 } else {
                     Toast.makeText(Recover_Password.this, recoverResponse.getMessage(), Toast.LENGTH_LONG).show();
                     waitingDialog.dismiss();
                 }

             }

             @Override
             public void onFailure(Call<RecoverResponse> call, Throwable t) {
                 Toast.makeText(Recover_Password.this, t.getMessage(), Toast.LENGTH_LONG).show();
                 waitingDialog.dismiss();
             }
         });
     }
}
