package com.minosai.scoringapp.model;

import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("emp_id")
    String empId;
    @SerializedName("phone_number")
    String phoneNumber;

    public Employee() {

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
