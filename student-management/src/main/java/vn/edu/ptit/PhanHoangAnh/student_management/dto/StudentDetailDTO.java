package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

import java.sql.Blob;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StudentDetailDTO {

    private Long id;

    private String name;
    private String dateOfBirth;
    private String fullName;

    private Blob avatar;

    private String address;

    private String hobby;

}
