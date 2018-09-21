package com.example.bgroseclose.interviewquestion.Login;

import android.util.Patterns;

import java.util.regex.Pattern;

public class LoginPresenter {

    private LoginModel loginModel;
    private View view;

    public LoginPresenter(View view) {
        this.view = view;
        loginModel = new LoginModel();
    }

    public void updateUsername(String username) {
        loginModel.setUsername(username);
    }

    public void updatePassword(String password) {
        loginModel.setPassword(password);
    }

    public boolean validateLogin(String username, String password) {
        updateUsername(username);
        updatePassword(password);
        return (validateUsername() && validatePassword());
    }

    private boolean validateUsername() {
        return Patterns.EMAIL_ADDRESS.matcher(loginModel.getUsername()).matches();
    }
    private boolean validatePassword() {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}");
        return pattern.matcher(loginModel.getPassword()).matches();
    }


    public interface View {
        void openLoginInformationDialog();
        void openRepositoryPage();
    }
}

