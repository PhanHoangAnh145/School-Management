package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.SubjectResponseDTO;
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
    public ResponseEntity<ApiResponse<List<SubjectResponseDTO>>> findAllSubject() {
        List<SubjectResponseDTO> subjectList = this.subjectService.findAllSubject();
        return ApiResponse.success(subjectList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectResponseDTO>> findSubjectById(@PathVariable Long id) {
        SubjectResponseDTO subject = this.subjectService.findSubjectById(id);
        return ApiResponse.success(subject);
    }

    @PostMapping("/{TeacherId}")
    public ResponseEntity<ApiResponse<SubjectResponseDTO>> saveSubject(@PathVariable Long TeacherId, @RequestBody Subject subject) {
        SubjectResponseDTO subjectSave = this.subjectService.saveSubject(TeacherId, subject);
        return ApiResponse.created(subjectSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectResponseDTO>> updateSubjectById(@PathVariable Long id, @RequestBody Subject subject) {
        SubjectResponseDTO subjectUpdate = this.subjectService.updateSubjectById(id, subject);
        return ApiResponse.success(subjectUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSubjectById(@PathVariable Long id) {
        this.subjectService.deleteSubjectById(id);
        return ApiResponse.success("delete success...");
    }
}
