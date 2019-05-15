package com.minosai.scoringapp.model.requestbody;

import com.google.gson.annotations.SerializedName;

public class EmpIdRequestModel {

    @SerializedName("emp_id")
    String empId;

    public EmpIdRequestModel() {

    }

    public EmpIdRequestModel(String empId) {
        this.empId = empId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
