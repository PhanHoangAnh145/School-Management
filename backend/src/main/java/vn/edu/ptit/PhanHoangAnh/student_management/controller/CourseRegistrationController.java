package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.RegistrationReponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.RegistrationRequestDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
import vn.edu.ptit.PhanHoangAnh.student_management.service.CourseRegistrationService;
import vn.edu.ptit.PhanHoangAnh.student_management.service.UserService;

@RestController
@RequestMapping("/api/registrations")
public class CourseRegistrationController {
    private final CourseRegistrationService courseRegistrationService;
    private final UserService userService;
    public CourseRegistrationController(CourseRegistrationService courseRegistrationService, UserService userService) {
        this.courseRegistrationService = courseRegistrationService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegistrationReponseDTO>> register(@AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails currentUser, @RequestBody RegistrationRequestDTO requestDTO) {
        // lay id nguoi dung
        String username = currentUser.getUsername();
        vn.edu.ptit.PhanHoangAnh.student_management.entity.User myUser = this.userService.findUserByUsername(username);
        Long studentId = myUser.getId();

        RegistrationReponseDTO reponseDTO = courseRegistrationService.registerCourse(studentId, requestDTO.getCourseClassId());

        return ApiResponse.success(reponseDTO);
    }
}
