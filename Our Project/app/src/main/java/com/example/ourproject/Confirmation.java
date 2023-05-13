package com.example.ourproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Confirmation extends AppCompatActivity {

    FirebaseAuth mAuth;
    private Button home, history;
    private String balance, balanceDiff, dollarsPerDay, dollarsDiff, days;
    private TextView balanceView, balanceDiffView, dollarsPerView, dollarsDiffView, daysView;
    private Date end = new Date(123,4,16);
    private long time,dayLong;
    private double daysDbl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        balanceView = findViewById(R.id.balance);
        balanceDiffView = findViewById(R.id.balanceChange);
        dollarsPerView = findViewById(R.id.dollarsPer);
        dollarsDiffView = findViewById(R.id.dollarsPerChange);
        daysView = findViewById(R.id.daysLeft);
        home = findViewById(R.id.homeBtn);
        history = findViewById(R.id.historyButton);

        Intent intent = getIntent();
        Double balance = intent.getDoubleExtra("balance", 0.0);
        Double difference = intent.getDoubleExtra("difference", 0.0);
        Double dollarsPer = intent.getDoubleExtra("newDollarsPer", 0.0);
        Double differenceDPD = intent.getDoubleExtra("differenceDPD", 0.0);

        setView(balanceView, balance);
        setView(balanceDiffView, difference);
        setView(dollarsPerView, dollarsPer);
        setView(dollarsDiffView, differenceDPD);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        String date = dateFormat.format(today);
        time = end.getTime() - today.getTime();
        dayLong = (TimeUnit.DAYS.convert(time, TimeUnit.MILLISECONDS)); //Retrieve amount of days between todays date and end of semester
        daysDbl = (double)dayLong;
        setView(daysView, daysDbl);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(userId);
        new UpdateDatabaseTask(mDatabase, today, difference, differenceDPD, balance, dollarsPer).execute();


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Confirmation.this, Home.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Confirmation.this, History.class);
                startActivity(intent);
            }
        });




    }

    public static void setView(TextView view, Double dub) {

        String result = view.getText().toString();
        result = result + String.valueOf(dub);
        view.setText(result);
    }
}