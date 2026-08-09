package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dto.RegistrationReponseDTO;

public interface CourseRegistrationService {
    RegistrationReponseDTO registerCourse(Long studentId, Long courseClassId);
}
