package com.kamdan.dkexpress.wallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.model.MyProfile;
import com.kamdan.dkexpress.products.Helper;

import java.util.Objects;

public class Wallet extends AppCompatActivity {
    TextView balance;
    ImageView user;
    RecyclerView recent_activities,available_cards;
    GridView gridView;
    ImageButton back;
    LinearLayout Send_money, Receive_money, Add_cards;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        Assignment();
        if(((Helper)this.getApplicationContext()).Available_Amount!="")
            balance.setText(((Helper)this.getApplicationContext()).Available_Amount);
        else{
            balance.setText("₹100.00");
        }
        Glide.with(this).load(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhotoUrl()).into(user);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    public void Assignment(){
        balance = findViewById(R.id.balance);
        user = findViewById(R.id.user);
        recent_activities = findViewById(R.id.recent_transection);
        available_cards = findViewById(R.id.available_cards);
        gridView = findViewById(R.id.gridview);
        Send_money = findViewById(R.id.sendmoney);
        Receive_money = findViewById(R.id.receivemoney);
        Add_cards  = findViewById(R.id.addcards);
        back = findViewById(R.id.back_button);
    }
}