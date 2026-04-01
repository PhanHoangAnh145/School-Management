package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
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
    public Student findStudentById(Long id) {
        return this.studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The student with id:" + id + " isn't existing"));
    }

    @Transactional
    @Override
    public Student saveStudent(Long classId, Student student) {
        Clazz clazz = this.classRepository.findById(classId).orElseThrow(() -> new EntityNotFoundException("The class with id:" + classId + " isn't existing"));
        clazz.addStudent(student);

        return this.studentRepository.save(student);
    }

    @Transactional
    @Override
    public Student updateStudentById(Long id, Student studentRq) {
        Student student = this.studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The student with id:" + id + " isn't existing"));
        student.setName(studentRq.getName());
        student.setDateOfBirth(studentRq.getDateOfBirth());

        return this.studentRepository.saveAndFlush(student);
    }

    @Transactional
    @Override
    public void deleteStudentById(Long id) {
        Student student = this.studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The student with id:" + id + " isn't existing"));
        this.studentRepository.delete(student);
    }
}
