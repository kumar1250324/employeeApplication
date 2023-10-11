package com.example.employee.repository;

import com.example.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.title = :title AND FUNCTION('YEAR', CURRENT_DATE()) - FUNCTION('YEAR', e.dob) > :age")
    List<Employee> findByTitleAndDobGreaterThan(@Param("title") String title, @Param("age") int age);
}

