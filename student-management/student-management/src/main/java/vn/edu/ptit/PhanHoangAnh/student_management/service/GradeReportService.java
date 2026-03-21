package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.GradeReport;
import java.util.List;

public interface GradeReportService {
    GradeReport findGradeReportById(int id);
    List<GradeReport> findAllGradeReport();
    GradeReport saveGradeReport(int transcriptionId, GradeReport gradeReport);
    GradeReport updateGradeReportById(int id, GradeReport gradeReport);
    void deleteGradeReportById(int id);
}