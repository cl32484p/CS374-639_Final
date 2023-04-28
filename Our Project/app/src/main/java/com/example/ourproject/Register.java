package com.example.ourproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private Button registerButton;
    private Button loginButton;
    private EditText editName;
    private EditText editPassword;
    private EditText editEmail;

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
                    Bundle bundle = new Bundle();
                    bundle.putString("name", fullName);
                    bundle.putString("email", email);
                    bundle.putString("password", password);
                    Intent intent = new Intent(Register.this, SignUp2.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
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