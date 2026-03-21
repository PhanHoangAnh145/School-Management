package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentRecord;
import vn.edu.ptit.PhanHoangAnh.student_management.service.StudentRecordService;
import java.util.List;

@RestController
@RequestMapping("/api/student-record")
public class StudentRecordController {

    private StudentRecordService studentRecordService;

    @Autowired
    public StudentRecordController(StudentRecordService studentRecordService) {
        this.studentRecordService = studentRecordService;
    }

    @GetMapping("/{id}")
    public StudentRecord findStudentRecordById(@PathVariable int id) {
        return this.studentRecordService.findStudentRecordById(id);
    }

    @GetMapping()
    public List<StudentRecord> findAllStudentRecord() {
        return this.studentRecordService.findAllStudentRecord();
    }

    @PostMapping("/{studentId}")
    public StudentRecord saveStudentRecord(@PathVariable int studentId, @RequestBody StudentRecord studentRecord) {
        return this.studentRecordService.saveStudentRecord(studentId, studentRecord);
    }

    @PutMapping("/{id}")
    public StudentRecord updateStudentRecordById(@PathVariable int id, @RequestBody StudentRecord studentRecord) {
        return this.studentRecordService.updateStudentRecordById(id, studentRecord);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentRecordById(@PathVariable int id) {
        this.studentRecordService.deleteStudentRecordById(id);
    }
}