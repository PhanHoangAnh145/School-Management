package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;

import java.util.List;

public interface ClassService {
    public Clazz findClassById(int id);
    public List<Clazz> findAllClass();
    public Clazz saveClass(int SchoolId, Clazz clazz);
    public Clazz updateClassById(int id, Clazz clazz);
    public void deleteClassById(int id);
}
