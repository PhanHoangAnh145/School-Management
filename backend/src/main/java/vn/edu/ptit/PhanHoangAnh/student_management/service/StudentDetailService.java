package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentDetailDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentDetail;

import java.util.List;

public interface StudentDetailService {

    public StudentDetailDTO findStudentDetailById(Long id);

    public StudentDetailDTO findByStudentId(Long studentId);

    public List<StudentDetailDTO> findAllStudentDetail();

    public StudentDetailDTO saveStudentDetail(Long studentId, StudentDetail studentDetail);

    public StudentDetailDTO updateStudentDetailById(Long id, StudentDetail studentDetail);

    public void deleteStudentDetailById(Long id);
}