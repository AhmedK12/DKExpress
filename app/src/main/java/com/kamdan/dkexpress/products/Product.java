package com.kamdan.dkexpress.products;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.model.Product_Price;
import com.kamdan.dkexpress.model.Products;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
public class Product extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView Title, Description, Price;
    RadioGroup RGroup;
    private RadioButton Layer;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private ImageButton incrementer,decrementer;
    private EditText no_of_product;
    Context context;
    String size = " Box";
    int Index = 0;
    private Button addtocart, remove_product;
    Products product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product2);
        assignment();
        context =this;
        Intent intent = getIntent();
        product = (Products) Objects.requireNonNull(intent.getSerializableExtra("product"));
        setInitials();
        String[] arrOfimage = product.getImage().split("@#");
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(new ImageAdapter(this,arrOfimage));

//        if ( ((Helper) context.getApplicationContext()).isAdded(product.getId())){
//            remove_product.setVisibility(View.VISIBLE);
//        }
        RGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton btn = findViewById(checkedId);


                if (btn.getText().toString().trim().equals("Box")){
                    if(!product.getNew_price_box().equals("null"))
                        Price.setText(product.getNew_price_box());
                    else
                        Price.setText(product.getNewprice());
                    if(!product.getDescription_box().equals("null"))
                        Description.setText(product.getDescription_box());
                    else
                        Description.setText(product.getDescription());

                    int n  = Integer.parseInt(no_of_product.getText().toString().replace(size,"").trim());
                    size = " Box";
                    no_of_product.setText(Integer.toString(n)+size);
                }
                else{
                    if(product.getNew_price_layer().equals("null")){
                        btn.setChecked(false);
                        RadioButton b = findViewById(R.id.box);
                        b.setChecked(true);
                        return;
                    }
                    Description.setText(product.getDescription_layer());
                    Price.setText(product.getNew_price_layer());
                    int n  = Integer.parseInt(no_of_product.getText().toString().replace(size,"").trim());
                    size = " " + product.getLayer().trim();
                    no_of_product.setText(Integer.toString(n)+size);
                }
            }
        });
        incrementer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int n = 1;
                try{
                    n = Integer.parseInt(no_of_product.getText().toString().replace(size,"").trim());
                } catch (Exception e){

                }

                no_of_product.setText(Integer.toString(n+1)+size);
            }
        });
        decrementer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = 1;
                try{
                    n = Integer.parseInt(no_of_product.getText().toString().replace(size,"").trim());
                } catch (Exception e){

                }
                if (n>1)
                    no_of_product.setText(Integer.toString(n-1)+size);
            }
        });
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(((Helper) context.getApplicationContext()).isAdded(product.getId()))){
                    Product_Price p = new Product_Price();
                    p.Product_Id = product.getId();
                    p.Price_Per_Item = Price.getText().toString().trim();
                    p.Product_Name = Title.getText().toString().trim().replace("Rs.","");
                    p.Quantity = no_of_product.getText().toString().trim().replace(size,"");
                    p.type1 = size;
                    p.Size = product.getSize();
                    p.Product_Image = product.getImage();
                    ((Helper) context.getApplicationContext()).Prods.add(p);
                    Toast.makeText(context, "SuccessFully Product Added", Toast.LENGTH_SHORT).show();
                }

                finish();

            }
        });

    }
    void assignment(){
        Title = findViewById(R.id.details_title);
        RGroup = findViewById(R.id.radiogrp);
        Description = findViewById(R.id.detail_description);
        Price = findViewById(R.id.details_price);
        recyclerView = findViewById(R.id.detailsrecycler);
        incrementer = findViewById(R.id.product_incrementer);
        decrementer = findViewById(R.id.product_decrementer);
        no_of_product = findViewById(R.id.product_quantity);
        addtocart = findViewById(R.id.product_place_order);
        remove_product = findViewById(R.id.product_remove_order);
        Layer = findViewById(R.id.line);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void setInitials(){
        Title.setText(product.getTitle());
        Layer.setText(product.getLayer().trim());
        if(!product.getNew_price_box().equals("null"))
            Price.setText(product.getNew_price_box());
        else
            Price.setText(product.getNewprice());
        if(!product.getDescription_box().equals("null"))
            Description.setText(product.getDescription_box());
        else
            Description.setText(product.getDescription());
    }

}