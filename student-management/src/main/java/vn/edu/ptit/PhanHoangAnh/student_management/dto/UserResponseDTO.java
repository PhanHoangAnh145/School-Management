package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

import java.sql.Blob;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;

    private String username;

    private boolean enabled;

    private String email;

    private String firstname;

    private String lastname;

    private Blob avatar;

}
