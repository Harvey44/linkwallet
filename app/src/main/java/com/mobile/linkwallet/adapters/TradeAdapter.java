package com.mobile.linkwallet.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mobile.linkwallet.R;
import com.mobile.linkwallet.models.Trade;

import java.util.List;

public class TradeAdapter  extends RecyclerView.Adapter<TradeAdapter.ViewHolder>{
    private Context context;
    private List<Trade> tradeList;

    public TradeAdapter(Context context, List<Trade> tradeList) {
        this.context = context;
        this.tradeList = tradeList;
    }

    @NonNull
    @Override
    public TradeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_list, parent, false);
        return new TradeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeAdapter.ViewHolder holder, int position) {
                final Trade trades = tradeList.get(position);
                holder.asset.setText(context.getResources().getString(R.string.rtt)+" " + trades.getAsset());
                holder.status.setText(trades.getStatus());
                holder.status.setTextColor(Color.parseColor(trades.getColor()));
                holder.date.setText(trades.getRequest_date());

                if(trades.getStatus().contains("Approved")){
                    holder.image.setImageResource(R.drawable.ic_access_time_black_24dp);
                    holder.note.setText(context.getResources().getString(R.string.note5));
                }
                else if(trades.getStatus().contains("Pending")){
            holder.image.setImageResource(R.drawable.ic_access_time_yellow_24dp);
                    holder.note.setText(context.getResources().getString(R.string.note4));
        }
                else{
                    holder.image.setImageResource(R.drawable.ic_access_time_red_24dp);
                    holder.note.setText(context.getResources().getString(R.string.note6));
                }

    }

    @Override
    public int getItemCount() {
        return tradeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView asset, date, status, note;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           asset = itemView.findViewById(R.id.textView148);
            date = itemView.findViewById(R.id.textView150);
            status = itemView.findViewById(R.id.textView149);
            note = itemView.findViewById(R.id.textView151);
            image = itemView.findViewById(R.id.imageView60);

        }
    }
}
