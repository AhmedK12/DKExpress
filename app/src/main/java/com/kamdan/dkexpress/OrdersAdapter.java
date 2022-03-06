package com.kamdan.dkexpress;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kamdan.dkexpress.grocery.model.Order;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyAdapterViewHolder> {
    private ArrayList<com.kamdan.dkexpress.grocery.model.Order> Orders;
    Context context;
    public OrdersAdapter(Context context,ArrayList<Order> Orders){
        this.context = context;
        this.Orders = Orders;
    }
    @NonNull
    @Override
    public OrdersAdapter.MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order,parent,false);
        return new OrdersAdapter.MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.MyAdapterViewHolder holder, int position) {
        ArrayList<String> products = new ArrayList<>(1),Names = new ArrayList(Arrays.asList(Orders.get(position).getNames().split(","))), ids = new ArrayList(Arrays.asList(Orders.get(position).getIds().split(","))),Price = new ArrayList(Arrays.asList(Orders.get(position).getPrices().split(","))),Quantities = new ArrayList(Arrays.asList(Orders.get(position).getQuantities().split(","))),Sizes = new ArrayList(Arrays.asList(Orders.get(position).getSizes().split(",")));


        products.addAll(Arrays.asList(this.Orders.get(position).getProducts().split(",")));
        int n;
        if(Orders.get(position).getType().equals("Image")){
            n=1;
        }
        if(Orders.get(position).getType().equals("Record"))
        {
            n = Names.size();
        }
        else
           n = Names.size();
        LinearLayout l1 = new LinearLayout(context), l2 = new LinearLayout(context),l3 = new LinearLayout(context);
        l1.setOrientation(LinearLayout.HORIZONTAL);
        l2.setOrientation(LinearLayout.HORIZONTAL);
        l3.setOrientation(LinearLayout.VERTICAL);
        l1.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(220), dpToPx(240)));
        if(n>1)
            l1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(120)));
        l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(120)));
        for(int i=0;i<5&&i<n;i++){
//            if(i<(n-n/2)){
//                ImageView img =new ImageView(context);
//                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                img.setLayoutParams(new android.view.ViewGroup.LayoutParams(dpToPx(220/(n/2 + (n%2==0?0:1))), ViewGroup.LayoutParams.MATCH_PARENT));
//                l1.addView(img);
//                Glide.with(context).load(images[i]).into((ImageView) img);
//            }
//            else{
//                ImageView img =new ImageView(context);
//                img.setScaleType(ImageView.ScaleType.FIT_XY);
//                img.setLayoutParams(new android.view.ViewGroup.LayoutParams(dpToPx(220/(n/2 + (n%2==0?0:1))), dpToPx(120)));
//                l2.addView(img);
//                img.setAdjustViewBounds(true);
//                Glide.with(context).load(images[i]).into((ImageView) img);
//            }
            TextView t = new TextView(context);

            if(i>=3){

                t.setText("..................................");
                t.setHorizontallyScrolling(true);
                t.setMaxLines(1);
                t.setPadding(10,10,0,0);
                t.setEllipsize(TextUtils.TruncateAt.END);
                t.setTextSize(13);
                t.setTextColor(Color.BLACK);
                l3.addView(t);
            }
            else{
                t.setText(Names.get(i));
                t.setHorizontallyScrolling(true);
                t.setMaxLines(1);
                t.setPadding(10,10,0,0);
                t.setEllipsize(TextUtils.TruncateAt.END);
                t.setTextSize(13);
                t.setTextColor(Color.BLACK);
                l3.addView(t);
            }
        }
//        holder.linearLayout1.addView(l1);
//        holder.linearLayout1.addView(l2);
        if(holder.linearLayout2.getChildCount()<1)
            holder.linearLayout2.addView(l3);
        holder.total.setText(Orders.get(position).getTotal_amount().replace("Total: ",""));
        holder.oeder_date.setText(Orders.get(position).getDate());
        holder.arrival_date.setText(Orders.get(position).getStatus());
        ArrayList<String> values;
        values = new ArrayList<>();


        for (int i=0;i<Names.size();i++){
            values.add(Names.get(i)
                    + ","+Price.get(i)+","+
                    Quantities.get(i)+","+
                    ids.get(i)+","+Sizes.get(i));
        }
        values.add("Date of Order,"+Orders.get(position).getDate());
        values.add("Amount,"+Orders.get(position).getTotal_amount());
        values.add("Status,"+Orders.get(position).getStatus());
        values.add("Slot of Arrival,"+Orders.get(position).getSlot());
        values.add("Payment Method," + Orders.get(position).getPayment_method());
        if (Orders.get(position).getStatus().equals("Delivered")||Orders.get(position).getStatus().equals("Cancelled")){
            holder.order_cancel.setVisibility(View.GONE);
            values.add("Due Amount,"+"₹0.00");
            if(Orders.get(position).getStatus().equals("Delivered"))
                values.add("Payment Status,"+"Paid");

        }
        else{
            holder.order_cancel.setVisibility(View.VISIBLE);
            values.add("Due,"+Orders.get(position).getDue());
        }
        List<ArrayList<String>> group = new ArrayList<ArrayList<String>>(4);


        holder.get_details.setOnClickListener(v -> {
           context.startActivity(new Intent(context,Get_Details.class).putExtra("Order", values));
        });
        holder.order_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference OrderRef = FirebaseDatabase.getInstance().getReference().child("Grocery/User/"+Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()+"/Order");
                OrderRef.child(Orders.get(position).getId()).child("status").setValue("Cancelled");
                holder.order_cancel.setVisibility(View.GONE);
                DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Order_Cancelled/");
                orderRef.child(Orders.get(position).getId()).setValue(Orders.get(position).getUser());
            }
        });

    }

    @Override
    public int getItemCount() {

        return Orders.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyAdapterViewHolder extends  RecyclerView.ViewHolder{
        TextView total,oeder_date,arrival_date;
        Button get_details,track,order_cancel;
        LinearLayout linearLayout1,linearLayout2;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            total = itemView.findViewById(R.id.order_total);
            oeder_date = itemView.findViewById(R.id.order_placed);
            arrival_date =itemView.findViewById(R.id.order_delivered);
            get_details = itemView.findViewById(R.id.order_details);
            linearLayout1 = itemView.findViewById(R.id.orders1);
            linearLayout2 = itemView.findViewById(R.id.names);
            order_cancel = itemView.findViewById(R.id.order_cencel);

        }


    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }




}
