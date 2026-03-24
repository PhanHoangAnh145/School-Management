package vn.edu.ptit.PhanHoangAnh.student_management.service;

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
    public Subject findSubjectById(int id) {
        return this.subjectRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    @Override
    public List<Subject> findAllSubject() {
        return this.subjectRepository.findAll();
    }

    @Override
    @Transactional
    public Subject saveSubject(int TeacherId, Subject subject) {
        Teacher teacher = this.teacherRepository.findById(TeacherId).orElseThrow(()->new RuntimeException());
        teacher.addSubject(subject);

        return this.subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public Subject updateSubjectById(int id, Subject subjectRq) {
        Subject subjectDb = this.subjectRepository.findById(id).orElseThrow(()-> new RuntimeException());
        subjectDb.setName(subjectRq.getName());

        return this.subjectRepository.saveAndFlush(subjectDb);
    }

    @Override
    @Transactional
    public void deleteSubjectById(int id) {
        Subject subject = this.subjectRepository.findById(id).orElseThrow(() -> new RuntimeException());
        this.subjectRepository.delete(subject);
    }
}
