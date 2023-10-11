package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping("/")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        logger.info("Received request to get all employees.");
        List<Employee> employees = employeeService.getAllEmployees();
        logger.debug("Returning {} employees.", employees.size());
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Employee>> getFilteredEmployees(@RequestParam int age, @RequestParam String title) {
        if (age < 0 || title == null || title.isEmpty()) {
            logger.error("Invalid request parameters: age={} and title={}", age, title);
            return ResponseEntity.badRequest().build();
        }

        logger.info("Received request to filter employees with age greater than {} and title {}.", age, title);
        List<Employee> employeeList = employeeService.getFilteredEmployees(age, title);
        logger.debug("Returning {} employees that match the criteria.", employeeList.size());
        return ResponseEntity.ok(employeeList);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        if (employee == null || employee.getName() == null || employee.getName().isEmpty()) {
            logger.error("Invalid request body or missing required fields.");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Received request to create a new employee: {}", employee.getName());
        Employee createdEmployee = employeeService.createEmployee(employee);

        if (createdEmployee == null) {
            logger.error("Failed to create a new employee.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        logger.info("New employee created with ID: {}", createdEmployee.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }
}



