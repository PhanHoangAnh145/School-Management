package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;
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
    public Student findStudentById(@PathVariable int id) {
        return this.studentService.findStudentById(id);
    }

    @GetMapping()
    public List<Student> findAllStudent() {
        return this.studentService.findAllStudent();
    }

    @PostMapping("/{classId}")
    public Student saveStudent(@PathVariable int classId, @RequestBody Student student) {
        return this.studentService.saveStudent(classId, student);
    }

    @PutMapping("/{id}")
    public Student updateStudentById(@PathVariable int id, @RequestBody Student studentRq) {
        return this.studentService.updateStudentById(id, studentRq);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable int id) {
        this.studentService.deleteStudentById(id);
    }
}
