package com.kamdan.dkexpress.grocery;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.client.ServerValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kamdan.dkexpress.Cart;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.model.CartItem;
import com.kamdan.dkexpress.grocery.model.MyProfile;
import com.kamdan.dkexpress.grocery.model.Order;
import com.kamdan.dkexpress.products.Helper;
import com.kamdan.dkexpress.products.ProductActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class OederPlaced extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    TextView address1,delivery_charge,change,total_amount,date,gift,payment_method,time_slot,place_order,Payable_amount,delivery_date;
    LinearLayout place_order_line, wholeline;
    ProgressBar progressBar;
    Spinner wallet_money;
    View done,doneerror;
    View newuserdata;
    Button Next;ImageButton Back;
    EditText fname,lname,phone,village, nearbyplace, pincode,address;
    LottieAnimationView lottieAnimationView;
    Order order = new Order();
    String[] data;
    Context context;
    MyProfile m;
    int avb = 0;
    ArrayList<String> wallet_monees = new ArrayList<>(0);
    String Available_Balance;
    ArrayAdapter ad;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oeder_placed);
        Assignment();
        wallet_money.setOnItemSelectedListener(this);
        context =this;
        Available_Balance = ((Helper)context.getApplicationContext()).Available_Amount;
        if(Available_Balance==null)
            Available_Balance = "₹50.00";
        m = ((Helper)context.getApplicationContext()).myProfile;
        place_order_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                make_order();
            }
        });
        data = Objects.requireNonNull(this.getIntent().getExtras()).getStringArray("data");
        try {
            set_initials();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wholeline.setVisibility(View.GONE);
                newuserdata.setVisibility(View.VISIBLE);
                fname.setText(m.getFname());
                lname.setText(m.getLname());
                nearbyplace.setText(m.getLandmark());
                address.setText(m.getAddress());
                phone.setText(m.getPhone());
                village.setText(m.getVillage());
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    change_address();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wholeline.getVisibility()==View.VISIBLE)
                    finish();
                newuserdata.setVisibility(View.GONE);
                wholeline.setVisibility(View.VISIBLE);
            }
        });
        set_Payable_Amount(0);
        setWallet_money();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void set_initials() throws ParseException {
//
         // Start date
        final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
        Date d2ate = new Date();
        d2ate = new Date(d2ate.getTime() + MILLIS_IN_A_DAY);


//



        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date d1ate = new Date(System.currentTimeMillis());
        String time = formatter.format(d1ate).split("at")[1].split(":")[0];
        total_amount.setText(data[0]);
        delivery_charge.setText(data[1]);
        time_slot.setText(data[2]);
        delivery_date.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        date.setText(new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss", Locale.getDefault()).format(new Date()));
        if(Integer.parseInt(time.trim())>=9 && data[2].equals("Morning Slot 8AM to 10AM")){
            delivery_date.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(d2ate));
        }
        if(Integer.parseInt(time.trim())>=13 && data[2].equals("After Noon Slot 1PM to 3PM")){
            delivery_date.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(d2ate));
        }
        if(Integer.parseInt(time.trim())>=16 && data[2].equals("Evening Slot 4PM to 6PM")){
            delivery_date.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(d2ate));
        }
        String s = m.getVillage()+m.getLandmark();
        address1.setText(s);
    }

    public void Assignment(){
        address1 =findViewById(R.id.address2);
        total_amount = findViewById(R.id.total_amount);
        date = findViewById(R.id.date_time);
        payment_method = findViewById(R.id.payment_method);
        time_slot = findViewById(R.id.Deliverslot);
        delivery_charge = findViewById(R.id.delivery_charge);
        change = findViewById(R.id.addresschange);
        place_order = findViewById(R.id.place_order_text);
        place_order_line = findViewById(R.id.place_order);
        progressBar = findViewById(R.id.progress_bar);
        doneerror = findViewById(R.id.doneerror);
        done = findViewById(R.id.done);
        wholeline = findViewById(R.id.wholeline);
        lottieAnimationView = findViewById(R.id.animation_id);
        newuserdata = findViewById(R.id.neuuserdata);
        fname = newuserdata.findViewById(R.id.First_name);
        lname = newuserdata.findViewById(R.id.last_Name);
        phone = newuserdata.findViewById(R.id.mobile_no);
        village = newuserdata.findViewById(R.id.village);
        pincode = newuserdata.findViewById(R.id.pincode);
        address = newuserdata.findViewById(R.id.address);
        nearbyplace = newuserdata.findViewById(R.id.landmark);
        Next = newuserdata.findViewById(R.id.next);
        Back = findViewById(R.id.back_button);
        Payable_amount = findViewById(R.id.payable_amount);
        delivery_date = findViewById(R.id.deliver_date);
        wallet_money = findViewById(R.id.wallet_money_picker);

    }
    public void make_order(){
        progressBar.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            Order_complete();
            }
        }, 1000);
    }

    @SuppressLint("SetTextI18n")
    public void set_Payable_Amount(int a){
        int from_wallet = 0;
        avb = a;
        float b = Float.parseFloat(data[0].replace("₹",""));
        Payable_amount.setText("₹"+Float.toString(b-a));
        order.setWallet_use(Integer.toString(a));

    }

    public void Order_complete(){
        order.setUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
        order.setSlot(time_slot.getText().toString());
        order.setPayment_method("Pay On Delivery");
        order.setTotal_amount(Payable_amount.getText().toString().trim());
        order.setDue(order.getTotal_amount());
        order.setStatus("Initiated");
        order.setId(UUID.randomUUID().toString());
        order.setDelivery_date(delivery_date.getText().toString().trim());
        order.setDate(date.getText().toString().trim());
        StringBuilder names = new StringBuilder();
        StringBuilder ids = new StringBuilder();
        StringBuilder sizes = new StringBuilder();
        StringBuilder prices = new StringBuilder();
        StringBuilder quantities = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Grocery").child("Products");
        for(CartItem c :((Helper) this.getApplicationContext()).Items ){
            db1.child(c.getProductid()).child("popularity").setValue(Integer.toString(Integer.parseInt(c.getPopularity())+1));
            ids.append(c.getProductid()).append(",");
            names.append(c.getProductname()).append(",");
            if(c.getSize()==""){
                sizes.append(c.getSize()).append("  ,");
            }
            else
               sizes.append(c.getSize()).append(",");
            prices.append(c.getPrice()).append(",");
            quantities.append(c.getQuantity()).append(",");

              }
        order.setIds(ids.substring(0,ids.toString().length()-1));
        order.setNames(names.substring(0,names.toString().length()-1));
        order.setSizes(sizes.substring(0,sizes.toString().length()-1));
        order.setPrices(prices.substring(0,prices.toString().length()-1));
        order.setQuantities(quantities.substring(0,quantities.toString().length()-1));
        order.setTimestamp1(ServerValue.TIMESTAMP);
        if (isNetworkAvailable()){
            ((Helper) this.getApplicationContext()).clear();
            DatabaseReference db =  FirebaseDatabase.getInstance().getReference().child("Grocery").child("User").child(order.getUser()).child("Order");
            FirebaseDatabase.getInstance().getReference().child("Order_Initiated").child(order.getId()).setValue(order.getUser());
            db.child(order.getId()).setValue(order);
            String avbs = Float.toString(Float.parseFloat(Available_Balance.replace("₹",""))-(float)avb);
            FirebaseDatabase.getInstance().getReference().child("Grocery").child("User").child(order.getUser()).child("Wallet").child("Available_Balance").setValue("₹"+avbs);
            progressBar.setVisibility(View.GONE);
            wholeline.setVisibility(View.GONE);
            done.setVisibility(View.VISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    done.setVisibility(View.GONE);
                    final Intent mainIntent = new Intent(OederPlaced.this, groceryproduct.class);
                    OederPlaced.this.finish();
                }
            }, 1000);
        }
        else{
            wholeline.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            doneerror.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    recreate();
                }
            }, 1000);
            recreate();
        }
    }

    public void setWallet_money(){
        if (Float.parseFloat(data[0].replace("₹",""))>4999.00){
            for(int i=5;i<50;i++) {
                if(i<=Integer.parseInt(Available_Balance.replace(".00","").replace("₹","").replace(".0","")))
                 wallet_monees.add("₹"+Integer.toString(i));
            }
        }
        else {
            wallet_monees.add("Total Amount Must be Greater or equal to 5000.00");
        }

         ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item,wallet_monees);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        wallet_money.setAdapter(ad);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void change_address() throws ParseException {
        if(fname.getText().toString().trim().length()<4){
            Toast.makeText(this,"Enter Name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(phone.getText().toString().trim().length()==10||phone.getText().toString().trim().length()==12||phone.getText().toString().trim().length()==13)){
            Toast.makeText(this,"Enter Valid Number",Toast.LENGTH_SHORT).show();
            return;
        }
        if(village.getText().toString().trim().length()==0){
            Toast.makeText(this,"Enter Village",Toast.LENGTH_SHORT).show();
            return;
        }
        if(nearbyplace.getText().toString().trim().length()==0){
            Toast.makeText(this,"Enter Near By Place",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(pincode.getText().toString().trim().length()==6&&!pincode.getText().toString().startsWith("0"))){
            Toast.makeText(this,"Enter Near By Place",Toast.LENGTH_SHORT).show();
            return;
        }
        if(address.getText().toString().trim().length()==0){
            Toast.makeText(this,"Enter Address",Toast.LENGTH_SHORT).show();
            return;
        }
       DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Grocery").child("User").child(((Helper)this.getApplicationContext()).myProfile.getId());
       db.child("lname").setValue(lname.getText().toString().trim());
        db.child("fname").setValue(fname.getText().toString().trim());
        db.child("landmark").setValue(nearbyplace.getText().toString().trim());
        db.child("phone").setValue(phone.getText().toString().trim());
        db.child("pincode").setValue(pincode.getText().toString().trim());
        db.child("address").setValue(address.getText().toString().trim());
        db.child("village").setValue(village.getText().toString().trim());
        wholeline.setVisibility(View.VISIBLE);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ((Helper)context.getApplicationContext()).myProfile = snapshot.getValue(MyProfile.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        newuserdata.setVisibility(View.GONE);
        String s = village.getText().toString().trim()+","+nearbyplace.getText().toString().trim();
        address1.setText(s);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ad.notifyDataSetChanged();
        if(!wallet_monees.get(position).contains(" "))
          set_Payable_Amount(Integer.parseInt(wallet_monees.get(position).replace("₹","")));


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}