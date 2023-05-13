package com.example.ourproject;

/*
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
*/

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

                if(weightEditText.getText().toString().isEmpty() || heightEditText.getText().toString().isEmpty() || ageEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(CalorieCalculatorActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!activityLevelSpinner.getSelectedItem().toString().equals("Select Level"))
                        calculateCalorieIntake();
                    else
                        Toast.makeText(CalorieCalculatorActivity.this, "Please select a level", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static double calculateBMR(double weight, double height, int age) {
        double bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        return bmr;
    }

    public static double calculateTDEE(double bmr, int activityLevel) {
        double tdee = 0;
        switch (activityLevel) {
            case 1:
                tdee = bmr * 1.2;
                break;
            case 2:
                tdee = bmr * 1.375;
                break;
            case 3:
                tdee = bmr * 1.55;
                break;
            case 4:
                tdee = bmr * 1.725;
                break;
            default:
                System.out.println("Invalid activity level entered.");
                break;
        }
        return tdee;
    }



    private void calculateCalorieIntake() {
        // Get user input
        int weight = Integer.parseInt(weightEditText.getText().toString());
        int height = Integer.parseInt(heightEditText.getText().toString());
        int age = Integer.parseInt(ageEditText.getText().toString());
        int activityLevel = activityLevelSpinner.getSelectedItemPosition();

        // Calculate calorie intake
       //double bmr = 10 * weight + 6.25 * height - 5 * age + 5;
       double bmr = calculateBMR(weight,height,age);
        double calorieIntake = calculateTDEE(bmr,activityLevel);
       //   double calorieIntake = bmr * (activityLevel);

        // Display the result
        calorieIntakeTextView.setText(String.format("%.2f", calorieIntake));
    }
}