package com.example.bgroseclose.interviewquestion.WebView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.bgroseclose.interviewquestion.Adapters.GitHubWebViewClient;
import com.example.bgroseclose.interviewquestion.Login.LoginActivity;
import com.example.bgroseclose.interviewquestion.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WebViewActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private WebView webView;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent startingIntent = getIntent();
        String url = startingIntent.getStringExtra(getString(R.string.passed_url_extra));
        String title = startingIntent.getStringExtra(getString(R.string.passed_title));

        mToolbar = findViewById(R.id.web_view_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        name = findViewById(R.id.web_view_name);
        webView = findViewById(R.id.web_view_view);
        webView.setWebViewClient(new GitHubWebViewClient());
        webView.loadUrl(url);
        name.setText(title);
    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(getString(R.string.return_login_extra), getString(R.string.return_login_extra));
        startActivity(intent);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String savedTime = sharedPref.getString(getString(R.string.save_date_time), null);
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        if(savedTime != null) try {
            Calendar saved = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            saved.setTime(sdf.parse(savedTime));
            saved.add(Calendar.MINUTE, 1);
            if(now.after(saved)) {
                logout();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.save_date_time), now.getTime().toString());
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.web_view_back:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
