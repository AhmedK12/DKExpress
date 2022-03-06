package com.kamdan.dkexpress.wallet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.adapter.descriptionadapter;

import java.util.ArrayList;

public class transaction_adapter extends RecyclerView.Adapter<transaction_adapter.MyAdapterViewHolder> {
    ArrayList<String> Primary_text, Secondary_text;
    Context context;
    public transaction_adapter(ArrayList<String> primary_text, ArrayList<String> secondary_text, Context context){
        this.Primary_text = primary_text;
        this.Secondary_text = secondary_text;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.description_row,parent,false);
        return new transaction_adapter.MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        holder.primary_text.setText(Primary_text.get(position));
        holder.secondary_text.setText(Secondary_text.get(position));
        if (Primary_text.get(position).contains("Send")){
            holder.primary_text.setTextColor(Color.parseColor("#64DD17"));
        }
        else if(Primary_text.get(position).contains("Received")){
            holder.primary_text.setTextColor(Color.RED);
        }
        else {
            holder.primary_text.setTextColor(Color.parseColor("#FF6D00"));
        }

    }

    @Override
    public int getItemCount() {
        return Primary_text.size();
    }

    class MyAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView primary_text,secondary_text;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            primary_text = itemView.findViewById(R.id.primary_text);
            secondary_text = itemView.findViewById(R.id.secondary_text);
        }
    }
}
