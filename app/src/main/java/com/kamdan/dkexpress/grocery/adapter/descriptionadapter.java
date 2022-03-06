package com.kamdan.dkexpress.grocery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kamdan.dkexpress.R;

import java.util.ArrayList;


public class descriptionadapter extends RecyclerView.Adapter<descriptionadapter.MyAdapterViewHolder> {
    ArrayList<String> data, value;

   public descriptionadapter(ArrayList<String> data, ArrayList<String> value){
        this.data=data;
        this.value = value;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.description_row,parent,false);
        return new descriptionadapter.MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
           holder.name.setText(data.get(position).trim());
           holder.val.setText(value.get(position).trim().replace(",","\n"));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView name, val;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.desc_name);
            val = itemView.findViewById(R.id.desc_value);
        }
    }
}
