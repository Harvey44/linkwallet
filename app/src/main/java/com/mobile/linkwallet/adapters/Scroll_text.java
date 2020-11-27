package com.mobile.linkwallet.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mobile.linkwallet.R;
import com.mobile.linkwallet.View_Noty;
import com.mobile.linkwallet.models.Noty;

import java.util.ArrayList;

public class Scroll_text extends RecyclerView.Adapter<Scroll_text.ViewHolder> {
    private Context context;
    private ArrayList<Noty> noties;
    private ArrayList<Noty> notyArrayList;

    public Scroll_text(Context context, ArrayList<Noty> noties) {
        this.context = context;
        this.noties = noties;
        this.notyArrayList = notyArrayList;
    }

    @NonNull
    @Override
    public Scroll_text.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noty, parent, false);
        return new Scroll_text.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Noty noty = noties.get(position);
        //holder.title.setText();
        holder.title.setText(noty.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, View_Noty.class);
                intent.putExtra("ID", noty.getId());
                intent.putExtra("Time", noty.getNotify_date());
                context.startActivity(intent);
            }
        });



    }


    @Override
    public int getItemCount() {
        return noties.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView142);
        }
    }
}
