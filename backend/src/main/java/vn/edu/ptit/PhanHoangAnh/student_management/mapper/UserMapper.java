package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.UserResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Role;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.User;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserResponseDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        Boolean en = user.getEnabled();
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .avatarBase64(blobToBase64(user.getAvatar()))
                .enabled(en != null && en)
                .role(mapRoles(user))
                .build();
    }

    private String mapRoles(User user) {
        if (user.getRoleCollection() == null || user.getRoleCollection().isEmpty()) {
            return null;
        }
        return user.getRoleCollection().stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));
    }

    private String blobToBase64(Blob blob) {
        if (blob == null) {
            return null;
        }
        try {
            long length = blob.length();
            if (length == 0) {
                return null;
            }
            byte[] bytes = blob.getBytes(1, (int) length);
            return Base64.getEncoder().encodeToString(bytes);
        } catch (SQLException e) {
            // Log error instead of silent failure
            System.err.println("Error converting blob to base64: " + e.getMessage());
            return null;
        }
    }
}
