package com.example.demo.service;

import com.example.demo.modal.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


public interface EmpService {
    ArrayList<Employee> findAllEmployee();
    Employee findAllEmployeeByID(long id);
    void addEmployee();
    void deleteAllData();
}
