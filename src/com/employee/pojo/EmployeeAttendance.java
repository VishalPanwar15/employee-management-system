package com.employee.pojo;

import java.time.LocalTime;

public class EmployeeAttendance {

    private int empId;
    private String firstName;
    private String emailId;
    private String deptName;
    private String date;
    private LocalTime inTime;
    private LocalTime outTIme;
    private LocalTime workingHours;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public LocalTime getInTime() {
        return inTime;
    }

    public void setInTime(LocalTime inTime) {
        this.inTime = inTime;
    }

    public LocalTime getOutTIme() {
        return outTIme;
    }

    public void setOutTIme(LocalTime outTIme) {
        this.outTIme = outTIme;
    }

    public LocalTime getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(LocalTime workingHours) {
        this.workingHours = workingHours;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
