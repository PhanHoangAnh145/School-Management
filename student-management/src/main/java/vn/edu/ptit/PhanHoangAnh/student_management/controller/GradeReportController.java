package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.GradeReportResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.GradeReport;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
import vn.edu.ptit.PhanHoangAnh.student_management.service.GradeReportService;
import java.util.List;

@RestController
@RequestMapping("/api/grade-report")
public class GradeReportController {

    private GradeReportService gradeReportService;

    @Autowired
    public GradeReportController(GradeReportService gradeReportService) {
        this.gradeReportService = gradeReportService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GradeReportResponseDTO>> findGradeReportById(@PathVariable Long id) {
        GradeReportResponseDTO gradeReport = this.gradeReportService.findGradeReportById(id);
        return ApiResponse.success(gradeReport);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<GradeReportResponseDTO>>> findAllGradeReport() {
        List<GradeReportResponseDTO> gradeReportList = this.gradeReportService.findAllGradeReport();
        return ApiResponse.success(gradeReportList);
    }

    @PostMapping("/{transcriptionId}")
    public ResponseEntity<ApiResponse<GradeReportResponseDTO>> saveGradeReport(@PathVariable Long transcriptionId, @RequestBody GradeReport gradeReport) {
        GradeReportResponseDTO gradeReportSave = this.gradeReportService.saveGradeReport(transcriptionId, gradeReport);
        return ApiResponse.created(gradeReportSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GradeReportResponseDTO>> updateGradeReportById(@PathVariable Long id, @RequestBody GradeReport gradeReport) {
        GradeReportResponseDTO gradeReportUpdate = this.gradeReportService.updateGradeReportById(id, gradeReport);
        return ApiResponse.success(gradeReportUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteGradeReportById(@PathVariable Long id) {
        this.gradeReportService.deleteGradeReportById(id);
        return ApiResponse.success("delete success...");
    }
}