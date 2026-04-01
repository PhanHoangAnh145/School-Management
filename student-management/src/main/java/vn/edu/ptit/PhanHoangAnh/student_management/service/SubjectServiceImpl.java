package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.SubjectRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.TeacherRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Subject;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Teacher;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    private SubjectRepository subjectRepository;
    private TeacherRepository teacherRepository;

    @Autowired
    public SubjectServiceImpl (SubjectRepository subjectRepository,  TeacherRepository teacherRepository) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Subject findSubjectById(Long id) {
        return this.subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The subject with id:" + id + " isn't existing"));
    }

    @Override
    public List<Subject> findAllSubject() {
        return this.subjectRepository.findAll();
    }

    @Override
    @Transactional
    public Subject saveSubject(Long TeacherId, Subject subject) {
        Teacher teacher = this.teacherRepository.findById(TeacherId).orElseThrow(() -> new EntityNotFoundException("The teacher with id:" + TeacherId + " isn't existing"));
        teacher.addSubject(subject);

        return this.subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public Subject updateSubjectById(Long id, Subject subjectRq) {
        Subject subjectDb = this.subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The subject with id:" + id + " isn't existing"));
        subjectDb.setName(subjectRq.getName());

        return this.subjectRepository.saveAndFlush(subjectDb);
    }

    @Override
    @Transactional
    public void deleteSubjectById(Long id) {
        Subject subject = this.subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The subject with id:" + id + " isn't existing"));
        this.subjectRepository.delete(subject);
    }
}
