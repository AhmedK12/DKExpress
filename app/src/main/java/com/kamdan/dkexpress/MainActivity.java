package com.kamdan.dkexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.rpc.Help;
import com.kamdan.dkexpress.cakes.Cakes_main;
import com.kamdan.dkexpress.grocery.Registration;
import com.kamdan.dkexpress.grocery.groceryproduct;
import com.kamdan.dkexpress.grocery.model.MyProfile;
import com.kamdan.dkexpress.grocery.model.Product;
import com.kamdan.dkexpress.products.Helper;
import com.kamdan.dkexpress.products.ProductActivity;
import com.kamdan.dkexpress.registraition.LoginCred;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
     private Button retailer, grocery;
     public Context context;
     private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        context = this;
//        startActivity(new Intent(context, Cakes_main.class));
//        finish();
            FirebaseDatabase.getInstance().getReference().child("Grocery").child("Keywords").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ((Helper)context.getApplicationContext()).Keywords = snapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            FirebaseDatabase.getInstance().getReference().child("Grocery").child("Products").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ((Helper)context.getApplicationContext()).Products.clear();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Product p = ds.getValue(Product.class);
                        if(!p.getAvailability().equals("notavailable")) {
                            p.setId(ds.getKey());
                            ((Helper) context.getApplicationContext()).Products.add(p);

                        }
                    }
                    startActivity(new Intent(context,groceryproduct.class));
                    finish();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            if(!(FirebaseAuth.getInstance().getCurrentUser() ==null))
             FirebaseDatabase.getInstance().getReference().child("Grocery").child("User").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ((Helper)context.getApplicationContext()).myProfile = snapshot.getValue(MyProfile.class);
                    ((Helper)context.getApplicationContext()).Available_Amount = snapshot.child("Wallet").child("Available_Balance").getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        FirebaseDatabase.getInstance().getReference().child("deliver_charge").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ((Helper)context.getApplicationContext()).DELIVERY_CHARGE_NEAR = Float.parseFloat(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("free_delivery_limit_I").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ((Helper)context.getApplicationContext()).FREE_DELIVERY_MINIMUM_AMOUNT_I = Float.parseFloat(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("free_delivery_limit_II").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ((Helper)context.getApplicationContext()).FREE_DELIVERY_MINIMUM_AMOUNT_II = Float.parseFloat(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}