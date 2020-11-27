package com.mobile.linkwallet;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.adapters.HorizontalAdapter;
import com.mobile.linkwallet.adapters.ListAdapter;
import com.mobile.linkwallet.adapters.Scroll_text;
import com.mobile.linkwallet.adapters.WalletAdapter;
import com.mobile.linkwallet.models.CoinListResponse;
import com.mobile.linkwallet.models.CoinResult;
import com.mobile.linkwallet.models.DefaultResponse;
import com.mobile.linkwallet.models.ListResponse;
import com.mobile.linkwallet.models.LoginResponse;
import com.mobile.linkwallet.models.MessageResponse;
import com.mobile.linkwallet.models.Noty;
import com.mobile.linkwallet.models.Wallets;
import com.mobile.linkwallet.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements Profile.onBackPressedListner{
    private TextView textViewEmail, textViewName, textViewCountry, nt, tv12, tv144;
    LoginResponse loginResponse;
    ImageView plus, search, np;
    private TextView balance, New, No_wallet, click_here, your, t;
    View add_bg, view48, view77, view78, view79, alc;
    Button show_all, new_wallet;
    private RecyclerView recyclerView2, rv3;
    private ListView listView;
    private ArrayList<Wallets> walletsList;
    private ArrayList<Noty> notyArrayList;
    private Scroll_text scroll_text;
    private WalletAdapter adapter;
    private Dialog dialog;
    private Handler handler;
    int count = 0;
    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;
    private List<CoinResult> coinResults;
    private HorizontalAdapter horizontalAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        balance = view.findViewById(R.id.text7);
        rv3 = view.findViewById(R.id.rv3);
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        No_wallet = view.findViewById(R.id.textView21);
        click_here = view.findViewById(R.id.textView92);
        show_all = view.findViewById(R.id.btn);
        plus = view.findViewById(R.id.add);
        //new_wallet = view.findViewById(R.id.btn4);
        recyclerView = view.findViewById(R.id.recyclerView);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_shared_preff", MODE_PRIVATE);
        balance.setText(sharedPreferences.getString("balance", "$0.00"));
        show_all.setVisibility(View.INVISIBLE);

        LinearLayoutManager layoutManagers = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManagers);
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv3.setLayoutManager(linearLayoutManager);



        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()){

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                try {
                    LinearSmoothScroller smoothScroller = new LinearSmoothScroller(Objects.requireNonNull(getContext())) {
                        private static final float SPEED = 3500f;// Change this value (default=25f)

                        @Override
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return SPEED / displayMetrics.densityDpi;
                        }
                    };
                    smoothScroller.setTargetPosition(position);
                    startSmoothScroll(smoothScroller);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Select_coin.class));
                Animatoo.animateSlideUp(getContext());
            }
        });


        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv3.setLayoutManager(layoutManager);
        rv3.setHasFixedSize(true);
        rv3.setItemViewCacheSize(1000);
        rv3.setDrawingCacheEnabled(true);
        rv3.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        if (isNetworkAvailable()){
            getbalance();
           listwallet();
            list_noty();
            listcoin();
        }
        else if(!isNetworkAvailable()){
            No_wallet.setText("No Internet Connection !!");
            No_wallet.setTextColor(Color.parseColor("#FF0000"));
            click_here.setVisibility(View.INVISIBLE);
            recyclerView2.setVisibility(View.INVISIBLE);
        }
        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), show_all.class));
            }
        });
    }

    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
    private void listwallet(){
        String Cmd = "list_wallet";
        String Email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
        int id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
        int limit = 5;
        // SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(getActivity()).getUser().getLogin_token();


        Call<ListResponse> call = ApiClient.getInstance().getApi().listwallet(id, Email, limit,Cmd, authToken);
        call.enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                ListResponse listResponse = response.body();
                assert listResponse != null;
                if(!listResponse.isError()) {
                    if (response.body() != null) {
                        No_wallet.setVisibility(View.INVISIBLE);
                        click_here.setVisibility(View.INVISIBLE);
                        walletsList = response.body().getResult();
                        adapter = new WalletAdapter(getActivity(), walletsList);
                        recyclerView2.setAdapter(adapter);

                        show_all.setVisibility(View.VISIBLE);
                       // view48.setVisibility(View.VISIBLE);
                    }


                }
                else if(listResponse.isError() && listResponse.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }
                else {
                    No_wallet.setVisibility(View.VISIBLE);
                    click_here.setVisibility(View.VISIBLE);
                   show_all.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {

                No_wallet.setText("No Internet Connection !!");
                No_wallet.setTextColor(Color.parseColor("#FF0000"));
                click_here.setVisibility(View.INVISIBLE);
                recyclerView2.setVisibility(View.INVISIBLE);
            }
        });

    }
    private void list_noty(){
        String Cmd = "list_admin_notification";
        String Type = "unread";
        String Email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
        int id = SharedPrefManager.getInstance(getActivity()).getUser().getId();

        final String authToken = "Bearer " + SharedPrefManager.getInstance(getActivity()).getUser().getLogin_token();

        Call<MessageResponse> call = ApiClient.getInstance().getApi().noty(id, Email, Type, Cmd, authToken);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                MessageResponse messageResponse = response.body();
                if (messageResponse != null && !messageResponse.isError()) {
                    notyArrayList = response.body().getResult();
                    scroll_text = new Scroll_text(getActivity(), notyArrayList);
                    autoScrollAnother();
                    rv3.setAdapter(scroll_text);

                }
                else if(messageResponse.isError() && messageResponse.getErrorMessage().equals("Inavlid Token Request")){
                    logout();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                // Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private  void changeFragment() {
        Fragment fragment = new AllCoins();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
    private void getbalance(){
        int Id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
        String email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
        String cmd = "all_balance";
        // SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(getActivity()).getUser().getLogin_token();

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
                    balance.setText(defaultResponse.getBalance().getBalance_comma_sign());




                }
                else if(defaultResponse.isErr() && defaultResponse.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }

                refresh(15000);
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                // Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void refresh(int ms){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //  content();
                getbalance();
            }
        };
        handler.postDelayed(runnable, ms);

    }
    private void content(){

        count++;

        your.setText("Refreshed " + count);
        refresh(1000);

    }
    public void autoScrollAnother() {
        final int scrollCount = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(count == scroll_text.getItemCount())
                    count = 0;
                if(count < scroll_text.getItemCount()){
                    rv3.smoothScrollToPosition(++count);

                }
                handler.postDelayed(this, 6000);
            }
        };
        handler.postDelayed(runnable, 6000);
    }
    private void logout() {
        SharedPrefManager.getInstance(getActivity()).clear();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(getActivity(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    @Override
    public boolean onBackPressed() {
       /* startActivity(new Intent(getContext(), Profile.class));
        return false;*/
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        return false;
    }

    private void listcoin(){
        String cmd = "list_coin";
        int id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
        String Email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
        Call<CoinListResponse> call = ApiClient.getInstance().getApi().getcoinlist(id, Email, cmd);
        call.enqueue(new Callback<CoinListResponse>() {
            @Override
            public void onResponse(Call<CoinListResponse> call, Response<CoinListResponse> response) {

                CoinListResponse coinListResponse = response.body();
                coinResults = response.body().getResult();
                //  Toast.makeText(getActivity(), coinListResponse.getMessage()+coinResults.get(0).getName(), Toast.LENGTH_LONG).show();
                horizontalAdapter = new HorizontalAdapter(getActivity(), coinResults);
                recyclerView.setAdapter(horizontalAdapter);
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CoinListResponse> call, Throwable t) {

            }
        });


    }


}
