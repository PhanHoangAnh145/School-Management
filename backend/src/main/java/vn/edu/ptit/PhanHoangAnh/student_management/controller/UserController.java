package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.UserProfileUpdateDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.UserResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.User;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
import vn.edu.ptit.PhanHoangAnh.student_management.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private static Long userIdFromAuth(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("Missing authentication principal");
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Object raw = jwt.getClaim("id");
        if (raw == null) {
            throw new IllegalStateException("JWT missing id claim");
        }
        if (raw instanceof Number) {
            return ((Number) raw).longValue();
        }
        return Long.valueOf(raw.toString());
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getProfile(Authentication authentication) {
        Long id = userIdFromAuth(authentication);
        UserResponseDTO user = this.userService.findUserById(id);
        return ApiResponse.success(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateProfile(
            Authentication authentication,
            @RequestBody UserProfileUpdateDTO dto) {
        Long id = userIdFromAuth(authentication);
        UserResponseDTO updated = this.userService.updateMyProfile(id, dto);
        return ApiResponse.success(updated);
    }

    /** `{id:\\d+}` so paths like `/profile` are not captured as an id. */
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> findUserById(@PathVariable Long id) {
        UserResponseDTO user = this.userService.findUserById(id);
        return ApiResponse.success(user);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> findAllUser() {
        List<UserResponseDTO> userList = this.userService.findAllUser();
        return ApiResponse.success(userList);
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUserById(@PathVariable Long id, @RequestBody User user) {
        UserResponseDTO userDb = this.userService.updateUserById(id, user);
        return ApiResponse.success(userDb);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            Authentication authentication,
            @RequestBody(required = false) Map<String, String> request) {
        try {
            Long id = userIdFromAuth(authentication);
            String oldPassword = request == null ? null : request.get("oldPassword");
            String newPassword = request == null ? null : request.get("newPassword");

            this.userService.changePassword(id, oldPassword, newPassword);
            return ApiResponse.success("Doi mat khau thanh cong");
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi server: " + e.getMessage());
        }
    }
}
