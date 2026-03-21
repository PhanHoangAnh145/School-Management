package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Subject;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Teacher;
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
    public Teacher findTeacherById(@PathVariable int id) {
        return this.teacherService.findTeacherById(id);
    }

    @GetMapping()
    public List<Teacher> findAllTeacher() {
        return this.teacherService.findAllTeacher();
    }

    @PostMapping("/{id}")
    public Teacher saveTeacher(@PathVariable int id, @RequestBody Teacher teacher) {
        return this.teacherService.saveTeacher(id, teacher);
    }

    @PutMapping("/{id}")
    public Teacher updateTeacherById(@PathVariable int id, @RequestBody  Teacher teacher) {
        return null;
    }

    @PutMapping("/{id}/class")
    public Teacher updateTeacherByIdWithClazz(@PathVariable int id, @RequestBody Teacher teacher) {
        return this.teacherService.updateTeacherByIdWithClazz(id, teacher);
    }

    @PutMapping("/{id}/subject")
    public Teacher updateTeacherByIdWithSubject(@PathVariable int id, @RequestBody Teacher teacher) {
        return this.teacherService.updateTeacherByIdWithSubject(id, teacher);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacherById(@PathVariable int id) {
        this.teacherService.deleteTeacherById(id);
    }
}
