package com.example.myapplication.ui.WorkAdder;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.SharedViewModel;
import com.example.myapplication.SharedViewModelFactory;
import com.example.myapplication.data.SimpleCallBack;
import com.example.myapplication.data.WorkData;

import java.util.Calendar;
import java.util.List;

public class AddWorkDayFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
   // private ImageButton calendarBtn;

    private AutoCompleteTextView addHourTxt;
    private AutoCompleteTextView addJobTxt;
    private AddWorkDayViewModel addWorkDayViewModel;
    private SharedViewModel sharedViewModel;
    private  EditText dateTxt;
    private Integer currentMonth;
    private Integer currentYear;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        String[] hours =  getResources().getStringArray(R.array.hour);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),R.layout.dropdown_hour,hours);
        addHourTxt = root.findViewById(R.id.addHourTxt);
        addHourTxt.setAdapter(arrayAdapter);
        addJobTxt = root.findViewById(R.id.add_jobTxt);
        sharedViewModel = new ViewModelProvider(getActivity(),new SharedViewModelFactory()).get(SharedViewModel.class);
       // calendarBtn = root.findViewById(R.id.calendarBtn);


        return root;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addWorkDayViewModel = new ViewModelProvider(this,new AddWorkDayViewModelFactory()).get(AddWorkDayViewModel.class);

        EditText jobText = view.findViewById(R.id.add_jobTxt);
        final EditText hourText = view.findViewById(R.id.addHourTxt);
        final EditText notesText = view.findViewById(R.id.notesTxt);
        final EditText dateText = view.findViewById(R.id.dateTxt);
        dateTxt = view.findViewById(R.id.dateTxt);
        final ImageButton calendarBtn = view.findViewById(R.id.calendarBtn);
        final Button addJobBtn = view.findViewById(R.id.add_jobBtn);
        addJobBtn.setEnabled(false);

        addWorkDayViewModel.getUserJobs(sharedViewModel.getText().getValue(), new SimpleCallBack<List<String>>() {
            @Override
            public void call(List<String> data) {
                ArrayAdapter<String> arrayAdapter;
                if(data == null)
                    arrayAdapter = new ArrayAdapter<>(getContext(),R.layout.dropdown_hour,getResources().getStringArray(R.array.jobs));
                else
                    arrayAdapter = new ArrayAdapter<>(getContext(),R.layout.dropdown_hour,data);
                addJobTxt.setAdapter(arrayAdapter);
            }
        });


        addWorkDayViewModel.getWorkDayFormState().observe(getActivity(), new Observer<WorkDayFormState>() {
            @Override
            public void onChanged(WorkDayFormState workDayFormState) {
                addJobBtn.setEnabled(workDayFormState.isDataValid());
                if(workDayFormState.getJobError()!=null)
                    jobText.setError(getString(workDayFormState.getJobError()));
                if(workDayFormState.getHourError()!=null)
                    hourText.setError(getString(workDayFormState.getHourError()));
                if(workDayFormState.getDateError()!=null)
                    dateText.setError(getString(workDayFormState.getDateError()));

            }
        });

        addJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 WorkData workData = new WorkData(sharedViewModel.getText().getValue(),jobText.getText().toString(),
                         Integer.parseInt(hourText.getText().toString()),dateText.getText().toString(),notesText.getText().toString()
                         ,currentMonth,currentYear);
                 addWorkDayViewModel.addWorkDay(workData, new SimpleCallBack<Boolean>() {
                     @Override
                     public void call(Boolean data) {
                         if(data) {
                             Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
                             dateText.setText("");
                             notesText.setText("");
                             addJobBtn.setEnabled(false);
                         }
                         else
                             Toast.makeText(getContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                     }
                 });
            }
        });

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 showDatePickerDialog();
            }
        });


        TextWatcher afterTextChangedWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                  addWorkDayViewModel.workDayDataChanged(jobText.getText().toString(),hourText.getText().toString(),
                          dateText.getText().toString());
            }
        };
        jobText.addTextChangedListener(afterTextChangedWatcher);
        hourText.addTextChangedListener(afterTextChangedWatcher);
        dateText.addTextChangedListener(afterTextChangedWatcher);




    }
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        currentMonth = month+1;
        currentYear = year;
        String date;
        if(currentMonth<10)
            date= dayOfMonth+"/"+0+currentMonth+"/"+year;
        else
            date= dayOfMonth+"/"+currentMonth+"/"+year;
        dateTxt.setText(date);
        dateTxt.setError("",null);
    }


}