package com.example.myapplication.ui.WorkAdder;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.data.model.WorkRepository;

public class AddWorkDayViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        if(aClass.isAssignableFrom(AddWorkDayViewModel.class)){
            return (T) new AddWorkDayViewModel(new WorkRepository());
        }
        else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
