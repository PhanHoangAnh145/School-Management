package vn.edu.ptit.PhanHoangAnh.student_management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentRecord;

import java.util.Optional;

@Repository
public interface StudentRecordRepository extends JpaRepository<StudentRecord, Long> {
    Optional<StudentRecord> findByStudentId(Long studentId);
}