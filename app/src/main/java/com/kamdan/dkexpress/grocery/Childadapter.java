package com.kamdan.dkexpress.grocery;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.kamdan.dkexpress.model.Products;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.model.category;
import com.kamdan.dkexpress.products.BrandsAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class Childadapter extends RecyclerView.Adapter<Childadapter.ChildViewHolder> {

    private ArrayList<com.kamdan.dkexpress.grocery.model.Product> ChildItemList;
    private Context context;
    public int act;
    // Constructor
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Childadapter(ArrayList<com.kamdan.dkexpress.grocery.model.Product> childItemList, Context context, int act)
    {
        this.ChildItemList = childItemList;
        this.context = context;
        this.act = act;
        Collections.sort(childItemList, new Comparator<com.kamdan.dkexpress.grocery.model.Product>() {
            @Override
            public int compare(com.kamdan.dkexpress.grocery.model.Product o1, com.kamdan.dkexpress.grocery.model.Product o2) {
                return Integer.parseInt(o2.getPopularity())-Integer.parseInt(o1.getPopularity());
            }
        });

    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup,
            int i)
    {

        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(
                        R.layout.grocerypoduct,
                        viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position) {

        // Create an instance of the ChildItem
        // class for the given position
        com.kamdan.dkexpress.grocery.model.Product childItem = ChildItemList.get(position);

        // For the created instance, set title.
        // No need to set the image for
        // the ImageViews because we have
        // provided the source for the images
        // in the layout file itself
        RatingBar ratingBar;
        ratingBar = childViewHolder.itemView.findViewById(R.id.ratingbar);
        {

            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            ratingBar.setRating((float) 2.5+(float)(int)(Math.random()*2));
            stars.getDrawable(2).setColorFilter(Color.parseColor("#FF9529"), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(Color.parseColor("#cfcac6"), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(Color.parseColor("#cfcac6"), PorterDuff.Mode.SRC_ATOP);
        }
        childViewHolder.ChildItemTitle.setText(childItem.getTitle());
        childViewHolder.Old_Price.setText(childItem.getPrice().split(",")[0]);
        childViewHolder.ssavve.setText(childItem.getSize().split(",")[0]);
        ArrayList<Float> difference = new ArrayList<>(0), percentage = new ArrayList<>(0);
        int ikg = -1;
        for(int i=0;i<childItem.getNewprice().split(",").length;i++){
            float s;
            try {
                s = Float.parseFloat(childItem.getPrice().split(",")[i].replace("₹", "")) - Float.parseFloat(childItem.getNewprice().split(",")[i].replace("₹", ""));
            } catch (Exception e) {
                s = 0;
            }
            float a;
            try {
                a = s / Float.parseFloat(childItem.getPrice().split(",")[i].replace("₹", ""));
            } catch (Exception e) {
                a = 0;

            }
            difference.add(s);
            percentage.add(a);
            if(!childItem.getSize().equals("")) {
                if (!childItem.getSize().equals("") && childItem.getSize().split(",")[i].contains("1 Kg")
                        || childItem.getSize().split(",")[i].contains("1Kg")
                        || childItem.getSize().split(",")[i].contains("1 KG")
                        || childItem.getSize().split(",")[i].contains("1 kg")
                        || childItem.getSize().split(",")[i].contains("1 Ltr")
                        || childItem.getSize().split(",")[i].contains("1 ltr")
                        || childItem.getSize().split(",")[i].contains("1KG")
                        || childItem.getSize().split(",")[i].contains("1Ltr")
                        || childItem.getSize().split(",")[i].contains("1ltr")
                        || childItem.getSize().split(",")[i].contains("1 LTR")
                        || childItem.getSize().split(",")[i].contains("1 LTR")) {
                    ikg = i;
                }
            }
        }
        if(ikg!=-1){
            if(difference.get(ikg)>0.00){
                childViewHolder.Old_Price.setText(childItem.getPrice().split(",")[ikg]);
                childViewHolder.Old_Price.setBackground(ContextCompat.getDrawable(context, R.drawable.line));
                childViewHolder.New_Price.setText(childItem.getNewprice().split(",")[ikg]);
                childViewHolder.New_Price.setVisibility(View.VISIBLE);
                childViewHolder.Old_Price.setVisibility(View.GONE);
                childViewHolder.ssavve.setText(childItem.getSize().split(",")[ikg]);
                childViewHolder.text_off.setText("₹"+Float.toString(difference.get(ikg))+"0");
                childViewHolder.text_off.setVisibility(View.VISIBLE);
            }
            else{

                childViewHolder.Old_Price.setBackgroundResource(0);
                childViewHolder.New_Price.setVisibility(View.GONE);
                childViewHolder.text_off.setVisibility(View.GONE);
                childViewHolder.save.setVisibility(View.GONE);
                childViewHolder.cardView.setVisibility(View.GONE);

            }

        }
        else{
            int kl = (int)(Math.random()*(childItem.getNewprice().split(",").length));
            if(difference.get(kl)>0.00){
                childViewHolder.ssavve.setText(childItem.getSize().split(",")[kl]);
                childViewHolder.Old_Price.setText(childItem.getPrice().split(",")[kl]);
                childViewHolder.Old_Price.setBackground(ContextCompat.getDrawable(context, R.drawable.line));
                childViewHolder.New_Price.setText(childItem.getNewprice().split(",")[kl]);
                childViewHolder.New_Price.setVisibility(View.VISIBLE);
                childViewHolder.Old_Price.setVisibility(View.GONE);
                childViewHolder.text_off.setText("₹"+Float.toString(difference.get(kl))+"0");
                childViewHolder.text_off.setVisibility(View.VISIBLE);
                System.out.println(childItem.getTitle());
            }
            else{
                childViewHolder.Old_Price.setBackgroundResource(0);
                childViewHolder.New_Price.setVisibility(View.GONE);
                childViewHolder.text_off.setVisibility(View.GONE);
                childViewHolder.save.setVisibility(View.GONE);
                childViewHolder.cardView.setVisibility(View.GONE);


            }




        }

        Glide.with(context).load(childItem.getImage().split(",")[0]).into(childViewHolder.Product_Image);
        childViewHolder.itemView.setOnClickListener(v -> {
            if (childItem.getAvailability().contains("true")) {
                context.startActivity(new Intent(context, Product.class).putExtra("product", childItem));
                if(this.act==1)
                    ((com.kamdan.dkexpress.grocery.Product)context).finish();
            }
            else
                Toast.makeText(context,"This Product is Not Available, Coming Soon",Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount()
    {

        // This method returns the number
        // of items we have added
        // in the ChildItemList
        // i.e. the number of instances
        // of the ChildItemList
        // that have been created
//        Toast.makeText(context, Integer.toString(ChildItemList.size()), Toast.LENGTH_SHORT).show();
        return ChildItemList.size();

    }




    // This class is to initialize
    // the Views present
    // in the child RecyclerView
    public  class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView ChildItemTitle,ssavve,New_Price, Old_Price,save,text_off;
        ImageView Product_Image;
        LinearLayout cardView;

        ChildViewHolder(View itemView)
        {
            super(itemView);
            ChildItemTitle = itemView.findViewById(R.id.product_name);
            New_Price = itemView.findViewById(R.id.new_product_price);
            Old_Price = itemView.findViewById(R.id.product_price);
            Product_Image = itemView.findViewById(R.id.product_image);
            cardView = itemView.findViewById(R.id.offerlogo);
            save = itemView.findViewById(R.id.offer);
            text_off = itemView.findViewById(R.id.text_off);
            ssavve = itemView.findViewById(R.id.ssavve);
        }
    }


}
