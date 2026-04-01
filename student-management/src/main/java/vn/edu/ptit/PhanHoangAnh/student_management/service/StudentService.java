package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.stereotype.Service;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;

import java.util.List;


public interface StudentService {
    public Student findStudentById(Long id);
    public List<Student> findAllStudent();
    public Student saveStudent(Long classId, Student student);
    public Student updateStudentById(Long id, Student studentRq);
    public void deleteStudentById(Long id);
}
