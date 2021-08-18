package com.example.myapplication.data.model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.MyApplication;
import com.example.myapplication.data.SimpleCallBack;
import com.example.myapplication.data.WorkData;
import com.example.myapplication.data.MonthOfWork;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class WorkRepository {
    private FirebaseDatabase db;

    public WorkRepository() {
        db = FirebaseDatabase.getInstance();
    }

    public void addNewWorkDay(WorkData workData, SimpleCallBack<Boolean> callBack){
        DatabaseReference ref = db.getReference("userJob").child(workData.getUsername());
        ref.child(workData.getJob()).setValue(workData.getJob(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError == null){
                    DatabaseReference ref1 = db.getReference("workDays").child(workData.getUsername());
                    ref1.push().setValue(workData, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if(databaseError == null) {
                                db.getReference("userYears").child(workData.getUsername())
                                        .child(workData.getYear().toString()).child(Month.of(workData.getMonth()).name()).setValue(workData.getMonth());
                                callBack.call(true);
                            }
                        }
                    });
                }
                else
                    callBack.call(false);
            }
        });
    }

    public void getUserJobs(String username, SimpleCallBack<List<String>> callBack){
        DatabaseReference ref = db.getReference("userJob").child(username);
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful() && task.getResult().exists()){
                    List<String> list = new ArrayList<>();
                    for (DataSnapshot job:task.getResult().getChildren()) {
                        list.add(job.getKey());
                    }
                    callBack.call(list);
                }
                else
                    callBack.call(null);
            }
        });
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    List<String> list = new ArrayList<>();
//                    for (DataSnapshot job:dataSnapshot.getChildren()) {
//                        list.add(job.getKey());
//                    }
//                    callBack.call(list);
//                }
//                else {
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
    public void getMonthOfWork(String username,String year, MonthOfWork monthOfWork, SimpleCallBack<Boolean> callBack ){
         DatabaseReference ref = db.getReference("workDays").child(username);


         ref.orderByChild("monthAndYear").equalTo(monthOfWork.getMonthNumber()+year).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 try {
                     if (snapshot.exists()) {

                         for (DataSnapshot child: snapshot.getChildren()) {
                             monthOfWork.getWorkOfMonth().add(child.getValue(WorkData.class));
                         }
                         callBack.call(true);
                     }
                     else
                         callBack.call(false);
                 }
                 catch (Exception e){
                     Log.d("bug","value listener bug");
                 }
                 }


             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
    }

    public void deleteWorkData(MonthOfWork monthOfWork,int position, SimpleCallBack<Boolean> callBack){
        WorkData workData = monthOfWork.getWorkOfMonth().get(position);
        DatabaseReference ref = db.getReference("workDays").child(workData.getUsername());
        ref.orderByChild("timeStamp").equalTo(workData.getTimeStamp()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                snapshot.getRef().removeValue();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                monthOfWork.getWorkOfMonth().clear();
                callBack.call(true);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callBack.call(false);
            }
        });
    }

    public void getYears(String username,SimpleCallBack<List<String>> callBack){
        List<String> list = new ArrayList<>();
        DatabaseReference ref = db.getReference("userYears").child(username);
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    for (DataSnapshot child : task.getResult().getChildren()) {
                        list.add(child.getKey());
                    }
                }
                callBack.call(list);
            }
        });
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if(snapshot.exists()) {
//                    for (DataSnapshot child : snapshot.getChildren()) {
//                          list.add(child.getKey());
//                    }
//                }
//                callBack.call(list);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                callBack.call(list);
//            }
//        });
    }

    public void getActiveMonths(String username,String year,SimpleCallBack<List<MonthOfWork>> callBack){
        DatabaseReference ref = db.getReference("userYears").child(username).child(year);
        ref.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<MonthOfWork> list = new ArrayList<>();
                if(snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {

                        try {
                            list.add(new MonthOfWork(child.getKey(),Integer.parseInt(child.getValue().toString())));
                        }
                        catch (Exception e){
                            callBack.call(new ArrayList<>());
                            return;
                        }

                    }
                    callBack.call(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callBack.call(new ArrayList<>());
            }
        });
    }

    public void checkNumOfWorkDays(String username,SimpleCallBack<Integer> callBack){
        DatabaseReference ref = db.getReference("workDays").child(username);
        ref.orderByChild("year").equalTo(Year.now().getValue()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    if(snapshot.exists()) {

                        Long count = snapshot.getChildrenCount();
                        callBack.call(count.intValue());
                    }
                    else {
                        Toast.makeText(MyApplication.getContext(),"aaaaa",Toast.LENGTH_SHORT).show();
                        callBack.call(0);
                    }
                }
                catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
