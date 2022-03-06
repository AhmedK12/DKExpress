package com.kamdan.dkexpress.grocery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kamdan.dkexpress.Cart;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.cartAdapter;
import com.kamdan.dkexpress.grocery.model.CartItem;
import com.kamdan.dkexpress.products.Helper;
import com.kamdan.dkexpress.products.ProductActivity;

import java.util.ArrayList;

public class Cart_adapter extends RecyclerView.Adapter<Cart_adapter.CartViewHolder> {
    private ArrayList<CartItem> Items;
    private Context context;
    public Cart_adapter(ArrayList<CartItem> items, Context context){
        this.Items = items;
        this.context = context;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_cart_item,parent,false);
        return new Cart_adapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.title.setText(Items.get(position).getProductname());
        holder.Subtotal.setText(Items.get(position).getSubtotal());
        holder.quantity.setText(Items.get(position).getQuantity());
        holder.size.setText(Items.get(position).getSize());
        Glide.with(context).load(Items.get(position).getImage()).into(holder.imageView);
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int n = 1;


                try{
                    n = Integer.parseInt(holder.quantity.getText().toString());
                } catch (Exception e){

                }
                holder.quantity.setText(Integer.toString(n+1));
                ((Helper) context.getApplicationContext()).Items.get(position).setQuantity(holder.quantity.getText().toString());


                com.kamdan.dkexpress.grocery.Cart.subtotl.setText("₹"+((Helper) context.getApplicationContext()).Totalprice());
                holder.Subtotal.setText("₹"+Float.toString(Float.parseFloat(holder.quantity.getText().toString().trim())*Float.parseFloat(Items.get(position).getPrice().replace("₹",""))));
                set_delivery_charge();
                set_gift();

            }
        });
        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = 1;

                try{
                    n = Integer.parseInt(holder.quantity.getText().toString());
                } catch (Exception e){

                }
                if(n>1) {
                    holder.quantity.setText(Integer.toString(n - 1));
                    ((Helper) context.getApplicationContext()).Items.get(position).setQuantity(holder.quantity.getText().toString());
                }

                holder.Subtotal.setText("₹"+Float.toString(Float.parseFloat(holder.quantity.getText().toString().trim())*Float.parseFloat(Items.get(position).getPrice().replace("₹",""))));
                com.kamdan.dkexpress.grocery.Cart.subtotl.setText("₹"+((Helper) context.getApplicationContext()).Totalprice());
                set_delivery_charge();
                set_gift();
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int actualPosition = holder.getAdapterPosition();
                Items.remove(actualPosition);
                notifyItemRemoved(actualPosition);
                notifyItemRangeChanged(actualPosition, Items.size());
                if(Items.size()==0){
                    context.startActivity(new Intent(context, groceryproduct.class));
                    ((com.kamdan.dkexpress.grocery.Cart)context).finish();
                }
                com.kamdan.dkexpress.grocery.Cart.subtotl.setText("₹"+((Helper) context.getApplicationContext()).Totalprice());
                set_delivery_charge();
                set_gift();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }


    public void set_gift(){

    }
    public void set_delivery_charge(){
        if(Float.parseFloat(((Helper) context.getApplicationContext()).Totalprice())<free_delivery_limit()){
            com.kamdan.dkexpress.grocery.Cart.delivery_charge.setText("₹"+Float.toString(((Helper)context.getApplicationContext()).DELIVERY_CHARGE_NEAR));
            com.kamdan.dkexpress.grocery.Cart.delivery_charge.setTextColor(Color.RED);

            com.kamdan.dkexpress.grocery.Cart.total.setText("₹"+Float.toString(Float.parseFloat(((Helper)context.getApplicationContext()).Totalprice().replace("₹",""))+((Helper)context.getApplicationContext()).DELIVERY_CHARGE_NEAR));
        }
        else{
            com.kamdan.dkexpress.grocery.Cart.delivery_charge.setText("FREE");
            com.kamdan.dkexpress.grocery.Cart.delivery_charge.setTextColor(Color.parseColor("#17A91D"));
            com.kamdan.dkexpress.grocery.Cart.total.setText("₹"+Float.toString(Float.parseFloat(((Helper)context.getApplicationContext()).Totalprice().replace("₹",""))));
        }
    }


    public float free_delivery_limit(){
        float delivery_limit = ((Helper)context.getApplicationContext()).FREE_DELIVERY_MINIMUM_AMOUNT_I;
        for(CartItem item: ((Helper)context.getApplicationContext()).Items){
            if(item.getDeliver_charge()!=1){
                delivery_limit = ((Helper)context.getApplicationContext()).FREE_DELIVERY_MINIMUM_AMOUNT_II;
                System.out.println(item.getDeliver_charge());
            }
        }
//        System.out.println(item.getDeliver_charge());
        return delivery_limit;
    }


    public class CartViewHolder extends RecyclerView.ViewHolder{
        TextView title,size,Subtotal;
        ImageView imageView;
        ImageButton increment, decrement, remove;
        EditText quantity;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.product_name);
            imageView = itemView.findViewById(R.id.cart_product_image);
            increment = itemView.findViewById(R.id.cart_product_incrementer);
            decrement = itemView.findViewById(R.id.cart_product_decrementer);
            quantity = itemView.findViewById(R.id.cart_product_quantity);
            remove = itemView.findViewById(R.id.item_remove);
            size = itemView.findViewById(R.id.product_size);
            Subtotal = itemView.findViewById(R.id.cart_subtotal);
        }
    }
}
