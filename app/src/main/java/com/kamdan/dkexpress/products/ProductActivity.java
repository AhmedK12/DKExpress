 package com.kamdan.dkexpress.products;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kamdan.dkexpress.Cart;
import com.kamdan.dkexpress.MainActivity;
import com.kamdan.dkexpress.MainActivity2;
import com.kamdan.dkexpress.Orders;
import com.kamdan.dkexpress.Profile;
import com.kamdan.dkexpress.R;
import com.bumptech.glide.Glide;
import com.kamdan.dkexpress.model.Brands;
import com.kamdan.dkexpress.model.Order;
import com.kamdan.dkexpress.model.Products;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kamdan.dkexpress.model.User;
import com.kamdan.dkexpress.model.category;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private RecyclerView recyclerView_brand,recyclerViewBrand;
    GridView gridView;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private TextView Product_title, Product_Price, Product_Description, Total_Price,User_Name;
    private Button orderplace,cancel_order;
    private LinearLayout linearLayout;
    CircleImageView user;
    Context context;
    String brand;
    ArrayList<String> Initiated, Cancelled;
    private String Phone,PhoneC, Msg = "Thanks for shopping with DKExpress!\nYour Order is being processed for shipping\nOrder_id   ";
    String CATEGORY = null;
    private SearchView searchView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<Products> products = new ArrayList<>(),products1 = new ArrayList<>();
    public ImageView background;
    ProductAdapter productAdapter = new ProductAdapter(this, products,background);
    ArrayList<String> favourite = new ArrayList<>();
    LinearLayout linearLayout1;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        assignment();
