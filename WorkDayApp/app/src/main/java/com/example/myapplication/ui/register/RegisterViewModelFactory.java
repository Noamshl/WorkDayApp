package com.example.myapplication.ui.register;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.data.model.LoginRepository;
import com.example.myapplication.data.model.LocalDataSource;
import com.example.myapplication.data.model.RemoteDataSource;

public class RegisterViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterViewModel.class))
            return (T) new RegisterViewModel(new LoginRepository(new RemoteDataSource(),new LocalDataSource()));
        else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
