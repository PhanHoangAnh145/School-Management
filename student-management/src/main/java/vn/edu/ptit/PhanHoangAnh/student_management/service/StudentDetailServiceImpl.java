package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentDetailRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentDetail;

import java.util.List;

@Service
public class StudentDetailServiceImpl implements StudentDetailService {

    private StudentDetailRepository studentDetailRepository;
    private StudentRepository studentRepository;

    @Autowired
    public StudentDetailServiceImpl(StudentDetailRepository studentDetailRepository,
                                    StudentRepository studentRepository) {
        this.studentDetailRepository = studentDetailRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDetail findStudentDetailById(int id) {
        return this.studentDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public List<StudentDetail> findAllStudentDetail() {
        return this.studentDetailRepository.findAll();
    }

    @Override
    @Transactional
    public StudentDetail saveStudentDetail(int studentId, StudentDetail studentDetail) {
        Student student = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException());
        student.setStudentDetail(studentDetail);
        studentDetail.setStudent(student);
        return this.studentDetailRepository.save(studentDetail);
    }

    @Override
    @Transactional
    public StudentDetail updateStudentDetailById(int id, StudentDetail studentDetail) {
        StudentDetail studentDetailDb = this.studentDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        studentDetailDb.setAddress(studentDetail.getAddress());
        studentDetailDb.setFullName(studentDetail.getFullName());
        studentDetailDb.setHobby(studentDetail.getHobby());
        studentDetailDb.setAvatar(studentDetail.getAvatar());

        return this.studentDetailRepository.save(studentDetailDb);
    }

    @Override
    @Transactional
    public void deleteStudentDetailById(int id) {
        StudentDetail studentDetail = this.studentDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.studentDetailRepository.delete(studentDetail);
    }
}