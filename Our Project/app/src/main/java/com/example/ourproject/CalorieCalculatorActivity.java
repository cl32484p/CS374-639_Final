package com.example.ourproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalorieCalculatorActivity extends AppCompatActivity {

    private EditText weightEditText;
    private EditText heightEditText;
    private EditText ageEditText;
    private Spinner activityLevelSpinner;
    private TextView calorieIntakeTextView;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);

        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);
        ageEditText = findViewById(R.id.ageEditText);
        activityLevelSpinner = findViewById(R.id.activityLevelSpinner);
        calorieIntakeTextView = findViewById(R.id.calorieIntakeTextView);
        calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCalorieIntake();
            }
        });
    }

    private void calculateCalorieIntake() {
        // Get user input
        int weight = Integer.parseInt(weightEditText.getText().toString());
        int height = Integer.parseInt(heightEditText.getText().toString());
        int age = Integer.parseInt(ageEditText.getText().toString());
        int activityLevel = activityLevelSpinner.getSelectedItemPosition();

        // Calculate calorie intake
        double bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        double calorieIntake = bmr * (activityLevel);

        // Display the result
        calorieIntakeTextView.setText(String.format("%.2f", calorieIntake));
    }
}
