package com.example.myapplication.ui.settings;

import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.myapplication.R;
import com.example.myapplication.SharedViewModel;
import com.example.myapplication.SharedViewModelFactory;
import com.example.myapplication.data.User;
import com.example.myapplication.data.UserSettings;

public class SettingsFragment extends PreferenceFragmentCompat {
    private SharedViewModel sharedViewModel;
    private EditTextPreference usernamePreference;
    private EditTextPreference passwordPreference;
    private EditTextPreference mailPreference;
    private EditTextPreference totalDaysPreference;
    private EditTextPreference feePerHourPreference;
    private EditTextPreference debtPreference;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        sharedViewModel = new ViewModelProvider(getActivity(),new SharedViewModelFactory()).get(SharedViewModel.class);
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        usernamePreference = findPreference("username");
        passwordPreference = findPreference("password");
        mailPreference = findPreference("mail");
        totalDaysPreference = findPreference("total_days");
        feePerHourPreference = findPreference("fee_per_hour");
        debtPreference = findPreference("debt");
        User user = sharedViewModel.getUser().getValue();
        UserSettings userSettings = sharedViewModel.getUserSetting().getValue();
        usernamePreference.setText(user.getUsername());
        passwordPreference.setText(user.getPassword());
        mailPreference.setText(user.getMail());
        totalDaysPreference.setText(userSettings.getTotalNumOfDays().toString());
        feePerHourPreference.setText(userSettings.getSumForHour().toString());
        debtPreference.setText(userSettings.getSumOfDebt().toString());

    }

    @Override
    public void onPause() {
        String previousUsername = sharedViewModel.getUser().getValue().getUsername();
        User user = new User(usernamePreference.getText(),passwordPreference.getText(),sharedViewModel.getUser().getValue().getFirstName(),
                sharedViewModel.getUser().getValue().getLastName(),mailPreference.getText());
        UserSettings userSettings = new UserSettings(usernamePreference.getText(),Integer.valueOf(totalDaysPreference.getText()),
                Double.valueOf(debtPreference.getText()),Integer.valueOf(feePerHourPreference.getText()));
        sharedViewModel.updateUserDetails(previousUsername,user,userSettings);
        sharedViewModel.setUser(user);
        sharedViewModel.setUserSetting(userSettings);

        super.onPause();
    }


}