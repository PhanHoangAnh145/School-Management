package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassResponseDTO {

    private Long id;

    private String name;

    private int grade;

    private int year;

    private String schoolName;
}
