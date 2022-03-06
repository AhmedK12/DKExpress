package com.kamdan.dkexpress;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.google.android.material.textfield.TextInputLayout;
import com.kamdan.dkexpress.model.Order;
import com.kamdan.dkexpress.model.Product_Price;
import com.kamdan.dkexpress.model.Products;
import com.kamdan.dkexpress.products.Helper;
import com.kamdan.dkexpress.products.ProductActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cart extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "l" ;
    static TextView Total_Price;
    boolean inaddress = false;
    LinearLayout linearLayout;
    EditText name,nickname,streetadd,pincode1;
    Button Make_Order, Cancel_Order , Change_Add, Save;
    RecyclerView recyclerView;
    Context context;
    UUID uniqueKey;
    DatabaseReference orderRef,area;
    View done,doneerror;
    ProgressBar progressBar;
    String[] nams;
    boolean task = false;
    TextView Address;
    ArrayList<String> pincode = new ArrayList<>();
    LottieAnimationView lottieAnimationView;
    BottomNavigationView bottomNavigationView;
    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.getDefaultConfig().setPersistenceEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        context = this;
        Assignment();
        set_adapter();

        name.setText(((Helper) this.getApplicationContext()).user.getName());
        nickname.setText(((Helper) this.getApplicationContext()).user.getNumber());
        streetadd.setText(((Helper) this.getApplicationContext()).user.getLocation());
        pincode1.setText("841245");

        area = FirebaseDatabase.getInstance().getReference("available_in");
        area.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String st = snapshot.getValue(String.class);
                assert st != null;
                pincode.addAll(Arrays.asList(st.split(",")));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Make_Order.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                try {
                    if(!check_pin(Address.getText().toString().trim())){
                        linearLayout.setVisibility(View.VISIBLE);
                        done.setVisibility(View.GONE);
                        inaddress = true;
                        return;
                    }
                    else{
                        if(!pincode.contains(return_pin(Address.getText().toString().trim()))){
                            Toast.makeText(context,"Coming Soon in your Area",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        area = FirebaseDatabase.getInstance().getReference("/User");
                        FirebaseDatabase.getInstance().getReference().child("User/"+ Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("location").setValue(Address.getText().toString().trim());


                    }
                    if (Float.parseFloat(((Helper) getApplicationContext()).Total_Price())>999.0)
                         Place_order();
                    else{
                        Toast.makeText(context,"Minimum Order Must be ₹1000 and Above",Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException | ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        Total_Price.setText("₹"+((Helper) this.getApplicationContext()).Total_Price());
        Cancel_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cancel();
            }
        });
        this.bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        bottomNavigationView.getMenu().findItem(R.id.cart).setChecked(true);
        Address.setText(((Helper) context.getApplicationContext()).user.getLocation());
        Change_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                done.setVisibility(View.GONE);
                inaddress = true;
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().length()<5) {
                    Toast.makeText(context, "Enter Valid Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(nickname.getText().toString().trim().length()!=10) {
                    Toast.makeText(context, "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(streetadd.getText().toString().trim().length()<5) {
                    Toast.makeText(context, "Enter Valid Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!check_pin(pincode1.getText().toString().trim())) {
                    Toast.makeText(context, "Enter Valid Pin", Toast.LENGTH_SHORT).show();
                    return;
                }
                String Add = name.getText().toString().trim()+" "+ nickname.getText().toString().trim() + " "+ streetadd.getText().toString().trim()+" "+pincode1.getText().toString().trim();
                Address.setText(Add);
                linearLayout.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
                inaddress = false;
            }
        });
//        change_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(change_add.getText().toString().trim().equals("Change Add")) {
//                    change_add.setText("Save");
//                    Address.setVisibility(View.GONE);
//                    edit_address.setVisibility(View.VISIBLE);
//                    edit_address.setPlaceholderText( ((Helper) context.getApplicationContext()).Address);
//                }
//                else{
//                    if (Address.getText().toString().trim().equals("")&&check_pin(address.getText().toString().trim())) {
//                        Toast.makeText(Cart.this, "Plz Enter Shop Address with pincode ", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    change_add.setText("Change Add");
//                    Address.setText(address.getText().toString().replace(" ","\n").replace(",","\n"));
//                    Address.setVisibility(View.VISIBLE);
//                    edit_address.setVisibility(View.GONE);
//                }
//            }
//        });

    }

    public void Assignment(){
        Total_Price = findViewById(R.id.cart_total);
        Cancel_Order = findViewById(R.id.cart_cancel_order);
        Make_Order = findViewById(R.id.cart_place_order);
        recyclerView = findViewById(R.id.cartrecyclerview);
        bottomNavigationView = findViewById(R.id.cart_bottomnav);
        done = findViewById(R.id.done);
        linearLayout = findViewById(R.id.addressline);
        doneerror = findViewById(R.id.doneerror);
        progressBar = findViewById(R.id.progress_bar);
        Address = findViewById(R.id.Adress);
        Change_Add = findViewById(R.id.change_add);
        lottieAnimationView = findViewById(R.id.animation_id);
        name = findViewById(R.id.retailer_name);
        nickname = findViewById(R.id.shop_name);
        streetadd = findViewById(R.id.mobile_no);
        pincode1 = findViewById(R.id.location);
        Save = findViewById(R.id.save);
    }

    boolean check_pin(String s){
        String regex = "\\b[1-9]\\d{2}\\s?\\d{3}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(s);
        return m.find();
    }
    String return_pin(String s){
        String regex = "\\b[1-9]\\d{2}\\s?\\d{3}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(s);

        if(m.find()) {
            return m.group().replace(" ","");
        }
        return "";
    }

    public void Place_order() throws InterruptedException, ParseException {
        orderRef = FirebaseDatabase.getInstance().getReference().child("User/"+ Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()+"/Order");
        String names ="",quantity="",images="";
        for(Product_Price products1:((Helper) this.getApplicationContext()).Prods){
            names = names + products1.Product_Name+" "+products1.Size +",";
            images = images + products1.Product_Image+",";
            quantity = quantity + products1.Quantity + products1.type1+",";
        }

        Order order = new Order();
        order.setUserid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        uniqueKey = UUID.randomUUID();
        order.setId(uniqueKey.toString());
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd-MM-yy");
        Date dateobj = new Date();
        order.setDateorder(df.format(dateobj));
        order.setPrice(Total_Price.getText().toString().trim());
        order.setDatarrival(getNextDate(order.getDateorder()));
        order.setStatus("Initiated");
        order.setPstatus("Due");
        order.setPdue(Total_Price.getText().toString().trim());
        names = names.substring(0, names.length() - 1);
        quantity = quantity.substring(0, quantity.length() - 1);
        images = images.substring(0, images.length() - 1);
        order.setNames(names);
        order.setQuantity(quantity);
        order.setImage(images);
        order.setTimestamp1(ServerValue.TIMESTAMP);
        nams = names.split(",");
        String[] qnt = quantity.split(",");
        order.setAddress(Address.getText().toString());
        lottieAnimationView.playAnimation();
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                make_order(order);
            }
        }, 1000);


    }

    public void set_adapter(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        cartAdapter cadapter = new cartAdapter(context,(Helper) context.getApplicationContext());
        recyclerView.setAdapter(cadapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.bottomNavigationView.postDelayed(() -> {

            switch (item.getItemId()){
                case R.id.main:
                    startActivity(new Intent(this, ProductActivity.class).putExtra("brand",""));
                    this.finish();
                    break;
                case R.id.user:
                    startActivity(new Intent(this, Profile.class));
                    this.finish();
                    break;
                case R.id.cart:
                    break;
            }

        }, 300);
        return true;
    }

    public void cancel(){
        ((Helper) context.getApplicationContext()).clear();
        startActivity(new Intent(this,ProductActivity.class).putExtra("brand",""));
        this.finish();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void make_order(Order order){
        if (isNetworkAvailable()){
            done.setVisibility(View.GONE);
            order.setUserid(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
            orderRef.child(uniqueKey.toString()).setValue(order);
            FirebaseDatabase.getInstance().getReference("Order_Initiated/").child(order.getId()).setValue(order.getUserid());
            DatabaseReference orderFev = FirebaseDatabase.getInstance().getReference().child("User/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + "/Favourite");

//            for (String id : nams) {
//                    if(id.contains("."))
//                        continue;
//                    orderFev.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.hasChild(id)) {
//                                String value = Objects.requireNonNull(snapshot.child(id).getValue()).toString();
//                                orderFev.child(id).setValue(Integer.toString(Integer.parseInt(value) + 1));
//                            } else
//                                orderFev.child(id).setValue(Integer.toString(1));
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//                }

            for (Product_Price p : ((Helper) this.getApplicationContext()).Prods){
                orderFev.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(p.Product_Id)) {
                            String value = Objects.requireNonNull(snapshot.child(p.Product_Id).getValue()).toString();
                            orderFev.child(p.Product_Id).setValue(Integer.toString(Integer.parseInt(value) + 1));
                        } else
                            orderFev.child(p.Product_Id).setValue(Integer.toString(1));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            ((Helper) this.getApplicationContext()).clear();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Intent mainIntent = new Intent(context, ProductActivity.class).putExtra("brand", "");
                    Cart.this.startActivity(mainIntent);
                    Cart.this.finish();
                }
            }, 1000);

        }
        else{
            done.setVisibility(View.GONE);
            doneerror.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Intent mainIntent = new Intent(context, ProductActivity.class).putExtra("brand", "");
                    Cart.this.startActivity(mainIntent);
                    Cart.this.finish();
                }
            }, 1000);
        }
    }

    public static String getNextDate(String  curDate) throws ParseException {
        final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        final Date date = format.parse(curDate);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return format.format(calendar.getTime());
    }

    @Override
    public void onBackPressed(){
        if (!inaddress){
            startActivity(new Intent(this, ProductActivity.class));
            this.finish();
        }
        else{
            linearLayout.setVisibility(View.GONE);
            done.setVisibility(View.VISIBLE);
            inaddress = true;
            name.setText("");
            nickname.setText("");
            streetadd.setText("");
            pincode1.setText("");
        }

    }





}