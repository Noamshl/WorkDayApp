package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.SharedViewModel;
import com.example.myapplication.SharedViewModelFactory;
import com.example.myapplication.data.User;
import com.example.myapplication.data.UserSettings;

import java.time.Year;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private SharedViewModel sharedViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this,new HomeViewModelFactory()).get(HomeViewModel.class);
        sharedViewModel = new ViewModelProvider(getActivity(),new SharedViewModelFactory()).get(SharedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);



        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(s.equals(""))
                    NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.nav_login);

            }
        });
        homeViewModel.checkLogin(sharedViewModel);
        final TextView progressTxt = view.findViewById(R.id.home_progressTxt);
        final ProgressBar progressBar = view.findViewById(R.id.progress_num_of_days);
        final TextView numOfDaysPercentTxt = view.findViewById(R.id.num_of_days_percentage);
        User user = sharedViewModel.getUser().getValue();

        UserSettings userSettings = sharedViewModel.getUserSetting().getValue();
        String text = sharedViewModel.getNumOfWorkDays().getValue()+"/"+userSettings.getTotalNumOfDays();

        progressTxt.setText(text);

        sharedViewModel.getUserSetting().observe(getViewLifecycleOwner(), new Observer<UserSettings>() {
            @Override
            public void onChanged(UserSettings userSettings) {
                Integer totalNumOfDays = userSettings.getTotalNumOfDays();
                Integer numOfWorkDays = sharedViewModel.getNumOfWorkDays().getValue();
                Double percentage = 100.0;
                String text = numOfWorkDays+"/"+totalNumOfDays;
                progressTxt.setText(text);
                if(totalNumOfDays != 0 && totalNumOfDays>numOfWorkDays)
                    percentage = (numOfWorkDays.doubleValue()/totalNumOfDays.doubleValue())*100;
                progressBar.setProgress(percentage.intValue());
                text = percentage.intValue()+"%";
                numOfDaysPercentTxt.setText(text);

            }
        });

        sharedViewModel.getNumOfWorkDays().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                User user = sharedViewModel.getUser().getValue();
                UserSettings userSettings = sharedViewModel.getUserSetting().getValue();
                String text = integer+"/"+userSettings.getTotalNumOfDays();
                progressTxt.setText(text);
            }
        });



//


    }

}