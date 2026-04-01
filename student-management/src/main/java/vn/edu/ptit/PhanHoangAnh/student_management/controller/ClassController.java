package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ApiResponse<Clazz>> findClassById(@PathVariable Long id) {
        Clazz clazz = this.classService.findClassById(id);
        return ApiResponse.success(clazz);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Clazz>>> findAllClass() {
        List<Clazz> clazzList = this.classService.findAllClass();
        return ApiResponse.success(clazzList);
    }

    @PostMapping("/{schoolId}")
    public ResponseEntity<ApiResponse<Clazz>> saveClass(@PathVariable Long schoolId, @RequestBody Clazz clazz) {
        Clazz clazzSave = this.classService.saveClass(schoolId, clazz);
        return ApiResponse.created(clazzSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Clazz>> updateClassById(@PathVariable Long id, @RequestBody Clazz clazz) {
        Clazz clazzUpdate = this.classService.updateClassById(id, clazz);
        return ApiResponse.success(clazzUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteClassById(@PathVariable Long id) {
        this.classService.deleteClassById(id);
        return ApiResponse.success("delete success...");
    }
}
