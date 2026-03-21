package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.ClassLogbook;
import vn.edu.ptit.PhanHoangAnh.student_management.service.ClassLogbookService;
import java.util.List;

@RestController
@RequestMapping("/api/class-logbook")
public class ClassLogbookController {

    private ClassLogbookService classLogbookService;

    @Autowired
    public ClassLogbookController(ClassLogbookService classLogbookService) {
        this.classLogbookService = classLogbookService;
    }

    @GetMapping("/{id}")
    public ClassLogbook findClassLogbookById(@PathVariable int id) {
        return this.classLogbookService.findClassLogbookById(id);
    }

    @GetMapping()
    public List<ClassLogbook> findAllClassLogbook() {
        return this.classLogbookService.findAllClassLogbook();
    }

    @PostMapping("/{classId}")
    public ClassLogbook saveClassLogbook(@PathVariable int classId, @RequestBody ClassLogbook classLogbook) {
        return this.classLogbookService.saveClassLogbook(classId, classLogbook);
    }

    @PutMapping("/{id}")
    public ClassLogbook updateClassLogbookById(@PathVariable int id, @RequestBody ClassLogbook classLogbook) {
        return this.classLogbookService.updateClassLogbookById(id, classLogbook);
    }

    @DeleteMapping("/{id}")
    public void deleteClassLogbookById(@PathVariable int id) {
        this.classLogbookService.deleteClassLogbookById(id);
    }
}