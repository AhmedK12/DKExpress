package com.kamdan.dkexpress.cakes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.Cart;
import com.kamdan.dkexpress.grocery.Childadapter;
import com.kamdan.dkexpress.grocery.Converter;
import com.kamdan.dkexpress.grocery.adapter.ImageAdapter;
import com.kamdan.dkexpress.grocery.adapter.descriptionadapter;
import com.kamdan.dkexpress.grocery.model.CartItem;
import com.kamdan.dkexpress.products.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import uk.co.senab.photoview.PhotoView;

public class Product extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String Delivery_Date;
    private com.kamdan.dkexpress.grocery.model.Product product;
    private PhotoView Product_Image;
    private AppCompatSpinner Size_Spinner;
    private Toolbar toolbar;
    int Index = 0;
    RecyclerView description,review;
    TextView Product_Title, Product_Price, Availability_in,new_price,offer;
    Button Add_to_Cart, Buy_Now;
    EditText No_of_Product,cake_message;
    Context context;
    RecyclerView Similar;
    RatingBar ratingBar;
    ProgressBar progressBar;
    Menu menu1;
    Childadapter childadapter;
    ArrayList<com.kamdan.dkexpress.grocery.model.Product> products = new ArrayList<>(0);
    ArrayList<String> Size = new ArrayList<>(0), New_Price = new ArrayList<>(0), Images = new ArrayList<>(0),Available = new ArrayList<>(0),Offers = new ArrayList<>(0);
    ImageButton Increment, Decrement, BackButton;
    LinearLayout description_text, reviews_text;
    boolean addcart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product4);
        Assignment();


        context = this;
        Size_Spinner.setOnItemSelectedListener(this);
        toolbar.inflateMenu(R.menu.top_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        product = (com.kamdan.dkexpress.grocery.model.Product) Objects.requireNonNull(getIntent().getSerializableExtra("product"));
        Size.addAll(Arrays.asList(product.getSize().split(",")));
        New_Price.addAll(Arrays.asList(product.getNewprice().split(",")));
        Available.addAll(Arrays.asList(product.getAvailability().split(",")));
        Images.addAll(Arrays.asList(product.getImage().split(",")));
        Product_Title.setText(product.getTitle());
        Product_Price.setText(product.getPrice().split(",")[0]);
        Glide.with(this).load(Images.get(0)).into(Product_Image);

        Add_Cart();

        incrementer();
        decrementer();
        set_Description();
        set_rating();
        set_Offer();
        set_review();
        Set_Back();
        Set_Spinner();
        Buy_Now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set_Buy_Now();
            }
        });




    }

    void Assignment(){
        cake_message = findViewById(R.id.message);
        Product_Price = findViewById(R.id.price);
        Product_Title = findViewById(R.id.title);
        Product_Image = findViewById(R.id.product_image);
        Size_Spinner = findViewById(R.id.sizespinner);
        BackButton = findViewById(R.id.back_button);
        Increment = findViewById(R.id.product_incrementer);
        Decrement = findViewById(R.id.product_decrementer);
        Add_to_Cart = findViewById(R.id.add_to_cart);
        No_of_Product = findViewById(R.id.product_quantity);
        Availability_in = findViewById(R.id.available);
        Similar = findViewById(R.id.similar_product);
        toolbar = findViewById(R.id.product_toolbar);
        ratingBar = findViewById(R.id.ratingbar);
        description = findViewById(R.id.description);
        review = findViewById(R.id.review_recyclerview);
        description_text = findViewById(R.id.desc);
        reviews_text = findViewById(R.id.rev);
        new_price = findViewById(R.id.new_price);
        offer = findViewById(R.id.offer);
        Buy_Now =findViewById(R.id.product_buy_now);
        progressBar = findViewById(R.id.add_to_cart_progress_bar);
    }

    public void Set_Buy_Now(){
        CartItem cartItem = new CartItem(Product_Title.getText().toString(), product.getId(), new_price.getText().toString(), Size.get(Index), product.getImage(), No_of_Product.getText().toString(),Sub_Total(new_price.getText().toString(),No_of_Product.getText().toString()),product.getPopularity(),Integer.parseInt(product.getFreed_elivery_amount()));
        if(!addcart)
            ((Helper) context.getApplicationContext()).Items.add(cartItem);
        startActivity(new Intent(this, Cart.class));
        finish();
    }



    public void Set_Initials(int i){
        new_price.setText(New_Price.get(i));
        float s ;
        try{
            s = Float.parseFloat(product.getPrice().split(",")[i].replace("₹",""))-Float.parseFloat(New_Price.get(i).replace("₹",""));
        }catch (Exception e){
            s = 0;
        }
        if(s==0){
            offer.setText("");
            return;
        }
        float a;
        try{
            a = s/Float.parseFloat(product.getPrice().split(",")[i].replace("₹", ""));
        }catch (Exception e){
            a = 0;
        }
        a = a*100;
        int b = (int) a;
        if(s>b){
            offer.setText("₹"+Float.toString(s));
        }
        else{
            offer.setText(b +"%");
        }


    }


    public void incrementer(){
        Increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = 1;
                try{
                    n = Integer.parseInt(No_of_Product.getText().toString().trim());
                } catch (Exception e){

                }

                No_of_Product.setText(Integer.toString(n+1));
            }


        });
    }
    public void decrementer(){
        Decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = 1;
                try{
                    n = Integer.parseInt(No_of_Product.getText().toString().trim());
                } catch (Exception e){

                }
                if (n>1) {
                    No_of_Product.setText(Integer.toString(n - 1));
                }
            }
        });
    }
    public void Add_Cart(){


        Add_to_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcart = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animation blink = AnimationUtils.loadAnimation(context, R.anim.blink);
                        Add_to_Cart.startAnimation(blink);
                    }
                }, 100);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        add_cart_item();
                    }
                }, 600);
            }
        });
    }
    public void Set_Spinner(){

        ArrayAdapter ad = new ArrayAdapter(this, R.layout.spinner_item, Size);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        Size_Spinner.setAdapter(ad);

    }
    public void Set_Back(){
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void SetSimilar(){
        childadapter = new Childadapter(products,context,1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        Similar.setLayoutManager(layoutManager);
        Similar.setAdapter(childadapter);
        for(com.kamdan.dkexpress.grocery.model.Product p:((Helper)context.getApplicationContext()).Products){
            if(p.getCategory().equals(product.getCategory()))
                products.add(p);
        }
        childadapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(Available.get(position).equals("true")){
            try {
                Set_Initials(position);
                Add_to_Cart.setClickable(true);
                Product_Price.setText(product.getPrice().split(",")[position]);
                Product_Price.setTextColor(Color.BLACK);
                new_price.setText(New_Price.get(position));
                Index = position;
                Glide.with(this).load(Images.get(position)).into(Product_Image);
            }catch (Exception e){
                Glide.with(this).load(Images.get(0)).into(Product_Image);
            }
        }
        else{
            Add_to_Cart.setClickable(false);
            Product_Price.setText("Out of Stock");
            Product_Price.setTextColor(Color.RED);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu1 = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.cart);
        int n=0;
        try {
            n = ((Helper) this.getApplicationContext()).Items.size();
        }catch (Exception e){
            n = 0;
        }
        menuItem.setIcon(Converter.convertLayoutToImage(com.kamdan.dkexpress.cakes.Product.this,n,R.drawable.ic_baseline_shopping_cart_24));
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getItemId()==R.id.cart && ((Helper) this.getApplicationContext()).Items.size()>0){
            startActivity(new Intent(context,Cart.class));
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
    public void menu_Update(){
        getMenuInflater().inflate(R.menu.top_app_bar, this.menu1);
        MenuItem menuItem = this.menu1.findItem(R.id.cart);
        int n=0;
        try {
            n = ((Helper) this.getApplicationContext()).Items.size();
        }catch (Exception e){
            n = 0;
        }
        menuItem.setIcon(Converter.convertLayoutToImage(com.kamdan.dkexpress.cakes.Product.this,n,R.drawable.ic_baseline_shopping_cart_24));
    }

    public String Sub_Total(String p, String n){
        float sub_totla = 0;
        float  a = Float.parseFloat(n.trim());
        float b = Float.parseFloat(p.replace("₹",""));
        sub_totla = a*b;
        return "₹"+Float.toString(sub_totla);
    }

    public void set_rating(){
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#FF9529"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.parseColor("#123456"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
    }

    public void set_Description(){
        ArrayList<String> data = new ArrayList<>(0), value = new ArrayList<>(0);
        data.add("Type");
        value.add("Veg");
        data.add("Availability");
        value.add("In Stock");
        data.add("Brand");
        value.add("DkExpress");


        data.add("Available Sizes"); value.add(product.getSize());
        descriptionadapter adapter = new descriptionadapter(data,value);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        description.addItemDecoration(dividerItemDecoration);
        description.setLayoutManager(layoutManager);
        description.setAdapter(adapter);
    }
    public void set_review(){

    }

    public void set_Offer(){
        for(int i=0;i<New_Price.size();i++){
            float s ;

            float aa = Float.parseFloat(product.getPrice().split(",")[i].replace("₹",""));
            s = Float.parseFloat(New_Price.get(i).replace("₹",""));

            float a;
            try{
                a = s/Float.parseFloat(product.getPrice().split(",")[i].replace("₹", ""));
            }catch (Exception e){
                a = 0;
            }

            a = a*100;
            int b = (int) a;

            String st = Float.toString(s) +" ("+ Integer.toString(b) +"%Off)";

            Offers.add(st);
        }
    }

    public void add_cart_item(){

        if(!Product_Price.getText().toString().equals("Out of Stock")) {
            CartItem cartItem = new CartItem(Product_Title.getText().toString(), product.getId(), new_price.getText().toString(), Size.get(Index), product.getImage(), No_of_Product.getText().toString(),Sub_Total(new_price.getText().toString(),No_of_Product.getText().toString()),product.getPopularity(),Integer.parseInt(product.getFreed_elivery_amount()));
            ((Helper) context.getApplicationContext()).Items.add(cartItem);
            menu_Update();

            Add_to_Cart.clearAnimation();
        }
    }

    public void setImage_pager(){
        ViewPager pager = findViewById(R.id.photos_viewpager);
        ImageAdapter adapter = new ImageAdapter(Images,context);
        pager.setAdapter(adapter);

        TabLayout tabLayout =  findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager, true);
    }



}