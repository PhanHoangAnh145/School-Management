package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.GradeReport;
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
    public GradeReport findGradeReportById(@PathVariable int id) {
        return this.gradeReportService.findGradeReportById(id);
    }

    @GetMapping()
    public List<GradeReport> findAllGradeReport() {
        return this.gradeReportService.findAllGradeReport();
    }

    @PostMapping("/{transcriptionId}")
    public GradeReport saveGradeReport(@PathVariable int transcriptionId, @RequestBody GradeReport gradeReport) {
        return this.gradeReportService.saveGradeReport(transcriptionId, gradeReport);
    }

    @PutMapping("/{id}")
    public GradeReport updateGradeReportById(@PathVariable int id, @RequestBody GradeReport gradeReport) {
        return this.gradeReportService.updateGradeReportById(id, gradeReport);
    }

    @DeleteMapping("/{id}")
    public void deleteGradeReportById(@PathVariable int id) {
        this.gradeReportService.deleteGradeReportById(id);
    }
}