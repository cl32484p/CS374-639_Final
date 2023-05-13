package com.example.ourproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class Home extends AppCompatActivity {

    private TextView balanceTextView, dollarsPerTextView;
    FirebaseAuth mAuth;
    Double balance, dollarsPer;
    private Button history;
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
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        history = findViewById(R.id.historyButton);






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

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, History.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //creates the 3 dot menu on Main
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history:
                // Handle settings action
                return true;
            case R.id.logOut:
                // Handle logout action
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            case R.id.editProfile:
                intent = new Intent(Home.this, History.class);
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}