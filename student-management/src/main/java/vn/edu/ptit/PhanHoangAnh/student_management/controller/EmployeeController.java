package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Employee;
import vn.edu.ptit.PhanHoangAnh.student_management.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController (EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public Employee findEmployeeById(@PathVariable int id) {
        return this.employeeService.findEmployeeById(id);
    }

    @GetMapping()
    public List<Employee> findAllEmployee() {
        return this.employeeService.findAllEmployee();
    }

    @PostMapping("/{schoolId}")
    public Employee saveEmployee(@PathVariable int schoolId, @RequestBody Employee employee) {
        return this.employeeService.saveEmployee(schoolId, employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable int id, @RequestBody Employee employee) {
        return this.employeeService.updateEmployeeById(id, employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        this.employeeService.deleteEmployeeById(id);
    }
}
