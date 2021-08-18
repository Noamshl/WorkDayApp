package com.example.myapplication.ui.register;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;
import com.example.myapplication.data.model.LoginRepository;
import com.example.myapplication.data.SimpleCallBack;
import com.example.myapplication.data.User;


public class RegisterViewModel extends ViewModel {
    private MutableLiveData<RegisterFormState> registerFormState;
    private LoginRepository appRepository;

    RegisterViewModel(LoginRepository appRepository){
        this.appRepository = appRepository;
        registerFormState = new MutableLiveData<>();
    }

    public MutableLiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    public void registerDataChanged(String username, String password, String email) {
        if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username, null,null));
        }
        else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password,null));
        }
        else if(!isEmailValid(email)){
            registerFormState.setValue(new RegisterFormState(null,null,R.string.invalid_email));
        }
        else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        }
        else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void register(SimpleCallBack<Boolean> callBack, User user){
        appRepository.register(callBack,user);

    }
}