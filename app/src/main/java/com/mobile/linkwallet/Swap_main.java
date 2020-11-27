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
import com.mobile.linkwallet.adapters.SwapAdapter;
import com.mobile.linkwallet.models.SwaplistResp;
import com.mobile.linkwallet.models.Swaps;
import com.mobile.linkwallet.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Swap_main extends AppCompatActivity {

    RecyclerView rvl;
    private List<Swaps> swaps;
    private SwapAdapter swapAdapter;
    TextView no_otc;
    View v;
    ImageView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_main);

        rvl = findViewById(R.id.rvl);
        no_otc = findViewById(R.id.textView);
        v = findViewById(R.id.v);
        add = findViewById(R.id.imageView61);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Swap_main.this, Swap.class));
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Swap_main.this, Swap.class));
            }
        });

        rvl.setLayoutManager(new LinearLayoutManager(Swap_main.this));

        if(isNetworkAvailable()){
           list_swaps();
        }
        else{
            no_otc.setText(getBaseContext().getResources().getString(R.string.no_int));
            no_otc.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Swap_main.this);
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

    private void list_swaps(){
        int id = SharedPrefManager.getInstance(Swap_main.this).getUser().getId();
        String email = SharedPrefManager.getInstance(Swap_main.this).getUser().getEmail();
        String cmd = "list_swap_request";
        final String authToken = "Bearer " +  SharedPrefManager.getInstance(Swap_main.this).getUser().getLogin_token();

        Call<SwaplistResp> call = ApiClient.getInstance().getApi().all_swap(id, email, cmd, authToken);
        call.enqueue(new Callback<SwaplistResp>() {
            @Override
            public void onResponse(Call<SwaplistResp> call, Response<SwaplistResp> response) {
                SwaplistResp swaplistResp = response.body();
                if (swaplistResp != null && !swaplistResp.isError()) {
                    if (response.body() != null) {
                        no_otc.setVisibility(View.INVISIBLE);
                        swaps = response.body().getResult();
                    }
                    swapAdapter = new SwapAdapter(Swap_main.this, swaps);
                    rvl.setAdapter(swapAdapter);
                    /*no1.setVisibility(View.INVISIBLE);
                    no2.setVisibility(View.INVISIBLE);*/
                    // Toast.makeText(View_wallet.this, historyResponse.getResult().get(0).getImage(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<SwaplistResp> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Swap_main.this, Profile.class));
        Animatoo.animateSwipeLeft(Swap_main.this);
    }
}
