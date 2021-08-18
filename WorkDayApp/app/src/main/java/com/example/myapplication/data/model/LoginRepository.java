package com.example.myapplication.data.model;

import com.example.myapplication.data.SimpleCallBack;
import com.example.myapplication.data.User;
import com.example.myapplication.data.UserSettings;


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {


    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;


    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore



    public LoginRepository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {

        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }



    public void login(SimpleCallBack<User> callBack, String username, String password) {
        remoteDataSource.login(data -> {
            if(data!=null){
                callBack.call(data);


            }
            else
                callBack.call(null);
        },username,password);
    }

    public void deleteCurrentUser(){
        //TODO
    }

    public String getUsername(){
        return localDataSource.getUsername();
    }

    public void checkLogin(SimpleCallBack<String> callBack){
        //TODO
    }

    public void register(SimpleCallBack<Boolean> callBack, User user){
        remoteDataSource.register(callBack,user);
    }
    public void getUserSettings(String username,SimpleCallBack<UserSettings> callBack){
        remoteDataSource.getUserSettings(username,callBack);
    }

    public void getUser(String username,SimpleCallBack<User> callBack){
        remoteDataSource.getUser(username,callBack);
    }

    public void updateUserDetails(String previousUsername,User user,UserSettings userSettings){
        remoteDataSource.updateUserDetails(previousUsername,user,userSettings);
    }
}