package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentRecordRequestDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentRecordResponseDTO;
import java.util.List;

public interface StudentRecordService {
    StudentRecordResponseDTO findStudentRecordById(Long id);
    StudentRecordResponseDTO findByStudentId(Long studentId);
    List<StudentRecordResponseDTO> findAllStudentRecord();
    StudentRecordResponseDTO saveStudentRecord(Long studentId, StudentRecordRequestDTO studentRecordRequestDTO);
    StudentRecordResponseDTO updateStudentRecordById(Long id, StudentRecordRequestDTO studentRecordRequestDTO);
    void deleteStudentRecordById(Long id);
}