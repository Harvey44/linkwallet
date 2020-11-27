package com.mobile.linkwallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.adapters.TransactionsAdapter;
import com.mobile.linkwallet.models.HistoryResponse;
import com.mobile.linkwallet.models.Recovercode_Response;
import com.mobile.linkwallet.models.Transact;
import com.mobile.linkwallet.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class View_Wallet extends AppCompatActivity {

    Button send, receive, proceed;
    View contentView, view, view2;
    String text, wallet_type, currency_type, amount, wallet_address, password2, wallet_nam;
    TextView wallet_name, wallet_bal, no1, no2, cname, cshort, cur;
    ImageView arr4, icon, swap, back;
    RecyclerView recyclerView7;
    private List<Transact> transactList;
    private TransactionsAdapter transactionsAdapter;
    String wallet_id;
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, b11, delete, enter;
    TextInputEditText amt;
    TextInputLayout input;
    EditText address, password;
    SwipeRefreshLayout swipe2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__wallet);
        initViews();

        final String Id = getIntent().getStringExtra("ID");
        final String balance = getIntent().getStringExtra("Balance");
        wallet_type = getIntent().getStringExtra("Coinshort");
        wallet_nam = getIntent().getStringExtra("Walletname");
        final String coiname = getIntent().getStringExtra("Coiname");
        final String cur_bal = getIntent().getStringExtra("cur_bal");
        final String address = getIntent().getStringExtra("Address");
        final String qrcode = getIntent().getStringExtra("qrlink");
        final String coin_icon = getIntent().getStringExtra("coinicon");

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_Wallet.this, Receive.class);
                intent.putExtra("ID", Id);
                intent.putExtra("Walletname", wallet_nam);
                intent.putExtra("Balance", balance);
                intent.putExtra("address", address);
                intent.putExtra("Coiname", coiname);
                intent.putExtra("cur_bal", cur_bal);
                intent.putExtra("qrcode", qrcode);
                intent.putExtra("coinicon", coin_icon);
                startActivity(intent);
            }
        });

        recyclerView7.setLayoutManager(new LinearLayoutManager(View_Wallet.this));


        wallet_id = Id;
        wallet_name.setText(wallet_nam);
        wallet_bal.setText(cur_bal);
        cname.setText(coiname);
        cshort.setText(wallet_type);
        if (coiname.contentEquals("Bitcoin")){
            icon.setImageResource(R.drawable.btc);
        }
       else if (coiname.contentEquals("Ethereum")){
            icon.setImageResource(R.drawable.eth);
        }
       else{
        Picasso.get().load(coin_icon).into(icon);}

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Wallet.this, Profile.class));
            }
        });

        if(isNetworkAvailable()){
            show_trans();
        }


        else{
            no2.setVisibility(View.INVISIBLE);
            no1.setText("No Internet Connection !!");
            no1.setTextColor(Color.parseColor("#FF0000"));
        }

    }

    private void initViews(){
        send = findViewById(R.id.btn);
        wallet_name = findViewById(R.id.text);
        wallet_bal = findViewById(R.id.text4);
        receive = findViewById(R.id.btn2);
        arr4 = findViewById(R.id.image);
        icon = findViewById(R.id.image3);
        recyclerView7 = findViewById(R.id.recyclerView7);
        no1 = findViewById(R.id.textView105);
        cname = findViewById(R.id.text2);
        cshort = findViewById(R.id.text3);
        back = findViewById(R.id.image);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RoundedBottomSheetDialog bottomsheet = new RoundedBottomSheetDialog(View_Wallet.this);
                contentView = View.inflate(View_Wallet.this,R.layout.send_process, null);
                amt = contentView.findViewById(R.id.amt);
                input = contentView.findViewById(R.id.input);
                swap = contentView.findViewById(R.id.swap);
                cur = contentView.findViewById(R.id.text);
                b1 = contentView.findViewById(R.id.btn);
                b2 = contentView.findViewById(R.id.btn2);
                b3 = contentView.findViewById(R.id.btn3);
                b4 = contentView.findViewById(R.id.btn4);
                b5 = contentView.findViewById(R.id.btn5);
                b6 = contentView.findViewById(R.id.btn6);
                b7 = contentView.findViewById(R.id.btn7);
                b8= contentView.findViewById(R.id.btn8);
                b9 = contentView.findViewById(R.id.btn9);
                b0 = contentView.findViewById(R.id.btn10);
                b11 = contentView.findViewById(R.id.btn11);
                delete = contentView.findViewById(R.id.del);
                enter = contentView.findViewById(R.id.ent);
                currency_type = "2";
                swap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cur.getText().equals("$")){
                            cur.setText(wallet_type);
                            currency_type = "1";
                        }
                        else if(cur.getText().equals(wallet_type)){
                            cur.setText("$");
                            currency_type = "2";
                        }
                    }
                });
                //String amount = amt.getText().toString();
                route();
                enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String amount = amt.getText().toString();
                        if (amount.isEmpty()) {
                            //editTextEmail.setError("Email is required");
                            input.setError("Enter Amount");
                            amt.requestFocus();
                            return;
                        }

                        RoundedBottomSheetDialog bottomsheet2 = new RoundedBottomSheetDialog(View_Wallet.this);
                        view = View.inflate(View_Wallet.this,R.layout.send_process_two, null);
                        address = view.findViewById(R.id.w_address);
                        password = view.findViewById(R.id.pwd_field);
                        proceed = view.findViewById(R.id.button2);

                        proceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                review();
                            }
                        });


                        bottomsheet2.setContentView(view);
                        bottomsheet2.show();
                        Animatoo.animateSlideRight(View_Wallet.this);


                    }
                });

                bottomsheet.setContentView(contentView);
                bottomsheet.show();
                Animatoo.animateSlideUp(View_Wallet.this);


            }
        });


    }
    private void route(){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt.setText(amt.getText().insert(amt.getText().length(), "1"));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt.setText(amt.getText().insert(amt.getText().length(), "2"));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt.setText(amt.getText().insert(amt.getText().length(), "3"));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt.setText(amt.getText().insert(amt.getText().length(), "4"));
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt.setText(amt.getText().insert(amt.getText().length(), "5"));
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt.setText(amt.getText().insert(amt.getText().length(), "6"));
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt.setText(amt.getText().insert(amt.getText().length(), "7"));
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt.setText(amt.getText().insert(amt.getText().length(), "8"));
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt.setText(amt.getText().insert(amt.getText().length(), "9"));
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt.setText(amt.getText().insert(amt.getText().length(), "0"));
            }
        });
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt.setText(amt.getText().insert(amt.getText().length(), "."));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amt.getText().length() == 0){
                    Toast.makeText(View_Wallet.this, "Enter Amount", Toast.LENGTH_LONG).show();
                }
                else{
                    amt.setText(amt.getText().delete(amt.getText().length()-1, amt.getText().length()));
                }
            }
        });

    }
    private void review(){
        final android.app.AlertDialog waitingDialog = new SpotsDialog(View_Wallet.this);
        waitingDialog.show();
        final String wallet_address = address.getText().toString();
        final String amount = amt.getText().toString();
        final String pass = password.getText().toString();
        final int id = SharedPrefManager.getInstance(View_Wallet.this).getUser().getId();
        final String email = SharedPrefManager.getInstance(View_Wallet.this).getUser().getEmail();
        final String cmd = "send_review";
        //SharedPreferences sharedPreference = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(View_Wallet.this).getUser().getLogin_token();

        if (wallet_address.isEmpty())  {
            address.setError("Wallet Address is required");
            address.requestFocus();
            return;
        }
        if (amount.isEmpty())  {
            amt.setError("Amount Value is required");
            amt.requestFocus();
            return;
        }
        if (pass.isEmpty())  {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }


        Call<Recovercode_Response> call = ApiClient.getInstance().getApi().review(id, email, pass, wallet_id, amount, wallet_address, currency_type, wallet_type, cmd, authToken);
        call.enqueue(new Callback<Recovercode_Response>() {
            @Override
            public void onResponse(Call<Recovercode_Response> call, Response<Recovercode_Response> response) {
                waitingDialog.dismiss();
                Recovercode_Response recovercode_response = response.body();
                if(!recovercode_response.isError()){
                    Toast.makeText(View_Wallet.this, recovercode_response.getMessage(), Toast.LENGTH_LONG).show();

                    String fee = recovercode_response.getResult().get(0).getFee_left() + " = " +
                            recovercode_response.getResult().get(0).getFee_right() ;
                    String amt_txt = "Amount" + " " + recovercode_response.getCoin().getCoin_name();
                    Intent intent = new Intent(View_Wallet.this, Profile.class);
                   // intent.putExtra("w_name", W_name);
                    intent.putExtra("w_id", wallet_id);
                    intent.putExtra("address", wallet_address);
                    intent.putExtra("amount", amount);
                    intent.putExtra("currencytype", currency_type);
                    intent.putExtra("walletype", wallet_type);
                    intent.putExtra("pwd", pass);
                    intent.putExtra("usd", recovercode_response.getResult().get(0).getRight_amount());
                    intent.putExtra("coin", recovercode_response.getResult().get(0).getLeft_amount());
                    intent.putExtra("fee", fee);
                    intent.putExtra("amt_txt", amt_txt);
                    intent.putExtra("info", recovercode_response.getResult().get(0).getWallet_type());
                  //  startActivity(intent);

                    RoundedBottomSheetDialog bottomsheet2 = new RoundedBottomSheetDialog(View_Wallet.this);
                    view2 = View.inflate(View_Wallet.this,R.layout.send_process_three, null);
                    TextView walletname, tfee, coin_amt, c_amt, address, status;
                    final Button pos;

                    walletname = view2.findViewById(R.id.txt4);
                    tfee = view2.findViewById(R.id.fee);
                    coin_amt = view2.findViewById(R.id.coin_amt);
                    c_amt = view2.findViewById(R.id.c_amt);
                    address = view2.findViewById(R.id.address);
                    status = view2.findViewById(R.id.txt7);
                    pos = view2.findViewById(R.id.btn);

                    walletname.setText(wallet_nam);
                    c_amt.setText(recovercode_response.getResult().get(0).getRight_amount());
                    coin_amt.setText(recovercode_response.getResult().get(0).getLeft_amount());
                    tfee.setText(fee);
                    address.setText(wallet_address);
                    status.setText(recovercode_response.getResult().get(0).getWallet_type());
                    final String cmd2 = "send";

                    pos.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final android.app.AlertDialog waitingDialog = new SpotsDialog(View_Wallet.this);
                            waitingDialog.show();
                            pos.setEnabled(false);
                            Call<Recovercode_Response> call = ApiClient.getInstance().getApi().review(id, email, pass, wallet_id, amount, wallet_address, currency_type, wallet_type, cmd2, authToken);
                            call.enqueue(new Callback<Recovercode_Response>() {
                                @Override
                                public void onResponse(Call<Recovercode_Response> call, Response<Recovercode_Response> response) {

                                    Recovercode_Response recovercode_response = response.body();
                                    if(!recovercode_response.isError()){
                                        waitingDialog.dismiss();
                                        KAlertDialog kAlertDialog = new KAlertDialog(View_Wallet.this, KAlertDialog.SUCCESS_TYPE).setContentText(recovercode_response.getMessage()).
                                                setConfirmText(getApplicationContext().getResources().getString(R.string.done2)).setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                            @Override
                                            public void onClick(KAlertDialog kAlertDialog) {
                                                startActivity(new Intent(View_Wallet.this, Profile.class));
                                                Animatoo.animateDiagonal(View_Wallet.this);
                                            }
                                        });
                                        kAlertDialog.show();


                                    }

                                    else if(recovercode_response.isError() && recovercode_response.getErrorMessage().equals("Invalid Token Request")){
                                        logout();

                                    }
                                    else{
                                        Toast.makeText(View_Wallet.this, recovercode_response.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Recovercode_Response> call, Throwable t) {
                                        waitingDialog.dismiss();
                                }
                            });
                        }
                    });



                    bottomsheet2.setContentView(view2);
                    bottomsheet2.show();
                    Animatoo.animateSlideRight(View_Wallet.this);




                }
                else if(recovercode_response.isError() && recovercode_response.getErrorMessage().equals("Invalid Token Request")){
                    waitingDialog.dismiss();
                    logout();

                }
                else{
                    Toast.makeText(View_Wallet.this, recovercode_response.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Recovercode_Response> call, Throwable t) {
                waitingDialog.dismiss();

            }
        });


    }

    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(View_Wallet.this);
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
    private void show_trans(){
        int id = SharedPrefManager.getInstance(View_Wallet.this).getUser().getId();
        String email = SharedPrefManager.getInstance(View_Wallet.this).getUser().getEmail();
        String cmd = "transaction_history";
        // SharedPreferences sharedPreference = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " +  SharedPrefManager.getInstance(View_Wallet.this).getUser().getLogin_token();

        Call<HistoryResponse> call = ApiClient.getInstance().getApi().trans(id, email, wallet_id, cmd, authToken);
        call.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                HistoryResponse historyResponse = response.body();
                if (historyResponse != null && !historyResponse.isError()) {
                    if (response.body() != null) {
                        transactList = response.body().getResult();
                    }
                    transactionsAdapter = new TransactionsAdapter(View_Wallet.this, transactList);
                    recyclerView7.setAdapter(transactionsAdapter);
                    no1.setVisibility(View.INVISIBLE);

                    // Toast.makeText(View_wallet.this, historyResponse.getResult().get(0).getImage(), Toast.LENGTH_LONG).show();

                }
                else if(historyResponse.isError() && historyResponse.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }
                else{
                    no1.setVisibility(View.VISIBLE);
                   // no2.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {

            }
        });
    }
    private void logout() {
        SharedPrefManager.getInstance(View_Wallet.this).clear();
        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(View_Wallet.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
