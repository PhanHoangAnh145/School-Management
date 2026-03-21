package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentRecordRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.StudentRecord;
import java.util.List;

@Service
public class StudentRecordServiceImpl implements StudentRecordService {

    private StudentRecordRepository studentRecordRepository;
    private StudentRepository studentRepository;

    @Autowired
    public StudentRecordServiceImpl(StudentRecordRepository studentRecordRepository,
                                    StudentRepository studentRepository) {
        this.studentRecordRepository = studentRecordRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentRecord findStudentRecordById(int id) {
        return this.studentRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public List<StudentRecord> findAllStudentRecord() {
        return this.studentRecordRepository.findAll();
    }

    @Override
    @Transactional
    public StudentRecord saveStudentRecord(int studentId, StudentRecord studentRecord) {
        Student student = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException());
        student.setStudentRecord(studentRecord);
        studentRecord.setStudent(student);
        return this.studentRecordRepository.save(studentRecord);
    }

    @Override
    @Transactional
    public StudentRecord updateStudentRecordById(int id, StudentRecord studentRecord) {
        StudentRecord studentRecordDb = this.studentRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        studentRecordDb.setDescription(studentRecord.getDescription());
        return this.studentRecordRepository.save(studentRecordDb);
    }

    @Override
    @Transactional
    public void deleteStudentRecordById(int id) {
        StudentRecord studentRecord = this.studentRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.studentRecordRepository.delete(studentRecord);
    }
}