package com.mobile.linkwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.developer.kalert.KAlertDialog;
import com.dx.dxloadingbutton.lib.LoadingButton;
import com.github.loadingview.LoadingView;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.models.CoinListResponse;
import com.mobile.linkwallet.models.StakeResp;
import com.mobile.linkwallet.models.StakeResponse;
import com.mobile.linkwallet.models.SwaplistResponse;
import com.mobile.linkwallet.storage.SharedPrefManager;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Stake extends AppCompatActivity {

    PowerSpinnerView coin, plan, wallet;
    RelativeLayout rl;
    String item_coin, item_plan, item_wallet, plan_id, wallet_id, email, authToken, coin_name, coin_sym;
    int id;
    LoadingButton stake, main_stake;
    EditText amt;
    ImageView back;
    LoadingView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stake);

        coin = findViewById(R.id.coin);
        plan = findViewById(R.id.plan);
        wallet = findViewById(R.id.wallet);
        stake = findViewById(R.id.stake);
        main_stake = findViewById(R.id.main_stake);
        amt = findViewById(R.id.amount);
        amt.setVisibility(View.INVISIBLE);
        back = findViewById(R.id.img);
        rl = findViewById(R.id.rl);
        lv = findViewById(R.id.lv);
        rl.setVisibility(View.INVISIBLE);
        id = SharedPrefManager.getInstance(Stake.this).getUser().getId();
        email = SharedPrefManager.getInstance(Stake.this).getUser().getEmail();
        authToken = "Bearer " + SharedPrefManager.getInstance(Stake.this).getUser().getLogin_token();
        String cmd = "list_coin_stake";
        ArrayList<String> list_from = new ArrayList<>();
        ArrayList<String> list_sym= new ArrayList<>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Stake.this, Stake_main.class));
                Animatoo.animateDiagonal(Stake.this);
            }
        });

        Call<CoinListResponse> call = ApiClient.getInstance().getApi().listcoinswap(id, email, cmd);
        call.enqueue(new Callback<CoinListResponse>() {
            @Override
            public void onResponse(Call<CoinListResponse> call, Response<CoinListResponse> response) {
                CoinListResponse coinListResponse = response.body();
                if(!coinListResponse.isError()){
                    lv.stop();
                    rl.setVisibility(View.VISIBLE);
                    for (int i = 0; i<coinListResponse.getResult().size() ; i++){
                       coin_name = coinListResponse.getResult().get(i).getName();
                        coin_sym =  coinListResponse.getResult().get(i).getSymbol();
                        list_from.add(coin_name);
                        list_sym.add(coin_sym);
                        coin.setItems(list_from);

                    }
                }

                else if (coinListResponse.isError()){
                    Toast.makeText(Stake.this, coinListResponse.getMessage(), Toast.LENGTH_LONG).show();
                    lv.stop();
                }
            }

            @Override
            public void onFailure(Call<CoinListResponse> call, Throwable t) {

            }
        });

        coin.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override public void onItemSelected(int position, String item) {
                item_coin = list_sym.get(position);
            }
        });

