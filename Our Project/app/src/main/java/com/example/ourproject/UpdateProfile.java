package com.example.ourproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfile extends AppCompatActivity {

    private EditText editMajor;
    private RadioGroup radioGroupResidency;
    private RadioGroup radioGroupCampus;
    private Spinner spinnerYear;
    private Spinner spinnerMealPlan;
    private Button updateButton;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editMajor = findViewById(R.id.edit_major);
        radioGroupResidency = findViewById(R.id.group_residency);
        radioGroupCampus = findViewById(R.id.group_campus);
        spinnerYear = findViewById(R.id.spinner_year);
        spinnerMealPlan = findViewById(R.id.spinner_mealplan);
        updateButton = findViewById(R.id.update_button);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            String major = editMajor.getText().toString();
            String residency = ((RadioButton) findViewById(radioGroupResidency.getCheckedRadioButtonId())).getText().toString();
            String campus = ((RadioButton) findViewById(radioGroupCampus.getCheckedRadioButtonId())).getText().toString();
            String year = spinnerYear.getSelectedItem().toString();
            String mealPlan = spinnerMealPlan.getSelectedItem().toString();

            DatabaseReference currentUserRef = mDatabase.child(userId);
            currentUserRef.child("major").setValue(major);
            currentUserRef.child("residency").setValue(residency);
            currentUserRef.child("campus").setValue(campus);
            currentUserRef.child("yearLevel").setValue(year);
            currentUserRef.child("mealplan").setValue(mealPlan)
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
        }
    }
}
