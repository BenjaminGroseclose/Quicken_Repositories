package com.example.bgroseclose.interviewquestion.Repository;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RepositoryModel {

    @SerializedName("name")
    private String name;
    @SerializedName("updated_at")
    private String updateDate;
    @SerializedName("description")
    private String description;
    @SerializedName("html_url")
    private String url;

    public String getName() {
        return name;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
