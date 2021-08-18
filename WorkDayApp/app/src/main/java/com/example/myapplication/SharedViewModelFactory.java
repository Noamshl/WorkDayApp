package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.data.model.LoginRepository;
import com.example.myapplication.data.model.LocalDataSource;
import com.example.myapplication.data.model.RemoteDataSource;
import com.example.myapplication.data.model.WorkRepository;


public class SharedViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SharedViewModel.class)) {
            return (T) new SharedViewModel(new LoginRepository(new RemoteDataSource(),new LocalDataSource()),new WorkRepository());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
