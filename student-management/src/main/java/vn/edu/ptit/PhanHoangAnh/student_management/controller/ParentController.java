package vn.edu.ptit.PhanHoangAnh.student_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.ParentResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Parent;
import vn.edu.ptit.PhanHoangAnh.student_management.helper.ApiResponse;
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
    public ResponseEntity<ApiResponse<ParentResponseDTO>> findParentById(@PathVariable Long id) {
        ParentResponseDTO parent = this.parentService.findByStudentId(id);
        return ApiResponse.success(parent);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ParentResponseDTO>>> findAllParent() {
        List<ParentResponseDTO> parentList = this.parentService.findAllParent();
        return ApiResponse.success(parentList);
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<ApiResponse<ParentResponseDTO>> saveParent(@PathVariable Long studentId, @RequestBody Parent parent) {
        ParentResponseDTO parentSave = this.parentService.saveParent(studentId, parent);
        return ApiResponse.created(parentSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ParentResponseDTO>> updateParentById(@PathVariable Long id, @RequestBody Parent parent) {
        ParentResponseDTO parentUpdate = this.parentService.updateParentById(id, parent);
        return ApiResponse.success(parentUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteParentById(@PathVariable Long id) {
        this.parentService.deleteParentById(id);
        return ApiResponse.success("delete success...");
    }
}