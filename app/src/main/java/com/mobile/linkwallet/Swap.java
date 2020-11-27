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
import com.mobile.linkwallet.models.SwapResp;
import com.mobile.linkwallet.models.Swaplist;
import com.mobile.linkwallet.models.SwaplistResponse;
import com.mobile.linkwallet.storage.SharedPrefManager;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Swap extends AppCompatActivity {

   PowerSpinnerView to, from;
    RelativeLayout rl;
    String item_from, item_to, from_name, from_id, to_id, to_name, email, authToken;
    LoadingButton swap, main_swap;
    EditText amt;
    ImageView back;
    LoadingView lv;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap);

        from = findViewById(R.id.from);
        to = findViewById(R.id.too);
        swap = findViewById(R.id.lang_btn);
        main_swap = findViewById(R.id.swap);
        amt = findViewById(R.id.amount);
        amt.setVisibility(View.INVISIBLE);
        main_swap.setVisibility(View.INVISIBLE);
        back = findViewById(R.id.img);
        rl = findViewById(R.id.rl);
        lv = findViewById(R.id.lv);
        rl.setVisibility(View.INVISIBLE);
        id = SharedPrefManager.getInstance(Swap.this).getUser().getId();
        email = SharedPrefManager.getInstance(Swap.this).getUser().getEmail();
        authToken = "Bearer " + SharedPrefManager.getInstance(Swap.this).getUser().getLogin_token();
        String cmd = "list_coin_swap";
        ArrayList<String> list_from = new ArrayList<>();
        ArrayList<String> list_sym= new ArrayList<>();
        ArrayList<String> list_to = new ArrayList<>();
        ArrayList<String> list_to_sym= new ArrayList<>();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Swap.this, Swap_main.class));
                Animatoo.animateDiagonal(Swap.this);
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
                        from_name = coinListResponse.getResult().get(i).getName();
                        from_id =  coinListResponse.getResult().get(i).getSymbol();
                        list_from.add(from_name);
                        list_sym.add(from_id);
                        from.setItems(list_from);
                        to.setItems(list_from);
                    }
                }

                else if (coinListResponse.isError()){
                    Toast.makeText(Swap.this, coinListResponse.getMessage(), Toast.LENGTH_LONG).show();
                    lv.stop();
                }
            }

            @Override
            public void onFailure(Call<CoinListResponse> call, Throwable t) {

            }
        });

        from.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override public void onItemSelected(int position, String item) {
               item_from = list_sym.get(position);
            }
        });

       to.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override public void onItemSelected(int position, String item) {
                item_to = list_sym.get(position);
            }
        });

