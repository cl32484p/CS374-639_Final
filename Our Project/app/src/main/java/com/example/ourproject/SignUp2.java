package com.example.ourproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp2 extends AppCompatActivity {

private EditText major;
    private RadioGroup campus;
    private Spinner year;
    private RadioGroup residency;
    private Spinner mealPlan;
    private Button create;
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

// Disable UI elements until the user has made a selection
        campus.setEnabled(false);
        year.setEnabled(false);
        mealPlan.setEnabled(false);
        create.setEnabled(false);

        // Set up the residency radio group to enable/disable the campus radio group
        residency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                campus.setEnabled(true);
                year.setEnabled(false);
                mealPlan.setEnabled(false);
                create.setEnabled(false);
            }
        });

// Set up the campus radio group to enable/disable the year spinner
        campus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                year.setEnabled(true);
                mealPlan.setEnabled(false);
                create.setEnabled(false);

            }
        });

        // Set up the year spinner to enable the meal plan spinner
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mealPlan.setEnabled(true);
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
                String userMajor = major.getText().toString();
                String userResidency = ((RadioButton)findViewById(residency.getCheckedRadioButtonId())).getText().toString();
                String userCampus = ((RadioButton)findViewById(campus.getCheckedRadioButtonId())).getText().toString();
                String userYear = year.getSelectedItem().toString();
                String userMealPlan = mealPlan.getSelectedItem().toString();

                //retrieve userID
                mAuth= FirebaseAuth.getInstance();
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



                /* Retrieve Intent
                Bundle mBundle = new Bundle();
                mBundle = getIntent().getExtras();
                String name = mBundle.getString("name");
                String email = mBundle.getString("email");
                String password = mBundle.getString("password");

                //Create new user
               userClass newUser = new userClass(name, email, password, userMajor, userMealPlan, userResidency, userCampus, userYear);
            */
            }

        });


    } //end of onCreate
}