package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDTO implements Serializable {
    private Long id;
    private String name;
    private String dateOfBirth;
    private String className;
    private String schoolName;
}
