package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentResponseDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> findStudentById(@PathVariable Long id) {
        StudentResponseDTO student = this.studentService.findStudentById(id);
        return ApiResponse.success(student);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> findAllStudent() {
        List<StudentResponseDTO> studentList = this.studentService.findAllStudent();
        return ApiResponse.success(studentList);
    }

    @PostMapping("/{classId}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> saveStudent(@PathVariable Long classId, @RequestBody Student student) {
        StudentResponseDTO studentSave = this.studentService.saveStudent(classId, student);
        return ApiResponse.created(studentSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> updateStudentById(@PathVariable Long id, @RequestBody Student studentRq) {
        StudentResponseDTO studentUpdate = this.studentService.updateStudentById(id, studentRq);
        return ApiResponse.success(studentUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudentById(@PathVariable Long id) {
        this.studentService.deleteStudentById(id);
        return ApiResponse.success("delete success...");
    }
}
