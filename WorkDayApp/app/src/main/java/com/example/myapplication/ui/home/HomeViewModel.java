package com.example.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.SharedViewModel;
import com.example.myapplication.data.model.LoginRepository;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> userText;
    private LoginRepository loginRepository;
    public HomeViewModel(LoginRepository loginRepository) {
        userText = new MutableLiveData<>("-");
        this.loginRepository = loginRepository;
    }

    public LiveData<String> getText() {
        return userText;
    }

    public void checkLogin(SharedViewModel sharedViewModel){
        if(!sharedViewModel.getText().getValue().equals(""))
            return;
        userText.setValue("");
//        loginRepository.checkLogin(data ->{
//            if(data!=null){
//                userText.setValue(data);
//                loginRepository.getUser(userText.getValue(), sharedViewModel::setUser);
//                loginRepository.getUserSettings(userText.getValue(), sharedViewModel::setUserSetting);
//                sharedViewModel.setUserText(data);
//            }
//            else {
//                userText.setValue("");
//                sharedViewModel.setUserText("");
//            }
//        });
    }
}