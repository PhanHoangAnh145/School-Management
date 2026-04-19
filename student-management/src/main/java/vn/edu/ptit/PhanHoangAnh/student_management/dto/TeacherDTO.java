package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDTO {
    private Long id;
    private List<SubjectResponseDTO> subjectList;
    private List<ClassResponseDTO> clazzList;
}
