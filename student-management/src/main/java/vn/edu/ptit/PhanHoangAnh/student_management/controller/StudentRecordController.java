package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentRecordRequestDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentRecordResponseDTO;
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
    public ResponseEntity<ApiResponse<StudentRecordResponseDTO>> findStudentRecordById(@PathVariable Long id) {
        StudentRecordResponseDTO studentRecord = this.studentRecordService.findByStudentId(id);
        return ApiResponse.success(studentRecord);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<StudentRecordResponseDTO>>> findAllStudentRecord() {
        List<StudentRecordResponseDTO> studentRecordList = this.studentRecordService.findAllStudentRecord();
        return ApiResponse.success(studentRecordList);
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<ApiResponse<StudentRecordResponseDTO>> saveStudentRecord(@PathVariable Long studentId, @RequestBody StudentRecordRequestDTO studentRecordRequestDTO) {
        StudentRecordResponseDTO studentRecordSave = this.studentRecordService.saveStudentRecord(studentId, studentRecordRequestDTO);
        return ApiResponse.created(studentRecordSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentRecordResponseDTO>> updateStudentRecordById(@PathVariable Long id, @RequestBody StudentRecordRequestDTO studentRecordRequestDTO) {
        StudentRecordResponseDTO studentRecordUpdate = this.studentRecordService.updateStudentRecordById(id, studentRecordRequestDTO);
        return ApiResponse.success(studentRecordUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudentRecordById(@PathVariable Long id) {
        this.studentRecordService.deleteStudentRecordById(id);
        return ApiResponse.success("delete success...");
    }
}