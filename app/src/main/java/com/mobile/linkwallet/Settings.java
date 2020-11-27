package com.mobile.linkwallet;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobile.linkwallet.Api.ApiClient;
import com.mobile.linkwallet.models.BackupPhrase_Response;
import com.mobile.linkwallet.models.CancelResponse;
import com.mobile.linkwallet.models.DefaultResponse;
import com.mobile.linkwallet.storage.SharedPrefManager;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment implements View.OnClickListener, Profile.onBackPressedListner {

    private View v1,v2,v3,v4,v5, v6, v7, v8, v9, v10, v11, v12;
    Button backup, logout;
    Switch switch1;
    private String share_app;
    ImageView arrow, Profile, Password, support, feed, abt, link;
    String currency;
    TextView Backup_status;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadLocale();

        v1 = view.findViewById(R.id.v5);
        v2 = view.findViewById(R.id.v7);
        v3 = view.findViewById(R.id.v13);
        v4 = view.findViewById(R.id.v15);
        v5 = view.findViewById(R.id.v17);
        v6 = view.findViewById(R.id.v20);
        v7 = view.findViewById(R.id.v22);
        v8 = view.findViewById(R.id.v24);
        v9 = view.findViewById(R.id.v24);
//        v10= view.findViewById(R.id.v26);
        v11= view.findViewById(R.id.v28);
        v12 = view.findViewById(R.id.v30);
        switch1 = view.findViewById(R.id.switch1);
        logout = view.findViewById(R.id.btn7);
        String first = SharedPrefManager.getInstance(getActivity()).getUser().getFirstname();
        String last =  SharedPrefManager.getInstance(getActivity()).getUser().getLastname();
        Backup_status = view.findViewById(R.id.text16);
        backup = view.findViewById(R.id.backup);
        init();

    }

        private void init(){

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_backup();
            }
        });

            v1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    profile();
                }
            });

            v2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), Change_Password.class));
                }
            });
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_curr();
            }
        });

        v5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_lang();
            }
        });

            switch1.setChecked(SharedPrefManager.getInstance(getActivity()).getSettings().isGcode());


            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(switch1.isChecked()){
                    /*SharedPreferences.Editor editor = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();*/

                        startActivity(new Intent(getActivity(), Create_2fa.class));


                    }
                    else if(!switch1.isChecked()){
                    /*SharedPreferences.Editor editor = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();*/
                        deactivate_2fa();
                    }
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logout();
                }
            });

            v6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("https://chainlinkwallet.freshdesk.com");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });

            v7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("https://chainlinkwallet.freshdesk.com");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });

//            v10.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Uri uri = Uri.parse("https://www.chainlink-wallet.com");
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);
//                }
//            });

            v8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  share_app = "https://play.google.com/store/apps/details?id=com.mobile.linkwallet";
