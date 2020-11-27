package com.mobile.linkwallet;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.loadingview.LoadingDialog;
import com.github.loadingview.LoadingView;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.adapters.ListAdapter;
import com.mobile.linkwallet.models.CoinListResponse;
import com.mobile.linkwallet.models.CoinResult;
import com.mobile.linkwallet.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllCoins extends Fragment implements Profile.onBackPressedListner {
    private RecyclerView recyclerView;
    private List<CoinResult> coinResults;
    private ListAdapter adapter;
    ImageView arr;
    LoadingView lv;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_coins, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        arr = view.findViewById(R.id.image);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv = view.findViewById(R.id.lv);

        lv.start();




        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Home();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

            }
        });

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
                adapter = new ListAdapter(getActivity(), coinResults);
                recyclerView.setAdapter(adapter);
                lv.stop();
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CoinListResponse> call, Throwable t) {

            }
        });






    }

    @Override
    public boolean onBackPressed() {
        // changeFragment();
        startActivity(new Intent(getContext(), Profile.class));
        return false;
    }

}
