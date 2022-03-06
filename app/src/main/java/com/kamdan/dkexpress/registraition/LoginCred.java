package com.kamdan.dkexpress.registraition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.products.ProductActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginCred extends AppCompatActivity {
    private TextView Signup,Login, Password_Change;
    private View Register_layout, Login_layout;
    private Button Next,Logedin;
    private FirebaseAuth auth;
    EditText Name, Shop, Mobile, Location, Email, Password;
    Button location;
    int PLACE_PICKER_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cred);
        auth = FirebaseAuth.getInstance();
        assignment();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            startActivity(new Intent(this, ProductActivity.class).putExtra("brand", ""));
            this.finish();
        }
        Login_layout.setVisibility(View.GONE);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login_layout.setVisibility(View.GONE);
                Register_layout.setVisibility(View.VISIBLE);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login_layout.setVisibility(View.VISIBLE);
                Register_layout.setVisibility(View.GONE);
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().trim().equals("")) {
                    Toast.makeText(LoginCred.this, "Plz Enter Your Retailer Name ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Shop.getText().toString().trim().equals("")) {
                    Toast.makeText(LoginCred.this, "Plz Enter Your Shop Name ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Mobile.getText().toString().trim().equals("")||Mobile.getText().toString().trim().length()!=10) {
                    Toast.makeText(LoginCred.this, "Plz Enter Valid Mobile Number ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Location.getText().toString().trim().equals("")) {
                    Toast.makeText(LoginCred.this, "Plz Enter Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] data = new String[]{"","","","","",""};
                data[0] = Name.getText().toString().trim();
                data[1] = Shop.getText().toString().trim();
                data[2] = Mobile.getText().toString().trim();
                data[3] = Location.getText().toString().trim();
                Intent intent = new Intent(LoginCred.this, Authentication.class);
                intent.putExtra("data", data);
                startActivity(intent);
                LoginCred.this.finish();
            }
        });
        Logedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim(),password = Password.getText().toString().trim();
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginCred.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                Password.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginCred.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            startActivity(new Intent(LoginCred.this, ProductActivity.class).putExtra("brand",""));
                            finish();
                        }
                    }
                });



            }
        });
    }
    

    void assignment(){
        Register_layout = findViewById(R.id.include_register);
        Login_layout = findViewById(R.id.include_login);
        Login = Register_layout.findViewById(R.id.login);
        Signup = Login_layout.findViewById(R.id.signup);
        Name = Register_layout.findViewById(R.id.retailer_name);
        Shop = Register_layout.findViewById(R.id.shop_name);
        Mobile = Register_layout.findViewById(R.id.mobile_no);
        Location = Register_layout.findViewById(R.id.location);
        Email = Login_layout.findViewById(R.id.editTextEmail);
        Password = Login_layout.findViewById(R.id.editTextPassword);
        Password_Change = Login_layout.findViewById(R.id.forget_password);
        Next = Register_layout.findViewById(R.id.next);
        Logedin = Login_layout.findViewById(R.id.cirLoginButton);
    }
}