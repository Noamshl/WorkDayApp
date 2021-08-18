package com.example.myapplication.ui.register;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.User;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext(),"aaa",Toast.LENGTH_SHORT).show();
        mViewModel = new ViewModelProvider(this,new RegisterViewModelFactory()).get(RegisterViewModel.class);
        final EditText usernameEditText = view.findViewById(R.id.registerUsername);
        final EditText passwordEditText = view.findViewById(R.id.registerPassword);
        final EditText emailEditText = view.findViewById(R.id.registerEmailAddress);
        final Button registerButton = view.findViewById(R.id.registerBtn);
        registerButton.setEnabled(false);
        mViewModel.getRegisterFormState().observe(getActivity(), new Observer<RegisterFormState>() {
            @Override
            public void onChanged(RegisterFormState registerFormState) {
                if(registerFormState == null)
                    return;
                registerButton.setEnabled(registerFormState.isDataValid());
                if(registerFormState.getUsernameError()!=null)
                    usernameEditText.setError(getString(registerFormState.getUsernameError()));
                if(registerFormState.getPasswordError()!=null)
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                if(registerFormState.getEmailError()!=null)
                    emailEditText.setError(getString(registerFormState.getEmailError()));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(usernameEditText.getText().toString(),passwordEditText.getText().toString(),"","",
                        emailEditText.getText().toString());
                mViewModel.register(data -> {
                    if(data){
                        Toast.makeText(getContext(),"Register success",Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(RegisterFragment.this).navigate(R.id.nav_login);
                    }
                    else
                        Toast.makeText(getContext(),"Username already exist",Toast.LENGTH_SHORT).show();
                },user);
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 mViewModel.registerDataChanged(usernameEditText.getText().toString(),passwordEditText.getText().toString(),
                         emailEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
    }
}