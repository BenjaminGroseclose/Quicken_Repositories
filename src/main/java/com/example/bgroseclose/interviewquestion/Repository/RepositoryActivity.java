package com.example.bgroseclose.interviewquestion.Repository;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bgroseclose.interviewquestion.Adapters.RepositoryAdapter;
import com.example.bgroseclose.interviewquestion.Login.LoginActivity;
import com.example.bgroseclose.interviewquestion.R;
import com.example.bgroseclose.interviewquestion.Retrofit.GitHubClient;
import com.example.bgroseclose.interviewquestion.Retrofit.RetrofitClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Todo: Implement a presenter for this class, that will function separate of this Activity
public class RepositoryActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView repositoryRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);

        mToolbar = findViewById(R.id.repository_toolbar);
        setSupportActionBar(mToolbar);

        repositoryRecyclerView = findViewById(R.id.repository_recycler_view);
        setAdapterWithRetrofit();

    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(getString(R.string.return_login_extra), getString(R.string.return_login_extra));
        startActivity(intent);
        finish();
    }

    private void setAdapterWithRetrofit() {
        GitHubClient service = RetrofitClient.getRetrofit().create(GitHubClient.class);
        Call<List<RepositoryModel>> call = service.getRepositories();
        call.enqueue(new Callback<List<RepositoryModel>>() {
            @Override
            public void onResponse(Call<List<RepositoryModel>> call, Response<List<RepositoryModel>> response) {
                RepositoryAdapter adapter = new RepositoryAdapter(response.body());
                repositoryRecyclerView.setAdapter(adapter);
                repositoryRecyclerView.setLayoutManager(new LinearLayoutManager(RepositoryActivity.this));
            }

            @Override
            public void onFailure(Call<List<RepositoryModel>> call, Throwable t) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(RepositoryActivity.this);
                dialog.setTitle("Couldn't Load Repositories")
                        .setCancelable(false)
                        .setMessage("Was unable to get the repositories from the api. Please logout and try again")
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_repository, menu);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkTimeOnRestart();
    }

    private void checkTimeOnRestart() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String savedTime = sharedPref.getString(getString(R.string.save_date_time), null);
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        if(savedTime != null) {
            try {
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
    }

    private void saveTime() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.save_date_time), now.getTime().toString());
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveTime();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.repository_logout:
                logout();
                break;
        }
        return true;
    }
}
