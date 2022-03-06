package com.kamdan.dkexpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Get_Details extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> text,value;
    ImageView imageView,line1,line2;
    ImageButton back;
    TextView text1,text2,text3;
    CircleImageView Outfordelivery,delivered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__details);
        Intent intent = getIntent();
        ArrayList<String> order = (ArrayList<String>) Objects.requireNonNull(intent.getSerializableExtra("Order"));
        recyclerView = findViewById(R.id.orderdetailsrecyclerview);
        imageView = findViewById(R.id.deliverytruck);
        imageView.setVisibility(View.GONE);
        Outfordelivery = findViewById(R.id.outfordelivery);
        Outfordelivery.setVisibility(View.GONE);
        delivered = findViewById(R.id.delivery);
        delivered.setVisibility(View.GONE);
        line1 = findViewById(R.id.initiate_outfordelivery);
        line2 = findViewById(R.id.outfordelivery_delivered);
        text1 = findViewById(R.id.outfordelivery_text);
        text2 = findViewById(R.id.delivered_text);
        text3 = findViewById(R.id.initiated1);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        text = new ArrayList<>();
        value = new ArrayList<>();
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.moveright);
        imageView.startAnimation(animation);

        for (String s: order){
            String[] st = s.split(",");
            if(st.length>2){
                value.add(s);
                continue;
            }
            if(st.length>=2&&st[1].equals("OutforDelivery")){
                Outfordelivery.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                line1.setVisibility(View.VISIBLE);
                text1.setVisibility(View.VISIBLE);

            }
            if(st.length>=2&&st[1].equals("Delivered")){
                Outfordelivery.setVisibility(View.VISIBLE);
                delivered.setVisibility(View.VISIBLE);
                line2.setVisibility(View.VISIBLE);
                line1.setVisibility(View.VISIBLE);
                text1.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
            }
            if(st.length>=2&&st[1].equals("Cancelled")){
                text3.setText("Cancelled");
//                Outfordelivery.setVisibility(View.VISIBLE);
//                imageView.setVisibility(View.VISIBLE);
//                line1.setVisibility(View.VISIBLE);
//                text1.setVisibility(View.VISIBLE);

            }
            if(st.length==2){
                text.add(st[0]);
                value.add(st[1]);
            }

        }
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new myorderadapter(this,text,value));


    }
}