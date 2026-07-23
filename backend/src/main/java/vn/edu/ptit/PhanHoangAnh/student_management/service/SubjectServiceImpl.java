package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.SubjectRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.TeacherRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.SubjectResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Subject;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Teacher;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.SubjectMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    private SubjectRepository subjectRepository;
    private TeacherRepository teacherRepository;
    private SubjectMapper subjectMapper;

    @Autowired
    public SubjectServiceImpl (SubjectRepository subjectRepository,  TeacherRepository teacherRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public SubjectResponseDTO findSubjectById(Long id) {
        return subjectMapper.toDTO(this.subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The subject with id:" + id + " isn't existing")));
    }

    @Override
    public List<SubjectResponseDTO> findAllSubject() {
        return this.subjectRepository.findAll()
                .stream()
                .map(subject -> subjectMapper.toDTO(subject))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubjectResponseDTO saveSubject(Long TeacherId, Subject subject) {
        Teacher teacher = this.teacherRepository.findById(TeacherId).orElseThrow(() -> new EntityNotFoundException("The teacher with id:" + TeacherId + " isn't existing"));
        teacher.addSubject(subject);

        return subjectMapper.toDTO(this.subjectRepository.save(subject));
    }

    @Override
    @Transactional
    public SubjectResponseDTO updateSubjectById(Long id, Subject subjectRq) {
        Subject subjectDb = this.subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The subject with id:" + id + " isn't existing"));
        subjectDb.setName(subjectRq.getName());

        return subjectMapper.toDTO(this.subjectRepository.saveAndFlush(subjectDb));
    }

    @Override
    @Transactional
    public void deleteSubjectById(Long id) {
        Subject subject = this.subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The subject with id:" + id + " isn't existing"));
        this.subjectRepository.delete(subject);
    }
}
