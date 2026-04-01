package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentDetail;

import java.util.List;

public interface StudentDetailService {

    StudentDetail findStudentDetailById(Long id);

    List<StudentDetail> findAllStudentDetail();

    StudentDetail saveStudentDetail(Long studentId, StudentDetail studentDetail);

    StudentDetail updateStudentDetailById(Long id, StudentDetail studentDetail);

    void deleteStudentDetailById(Long id);
}