package com.kamdan.dkexpress.grocery;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.client.ServerValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.model.CartItem;
import com.kamdan.dkexpress.grocery.model.MyProfile;
import com.kamdan.dkexpress.grocery.model.Order;
import com.kamdan.dkexpress.products.Helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Base64;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class OrderByImage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private ImageButton imageButton,back_button;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    Context context;
    Order order;
    ProgressBar progressBar;
    LottieAnimationView lottieAnimationView;
    RelativeLayout place_order_line, wholeline;
    LinearLayout Order_Line;

//
    TextView subtotl,delivery_charge,free_delivery_amount,total;
    Spinner spinner_slot_picker;
    Button Next, button,place_order;
    EditText fname,lname,phone,village, nearbyplace, pincode,address;
    LinearLayout order_place;
    String slot="";
    RelativeLayout relativeLayout;
    View newuserdata;
    View done,doneerror;
    ArrayList<String> Slots = new ArrayList<>(0);
    ArrayAdapter ad;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    String Image1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_by_image);
        Assignment();
        context=this;
        order = new Order();
        spinner_slot_picker.setOnItemSelectedListener(this);
        set_slot_picker();
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,groceryproduct.class));
                finish();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    cameraIntent.putExtra();
                someActivityResultLauncher.launch(cameraIntent);
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set_User_Info();
                wholeline.setVisibility(View.GONE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_order();
            }
        });
        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    wholeline.setVisibility(View.GONE);
                    Order_Line.setVisibility(View.VISIBLE);
                }
                else{

                    newuserdata.setVisibility(View.VISIBLE);
                }
            }
        });
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        if (result.getResultCode() == Activity.RESULT_OK && data != null) {
                            Bitmap photo = (Bitmap) data.getExtras().get("data");

                            imageView.setImageBitmap(photo);
                        }
                    }
                });

    }

    void Assignment(){
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
        imageView = findViewById(R.id.image_view);
        imageButton = findViewById(R.id.button2);
        back_button = findViewById(R.id.back_button);
        progressBar = findViewById(R.id.progress_bar);
        lottieAnimationView = findViewById(R.id.animation_id);
        place_order = findViewById(R.id.place_order);
        wholeline = findViewById(R.id.wholeline);
        Order_Line = findViewById(R.id.order_line);
        done = findViewById(R.id.done);
        doneerror = findViewById(R.id.doneerror);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            imageView.setImageBitmap(photo);
        }
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

        try {
            set_initials(spinner_slot_picker.getItemAtPosition(position).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void set_initials(String slot1) throws ParseException {
//
        // Start date
        final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
        Date d2ate = new Date();
        d2ate = new Date(d2ate.getTime() + MILLIS_IN_A_DAY);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date d1ate = new Date(System.currentTimeMillis());
        String time = formatter.format(d1ate).split("at")[1].split(":")[0];
        order.setDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        if(Integer.parseInt(time.trim())>=9 && slot1.equals("Morning Slot 8AM to 10AM")){
            order.setDelivery_date(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(d2ate));
        }
        if(Integer.parseInt(time.trim())>=13 && slot1.equals("After Noon Slot 1PM to 3PM")){
            order.setDelivery_date(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(d2ate));
        }
        if(Integer.parseInt(time.trim())>=16 && slot1.equals("Evening Slot 4PM to 6PM")){
            order.setDelivery_date(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(d2ate));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        String[] data = new String[10];
        data[0] = fname.getText().toString().trim();
        data[1] = lname.getText().toString().trim();
        data[2] = phone.getText().toString().trim();
        data[3] = village.getText().toString().trim();
        data[4] = nearbyplace.getText().toString().trim();
        data[5] = pincode.getText().toString().trim();
        data[6] = address.getText().toString().trim();
        data[7] = "₹100.00";
        data[8] = "12";
        data[9] = "123";
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
    }

    public void make_order(){
        progressBar.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Order_complete();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }
    public void Order_complete() throws ParseException {
        order.setUser(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        order.setPayment_method("Pay On Delivery");
        order.setTotal_amount("Not Available");
        order.setDue(order.getTotal_amount());
        order.setStatus("Initiated");
        order.setId(UUID.randomUUID().toString());
        order.setType("Image");
        order.setWallet_use("0.0");
        order.setSizes("Not Available");
        order.setQuantities("Not Available");
        order.setPrices("Not Available");
        order.setNames("Order by Photo");
        order.setIds("Not Available");
        order.setProducts(" ");
        set_initials("");
        StringBuilder temp = new StringBuilder();
        order.setTimestamp1(ServerValue.TIMESTAMP);
        if (isNetworkAvailable()){
            ((Helper) this.getApplicationContext()).clear();
            DatabaseReference db =  FirebaseDatabase.getInstance().getReference().child("Grocery").child("User").child(order.getUser()).child("Order");
            FirebaseDatabase.getInstance().getReference().child("Order_Initiated").child(order.getId()).setValue(order.getUser());
            db.child(order.getId()).setValue(order);
            progressBar.setVisibility(View.GONE);
            wholeline.setVisibility(View.GONE);
            done.setVisibility(View.VISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    done.setVisibility(View.GONE);
                    startActivity(new Intent(context,groceryproduct.class));
                    OrderByImage.this.finish();
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}