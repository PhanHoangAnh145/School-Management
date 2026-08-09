package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.RegistrationReponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.CourseRegistration;

@Component
public class RegistrationMapper {

    public RegistrationReponseDTO toDTO(CourseRegistration registration) {
        if (registration == null) {
            return null;
        }

        return RegistrationReponseDTO.builder()
                .registrationId(registration.getId())
                .studentName(registration.getStudent() != null && registration.getStudent().getStudentDetail() != null
                        ? registration.getStudent().getName()
                        : null)
                .courseName(registration.getCourseClass() != null && registration.getCourseClass() != null
                        ? registration.getCourseClass().getName()
                        : null)
                .classCode(registration.getCourseClass() != null
                        ? registration.getCourseClass().getClassCode()
                        : null)
                .registrationTime(registration.getRegistrationTime())
                .build();
    }
}
