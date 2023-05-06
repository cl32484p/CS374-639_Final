package com.example.ourproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private Button registerButton;
    private Button loginButton;
    private EditText editName;
    private EditText editPassword;
    private EditText editEmail;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Assign UI Elements
        registerButton = findViewById(R.id.signup_button);
        loginButton = findViewById(R.id.login_button);
        editName = findViewById(R.id.full_name);
        editPassword = findViewById(R.id.password_field);
        editEmail = findViewById(R.id.email_field);
        mAuth= FirebaseAuth.getInstance();

        //When User Clicks Register
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = editName.getText().toString();
                String password = editPassword.getText().toString();
                String email = editEmail.getText().toString();

                //Check if all info is entered
                if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                //Continue registration
                // (Firebase implementation can be added here to check if email was previously registered)

                else {
                    // userClass newUser = new userClass(fullName, email, password);
                    //send user info to next activity
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        //Create user in database with generated userID
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String userId = user.getUid();

                                        userClass newUser = new userClass(fullName, email, password);
                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                        mDatabase.child("users").child(userId).setValue(newUser);

                                        Toast.makeText(Register.this, "Signup done",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Register.this,SignUp2.class);
                                        startActivity(intent);
                                    } else {

                                        Toast.makeText(Register.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            }
        }); //end of OnClickListener

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });






    }
}