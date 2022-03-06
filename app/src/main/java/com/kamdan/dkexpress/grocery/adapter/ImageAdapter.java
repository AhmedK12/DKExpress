package com.kamdan.dkexpress.grocery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.kamdan.dkexpress.R;

import java.util.ArrayList;
import java.util.Objects;

import uk.co.senab.photoview.PhotoView;

public class ImageAdapter extends PagerAdapter {
    private ArrayList<String> Images;
    private Context context;
    LayoutInflater mLayoutInflater;
    public ImageAdapter(ArrayList<String> Images, Context context){
        this.Images = Images;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Images.size();
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
        View itemView = mLayoutInflater.inflate(R.layout.imageview, container, false);

        // referencing the image view from the item.xml file
        PhotoView imageView =  itemView.findViewById(R.id.product_image);

        // setting the image in the imageView
        Glide.with(context).load(Images.get(position)).into(imageView);

        // Adding the View
        Objects.requireNonNull(container).addView(itemView);


        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
}
