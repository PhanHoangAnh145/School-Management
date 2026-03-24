package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.ClassLogbook;
import java.util.List;

public interface ClassLogbookService {
    ClassLogbook findClassLogbookById(int id);
    List<ClassLogbook> findAllClassLogbook();
    ClassLogbook saveClassLogbook(int classId, ClassLogbook classLogbook);
    ClassLogbook updateClassLogbookById(int id, ClassLogbook classLogbook);
    void deleteClassLogbookById(int id);
}