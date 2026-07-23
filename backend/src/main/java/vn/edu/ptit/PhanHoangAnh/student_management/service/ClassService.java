package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dto.ClassResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;

import java.util.List;

public interface ClassService {
    public ClassResponseDTO findClassById(Long id);
    public List<ClassResponseDTO> findAllClass();
    public ClassResponseDTO saveClass(Long SchoolId, Clazz clazz);
    public ClassResponseDTO updateClassById(Long id, Clazz clazz);
    public void deleteClassById(Long id);
}
