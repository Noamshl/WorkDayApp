package com.example.myapplication.ui.WorkDisplay;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.MonthOfWork;
import com.example.myapplication.data.SimpleCallBack;
import com.example.myapplication.data.model.WorkRepository;

import java.util.List;

public class ShowWorkDaysViewModel extends ViewModel {
    private WorkRepository workRepository;
    private MutableLiveData<String> mText;


    public ShowWorkDaysViewModel(WorkRepository workRepository) {
        this.workRepository = workRepository;
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void getYears(String username, SimpleCallBack<List<String>> callBack){
        workRepository.getYears(username,callBack);
    }

    public void getActiveMonths(String username,String year,SimpleCallBack<List<MonthOfWork>> callBack){
        workRepository.getActiveMonths(username,year,callBack);
    }
}