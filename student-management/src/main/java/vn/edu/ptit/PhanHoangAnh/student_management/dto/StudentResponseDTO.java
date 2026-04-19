package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDTO {
    private Long id;
    private String name;
    private String dateOfBirth;
    private String className;
    private String schoolName;
}
