package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.School;
import vn.edu.ptit.PhanHoangAnh.student_management.service.SchoolService;

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
    public List<School> findAllSchool() {
        return this.schoolService.findAllSchool();
    }

    @GetMapping("/{id}")
    public School findSchoolById(@PathVariable int id) {
        return this.schoolService.findSchoolById(id);
    }

    @PostMapping()
    public School saveSchool(@RequestBody School school) {
        return this.schoolService.saveSchool(school);
    }

    @PutMapping("/{id}")
    public School updateSchoolById(@PathVariable int id, @RequestBody School school) {
        return this.schoolService.updateSchoolById(id, school);
    }

    @DeleteMapping("/{id}")
    public void deleleSchoolById(@PathVariable int id) {
        this.schoolService.deleleSchoolById(id);
    }

}
