package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.GradeReportResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.GradeReport;

@Component
public class GradeReportMapper {

    public GradeReportResponseDTO toDTO(GradeReport gradeReport) {
        if (gradeReport == null) {
            return null;
        }

        return GradeReportResponseDTO.builder()
                .id(gradeReport.getId())
                .name(gradeReport.getName())
                .tenMark(gradeReport.getTenMark())
                .transcriptionId(gradeReport.getTranscription() != null ? gradeReport.getTranscription().getId() : null)
                .transcriptionYear(gradeReport.getTranscription() != null ? gradeReport.getTranscription().getYear() : null)
                .build();
    }
}
