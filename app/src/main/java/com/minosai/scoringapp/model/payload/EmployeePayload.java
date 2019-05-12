package com.minosai.scoringapp.model.payload;

import com.minosai.scoringapp.model.Employee;

public class EmployeePayload {

    Employee employee;

    public EmployeePayload() {

    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
