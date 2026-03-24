package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentDetail;
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
    public StudentDetail findStudentDetailById(@PathVariable int id) {
        return this.studentDetailService.findStudentDetailById(id);
    }

    @GetMapping()
    public List<StudentDetail> findAllStudentDetail() {
        return this.studentDetailService.findAllStudentDetail();
    }

    @PostMapping("/{studentId}")
    public StudentDetail saveStudentDetail(@PathVariable int studentId, @RequestBody StudentDetail studentDetail) {
        return this.studentDetailService.saveStudentDetail(studentId, studentDetail);
    }

    @PutMapping("/{id}")
    public StudentDetail updateStudentDetailById(@PathVariable int id, @RequestBody StudentDetail studentDetail) {
        return this.studentDetailService.updateStudentDetailById(id, studentDetail);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentDetailById(@PathVariable int id) {
        this.studentDetailService.deleteStudentDetailById(id);
    }
}