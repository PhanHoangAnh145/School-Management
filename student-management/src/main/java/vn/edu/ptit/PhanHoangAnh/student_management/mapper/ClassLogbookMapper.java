package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.ClassLogbookResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.ClassLogbook;

@Component
public class ClassLogbookMapper {

    public ClassLogbookResponseDTO toDTO(ClassLogbook classLogbook) {
        if (classLogbook == null) {
            return null;
        }

        return ClassLogbookResponseDTO.builder()
                .id(classLogbook.getId())
                .description(classLogbook.getDescription())
                .classId(classLogbook.getClazz() != null ? classLogbook.getClazz().getId() : null)
                .className(classLogbook.getClazz() != null ? classLogbook.getClazz().getName() : "N/A")
                .build();
    }
}
