package com.mobile.linkwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.loadingview.LoadingView;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.models.Trans_Response;
import com.mobile.linkwallet.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transaction_info extends AppCompatActivity {

    TextView date, title, desc, info, amount, fee, tnx_id, tnx_url, nill;
    private String trans_id;
    private LoadingView lv;
    ImageView back, copy;
    RelativeLayout rl2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_info);
        initViews();
        lv.start();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        trans_id = getIntent().getStringExtra("transid");




        if(isNetworkAvailable()){
            view_trans();
        }
        else{
            checknetwork();
        }
    }

    private void initViews() {
        lv = findViewById(R.id.lv);
        back = findViewById(R.id.image);
        copy = findViewById(R.id.image2);
        date = findViewById(R.id.textView109);
        title = findViewById(R.id.textView128);
        desc  = findViewById(R.id.textView113);
        amount = findViewById(R.id.textView111);
        fee = findViewById(R.id.textView117);
        tnx_id = findViewById(R.id.textView131);
        tnx_url = findViewById(R.id.textView132);
        rl2 = findViewById(R.id.rl2);

        rl2.setVisibility(View.INVISIBLE);
    }

    private void view_trans(){
        String email = SharedPrefManager.getInstance(Transaction_info.this).getUser().getEmail();
        int id = SharedPrefManager.getInstance(Transaction_info.this).getUser().getId();
        String transaction_id = trans_id;
        String cmd = "view_transaction";
        // SharedPreferences sharedPreference = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(Transaction_info.this).getUser().getLogin_token();

        Call<Trans_Response> call = ApiClient.getInstance().getApi().view_trans(id, email, transaction_id, cmd, authToken);
        call.enqueue(new Callback<Trans_Response>() {
            @Override
            public void onResponse(Call<Trans_Response> call, Response<Trans_Response> response) {
                final Trans_Response trans_response = response.body();
                if(!trans_response.isError()){
                    rl2.setVisibility(View.VISIBLE);
                    lv.stop();


                    date.setText(trans_response.getResult().getTrans_date());
                    title.setText(trans_response.getResult().getTitle());
                    desc.setText(trans_response.getResult().getDescription());
                    String usd = trans_response.getResult().getAmount_fiat();
                    String cur = trans_response.getResult().getAmount();
                    String usdfee = trans_response.getResult().getFee_fiat();
                    String curfee = trans_response.getResult().getFee();
                    amount.setText(usd + " = " + cur);
                    fee.setText(curfee + " = " + usdfee);
                    String TNX_ID = trans_response.getResult().getTxn_id();
                    String TNX_URL = trans_response.getResult().getTxn_url();

                    if(TNX_ID.equals("null")){
                        tnx_id.setText("Not Available");
                        copy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(Transaction_info.this, "Transaction id Not Available", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    else{
                        final String txn_id = trans_response.getResult().getTxn_id();
                        tnx_id.setText(trans_response.getResult().getTxn_id());
                        copy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clipData = ClipData.newPlainText("Secret CodeTxn_ID", txn_id);
                                clipboardManager.setPrimaryClip(clipData);
                                Toast.makeText(Transaction_info.this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    if (TNX_URL.equals("null")){
                        tnx_url.setText("Not Available");
                    }
                    else{
                        tnx_url.setText("VIEW IN BLOCKCHAIN");
                        tnx_url.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = trans_response.getResult().getTxn_url();
                                Uri uri = Uri.parse(url);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
                    }



                }

                else if(trans_response.isError() && trans_response.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }
                else{
                    Toast.makeText(Transaction_info.this, trans_response.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Trans_Response> call, Throwable t) {

            }
        });


    }


    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Transaction_info.this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please Enable Internet Connection");
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    private boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

        if(isConnected){
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checknetwork();
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    private void logout() {
        SharedPrefManager.getInstance(Transaction_info.this).clear();
        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(Transaction_info.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
