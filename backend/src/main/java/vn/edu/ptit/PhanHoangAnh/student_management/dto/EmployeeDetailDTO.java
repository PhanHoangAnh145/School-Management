package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDetailDTO {
    private Long id;
    private String name;
    private String role;
    private String dayOfBirth;
    private String address;
    private String phoneNumber;
}
