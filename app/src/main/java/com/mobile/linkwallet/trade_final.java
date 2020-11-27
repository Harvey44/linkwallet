package com.mobile.linkwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.developer.kalert.KAlertDialog;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.models.DefaultResponse;
import com.mobile.linkwallet.storage.SharedPrefManager;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class trade_final extends AppCompatActivity {

    EditText aot, voa, qup;
    String fname, date, nationality, idtype, id_no, address, mn, country;
    Button submit;
    ImageView back, chat, help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp == 320 || configuration.smallestScreenWidthDp == 400 || configuration.smallestScreenWidthDp == 420 ||
                configuration.smallestScreenWidthDp == 480){
            setContentView(R.layout.activity_trade_final_small);
        }
        else {
            setContentView(R.layout.activity_trade_final);
        }

        init();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            fname = bundle.getString("fname");
            date = bundle.getString("date");
            nationality = bundle.getString("nationality");
            idtype = bundle.getString("idtype");
            id_no = bundle.getString("id_no");
            address = bundle.getString("address");
            mn = bundle.getString("mn");
            country = bundle.getString("country");
        }
    }

    private void init(){
        aot = findViewById(R.id.et);
        voa = findViewById(R.id.et2);
        qup = findViewById(R.id.et3);
        submit = findViewById(R.id.nexts);
        back = findViewById(R.id.imageView56);
        help = findViewById(R.id.imageView46);
        chat = findViewById(R.id.imageView47);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(trade_final.this, trade_form.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://on-custodian.freshdesk.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://on-custodian.freshdesk.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    private void submit(){

        String asset = aot.getText().toString();
        String volume = voa.getText().toString();
        String price = qup.getText().toString();
        String email = SharedPrefManager.getInstance(getBaseContext()).getUser().getEmail();
        int id = SharedPrefManager.getInstance(getBaseContext()).getUser().getId();
        String cmd = "otc_trade_request";
        SharedPreferences sharedPreference = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + sharedPreference.getString("token", null);

        if(asset.isEmpty()){
            aot.setError("Asset "+ getBaseContext().getResources().getString(R.string.error_msg));
            aot.requestFocus();
            return;
        }
        if(volume.isEmpty()){
            voa.setError("Asset Volume "+ getBaseContext().getResources().getString(R.string.error_msg));
            voa.requestFocus();
            return;
        }
        if(price.isEmpty()){
            qup.setError("Price quote "+ getBaseContext().getResources().getString(R.string.error_msg));
            qup.requestFocus();
            return;
        }

        final AlertDialog waitingDialog = new SpotsDialog(trade_final.this);
        waitingDialog.show();
        Call<DefaultResponse> call = ApiClient.getInstance().getApi().otc_form(id, email, asset, volume, price, fname, date, nationality, idtype, id_no, address, country, mn, cmd, authToken);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                waitingDialog.dismiss();
                if(!defaultResponse.isErr()){
                    Toast.makeText(getBaseContext(), defaultResponse.getMsg(), Toast.LENGTH_LONG).show();
                    waitingDialog.dismiss();
                    KAlertDialog kAlertDialog = new KAlertDialog(trade_final.this, KAlertDialog.SUCCESS_TYPE).setContentText(defaultResponse.getMsg()).
                            setConfirmText("Done").setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                        @Override
                        public void onClick(KAlertDialog kAlertDialog) {
                            startActivity(new Intent(trade_final.this, Otc_Trade.class));
                            Animatoo.animateDiagonal(trade_final.this);
                        }
                    });
                    kAlertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                waitingDialog.dismiss();
            }
        });


    }
}
