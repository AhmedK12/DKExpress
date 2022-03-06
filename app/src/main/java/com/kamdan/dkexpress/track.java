package com.kamdan.dkexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.kamdan.dkexpress.model.Order;

import java.util.Objects;

public class track extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        Intent intent = getIntent();
        Order order = (Order) Objects.requireNonNull(intent.getSerializableExtra("Order"));
    }
}