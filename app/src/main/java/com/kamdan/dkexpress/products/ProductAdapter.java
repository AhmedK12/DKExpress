package com.kamdan.dkexpress.products;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.model.Products;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    Context c;
    ArrayList<Products> items;
    ImageView imageView;


    public ProductAdapter(Context c, ArrayList<Products> arr, ImageView background)
    {
        this.c = c;
        items = arr;
        imageView = background;
    }
    @Override
    public int getCount() {
        return items.size();
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
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
        {
            if(((Helper) c.getApplicationContext()).Retailer)
                view = inflater.inflate(R.layout.productlayout, null);
            else
                view = inflater.inflate(R.layout.grocerypoduct, null);

        }
        TextView textView = view.findViewById(R.id.product_name);
        TextView Product_Price = view.findViewById(R.id.product_price);
        TextView newPrice = view.findViewById(R.id.new_product_price);

        TextView brand_nam = view.findViewById(R.id.brand_nam);
        brand_nam.setText(items.get(i).getBrand_id());
        textView.setText(items.get(i).getTitle());
        try {
            TextView Product_Description = view.findViewById(R.id.product_description);
            if(!items.get(i).getDescription_box().equals("null"))
                Product_Description.setText(items.get(i).getDescription_box());
            else
                Product_Description.setText(items.get(i).getDescription());
        }catch (Exception ignored){

        }

        if(!items.get(i).getNew_price_box().equals("null"))
            newPrice.setText(items.get(i).getNew_price_box());
        else
            newPrice.setText(items.get(i).getNewprice());
        Product_Price.setText(items.get(i).getPrice());

        Glide.with(c).load(items.get(i).getImage()).into((ImageView) view.findViewById(R.id.product_image));
        if(i>0){
            if(imageView.getVisibility()==View.VISIBLE)
                imageView.setVisibility(View.GONE);
        }

        return view;
    }
}
