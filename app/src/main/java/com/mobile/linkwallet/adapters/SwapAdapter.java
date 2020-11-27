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
import com.mobile.linkwallet.models.Swaps;

import java.util.List;

public class SwapAdapter  extends RecyclerView.Adapter<SwapAdapter.ViewHolder>{
    private Context context;
    private List<Swaps> arrayList;

    public SwapAdapter(Context context, List<Swaps> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SwapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swap_view, parent, false);
        return new SwapAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwapAdapter.ViewHolder holder, int position) {
        final Swaps swaps = arrayList.get(position);
        holder.swap.setText(swaps.getCoin_from_code() + " to " + swaps.getCoin_to_code());
        holder.status.setText("Request " + swaps.getStatus());
        holder.from.setText("From : " + swaps.getAmount_from() );
        holder.to.setText("To : " + swaps.getAmount_to() );
        holder.status.setTextColor(Color.parseColor(swaps.getColor()));
        holder.date.setText(swaps.getRequest_date());

        if(swaps.getStatus().contains("Approved")){
            holder.image.setImageResource(R.drawable.ic_access_time_black_24dp);

        }
        else if(swaps.getStatus().contains("Pending")){
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
