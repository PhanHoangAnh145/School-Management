package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.stereotype.Service;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;

import java.util.List;


public interface StudentService {
    public StudentResponseDTO findStudentById(Long id);
    public List<StudentResponseDTO> findAllStudent();
    public StudentResponseDTO saveStudent(Long classId, Student student);
    public StudentResponseDTO updateStudentById(Long id, Student studentRq);
    public void deleteStudentById(Long id);
}
