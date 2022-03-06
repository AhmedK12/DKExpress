package com.kamdan.dkexpress.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.kamdan.dkexpress.Orders;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.wallet.Wallet;

public class Profile extends AppCompatActivity {
    TextView myprofile,mywallet,myorders,logedin,aboutus,refund_policy,privacy_polocy;
    LinearLayout refund,privacy,about;
    RelativeLayout Whole;
    View newuserdata;
    ImageButton back;
    EditText fname,lname,phone,village, nearbyplace, pincode,address;
    Button Next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        Assignment();
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Whole.setVisibility(View.GONE);
                about.setVisibility(View.VISIBLE);
            }
        });
        refund_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Whole.setVisibility(View.GONE);
                refund.setVisibility(View.VISIBLE);
            }
        });
        privacy_polocy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Whole.setVisibility(View.GONE);
                privacy.setVisibility(View.VISIBLE);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about.setVisibility(View.GONE);
                refund.setVisibility(View.GONE);
                privacy.setVisibility(View.GONE);
                if(Whole.getVisibility()==View.GONE)
                    Whole.setVisibility(View.VISIBLE);
                else{
                    startActivity(new Intent(Profile.this,groceryproduct.class));
                    finish();
                }
            }
        });
        mywallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Wallet.class));
            }
        });
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, com.kamdan.dkexpress.Profile.class));
            }
        });
        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Orders.class));
            }
        });
       if(FirebaseAuth.getInstance().getCurrentUser()==null)
           newuserdata.setVisibility(View.VISIBLE);
       Next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Set_User_Info();
           }
       });
    }
    public void Assignment(){
        myorders = findViewById(R.id.myorders);
        myprofile = findViewById(R.id.myprofile);
        mywallet = findViewById(R.id.mywallet);
        logedin = findViewById(R.id.login);
        aboutus = findViewById(R.id.aboutus);
        refund_policy = findViewById(R.id.refund);
        privacy_polocy = findViewById(R.id.privacy_policy);
        refund = findViewById(R.id.refund_policyl);
        privacy = findViewById(R.id.privacy_policyl);
        about = findViewById(R.id.aboutusl);
        Whole = findViewById(R.id.wholerelative);
        back = findViewById(R.id.back_button);
        newuserdata = findViewById(R.id.user_data);
        fname = newuserdata.findViewById(R.id.First_name);
        lname = newuserdata.findViewById(R.id.last_Name);
        phone = newuserdata.findViewById(R.id.mobile_no);
        village = newuserdata.findViewById(R.id.village);
        pincode = newuserdata.findViewById(R.id.pincode);
        address = newuserdata.findViewById(R.id.address);
        nearbyplace = newuserdata.findViewById(R.id.landmark);
        Next = newuserdata.findViewById(R.id.next);
    }
    public void Set_User_Info(){
        if(fname.getText().toString().trim().length()<4){
            Toast.makeText(this,"Enter Name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(phone.getText().toString().trim().length()==10||phone.getText().toString().trim().length()==12||phone.getText().toString().trim().length()==13)){
            Toast.makeText(this,"Enter Valid Number",Toast.LENGTH_SHORT).show();
            return;
        }
        if(village.getText().toString().trim().length()==0){
            Toast.makeText(this,"Enter Village",Toast.LENGTH_SHORT).show();
            return;
        }
        if(nearbyplace.getText().toString().trim().length()==0){
            Toast.makeText(this,"Enter Near By Place",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(pincode.getText().toString().trim().length()==6&&!pincode.getText().toString().startsWith("0"))){
            Toast.makeText(this,"Enter Near By Place",Toast.LENGTH_SHORT).show();
            return;
        }
        if(address.getText().toString().trim().length()==0){
            Toast.makeText(this,"Enter Address",Toast.LENGTH_SHORT).show();
            return;
        }
        String[] data = new String[9];
        data[0] = fname.getText().toString().trim();
        data[1] = lname.getText().toString().trim();
        data[2] = phone.getText().toString().trim();
        data[3] = village.getText().toString().trim();
        data[4] = nearbyplace.getText().toString().trim();
        data[5] = pincode.getText().toString().trim();
        data[6] = address.getText().toString().trim();
        data[7] = "₹100.00";
        startActivity(new Intent(this,Registration.class).putExtra("data",data));
        finish();
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Profile.this,groceryproduct.class));
        finish();
    }
}