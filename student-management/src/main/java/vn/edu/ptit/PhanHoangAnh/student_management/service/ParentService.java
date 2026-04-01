package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.Parent;
import java.util.List;

public interface ParentService {
    Parent findParentById(Long id);
    List<Parent> findAllParent();
    Parent saveParent(Long studentId, Parent parent);
    Parent updateParentById(Long id, Parent parent);
    void deleteParentById(Long id);
}