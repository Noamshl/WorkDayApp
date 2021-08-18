package com.example.myapplication.data.model;





import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LocalDataSource {
    String username;

    public LocalDataSource(){
        username ="";
    }

    public String getUsername() {
        return username;
    }

//    public void login(User user){
//        UserDao userDao = dataStorage.userDao();
//        userDao.getUser().subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableSingleObserver<List<User>>() {
//                    @Override
//                    public void onSuccess(@NonNull List<User> users) {
//                        if(users.size()>0){
//                            if(users.get(0).getUsername().equals(user.getUsername())){
//
//                            }
//                            else {
//                                userDao.updateUser(user.getUsername(),user.getPassword(),user.getMail())
//                                        .subscribeOn(Schedulers.newThread())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .subscribe(new DisposableCompletableObserver() {
//                                            @Override
//                                            public void onComplete() {
//
//                                            }
//
//                                            @Override
//                                            public void onError(@NonNull Throwable e) {
//
//                                            }
//                                        });
//                            }
//                        }
//                        else{
//                            userDao.insertUser(user).subscribeOn(Schedulers.newThread())
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .subscribe(new DisposableCompletableObserver() {
//                                        @Override
//                                        public void onComplete() {
//
//                                        }
//
//                                        @Override
//                                        public void onError(@NonNull Throwable e) {
//
//                                        }
//                                    });
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//                });
//    }
//
//    public void deleteCurrentUser(){
//        UserDao userDao = dataStorage.userDao();
//        userDao.deleteAllUsers().subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableCompletableObserver() {
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//                });
//    }
//
//    public void checkLogin(SimpleCallBack<String> callBack){
//        UserDao userDao = dataStorage.userDao();
//        userDao.getUser().subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableSingleObserver<List<User>>() {
//                    @Override
//                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<User> users) {
//                        if(users.size() > 0) {
//                            callBack.call(users.get(0).getUsername());
//                        }
//                        else
//                            callBack.call("");
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//
//                    }
//                });
//    }
}
