package com.mobile.linkwallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.github.loadingview.LoadingView;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.adapters.SearchListAdapter;
import com.mobile.linkwallet.models.ListResponse;
import com.mobile.linkwallet.models.Wallets;
import com.mobile.linkwallet.storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class show_all extends AppCompatActivity {
    private RecyclerView recyclerView4;
    private ArrayList<Wallets> walletsList;
    private SearchListAdapter adapter;
    private ImageView back;
    private TextView no, click;
    private LoadingView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        recyclerView4 = findViewById(R.id.recyclerView4);
        back = findViewById(R.id.image);
        no = findViewById(R.id.textView129);
        click = findViewById(R.id.textView134);
        lv = findViewById(R.id.lv);

        lv.start();

        no.setVisibility(View.INVISIBLE);
        click.setVisibility(View.INVISIBLE);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(show_all.this, Select_coin.class));
            }
        });


        recyclerView4.setLayoutManager(new LinearLayoutManager(show_all.this));




        if(isNetworkAvailable()){
            show_all_wallet();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Fragment fragment = new Home();
                // getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                startActivity(new Intent(show_all.this, Profile.class));
            }
        });
    }
    public void show_all_wallet(){
        String Cmd = "list_wallet";
        String Email = SharedPrefManager.getInstance(show_all.this).getUser().getEmail();
        int id = SharedPrefManager.getInstance(show_all.this).getUser().getId();
        SharedPreferences sharedPreference = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(show_all.this).getUser().getLogin_token();



        Call<ListResponse> call = ApiClient.getInstance().getApi().showall(id, Email, Cmd, authToken);
        call.enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                ListResponse listResponse = response.body();
                if (listResponse != null && !listResponse.isError()) {
                   lv.stop();

                    walletsList = response.body().getResult();
                    adapter = new  SearchListAdapter(show_all.this, walletsList);
                    recyclerView4.setAdapter(adapter);
                }

                else if(listResponse.isError() && listResponse.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }
                else {
                    no.setVisibility(View.VISIBLE);
                    click.setVisibility(View.VISIBLE);
                    lv.stop();
                }

            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {


            }
        });


    }

    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(show_all.this);
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
        SharedPrefManager.getInstance(show_all.this).clear();
        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(show_all.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
