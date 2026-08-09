package vn.edu.ptit.PhanHoangAnh.student_management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.CourseRegistration;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
    boolean existsByStudentIdAndCourseClassId(Long studentId, Long courseClassId);
}