//        New_order();
//        Cancelled_Order();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Intent intent = new Intent();
//            String packageName = getPackageName();
//            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
//            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
//                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent.setData(Uri.parse("package:" + packageName));
//                startActivity(intent);
//            }
//        }


        context =this;
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User");
        userRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                ((Helper) context.getApplicationContext()).user = user;
                User_Name.setText(user.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent intent = getIntent();
        try {
            brand = Objects.requireNonNull(intent.getStringExtra("brand"));
        }catch (Exception e){
            brand="";
        }

        setToolbar();
        set_ProductAdapter(brand);
        set_brandAdapter();
        set_brandsAdapter();
        this.bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        Glide.with(context).load(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhotoUrl()).into(user);
        if ( ((Helper) context.getApplicationContext()).Prods.size()>0){
            linearLayout.setVisibility(View.VISIBLE);
            Total_Price.setText("Total: ₹"+((Helper) context.getApplicationContext()).Total_Price());
        }
        else{
            linearLayout.setVisibility(View.GONE);
        }
        orderplace.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Cart.class));
                ProductActivity.this.finish();
                linearLayout.setVisibility(View.GONE);
            }
        });
        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Helper) context.getApplicationContext()).clear();
                linearLayout.setVisibility(View.GONE);
            }
        });
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            if (products.get(position).getAvailability().equals("true"))
                startActivity(new Intent(context,Product.class).putExtra("product",products.get(position)));
            else
                Toast.makeText(context,"This Product is Not Available, Coming Soon",Toast.LENGTH_SHORT).show();
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.equals("")){
                    Collections.sort(products, new Comparator<Products>() {
                        @Override
                        public int compare(Products o1, Products o2) {
                            String s1 = o1.getTitle().toLowerCase().replace(" ", ""), s2 = o2.getTitle().toLowerCase();
                            boolean o1Has = s1.startsWith(query.toLowerCase());
                            boolean o2Has = s2.startsWith(query.toLowerCase());

                            if (o1Has && !o2Has) return -1;
                            else if (o2Has && !o1Has) return 1;
                            else if (o1Has && o2Has) return 0;
                            else
                                return s1.length() - s2.length();
                        }
                    });
                    ProductAdapter productAdapter1 = new ProductAdapter(context, products,background);
                    gridView.setAdapter(productAdapter1);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }



        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Collections.copy(products,products1);
                gridView.setAdapter(productAdapter);
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.top_app_bar);
        toolbar.findViewById(R.id.action_favorite).setOnClickListener(v -> fav());
        }


    @Override
    public void onRestart()
    {
        super.onRestart();
        //Refresh your stuff here


        if (Float.parseFloat(((Helper) context.getApplicationContext()).Total_Price())>0) {
            linearLayout.setVisibility(View.VISIBLE);
            Total_Price.setText("Total: ₹" + ((Helper) context.getApplicationContext()).Total_Price());
        }
        ((Helper) context.getApplicationContext()).Box = true;
    }



    public void set_ProductAdapter(String brand){
        productAdapter = new ProductAdapter(this, products,background);
        gridView.setAdapter(productAdapter);
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Product");
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                products1.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Products product = snapshot1.getValue(Products.class);
                    assert product != null;
                    if(!product.getAvailability().equals("notavailable")) {
                        if (product.getBrand_id().startsWith(brand.trim())) {
                            products.add(product);
                        }
                        products1.add(product);
                    }

                }
                set_fav();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void set_brandAdapter(){
        ArrayList<Brands> brands = new ArrayList<>();
        recyclerViewBrand.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        DatabaseReference brandRef = FirebaseDatabase.getInstance().getReference().child("Brand");
        brandRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                brands.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Brands brand = snapshot1.getValue(Brands.class);
                    assert brand != null;
                    brands.add(brand);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerViewBrand.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    public void set_brandsAdapter(){
        ArrayList<category> categoris = new ArrayList<>();
        BrandsAdapter CategoryAdapter = new BrandsAdapter(this, categoris, new BrandsAdapter.ItemclickListner() {
            @Override
            public void onItemclick(category Category,LinearLayout linearLayout) {
                if(linearLayout1!=null){
                    linearLayout1.setBackgroundColor(getResources().getColor(R.color.whiteCardColor));
                }
                linearLayout1 = linearLayout;

                products.clear();
                products.addAll(products1);
                if(CATEGORY==null){
                    CATEGORY = Category.getCategory().toLowerCase();
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.quantum_grey));
                    for (Iterator<Products> iterator = products.iterator(); iterator.hasNext(); ) {
                        if (!iterator.next().getCategory().trim().toLowerCase().equals(Category.getCategory().trim().toLowerCase())) {
                            iterator.remove();
                        }
                    }
                }
                else {
                    if (CATEGORY.equals(Category.getCategory().trim().toLowerCase())){
                        CATEGORY = null;
                        linearLayout.setBackgroundColor(getResources().getColor(R.color.whiteCardColor));

                    }
                    else {
                        CATEGORY = Category.getCategory().toLowerCase();
                        linearLayout.setBackgroundColor(getResources().getColor(R.color.quantum_grey));
                        for (Iterator<Products> iterator = products.iterator(); iterator.hasNext(); ) {
                            if (!iterator.next().getCategory().trim().toLowerCase().equals(Category.getCategory().trim().toLowerCase())) {
                                iterator.remove();
                            }
                        }
                    }
                }
                ProductAdapter productAdapter1 = new ProductAdapter(context, products,background);

                gridView.setAdapter(productAdapter1);
            }
        });
        recyclerView_brand.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        recyclerView_brand.setAdapter(CategoryAdapter);
        DatabaseReference brandRef = FirebaseDatabase.getInstance().getReference().child("Category/");
        brandRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoris.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    category Category = snapshot1.getValue(category.class);
                    categoris.add(Category);
                }
                CategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void assignment(){
        recyclerView_brand = findViewById(R.id.brands_name);
        bottomNavigationView = findViewById(R.id.bottomnav);
        toolbar = findViewById(R.id.product_toolbar);
        Total_Price = findViewById(R.id.product_total);
        orderplace = findViewById(R.id.product_place_order);
        drawerLayout = findViewById(R.id.drawer);
        gridView = findViewById(R.id.products_recycle);
        recyclerViewBrand = findViewById(R.id.brands);
        user = findViewById(R.id.nav_header_imageView);
        User_Name = findViewById(R.id.nav_header_textView);
        linearLayout = findViewById(R.id.product_place_order_line);
        searchView = findViewById(R.id.product_search_view);
        cancel_order = findViewById(R.id.product_cancel_order);
        background = findViewById(R.id.background);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.bottomNavigationView.postDelayed(() -> {

            switch (item.getItemId()){
                case R.id.main:
                    set_ProductAdapter("");
                    ProductAdapter productAdapter1 = new ProductAdapter(context, products,background);
                    gridView.setAdapter(productAdapter1);
                    break;
                case R.id.user:
                    startActivity(new Intent(this, Profile.class));
                    this.finish();
                    break;
                case R.id.cart:
                    if(((Helper) context.getApplicationContext()).Prods.size()<1){
                        Toast.makeText(context,"Cart is Empty ",Toast.LENGTH_SHORT).show();
                        bottomNavigationView.getMenu().findItem(R.id.main).setChecked(true);
                    }
                    else {
                        startActivity(new Intent(this, Cart.class));
                        this.finish();
                    }
                    break;
            }

        }, 300);
        return true;
    }
    public void setToolbar(){
        setSupportActionBar(this.toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    public void fav(){

            ArrayList<Products> products1 = new ArrayList<>();
            ProductAdapter productAdapter1 = new ProductAdapter(context, products1,background);
            gridView.setAdapter(productAdapter1);
            toolbar.findViewById(R.id.action_favorite);
            DatabaseReference orderFev = FirebaseDatabase.getInstance().getReference().child("User/"+ Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()+"/Favourite");
            orderFev.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1: snapshot.getChildren()) {
                        favourite.add(snapshot1.getKey());
                        set_popularity(snapshot1.getKey(),snapshot1.getValue(String.class));
                    }
                    for (Products p: products){
                        if(favourite.contains(p.getTitle())){
                            products1.add(p);
                        }
                    }
                    productAdapter1.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            favourite.clear();
    }


    public void set_popularity(String id, String popularity){

        for(Products p: products){
            if(p.getId().equals(id)){
                p.setPopularity(popularity);
            }
        }
    }
    public void set_fav(){
        DatabaseReference orderFev = FirebaseDatabase.getInstance().getReference().child("User/"+ Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()+"/Favourite");
        orderFev.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    favourite.add(snapshot1.getKey());
                    set_popularity(snapshot1.getKey(),snapshot1.getValue(String.class));
                }
                Collections.sort(products, new Comparator<Products>() {
                    @Override
                    public int compare(Products o1, Products o2) {
                        int a = Integer.parseInt(o1.getPopularity().toString().trim());
                        int b = Integer.parseInt(o2.getPopularity().toString().trim());
                        return b-a;
                    }
                });
                Collections.sort(products1, new Comparator<Products>() {
                    @Override
                    public int compare(Products o1, Products o2) {
                        int a = Integer.parseInt(o1.getPopularity().toString().trim());
                        int b = Integer.parseInt(o2.getPopularity().toString().trim());
                        return b-a;
                    }
                });
                productAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



//    public void send_sms(String sms, String phone){
//        if(check_sms_list(phone,sms))
//            return;
//        SmsManager Sms= SmsManager.getDefault();
//        Sms.sendTextMessage(phone, null, sms, null,null);
//
//    }
//    public void New_order(){
//        FirebaseDatabase.getInstance().getReference().child("Order_Initiated").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
//
//                    String key = dataSnapshot.getKey(), value = dataSnapshot.getValue(String.class);
//
//                    if(key.equals("kamran"))
//                        continue;
//                    dataSnapshot.getRef().removeValue();
//                         sms(key,value);
//
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//    public int i=0;
//    public void sms(String Order_Id, String User_Id ) {
//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(), childrf = userRef.child("User").child(Objects.requireNonNull(User_Id)).child("number");
//        childrf.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String phone = (String) snapshot.getValue();
//                Phone = phone;
//                send_sms(Msg + Order_Id, Phone);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//
//
//
//        FirebaseDatabase.getInstance().getReference().child("User").child(User_Id).child("Order").child(Order_Id).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                Order order1 = snapshot.getValue(Order.class);
//                StringBuilder sm = new StringBuilder();
//                String[] n = order1.getNames().split(","), q = order1.getQuantity().split(",");
//                for (int i = 0; i < order1.getNames().split(",").length; i++) {
//                    sm.append(n[i]).append("  ").append(q[i]).append("\n");
//                }
//                send_sms(order1.getAddress() + "\n" + Phone + "\n" + sm, "7295078143");
//                send_sms(order1.getAddress() + "\n" + Phone + "\n" + sm, "7602256379");
//
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }
//    public void Cancelled_Order(){
//        FirebaseDatabase.getInstance().getReference().child("Order_Cancelled").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    String key = dataSnapshot.getKey(), value = dataSnapshot.getValue(String.class);
//                    if(key.equals("kamran"))
//                        continue;
//                    dataSnapshot.getRef().removeValue();
//                    sms_cancelled(key,value);
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//    public void sms_cancelled(String Order_Id, String User_Id){
//        FirebaseDatabase.getInstance().getReference().child("User").child(User_Id).child("number").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//               String phoneC = snapshot.getValue(String.class);
//               PhoneC = phoneC;
//               send_sms("Oops! you have cancelled order\nOrder_id   "+Order_Id,PhoneC);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        FirebaseDatabase.getInstance().getReference().child("User").child(User_Id).child("Order").child(Order_Id).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                Order order1 = snapshot.getValue(Order.class);
//                StringBuilder sm= new StringBuilder();
//                String[] n=order1.getNames().split(","),q=order1.getQuantity().split(",");
//                for (int i=0;i<order1.getNames().split(",").length;i++){
//                    sm.append(n[i]).append("  ").append(q[i]).append("\n");
//                }
//                send_sms(order1.getAddress()+"\n"+PhoneC+"\n"+sm + "Cancelled","7295078143");
//                send_sms(order1.getAddress()+"\n"+PhoneC+"\n"+sm+ "Cancelled","7602256379");
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }
//
//    public boolean check_sms_list(String number, String msg){
//        Uri mSmsinboxQueryUri = Uri.parse("content://sms/sent");
//        Cursor cursor1 = getContentResolver().query(mSmsinboxQueryUri,new String[] { "_id", "thread_id", "address", "person", "date","body", "type" }, null, null, null);
//        startManagingCursor(cursor1);
//        String[] columns = new String[] { "address", "person", "date", "body","type" };
//        if (cursor1.getCount() > 0) {
//            String count = Integer.toString(cursor1.getCount());
//            while (cursor1.moveToNext()){
//                String address = cursor1.getString(cursor1.getColumnIndex(columns[0]));
//                String body = cursor1.getString(cursor1.getColumnIndex(columns[3]));
//
//
//                Date date = new Date(cursor1.getLong(2));
//                Toast.makeText(context,date.toString(),Toast.LENGTH_LONG).show();
//
//                if(address.equalsIgnoreCase(number)){ //put your number here
//                   if(body.equals(msg)){
//                       return true;
//                   }
//
//                }
//
//
//            }
//        }
//        return false;
//    }
}