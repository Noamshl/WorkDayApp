package com.example.myapplication.data;

import com.example.myapplication.data.WorkData;

import java.util.ArrayList;
import java.util.List;

public class MonthOfWork {
    private String monthName;
    private Integer monthNumber;
    private boolean isExpanded;
    private List<WorkData> workOfMonth;

    public MonthOfWork(){
        monthName = "";
        monthNumber = 0;
        isExpanded = false;
        workOfMonth = new ArrayList<>();
    }

    public MonthOfWork(String monthName,Integer monthNumber){
        this.monthName = monthName;
        this.monthNumber = monthNumber;
        isExpanded=false;
        workOfMonth = new ArrayList<>();
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public Integer getMonthNumber() {
        return monthNumber;
    }

    public List<WorkData> getWorkOfMonth() {
        return workOfMonth;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

}
