package com.example.ourproject;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateDatabaseTask extends AsyncTask<Void, Void, transactionClass> {
    private DatabaseReference mDatabase;
    private Date today;
    private Double difference;
    private Double differenceDPD;
    private Double balDbl;
    private Double newDPD;
    private int transactions;

    public UpdateDatabaseTask(DatabaseReference mDatabase, Date today, Double difference, Double differenceDPD, Double balDbl, Double newDPD) {
        this.mDatabase = mDatabase;
        this.today = today;
        this.difference = difference;
        this.differenceDPD = differenceDPD;
        this.balDbl = balDbl;
        this.newDPD = newDPD;
    }

    @Override
    protected transactionClass doInBackground(Void... params) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        String date = dateFormat.format(today);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

userClass currentUser = dataSnapshot.getValue(userClass.class);



                                                                 transactions = currentUser.getTransactions() + 1;

                                                                 currentUser.setTransactions(transactions);
                                                                 currentUser.setBalance(balDbl);
                                                                 currentUser.setDollarsPer(newDPD);
                                                                 mDatabase.setValue(currentUser);

                                                                 String title = "Transaction: " + transactions;
                                                                 Log.d("MyApp", "The value of title is "+ title);
                                                                 String hisTitle = "Placer";
                                                                 if (difference == 0) {
                                                                     hisTitle = "Transaction: " + transactions + "Balance: " + balDbl + "( " + difference + ") Dollers Per Day: " + newDPD + "( " + differenceDPD + ") Date: " + date;
                                                                 } else if (difference > 0) {
                                                                     hisTitle = "Transaction: " + transactions + "Balance: " + balDbl + "(+ " + difference + ") Dollers Per Day: " + newDPD + "(+ " + differenceDPD + ") Date: " + date;
                                                                 } else if (difference < 0) {
                                                                     if (differenceDPD < 0) {
                                                                         hisTitle = "Transaction: " + transactions + "Balance: " + balDbl + "( " + difference + ") Dollers Per Day: " + newDPD + "( " + differenceDPD + ") Date: " + date;
                                                                     } else if (differenceDPD > 0) {
                                                                         hisTitle = "Transaction: " + transactions + "Balance: " + balDbl + "( " + difference + ") Dollers Per Day: " + newDPD + "(+ " + differenceDPD + ") Date: " + date;
                                                                     } else if (differenceDPD == 0) {
                                                                         hisTitle = "Transaction: " + transactions + "Balance: " + balDbl + "( " + difference + ") Dollers Per Day: " + newDPD + "( " + differenceDPD + ") Date: " + date;
                                                                     }
                                                                 }

                                                                 transactionClass transaction = new transactionClass(balDbl, difference, newDPD, differenceDPD, date, title);
                                                                 mDatabase.child("history").child(title).setValue(transaction);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database read error
            }
        });

           /* String title = "Transaction: " + transactions;
        Log.d("MyApp", "The value of title is "+ title);
        String hisTitle = "Placer";
        if (difference == 0) {
            hisTitle = "Transaction: " + transactions + "Balance: " + balDbl + "( " + difference + ") Dollers Per Day: " + newDPD + "( " + differenceDPD + ") Date: " + date;
        } else if (difference > 0) {
            hisTitle = "Transaction: " + transactions + "Balance: " + balDbl + "(+ " + difference + ") Dollers Per Day: " + newDPD + "(+ " + differenceDPD + ") Date: " + date;
        } else if (difference < 0) {
            if (differenceDPD < 0) {
                hisTitle = "Transaction: " + transactions + "Balance: " + balDbl + "( " + difference + ") Dollers Per Day: " + newDPD + "( " + differenceDPD + ") Date: " + date;
            } else if (differenceDPD > 0) {
                hisTitle = "Transaction: " + transactions + "Balance: " + balDbl + "( " + difference + ") Dollers Per Day: " + newDPD + "(+ " + differenceDPD + ") Date: " + date;
            } else if (differenceDPD == 0) {
                hisTitle = "Transaction: " + transactions + "Balance: " + balDbl + "( " + difference + ") Dollers Per Day: " + newDPD + "( " + differenceDPD + ") Date: " + date;
            }
        }


        transactionClass transaction = new transactionClass(balDbl, difference, newDPD, differenceDPD, date, title);
        String key = mDatabase.child("history").push().getKey();
        Map<String, Object> postValues = transaction.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/history/" + key, postValues);
        mDatabase.updateChildren(childUpdates); */

        transactionClass transaction = new transactionClass(balDbl, difference, newDPD, differenceDPD, date, "fail");


        return transaction;
    }

    @Override
    protected void onPostExecute(transactionClass transaction) {
        // Use the Transaction object returned from doInBackground() here
        //String title = transaction.getTitle();



    }





}