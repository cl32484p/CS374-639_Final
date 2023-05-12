package com.example.ourproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button loginBtn, registerBtn;

    EditText EmailE,PassowrdE;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance();
       loginBtn = findViewById(R.id.login_button);
       registerBtn = findViewById(R.id.signup_button);
EmailE=findViewById(R.id.email_field);
PassowrdE=findViewById(R.id.password_field);
       loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
String email=EmailE.getText().toString();
String passowrd =PassowrdE.getText().toString();
               if(TextUtils.isEmpty(email))
               {
                   Toast.makeText(Login.this,"PLEASE ENTER YOUR CORRECT EMAIL",Toast.LENGTH_SHORT).show();
                   return;
               }
               if(TextUtils.isEmpty(passowrd))
               {
                   Toast.makeText(Login.this,"PLEASE ENTER YOUR CORRECT PASSOWRD",Toast.LENGTH_SHORT).show();
                   return;
               }
               mAuth.signInWithEmailAndPassword(email, passowrd)
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if (task.isSuccessful()) {

                                   Toast.makeText(Login.this, "Login Succesed.",
                                           Toast.LENGTH_SHORT).show();
                                   Intent intent = new Intent(Login.this, Home.class);
                                   startActivity(intent);
                               } else {
                                   Toast.makeText(Login.this, "Login failed.",
                                           Toast.LENGTH_SHORT).show();

                               }
                           }
                       });
           }
       });


       registerBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Login.this, Register.class);
               startActivity(intent);
           }
       });





    }
}