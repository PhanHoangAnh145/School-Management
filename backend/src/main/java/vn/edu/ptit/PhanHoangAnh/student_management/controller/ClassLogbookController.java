package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.ClassLogbookResponseDTO;
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
    public ResponseEntity<ApiResponse<ClassLogbookResponseDTO>> findClassLogbookById(@PathVariable Long id) {
        ClassLogbookResponseDTO classLogbook = this.classLogbookService.findClassLogbookById(id);
        return ApiResponse.success(classLogbook);
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<ApiResponse<ClassLogbookResponseDTO>> findClassLogbookByClassId(@PathVariable Long classId) {
        ClassLogbookResponseDTO classLogbook = this.classLogbookService.findClassLogbookByClassId(classId);
        return ApiResponse.success(classLogbook);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ClassLogbookResponseDTO>>> findAllClassLogbook() {
        List<ClassLogbookResponseDTO> classLogbookList = this.classLogbookService.findAllClassLogbook();
        return ApiResponse.success(classLogbookList);
    }

    @PostMapping("/{classId}")
    public ResponseEntity<ApiResponse<ClassLogbookResponseDTO>> saveClassLogbook(@PathVariable Long classId, @RequestBody ClassLogbook classLogbook) {
        ClassLogbookResponseDTO classLogbookSave = this.classLogbookService.saveClassLogbook(classId, classLogbook);
        return ApiResponse.created(classLogbookSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassLogbookResponseDTO>> updateClassLogbookById(@PathVariable Long id, @RequestBody ClassLogbook classLogbook) {
        ClassLogbookResponseDTO classLogbookUpdate = this.classLogbookService.updateClassLogbookById(id, classLogbook);
        return ApiResponse.success(classLogbookUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteClassLogbookById(@PathVariable Long id) {
        this.classLogbookService.deleteClassLogbookById(id);
        return ApiResponse.success("delete success...");
    }
}