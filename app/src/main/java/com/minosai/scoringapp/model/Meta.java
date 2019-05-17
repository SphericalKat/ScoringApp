package com.minosai.scoringapp.model;

import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("status")
    boolean statusSuccess;
    String message;
    int code;

    public Meta() {

    }

    public boolean isStatusSuccess() {
        return statusSuccess;
    }

    public void setStatusSuccess(boolean statusSuccess) {
        this.statusSuccess = statusSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
