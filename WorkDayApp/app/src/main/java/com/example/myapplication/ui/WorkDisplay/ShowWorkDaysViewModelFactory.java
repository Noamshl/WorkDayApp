package com.example.myapplication.ui.WorkDisplay;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.example.myapplication.data.model.WorkRepository;


public class ShowWorkDaysViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShowWorkDaysViewModel.class))
            return (T) new ShowWorkDaysViewModel(new WorkRepository());
        else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
