package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

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

    /** Space-separated role names, aligned with JWT scope */
    private String role;

    /** Base64-encoded avatar bytes for JSON (no data URL prefix) */
    private String avatarBase64;
}
