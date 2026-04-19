package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentDetailRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentDetailDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentDetail;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.StudentMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentDetailServiceImpl implements StudentDetailService {

    private StudentDetailRepository studentDetailRepository;
    private StudentRepository studentRepository;
    private StudentMapper studentMapper;

    @Autowired
    public StudentDetailServiceImpl(StudentDetailRepository studentDetailRepository,
                                    StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentDetailRepository = studentDetailRepository;
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentDetailDTO findStudentDetailById(Long id) {
        return studentMapper.toDetailDTO(this.studentDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException()));
    }

    @Override
    public StudentDetailDTO findByStudentId(Long studentId) {
        return this.studentDetailRepository.findByStudentId(studentId)
                .map(studentMapper::toDetailDTO)
                .orElse(null);
    }

    @Override
    public List<StudentDetailDTO> findAllStudentDetail() {
        return this.studentDetailRepository.findAll()
                .stream()
                .map(studentDetail -> studentMapper.toDetailDTO(studentDetail))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentDetailDTO saveStudentDetail(Long studentId, StudentDetail studentDetail) {
        Student student = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException());
        student.setStudentDetail(studentDetail);
        studentDetail.setStudent(student);
        return studentMapper.toDetailDTO(this.studentDetailRepository.save(studentDetail));
    }

    @Override
    @Transactional
    public StudentDetailDTO updateStudentDetailById(Long id, StudentDetail studentDetail) {
        StudentDetail studentDetailDb = this.studentDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        studentDetailDb.setAddress(studentDetail.getAddress());
        studentDetailDb.setFullName(studentDetail.getFullName());
        studentDetailDb.setHobby(studentDetail.getHobby());
        studentDetailDb.setAvatar(studentDetail.getAvatar());

        return studentMapper.toDetailDTO(this.studentDetailRepository.save(studentDetailDb));
    }

    @Override
    @Transactional
    public void deleteStudentDetailById(Long id) {
        StudentDetail studentDetail = this.studentDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.studentDetailRepository.delete(studentDetail);
    }
}