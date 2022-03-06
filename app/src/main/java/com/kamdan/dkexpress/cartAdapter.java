package com.kamdan.dkexpress;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kamdan.dkexpress.model.Product_Price;
import com.kamdan.dkexpress.products.Helper;
import com.kamdan.dkexpress.products.ProductActivity;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.MyAdapterViewHolder> {
    Context context;
    Helper Data;
    public cartAdapter(Context context, Helper Data){
        this.context = context;
        this.Data = Data;

    }

    @NonNull
    @Override
    public cartAdapter.MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cart_row,parent,false);
        return new cartAdapter.MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cartAdapter.MyAdapterViewHolder holder, int position) {
          holder.title.setText(Data.Prods.get(position).Product_Name);

          holder.quantity.setText(Data.Prods.get(position).Quantity+Data.Prods.get(position).type1);
          Glide.with(context).load(Data.Prods.get(position).Product_Image).into(holder.imageView);
          holder.increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int n = 1;


                        try{
                            n = Integer.parseInt(holder.quantity.getText().toString().replace(Data.Prods.get(position).type1,"").trim());
                        } catch (Exception e){

                        }
                        holder.quantity.setText(Integer.toString(n+1)+Data.Prods.get(position).type1);
                    ((Helper) context.getApplicationContext()).Prods.get(position).Quantity = holder.quantity.getText().toString().replace(Data.Prods.get(position).type1, "").trim();



                    Cart.Total_Price.setText("₹"+((Helper) context.getApplicationContext()).Total_Price());
                }
            });
          holder.decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n = 1;

                        try{
                            n = Integer.parseInt(holder.quantity.getText().toString().replace(Data.Prods.get(position).type1,"").trim());
                        } catch (Exception e){

                        }
                        if(n>1) {
                            holder.quantity.setText(Integer.toString(n - 1) + Data.Prods.get(position).type1);
                            ((Helper) context.getApplicationContext()).Prods.get(position).Quantity = holder.quantity.getText().toString().replace(Data.Prods.get(position).type1, "").trim();
                        }

                    Cart.Total_Price.setText("₹"+((Helper) context.getApplicationContext()).Total_Price());
                }
            });
          holder.remove.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  int actualPosition = holder.getAdapterPosition();
                  Data.Prods.remove(actualPosition);
                  notifyItemRemoved(actualPosition);
                  notifyItemRangeChanged(actualPosition, Data.sze());
                  if(Data.sze()==0){
                      context.startActivity(new Intent(context, ProductActivity.class));
                      ((Cart)context).finish();
                  }
                  Cart.Total_Price.setText("₹"+((Helper) context.getApplicationContext()).Total_Price());
              }
          });
    }

    @Override
    public int getItemCount() {
        return Data.sze();
    }

    public class MyAdapterViewHolder extends  RecyclerView.ViewHolder{
        TextView title;
        ImageView imageView;
        ImageButton increment, decrement, remove;
        EditText quantity;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cart_product_title);
            imageView = itemView.findViewById(R.id.cart_product_image);
            increment = itemView.findViewById(R.id.cart_product_incrementer);
            decrement = itemView.findViewById(R.id.cart_product_decrementer);
            quantity = itemView.findViewById(R.id.cart_product_quantity);
            remove = itemView.findViewById(R.id.item_remove);

        }


    }
}
