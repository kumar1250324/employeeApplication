package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public List<Employee> getAllEmployees() {
        logger.info("Getting all employees.");
        List<Employee> employees = employeeRepository.findAll();
        logger.debug("Retrieved {} employees.", employees.size());
        return employees;
    }

    @Override
    public List<Employee> getFilteredEmployees(int age, String title) {
        logger.info("Getting filtered employees with age greater than {} and title {}.", age, title);
        List<Employee> employeeList = employeeRepository.findByTitleAndDobGreaterThan(title, age);
        logger.debug("Retrieved {} employees that match the criteria.", employeeList.size());
        return employeeList;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        logger.info("Creating a new employee: {}", employee.getName());

        if (employee.getSin() != null && employee.getSin().length() > 4) {
            String last4Digits = employee.getSin().substring(employee.getSin().length() - 4);
            employee.setSin(last4Digits);
        }

        Employee createdEmployee = employeeRepository.save(employee);
        logger.info("New employee created with ID: {}", createdEmployee.getId());
        return createdEmployee;
    }
}

