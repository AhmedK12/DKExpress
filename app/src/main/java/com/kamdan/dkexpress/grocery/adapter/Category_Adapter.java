package com.kamdan.dkexpress.grocery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kamdan.dkexpress.R;

import java.util.ArrayList;

public class Category_Adapter extends BaseAdapter {
    ArrayList<String> images;
    Context context;
    int a;
    ArrayList<String> texts;
    public Category_Adapter(Context context, ArrayList<String> images, ArrayList<String> texts,int a){
        this.context = context;
        this.images = images;
        this.texts = texts;
        this.a = a;
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            if(a==1)
               convertView = inflater.inflate(R.layout.category,null,false);
            else
                convertView = inflater.inflate(R.layout.specialview,null,false);
        }
        TextView textView;
        ImageView imageView;
        textView = convertView.findViewById(R.id.category_text);
        imageView = convertView.findViewById(R.id.category_image);
        textView.setText(texts.get(position));
        Glide.with(context).load(images.get(position)).into(imageView);
        return convertView;
    }
}
