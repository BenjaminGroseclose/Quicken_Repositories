package com.example.bgroseclose.interviewquestion.Login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bgroseclose.interviewquestion.R;
import com.example.bgroseclose.interviewquestion.Repository.RepositoryActivity;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.View {

    private Toolbar mToolbar;
    private Button mBtnLogin;
    private EditText mUsernameEditText, mPasswordEditText;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginPresenter = new LoginPresenter(this);
        mUsernameEditText = findViewById(R.id.edit_username);
        mPasswordEditText = findViewById(R.id.edit_password);
        mBtnLogin = findViewById(R.id.button_login);

        handleActivityStart();

        mToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = mUsernameEditText.getText().toString();
                password = mPasswordEditText.getText().toString();
                if ((!username.isEmpty()) && (!password.isEmpty()))
                    login();
                else
                    openLoginInformationDialog();
            }
        });
    }

    private void handleActivityStart() {
        Intent startingIntent = getIntent();
        String loggedOut = startingIntent.getStringExtra(getString(R.string.return_login_extra));
        if(loggedOut != null) {
            if(loggedOut.equals(getString(R.string.return_login_extra))) {
                mUsernameEditText.setText("");
                mPasswordEditText.setText("");
            }
        } else {
            String[] loginInfo = retrieveLoginInformation();
            if((!loginInfo[0].isEmpty()) || (!loginInfo[1].isEmpty())) {
                mUsernameEditText.setText(loginInfo[0]);
                mPasswordEditText.setText(loginInfo[1]);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_info:
                openLoginInformationDialog();
                break;
        }
        return true;
    }

    private void login() {
        if(mLoginPresenter.validateLogin(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString()))
            openRepositoryPage();
        else
            openLoginInformationDialog();
    }

    private String[] retrieveLoginInformation() {
        String[] loginInfo = new String[2];
        String defaultValue = "";
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        loginInfo[0] = sharedPref.getString(getString(R.string.login_save_username), defaultValue);
        loginInfo[1] = sharedPref.getString(getString(R.string.login_save_password), defaultValue);
        return loginInfo;
    }

    private void saveLoginInfomation() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.login_save_username), mUsernameEditText.getText().toString());
        editor.putString(getString(R.string.login_save_password), mPasswordEditText.getText().toString());
        editor.commit();
    }

    @Override
    public void openRepositoryPage() {
        saveLoginInfomation();
        Intent intent = new Intent(this, RepositoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void openLoginInformationDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater = LoginActivity.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.layout_login_info_dialog, null))
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
