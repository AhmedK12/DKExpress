package com.kamdan.dkexpress.cakes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.kamdan.dkexpress.R;
import android.os.Bundle;


public class Cakes_main extends AppCompatActivity {
    boolean cake = false;
    private ViewPager viewPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cakes_main);
    }
}