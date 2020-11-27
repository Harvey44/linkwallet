package com.mobile.linkwallet.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mobile.linkwallet.R;
import com.mobile.linkwallet.View_Wallet;
import com.mobile.linkwallet.models.Wallets;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    private Context context;
    private List<Wallets> walletsList;
    private List<Wallets> mfilteredList;

    public WalletAdapter(Context context, List<Wallets> walletsList) {
        this.context = context;
        this.walletsList = walletsList;
        mfilteredList = walletsList;
    }
    @NonNull
    @Override
    public WalletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_list, parent, false);
        return new WalletAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapter.ViewHolder holder, int position) {
        final Wallets wallets = walletsList.get(position);
        holder.wallet_type.setText(wallets.getCoin_name());
        holder.wallet_name.setText(wallets.getName());
        holder.wallet_fiat.setText(wallets.getBalance_symbol());
        holder.wallet_usd.setText(wallets.getBalance_fiat_comma_symbol());
        Picasso.get().load(wallets.getCoin_image()).into(holder.wallet_img);
        final String qr = wallets.getAddress_qrcode();
        final String coin_icon = wallets.getCoin_image();


        holder.cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, View_Wallet.class);
                intent.putExtra("ID", wallets.getId());
                intent.putExtra("Walletname", wallets.getName());
                intent.putExtra("Balance", wallets.getBalance_fiat());
                intent.putExtra("Coinshort", wallets.getCoin_symbol());
                intent.putExtra("Address", wallets.getAddress());
                intent.putExtra("Coiname", wallets.getCoin_name());
                intent.putExtra("cur_bal", wallets.getBalance_symbol());
                intent.putExtra("qrlink", qr);
                intent.putExtra("coinicon", coin_icon);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return walletsList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView wallet_img;
        TextView wallet_name, wallet_type, wallet_usd, wallet_fiat;
        RelativeLayout cv2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallet_img = itemView.findViewById(R.id.imageView4);
            wallet_type = itemView.findViewById(R.id.textView143);
            wallet_name = itemView.findViewById(R.id.textView145);
            wallet_usd = itemView.findViewById(R.id.textView147);
            wallet_fiat = itemView.findViewById(R.id.textView146);
            cv2 = itemView.findViewById(R.id.rl);

        }
    }


}
