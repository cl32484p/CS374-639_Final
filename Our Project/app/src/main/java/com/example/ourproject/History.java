package com.example.ourproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*public class History extends AppCompatActivity{
    ListView listView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        String username = "bob";

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(userId).child("history");


        listView = (ListView) findViewById(R.id.idLVTransactions);

        arrayList.add(username+"'s Balance History Below");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, transactionClass> map = (Map<String, transactionClass>) dataSnapshot.getValue();
                String title = map.toString();
                String current = title;
                 String [] modify = current.split(", dollersPer=");
                current = modify[0];
                 String [] modify2 = current.split(", hisTitle=");
                  String result = modify2[1];


                // String value = dataSnapshot.child(title).getValue(donationClass.class).toString();
                arrayList.add(result);
                arrayAdapter = new ArrayAdapter<String>(History.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }} */

public class History extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TransactionAdapter transactionAdapter;
    private Button newTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.rvTransactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newTransaction = findViewById(R.id.addTransaction);

        newTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History.this, editBalance.class);
                startActivity(intent);
            }
        });




        // Retrieve transaction data from Firebase database and add it to the adapter

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("history");
        historyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<transactionClass> transactions = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    transactionClass transaction = childSnapshot.getValue(transactionClass.class);
                    transactions.add(transaction);
                }
                transactionAdapter = new TransactionAdapter(transactions);
                recyclerView.setAdapter(transactionAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("History", "Failed to retrieve transactions", error.toException());
            }
        });
    }
}
