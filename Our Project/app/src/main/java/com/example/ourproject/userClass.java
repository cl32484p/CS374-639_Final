package com.example.ourproject;

import java.util.Map;

public class userClass {

    String name, major, age, weight, height, yearLevel, mealPlan, residency, campus,  activityLevel, password, email;
    int transactions;
    Double balance, dollarsPer, calories;
    private Map<String, Object> history;

    public  userClass(){

    }

    public userClass(String name, String email, String password){
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public userClass(String name, String email, String password, String major, String mealPlan, String residency, String campus, String yearLevel){
        this.name = name;
        this.password = password;
        this.email = email;
        this.major = major;
        this.mealPlan = mealPlan;
        this.residency = residency;
        this.campus = campus;
        this.yearLevel = yearLevel;
        transactions = 0;
    }

    public userClass(Map<String, Object> history) {
        this.history = history;
    }

    public Map<String, Object> getHistory() {
        return history;
    }

    public void setHistory(Map<String, Object> history) {
        this.history = history;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMealplan() {
        return mealPlan;
    }

    public void setMealplan(String mealplan) {
        this.mealPlan = mealplan;
    }

    public String getResidency() {
        return residency;
    }

    public void setResidency(String residency) {
        this.residency = residency;
    }

    public int getTransactions() {
        return transactions;
    }

    public void setTransactions(int transactions) {
        this.transactions = transactions;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getDollarsPer() {
        return dollarsPer;
    }

    public void setDollarsPer(Double dollarsPer) {
        this.dollarsPer = dollarsPer;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public String getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }







}
