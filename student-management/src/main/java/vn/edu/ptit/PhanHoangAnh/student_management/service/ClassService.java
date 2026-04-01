package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;

import java.util.List;

public interface ClassService {
    public Clazz findClassById(Long id);
    public List<Clazz> findAllClass();
    public Clazz saveClass(Long SchoolId, Clazz clazz);
    public Clazz updateClassById(Long id, Clazz clazz);
    public void deleteClassById(Long id);
}
