package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.Employee;

import java.util.List;

public interface EmployeeService {
    public Employee findEmployeeById(int id);
    public List<Employee> findAllEmployee();
    public Employee saveEmployee(int SchoolId, Employee employee);
    public Employee updateEmployeeById(int id, Employee employee);
    public void deleteEmployeeById(int id);
}