//        coin.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
//            @Override public void onItemSelected(int position, String item) {
//
//
//                if (position == 0){
//                    item_coin = "BTC";
//                }
//                else if (position == 1){
//                    item_coin = "ETH";
//                }
//                else if (position == 2){
//                    item_coin = "BCH";
//                }
//                else if (position == 3){
//                    item_coin = "LTC";
//                }
//                else if (position == 4){
//                    item_coin = "XMR";
//                }
//                else if (position == 5){
//                    item_coin = "XRP";
//                }
//            }
//        });

        stake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perform_stake();
            }
        });


    }

    private void perform_stake() {
        stake.startLoading();
        String cmd = "stake_process";
//        String email = SharedPrefManager.getInstance(Stake.this).getUser().getEmail();
        String coins = item_coin;
//        int id = SharedPrefManager.getInstance(Stake.this).getUser().getId();
//
//        // SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
//        final String authToken = "Bearer " + SharedPrefManager.getInstance(Stake.this).getUser().getLogin_token();
        Call<StakeResp> call = ApiClient.getInstance().getApi().stake_list(id, email, coins, cmd,  authToken);

        call.enqueue(new Callback<StakeResp>() {
            @Override
            public void onResponse(Call<StakeResp> call, Response<StakeResp> response) {
                StakeResp  stakeResp = response.body();
                if (!stakeResp.isError()) {
                    stake.loadingSuccessful();
                    coin.setVisibility(View.INVISIBLE);
                    stake.setVisibility(View.INVISIBLE);

                    //plans
                    ArrayList<String> plan_list = new ArrayList<>();
                    ArrayList<String> plan_list_id= new ArrayList<>();

                    for (int i = 0; i<stakeResp.getPlan_result().size() ; i++){
                        item_plan = stakeResp.getPlan_result().get(i).getName();
                        plan_id = stakeResp.getPlan_result().get(i).getId();
                       plan_list.add(item_plan + "  " + "("+stakeResp.getPlan_result().get(i).getPercentage_cent()+")");
                        plan_list_id.add(plan_id);

                    }
                    plan.setVisibility(View.VISIBLE);
                    plan.setItems(plan_list);
                    plan.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
                        @Override public void onItemSelected(int position, String item) {
                            plan_id = plan_list_id.get(position);
                        }
                    });


                    //wallet
                    ArrayList<String> wallet_list = new ArrayList<>();
                    ArrayList<String> wallet_list_id= new ArrayList<>();

                    for (int i = 0; i<stakeResp.getWallet_result().size() ; i++){
                        item_wallet = stakeResp.getWallet_result().get(i).getName();
                        wallet_id = stakeResp.getWallet_result().get(i).getId();
                        wallet_list.add(item_wallet + "  " + "("+stakeResp.getWallet_result().get(i).getBalance_fiat_symbol()+")");
                        wallet_list_id.add(wallet_id);

                    }
                    wallet.setVisibility(View.VISIBLE);
                    amt.setVisibility(View.VISIBLE);
                    amt.setHint("Enter Amount in " + coins);
                    wallet.setItems(wallet_list);
                    wallet.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
                        @Override public void onItemSelected(int position, String item) {
                            wallet_id = wallet_list_id.get(position);
                        }
                    });

                    main_stake.setVisibility(View.VISIBLE);

                    main_stake.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            perform_main_stake();
                        }
                    });

                }

                else if(stakeResp.isError()){
                    stake.reset();
                    stake.cancelLoading();
                    Toast.makeText(Stake.this, stakeResp.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<StakeResp> call, Throwable t) {
                stake.reset();
                stake.cancelLoading();
            }
        });
    }

    private  void perform_main_stake(){
        main_stake.startLoading();
        String email = SharedPrefManager.getInstance(Stake.this).getUser().getEmail();
        String coin = item_coin;
        String wallet = wallet_id;
        String plan = plan_id;
        String cmd = "stake";
        String amount = amt.getText().toString();
        int id = SharedPrefManager.getInstance(Stake.this).getUser().getId();
        final String authToken = "Bearer " + SharedPrefManager.getInstance(Stake.this).getUser().getLogin_token();
        Call<StakeResponse> call = ApiClient.getInstance().getApi().stake(id, email, coin, wallet, plan,  amount, cmd,  authToken);

        call.enqueue(new Callback<StakeResponse>() {
            @Override
            public void onResponse(Call<StakeResponse> call, Response<StakeResponse> response) {
                StakeResponse stakeResponse = response.body();
                if (!stakeResponse.isError()){
                    main_stake.loadingSuccessful();
                    KAlertDialog kAlertDialog = new KAlertDialog(Stake.this, KAlertDialog.SUCCESS_TYPE).setContentText("Stake Placed Successfully").
                            setConfirmText("Done").setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                        @Override
                        public void onClick(KAlertDialog kAlertDialog) {
                            startActivity(new Intent(Stake.this, Stake_main.class));
                            Animatoo.animateDiagonal(Stake.this);
                        }
                    });
                    kAlertDialog.show();
                    main_stake.reset();
                    main_stake.setVisibility(View.INVISIBLE);
                }

                else if(stakeResponse.isError()){
                    Toast.makeText(Stake.this, stakeResponse.getMessage(), Toast.LENGTH_LONG).show();
                    main_stake.reset();
                    main_stake.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<StakeResponse> call, Throwable t) {
                main_stake.reset();
                main_stake.cancelLoading();
            }
        });
    }
}
