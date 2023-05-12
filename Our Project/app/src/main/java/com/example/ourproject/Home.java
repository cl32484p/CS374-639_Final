package com.example.ourproject;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private TextView balanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button calorieCalculatorButton = findViewById(R.id.calorieCalculatorButton);
        calorieCalculatorButton.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, CalorieCalculatorActivity.class);
            startActivity(intent);
        });

        Button offersButton = findViewById(R.id.offersButton);
        offersButton.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, Login.class);
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