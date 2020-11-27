package com.mobile.linkwallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.adapters.TradeAdapter;
import com.mobile.linkwallet.models.Trade;
import com.mobile.linkwallet.models.TradeResponse;
import com.mobile.linkwallet.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Otc_Trade extends AppCompatActivity {

    RecyclerView rvl;
    private List<Trade> tradeList;
    private TradeAdapter tradeAdapter;
    TextView no_otc;
    View v;
    ImageView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otc__trade);

        rvl = findViewById(R.id.rvl);
        no_otc = findViewById(R.id.textView);
        v = findViewById(R.id.v);
        add = findViewById(R.id.imageView61);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Otc_Trade.this, Pre_otc.class));
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Otc_Trade.this, Pre_otc.class));
            }
        });


        rvl.setLayoutManager(new LinearLayoutManager(Otc_Trade.this));
        if(isNetworkAvailable()){
            list_otc();
        }
        else{
            no_otc.setText(getBaseContext().getResources().getString(R.string.no_int));
            no_otc.setTextColor(Color.parseColor("#FF0000"));
        }

    }

    private void list_otc(){
        int id = SharedPrefManager.getInstance(Otc_Trade.this).getUser().getId();
        String email = SharedPrefManager.getInstance(Otc_Trade.this).getUser().getEmail();
        String cmd = "list_otc_trade_request";
        final String authToken = "Bearer " +  SharedPrefManager.getInstance(Otc_Trade.this).getUser().getLogin_token();

        Call<TradeResponse> call = ApiClient.getInstance().getApi().otc_list(id, email, cmd, authToken);
        call.enqueue(new Callback<TradeResponse>() {
            @Override
            public void onResponse(Call<TradeResponse> call, Response<TradeResponse> response) {
                TradeResponse tradeResponse = response.body();
                if (tradeResponse != null && !tradeResponse.isError()) {
                    if (response.body() != null) {
                        no_otc.setVisibility(View.INVISIBLE);
                        tradeList = response.body().getResult();
                    }
                    tradeAdapter = new TradeAdapter(Otc_Trade.this, tradeList);
                    rvl.setAdapter(tradeAdapter);
                    /*no1.setVisibility(View.INVISIBLE);
                    no2.setVisibility(View.INVISIBLE);*/
                    // Toast.makeText(View_wallet.this, historyResponse.getResult().get(0).getImage(), Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<TradeResponse> call, Throwable t) {

            }
        });
    }
    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Otc_Trade.this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Otc_Trade.this, Profile.class));
        Animatoo.animateSwipeLeft(Otc_Trade.this);
    }
}
