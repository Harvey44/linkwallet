package com.mobile.linkwallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class trade_form extends AppCompatActivity {

    String id_type;
    View id;
    TextView id_text;
    EditText name, dob, ntn, idn, ra, phone;
    Spinner ctry;
    Button next;
    Calendar calendar;
    ImageView help, chat, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp == 320 || configuration.smallestScreenWidthDp == 400 || configuration.smallestScreenWidthDp == 420 ||
                configuration.smallestScreenWidthDp == 480){
            setContentView(R.layout.activity_trade_form_small);
        }
        else {
            setContentView(R.layout.activity_trade_form);
        }
        init();
        dob = findViewById(R.id.et2);
        id_type = getBaseContext().getResources().getString(R.string.ni);
        calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updatelabel();
            }
        };
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(trade_form.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updatelabel() {
        String myformat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myformat, Locale.US);
        dob.setText(sdf.format(calendar.getTime()));
    }


    private void init(){
        id = findViewById(R.id.et4);
        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog();
            }
        });

        id_text = findViewById(R.id. id);
        id_text.setText(id_type);
        name = findViewById(R.id.et);

        ntn = findViewById(R.id.et3);
        idn = findViewById(R.id.et5);
        ra = findViewById(R.id.et6);
        phone = findViewById(R.id.et7);
        ctry = findViewById(R.id.sp8);
        next = findViewById(R.id.next);
        help = findViewById(R.id.imageView46);
        chat = findViewById(R.id.imageView47);
        back = findViewById(R.id.imageView56);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://on-custodian.freshdesk.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://on-custodian.freshdesk.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(trade_form.this, Pre_otc.class));
            }
        });



    }
    private void dialog(){
        final String[] items = {getBaseContext().getResources().getString(R.string.ni),getBaseContext().getResources().getString(R.string.dl),getBaseContext().getResources().getString(R.string.ip)  };

        final AlertDialog.Builder builder = new AlertDialog.Builder(trade_form.this);
        builder.setCancelable(true);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    id_type = "National Identity";

                }
                else if(which == 1){
                    id_type = "Driver's License";
                }
                else if(which == 2){
                    id_type = "International Passport";

                }
                id_text.setText(id_type);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void bundle(){
        String fname = name.getText().toString();
        String date = dob.getText().toString();
        String nationality = ntn.getText().toString();
        String id_no = idn.getText().toString();
        String address = ra.getText().toString();
        final String country = ctry.getSelectedItem().toString();
        String idtype = id_type;
        String mn = phone.getText().toString();

        if(fname.isEmpty()){
            name.setError("Your Full Name "+ getBaseContext().getResources().getString(R.string.error_msg));
            name.requestFocus();
            return;
        }
        if(date.isEmpty()){
            dob.setError("Your Date of Birth "+ getBaseContext().getResources().getString(R.string.error_msg));
            dob.requestFocus();
            return;
        }
        if(nationality.isEmpty()){
            ntn.setError("Nationality "+ getBaseContext().getResources().getString(R.string.error_msg));
            ntn.requestFocus();
            return;
        }
        if(idtype.isEmpty()){
            Toast.makeText(trade_form.this, "Identity Type " + getBaseContext().getResources().getString(R.string.error_msg), Toast.LENGTH_LONG).show();
            return;
        }
        if(id_no.isEmpty()){
            idn.setError("Identity Number "+ getBaseContext().getResources().getString(R.string.error_msg));
            idn.requestFocus();
            return;
        }
        if(address.isEmpty()){
            ra.setError("Residential Address "+ getBaseContext().getResources().getString(R.string.error_msg));
            ra.requestFocus();
            return;
        }
        if(mn.isEmpty()){
            phone.setError("Phone Number "+ getBaseContext().getResources().getString(R.string.error_msg));
            phone.requestFocus();
            return;
        }
        if(country.isEmpty()){
            Toast.makeText(trade_form.this, "Country " + getBaseContext().getResources().getString(R.string.error_msg), Toast.LENGTH_LONG).show();
            return;
        }

        if (country.equals("Country")) {
            Toast.makeText(this, getBaseContext().getResources().getString(R.string.sc2), Toast.LENGTH_LONG).show();
        }

        Bundle bundle = new Bundle();
        bundle.putString("fname", fname);
        bundle.putString("date", date);
        bundle.putString("nationality", nationality);
        bundle.putString("id_no", id_no);
        bundle.putString("address", address);
        bundle.putString("country", country);
        bundle.putString("idtype", idtype);
        bundle.putString("mn", mn);

        Intent intent = new Intent(trade_form.this, trade_final.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
