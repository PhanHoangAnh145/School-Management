package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.SubjectResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Subject;

@Component
public class SubjectMapper {

    public SubjectResponseDTO toDTO(Subject subject) {
        if (subject == null) {
            return null;
        }

        return SubjectResponseDTO.builder()
                .id(subject.getId())
                .name(subject.getName())
                .build();
    }
}
