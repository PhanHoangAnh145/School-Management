package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.User;
import vn.edu.ptit.PhanHoangAnh.student_management.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register/user")
    public String register(@RequestBody User user, @RequestParam String role) {
        try{
            this.userService.createUserByAdmin(user, role);
            return "OK";
        }
        catch (Exception e) {
            return "Error...";
        }
    }
}
