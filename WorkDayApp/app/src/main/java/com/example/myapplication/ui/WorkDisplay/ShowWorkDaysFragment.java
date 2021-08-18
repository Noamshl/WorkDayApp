package com.example.myapplication.ui.WorkDisplay;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.SharedViewModel;
import com.example.myapplication.SharedViewModelFactory;
import com.example.myapplication.data.MonthOfWork;
import com.example.myapplication.data.SimpleCallBack;

import java.util.ArrayList;
import java.util.List;

public class ShowWorkDaysFragment extends Fragment {
    private String[] months;
    private List<MonthOfWork> monthOfWorks;
    private ShowWorkDaysViewModel showWorkDaysViewModel;
    private SharedViewModel sharedViewModel;
    private RecyclerView recyclerView;
    private MonthsAdapter monthsAdapter;
    private String username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        showWorkDaysViewModel = new ViewModelProvider(this,new ShowWorkDaysViewModelFactory()).get(ShowWorkDaysViewModel.class);
        sharedViewModel = new ViewModelProvider(getActivity(),new SharedViewModelFactory()).get(SharedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AutoCompleteTextView yearsTxt = view.findViewById(R.id.yearsTxt);
        months = getResources().getStringArray(R.array.months);
        username = sharedViewModel.getText().getValue();
        initMonths();
        recyclerView = view.findViewById(R.id.months_recyclerView);
        monthsAdapter = new MonthsAdapter(getContext(),monthOfWorks,username);
        recyclerView.setAdapter(monthsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setYears(view);
        yearsTxt.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                monthsAdapter.setYear(s.toString());
                showWorkDaysViewModel.getActiveMonths(username, s.toString(), new SimpleCallBack<List<MonthOfWork>>() {
                    @Override
                    public void call(List<MonthOfWork> data) {
                        monthsAdapter.setMonthOfWorks(data);
                        monthsAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    private void initMonths(){
        monthOfWorks = new ArrayList<>();
        monthOfWorks.add(new MonthOfWork("January",1));
        monthOfWorks.add(new MonthOfWork("February",2));
        monthOfWorks.add(new MonthOfWork("March",3));
        monthOfWorks.add(new MonthOfWork("April",4));
        monthOfWorks.add(new MonthOfWork("May",5));
        monthOfWorks.add(new MonthOfWork("June",6));
        monthOfWorks.add(new MonthOfWork("July",7));
        monthOfWorks.add(new MonthOfWork("August",8));
        monthOfWorks.add(new MonthOfWork("September",9));
        monthOfWorks.add(new MonthOfWork("October",10));
        monthOfWorks.add(new MonthOfWork("November",11));
        monthOfWorks.add(new MonthOfWork("December",12));
    }

    private void setYears(View view){
        AutoCompleteTextView yearsTxt = view.findViewById(R.id.yearsTxt);
        showWorkDaysViewModel.getYears(username, new SimpleCallBack<List<String>>() {
            @Override
            public void call(List<String> data) {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),R.layout.dropdown_hour,data);
                yearsTxt.setAdapter(arrayAdapter);
                if(!data.isEmpty()) {
                    String recentYear = data.get(data.size() - 1);
                    yearsTxt.setText(recentYear);


                }
            }
        });
    }
}