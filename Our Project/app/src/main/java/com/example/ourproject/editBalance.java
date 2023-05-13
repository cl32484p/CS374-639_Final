package com.example.ourproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class editBalance extends AppCompatActivity {

    private String balance,dollarsPer,newBalance,newDollarsPer;
    private Double calcDollars, difference,differenceDPD, newDPD, daysDbl, balDbl;
    private long time,days;
    private TextView currentBal,currentDollars,newBal,newDollars;
    private EditText editBal;
    private Button preview, save;
    private userClass user;
    FirebaseAuth mAuth;
    private Date today = new Date();
    private Date end = new Date(123,4,16);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_balance);

        currentBal = findViewById(R.id.current_val);
        currentDollars  = findViewById(R.id.current_dollarsVal);
        newBal  = findViewById(R.id.new_val);
        newDollars = findViewById(R.id.new_dollarsVal);
        editBal = findViewById(R.id.edit_balance);
        preview = findViewById(R.id.preview_button);
        save = findViewById(R.id.save_button);

        //retrieve user info
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currUser = mAuth.getCurrentUser();
        String userId = currUser.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users").child(userId);
        getBalance(mDatabase, currentBal, currentDollars);
        balance = currentBal.getText().toString();
        dollarsPer = currentDollars.getText().toString();

        //preview new Balance
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        newBalance = editBal.getText().toString();
                        balance = currentBal.getText().toString();
                        dollarsPer = currentDollars.getText().toString();
                        newBal.setText(newBalance);
                Log.d("MyApp", "The value of balance is "+ balance);
                        difference = Double.parseDouble(newBalance) - Double.parseDouble(balance);

                    //Calculate new dollars per day

                        time = end.getTime() - today.getTime();
                        days = (TimeUnit.DAYS.convert(time, TimeUnit.MILLISECONDS)); //Retrieve amount of days between todays date and end of semester
                        daysDbl = (double)days;
                Log.d("MyApp", "The value of daysDBl is "+ daysDbl);
                        newDPD = Double.parseDouble(newBalance);
                Log.d("MyApp", "The value of newDPD is "+ newDPD);
                        newDPD = newDPD/daysDbl; //calculate new Dollars Per Day
                Log.d("MyApp", "The value of newDPD is "+ newDPD);
                differenceDPD = newDPD - Double.parseDouble(dollarsPer);
                Log.d("MyApp", "The value of dollarsPer is "+ dollarsPer);
                Log.d("MyApp", "The value of differenceDPD is "+ differenceDPD);
               newDPD = formatDouble(newDPD);
               difference = formatDouble(difference);
               differenceDPD = formatDouble(differenceDPD);
                newDollars.setText(String.valueOf(newDPD));
                             SetColorTask  setColorTask = new SetColorTask(getApplicationContext(),difference,differenceDPD, newBal, newDollars,save);
                             setColorTask.execute();



            }
        });


