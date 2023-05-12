package com.example.ourproject;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    private TextView balanceTextView, dollarsPerTextView;
    FirebaseAuth mAuth;
    Double balance, dollarsPer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        balanceTextView = findViewById(R.id.balanceTextView);
        dollarsPerTextView = findViewById(R.id.dollarsPer);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(userId);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                userClass user = dataSnapshot.getValue(userClass.class);
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

        Button transactionsButton = findViewById(R.id.transactionsButton);
        transactionsButton.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, editBalance.class);
            startActivity(intent);
        });

        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, CalorieCalculatorActivity.class);
            startActivity(intent);
        });
    }
}