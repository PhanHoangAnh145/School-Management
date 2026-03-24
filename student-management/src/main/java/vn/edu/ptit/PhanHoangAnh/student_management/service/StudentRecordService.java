package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentRecord;
import java.util.List;

public interface StudentRecordService {
    StudentRecord findStudentRecordById(int id);
    List<StudentRecord> findAllStudentRecord();
    StudentRecord saveStudentRecord(int studentId, StudentRecord studentRecord);
    StudentRecord updateStudentRecordById(int id, StudentRecord studentRecord);
    void deleteStudentRecordById(int id);
}