package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.RoleRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.UserRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Role;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.User;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
import vn.edu.ptit.PhanHoangAnh.student_management.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private UserService userService;

    @Autowired
    public RegisterController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody User user) {
        try {
            userService.register(user);
            return ApiResponse.created("User registered successfully");
        }
        catch (Exception e) {
            return ApiResponse.error(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR, "Error registering user: " + e.getMessage());
        }
    }

}