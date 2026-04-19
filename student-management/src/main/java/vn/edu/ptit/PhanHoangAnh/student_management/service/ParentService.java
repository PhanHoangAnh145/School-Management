package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dto.ParentResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Parent;
import java.util.List;

public interface ParentService {
    ParentResponseDTO findParentById(Long id);
    ParentResponseDTO findByStudentId(Long studentId);
    List<ParentResponseDTO> findAllParent();
    ParentResponseDTO saveParent(Long studentId, Parent parent);
    ParentResponseDTO updateParentById(Long id, Parent parent);
    void deleteParentById(Long id);
}
