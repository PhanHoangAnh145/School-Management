package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.Parent;
import java.util.List;

public interface ParentService {
    Parent findParentById(int id);
    List<Parent> findAllParent();
    Parent saveParent(int studentId, Parent parent);
    Parent updateParentById(int id, Parent parent);
    void deleteParentById(int id);
}