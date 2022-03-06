package com.kamdan.dkexpress;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kamdan.dkexpress.products.Helper;

import java.util.ArrayList;

public class myorderadapter extends RecyclerView.Adapter<myorderadapter.MyAdapterViewHolder>{
    private ArrayList<String> text,value,image = new ArrayList<>(0);
    Context context;
    int n = 0;
    public myorderadapter(Context context,ArrayList<String> text, ArrayList<String> value){
        this.context = context;
        this.text = text;
        this.value = value;
        n = value.size()-text.size();
        System.out.println(value.size()+"        "+text.size());

    }
    @NonNull
    @Override
    public myorderadapter.MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.details_row,parent,false);
        return new myorderadapter.MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myorderadapter.MyAdapterViewHolder holder, int position) {

        if(value.get(position).split(",").length>2)
         {
            Glide.with(context).load(((Helper)context.getApplicationContext()).get_image(value.get(position).split(",")[0])).into(holder.imageView);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.text.setText(value.get(position).split(",")[0]);
            holder.value.setText(value.get(position).split(",")[1]+"     "+value.get(position).split(",")[2]+"pc"+"     "+value.get(position).split(",")[4]);
            return;
        }
        else {
            holder.imageView.setVisibility(View.GONE);
            holder.text.setText(text.get(position - n));
            if (value.get(position).equals("Cancelled")) {
                holder.value.setText(value.get(position));
                holder.value.setTextColor(Color.BLACK);
                holder.value.setBackgroundColor(Color.RED);

            }
            if (value.get(position).equals("Delivered")) {
                holder.value.setText(value.get(position));
                holder.value.setTextColor(Color.BLACK);
                holder.value.setBackgroundColor(Color.GREEN);

            } else
                holder.value.setText(value.get(position));
        }
    }

    @Override
    public int getItemCount() {

        return value.size();
    }
    public class MyAdapterViewHolder extends  RecyclerView.ViewHolder{
            TextView text,value;
            ImageView imageView;
            public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            value = itemView.findViewById(R.id.value);
            imageView = itemView.findViewById(R.id.order_product_image);

        }


    }
}
