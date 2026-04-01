package vn.edu.ptit.PhanHoangAnh.student_management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.GradeReport;

@Repository
public interface GradeReportRepository extends JpaRepository<GradeReport, Long> {
}