package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.Employee;

import java.util.List;

public interface EmployeeService {
    public Employee findEmployeeById(Long id);
    public List<Employee> findAllEmployee();
    public Employee saveEmployee(Long SchoolId, Employee employee);
    public Employee updateEmployeeById(Long id, Employee employee);
    public void deleteEmployeeById(Long id);
}
