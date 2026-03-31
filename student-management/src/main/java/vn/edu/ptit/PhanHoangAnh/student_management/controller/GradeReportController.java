package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ApiResponse<GradeReport>> findGradeReportById(@PathVariable int id) {
        GradeReport gradeReport = this.gradeReportService.findGradeReportById(id);
        return ApiResponse.success(gradeReport);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<GradeReport>>> findAllGradeReport() {
        List<GradeReport> gradeReportList = this.gradeReportService.findAllGradeReport();
        return ApiResponse.success(gradeReportList);
    }

    @PostMapping("/{transcriptionId}")
    public ResponseEntity<ApiResponse<GradeReport>> saveGradeReport(@PathVariable int transcriptionId, @RequestBody GradeReport gradeReport) {
        GradeReport gradeReportSave = this.gradeReportService.saveGradeReport(transcriptionId, gradeReport);
        return ApiResponse.created(gradeReportSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GradeReport>> updateGradeReportById(@PathVariable int id, @RequestBody GradeReport gradeReport) {
        GradeReport gradeReportUpdate = this.gradeReportService.updateGradeReportById(id, gradeReport);
        return ApiResponse.success(gradeReportUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteGradeReportById(@PathVariable int id) {
        this.gradeReportService.deleteGradeReportById(id);
        return ApiResponse.success("delete success...");
    }
}