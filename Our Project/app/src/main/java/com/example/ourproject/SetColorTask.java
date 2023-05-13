package com.example.ourproject;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SetColorTask extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private Double mDifference;
    private Double mDifferenceDPD;
    private TextView mNewBal;
    private TextView mNewDollars;
    private Button mSave;

    public SetColorTask(Context context, Double difference, Double differenceDPD,
                        TextView newBal, TextView newDollars, Button save) {
        mContext = context;
        mDifference = difference;
        mDifferenceDPD = differenceDPD;
        mNewBal = newBal;
        mNewDollars = newDollars;
        mSave = save;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String newBalanceStr = mNewBal.getText().toString() + " ("+ String.valueOf(mDifference) + ")";
        String newDollarsStr = mNewDollars.getText().toString() + " (" + String.valueOf(mDifferenceDPD + ")");

        if(mDifference == 0) {
            mNewBal.setText(newBalanceStr);
            mNewDollars.setText(newDollarsStr);
            mNewBal.setTextColor(Color.parseColor("blue"));
            mNewDollars.setTextColor(Color.parseColor("blue"));
            mSave.setTextColor(Color.parseColor("blue"));
           // Toast.makeText(mContext,"Balance will not change",Toast.LENGTH_SHORT).show();
        } else if(mDifference > 0) {
            newBalanceStr = mNewBal.getText().toString() + " (+ "+ String.valueOf(mDifference) + ")";
            newDollarsStr = mNewDollars.getText().toString() + " (+ " + String.valueOf(mDifferenceDPD + ")");
            mNewBal.setText(newBalanceStr);
            mNewDollars.setText(newDollarsStr);
            mNewBal.setTextColor(Color.parseColor("lime"));
            mNewDollars.setTextColor(Color.parseColor("lime"));
            mSave.setTextColor(Color.parseColor("lime"));
          //  Toast.makeText(mContext,"Balance will increase",Toast.LENGTH_SHORT).show();
        } else if(mDifference < 0) {
            if(mDifferenceDPD < 0) {
                mNewBal.setText(newBalanceStr);
                mNewDollars.setText(newDollarsStr);
                mNewBal.setTextColor(Color.parseColor("red"));
                mNewDollars.setTextColor(Color.parseColor("red"));
                mSave.setTextColor(Color.parseColor("red"));
               // Toast.makeText(mContext, "Balance will decrease", Toast.LENGTH_SHORT).show();
            } else if (mDifferenceDPD > 0) {
                newDollarsStr = mNewDollars.getText().toString() + " (+ " + String.valueOf(mDifferenceDPD + ")");
                mNewBal.setText(newBalanceStr);
                mNewDollars.setText(newDollarsStr);
                mNewBal.setTextColor(Color.parseColor("red"));
                mNewDollars.setTextColor(Color.parseColor("lime"));
                mSave.setTextColor(Color.parseColor("yellow"));
              //  Toast.makeText(mContext, "Balance will decrease but dollars per day is increased", Toast.LENGTH_SHORT).show();
            } else if (mDifferenceDPD == 0) {
                mNewBal.setText(newBalanceStr);
                mNewDollars.setText(newDollarsStr);
                mNewBal.setTextColor(Color.parseColor("red"));
                mNewDollars.setTextColor(Color.parseColor("blue"));
                mSave.setTextColor(Color.parseColor("purple"));
               // Toast.makeText(mContext, "Balance will decrease but dollars per day remains the same", Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }
}