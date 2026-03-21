package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Parent;
import vn.edu.ptit.PhanHoangAnh.student_management.service.ParentService;
import java.util.List;

@RestController
@RequestMapping("/api/parent")
public class ParentController {

    private ParentService parentService;

    @Autowired
    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("/{id}")
    public Parent findParentById(@PathVariable int id) {
        return this.parentService.findParentById(id);
    }

    @GetMapping()
    public List<Parent> findAllParent() {
        return this.parentService.findAllParent();
    }

    @PostMapping("/{studentId}")
    public Parent saveParent(@PathVariable int studentId, @RequestBody Parent parent) {
        return this.parentService.saveParent(studentId, parent);
    }

    @PutMapping("/{id}")
    public Parent updateParentById(@PathVariable int id, @RequestBody Parent parent) {
        return this.parentService.updateParentById(id, parent);
    }

    @DeleteMapping("/{id}")
    public void deleteParentById(@PathVariable int id) {
        this.parentService.deleteParentById(id);
    }
}