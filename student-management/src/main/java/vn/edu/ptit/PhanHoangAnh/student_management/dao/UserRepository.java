package vn.edu.ptit.PhanHoangAnh.student_management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String Username);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User u set u.password = :password where u.id = :id")
    int updatePasswordById(@Param("id") Long id, @Param("password") String password);
}
