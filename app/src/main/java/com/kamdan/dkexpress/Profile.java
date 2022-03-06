package com.kamdan.dkexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kamdan.dkexpress.grocery.Registration;
import com.kamdan.dkexpress.grocery.model.MyProfile;
import com.kamdan.dkexpress.model.User;
import com.kamdan.dkexpress.products.Helper;
import com.kamdan.dkexpress.products.ProductActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity{
    TextView fname,lname,mobile,village_mohalla,nearbyplace,address,pincode,change;
    RelativeLayout relativeLayout;
    private CircleImageView User_Image;
    Context context;
    View newuserdata;
    ImageButton back;
    EditText efname,elname,phone,village, enearbyplace, epincode,eaddress;
    Button Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_profile);
        assignment();
        try {
//            Glide.with(this).load(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhotoUrl()).toString()).error(R.drawable.logo4).into(User_Image);
        }catch (Exception e){
        }

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Grocery/User");
        userRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MyProfile user = snapshot.getValue(MyProfile.class);
                assert user != null;
                fname.setText(user.getFname());
                efname.setText(user.getFname());
                lname.setText(user.getLname());
                elname.setText(user.getLname());
                mobile.setText(user.getPhone());
                phone.setText(user.getPhone());
                village_mohalla.setText(user.getVillage());
                village.setText(user.getVillage());
                nearbyplace.setText(user.getLandmark());
                enearbyplace.setText(user.getLandmark());
                address.setText(user.getAddress());
                eaddress.setText(user.getAddress());
                pincode.setText(user.getPincode());
                epincode.setText(user.getPincode());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newuserdata.getVisibility()==View.VISIBLE){
                    newuserdata.setVisibility(View.GONE);
                }
                else{
                    finish();
                }
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newuserdata.setVisibility(View.VISIBLE);
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set_User_Info();
            }
        });
    }
    public void assignment(){
        fname = findViewById(R.id.user_name_first);
        lname = findViewById(R.id.user_name_last);
        mobile = findViewById(R.id.user_mobile_no);
        village_mohalla = findViewById(R.id.village_mohalla_name);
        nearbyplace = findViewById(R.id.nearbyplace);
        User_Image = findViewById(R.id.user_image);
        relativeLayout = findViewById(R.id.back_to_home);
        address = findViewById(R.id.user_address);
        pincode = findViewById(R.id.user_pincode);
        relativeLayout = findViewById(R.id.back_to_home);
        change = findViewById(R.id.change_address);
        newuserdata = findViewById(R.id.newuserdata);
        efname = newuserdata.findViewById(R.id.First_name);
        elname = newuserdata.findViewById(R.id.last_Name);
        phone = newuserdata.findViewById(R.id.mobile_no);
        village = newuserdata.findViewById(R.id.village);
        epincode = newuserdata.findViewById(R.id.pincode);
        eaddress = newuserdata.findViewById(R.id.address);
        enearbyplace = newuserdata.findViewById(R.id.landmark);
        Next = newuserdata.findViewById(R.id.next);
        back = findViewById(R.id.back_button);

    }


    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, com.kamdan.dkexpress.grocery.Profile.class));
        this.finish();
    }

    public void Set_User_Info(){
        if(efname.getText().toString().trim().length()<4){
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
        if(enearbyplace.getText().toString().trim().length()==0){
            Toast.makeText(this,"Enter Near By Place",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(epincode.getText().toString().trim().length()==6&&!pincode.getText().toString().startsWith("0"))){
            Toast.makeText(this,"Enter Near By Place",Toast.LENGTH_SHORT).show();
            return;
        }
        if(eaddress.getText().toString().trim().length()==0){
            Toast.makeText(this,"Enter Address",Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Grocery").child("User").child(((Helper)this.getApplicationContext()).myProfile.getId());
        db.child("lname").setValue(elname.getText().toString().trim());
        db.child("fname").setValue(efname.getText().toString().trim());
        db.child("landmark").setValue(enearbyplace.getText().toString().trim());
        db.child("phone").setValue(phone.getText().toString().trim());
        db.child("pincode").setValue(epincode.getText().toString().trim());
        db.child("address").setValue(eaddress.getText().toString().trim());
        db.child("village").setValue(village.getText().toString().trim());
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
    }
}