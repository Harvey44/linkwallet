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
import com.mobile.linkwallet.models.Stakes;

import java.util.List;

public class StakeAdapter extends RecyclerView.Adapter<StakeAdapter.ViewHolder> {
    private Context context;
    private List<Stakes> arrayList;

    public StakeAdapter(Context context, List<Stakes> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public StakeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stake_view, parent, false);
        return new StakeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StakeAdapter.ViewHolder holder, int position) {
        final Stakes stakes = arrayList.get(position);
        holder.swap.setText(stakes.getStart_date());
        holder.status.setText("Stake " + stakes.getStatus());
        holder.from.setText("Staked amount : " + stakes.getStake_amount() );
        holder.to.setText("Profit : " + stakes.getStake_profit() );
        holder.status.setTextColor(Color.parseColor(stakes.getColor()));
        holder.date.setText(stakes.getEnd_date());

        if(stakes.getStatus().contains("Complete")){
            holder.image.setImageResource(R.drawable.ic_access_time_black_24dp);

        }
        else if(stakes.getStatus().contains("Running")){
            holder.image.setImageResource(R.drawable.ic_access_time_yellow_24dp);

        }
        else{
            holder.image.setImageResource(R.drawable.ic_access_time_red_24dp);

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView status, date, swap, from, to;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.textView148);
            date = itemView.findViewById(R.id.textView150);
            swap = itemView.findViewById(R.id.textView149);
            from = itemView.findViewById(R.id.textView151);
            to= itemView.findViewById(R.id.textView152);
            image = itemView.findViewById(R.id.imageView60);
        }
    }
}
