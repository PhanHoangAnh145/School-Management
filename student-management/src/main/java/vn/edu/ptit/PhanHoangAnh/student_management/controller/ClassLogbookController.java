package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.ClassLogbook;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
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
    public ResponseEntity<ApiResponse<ClassLogbook>> findClassLogbookById(@PathVariable int id) {
        ClassLogbook classLogbook = this.classLogbookService.findClassLogbookById(id);
        return ApiResponse.success(classLogbook);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ClassLogbook>>> findAllClassLogbook() {
        List<ClassLogbook> classLogbookList = this.classLogbookService.findAllClassLogbook();
        return ApiResponse.success(classLogbookList);
    }

    @PostMapping("/{classId}")
    public ResponseEntity<ApiResponse<ClassLogbook>> saveClassLogbook(@PathVariable int classId, @RequestBody ClassLogbook classLogbook) {
        ClassLogbook classLogbookSave = this.classLogbookService.saveClassLogbook(classId, classLogbook);
        return ApiResponse.created(classLogbookSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassLogbook>> updateClassLogbookById(@PathVariable int id, @RequestBody ClassLogbook classLogbook) {
        ClassLogbook classLogbookUpdate = this.classLogbookService.updateClassLogbookById(id, classLogbook);
        return ApiResponse.success(classLogbookUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteClassLogbookById(@PathVariable int id) {
        this.classLogbookService.deleteClassLogbookById(id);
        return ApiResponse.success("delete success...");
    }
}