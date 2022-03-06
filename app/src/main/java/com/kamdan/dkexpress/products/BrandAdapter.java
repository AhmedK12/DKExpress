package com.kamdan.dkexpress.products;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.model.Product;
import com.kamdan.dkexpress.model.Brands;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyAdapterViewHolder> {
    private Context context;
    private ArrayList<String> Data;
    ItemclickListner itemclicklistner;
    public BrandAdapter(Context context, ArrayList<String> data, ItemclickListner itemclicklistner){
        this.context = context;
        this.Data = data;
        this.itemclicklistner = itemclicklistner;

    }
    @NonNull


    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.brands_row,parent,false);
        return new BrandAdapter.MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        holder.BrandName.setText(Data.get(position));
        holder.BrandName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemclicklistner.onItemclick(Data.get(position));
            }
        });

    }

    public interface ItemclickListner {
        void onItemclick(String text);
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }


    public class MyAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView BrandName;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            BrandName = itemView.findViewById(R.id.brandname);

        }



    }



}
