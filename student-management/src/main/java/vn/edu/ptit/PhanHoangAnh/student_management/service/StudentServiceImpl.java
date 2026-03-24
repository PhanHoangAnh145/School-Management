package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.ClassRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    public StudentRepository studentRepository;
    public ClassRepository classRepository;

    @Autowired
    public StudentServiceImpl (StudentRepository studentRepository, ClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    @Override
    public List<Student> findAllStudent() {
        return this.studentRepository.findAll();
    }

    @Override
    public Student findStudentById(int id) {
        return this.studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Error"));
    }

    @Transactional
    @Override
    public Student saveStudent(int classId, Student student) {
        Clazz clazz = this.classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Error"));
        clazz.addStudent(student);

        return this.studentRepository.save(student);
    }

    @Transactional
    @Override
    public Student updateStudentById(int id, Student studentRq) {
        Student student = this.studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Error"));
        student.setName(studentRq.getName());
        student.setDateOfBirth(studentRq.getDateOfBirth());

        return this.studentRepository.saveAndFlush(student);
    }

    @Transactional
    @Override
    public void deleteStudentById(int id) {
        Student student = this.studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Error"));
        this.studentRepository.delete(student);
    }
}
