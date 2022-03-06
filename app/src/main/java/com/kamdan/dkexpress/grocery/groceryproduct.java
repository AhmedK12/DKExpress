package com.kamdan.dkexpress.grocery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kamdan.dkexpress.Orders;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.adapter.Category_Adapter;
import com.kamdan.dkexpress.grocery.adapter.Gridadapter;
import com.kamdan.dkexpress.grocery.adapter.Product_lite;
import com.kamdan.dkexpress.grocery.adapter.offeradapter;
import com.kamdan.dkexpress.grocery.model.Category;
import com.kamdan.dkexpress.grocery.model.Offer;
import com.kamdan.dkexpress.grocery.model.Product;
import com.kamdan.dkexpress.model.category;
import com.kamdan.dkexpress.products.BrandAdapter;
import com.kamdan.dkexpress.products.BrandsAdapter;
import com.kamdan.dkexpress.products.Helper;
import com.kamdan.dkexpress.products.ProductAdapter;
import com.kamdan.dkexpress.wallet.Wallet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class groceryproduct extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnCreateContextMenuListener{
//    something else
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 11;
//
    ArrayList<Product_lite> products;
    public TextView seeall;
    public Context context;
    private RecyclerView recyclerView_category;
    private BottomNavigationView bottomNavigationView;
    private ViewPager offer_recyclerview;
    private Toolbar toolbar;
    private GridView gridView;
    private ScrollableGridView Category_gridview,product_gridview;

    LinearLayout gridline,normaline;
    private ImageButton backbutton;
    private SearchView searchView;
    ArrayList<com.kamdan.dkexpress.grocery.model.Category> category = new ArrayList<>(0);
    ArrayList<Offer> Offers = new ArrayList<>(0);
    ArrayList<Integer> indexs = new ArrayList<>(0);
    ArrayList<String> images = new ArrayList<>(0),images1 = new ArrayList<>(0) ,texts = new ArrayList<>(0),texts1 = new ArrayList<>(0),keys= new ArrayList<>(0),s_list = new ArrayList<>(0);
    private ImageView background;
    Category_Adapter categoryAdapter;
    com.kamdan.dkexpress.grocery.adapter.offeradapter offeradapter;
    Gridadapter gridadapter,main_product_gridadapter;
    BrandAdapter suggesion_adapter;
    boolean doubleBackToExitPressedOnce = false;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    Database database;
    RecyclerView suggesion_list;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceryproduct);
        Assignments();
        context =this;
        suggesion_adapter = new BrandAdapter(context, s_list, text -> {
            searchView.setQuery(text,true);
        });
        suggesion_list.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        suggesion_list.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        suggesion_list.setAdapter(suggesion_adapter);
        database = new Database(this);
        set_ProductAdapter();
        set_offer();
        set_brandsAdapter();
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridline.setVisibility(View.GONE);
                normaline.setVisibility(View.VISIBLE);
                searchView.setIconified(true);
                suggesion_list.setVisibility(View.GONE);
                searchView.clearFocus();
                indexs.clear();
                for(Product_lite p: products) {
                        indexs.add(p.getIndex());
                }
                main_product_gridadapter.notifyDataSetChanged();
            }
        });
        searchView.clearFocus();
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
                suggesion_list.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                 suggesion_list.setVisibility(View.GONE);
                 indexs.clear();
                 get_data(query);
                 normaline.setVisibility(View.GONE);
                 gridline.setVisibility(View.VISIBLE);
                 gridView.setAdapter(main_product_gridadapter);
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
                indexs.clear();
                return false;
            }
        });
        categoryAdapter = new Category_Adapter(context,images1,texts1,1);
        Category_gridview.setAdapter(categoryAdapter);
        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seeall.getText().toString().trim()=="See All"){
                    seeall.setText("Minimize");
                   categoryAdapter = null;
                   categoryAdapter = new Category_Adapter(context,images,texts,1);
                   Category_gridview.setAdapter(categoryAdapter);
                }
                else {
                    seeall.setText("See All");
                    categoryAdapter = null;
                    categoryAdapter = new Category_Adapter(context,images1,texts1,1);
                    Category_gridview.setAdapter(categoryAdapter);
                }
            }
        });
        setSupportActionBar(toolbar);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                offer_recyclerview.post(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            offer_recyclerview.setCurrentItem((offer_recyclerview.getCurrentItem()+1)%Offers.size());
                        }catch (Exception e){
                            return;
                        }

                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);
        this.bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        bottomNavigationView.setOnCreateContextMenuListener(this);
        callAppUpdate();
        set_category();
        Category_gridview.setOnItemClickListener((parent, view, position, id) -> {
            startActivity(new Intent(context,Product_Category.class).putExtra("cat",texts.get(position)+"kamaranAhmed"+keys.get(position)));


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_APP_UPDATE)
            Toast.makeText(context,"Start Download", Toast.LENGTH_LONG).show();
        if(requestCode!=RESULT_OK)
            Toast.makeText(context, "Update flow failed", Toast.LENGTH_SHORT).show();
    }

    public void Assignments(){
        product_gridview = findViewById(R.id.product_gridview);
        recyclerView_category = findViewById(R.id.category);
        toolbar = findViewById(R.id.product_toolbar);
        seeall = findViewById(R.id.seeaall);
        searchView = findViewById(R.id.product_search_view1);
        background = findViewById(R.id.background);
        offer_recyclerview = findViewById(R.id.offer);
        gridView = findViewById(R.id.products_grid);
        gridline = findViewById(R.id.gridline);
        normaline = findViewById(R.id.normalLine);
        backbutton = findViewById(R.id.back_button);
        bottomNavigationView = findViewById(R.id.bottomnav);
        Category_gridview = findViewById(R.id.category_grid);
        suggesion_list = findViewById(R.id.suggestionlist);
    }
    public void set_category(){
        FirebaseDatabase.getInstance().getReference().child("Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int k=0;
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    keys.add(snapshot1.getKey());
                    String  Category = snapshot1.child("image").getValue(String.class);
                    String  Category1 = snapshot1.child("category").getValue(String.class);
                    images.add(Category);
                    texts.add(Category1);
                    k++;
                    if(k<5){
                        images1.add(Category);
                        texts1.add(Category1);
                    }

                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void set_ProductAdapter(){
        indexs.clear();
        products = ((Helper)context.getApplicationContext()).getProducts("");
        Collections.sort(products, new Comparator<Product_lite>() {
            @Override
            public int compare(Product_lite o1, Product_lite o2) {
                return Integer.parseInt(o2.getPopularity())-Integer.parseInt(o1.getPopularity());
            }
        });
        for(Product_lite p: products)
            indexs.add(p.getIndex());
        main_product_gridadapter = new Gridadapter(context,indexs);
        product_gridview.setAdapter(main_product_gridadapter);
        main_product_gridadapter.notifyDataSetChanged();
    }

    public void setToolbar(){
        setSupportActionBar(this.toolbar);
    }
    public void set_offer(){
        offeradapter = new offeradapter(this,Offers, new offeradapter.ItemclickListner() {
            @Override
            public void onItemclick(Offer offer) {

//                normaline.setVisibility(View.GONE);
//                gridline.setVisibility(View.VISIBLE);
//                indexs.clear();
//                indexs = ((Helper)context.getApplicationContext()).getOffer(Integer.parseInt(offer.getOffer()),offer.getCategory());
//                gridView.setAdapter(gridadapter);
//                gridadapter.notifyDataSetChanged();

            }
        });
        offer_recyclerview.setAdapter(offeradapter);
        FirebaseDatabase.getInstance().getReference().child("Offer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Offers.clear();
                for (DataSnapshot s: snapshot.getChildren()){
                    Offers.add(s.getValue(Offer.class));
                }
                offeradapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void set_brandsAdapter(){
        ArrayList<category> categoris = new ArrayList<>();
        BrandsAdapter CategoryAdapter = new BrandsAdapter(this, categoris, new BrandsAdapter.ItemclickListner() {
            @Override
            public void onItemclick(category Category,LinearLayout linearLayout) {
                startActivity(new Intent(context,Product_Category.class).putExtra("cat",Category.getCategory()+"kamaranAhmed"+Category.getKey()));
            }
        });
        recyclerView_category.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        recyclerView_category.setAdapter(CategoryAdapter);
        DatabaseReference brandRef = FirebaseDatabase.getInstance().getReference().child("Category/");
        brandRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoris.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    category Category = snapshot1.getValue(category.class);
                    assert Category != null;
                    Category.setKey(snapshot1.getKey());
                    categoris.add(Category);

                }
                CategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.bottomNavigationView.postDelayed(() -> {

            switch (item.getItemId()){

                case R.id.cart:
                    if(item.getItemId()==R.id.cart && ((Helper) this.getApplicationContext()).Items.size()>0){
                        startActivity(new Intent(context,Cart.class));
                    }
                    else{
                        Toast.makeText(context,"Cart is Empty ",Toast.LENGTH_SHORT).show();
                        bottomNavigationView.getMenu().findItem(R.id.main).setChecked(true);
                    }
                    break;

                case R.id.user:
                    startActivity(new Intent(context, Profile.class));
                    this.finish();
                    break;

//                case R.id.record_order:
//                    startActivity(new Intent(context, Record_Order.class));
//                    this.finish();
//                    break;
//
//                case R.id.Upload_Order:
//                    startActivity(new Intent(context, OrderByImage.class));
//                    this.finish();
//                    break;
            }

        }, 100);
        return true;
    }



    @Override
    protected void onResume() {
        callAppUpdate();
        super.onResume();
    }


    @Override
    public void onBackPressed(){
        set_ProductAdapter();
        if(gridView.getVisibility()==View.VISIBLE){
            gridView.setVisibility(View.GONE);
            normaline.setVisibility(View.VISIBLE);
        }
        else{
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }
    @Override
    public void onRestart() {
        callAppUpdate();
        super.onRestart();
        int n = 0;
        try {
            n = ((Helper) this.getApplicationContext()).Items.size();
        } catch (Exception e) {
            n = 0;
        }
        if(n>0)
         bottomNavigationView.getOrCreateBadge(R.id.cart).setNumber(n);

    }

    @Override
    protected void onStart() {
        callAppUpdate();
        super.onStart();
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
//        for (String s: treeMap.values())
//        {
//            if(s.toLowerCase().startsWith(text.toLowerCase())){
//
//                deque.addLast(s);
//            }
//            else{
//                deque.addFirst(s);
//            }
//        }

        s_list.addAll(treeMap.values());
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
             ArrayList<String> Name = new ArrayList<>(0);
             Name.addAll(Arrays.asList(product_lite.getTitle().split(" ")));

            if(check(Name,text1)){
                product_lites4.add(product_lite);
                if(!text2.equals("")&&check(Name,text2)){
                    product_lites4.remove(product_lite);
                    product_lites3.add(product_lite);
                    if(!text31.equals("") &&check(Name,text31)){
                        product_lites4.remove(product_lite);
                        product_lites3.remove(product_lite);
                        product_lites2.add(product_lite);
                        if(!text32.equals("")&&check(Name,text32)){
                            product_lites1.add(product_lite);
                        }
                    }
                }
            }
        }
        product_lites1.addAll(product_lites2);
        product_lites1.addAll(product_lites3);
        product_lites1.addAll(product_lites4);
        product_lites2.clear();
        for(Product_lite product_lite:products){
            if(!text2.equals("")&&product_lite.getTitle().toLowerCase().contains(text2))
                product_lites2.add(product_lite);
        }
        product_lites1.addAll(product_lites2);
        for(Product_lite p: product_lites1) {
            indexs.add(p.getIndex());

        }


    }

    public boolean check(ArrayList<String> a,String b){
        boolean result=false;
        for(String s : a){
            if(s.startsWith(b)){
                return true;
            }
        }
        return result;
    }

    public void callAppUpdate(){
        mAppUpdateManager = AppUpdateManagerFactory.create(context);
        Task<AppUpdateInfo> appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if(appUpdateInfo.updateAvailability()==UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,groceryproduct.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
    }



}