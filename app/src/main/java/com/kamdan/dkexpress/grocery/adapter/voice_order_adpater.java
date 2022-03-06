package com.kamdan.dkexpress.grocery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.ParentItemAdapter;
import com.kamdan.dkexpress.grocery.model.Product;

import java.util.ArrayList;

public class voice_order_adpater extends RecyclerView.Adapter<voice_order_adpater.MyAdapterViewHolder> {
    ArrayList<String> data;
    private ItemclickListner itemclickListner;

    public voice_order_adpater(ArrayList<String> data, ItemclickListner itemclickListner) {
        this.data = data;
        this.itemclickListner = itemclickListner;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.voice_order_item,parent,false);
        return new voice_order_adpater.MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
     holder.item.setText(data.get(position));
     holder.delete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             itemclickListner.onItemclick(position);
         }
     });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemclickListner {
        void onItemclick(int position);
    }

    class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView item;
        ImageButton delete;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.txvResult);
            delete = itemView.findViewById(R.id.item_remove);
        }
    }
}
