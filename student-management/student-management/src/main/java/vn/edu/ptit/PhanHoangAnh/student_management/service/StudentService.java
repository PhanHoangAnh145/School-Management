package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.stereotype.Service;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;

import java.util.List;


public interface StudentService {
    public List<Student> findAllStudent();
    public Student findStudentById(int id);
    public Student saveStudent(int classId, Student student);
    public Student updateStudentById(int id, Student studentRq);
    public void deleteStudentById(int id);
}
