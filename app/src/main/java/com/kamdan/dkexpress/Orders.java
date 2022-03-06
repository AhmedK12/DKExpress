package com.kamdan.dkexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kamdan.dkexpress.grocery.model.Order;
import com.kamdan.dkexpress.products.Helper;
import com.kamdan.dkexpress.products.ProductActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Orders extends AppCompatActivity{
    private RecyclerView recyclerView;
    int size;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<com.kamdan.dkexpress.grocery.model.Order> Orders = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        recyclerView = findViewById(R.id.orders_recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        OrdersAdapter ordersAdapter = new OrdersAdapter(this,Orders);
        DatabaseReference OrderRef = FirebaseDatabase.getInstance().getReference().child("Grocery/User/"+Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()+"/Order");
        OrderRef.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Orders.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Order order = snapshot1.getValue(com.kamdan.dkexpress.grocery.model.Order.class);
                    Orders.add(order);
                }

                Collections.reverse(Orders);
                ordersAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setAdapter(ordersAdapter);
        Orders.clear();
        back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, com.kamdan.dkexpress.grocery.groceryproduct.class));
        this.finish();
    }

}