package com.mobile.linkwallet;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;


/**
 * A simple {@link Fragment} subclass.
 */
public class Services extends Fragment {

    RelativeLayout new_wallet, otc, loan, swap, stake;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        otc = view.findViewById(R.id.rl2);
        loan = view.findViewById(R.id.rl3);
        swap = view.findViewById(R.id.rl4);
        stake = view.findViewById(R.id.rl5);
        initViews();

    }

    private void initViews(){


        otc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Otc_Trade.class));
                Animatoo.animateSplit(getActivity());
            }
        });

        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Loan.class));
                Animatoo.animateSplit(getActivity());
            }
        });
        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Swap_main.class));
                Animatoo.animateSplit(getActivity());
            }
        });

        stake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Stake_main.class));
                Animatoo.animateSplit(getActivity());
            }
        });
    }
}
