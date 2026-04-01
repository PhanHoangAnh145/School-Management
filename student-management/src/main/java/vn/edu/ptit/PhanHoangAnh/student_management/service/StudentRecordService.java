package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentRecord;
import java.util.List;

public interface StudentRecordService {
    StudentRecord findStudentRecordById(Long id);
    List<StudentRecord> findAllStudentRecord();
    StudentRecord saveStudentRecord(Long studentId, StudentRecord studentRecord);
    StudentRecord updateStudentRecordById(Long id, StudentRecord studentRecord);
    void deleteStudentRecordById(Long id);
}