package com.kamdan.dkexpress.grocery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.ParentItemAdapter;
import com.kamdan.dkexpress.grocery.model.Offer;
import com.kamdan.dkexpress.grocery.model.Product;

import java.util.ArrayList;
import java.util.Objects;

public class offeradapter extends PagerAdapter {
    public ArrayList<Offer> Offers;
    public Context context;
    LayoutInflater mLayoutInflater;
    private ItemclickListner itemclickListner;


    // Viewpager Constructor
    public offeradapter(Context context, ArrayList<Offer> offers, ItemclickListner itemclickListner) {
        this.context = context;
        this.Offers = offers;
        this.itemclickListner = itemclickListner;

    }

    @Override
    public int getCount() {
        return Offers.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.offerproduct, container, false);

        // referencing the image view from the item.xml file
        ImageView imageView = (ImageView) itemView.findViewById(R.id.offerimage);

        // setting the image in the imageView
        Glide.with(context).load(Offers.get(position).getImage()).into(imageView);

        // Adding the View
        Objects.requireNonNull(container).addView(itemView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemclickListner.onItemclick(Offers.get(position));
            }
        });

        return itemView;
    }
    public interface ItemclickListner {
        void onItemclick(Offer offer);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout) object);
    }
}
