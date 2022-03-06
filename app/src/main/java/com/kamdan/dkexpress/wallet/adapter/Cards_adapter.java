package com.kamdan.dkexpress.wallet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kamdan.dkexpress.R;

import java.util.ArrayList;

public class Cards_adapter extends RecyclerView.Adapter<Cards_adapter.CardViewHolder> {
    public Cards_adapter(ArrayList<String> info){

    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.description_row,parent,false);
        return new Cards_adapter.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        TextView card_no, bank_name;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
