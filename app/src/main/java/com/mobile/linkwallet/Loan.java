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
import com.mobile.linkwallet.adapters.LoanAdapter;
import com.mobile.linkwallet.models.LoanResponse;
import com.mobile.linkwallet.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loan extends AppCompatActivity {

    RecyclerView rvl;
    private List loanResponses;
    private LoanAdapter loanAdapter;
    TextView no_loan;
    View v;
    ImageView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        rvl = findViewById(R.id.rvl);
        no_loan = findViewById(R.id.textView);
        v = findViewById(R.id.v);
        add = findViewById(R.id.imageView61);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Loan.this, Pre_loan.class));
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Loan.this, Pre_loan.class));
            }
        });
        rvl.setLayoutManager(new LinearLayoutManager(Loan.this));
        if(isNetworkAvailable()){
            loan_list();
        }
        else{
            no_loan.setText(getBaseContext().getResources().getString(R.string.no_int));
            no_loan.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    private void loan_list(){
        int id = SharedPrefManager.getInstance(Loan.this).getUser().getId();
        String email = SharedPrefManager.getInstance(Loan.this).getUser().getEmail();
        String cmd = "list_loan_request";
        final String authToken = "Bearer " +  SharedPrefManager.getInstance(Loan.this).getUser().getLogin_token();

        Call<LoanResponse> call = ApiClient.getInstance().getApi().loan_list(id, email, cmd, authToken);
        call.enqueue(new Callback<LoanResponse>() {
            @Override
            public void onResponse(Call<LoanResponse> call, Response<LoanResponse> response) {
                LoanResponse loanResponse = response.body();
                if (loanResponse != null && !loanResponse.isError()) {
                    if (response.body() != null) {
                        no_loan.setVisibility(View.INVISIBLE);
                        loanResponses = response.body().getResult();
                    }
                    loanAdapter = new LoanAdapter(Loan.this, loanResponses);
                    rvl.setAdapter(loanAdapter);
                    /*no1.setVisibility(View.INVISIBLE);
                    no2.setVisibility(View.INVISIBLE);*/

                    // Toast.makeText(View_wallet.this, historyResponse.getResult().get(0).getImage(), Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<LoanResponse> call, Throwable t) {

            }
        });
    }

    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Loan.this);
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
        startActivity(new Intent(Loan.this, Profile.class));
        Animatoo.animateSwipeLeft(Loan.this);
    }
}
