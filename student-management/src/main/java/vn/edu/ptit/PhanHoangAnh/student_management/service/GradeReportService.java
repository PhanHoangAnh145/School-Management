package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.GradeReport;
import java.util.List;

public interface GradeReportService {
    GradeReport findGradeReportById(Long id);
    List<GradeReport> findAllGradeReport();
    GradeReport saveGradeReport(Long transcriptionId, GradeReport gradeReport);
    GradeReport updateGradeReportById(Long id, GradeReport gradeReport);
    void deleteGradeReportById(Long id);
}