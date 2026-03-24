package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentDetail;

import java.util.List;

public interface StudentDetailService {

    StudentDetail findStudentDetailById(int id);

    List<StudentDetail> findAllStudentDetail();

    StudentDetail saveStudentDetail(int studentId, StudentDetail studentDetail);

    StudentDetail updateStudentDetailById(int id, StudentDetail studentDetail);

    void deleteStudentDetailById(int id);
}