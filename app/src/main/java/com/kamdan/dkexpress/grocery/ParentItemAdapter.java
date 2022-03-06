package com.kamdan.dkexpress.grocery;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.model.Category;
import com.kamdan.dkexpress.grocery.model.Product;
import com.kamdan.dkexpress.model.category;
import com.kamdan.dkexpress.products.BrandsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ParentItemAdapter extends RecyclerView.Adapter<ParentItemAdapter.ParentViewHolder> {

    // An object of RecyclerView.RecycledViewPool
    // is created to share the Views
    // between the child and
    // the parent RecyclerViews
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ItemclickListner itemclickListner;
    private ArrayList<com.kamdan.dkexpress.grocery.model.Category> itemList;
    private Context context;

    ParentItemAdapter(ArrayList<com.kamdan.dkexpress.grocery.model.Category> itemList, Context context, ItemclickListner itemclickListner)
    {
        this.itemList = itemList;
        this.context = context;
        this.itemclickListner = itemclickListner;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup,
            int i)
    {

        // Here we inflate the corresponding
        // layout of the parent item
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(
                        R.layout.childrecycleview,
                        viewGroup, false);

        return new ParentViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(
            @NonNull ParentViewHolder parentViewHolder,
            int position)
    {

        // Create an instance of the ParentItem
        // class for the given position
        Category parentItem
                = itemList.get(position);

        // For the created instance,
        // get the title and set it
        // as the text for the TextView
        parentViewHolder.ParentItemTitle.setText(parentItem.getCategory());

        // Create a layout manager
        // to assign a layout
        // to the RecyclerView.

        // Here we have assigned the layout
        // as LinearLayout with vertical orientation
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                parentViewHolder
                        .ChildRecyclerView
                        .getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
        layoutManager
                .setInitialPrefetchItemCount(
                        parentItem
                                .getProducts()
                                .size());

        // Create an instance of the child
        // item view adapter and set its
        // adapter, layout manager and RecyclerViewPool
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,
                layoutManager.getOrientation());
        parentViewHolder.ChildRecyclerView.addItemDecoration(dividerItemDecoration);

        Childadapter childItemAdapter;
        if(parentItem.getProducts().size()<=2){
            childItemAdapter = new Childadapter(parentItem.getProducts(),context ,0);
        }
        else{
            childItemAdapter = new Childadapter(new ArrayList(parentItem.getProducts().subList(0,3)),context ,0);
        }
        parentViewHolder.ChildRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.ChildRecyclerView.setAdapter(childItemAdapter);
        parentViewHolder.ChildRecyclerView.setRecycledViewPool(viewPool);
        parentViewHolder.Viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemclickListner.onItemclick(itemList.get(position).getProducts());
            }
        });
    }

    // This method returns the number
    // of items we have added in the
    // ParentItemList i.e. the number
    // of instances we have created
    // of the ParentItemList
    @Override
    public int getItemCount()
    {

        return itemList.size();
    }

    public interface ItemclickListner {
        void onItemclick(ArrayList<Product> categ);
    }

    // This class is to initialize
    // the Views present in
    // the parent RecyclerView
    public  class ParentViewHolder
            extends RecyclerView.ViewHolder {

        private TextView ParentItemTitle;
        private RecyclerView ChildRecyclerView;
        private Button Viewmore;

        ParentViewHolder(final View itemView)
        {
            super(itemView);

            ParentItemTitle = itemView.findViewById(R.id.parent_item_title);
            Viewmore = itemView.findViewById(R.id.viewmore);
            ChildRecyclerView = itemView.findViewById(R.id.child_recyclerview);

        }
    }
}
