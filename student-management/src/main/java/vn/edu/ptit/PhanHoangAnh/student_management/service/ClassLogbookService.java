package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.ClassLogbook;
import java.util.List;

public interface ClassLogbookService {
    ClassLogbook findClassLogbookById(Long id);
    List<ClassLogbook> findAllClassLogbook();
    ClassLogbook saveClassLogbook(Long classId, ClassLogbook classLogbook);
    ClassLogbook updateClassLogbookById(Long id, ClassLogbook classLogbook);
    void deleteClassLogbookById(Long id);
}