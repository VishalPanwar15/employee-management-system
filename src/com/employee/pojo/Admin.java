package com.employee.pojo;

public class Admin {
    private String adminId;
    private String emailId;
    private String password;

    public Admin() {
    }

    public Admin(String adminId, String emailId, String password) {
        this.adminId = adminId;
        this.emailId = emailId;
        this.password = password;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
