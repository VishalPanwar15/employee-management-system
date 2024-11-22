package com.employee.pojo;

import javax.xml.crypto.Data;
import java.util.Objects;

public class Employee {

    private int empId;
    private String empFirstName;
    private String empLastName;
    private String empEmailId;
    private int age;
    private String departmentName;
    private double salary;
    private String joiningDate;


//    Create a non-parametrized constructor
    public Employee() {
    }


//    create a parametrized constructor to intialize the data field present in employee class


    public Employee(String empFirstName, String empLastName, String empEmailId, int age, String departmentName, double salary, String joiningDate) {
        this.empFirstName = empFirstName;
        this.empLastName = empLastName;
        this.empEmailId = empEmailId;
        this.age = age;
        this.departmentName = departmentName;
        this.salary = salary;
        this.joiningDate = joiningDate;
    }
//    Getter and setter method present with the help of this we can access the data member outside the class using getter methods
//    with the help of setter we can update the employee value
    public int getEmpId() {
        return empId;
    }
    public void setEmpId(int empId){
        this.empId = empId;
    }

    public String getEmpFirstName() {
        return empFirstName;
    }

    public void setEmpFirstName(String empFirstName) {
        this.empFirstName = empFirstName;
    }

    public String getEmpLastName() {
        return empLastName;
    }

    public void setEmpLastName(String empLastName) {
        this.empLastName = empLastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getJoiningString() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getEmpEmailId() {
        return empEmailId;
    }

    public void setEmpEmailId(String empEmailId) {
        this.empEmailId = empEmailId;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "empNumber='" + empId + '\'' +
                ", empFirstName='" + empFirstName + '\'' +
                ", empLastName='" + empLastName + '\'' +
                ", empEmailId='" + empEmailId + '\'' +
                ", age=" + age +
                ", departmentName='" + departmentName + '\'' +
                ", salary=" + salary +
                ", joiningDate=" + joiningDate + "\n"+
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return age == employee.age && Double.compare(salary, employee.salary) == 0 && Objects.equals(empId, employee.empId) && Objects.equals(empFirstName, employee.empFirstName) && Objects.equals(empLastName, employee.empLastName) && Objects.equals(empEmailId, employee.empEmailId) && Objects.equals(departmentName, employee.departmentName) && Objects.equals(joiningDate, employee.joiningDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, empFirstName, empLastName, empEmailId, age, departmentName, salary, joiningDate);
    }
}
