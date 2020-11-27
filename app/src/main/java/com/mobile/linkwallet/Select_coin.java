package com.mobile.linkwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.loadingview.LoadingView;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.adapters.ListAdapter2;
import com.mobile.linkwallet.models.CoinListResponse;
import com.mobile.linkwallet.models.CoinResult;
import com.mobile.linkwallet.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Select_coin extends AppCompatActivity {

    private RecyclerView recyclerView2;
    private List<CoinResult> coinResults;
    private ListAdapter2 adapter;
    ImageView arr3;
    LoadingView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coin);

        recyclerView2 = findViewById (R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(Select_coin.this));
        arr3 = findViewById(R.id.arr3);
        lv = findViewById(R.id.lv);
        lv.start();

        arr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                Animatoo.animateSwipeLeft(Select_coin.this);
            }
        });

        list_coins();
    }

    private void list_coins(){
        String cmd = "list_coin";
        int id = SharedPrefManager.getInstance(Select_coin.this).getUser().getId();
        String email = SharedPrefManager.getInstance(Select_coin.this).getUser().getEmail();
        Call<CoinListResponse> call = ApiClient.getInstance().getApi().getcoinlist(id, email, cmd);

        call.enqueue(new Callback<CoinListResponse>() {
            @Override
            public void onResponse(Call<CoinListResponse> call, Response<CoinListResponse> response) {
                lv.stop();
                CoinListResponse coinListResponse = response.body();
                coinResults = response.body().getResult();
                //  Toast.makeText(getActivity(), coinListResponse.getMessage()+coinResults.get(0).getName(), Toast.LENGTH_LONG).show();
                adapter = new ListAdapter2(Select_coin.this, coinResults);
                recyclerView2.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CoinListResponse> call, Throwable t) {
                Toast.makeText(Select_coin.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
