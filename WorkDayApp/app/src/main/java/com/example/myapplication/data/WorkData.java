package com.example.myapplication.data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class WorkData {
    private String username;
    private String job;
    private Integer hour;
    private String date;
    private String notes;
    private String monthAndYear;
    private Integer year;
    private Integer month;
    private String timeStamp;

    public WorkData(){
        username = "";
        job = "";
        hour = 0;
        date = "";
        notes = "";
        monthAndYear = "";
        year = 0;
        month = 0;
        timeStamp = ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy/MM/dd/HH/mm/ss"));

    }
    public WorkData(String username,String job,Integer hour,String date,String notes,int month,int year){
        this.username = username;
        this.job = job;
        this.hour = hour;
        this.date = date;
        this.notes = notes;
        this.monthAndYear = month+""+year;
        this.year = year;
        this.month = month;
        timeStamp = ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy/MM/dd/HH/mm/ss"));
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getUsername() {
        return username;
    }

    public Integer getHour() {
        return hour;
    }

    public String getDate() {
        return date;
    }

    public String getJob() {
        return job;
    }

    public String getNotes() {
        return notes;
    }

    public String getMonthAndYear() {
        return monthAndYear;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }
}
