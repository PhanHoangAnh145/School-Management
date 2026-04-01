package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.EmployeeDetail;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
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
    public ResponseEntity<ApiResponse<EmployeeDetail>> findEmployeeDetailById(@PathVariable Long id) {
        EmployeeDetail employeeDetail = this.employeeDetailService.findEmployeeDetailById(id);
        return ApiResponse.success(employeeDetail);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<EmployeeDetail>>> findAllEmployeeDetail() {
        List<EmployeeDetail> employeeDetailList = this.employeeDetailService.findAllEmployeeDetail();
        return ApiResponse.success(employeeDetailList);
    }

    @PostMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<EmployeeDetail>> saveEmployeeDetail(@PathVariable Long employeeId, @RequestBody EmployeeDetail employeeDetail) {
        EmployeeDetail employeeDetailSave = this.employeeDetailService.saveEmployeeDetail(employeeId, employeeDetail);
        return ApiResponse.created(employeeDetailSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDetail>> updateEmployeeDetailById(@PathVariable Long id, @RequestBody EmployeeDetail employeeDetail) {
        EmployeeDetail employeeDetailUpdate = this.employeeDetailService.updateEmployeeDetailById(id, employeeDetail);
        return ApiResponse.success(employeeDetailUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployeeDetailById(@PathVariable Long id) {
        this.employeeDetailService.deleteEmployeeDetailById(id);
        return ApiResponse.success("delete success...");
    }
}