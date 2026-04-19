package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.ClassRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.StudentMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{
    private StudentRepository studentRepository;
    private ClassRepository classRepository;
    private StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl (StudentRepository studentRepository, ClassRepository classRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public List<StudentResponseDTO> findAllStudent() {
        return this.studentRepository.findAll()
                .stream()
                .map(student -> studentMapper.toDTO(student))
                .collect(Collectors.toList());

    }

    @Override
    public StudentResponseDTO findStudentById(Long id) {
        return studentMapper.toDTO(this.studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The student with id:" + id + " isn't existing")));
    }

    @Transactional
    @Override
    public StudentResponseDTO saveStudent(Long classId, Student student) {
        Clazz clazz = this.classRepository.findById(classId).orElseThrow(() -> new EntityNotFoundException("The class with id:" + classId + " isn't existing"));
        clazz.addStudent(student);

        return studentMapper.toDTO(this.studentRepository.save(student));
    }

    @Transactional
    @Override
    public StudentResponseDTO updateStudentById(Long id, Student studentRq) {
        Student student = this.studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The student with id:" + id + " isn't existing"));
        student.setName(studentRq.getName());
        student.setDateOfBirth(studentRq.getDateOfBirth());

        return studentMapper.toDTO(this.studentRepository.saveAndFlush(student));
    }

    @Transactional
    @Override
    public void deleteStudentById(Long id) {
        Student student = this.studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The student with id:" + id + " isn't existing"));
        this.studentRepository.delete(student);
    }
}
