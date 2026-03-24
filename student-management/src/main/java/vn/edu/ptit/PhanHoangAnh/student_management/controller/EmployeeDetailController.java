package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.EmployeeDetail;
import vn.edu.ptit.PhanHoangAnh.student_management.service.EmployeeDetailService;
import java.util.List;

@RestController
@RequestMapping("/api/employee-detail")
public class EmployeeDetailController {

    private EmployeeDetailService employeeDetailService;

    @Autowired
    public EmployeeDetailController(EmployeeDetailService employeeDetailService) {
        this.employeeDetailService = employeeDetailService;
    }

    @GetMapping("/{id}")
    public EmployeeDetail findEmployeeDetailById(@PathVariable int id) {
        return this.employeeDetailService.findEmployeeDetailById(id);
    }

    @GetMapping()
    public List<EmployeeDetail> findAllEmployeeDetail() {
        return this.employeeDetailService.findAllEmployeeDetail();
    }

    @PostMapping("/{employeeId}")
    public EmployeeDetail saveEmployeeDetail(@PathVariable int employeeId, @RequestBody EmployeeDetail employeeDetail) {
        return this.employeeDetailService.saveEmployeeDetail(employeeId, employeeDetail);
    }

    @PutMapping("/{id}")
    public EmployeeDetail updateEmployeeDetailById(@PathVariable int id, @RequestBody EmployeeDetail employeeDetail) {
        return this.employeeDetailService.updateEmployeeDetailById(id, employeeDetail);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeDetailById(@PathVariable int id) {
        this.employeeDetailService.deleteEmployeeDetailById(id);
    }
}