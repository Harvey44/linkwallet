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
import com.mobile.linkwallet.models.Loan;

import java.util.List;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.ViewHolder> {
    private Context context;
    private List<Loan> arrayList;

    public LoanAdapter(Context context, List<Loan> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public LoanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_list, parent, false);
        return new LoanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanAdapter.ViewHolder holder, int position) {
        final Loan loans = arrayList.get(position);
        holder.amount.setText(loans.getAmount());
        holder.status.append(" " + loans.getStatus());
        holder.status.setTextColor(Color.parseColor(loans.getColor()));
        holder.date.setText(loans.getRequest_date());

        if(loans.getStatus().contains("Approved")){
            holder.image.setImageResource(R.drawable.ic_access_time_black_24dp);
            holder.note.setText(context.getResources().getString(R.string.note2));
        }
        else if(loans.getStatus().contains("Pending")){
            holder.image.setImageResource(R.drawable.ic_access_time_yellow_24dp);
            holder.note.setText(context.getResources().getString(R.string.note1));
        }
        else{
            holder.image.setImageResource(R.drawable.ic_access_time_red_24dp);
            holder.note.setText(context.getResources().getString(R.string.note3));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView status, date, amount, note;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.textView148);
            date = itemView.findViewById(R.id.textView150);
            amount = itemView.findViewById(R.id.textView149);
            note = itemView.findViewById(R.id.textView151);
            image = itemView.findViewById(R.id.imageView60);
        }
    }
}
