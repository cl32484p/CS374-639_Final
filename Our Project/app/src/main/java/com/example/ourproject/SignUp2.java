package com.example.ourproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp2 extends AppCompatActivity {

private EditText major;
    private RadioGroup campus;
    private Spinner year;
    private RadioGroup residency;
    private Spinner mealPlan;
    private Button create;
    private Boolean resCheck = false;
    private Boolean campCheck = false;
    private int balanceCheck = 0;
    private double balance, daily;
    private int [] westBalance, nycBalance, townBalance, commBalance, gradBalance;
    private int [] westDaily, nycDaily, townDaily, commDaily, gradDaily;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        major = findViewById(R.id.edit_major);
        campus = findViewById(R.id.group_campus);
        year = findViewById(R.id.spinner_year);
        residency = findViewById(R.id.group_residency);
        mealPlan = findViewById(R.id.spinner_mealplan);
        create = findViewById(R.id.newUserBtn);
        westBalance = getResources().getIntArray(R.array.west_values);
        westDaily = getResources().getIntArray(R.array.west_daily);
        nycBalance = getResources().getIntArray(R.array.nyc_values);
        nycDaily = getResources().getIntArray(R.array.nyc_daily);
        townBalance = getResources().getIntArray(R.array.town_values);
        townDaily = getResources().getIntArray(R.array.town_daily);
        commBalance = getResources().getIntArray(R.array.comm_values);
        commDaily = getResources().getIntArray(R.array.comm_daily);
        gradBalance = getResources().getIntArray(R.array.grad_values);
        gradDaily = getResources().getIntArray(R.array.grad_daily);

/*Disable UI elements until the user has made a selection
        campus.setEnabled(false);
        year.setEnabled(false);
        mealPlan.setEnabled(false);
        create.setEnabled(false); */
        create.setEnabled(false);

        // Set up the residency radio group to enable/disable the campus radio group
        residency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selected = radioButton.getText().toString();
                String[] plans = null;

               //If a user is a commuter, only display commuter meal plan options
                if(selected.equals("Commuter")) {
                    resCheck = true;
                    plans = getResources().getStringArray(R.array.commuter_plans);
                    balanceCheck = 5;
                }
                else if(selected.equals("Resident")) {
                    resCheck = false;
                    plans = getResources().getStringArray(R.array.default_plans);
                    if(balanceCheck == 5)
                        balanceCheck=0;

                    /*if(campCheck)
                    {
                        if (checkedId == R.id.button_resident) {
                        campus.clearCheck();
                        }
                    } */

                }

                if(plans != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUp2.this, android.R.layout.simple_spinner_dropdown_item, plans);
                    mealPlan.setAdapter(adapter);
                }

                  /*  campus.setEnabled(true);
                    year.setEnabled(false);
                mealPlan.setEnabled(false);
                    */
                create.setEnabled(false);


        }});

// Set up the campus radio group to enable/disable the year spinner
        campus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               // mealPlan.setEnabled(false);
                RadioButton radioButton = findViewById(checkedId);
                String selected = radioButton.getText().toString();
                String[] plans = null;




                //If user is a resident, display meal plan options based on campus
                if(!resCheck)
                {
                    if(selected.equals("Westchester")) {
                        plans = getResources().getStringArray(R.array.west_plans);
                        campCheck = true;
                        balanceCheck = 1;
                    }
                    else if(selected.equals("Westchester(Townhouses)")) {
                        plans = getResources().getStringArray(R.array.townhouse_plans);
                        campCheck = true;
                        balanceCheck = 2;
                    }
                    else if(selected.equals("Grad or Law")){
                        plans = getResources().getStringArray(R.array.grad_plans);
                        campCheck = true;
                        balanceCheck = 3;
                    }

                    else if(selected.equals("NYC")){
                        plans = getResources().getStringArray(R.array.nyc_plans);
                        campCheck = true;
                        balanceCheck = 4;
                    }

                    if(plans != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUp2.this, android.R.layout.simple_spinner_dropdown_item, plans);
                        mealPlan.setAdapter(adapter);
                    }
                }


               // year.setEnabled(true);

                create.setEnabled(false);

            }
        });

        // Set up the year spinner to enable the meal plan spinner
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // mealPlan.setEnabled(true);
                create.setEnabled(false);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

 //et up the meal plan spinner to enable the create user button
        mealPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                create.setEnabled(true);
                switch(balanceCheck) {
                    case 1:
                            balance = westBalance[position];
                            daily = westDaily[position];
                        break;

                    case 2:
                        balance = townBalance[position];
                        daily = townDaily[position];
                        break;

                    case 3:
                        balance = gradBalance[position];
                        daily = gradDaily[position];
                        break;

                    case 4:
                        balance = nycBalance[position];
                        daily = nycDaily[position];
                        break;

                    case 5:
                        balance = commBalance[position];
                        daily = commDaily[position];
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


        //Profile will be completed and updated below.
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the user input
                if(mealPlan.getSelectedItem().toString().equals("Select Plan") || mealPlan.getSelectedItem().toString().equals("Select or Reselect Campus for more options!")||major.getText().toString().isEmpty() || campus.getCheckedRadioButtonId() == -1 || residency.getCheckedRadioButtonId() == -1 || year.getSelectedItem().toString().equals("Select Year")){
                    Toast.makeText(SignUp2.this, "Please fill every field", Toast.LENGTH_SHORT).show();
                }

                else
                {
                String userMajor = major.getText().toString();
                String userResidency = ((RadioButton) findViewById(residency.getCheckedRadioButtonId())).getText().toString();
                String userCampus = ((RadioButton) findViewById(campus.getCheckedRadioButtonId())).getText().toString();
                String userYear = year.getSelectedItem().toString();
                String userMealPlan = mealPlan.getSelectedItem().toString();


                //retrieve userID
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String userId = user.getUid();


                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(userId);
                // mDatabase.child("users").child(userId).setValue(newUser);

                //set values for users profile in the database
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        userClass user = dataSnapshot.getValue(userClass.class);
                        user.setMajor(userMajor);
                        user.setResidency(userResidency);
                        user.setCampus(userCampus);
                        user.setYearLevel(userYear);
                        user.setMealplan(userMealPlan);
                        user.setDollarsPer(Double.valueOf(daily));
                        user.setBalance(Double.valueOf(balance));


                        // Update the user's data in the database
                        mDatabase.setValue(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle database read error
                    }
                });

                Intent intent = new Intent(SignUp2.this, MainActivity.class);
                startActivity(intent);

            }

                /* Retrieve Intent
                Bundle mBundle = new Bundle();
                mBundle = getIntent().getExtras();
                String name = mBundle.getString("name");
                String email = mBundle.getString("email");
                String password = mBundle.getString("password");

                //Create new user
               userClass newUser = new userClass(name, email, password, userMajor, userMealPlan, userResidency, userCampus, userYear);
            */
            } //end of onClick

        });


    } //end of onCreate







}