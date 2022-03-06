package com.kamdan.dkexpress.grocery.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.Childadapter;
import com.kamdan.dkexpress.grocery.model.Product;
import com.kamdan.dkexpress.model.Products;
import com.kamdan.dkexpress.products.Helper;

import java.util.ArrayList;

public class Gridadapter extends BaseAdapter {
    Context c;
    ArrayList<Integer> items;


    public Gridadapter(Context c, ArrayList<Integer> arr)
    {
        this.c = c;
        this.items = arr;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return  items.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.cake_items,null,false);


        final Product p = ((Helper)c.getApplicationContext()).Products.get(items.get(position));
        TextView ChildItemTitle,New_Price, Old_Price,save,text_off,ssavve;

        LinearLayout cardView;
        ImageView Product_Image;
        RatingBar ratingBar;
        ratingBar = convertView.findViewById(R.id.ratingbar);
        {

            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            ratingBar.setRating((float) 3.25+(float)(int)(Math.random()*2));
            stars.getDrawable(2).setColorFilter(Color.parseColor("#FF9529"), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(Color.parseColor("#cfcac6"), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(Color.parseColor("#cfcac6"), PorterDuff.Mode.SRC_ATOP);
        }
        ChildItemTitle = convertView.findViewById(R.id.product_name);
        New_Price = convertView.findViewById(R.id.new_product_price);
        Old_Price = convertView.findViewById(R.id.product_price);
        Product_Image = convertView.findViewById(R.id.product_image);
        cardView = convertView.findViewById(R.id.offerlogo);
        save = convertView.findViewById(R.id.offer);
        text_off = convertView.findViewById(R.id.text_off);
        ssavve = convertView.findViewById(R.id.ssavve);
        ChildItemTitle.setText(p.getTitle());
        Old_Price.setVisibility(View.GONE);
        ssavve.setText("");
        New_Price.setText("");
        Old_Price.setText("");
        text_off.setText("");
        ssavve.setText(p.getSize().split(",")[0]);
        New_Price.setText(p.getNewprice().split(",")[0]);
        int kl =0;float s;
                try {
                    s = Float.parseFloat(p.getPrice().split(",")[0].replace("₹", "")) - Float.parseFloat(((Helper)c.getApplicationContext()).Products.get(items.get(position)).getNewprice().split(",")[0].replace("₹", ""));
                } catch (Exception e) {
                    s = 0;
                }
        text_off.setText("₹"+Float.toString(s)+"0");
        if(s<0.09){
            Old_Price.setText(p.getPrice().split(",")[0]);
            Old_Price.setVisibility(View.VISIBLE);
            New_Price.setVisibility(View.GONE);
            text_off.setVisibility(View.GONE);
            save.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
        }


//        Glide.with(c).load(p.getImage().split(",")[0]).into(Product_Image);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p.getAvailability().contains("true")&& p.getCake().equals("-1"))
                    c.startActivity(new Intent(c, com.kamdan.dkexpress.grocery.Product.class).putExtra("product",p));
                if (p.getAvailability().contains("true")&& p.getCake().equals("1"))
                    c.startActivity(new Intent(c, com.kamdan.dkexpress.cakes.Product.class).putExtra("product",p));
                else
                    Toast.makeText(c,"This Product is Not Available, Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public String get1(String price, String newprice){
        String result = "";
        float s;
        try {
            s = Float.parseFloat(price.replace("₹", "")) - Float.parseFloat(newprice.replace("₹", ""));
        } catch (Exception e) {
            s = 0;
        }
        float a;
        try {
            a = s / Float.parseFloat(price.replace("₹", ""));
        } catch (Exception e) {
            a = 0;

        }
        result = Float.toString(s);
        return result;
    }
}
