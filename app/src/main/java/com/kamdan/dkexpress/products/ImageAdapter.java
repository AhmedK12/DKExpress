package com.kamdan.dkexpress.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kamdan.dkexpress.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyAdapterViewHolder> {
    private String[] images;
    private Context context;
    ImageAdapter(Context context, String[] images){
        this.context = context;
        this.images = images;
    }
    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.productimage,parent,false);
        return new ImageAdapter.MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        Glide.with(context).load(images[position]).into(holder.imageview);
    }



    @Override
    public int getItemCount() {
        return images.length;
    }
    public class MyAdapterViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageview;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.details_image);
        }
    }
}
