package com.example.bgroseclose.interviewquestion.Login;

public class LoginModel {

    private String Username;
    private String Password;

    public LoginModel() { }

    public LoginModel(String username, String password) {
        this.Username = username;
        this.Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

}
