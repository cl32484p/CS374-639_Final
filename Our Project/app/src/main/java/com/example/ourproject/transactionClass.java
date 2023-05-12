package com.example.ourproject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class transactionClass {

    Double balance, difference, dollersPer, dollarsPerDifference;
    String date;
    String title, hisTitle;

    public transactionClass(){

    }

    public transactionClass(Double balance, Double difference, Double dollarsPer, Double dollarsPerDifference, String date, String title) {
        this.balance = balance;
        this.difference = difference;
        this.dollersPer = dollarsPer;
        this.dollarsPerDifference = dollarsPerDifference;
        this.date = date;
        this.title = title;
       // this.hisTitle = hisTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

    public Double getDollersPer() {
        return dollersPer;
    }

    public void setDollersPer(Double dollersPer) {
        this.dollersPer = dollersPer;
    }

    public Double getDollarsPerDifference() {
        return dollarsPerDifference;
    }

    public void setDollarsPerDifference(Double dollarsPerDifference) {
        this.dollarsPerDifference = dollarsPerDifference;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("balDbl", balance);
        result.put("difference", difference);
        result.put("newDPD", dollersPer);
        result.put("differenceDPD", dollarsPerDifference);
        result.put("date", date);
        result.put("title", title);

        return result;
    }


}
