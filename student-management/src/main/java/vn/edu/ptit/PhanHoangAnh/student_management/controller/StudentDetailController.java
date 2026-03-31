package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentDetail;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
import vn.edu.ptit.PhanHoangAnh.student_management.service.StudentDetailService;

import java.util.List;

@RestController
@RequestMapping("/api/student-detail")
public class StudentDetailController {

    private StudentDetailService studentDetailService;

    @Autowired
    public StudentDetailController(StudentDetailService studentDetailService) {
        this.studentDetailService = studentDetailService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDetail>> findStudentDetailById(@PathVariable int id) {
        StudentDetail studentDetail = this.studentDetailService.findStudentDetailById(id);
        return ApiResponse.success(studentDetail);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<StudentDetail>>> findAllStudentDetail() {
        List<StudentDetail> studentDetailList = this.studentDetailService.findAllStudentDetail();
        return ApiResponse.success(studentDetailList);
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<ApiResponse<StudentDetail>> saveStudentDetail(@PathVariable int studentId, @RequestBody StudentDetail studentDetail) {
        StudentDetail studentDetailSave = this.studentDetailService.saveStudentDetail(studentId, studentDetail);
        return ApiResponse.created(studentDetailSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDetail>> updateStudentDetailById(@PathVariable int id, @RequestBody StudentDetail studentDetail) {
        StudentDetail studentDetailUpdate = this.studentDetailService.updateStudentDetailById(id, studentDetail);
        return ApiResponse.success(studentDetailUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudentDetailById(@PathVariable int id) {
        this.studentDetailService.deleteStudentDetailById(id);
        return ApiResponse.success("delete success...");
    }
}