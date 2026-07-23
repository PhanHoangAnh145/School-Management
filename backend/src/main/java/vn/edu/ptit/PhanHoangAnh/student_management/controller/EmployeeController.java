package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.EmployeeDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Employee;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
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
    public ResponseEntity<ApiResponse<EmployeeDTO>> findEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = this.employeeService.findEmployeeById(id);
        return ApiResponse.success(employee);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> findAllEmployee() {
        List<EmployeeDTO> employeeList = this.employeeService.findAllEmployee();
        return ApiResponse.success(employeeList);
    }

    @PostMapping("/{schoolId}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> saveEmployee(@PathVariable Long schoolId, @RequestBody Employee employee) {
        EmployeeDTO employeeSave = this.employeeService.saveEmployee(schoolId, employee);
        return ApiResponse.created(employeeSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDTO>> updateEmployeeById(@PathVariable Long id, @RequestBody Employee employee) {
        EmployeeDTO employeeUpdate = this.employeeService.updateEmployeeById(id, employee);
        return ApiResponse.success(employeeUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployeeById(@PathVariable Long id) {
        this.employeeService.deleteEmployeeById(id);
        return ApiResponse.success("delete success...");
    }
}
