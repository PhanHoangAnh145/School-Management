package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
import vn.edu.ptit.PhanHoangAnh.student_management.service.StudentService;
import vn.edu.ptit.PhanHoangAnh.student_management.service.StudentServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController (StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<ApiResponse<Student>> findStudentById(@PathVariable int id) {
        Student student = this.studentService.findStudentById(id);
        return ApiResponse.success(student);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Student>>> findAllStudent() {
        List<Student> studentList = this.studentService.findAllStudent();
        return ApiResponse.success(studentList);
    }

    @PostMapping("/{classId}")
    public ResponseEntity<ApiResponse<Student>> saveStudent(@PathVariable int classId, @RequestBody Student student) {
        Student studentSave = this.studentService.saveStudent(classId, student);
        return ApiResponse.created(studentSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudentById(@PathVariable int id, @RequestBody Student studentRq) {
        Student studentUpdate = this.studentService.updateStudentById(id, studentRq);
        return ApiResponse.success(studentUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudentById(@PathVariable int id) {
        this.studentService.deleteStudentById(id);
        return ApiResponse.success("delete success...");
    }
}
