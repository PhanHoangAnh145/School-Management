package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.TeacherDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Teacher;

import java.util.stream.Collectors;

@Component
public class TeacherMapper {

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private ClassMapper classMapper;

    // Từ Entity sang DTO
    public TeacherDTO toDTO(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        TeacherDTO dto = TeacherDTO.builder()
                .id(teacher.getId())
                .build();

        if (teacher.getSubjectList() != null) {
            dto.setSubjectList(teacher.getSubjectList().stream()
                    .map(subjectMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        if (teacher.getClazzList() != null) {
            dto.setClazzList(teacher.getClazzList().stream()
                    .map(classMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
