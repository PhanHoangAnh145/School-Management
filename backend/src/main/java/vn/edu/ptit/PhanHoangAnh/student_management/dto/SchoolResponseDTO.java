package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolResponseDTO implements Serializable {

    private Long id;

    private String name;

    private String phoneNumber;

    private String address;

    private int grade;
}
