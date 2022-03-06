package com.kamdan.dkexpress.grocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.model.CartItem;
import com.kamdan.dkexpress.grocery.model.MyProfile;
import com.kamdan.dkexpress.grocery.model.Order;
import com.kamdan.dkexpress.grocery.model.Product;
import com.kamdan.dkexpress.products.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Cart extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    RecyclerView recyclerView_items,recyclerView_gifts;
    static TextView subtotl,delivery_charge,free_delivery_amount,total;
    Spinner spinner_slot_picker;
    Button Next, button;
    ImageButton back_button;
    TextView logedin;
    EditText fname,lname,phone,village, nearbyplace, pincode,address;
    LinearLayout order_place;
    Cart_adapter cart_adapter;
    Order order = new Order();
    Database database;
    String slot="";
    RelativeLayout relativeLayout;
    View newuserdata;
    ArrayList<String> Slots = new ArrayList<>(0);
    ArrayAdapter ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);
        Assignment();
        SetCart_Item();
        set_Gift();
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        spinner_slot_picker.setOnItemSelectedListener(this);
        set_slot_picker();
        setDelivery_charge();
        order_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ready_to_go();
            }
        });
        subtotl.setText("₹"+((Helper) this.getApplicationContext()).Totalprice());
        if(Float.parseFloat(((Helper) this.getApplicationContext()).Totalprice())<free_delivery_limit())
           total.setText("₹"+Float.toString(Float.parseFloat(((Helper) this.getApplicationContext()).Totalprice())+((Helper) this.getApplicationContext()).DELIVERY_CHARGE_NEAR));
        else
            total.setText("₹"+Float.toString(Float.parseFloat(((Helper) this.getApplicationContext()).Totalprice())));
        database = new Database(this);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set_User_Info();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ready_to_go();
            }
        });
        free_delivery_amount.setText("On orders over ₹"+Float.toString(free_delivery_limit()));

    }
    public void Assignment(){
        recyclerView_gifts = findViewById(R.id.gifts);
        recyclerView_items = findViewById(R.id.cart_item);
        subtotl = findViewById(R.id.cartsubtotal);
        delivery_charge = findViewById(R.id.delivery);
        free_delivery_amount = findViewById(R.id.free_delivery_minimum_amount);
        spinner_slot_picker = findViewById(R.id.slotpicker);
        order_place = findViewById(R.id.Continueline);
        total = findViewById(R.id.total);
        relativeLayout = findViewById(R.id.relativelayout);
        newuserdata = findViewById(R.id.neuuserdata);
        fname = newuserdata.findViewById(R.id.First_name);
        lname = newuserdata.findViewById(R.id.last_Name);
        phone = newuserdata.findViewById(R.id.mobile_no);
        village = newuserdata.findViewById(R.id.village);
        pincode = newuserdata.findViewById(R.id.pincode);
        address = newuserdata.findViewById(R.id.address);
        nearbyplace = newuserdata.findViewById(R.id.landmark);
        Next = newuserdata.findViewById(R.id.next);
        button = findViewById(R.id.Continue);
        back_button = findViewById(R.id.back_button);

    }
    public void SetCart_Item(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(Cart.this);
        cart_adapter = new Cart_adapter(((Helper) this.getApplicationContext()).Items, this);
        recyclerView_items.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView_items.addItemDecoration(dividerItemDecoration);
        recyclerView_items.setAdapter(cart_adapter);
    }
    public void setDelivery_charge(){
        if(Float.parseFloat(((Helper) this.getApplicationContext()).Totalprice())>=free_delivery_limit()){
            delivery_charge.setText("FREE");
            delivery_charge.setTextColor(Color.parseColor("#17A91D"));
        }
        else{
            delivery_charge.setText(Float.toString(((Helper)this.getApplicationContext()).DELIVERY_CHARGE_NEAR));
            delivery_charge.setTextColor(Color.RED);
            total.setText("₹"+Float.toString(Float.parseFloat(((Helper)this.getApplicationContext()).Totalprice().replace("₹",""))+((Helper)this.getApplicationContext()).DELIVERY_CHARGE_NEAR));
        }
    }
    public void set_Gift(){

    }
    public void set_slot_picker(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date d1ate = new Date(System.currentTimeMillis());
        String time = formatter.format(d1ate).split("at")[1].split(":")[0].trim();
        if(Integer.parseInt(time)>=16 || Integer.parseInt(time)<8){
            Slots.add("Morning Slot 8AM to 10AM");
            Slots.add("After Noon Slot 1PM to 3PM");
            Slots.add("Evening Slot 4PM to 6PM");
        }
        if(Integer.parseInt(time)>=8 && Integer.parseInt(time)<13){
            Slots.add("After Noon Slot 1PM to 3PM");
            Slots.add("Morning Slot 8AM to 10AM");
            Slots.add("Evening Slot 4PM to 6PM");
        }
        else if (Integer.parseInt(time)>=13 && Integer.parseInt(time)<16){
            Slots.add("Evening Slot 4PM to 6PM");
            Slots.add("After Noon Slot 1PM to 3PM");
            Slots.add("Morning Slot 8AM to 10AM");
        }

//        Slots.add("Instant Delivery");
        ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Slots);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spinner_slot_picker.setAdapter(ad);
        ad.notifyDataSetChanged();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        slot = spinner_slot_picker.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void setOrder_place(){
        try {
           order.setUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }catch (Exception e){

        }
    }

    public void add_gift(){
        Cursor cursor = database.fetch_product();
        if (cursor.moveToFirst()){
            do{
                String data = cursor.getString(cursor.getColumnIndex("Price"));
                String dat1 = cursor.getString(cursor.getColumnIndex("Size"));
                // do what ever you want here
            }while(cursor.moveToNext());
        }
        cursor.close();
    }

    @SuppressLint("DefaultLocale")
    public void ready_to_go (){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            String[] data = new String[4];
            data[0] = "₹"+String.format("%.2f", Float.parseFloat(total.getText().toString().trim().replace("₹","")));
            data[1] = delivery_charge.getText().toString().trim();
            data[2] = slot;
            startActivity(new Intent(this,OederPlaced.class).putExtra("data",data));
            finish();

        }
        else{
            relativeLayout.setVisibility(View.GONE);
            newuserdata.setVisibility(View.VISIBLE);
        }
    }

    public void Set_User_Info(){
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
        String[] data = new String[8];
        data[0] = fname.getText().toString().trim();
        data[1] = lname.getText().toString().trim();
        data[2] = phone.getText().toString().trim();
        data[3] = village.getText().toString().trim();
        data[4] = nearbyplace.getText().toString().trim();
        data[5] = pincode.getText().toString().trim();
        data[6] = address.getText().toString().trim();
        data[7] = "₹100.00";
        MyProfile m = ((Helper)this.getApplicationContext()).myProfile;
        m.setFname(data[0]);
        m.setLname(data[1]);
        m.setPhone(data[2]);
        m.setVillage(data[3]);
        m.setLandmark(data[4]);
        m.setPincode(data[5]);
        m.setAddress(data[6]);
        newuserdata.setVisibility(View.GONE);
        startActivity(new Intent(this,Registration.class).putExtra("data",data));
        finish();
    }

    public float free_delivery_limit(){
        float delivery_limit = ((Helper)this.getApplicationContext()).FREE_DELIVERY_MINIMUM_AMOUNT_I;
        for(CartItem item: ((Helper)this.getApplicationContext()).Items){
             if(item.getDeliver_charge()!=1){
                 delivery_limit = ((Helper)this.getApplicationContext()).FREE_DELIVERY_MINIMUM_AMOUNT_II;

             }
        }

        return delivery_limit;
    }

}