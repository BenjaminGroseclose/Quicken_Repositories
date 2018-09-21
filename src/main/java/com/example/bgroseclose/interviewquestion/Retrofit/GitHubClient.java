package com.example.bgroseclose.interviewquestion.Retrofit;

import com.example.bgroseclose.interviewquestion.Repository.RepositoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GitHubClient {
    @GET("repos")
    Call<List<RepositoryModel>> getRepositories();
}
