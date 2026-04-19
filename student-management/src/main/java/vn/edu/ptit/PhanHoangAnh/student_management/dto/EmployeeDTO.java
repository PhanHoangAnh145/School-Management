package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    private String name;
    private String role;
    private TeacherDTO teacher;
}
