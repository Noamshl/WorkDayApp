package com.example.myapplication.ui.WorkAdder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;
import com.example.myapplication.data.SimpleCallBack;
import com.example.myapplication.data.WorkData;
import com.example.myapplication.data.model.WorkRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class AddWorkDayViewModel extends ViewModel {

    private MutableLiveData<WorkDayFormState> workDayFormState = new MutableLiveData<>();
    private WorkRepository workRepository;

    public AddWorkDayViewModel(WorkRepository workRepository) {
       this.workRepository = workRepository;
    }

    public MutableLiveData<WorkDayFormState> getWorkDayFormState() {
        return workDayFormState;
    }

    public void workDayDataChanged(String job, String hour, String date){
        if(!isJobValid(job))
            workDayFormState.setValue(new WorkDayFormState(R.string.invalid_job,null,null));
        else if(!isHourValid(hour))
            workDayFormState.setValue(new WorkDayFormState(null,R.string.invalid_hour,null));
        else if(!isDateValid(date))
            workDayFormState.setValue(new WorkDayFormState(null, null, R.string.invalid_date));
        else
            workDayFormState.setValue(new WorkDayFormState(true));

    }

    private boolean isJobValid(String job) {
        if (job == null) {
            return false;
        }
        return !job.trim().isEmpty();
    }

    private boolean isHourValid(String hour) {
        if (hour == null) {
            return false;
        }
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(hour).matches();
    }

    private boolean isDateValid(String date) {
        if (date == null)
            return false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        simpleDateFormat.setLenient(false);
        try {
            Date date1 = simpleDateFormat.parse(date);
        } catch (ParseException p) {
            return false;
        }
        return true;
    }

    public void addWorkDay(WorkData workData,SimpleCallBack<Boolean> callBack){
        workRepository.addNewWorkDay(workData,callBack);
    }

    public void getUserJobs(String username, SimpleCallBack<List<String>> callBack){
        workRepository.getUserJobs(username,callBack);
    }
}