//update new Balance and close activity
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBalance = editBal.getText().toString();
                balance = currentBal.getText().toString();
                dollarsPer = currentDollars.getText().toString();
                newBal.setText(newBalance);
                balDbl = Double.parseDouble(newBalance);
                Log.d("MyApp", "The value of balance is "+ balance);
                difference = Double.parseDouble(newBalance) - Double.parseDouble(balance);

                //Calculate new dollars per day
                time = end.getTime() - today.getTime();
                days = (TimeUnit.DAYS.convert(time, TimeUnit.MILLISECONDS)); //Retrieve amount of days between todays date and end of semester
                daysDbl = (double)days;
                // Log.d("MyApp", "The value of daysDBl is "+ daysDbl);
                newDPD = Double.parseDouble(newBalance);
                // Log.d("MyApp", "The value of newDPD is "+ newDPD);
                newDPD = newDPD/daysDbl; //calculate new Dollars Per Day
                 Log.d("MyApp", "The value of newDPD is "+ newDPD);
                differenceDPD = newDPD - Double.parseDouble(dollarsPer);



                newDPD = formatDouble(newDPD);
                difference = formatDouble(difference);
                Log.d("MyApp", "The value of difference is "+ difference);
                differenceDPD = formatDouble(differenceDPD);
                balDbl = formatDouble(balDbl);
                Intent intent = new Intent(editBalance.this,Confirmation.class);
                Bundle form = new Bundle();
                form.putDouble("difference", difference);
                form.putDouble("differenceDPD" , differenceDPD);
                form.putDouble("balance", balDbl);
                form.putDouble("newDollarsPer", newDPD);

                SetColorTask setColorTask = new SetColorTask(getApplicationContext(), difference, differenceDPD, newBal, newDollars, save);
                setColorTask.execute();
                //setColor(getApplicationContext(),difference,differenceDPD, newBal, newDollars,save);
                Log.d("MyApp", "The value of balDbl is "+ balDbl);
              //  setBalance(mDatabase, balDbl,newDPD);
               //today = new Date();

                intent.putExtras(form);
               // new UpdateDatabaseTask(mDatabase, today, difference, differenceDPD, balDbl, newDPD).execute();

               // updateDatabase(mDatabase,today,difference,differenceDPD,balDbl,newDPD);
               // Intent intent = new Intent(editBalance.this, Home.class);
                startActivity(intent);


            }
        });

    }  //end of onCreate

    public static void getBalance(DatabaseReference mDatabase, TextView currentBal, TextView currentDollars) {
        //Retrieve the Current Users Current Balance and Dollars Per Day

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userClass currentUser = dataSnapshot.getValue(userClass.class);
                String balance = String.valueOf(currentUser.getBalance());
                String dollarsPer = String.valueOf(currentUser.getDollarsPer());
                currentBal.setText(balance);
                currentDollars.setText(dollarsPer);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database read error
            }
        });
    }

   /* public static void setBalance(DatabaseReference mDatabase,Double balDbl, Double newDPD) {
        //Update the Current Balance and Dollars Per Day for the Current User
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userClass currentUser = dataSnapshot.getValue(userClass.class);
                currentUser.setBalance(balDbl);

                currentUser.setDollarsPer(newDPD);

                mDatabase.setValue(currentUser);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database read error
            }
        });
    } */

 /*public static void setColor(Context context, Double difference, Double differenceDPD,TextView newBal, TextView newDollars, Button save)
{
    String newBalanceStr = newBal.getText().toString() + " ("+ String.valueOf(difference) + ")";
    String newDollarsStr = newDollars.getText().toString() + " (" + String.valueOf(differenceDPD + ")");

    if(difference == 0)
    {
                newBal.setText(newBalanceStr);
                newDollars.setText(newDollarsStr);
                newBal.setTextColor(Color.parseColor("blue"));
                newDollars.setTextColor(Color.parseColor("blue"));
                save.setTextColor(Color.parseColor("blue"));
        Toast.makeText(context,"Balance will not change",Toast.LENGTH_SHORT).show();
    }

    else if(difference > 0)
    {
        newBalanceStr = newBal.getText().toString() + " (+ "+ String.valueOf(difference) + ")";
        newDollarsStr = newDollars.getText().toString() + " (+ " + String.valueOf(differenceDPD + ")");
        newBal.setText(newBalanceStr);
        newDollars.setText(newDollarsStr);
        newBal.setTextColor(Color.parseColor("lime"));
        newDollars.setTextColor(Color.parseColor("lime"));
        save.setTextColor(Color.parseColor("lime"));
        Toast.makeText(context,"Balance will increase",Toast.LENGTH_SHORT).show();

    }

    else if(difference < 0)
    {
        if(differenceDPD < 0) {

            newBal.setText(newBalanceStr);
            newDollars.setText(newDollarsStr);
            newBal.setTextColor(Color.parseColor("red"));
            newDollars.setTextColor(Color.parseColor("red"));
            save.setTextColor(Color.parseColor("red"));
            Toast.makeText(context, "Balance will decrease", Toast.LENGTH_SHORT).show();
        }

        else if (differenceDPD>0){
            newDollarsStr = newDollars.getText().toString() + " (+ " + String.valueOf(differenceDPD + ")");
            newBal.setText(newBalanceStr);
            newDollars.setText(newDollarsStr);
            newBal.setTextColor(Color.parseColor("red"));
            newDollars.setTextColor(Color.parseColor("lime"));
            save.setTextColor(Color.parseColor("yellow"));
            Toast.makeText(context, "Balance will decrease but dollars per day is increased", Toast.LENGTH_SHORT).show();
        }

        else if (differenceDPD==0){
            newBal.setText(newBalanceStr);
            newDollars.setText(newDollarsStr);
            newBal.setTextColor(Color.parseColor("red"));
            newDollars.setTextColor(Color.parseColor("blue"));
            save.setTextColor(Color.parseColor("purple"));
            Toast.makeText(context, "Balance will decrease but dollars per day remains the same", Toast.LENGTH_SHORT).show();
        }


    }

}
*/
/*
public static void updateDatabase(DatabaseReference mDatabase, Date today, Double difference, Double differenceDPD, Double balDbl, Double newDPD){
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
    String date = dateFormat.format(today);


    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            userClass currentUser = dataSnapshot.getValue(userClass.class);

            int transactions;


            transactions = currentUser.getTransactions()+1;
            String title = "Transaction: " + transactions;
            String hisTitle = "";
            if(difference == 0)
            {
                hisTitle ="Transaction: " + transactions + "Balance: " + balDbl + "( " + difference + ") Dollers Per Day: " + newDPD + "( " + differenceDPD + ") Date: " + date;
            }

            else if(difference > 0)
            {
                hisTitle ="Transaction: " + transactions + "Balance: " + balDbl + "(+ " + difference + ") Dollers Per Day: " + newDPD + "(+ " + differenceDPD + ") Date: " + date;
            }

            else if(difference < 0)
            {
                if(differenceDPD < 0)
                {

                     hisTitle ="Transaction: " + transactions + "Balance: " + balDbl + "( " + difference + ") Dollers Per Day: " + newDPD + "( " + differenceDPD + ") Date: " + date;
                }

                else if (differenceDPD>0){
                    hisTitle ="Transaction: " + transactions + "Balance: " + balDbl + "( " + difference + ") Dollers Per Day: " + newDPD + "(+ " + differenceDPD + ") Date: " + date;
                }

                else if (differenceDPD==0){
                    hisTitle ="Transaction: " + transactions + "Balance: " + balDbl + "( " + difference + ") Dollers Per Day: " + newDPD + "( " + differenceDPD + ") Date: " + date;
                }

            }



            transactionClass transaction = new transactionClass(balDbl,difference,newDPD,differenceDPD,date,title);
            String key = mDatabase.child("history").push().getKey();
            Map<String, Object> postValues = transaction.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/history/" + key, postValues);
            mDatabase.updateChildren(childUpdates);

            mDatabase.setValue(currentUser);
          //  DatabaseReference historyRef = mDatabase.child("history");
            //String key = historyRef.push().getKey();
           // historyRef.child(key).setValue(transaction);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            // Handle database read error
        }
    });




} */

public static Double formatDouble(Double dub)
{
    Double thisDBL = dub;
    DecimalFormat df = new DecimalFormat("#.00");
    String formattedValue = df.format(dub);

    dub = Double.parseDouble(formattedValue);

    return dub;
}









}