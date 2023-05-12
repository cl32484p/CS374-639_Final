package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton pizzabtn;
    FloatingActionButton saladbtn;

    FloatingActionButton Smoothie;
    FloatingActionButton alfreno;
    FloatingActionButton coffe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pizzabtn=findViewById(R.id.fab_seagrill);
        saladbtn=findViewById(R.id.fab_Salad);
        Smoothie=findViewById(R.id.fab_Smoothie);
        alfreno=findViewById(R.id.fab_alferno);
        coffe=findViewById(R.id.fab_coffe);
        coffe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Coffe.class);
                startActivity(intent);
            }
        });
        Smoothie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Smoothie.class);
                startActivity(intent);
            }
        });
        alfreno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,alferno.class);
                startActivity(intent);
            }
        });
        pizzabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,Pizza.class);
                startActivity(intent);
            }
        });
        saladbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,salad.class);
                startActivity(intent);
            }
        });
    }
}