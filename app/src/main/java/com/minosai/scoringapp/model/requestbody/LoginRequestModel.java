package com.minosai.scoringapp.model.requestbody;

import com.google.gson.annotations.SerializedName;

public class LoginRequestModel {

    @SerializedName("emp_id")
    String empId;

    public LoginRequestModel() {

    }

    public LoginRequestModel(String empId) {
        this.empId = empId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