//                    Intent sharing = new Intent(Intent.ACTION_SEND);
//                    sharing.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.mobile.linkwallet"));
//                    sharing.putExtra(Intent.EXTRA_SUBJECT, "Download Link Wallet");
//                    sharing.putExtra(Intent.EXTRA_TEXT, share_app);
//                    startActivity(Intent.createChooser(sharing, "Share link via"));

                    Intent share = new Intent();
                    share.setAction(Intent.ACTION_SEND);
                    share.putExtra(Intent.EXTRA_TEXT, share_app);
                    share.setType("text/plain");
                    startActivity(share);
                }
            });

            v11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("https://team.chain.link");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }

    private void profile(){
        startActivity(new Intent(getActivity(), View_Profile.class));

    }

    private void select_curr(){
        View view  = LayoutInflater.from(getActivity()).inflate(R.layout.select_currency, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(view).setCancelable(true);
        final AlertDialog alertDialog = builder.create();

        String selected = SharedPrefManager.getInstance(getActivity()).getSettings().getCurrency();
        int curr = 0;

        switch (selected) {
            case "USD":
                curr = 0;
                break;
            case "EUR":
                curr = 2;
                break;
            case "GBP":
                curr = 4;
                break;
            case "CNY":
                curr = 6;
                break;
        }




        RadioGroup rg = view.findViewById(R.id.rg);
        final RadioButton rb1 = view.findViewById(R.id.rb1);
        final RadioButton rb2 = view.findViewById(R.id.rb2);
        final RadioButton rb3 = view.findViewById(R.id.rb3);
        final RadioButton rb4 = view.findViewById(R.id.rb4);

        int id = rg.getChildAt(curr).getId();
        //rg.check(id);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(rb1.isChecked()){
                    currency = "USD";
                    edit_curr();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb2.isChecked()){
                    currency = "EUR";
                    edit_curr();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb3.isChecked()){
                    currency = "GBP";
                    edit_curr();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb4.isChecked()){
                    currency = "CNY";
                    edit_curr();
                    changeFragment();
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog.show();
    }

    private  void changeFragment() {
       /* Fragment fragment = new Home();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
 */
        startActivity(new Intent(getActivity(), Profile.class));
    }

    private void edit_lang(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Lang", Activity.MODE_PRIVATE);
        String language = sharedPreferences.getString("My_Lang", "en");
        int id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
        String email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
        String cmd = "update_language";
        SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + sharedPreference.getString("token", null);

        Call<DefaultResponse> call = ApiClient.getInstance().getApi().edit_lang(id, email, language, cmd,authToken);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                if(!defaultResponse.isErr()){
                    //  Toast.makeText(getActivity(), defaultResponse.getMsg(), Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), defaultResponse.getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });




    }

    private void edit_curr(){
        int id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
        String email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
        String cmd = "update_currency";
        SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + sharedPreference.getString("token", null);

        Call<DefaultResponse> call = ApiClient.getInstance().getApi().edit_curr(id, email, currency, cmd, authToken);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                if(!defaultResponse.isErr()){
                    //  Toast.makeText(getActivity(), defaultResponse.getMsg(), Toast.LENGTH_LONG).show();
                    // SharedPrefManager.getInstance(getActivity()).saveSettings(defaultResponse.getSettings());
                 /*   SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("currency", currency);
                    editor.apply();*/

                }
                else {
                    Toast.makeText(getActivity(), defaultResponse.getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }

    private void select_lang(){
        View views  = LayoutInflater.from(getActivity()).inflate(R.layout.select_lang, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(views).setCancelable(true);
        final AlertDialog alertDialog = builder.create();
        TextView tv = views.findViewById(R.id.textView139);

        RadioGroup rg = views.findViewById(R.id.rg);
        final RadioButton rb1 = views.findViewById(R.id.rb1);
        final RadioButton rb2 = views.findViewById(R.id.rb2);
        final RadioButton rb3 = views.findViewById(R.id.rb3);
        final RadioButton rb4 = views.findViewById(R.id.rb4);
        final RadioButton rb5 = views.findViewById(R.id.rb5);
        final RadioButton rb6 = views.findViewById(R.id.rb6);
        final RadioButton rb7 = views.findViewById(R.id.rb7);
        final RadioButton rb8 = views.findViewById(R.id.rb8);
        final RadioButton rb9 = views.findViewById(R.id.rb9);
        final RadioButton rb10 = views.findViewById(R.id.rb10);
        final  RadioButton rb11 = views.findViewById(R.id.rb11);
        final RadioButton rb12 = views.findViewById(R.id.rb12);
        final RadioButton selected;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Lang", Activity.MODE_PRIVATE);
        int language = sharedPreferences.getInt("checkedid", 6);
        int id = rg.getChildAt(language).getId();


        rg.check(id);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(rb1.isChecked()){

                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 0);
                    editor.apply();
                    edit_lang();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb2.isChecked()){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 2);
                    editor.apply();
                    setLocale("zh");
                    edit_lang();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb3.isChecked()){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 4);
                    editor.apply();
                    setLocale("nl");
                    edit_lang();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb4.isChecked()){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 6);
                    editor.apply();
                    setLocale("en");
                    edit_lang();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb5.isChecked()){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 8);
                    editor.apply();
                    setLocale("fr");
                    edit_lang();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb6.isChecked()){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 10);
                    editor.apply();
                    setLocale("de");
                    edit_lang();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb7.isChecked()){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 12);
                    editor.apply();
                    setLocale("it");
                    edit_lang();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb8.isChecked()){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 14);
                    editor.apply();
                    setLocale("ja");
                    edit_lang();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb9.isChecked()){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 16);
                    editor.apply();
                    setLocale("ko");
                    edit_lang();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb10.isChecked()){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 18);
                    editor.apply();
                    setLocale("pt");
                    edit_lang();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb11.isChecked()){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 20);
                    editor.apply();
                    setLocale("ru");
                    edit_lang();
                    changeFragment();
                    alertDialog.dismiss();
                }
                if(rb12.isChecked()){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
                    editor.putInt("checkedid", 22);
                    editor.apply();
                    setLocale("es");
                    edit_lang();
                    changeFragment();
                    alertDialog.dismiss();
                }
            }

        });








        alertDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getActivity().getResources().updateConfiguration(configuration, getActivity().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Lang", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();


    }

    private void loadLocale(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Lang", Activity.MODE_PRIVATE);
        String language = sharedPreferences.getString("My_Lang", "en");
        setLocale(language);

    }

    private void deactivate_2fa() {
        final View views = LayoutInflater.from(getActivity()).inflate(R.layout.cancel_2fa, null);
        final EditText gcode = views.findViewById(R.id.gcode);
        Button gcancel = views.findViewById(R.id.gcancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(views).setCancelable(true);
        final AlertDialog alertDialog = builder.create();




        gcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Google_code = gcode.getText().toString().trim();
                final String Email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
                final int id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
                String Cmd = "deactivate_2fa";
                // SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                final String authToken = "Bearer " + SharedPrefManager.getInstance(getActivity()).getUser().getLogin_token();

                if (Google_code.isEmpty()) {
                    gcode.setError("Authenticator code  is required");
                    //code.requestFocus();
                }
                Call<CancelResponse> responseCall = ApiClient.getInstance().getApi().deactivate_2fa(id, Email, Google_code, Cmd, authToken);
                responseCall.enqueue(new Callback<CancelResponse>() {
                    @Override
                    public void onResponse(Call<CancelResponse> call, Response<CancelResponse> response) {
                        CancelResponse cancelResponse = response.body();

                        if (!response.body().isError()) {

                            Toast.makeText(getActivity(), cancelResponse.getMessage(), Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("gcode", false);
                            editor.apply();



                        }
                        else if(cancelResponse.isError() && cancelResponse.getErrorMessage().equals("Invalid Token Request")){
                            logout();

                        }
                        else if (cancelResponse.isError()) {
                            Toast.makeText(getActivity(), cancelResponse.getMessage(), Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }
                        alertDialog.dismiss();



                    }

                    @Override
                    public void onFailure(Call<CancelResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getContext(), Profile.class));
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    private void create_backup(){
        String Email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
        int id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
        String Cmd = "create_backup_phrase";
        //SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(getActivity()).getUser().getLogin_token();

        Call<BackupPhrase_Response> call = ApiClient
                .getInstance().getApi().backup(id, Email, Cmd, authToken);

        call.enqueue(new Callback<BackupPhrase_Response>() {
            @Override
            public void onResponse(Call<BackupPhrase_Response> call, Response<BackupPhrase_Response> response) {
                BackupPhrase_Response backupPhrase_response = response.body();
                if(!response.body().isError()){

                    final View view  = LayoutInflater.from(getActivity()).inflate(R.layout.backuphrase, null);
                    final TextView Private =  view.findViewById(R.id.p1);
                    final TextView P2 =  view.findViewById(R.id.p2);
                    final TextView P3 =  view.findViewById(R.id.p3);
                    final TextView P4 =  view.findViewById(R.id.p4);
                    final TextView P5 =  view.findViewById(R.id.p5);
                    final TextView P6 =  view.findViewById(R.id.p6);
                    final TextView P7 =  view.findViewById(R.id.p7);
                    final TextView P8 =  view.findViewById(R.id.p8);
                    final TextView P9 =  view.findViewById(R.id.p9);
                    final TextView P10 =  view.findViewById(R.id.p10);
                    final TextView P11 =  view.findViewById(R.id.p11);
                    final TextView P12 =  view.findViewById(R.id.p12);
                    Private.setText(backupPhrase_response.getResult().get(0).getWord());
                    P2.setText(backupPhrase_response.getResult().get(1).getWord());
                    P3.setText(backupPhrase_response.getResult().get(2).getWord());
                    P4.setText(backupPhrase_response.getResult().get(3).getWord());
                    P5.setText(backupPhrase_response.getResult().get(4).getWord());
                    P6.setText(backupPhrase_response.getResult().get(5).getWord());
                    P7.setText(backupPhrase_response.getResult().get(6).getWord());
                    P8.setText(backupPhrase_response.getResult().get(7).getWord());
                    P9.setText(backupPhrase_response.getResult().get(8).getWord());
                    P10.setText(backupPhrase_response.getResult().get(9).getWord());
                    P11.setText(backupPhrase_response.getResult().get(10).getWord());
                    P12.setText(backupPhrase_response.getResult().get(11).getWord());

                    backup.setText("Retrieve");
                    Backup_status.setText("Retrieve Backup Phrase");
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("backup", "Retrieve");
                    editor.putString("backup_status", "Retrieve Backup Phrase");
                    editor.apply();





                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder
                            .setView(view).setCancelable(true);


                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else if(response.body().isError()){
                    BackupPhrase_Response backupPhrase_response1 = response.body();
                    Toast.makeText(getActivity(),backupPhrase_response1.getMessage(), Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("backup", "Create");
                    editor.putString("backup_status", getString(R.string.backup2));
                    editor.putString("Backup_note", getString(R.string.backup));
                    editor.apply();

                }
                else if(backupPhrase_response.isError() && backupPhrase_response.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }
            }

            @Override
            public void onFailure(Call<BackupPhrase_Response> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    private void logout() {
        SharedPrefManager.getInstance(getActivity()).clear();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(getActivity(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
