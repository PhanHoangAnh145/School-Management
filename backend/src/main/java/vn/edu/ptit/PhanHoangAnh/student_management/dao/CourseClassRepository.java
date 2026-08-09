package vn.edu.ptit.PhanHoangAnh.student_management.dao;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.CourseClass;

import java.util.Optional;

@Repository
public interface CourseClassRepository extends JpaRepository<CourseClass, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CourseClass c WHERE c.id = :id")
    Optional<CourseClass> findByIdForUpdate(Long id);
}
