package com.mobile.linkwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.models.SetNewResponse;
import com.mobile.linkwallet.storage.SharedPrefManager;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class New_Wallet extends AppCompatActivity {
    private TextView type;
    private TextInputEditText wallet_name, password1, password2;
    private TextInputLayout input, input2, input3;
    private Button cwallet;
    private String coin_name;
    private int coin_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__wallet);
        initViews();

        coin_name = getIntent().getStringExtra("coin_name");
        coin_id = getIntent().getIntExtra("coin_id", 0);
        type.setText("Create a new " + coin_name + " wallet");

    }

    private void initViews() {
        type = findViewById(R.id.sc);
        wallet_name = findViewById(R.id.wn);
        password1 = findViewById(R.id.wp);
        password2 = findViewById(R.id.wp2);
        cwallet = findViewById(R.id.cwallet);
        input = findViewById(R.id.input);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);

        cwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create();
            }
        });
    }

    private void create() {

        final String Wallet_Name = wallet_name.getText().toString();
        String Wallet_Password = password1.getText().toString();
        String Wallet_Password2 = password2.getText().toString();
        int ID = SharedPrefManager.getInstance(New_Wallet.this).getUser().getId();
        String Email = SharedPrefManager.getInstance(New_Wallet.this).getUser().getEmail();
        String Cmd = "create_wallet";
        final String authToken = "Bearer " + SharedPrefManager.getInstance(New_Wallet.this).getUser().getLogin_token();

        if (Wallet_Name.isEmpty()) {
            input.setError("Wallet Name is required");
            wallet_name.requestFocus();
            return;
        }
        if (Wallet_Name.length() > 15) {
            input.setError("Wallet name can't be longer than 15 words");
            wallet_name.requestFocus();
            return;
        }
        if (Wallet_Password.isEmpty()) {
            input2.setError("Password required");
            password1.requestFocus();
            return;
        }
        if (Wallet_Password.length() < 6) {
            input2.setError("Password should be atleast 6 characters long");
            password1.requestFocus();
            return;
        }

        Call<SetNewResponse> call = ApiClient.getInstance().getApi().create_wallet(ID, Email, coin_id, Wallet_Name, Wallet_Password, Wallet_Password2, Cmd, authToken);
        final AlertDialog waitingDialog = new SpotsDialog(New_Wallet.this);
        waitingDialog.show();
        call.enqueue(new Callback<SetNewResponse>() {
            @Override
            public void onResponse(Call<SetNewResponse> call, Response<SetNewResponse> response) {
                SetNewResponse setNewResponse = response.body();
                                if(!setNewResponse.isError()){
                                    waitingDialog.dismiss();
                                    KAlertDialog kAlertDialog = new KAlertDialog(New_Wallet.this, KAlertDialog.SUCCESS_TYPE).setContentText(setNewResponse.getMessage()).
                                           setConfirmText("Done").setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                        @Override
                                        public void onClick(KAlertDialog kAlertDialog) {
                                            startActivity(new Intent(New_Wallet.this, Profile.class));
                                            Animatoo.animateDiagonal(New_Wallet.this);
                                        }
                                    });
                                    kAlertDialog.show();


                                }
            }

            @Override
            public void onFailure(Call<SetNewResponse> call, Throwable t) {

            }
        });


    }

}
