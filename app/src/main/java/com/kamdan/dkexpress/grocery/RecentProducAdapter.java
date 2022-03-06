package com.kamdan.dkexpress.grocery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.model.Products;
import com.kamdan.dkexpress.model.category;
import com.kamdan.dkexpress.products.BrandAdapter;
import com.kamdan.dkexpress.products.BrandsAdapter;
import com.kamdan.dkexpress.products.ProductActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecentProducAdapter extends RecyclerView.Adapter<RecentProducAdapter.MyAdapterViewHolder> {
    Context context;
    public RecentProducAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.brands_row,parent,false);
        return new RecentProducAdapter.MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView BrandName;
        CircleImageView Imageview;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            BrandName = itemView.findViewById(R.id.brandname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, ProductActivity.class).putExtra("brand",BrandName.getText().toString()));
                    ((ProductActivity)context).finish();
                }
            });
        }



    }
}