//        from.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
//            @Override public void onItemSelected(int position, String item) {
//
//
//               if (position == 0){
//                   item_from = "BTC";
//               }
//                else if (position == 1){
//                    item_from = "ETH";
//                }
//               else if (position == 2){
//                   item_from = "BCH";
//               }
//               else if (position == 3){
//                   item_from = "LTC";
//               }
//               else if (position == 4){
//                   item_from = "XMR";
//               }
//               else if (position == 5){
//                   item_from = "XRP";
//               }
//            }
//        });
//
//
//        to.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
//            @Override public void onItemSelected(int position, String item) {
//
//                if (position == 0){
//                    item_to = "BTC";
//                }
//                else if (position == 1){
//                    item_to  = "ETH";
//                }
//                else if (position == 2){
//                    item_to  = "BCH";
//                }
//                else if (position == 3){
//                    item_to  = "LTC";
//                }
//                else if (position == 4){
//                    item_to  = "XMR";
//                }
//                else if (position == 5){
//                    item_to  = "XRP";
//                }
//            }
//        });


        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perform_swap();
            }
        });

    }

    void perform_swap(){
        swap.startLoading();
        String cmd = "swap_process";

        String froms = item_from;
        String tos = item_to;


        // SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);

        Call<SwaplistResponse> call = ApiClient.getInstance().getApi().swap_list(id, email, froms, tos, cmd,  authToken);

        call.enqueue(new Callback<SwaplistResponse>() {
            @Override
            public void onResponse(Call<SwaplistResponse> call, Response<SwaplistResponse> response) {
                SwaplistResponse swaplistResponse = response.body();
                if (!swaplistResponse.isError()) {
                    amt.setVisibility(View.VISIBLE);
                    main_swap.setVisibility(View.VISIBLE);
                    swap.setVisibility(View.GONE);
                    amt.setHint("Enter Amount in " + froms);

//                    Intent intent = new Intent(Swap.this, Swap_main.class);
//                    Bundle bundle = new Bundle();
//                    Bundle bundle_to = new Bundle();
//                    bundle.putParcelableArrayList("from", (ArrayList<? extends Parcelable>) swaplistResponse.getResult_from());
//                    bundle_to.putParcelableArrayList("to", (ArrayList<? extends Parcelable>) swaplistResponse.getResult_to());
//                    intent.putExtras(bundle);
//                    intent.putExtras(bundle_to);
//                    Swap.this.startActivity(intent);
                    //from
                    ArrayList<List<Swaplist>> swaplistList = new ArrayList<>();
                    ArrayList<String> list_from = new ArrayList<>();
                    ArrayList<String> list_from_id= new ArrayList<>();

                    for (int i = 0; i<swaplistResponse.getResult_from().size() ; i++){
                            from_name = swaplistResponse.getResult_from().get(i).getName();
                            from_id = swaplistResponse.getResult_from().get(i).getId();
                            list_from.add(from_name + "  " + "("+swaplistResponse.getResult_from().get(i).getBalance_fiat_symbol()+")");
                            list_from_id.add(from_id);

                    }
                    from.setItems(list_from);
                    from.setHint("Select Wallet");
                    from.setText("Select Wallet");
                    from.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
                        @Override public void onItemSelected(int position, String item) {
                            from_id = list_from_id.get(position);
                        }
                    });

                    //to
                    ArrayList<String> list_to = new ArrayList<>();
                    ArrayList<String> list_to_id= new ArrayList<>();

                    for (int i = 0; i<swaplistResponse.getResult_to().size() ; i++){
                        to_name = swaplistResponse.getResult_to().get(i).getName();
                        to_id = swaplistResponse.getResult_to().get(i).getId();
                        list_to.add(to_name + "  " + "("+swaplistResponse.getResult_to().get(i).getBalance_fiat_symbol()+")");
                        list_to_id.add(to_id);

                    }
                    to.setItems(list_to);
                    to.setHint("Select Wallet");
                    to.setText("Select Wallet");
                    to.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
                        @Override public void onItemSelected(int position, String item) {
                            to_id = list_to_id.get(position);
                        }
                    });



                    main_swap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            swap_main();
                        }
                    });








                }

                else if (swaplistResponse.isError()){
                    Toast.makeText(Swap.this, swaplistResponse.getMessage(), Toast.LENGTH_LONG).show();
                    swap.reset();
                    swap.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<SwaplistResponse> call, Throwable t) {
                swap.reset();
            }
        });

    }

    void swap_main(){
        main_swap.startLoading();
        String email = SharedPrefManager.getInstance(Swap.this).getUser().getEmail();
        String froms = item_from;
        String tos = item_to;
        String Cmd = "swap";
        String amount = amt.getText().toString();
        int id = SharedPrefManager.getInstance(Swap.this).getUser().getId();
        final String authToken = "Bearer " + SharedPrefManager.getInstance(Swap.this).getUser().getLogin_token();
        Call<SwapResp> call = ApiClient.getInstance().getApi().swap(id, email, froms, tos, from_id, to_id,  amount, Cmd,  authToken);
        call.enqueue(new Callback<SwapResp>() {
            @Override
            public void onResponse(Call<SwapResp> call, Response<SwapResp> response) {
                SwapResp swapResp = response.body();
                if (!swapResp.isError()){
                    main_swap.loadingSuccessful();
                    KAlertDialog kAlertDialog = new KAlertDialog(Swap.this, KAlertDialog.SUCCESS_TYPE).setContentText(swapResp.getMessage()).
                            setConfirmText("Done").setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                        @Override
                        public void onClick(KAlertDialog kAlertDialog) {
                            startActivity(new Intent(Swap.this, Swap_main.class));
                            Animatoo.animateDiagonal(Swap.this);
                        }
                    });
                    kAlertDialog.show();
                }

                else if (swapResp.isError()){
                    main_swap.reset();
                    main_swap.cancelLoading();
                    Toast.makeText(Swap.this, swapResp.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SwapResp> call, Throwable t) {
                main_swap.reset();
                main_swap.cancelLoading();
            }
        });
    }

}
