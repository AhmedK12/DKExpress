package com.kamdan.dkexpress.products;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.model.category;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.MyAdapterViewHolder> {
    private Context context;
    private ArrayList<category> Data;
    private ItemclickListner itemclickListner;
    public BrandsAdapter(Context context, ArrayList<category> data,ItemclickListner itemclickListner){
        this.context = context;
        this.Data = data;
        this.itemclickListner = itemclickListner;
    }
    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.brand_name_row,parent,false);
        return new MyAdapterViewHolder(view);
    }

    @Override
     public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
     holder.textView.setText(Data.get(position).getCategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               LinearLayout linearLayout = holder.linearLayout;
                itemclickListner.onItemclick(Data.get(position),linearLayout);
            }
        });
        Glide.with(context).load(Data.get(position).getImage()).into((ImageView) holder.brand_image);

    }



    @Override
    public int getItemCount() {
        return Data.size();
    }

    public interface ItemclickListner {
        void onItemclick(category Category,LinearLayout linearLayout);
    }

    public  class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        ImageView brand_image;
        LinearLayout linearLayout;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView =itemView.findViewById(R.id.brand_name);
            brand_image = itemView.findViewById(R.id.category_image);
            linearLayout = itemView.findViewById(R.id.cat_row);

        }

    }


}
