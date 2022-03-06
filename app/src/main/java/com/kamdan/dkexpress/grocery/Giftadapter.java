package com.kamdan.dkexpress.grocery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.model.Product;

import java.util.ArrayList;

public class Giftadapter extends RecyclerView.Adapter<Giftadapter.GiftViewHolder> {
    Context context;
    ArrayList<Product> GiftItems;
    private ItemclickListner itemclickListner;
    public Giftadapter(ArrayList<Product> Gits, Context context, ItemclickListner itemclickListner){
        this.GiftItems = Gits;
        this.context = context;
        this.itemclickListner = itemclickListner;
    }
    @NonNull
    @Override
    public GiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.gift_item,
                        parent, false);

        return new Giftadapter.GiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftViewHolder holder, int position) {
        holder.addgift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemclickListner.onItemclick(GiftItems.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return GiftItems.size();
    }
    public interface ItemclickListner {
        void onItemclick(Product product);
    }


    public class GiftViewHolder extends RecyclerView.ViewHolder {
        Button addgift;
        public GiftViewHolder(@NonNull View itemView) {
            super(itemView);
            addgift = itemView.findViewById(R.id.add_gift);
        }
    }
}
