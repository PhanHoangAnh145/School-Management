package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dto.EmployeeDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Employee;

import java.util.List;

public interface EmployeeService {
    public EmployeeDTO findEmployeeById(Long id);
    public List<EmployeeDTO> findAllEmployee();
    public EmployeeDTO saveEmployee(Long SchoolId, Employee employee);
    public EmployeeDTO updateEmployeeById(Long id, Employee employee);
    public void deleteEmployeeById(Long id);
}
