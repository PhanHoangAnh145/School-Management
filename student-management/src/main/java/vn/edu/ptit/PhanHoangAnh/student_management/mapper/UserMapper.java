package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.UserResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.User;

@Component
public class UserMapper {
    public UserResponseDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .enabled(true)
                .build();
    }
}
