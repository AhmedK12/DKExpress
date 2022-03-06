package com.kamdan.dkexpress.grocery;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.adapter.Category_Adapter;
import com.kamdan.dkexpress.grocery.adapter.Gridadapter;
import com.kamdan.dkexpress.grocery.adapter.Product_lite;
import com.kamdan.dkexpress.products.BrandAdapter;
import com.kamdan.dkexpress.products.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class Product_Category extends AppCompatActivity {
    ScrollableGridView Category_gridview,Product_gridview;
    private ImageButton backbutton,back_button;
    private SearchView searchView;
    ArrayList<String> images = new ArrayList<>(0), texts = new ArrayList<>(0), s_list = new ArrayList<>(0);
    ArrayList<Integer> indexs = new ArrayList<>(0);
    BrandAdapter suggesion_adapter;
    RecyclerView suggesion_list;
    Context context;
    TextView available_category;
    ArrayList<Product_lite> products;
    LinearLayout only_product, category_product;
    String category;
    boolean In_product = true;
    Gridadapter gridadapter,main_product_gridadapter;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        category = getIntent().getStringExtra("cat");
        get_subcategory();
        Assignment();
        context = this;
        Category_gridview.setOnItemClickListener((parent, view, position, id) -> {
            indexs = ((Helper)context.getApplicationContext()).getProduct(texts.get(position));
            if (indexs.size()>0) {
                In_product = false;
                available_category.setVisibility(View.GONE);
                Category_gridview.setNumColumns(2);
                Category_gridview.setAdapter(new Gridadapter(context,indexs));
                Product_gridview.setVisibility(View.GONE);
            }
            else
                Toast.makeText(context,"Not Available, Coming Soon",Toast.LENGTH_SHORT).show();
        });
        Category_gridview.setAdapter(new Category_Adapter(context,images,texts,0));
        searchView.clearFocus();
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                suggesion_list.setVisibility(View.GONE);
                indexs.clear();
                get_data(query);
                Category_gridview.setVisibility(View.GONE);
                Product_gridview.setAdapter(main_product_gridadapter);
                main_product_gridadapter.notifyDataSetChanged();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                suggesion_list.setVisibility(View.GONE);
                Category_gridview.setVisibility(View.VISIBLE);
                indexs.clear();
                return false;
            }
        });
        set_ProductAdapter();
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(In_product)
                   finish();
                else{
                    startActivity(new Intent(context,Product_Category.class).putExtra("cat",category));
                    finish();
                }
            }
        });

        suggesion_adapter = new BrandAdapter(context, s_list, text -> {
            searchView.setQuery(text,true);
        });
        suggesion_list.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        suggesion_list.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        suggesion_list.setAdapter(suggesion_adapter);

    }

    public void Assignment(){
        Category_gridview =  findViewById(R.id.category_grid);
        backbutton= findViewById(R.id.back_button);
        searchView = findViewById(R.id.product_search_view1);
        only_product = findViewById(R.id.gridline);
        category_product = findViewById(R.id.normalLine);
        Product_gridview =  findViewById(R.id.product_gridview);
        suggesion_list = findViewById(R.id.suggestionlist);
        back_button = findViewById(R.id.back_button2);
        available_category = findViewById(R.id.available_category);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void set_ProductAdapter(){
        indexs.clear();
        products = ((Helper)context.getApplicationContext()).getProducts(category.split("kamaranAhmed")[0]);
        Collections.sort(products, new Comparator<Product_lite>() {
            @Override
            public int compare(Product_lite o1, Product_lite o2) {
                return Integer.parseInt(o2.getPopularity())-Integer.parseInt(o1.getPopularity());
            }
        });
        for(Product_lite p: products)
            indexs.add(p.getIndex());
        main_product_gridadapter = new Gridadapter(context,indexs);
        Product_gridview.setAdapter(main_product_gridadapter);
        main_product_gridadapter.notifyDataSetChanged();

        Product_gridview.setOnItemClickListener((parent, view, position, id) -> {
            if(((Helper)context.getApplicationContext()).Products.get(products.get(position).getIndex()).getAvailability().contains("true"))
              startActivity(new Intent(context, Product.class).putExtra("product",((Helper)context.getApplicationContext()).Products.get(products.get(position).getIndex())));

        });
    }


    public void get_subcategory(){
        FirebaseDatabase.getInstance().getReference().child("Category").child(category.split("kamaranAhmed")[1]).child("subcategory").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    String  Category = snapshot1.child("image").getValue(String.class);
                    String  Category1 = snapshot1.child("value").getValue(String.class);
                    images.add(Category);
                    texts.add(Category1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void filter(String text){

        s_list.clear();
        Deque<String> deque = new LinkedList<>();
        if(text.equals("")){
            suggesion_list.setVisibility(View.GONE);
            suggesion_adapter.notifyDataSetChanged();
            return;
        }
        Map<Integer, String> map = new HashMap<>();


        for(String s: ((Helper) context.getApplicationContext()).Keywords.split(",")){
            if(s.toLowerCase().startsWith(text.toLowerCase().substring(0,1)))
                map.put(minDistance(text.toLowerCase(),s.toLowerCase()),s);

        }
        Map<Integer, String> treeMap = new TreeMap<>(map);
        for (String s: treeMap.values())
        {
            if(s.toLowerCase().startsWith(text.toLowerCase())){

                deque.addLast(s);
            }
            else{
                deque.addFirst(s);
            }
        }

        s_list.addAll(deque);
        suggesion_list.setVisibility(View.VISIBLE);
        suggesion_adapter.notifyDataSetChanged();
    }
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];    //  [1 + m] means [word1 is null + word1.length()],  [1 + n] means [word2 is null + word2.length()]
        for(int i = 0; i <= m; i++) {
            dp[i][0] = i;    // word2 is null, word1 need to remove elements
        }
        for(int i = 0; i <= n; i++) {
            dp[0][i] = i;   // word1 is null, word1 need to insert elements
        }
        for(int i = 1; i <= m; i++) {
            for(int j = 1; j <= n; j++) {
                if(word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];   // current status same as previous status
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;  // dp[i - 1][j] : remove  dp[i][j - 1] : insert dp[i - 1][j - 1] : replace,   + 1 means need one more operation(insert/remove/replace) base on the previous status
                }
            }
        }
        return dp[m][n];
    }


    public void get_data(String text){
        indexs.clear();
        String text1 = text.split(" ")[0].toLowerCase(), text2,text31,text32;
        try{
            text2 = text.split(" ")[1].toLowerCase();
        }catch (Exception e){
            text2 = "";
        }
        try{
            text31 = text.split(" ")[2].toLowerCase();
        }catch (Exception e){
            text31 = "";
        }
        try{
            text32 = text.split(" ")[3].toLowerCase();
        }catch (Exception e){
            text32 = "";
        }
        ArrayList<Product_lite> product_lites1 = new ArrayList<>(0),product_lites2 = new ArrayList<>(0),product_lites3 = new ArrayList<>(0),product_lites4 = new ArrayList<>(0);
        for(Product_lite product_lite: products){
            if(product_lite.getTitle().toLowerCase().contains(text1)){
                product_lites4.add(product_lite);
                if(!text2.equals("")&&product_lite.getTitle().toLowerCase().contains(text2)){
                    product_lites4.remove(product_lite);
                    product_lites3.add(product_lite);
                    if(!text31.equals("") &&product_lite.getTitle().toLowerCase().contains(text31)){
                        product_lites4.remove(product_lite);
                        product_lites3.remove(product_lite);
                        product_lites2.add(product_lite);
                        if(!text32.equals("")&&product_lite.getTitle().toLowerCase().contains(text32)){
                            product_lites4.remove(product_lite);
                            product_lites3.remove(product_lite);
                            product_lites2.remove(product_lite);
                            product_lites1.add(product_lite);
                        }
                    }
                }
            }
        }
        product_lites1.addAll(product_lites2);
        product_lites1.addAll(product_lites3);
        product_lites1.addAll(product_lites4);


        for(Product_lite p: product_lites1) {
            indexs.add(p.getIndex());

        }


    }

    @Override
    public void onBackPressed(){

        if(Category_gridview.getNumColumns()!=2){
            super.onBackPressed();
           finish();
        }
        else{
            Category_gridview.setNumColumns(3);
            Category_gridview.setAdapter(new Category_Adapter(context,images,texts,0));
        }
    }

}