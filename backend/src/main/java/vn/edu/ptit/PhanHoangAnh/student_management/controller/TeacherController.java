package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.TeacherDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Teacher;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
import vn.edu.ptit.PhanHoangAnh.student_management.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")

public class TeacherController {
    private TeacherService teacherService;

    @Autowired
    public TeacherController (TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherDTO>> findTeacherById(@PathVariable Long id) {
        TeacherDTO teacher = this.teacherService.findTeacherById(id);
        return ApiResponse.success(teacher);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<TeacherDTO>>> findAllTeacher() {
        List<TeacherDTO> teacherList = this.teacherService.findAllTeacher();
        return ApiResponse.success(teacherList);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherDTO>> saveTeacher(@PathVariable Long id, @RequestBody Teacher teacher) {
        TeacherDTO teacherSave = this.teacherService.saveTeacher(id, teacher);
        return ApiResponse.created(teacherSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherDTO>> updateTeacherById(@PathVariable Long id, @RequestBody  Teacher teacher) {
        TeacherDTO teacherUpdate = this.teacherService.updateTeacherById(id, teacher);
        return ApiResponse.success(teacherUpdate);
    }

    @PutMapping("/{id}/class")
    public ResponseEntity<ApiResponse<TeacherDTO>> updateTeacherByIdWithClazz(@PathVariable Long id, @RequestBody  List<Long> classIdList) {
        TeacherDTO teacherUpdate = this.teacherService.updateTeacherByIdWithClazz(id, classIdList);
        return ApiResponse.success(teacherUpdate);
    }

    @PutMapping("/{id}/subject")
    public ResponseEntity<ApiResponse<TeacherDTO>> updateTeacherByIdWithSubject(@PathVariable Long id, @RequestBody  List<Long> subjectIdList) {
        TeacherDTO teacherUpdate = this.teacherService.updateTeacherByIdWithSubject(id, subjectIdList);
        return ApiResponse.success(teacherUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTeacherById(@PathVariable Long id) {
        this.teacherService.deleteTeacherById(id);
        return ApiResponse.success("delete success...");
    }
}
