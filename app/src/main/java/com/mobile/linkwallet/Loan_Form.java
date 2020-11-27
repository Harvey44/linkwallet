package com.mobile.linkwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class Loan_Form extends AppCompatActivity {

    String type = "National Identity";
    String serv = "";
    View tob, pos;
    TextView pt,bt;
    EditText fn, ln, ea, cmp, phone;
    CountryCodePicker ctry;
    Button next;
    ImageView help, chat, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp == 320 || configuration.smallestScreenWidthDp == 400 || configuration.smallestScreenWidthDp == 420 ||
                configuration.smallestScreenWidthDp == 480){
            setContentView(R.layout.activity_loan__form_small);
        }
        else {
            setContentView(R.layout.activity_loan__form);
        }
        init();
    }

    private void init(){
        fn = findViewById(R.id.et);
        ln = findViewById(R.id.et2);
        ea = findViewById(R.id.et5);
        phone = findViewById(R.id.et3);
        cmp = findViewById(R.id.et7);
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
               // startActivity(new Intent(Loan_form.this, Pre_loan.class));
            }
        });




    }

    private void bundle(){
        String fname = fn.getText().toString();
        String lname = ln.getText().toString();
        String email = ea.getText().toString();
        String company = cmp.getText().toString();
        final String country = ctry.getSelectedCountryName();
        String mn = phone.getText().toString();

        if(fname.isEmpty()){
            fn.setError("Your First Name "+ getBaseContext().getResources().getString(R.string.error_msg));
            fn.requestFocus();
            return;
        }
        if(lname.isEmpty()){
            ln.setError("Last Name "+ getBaseContext().getResources().getString(R.string.error_msg));
            ln.requestFocus();
            return;
        }

        if(mn.isEmpty()){
            phone.setError("Phone Number "+ getBaseContext().getResources().getString(R.string.error_msg));
            phone.requestFocus();
            return;
        }

        if(email.isEmpty()){
            ea.setError("Email Address "+ getBaseContext().getResources().getString(R.string.error_msg));
            ea.requestFocus();
            return;
        }

        if(company.isEmpty()){
            cmp.setError("Company name "+ getBaseContext().getResources().getString(R.string.error_msg));
            cmp.requestFocus();
            return;
        }


        Bundle bundle = new Bundle();
        bundle.putString("fname", fname);
        bundle.putString("lname", lname);
        bundle.putString("mn", mn);
        bundle.putString("email", email);
        bundle.putString("country", country);
        bundle.putString("company", company);

        Intent intent = new Intent(Loan_Form.this, Loan_Final.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
