package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.UserResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.User;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
import vn.edu.ptit.PhanHoangAnh.student_management.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> findUserById(@PathVariable Long id) {
        UserResponseDTO user = this.userService.findUserById(id);
        return ApiResponse.success(user);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> findAllUser() {
        List<UserResponseDTO> userList = this.userService.findAllUser();
        return ApiResponse.success(userList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUserById(Long id, User user) {
        UserResponseDTO userDb = this.userService.updateUserById(id, user);
        return ApiResponse.success(userDb);
    }
}
