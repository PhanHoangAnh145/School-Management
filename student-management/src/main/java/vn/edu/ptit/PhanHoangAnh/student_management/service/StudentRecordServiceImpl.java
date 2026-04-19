package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentRecordRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentRecordRequestDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.StudentRecordResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentRecord;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.StudentRecordMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentRecordServiceImpl implements StudentRecordService {

    private StudentRecordRepository studentRecordRepository;
    private StudentRepository studentRepository;
    private StudentRecordMapper studentRecordMapper;

    @Autowired
    public StudentRecordServiceImpl(StudentRecordRepository studentRecordRepository,
                                    StudentRepository studentRepository,
                                    StudentRecordMapper studentRecordMapper) {
        this.studentRecordRepository = studentRecordRepository;
        this.studentRepository = studentRepository;
        this.studentRecordMapper = studentRecordMapper;
    }

    @Override
    public StudentRecordResponseDTO findStudentRecordById(Long id) {
        StudentRecord studentRecord = this.studentRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        return this.studentRecordMapper.toDTO(studentRecord);
    }

    @Override
    public StudentRecordResponseDTO findByStudentId(Long studentId) {
        return this.studentRecordRepository.findByStudentId(studentId)
                .map(studentRecordMapper::toDTO)
                .orElse(null);
    }

    @Override
    public List<StudentRecordResponseDTO> findAllStudentRecord() {
        List<StudentRecord> studentRecords = this.studentRecordRepository.findAll();
        return studentRecords.stream()
                .map(this.studentRecordMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentRecordResponseDTO saveStudentRecord(Long studentId, StudentRecordRequestDTO studentRecordRequestDTO) {
        Student student = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException());
        
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setDescription(studentRecordRequestDTO.getDescription());
        student.setStudentRecord(studentRecord);
        studentRecord.setStudent(student);
        
        StudentRecord savedStudentRecord = this.studentRecordRepository.save(studentRecord);
        return this.studentRecordMapper.toDTO(savedStudentRecord);
    }

    @Override
    @Transactional
    public StudentRecordResponseDTO updateStudentRecordById(Long id, StudentRecordRequestDTO studentRecordRequestDTO) {
        StudentRecord studentRecordDb = this.studentRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        studentRecordDb.setDescription(studentRecordRequestDTO.getDescription());
        StudentRecord updatedStudentRecord = this.studentRecordRepository.save(studentRecordDb);
        return this.studentRecordMapper.toDTO(updatedStudentRecord);
    }

    @Override
    @Transactional
    public void deleteStudentRecordById(Long id) {
        StudentRecord studentRecord = this.studentRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.studentRecordRepository.delete(studentRecord);
    }
}