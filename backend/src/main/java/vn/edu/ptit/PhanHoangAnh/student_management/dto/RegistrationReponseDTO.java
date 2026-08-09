package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationReponseDTO {
    private Long registrationId;
    private String studentName;
    private String courseName;
    private String classCode;
    private LocalDateTime registrationTime;
}
