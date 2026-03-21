package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;
import vn.edu.ptit.PhanHoangAnh.student_management.service.ClassService;

import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {
    private ClassService classService;

    @Autowired
    public ClassController (ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/{id}")
    public Clazz findClassById(@PathVariable int id) {
        return this.classService.findClassById(id);
    }

    @GetMapping()
    public List<Clazz> findAllClass() {
        return this.classService.findAllClass();
    }

    @PostMapping("/{schoolId}")
    public Clazz saveClass(@PathVariable int schoolId, @RequestBody Clazz clazz) {
        return this.classService.saveClass(schoolId, clazz);
    }

    @PutMapping("/{id}")
    public Clazz updateClassById(@PathVariable int id, @RequestBody Clazz clazz) {
        return this.classService.updateClassById(id, clazz);
    }

    @DeleteMapping("/{id}")
    public void deleteClassById(@PathVariable int id) {
        this.classService.deleteClassById(id);
    }
}
