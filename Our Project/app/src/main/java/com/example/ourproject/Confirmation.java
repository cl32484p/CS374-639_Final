package com.example.ourproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Confirmation extends AppCompatActivity {

    FirebaseAuth mAuth;
    private Button home ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Intent intent = getIntent();
        Double balance = intent.getDoubleExtra("balance", 0.0);
        Double difference = intent.getDoubleExtra("difference", 0.0);
        Double dollarsPer = intent.getDoubleExtra("newDollarsPer", 0.0);
        Double differenceDPD = intent.getDoubleExtra("differenceDPD", 0.0);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        String date = dateFormat.format(today);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(userId);
         new UpdateDatabaseTask(mDatabase, today, difference, differenceDPD, balance, dollarsPer).execute();

         home = findViewById(R.id.button);

         home.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Confirmation.this, Home.class);
                 startActivity(intent);
             }
         });



    }
}