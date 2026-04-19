package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.SchoolResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.School;

@Component
public class SchoolMapper {

    public SchoolResponseDTO toDTO(School school) {
        if (school == null) {
            return null;
        }

        return SchoolResponseDTO.builder()
                .id(school.getId())
                .name(school.getName())
                .phoneNumber(school.getPhoneNumber())
                .address(school.getAddress())
                .grade(school.getGrade())
                .build();
    }
}
