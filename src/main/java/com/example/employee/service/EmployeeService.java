package com.example.employee.service;

import com.example.employee.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();
    List<Employee> getFilteredEmployees(int age, String title);

    Employee createEmployee(Employee employee);


}
