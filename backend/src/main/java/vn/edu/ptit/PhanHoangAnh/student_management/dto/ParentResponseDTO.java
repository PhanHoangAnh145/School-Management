package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParentResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private Long studentId;
    private String studentName;
}
