package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentRecord;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
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
    public ResponseEntity<ApiResponse<StudentRecord>> findStudentRecordById(@PathVariable int id) {
        StudentRecord studentRecord = this.studentRecordService.findStudentRecordById(id);
        return ApiResponse.success(studentRecord);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<StudentRecord>>> findAllStudentRecord() {
        List<StudentRecord> studentRecordList = this.studentRecordService.findAllStudentRecord();
        return ApiResponse.success(studentRecordList);
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<ApiResponse<StudentRecord>> saveStudentRecord(@PathVariable int studentId, @RequestBody StudentRecord studentRecord) {
        StudentRecord studentRecordSave = this.studentRecordService.saveStudentRecord(studentId, studentRecord);
        return ApiResponse.created(studentRecordSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentRecord>> updateStudentRecordById(@PathVariable int id, @RequestBody StudentRecord studentRecord) {
        StudentRecord studentRecordUpdate = this.studentRecordService.updateStudentRecordById(id, studentRecord);
        return ApiResponse.success(studentRecordUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudentRecordById(@PathVariable int id) {
        this.studentRecordService.deleteStudentRecordById(id);
        return ApiResponse.success("delete success...");
    }
}