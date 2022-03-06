package com.kamdan.dkexpress.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.kamdan.dkexpress.grocery.model.MyProfile;
import com.kamdan.dkexpress.model.User;
import com.kamdan.dkexpress.products.ProductActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.kamdan.dkexpress.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Registration extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private String[] data;
    // declaring a const int value which we
    // will be using in Firebase auth.
    public static final int RC_SIGN_IN = 1;

    // creating an auth listener for our Firebase auth
    private FirebaseAuth.AuthStateListener mAuthStateListner;

    // below is the list which we have created in which
    // we can add the authentication which we have to
    // display inside our app.
    List<AuthUI.IdpConfig> providers = Arrays.asList(

            // below is the line for adding
            // email and password authentication.
            new AuthUI.IdpConfig.EmailBuilder().build(),

            // below line is used for adding google
            // authentication builder in our app.
            new AuthUI.IdpConfig.GoogleBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        // below line is for getting instance
        // for our firebase auth
        data = Objects.requireNonNull(this.getIntent().getExtras()).getStringArray("data");
        mFirebaseAuth = FirebaseAuth.getInstance();

        // below line is used for calling auth listener
        // for oue Firebase authentication.
        mAuthStateListner = new FirebaseAuth.AuthStateListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                // we are calling method for on authentication state changed.
                // below line is used for getting current user which is
                // authenticated previously.
                FirebaseUser user = firebaseAuth.getCurrentUser();

                // checking if the user
                // is null or not.
                if (user != null) {
                    // if the user is already authenticated then we will
                    // redirect our user to next screen which is our home screen.
                    // we are redirecting to new screen via an intent.
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Grocery").child("User");
                    MyProfile user1 = new MyProfile();
                    user1.setFname(data[0]);
                    user1.setLname(data[1]);
                    user1.setPhone(data[2]);
                    user1.setVillage(data[3]);
                    user1.setLandmark(data[4]);
                    user1.setPincode(data[5]);
                    user1.setAddress(data[6]);
                    user1.setId(user.getUid());
                    try {
                        user1.setImage(Objects.requireNonNull(user.getPhotoUrl()).toString());
                    }catch (Exception ignored){
                    }
                    rootRef.child(user.getUid()).setValue(user1);
                    if(data.length==8){
                        rootRef.child(user.getUid()).child("Wallet").child("Available_Balance").setValue("₹100.00");
                        startActivity(new Intent(Registration.this,Cart.class).putExtra("form","registration"));
                        finish();
                    }
                    if(data.length==9){
                        rootRef.child(user.getUid()).child("Wallet").child("Available_Balance").setValue("₹100.00");
                        startActivity(new Intent(Registration.this,Profile.class));
                        finish();
                    }
                    if(data.length==10){
                        rootRef.child(user.getUid()).child("Wallet").child("Available_Balance").setValue("₹100.00");
                        finish();
                    }

                } else {
                    // this method is called when our
                    // user is not authenticated previously.
                    startActivityForResult(
                            // below line is used for getting
                            // our authentication instance.
                            AuthUI.getInstance()
                                    // below line is used to
                                    // create our sign in intent
                                    .createSignInIntentBuilder()

                                    // below line is used for adding smart
                                    // lock for our authentication.
                                    // smart lock is used to check if the user
                                    // is authentication through different devices.
                                    // currently we are disabling it.
                                    .setIsSmartLockEnabled(false)

                                    // we are adding different login providers which
                                    // we have mentioned above in our list.
                                    // we can add more providers according to our
                                    // requirement which are available in firebase.
                                    .setAvailableProviders(providers)

                                    // below line is for customizing our theme for
                                    // login screen and set logo method is used for
                                    // adding logo for our login page.
                                    .setLogo(R.drawable.dkmodified).setTheme(R.style.Theme)

                                    // after setting our theme and logo
                                    // we are calling a build() method
                                    // to build our login screen.
                                    .build(),
                            // and lastly we are passing our const
                            // integer which is declared above.
                            RC_SIGN_IN
                    );
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        // we are calling our auth
        // listener method on app resume.
        mFirebaseAuth.addAuthStateListener(mAuthStateListner);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // here we are calling remove auth
        // listener method on stop.
        mFirebaseAuth.removeAuthStateListener(mAuthStateListner);
    }

}