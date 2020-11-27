package com.mobile.linkwallet.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.New_Wallet;
import com.mobile.linkwallet.R;
import com.mobile.linkwallet.View_Wallet;
import com.mobile.linkwallet.models.CoinResult;
import com.mobile.linkwallet.models.SetNewResponse;
import com.mobile.linkwallet.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapter2 extends RecyclerView.Adapter<ListAdapter2.ViewHolder> {
    private Context context;
    private List<CoinResult> coinResults;

    public ListAdapter2(Context context, List<CoinResult> coinResults) {
        this.context = context;
        this.coinResults = coinResults;
    }

    @NonNull
    @Override
    public ListAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_coins, parent, false);
        return new ListAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter2.ViewHolder holder, int position) {
        final CoinResult coinResult = coinResults.get(position);

        holder.name2.setText(coinResult.getName());
        holder.symbol2.setText("("+coinResult.getSymbol() + ")");

        Picasso.get().load(coinResult.getImage()).into(holder.image2);

        holder.constraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, New_Wallet.class);
                intent.putExtra("coin_name", coinResult.getName());
                intent.putExtra("coin_id", coinResult.getId());
                context.startActivity(intent);
            }
        });

//        holder.constraint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final View views = LayoutInflater.from(context).inflate(R.layout.create_wallet, null);
//                final TextView type = views.findViewById(R.id.wname);
//                final EditText wallet_name =  views.findViewById(R.id.wn);
//                final EditText password1 =  views.findViewById(R.id.wp);
//                final EditText password2 =  views.findViewById(R.id.wp2);
//                final int  coin = coinResult.getId();
//                Button cwallet = views.findViewById(R.id.cwallet);
//                type.setText("Create a new "+coinResult.getName() + " wallet");
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder
//                        .setView(views).setCancelable(true);
//
//
//                final AlertDialog alertDialog = builder.create();
//
//                cwallet.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                       int Coin = coin;
//                        final String Wallet_Name = wallet_name.getText().toString();
//                        String Wallet_Password = password1.getText().toString();
//                        String Wallet_Password2 = password2.getText().toString();
//                        int ID = SharedPrefManager.getInstance(context).getUser().getId();
//                        String Email = SharedPrefManager.getInstance(context).getUser().getEmail();
//                        String Cmd = "create_wallet";
//                       // SharedPreferences sharedPreference = context.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
//                        final String authToken = "Bearer " + SharedPrefManager.getInstance(context).getUser().getLogin_token();
//                        if (Wallet_Name.isEmpty())  {
//                            wallet_name.setError("Email is required");
//                            wallet_name.requestFocus();
//                            return;
//                        }
//                        if (Wallet_Name.length() > 15)  {
//                            wallet_name.setError("Wallet name can't be longer than 15 words");
//                            wallet_name.requestFocus();
//                            return;
//                        }
//
//                        Call<SetNewResponse> call = ApiClient.getInstance().getApi().create_wallet(ID,Email,Coin,Wallet_Name,Wallet_Password,Wallet_Password2,Cmd, authToken);
//                        call.enqueue(new Callback<SetNewResponse>() {
//                            @Override
//                            public void onResponse(Call<SetNewResponse> call, Response<SetNewResponse> response) {
//                                SetNewResponse setNewResponse = response.body();
//                                if(!setNewResponse.isError()){
//                                    Toast.makeText(context, setNewResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                                    alertDialog.dismiss();
//                                    Intent intent = new Intent(context, show_all.class);
//                                    context.startActivity(intent);
//
//
//  }
//                                else if(setNewResponse.isError() && setNewResponse.getErrorMessage().equals("Invalid Token Request")){
//                                    SharedPrefManager.getInstance(context).clear();
//                                    SharedPreferences.Editor editor = context.getSharedPreferences("save", Context.MODE_PRIVATE).edit();
//                                    editor.putBoolean("value", false);
//                                    editor.apply();
//
//                                    Intent intent = new Intent(context, Login.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    context.startActivity(intent);
//
//                                }
//                                else {
//                                    Toast.makeText(context, setNewResponse.getMessage(), Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<SetNewResponse> call, Throwable t) {
//                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//                });
//
//
//                alertDialog.show();
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return coinResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image2;
        TextView name2, symbol2;
        RelativeLayout constraint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image2 = itemView.findViewById(R.id.image2);
            name2 = itemView.findViewById(R.id.name2);
            symbol2 = itemView.findViewById(R.id.symbol2);
            constraint = itemView.findViewById(R.id.rl);
        }
    }
}
