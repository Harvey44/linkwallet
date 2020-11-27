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
import com.mobile.linkwallet.adapters.StakeAdapter;
import com.mobile.linkwallet.adapters.SwapAdapter;
import com.mobile.linkwallet.models.Stakes;
import com.mobile.linkwallet.models.StakesResponse;
import com.mobile.linkwallet.models.SwaplistResp;
import com.mobile.linkwallet.storage.SharedPrefManager;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class Stake_main extends AppCompatActivity {

     RecyclerView rvl;
     private List<Stakes> stakes;
     private StakeAdapter stakeAdapter;
     TextView no_otc;
     View v;
     ImageView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stake_main);

        rvl = findViewById(R.id.rvl);
        no_otc = findViewById(R.id.textView);
        v = findViewById(R.id.v);
        add = findViewById(R.id.imageView61);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Stake_main.this, Stake.class));
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Stake_main.this, Stake.class));
            }
        });

        rvl.setLayoutManager(new LinearLayoutManager(Stake_main.this));

        if(isNetworkAvailable()){
           list_stakes();
        }
        else{
            no_otc.setText(getBaseContext().getResources().getString(R.string.no_int));
            no_otc.setTextColor(Color.parseColor("#FF0000"));
        }
    }

     private void checknetwork(){
         AlertDialog.Builder builder = new AlertDialog.Builder(Stake_main.this);
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

     private void list_stakes(){
         int id = SharedPrefManager.getInstance(Stake_main.this).getUser().getId();
         String email = SharedPrefManager.getInstance(Stake_main.this).getUser().getEmail();
         String cmd = "list_stake_request";
         final String authToken = "Bearer " +  SharedPrefManager.getInstance(Stake_main.this).getUser().getLogin_token();

         Call<StakesResponse> call = ApiClient.getInstance().getApi().stakes(id, email, cmd, authToken);
         call.enqueue(new Callback<StakesResponse>() {
             @Override
             public void onResponse(Call<StakesResponse> call, Response<StakesResponse> response) {
                 StakesResponse stakesResponse = response.body();
                 if (stakesResponse != null && !stakesResponse.isError()) {
                     if (response.body() != null) {
                         no_otc.setVisibility(View.INVISIBLE);
                         stakes = response.body().getResult();
                     }
                     stakeAdapter = new StakeAdapter(Stake_main.this, stakes);
                     rvl.setAdapter(stakeAdapter);
                    /*no1.setVisibility(View.INVISIBLE);
                    no2.setVisibility(View.INVISIBLE);*/
                     // Toast.makeText(View_wallet.this, historyResponse.getResult().get(0).getImage(), Toast.LENGTH_LONG).show();

                 }
             }

             @Override
             public void onFailure(Call<StakesResponse> call, Throwable t) {

             }
         });

     }

     @Override
     public void onBackPressed() {
         super.onBackPressed();
         startActivity(new Intent(Stake_main.this, Profile.class));
         Animatoo.animateSwipeLeft(Stake_main.this);
     }
}
