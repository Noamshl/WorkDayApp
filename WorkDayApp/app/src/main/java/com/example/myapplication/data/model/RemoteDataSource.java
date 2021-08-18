package com.example.myapplication.data.model;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.MyApplication;
import com.example.myapplication.data.SimpleCallBack;
import com.example.myapplication.data.User;
import com.example.myapplication.data.UserSettings;
import com.example.myapplication.data.WorkData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RemoteDataSource {
    FirebaseDatabase db;


    public RemoteDataSource(){
        db = FirebaseDatabase.getInstance();
    }

    public void login(SimpleCallBack<User> callBack,String username,String password){
        DatabaseReference ref = db.getReference("users").child(username);
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful() || !task.getResult().exists()) {

                    callBack.call(null);
                }
                else{
                    User user = task.getResult().getValue(User.class);
                    if(user.getPassword().equals(password)) {

                        callBack.call(user);
                    }
                    else
                        callBack.call(null);
                }
            }
        });

//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    User user = snapshot.getValue(User.class);
//                    if(user.getPassword().equals(password))
//                        callBack.call(user);
//                    else
//                        callBack.call(null);
//                }
//                else
//                    callBack.call(null);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    public void getUserSettings(String username,SimpleCallBack<UserSettings> callBack){
        DatabaseReference ref = db.getReference("userSettings").child(username);
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                UserSettings userSettings = new UserSettings(username);
                if(task.isSuccessful() && task.getResult().exists()) {

                    userSettings = task.getResult().getValue(UserSettings.class);

                }
                Toast.makeText(MyApplication.getContext(),userSettings.getTotalNumOfDays().toString(),Toast.LENGTH_SHORT).show();
                callBack.call(userSettings);
            }
        });
    }

    public void getUser(String username,SimpleCallBack<User> callBack){
        DatabaseReference ref = db.getReference("users").child(username);
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                User user = new User();
                if(task.isSuccessful() && task.getResult().exists())
                    user = task.getResult().getValue(User.class);
                callBack.call(user);
            }
        });
    }

    public void register(SimpleCallBack<Boolean> callBack,User user){
        DatabaseReference ref = db.getReference("users").child(user.getUsername());
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()) {
                    callBack.call(false);
                }
                else if(task.getResult().exists())
                    callBack.call(false);
                else {
                    ref.setValue(user);
                    createSettings(user.getUsername());
                    callBack.call(true);

                }
            }
        });
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(!snapshot.exists()){
//                    ref.setValue(user);
//                    callBack.call(true);
//                }
//                else
//                    callBack.call(false);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                    callBack.call(false);
//            }
//        });
    }

    public void createSettings(String username){
        UserSettings userSettings = new UserSettings(username);
        db.getReference("userSettings").child(username).setValue(userSettings);
    }

    public void updateUserDetails(String previousUsername,User user,UserSettings userSettings){

        db.getReference("users").child(user.getUsername()).setValue(user);
        db.getReference("userSettings").child(user.getUsername()).setValue(userSettings);
        if(!previousUsername.equals(user.getUsername())){
            copyUserData(user.getUsername(),previousUsername,"workDays", WorkData.class);
            copyUserData(user.getUsername(),previousUsername,"userJob", null);
            copyUserData(user.getUsername(),previousUsername,"userYears", null);
            removeUserDetails(previousUsername,"users");
            removeUserDetails(previousUsername,"userSettings");
        }
    }

    private void copyUserData(String newUser,String previous,String branch,Class c){
        DatabaseReference ref1 = db.getReference(branch).child(previous);
        DatabaseReference ref2 = db.getReference(branch).child(newUser);
        ref1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if(c!=null)
                    ref2.child(snapshot.getKey()).setValue(snapshot.getValue(c)).addOnCompleteListener(task -> {
                        removeUserDetails(previous,branch);
                    });
                else
                    ref2.child(snapshot.getKey()).setValue(snapshot.getValue()).addOnCompleteListener(task -> {
                        removeUserDetails(previous,branch);
                    });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Toast.makeText(MyApplication.getContext(),"aaa",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void removeUserDetails(String username,String branch){
        db.getReference(branch).child(username).removeValue();
    }
}
