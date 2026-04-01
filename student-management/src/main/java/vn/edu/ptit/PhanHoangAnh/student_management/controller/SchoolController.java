package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.School;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
import vn.edu.ptit.PhanHoangAnh.student_management.service.SchoolService;
import vn.edu.ptit.PhanHoangAnh.student_management.service.SchoolServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/school")
public class SchoolController {
    private SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<School>>> findAllSchool() {
        List<School> schoolList =  this.schoolService.findAllSchool();
        return ApiResponse.success(schoolList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<School>> findSchoolById(@PathVariable Long id) {
        School school = this.schoolService.findSchoolById(id);
        return ApiResponse.success(school);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<School>> saveSchool(@RequestBody School school) {
        School schoolSave = this.schoolService.saveSchool(school);
        return ApiResponse.created(schoolSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateSchoolById(@PathVariable Long id, @RequestBody School school) {
        School schoolUpdate = this.schoolService.updateSchoolById(id, school);
        return ApiResponse.success("update success...");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleleSchoolById(@PathVariable Long id) {
        this.schoolService.deleleSchoolById(id);

        return ApiResponse.success("delete success...");
    }

}
