package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentDetailDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentDetail;

@Component
public class StudentMapper {

    public StudentResponseDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }

        return StudentResponseDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .dateOfBirth(student.getDateOfBirth())
                .className(student.getClazz() != null ? student.getClazz().getName() : "N/A")
                .schoolName(
                        (student.getClazz() != null && student.getClazz().getSchool() != null)
                                ? student.getClazz().getSchool().getName()
                                : "N/A"
                )
                .build();
    }

    public StudentDetailDTO toDetailDTO(StudentDetail studentDetail) {
        if (studentDetail == null)
            return null;

        Student student = studentDetail.getStudent();

        StudentDetailDTO dto = StudentDetailDTO.builder()
                .id(studentDetail.getId())
                .name(student.getName())
                .dateOfBirth(student.getDateOfBirth())
                .fullName(studentDetail.getFullName())
                .hobby(studentDetail.getHobby())
                .avatar(studentDetail.getAvatar())
                .address(studentDetail.getAddress())
                .build();

        return dto;
    }
}
