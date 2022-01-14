package com.dorofeev.testemployees.screens.employees;

import com.dorofeev.testemployees.pojo.Employee;

import java.util.List;

public interface EmployeesListView {
    void showData(List<Employee> employeeList);
    void showToast(String message);
}
