package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Subject;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
import vn.edu.ptit.PhanHoangAnh.student_management.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    private SubjectService subjectService;

    @Autowired
    public SubjectController (SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Subject>>> findAllSubject() {
        List<Subject> subjectList = this.subjectService.findAllSubject();
        return ApiResponse.success(subjectList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Subject>> findSubjectById(@PathVariable int id) {
        Subject subject = this.subjectService.findSubjectById(id);
        return ApiResponse.success(subject);
    }

    @PostMapping("/{TeacherId}")
    public ResponseEntity<ApiResponse<Subject>> saveSubject(@PathVariable int id, @RequestBody Subject subject) {
        Subject subjectSave = this.subjectService.saveSubject(id, subject);
        return ApiResponse.created(subjectSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Subject>> updateSubjectById(@PathVariable int id, @RequestBody Subject subject) {
        Subject subjectUpdate = this.subjectService.updateSubjectById(id, subject);
        return ApiResponse.success(subjectUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSubjectById(@PathVariable int id) {
        this.subjectService.deleteSubjectById(id);
        return ApiResponse.success("delete success...");
    }
}
