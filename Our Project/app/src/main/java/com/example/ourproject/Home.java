package com.example.ourproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Home extends AppCompatActivity {

    private TextView balanceTextView, dollarsPerTextView, daysView;
    FirebaseAuth mAuth;
    Double balance, dollarsPer;
    private int check = 0;
    private String name, major;
    private Button history;
    private Date end = new Date(123,4,16);
    private long time,dayLong;
    private double daysDbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        // Set the title
        if (actionBar != null) {
            actionBar.setTitle("Welcome to Thrifty Bites");
        }

        balanceTextView = findViewById(R.id.balanceTextView);
        dollarsPerTextView = findViewById(R.id.dollarsPer);
        daysView = findViewById(R.id.daysLeft);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        history = findViewById(R.id.transactionsButton);
        Date today = new Date();
        time = end.getTime() - today.getTime();
        dayLong = (TimeUnit.DAYS.convert(time, TimeUnit.MILLISECONDS)); //Retrieve amount of days between todays date and end of semester
        daysDbl = (double)dayLong;
        setView(daysView, daysDbl);





        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(userId);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                userClass user = dataSnapshot.getValue(userClass.class);
                check = user.getTransactions();
                name = user.getName();
                major = user.getMajor();
                balance = user.getBalance();
                dollarsPer = user.getDollarsPer();
                balanceTextView.setText("Current Balance: $" + balance);
                dollarsPerTextView.setText("Dollars Per Day: $"+ dollarsPer);


                // Update the user's data in the database
                mDatabase.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database read error
            }
        });

        Button calorieCalculatorButton = findViewById(R.id.calorieCalculatorButton);
        calorieCalculatorButton.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, CalorieCalculatorActivity.class);
            startActivity(intent);
        });

        Button offersButton = findViewById(R.id.offersButton);
        offersButton.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, Offers.class);
            startActivity(intent);
        });

        Button editBalButton = findViewById(R.id.editBalance);
        editBalButton.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, editBalance.class);
            startActivity(intent);
        });

        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, UpdateProfile.class);
            intent.putExtra("name", name);
            intent.putExtra("major", major);
            startActivity(intent);
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(check == 0)
                    {
                        Toast.makeText(Home.this, "You must edit balance to view history", Toast.LENGTH_SHORT).show();
                    }
                    else{
                Intent intent = new Intent(Home.this, History.class);
                startActivity(intent); }

            }
        });


    }

    public static void setView(TextView view, Double dub) {

        String result = view.getText().toString();
        result = result + String.valueOf(dub);
        view.setText(result);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //creates the 3 dot menu on Main
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.mission:
                 intent = new Intent(Home.this, More.class);
                startActivity(intent);
                return true;
            case R.id.logOut:
                // Handle logout action
                FirebaseAuth.getInstance().signOut();
                 intent = new Intent(this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            case R.id.Resources:
                intent = new Intent(Home.this, Contact.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}