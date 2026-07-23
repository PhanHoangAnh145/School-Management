package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dto.GradeReportResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.GradeReport;
import java.util.List;

public interface GradeReportService {
    GradeReportResponseDTO findGradeReportById(Long id);
    List<GradeReportResponseDTO> findAllGradeReport();
    GradeReportResponseDTO saveGradeReport(Long transcriptionId, GradeReport gradeReport);
    GradeReportResponseDTO updateGradeReportById(Long id, GradeReport gradeReport);
    void deleteGradeReportById(Long id);
}