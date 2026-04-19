package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.ClassResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
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
    public ResponseEntity<ApiResponse<ClassResponseDTO>> findClassById(@PathVariable Long id) {
        ClassResponseDTO clazz = this.classService.findClassById(id);
        return ApiResponse.success(clazz);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ClassResponseDTO>>> findAllClass() {
        List<ClassResponseDTO> clazzList = this.classService.findAllClass();
        return ApiResponse.success(clazzList);
    }

    @PostMapping("/{schoolId}")
    public ResponseEntity<ApiResponse<ClassResponseDTO>> saveClass(@PathVariable Long schoolId, @RequestBody Clazz clazz) {
        ClassResponseDTO clazzSave = this.classService.saveClass(schoolId, clazz);
        return ApiResponse.created(clazzSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassResponseDTO>> updateClassById(@PathVariable Long id, @RequestBody Clazz clazz) {
        ClassResponseDTO clazzUpdate = this.classService.updateClassById(id, clazz);
        return ApiResponse.success(clazzUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteClassById(@PathVariable Long id) {
        this.classService.deleteClassById(id);
        return ApiResponse.success("delete success...");
    }
}
