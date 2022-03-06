package com.kamdan.dkexpress.grocery;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kamdan.dkexpress.R;
import com.kamdan.dkexpress.grocery.adapter.voice_order_adpater;

import java.util.ArrayList;
import java.util.Locale;

public class Record_Order extends AppCompatActivity {

    ImageButton back;
    Context context;
    RecyclerView recyclerView;
    ArrayList<String> items = new ArrayList<>(0);
    com.kamdan.dkexpress.grocery.adapter.voice_order_adpater voice_order_adapter;
    ActivityResultLauncher<Intent> someActivityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_order);
        context = this;
        back = findViewById(R.id.back_button);
        recyclerView = findViewById(R.id.items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        voice_order_adapter = new com.kamdan.dkexpress.grocery.adapter.voice_order_adpater(items, new voice_order_adpater.ItemclickListner() {
            @Override
            public void onItemclick(int position) {
                items.remove(position);
                voice_order_adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(voice_order_adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,groceryproduct.class));
                finish();
            }
        });

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        if (result.getResultCode() == Activity.RESULT_OK && data != null) {
                            ArrayList<String> resul = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            items.add(resul.get(0));
                            voice_order_adapter.notifyDataSetChanged();
                        }
                    }
                });


    }

    public void getSpeechInput(View view) {
        Toast.makeText(context," Comming Soon ",Toast.LENGTH_LONG).show();


//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            someActivityResultLauncher.launch(intent);
//        } else {
//            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
//        }
    }

}