package com.example.ourproject;

import java.util.Date;

public class transactionClass {

    Double balance, difference, dollersPer, dollarsPerDifference;
    Date date;
    String title;

    public transactionClass(){

    }

    public transactionClass(Double balance, Double difference, Double dollarsPer, Double dollarsPerDifference, Date date, String title) {
        this.balance = balance;
        this.difference = difference;
        this.dollersPer = dollarsPer;
        this.dollarsPerDifference = dollarsPerDifference;
        this.date = date;
        this.title = title;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
