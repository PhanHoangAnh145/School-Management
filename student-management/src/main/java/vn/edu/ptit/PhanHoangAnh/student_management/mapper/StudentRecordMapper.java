package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentRecordResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentRecord;

@Component
public class StudentRecordMapper {

    public StudentRecordResponseDTO toDTO(StudentRecord studentRecord) {
        if (studentRecord == null) {
            return null;
        }

        return StudentRecordResponseDTO.builder()
                .id(studentRecord.getId())
                .description(studentRecord.getDescription())
                .studentId(studentRecord.getStudent() != null ? studentRecord.getStudent().getId() : null)
                .studentName(studentRecord.getStudent() != null ? studentRecord.getStudent().getName() : "N/A")
                .build();
    }
}
