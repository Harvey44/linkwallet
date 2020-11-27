package com.mobile.linkwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.developer.kalert.KAlertDialog;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.models.DefaultResponse;
import com.mobile.linkwallet.storage.SharedPrefManager;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loan_Final extends AppCompatActivity {

    EditText web, amount;
    String pos, tob, fname, lname, mn, email2, company, country,psize;
    View pv, bv, size;
    Button submit;
    ImageView back, help, chat;
    TextView bt, pt, st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp == 320 || configuration.smallestScreenWidthDp == 400 || configuration.smallestScreenWidthDp == 420 ||
                configuration.smallestScreenWidthDp == 480){
            setContentView(R.layout.activity_loan__final_small);
        }
        else {
            setContentView(R.layout.activity_loan__final);
        }

        init();
        getbalance();
        pos = getBaseContext().getResources().getString(R.string.bw);
        tob = getBaseContext().getResources().getString(R.string.bd);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            fname = bundle.getString("fname");
            lname = bundle.getString("lname");
            mn = bundle.getString("mn");
            email2 = bundle.getString("email");
            company = bundle.getString("company");
            country = bundle.getString("country");

        }
    }

    private void init(){
        web = findViewById(R.id.et);
        amount = findViewById(R.id.et2);
        pv = findViewById(R.id.et8);
        bv = findViewById(R.id.et4);
        size = findViewById(R.id.et9);
        submit = findViewById(R.id.nexts);
        back = findViewById(R.id.imageView56);
        bt = findViewById(R.id.id);
        pt = findViewById(R.id.id2);
        st = findViewById(R.id.id3);
        help = findViewById(R.id.imageView46);
        chat = findViewById(R.id.imageView47);

        bv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });

        pv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(Loan_Final.this, loan_form.class));
                onBackPressed();

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



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit();
            }
        });
    }

    private void dialog(){
        final String[] items = {getBaseContext().getResources().getString(R.string.bd),getBaseContext().getResources().getString(R.string.ex),getBaseContext().getResources().getString(R.string.ico)  };

        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Loan_Final.this);
        builder.setCancelable(true);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    tob = "Broker-deal";
                }
                else if(which == 1){
                    tob = "Exchange";
                }
                else if(which == 2){
                    tob = "ICO";

                }
                bt.setText(tob);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void dialog2(){
        final String[] items = {getBaseContext().getResources().getString(R.string.bw),getBaseContext().getResources().getString(R.string.cu),getBaseContext().getResources().getString(R.string.cp3)  };

        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Loan_Final.this);
        builder.setCancelable(true);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    pos = "Business Wallet";

                }
                else if(which == 1){
                    pos = "Custody";
                }
                else if(which == 2){
                    pos= "Custom Project";

                }
                pt.setText(pos);
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void submit(){

        String website = web.getText().toString();
        String loan = amount.getText().toString();
        String email = SharedPrefManager.getInstance(getBaseContext()).getUser().getEmail();
        int id = SharedPrefManager.getInstance(getBaseContext()).getUser().getId();
        String cmd = "loan_request";
        SharedPreferences sharedPreference = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + sharedPreference.getString("token", null);

        if(website.isEmpty()){
            web.setError("Website "+ getBaseContext().getResources().getString(R.string.error_msg));
            web.requestFocus();
            return;
        }
        if(loan.isEmpty()){
            amount.setError("Loan amount "+ getBaseContext().getResources().getString(R.string.error_msg));
            amount.requestFocus();
            return;
        }

        final AlertDialog waitingDialog = new SpotsDialog(Loan_Final.this);
        waitingDialog.show();
        Call<DefaultResponse> call = ApiClient.getInstance().getApi().loan_form(id, email, fname, lname, mn, email2, company, website, tob, pos, loan, psize, country, cmd, authToken);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                waitingDialog.dismiss();
                if(!defaultResponse.isErr()){

                    waitingDialog.dismiss();
                    KAlertDialog kAlertDialog = new KAlertDialog(Loan_Final.this, KAlertDialog.SUCCESS_TYPE).setContentText(defaultResponse.getMsg()).
                            setConfirmText("Done").setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                        @Override
                        public void onClick(KAlertDialog kAlertDialog) {
                            startActivity(new Intent(Loan_Final.this,Loan.class));
                            Animatoo.animateDiagonal(Loan_Final.this);
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

    private void getbalance(){
        int Id = SharedPrefManager.getInstance(Loan_Final.this).getUser().getId();
        String email = SharedPrefManager.getInstance(Loan_Final.this).getUser().getEmail();
        String cmd = "all_balance";
        // SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(Loan_Final.this).getUser().getLogin_token();

        Call<DefaultResponse> call2 = ApiClient.getInstance().getApi().all_balance(Id, email, cmd, authToken);
        call2.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse( Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                if (defaultResponse != null && !defaultResponse.isErr()) {
               /* SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putString("balance", defaultResponse.getBalance().getBalance_comma_sign());
               editor.apply();*/
                    st.setText(defaultResponse.getBalance().getBalance_comma_sign());
                    psize = defaultResponse.getBalance().getBalance_comma_sign();




                }
                else if(defaultResponse.isErr() && defaultResponse.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }


            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                // Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void logout() {
        SharedPrefManager.getInstance(Loan_Final.this).clear();
        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(Loan_Final.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
