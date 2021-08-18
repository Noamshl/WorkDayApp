package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.model.LoginRepository;
import com.example.myapplication.data.User;
import com.example.myapplication.data.UserSettings;
import com.example.myapplication.data.model.WorkRepository;


public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> userText;
    private MutableLiveData<User> user;
    private MutableLiveData<UserSettings> userSetting;
    private MutableLiveData<Integer> numOfWorkDays;
    private LoginRepository loginRepository;
    private WorkRepository workRepository;


    public SharedViewModel(LoginRepository repository, WorkRepository workRepository) {
        this.userText = new MutableLiveData<>("");
        this.user = new MutableLiveData<>(new User());
        this.userSetting = new MutableLiveData<>(new UserSettings());
        numOfWorkDays = new MutableLiveData<>(0);
        this.loginRepository = repository;
        this.workRepository = workRepository;
    }

    public void updateUserDetails(String previousUsername,User user,UserSettings userSettings){
        loginRepository.updateUserDetails(previousUsername,user,userSettings);
    }



    public LiveData<String> getText() {
        return userText;
    }

    public LiveData<Integer> getNumOfWorkDays() {
        return numOfWorkDays;
    }

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<UserSettings> getUserSetting() {
        return userSetting;
    }

    public void setUserText(String mText) {
        this.userText.setValue(mText);
    }

    public void setNumOfWorkDays(Integer numOfWorkDays) {
        this.numOfWorkDays.setValue(numOfWorkDays);
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public void setUserSetting(UserSettings userSetting) {
        this.userSetting.setValue(userSetting);
    }

    public void checkNumOfWorkDays(){
        workRepository.checkNumOfWorkDays(user.getValue().getUsername(), numOfWorkDays::setValue);

    }
}
