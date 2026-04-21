package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String avatarBase64;
}
