package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassResponseDTO implements Serializable {

    private Long id;

    private String name;

    private int grade;

    private int year;

    private String schoolName;
}
