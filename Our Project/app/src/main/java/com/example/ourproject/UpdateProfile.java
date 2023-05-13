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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UpdateProfile extends AppCompatActivity {

    private EditText editMajor;
    private EditText editName, editPassword, editEmail;
    private RadioGroup radioGroupResidency;
    private RadioGroup radioGroupCampus;
    private Spinner spinnerYear;
    private Spinner spinnerMealPlan;
    private Button updateButton;
    private boolean updated = false;
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
    DatabaseReference mDatabase;
private FirebaseUser current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        Intent intent = getIntent();
        String intName = intent.getStringExtra("name");
        String intMajor = intent.getStringExtra("major");

        editName = findViewById(R.id.edit_name);
        // editEmail = findViewById(R.id.edit_email);
        //  editPassword = findViewById(R.id.edit_pass);
        editMajor = findViewById(R.id.edit_major);
        editName.setText(intName);
        editMajor.setText(intMajor);
        //radioGroupResidency = findViewById(R.id.group_residency);
       // radioGroupCampus = findViewById(R.id.group_campus);
        //spinnerYear = findViewById(R.id.spinner_year);
       // spinnerMealPlan = findViewById(R.id.spinner_mealplan);
        updateButton = findViewById(R.id.save);


      //  major = findViewById(R.id.edit_major);
       campus = findViewById(R.id.group_campus);
        year = findViewById(R.id.spinner_year);
        residency = findViewById(R.id.group_residency);
      //  mealPlan = findViewById(R.id.spinner_mealplan);
        create = findViewById(R.id.save);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateProfile.this, android.R.layout.simple_spinner_dropdown_item, plans);
                 //   mealPlan.setAdapter(adapter);
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



/*
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateProfile.this, android.R.layout.simple_spinner_dropdown_item, plans);
                        mealPlan.setAdapter(adapter);
                    }
                } */


                // year.setEnabled(true);

                create.setEnabled(false);

            }
        });

        // Set up the year spinner to enable the meal plan spinner
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // mealPlan.setEnabled(true);
                create.setEnabled(true);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        //et up the meal plan spinner to enable the create user button
      /*  mealPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                create.setEnabled(true);
               /* switch(balanceCheck) {
                    case 1:
                      //  balance = westBalance[position];
                      //  daily = westDaily[position];
                        break;

                    case 2:
                     //   balance = townBalance[position];
                      //  daily = townDaily[position];
                        break;

                    case 3:
                      //  balance = gradBalance[position];
                      //  daily = gradDaily[position];
                        break;

                    case 4:
                      //  balance = nycBalance[position];
                       // daily = nycDaily[position];
                        break;

                    case 5:
                      //  balance = commBalance[position];
                      //  daily = commDaily[position];
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        }); */


        //Profile will be completed and updated below.
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the user input
                if(editMajor.getText().toString().isEmpty() || editName.getText().toString().isEmpty()|| campus.getCheckedRadioButtonId() == -1 || residency.getCheckedRadioButtonId() == -1 || year.getSelectedItem().toString().equals("Select Year")){
                    Toast.makeText(UpdateProfile.this, "Please fill every field", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    String userName = editName.getText().toString();
                    String userMajor = editMajor.getText().toString();
                    String userResidency = ((RadioButton) findViewById(residency.getCheckedRadioButtonId())).getText().toString();
                    String userCampus = ((RadioButton) findViewById(campus.getCheckedRadioButtonId())).getText().toString();
                    String userYear = year.getSelectedItem().toString();
                   // String userMealPlan = mealPlan.getSelectedItem().toString();


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
                            user.setName(userName);
                            user.setMajor(userMajor);
                            user.setResidency(userResidency);
                            user.setCampus(userCampus);
                            user.setYearLevel(userYear);
                           // user.setMealplan(userMealPlan);
                          //  user.setDollarsPer(Double.valueOf(daily));
                         //   user.setBalance(Double.valueOf(balance));


                            // Update the user's data in the database
                            mDatabase.setValue(user);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle database read error
                        }
                    });

                    Intent intent = new Intent(UpdateProfile.this, Home.class);
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










      /*  mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        current = mAuth.getCurrentUser();
        String userId = current.getUid();
        DatabaseReference currentUserRef = mDatabase.child(userId);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }
//Framework of code below generated via AI: updates profile after user edits but also prevents users from sending null object to database when there are no changes














    //name,major,campus,residency,year, mealplan

    private void updateProfile() {

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();


            DatabaseReference currentUserRef = mDatabase.child(userId);
           // boolean updated = false;

            // Listen for changes in the Spinner
            spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String year = spinnerYear.getSelectedItem().toString();
                    currentUserRef.child("yearLevel").setValue(year);
                    updated = true;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            });

// Listen for changes in the RadioGroup
            radioGroupResidency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String residency = ((RadioButton) findViewById(checkedId)).getText().toString();
                    currentUserRef.child("residency").setValue(residency);
                    updated = true;
                }
            });

            radioGroupCampus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String campus = ((RadioButton) findViewById(checkedId)).getText().toString();
                    currentUserRef.child("campus").setValue(campus);
                    updated = true;
                }
            });

            // Listen for changes in the Spinner
            spinnerMealPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String mealPlan = spinnerMealPlan.getSelectedItem().toString();
                    currentUserRef.child("mealplan").setValue(mealPlan);
                    updated = true;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            });






            if (editName.hasFocus()) {
                String name = editName.getText().toString();
                currentUserRef.child("name").setValue(name);
                updated = true;
            }


            if (editMajor.hasFocus()) {
                String major = editMajor.getText().toString();
                currentUserRef.child("major").setValue(major);
                updated = true;
            }



            if (updated) {
                currentUserRef
                        .updateChildren(new HashMap<String, Object>())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                    finish(); // Close the activity after successful update
                                } else {
                                    Toast.makeText(UpdateProfile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(UpdateProfile.this, "No changes made", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
    }

