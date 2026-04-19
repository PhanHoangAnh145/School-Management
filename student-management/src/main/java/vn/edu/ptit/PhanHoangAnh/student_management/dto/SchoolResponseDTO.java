package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolResponseDTO {

    private Long id;

    private String name;

    private String phoneNumber;

    private String address;

    private int grade;
}
