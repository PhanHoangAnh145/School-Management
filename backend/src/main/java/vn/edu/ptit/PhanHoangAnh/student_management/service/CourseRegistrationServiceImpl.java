package vn.edu.ptit.PhanHoangAnh.student_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.CourseClassRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.CourseRegistrationRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.RegistrationReponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.CourseClass;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.CourseRegistration;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.RegistrationMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CourseRegistrationServiceImpl implements CourseRegistrationService {
    private final CourseClassRepository courseClassRepository;
    private final CourseRegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;
    private final RegistrationMapper registrationMapper;

    @Override
    public RegistrationReponseDTO registerCourse(Long studentId, Long courseClassId) {
        CourseClass courseClass = this.courseClassRepository.findByIdForUpdate(courseClassId).orElseThrow(() -> new RuntimeException("khong thay lop tin chi nay"));

        Student student = this.studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("khong thay lop tin chi nay"));

        if (courseClass.getCurrentStudents() >= courseClass.getMaxStudents()) {
            throw new RuntimeException("Lớp học phần đã đầy!");
        }

        if (registrationRepository.existsByStudentIdAndCourseClassId(studentId, courseClassId)) {
            throw new RuntimeException("Bạn đã đăng ký lớp học phần này rồi!");
        }

        courseClass.setCurrentStudents(courseClass.getCurrentStudents() + 1);
        this.courseClassRepository.save(courseClass);

        CourseRegistration registration = new CourseRegistration();
        registration.setStudent(student);
        registration.setCourseClass(courseClass);
        registration.setRegistrationTime(LocalDateTime.now());

        return registrationMapper.toDTO(registration);
    }
}
