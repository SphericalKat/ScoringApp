package com.minosai.scoringapp.model.requestbody;

import com.google.gson.annotations.SerializedName;

public class RegisterRequestModel {

    @SerializedName("emp_id")
    String empId;
    @SerializedName("phone_number")
    String phoneNumber;

    public RegisterRequestModel(String empId, String phoneNumber, String groupName) {
        this.empId = empId;
        this.phoneNumber = phoneNumber;
    }

    public RegisterRequestModel() {
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
