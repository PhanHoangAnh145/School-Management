package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Subject;
import vn.edu.ptit.PhanHoangAnh.student_management.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    private SubjectService subjectService;

    @Autowired
    public SubjectController (SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping()
    public List<Subject> findAllSubject() {
        return this.subjectService.findAllSubject();
    }

    @GetMapping("/{id}")
    public Subject findSubjectById(@PathVariable int id) {
        return this.subjectService.findSubjectById(id);
    }

    @PostMapping("/{TeacherId}")
    public Subject saveSubject(@PathVariable int id, @RequestBody Subject subject) {
        return this.subjectService.saveSubject(id, subject);
    }

    @PutMapping("/{id}")
    public Subject updateSubjectById(@PathVariable int id, @RequestBody Subject subject) {
        return this.subjectService.updateSubjectById(id, subject);
    }

    @DeleteMapping("/{id}")
    public void deleteSubjectById(@PathVariable int id) {
        this.subjectService.deleteSubjectById(id);
    }
}
