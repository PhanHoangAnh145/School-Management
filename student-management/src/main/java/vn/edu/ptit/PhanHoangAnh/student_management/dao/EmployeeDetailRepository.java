package vn.edu.ptit.PhanHoangAnh.student_management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.EmployeeDetail;

import java.util.Optional;

@Repository
public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail, Long> {
    Optional<EmployeeDetail> findByEmployeeId(Long employeeId);
}