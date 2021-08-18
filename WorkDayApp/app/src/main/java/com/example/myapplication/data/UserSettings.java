package com.example.myapplication.data;

public class UserSettings {
    String username;
    Integer totalNumOfDays;
    Double sumOfDebt;
    Integer sumForHour;

    public UserSettings(){
        username = "";
        totalNumOfDays = 0;
        sumForHour = 0;
        sumOfDebt = 0.0;
    }
    public UserSettings(String username){
        this.username = username;
        totalNumOfDays = 0;
        sumForHour = 0;
        sumOfDebt = 0.0;
    }

    public UserSettings(String username,Integer totalNumOfDays,Double sumOfDebt,Integer sumForHour){
        this.username = username;
        this.totalNumOfDays = totalNumOfDays;
        this.sumOfDebt = sumOfDebt;
        this.sumForHour = sumForHour;
    }

    public String getUsername() {
        return username;
    }

    public Double getSumOfDebt() {
        return sumOfDebt;
    }

    public Integer getSumForHour() {
        return sumForHour;
    }

    public Integer getTotalNumOfDays() {
        return totalNumOfDays;
    }
}
