package com.sanjay.laravel.models.loginModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginFailResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("stats")
    @Expose
    private String stats;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

}