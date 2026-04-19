package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.ClassResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;

@Component
public class ClassMapper {

    public ClassResponseDTO toDTO(Clazz clazz) {
        if (clazz == null) {
            return null;
        }

        return ClassResponseDTO.builder()
                .id(clazz.getId())
                .name(clazz.getName())
                .grade(clazz.getGrade())
                .year(clazz.getYear())
                .schoolName(clazz.getSchool() != null ? clazz.getSchool().getName() : "N/A")
                .build();
    }
}
