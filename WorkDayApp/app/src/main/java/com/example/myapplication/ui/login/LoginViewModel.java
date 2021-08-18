package com.example.myapplication.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;
import android.widget.Toast;

import com.example.myapplication.MyApplication;
import com.example.myapplication.SharedViewModel;
import com.example.myapplication.data.model.LoginRepository;
import com.example.myapplication.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
   // private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository appRepository;
    private MutableLiveData<String> usernameText = new MutableLiveData<>("");

    LoginViewModel(LoginRepository appRepository) {
        this.appRepository = appRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public MutableLiveData<String> getUsernameText() {
        return usernameText;
    }

    public void login(String username, String password, SharedViewModel sharedViewModel) {
//        // can be launched in a separate asynchronous job
          appRepository.login((data -> {
              if(data!=null){
                  usernameText.setValue(username);
                  sharedViewModel.setUserText(data.getUsername());
                  sharedViewModel.setUser(data);
                  sharedViewModel.checkNumOfWorkDays();
                  appRepository.getUserSettings(username, sharedViewModel::setUserSetting);


              }
              else {
                  Toast.makeText(MyApplication.getContext(),"Login failed",Toast.LENGTH_SHORT).show();
              }
          }),username,password);
    }

    public void deleteCurrentUsers(){
       appRepository.deleteCurrentUser();
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
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
}