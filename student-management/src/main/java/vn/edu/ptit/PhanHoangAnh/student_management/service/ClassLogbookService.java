package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dto.ClassLogbookResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.ClassLogbook;
import java.util.List;

public interface ClassLogbookService {
    ClassLogbookResponseDTO findClassLogbookById(Long id);
    ClassLogbookResponseDTO findClassLogbookByClassId(Long classId);
    List<ClassLogbookResponseDTO> findAllClassLogbook();
    ClassLogbookResponseDTO saveClassLogbook(Long classId, ClassLogbook classLogbook);
    ClassLogbookResponseDTO updateClassLogbookById(Long id, ClassLogbook classLogbook);
    void deleteClassLogbookById(Long id);
}