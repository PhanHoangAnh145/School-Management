package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.ParentRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.ParentResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Parent;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.ParentMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParentServiceImpl implements ParentService {

    private ParentRepository parentRepository;
    private StudentRepository studentRepository;
    private ParentMapper parentMapper;
    @Autowired
    public ParentServiceImpl(ParentRepository parentRepository,
                             StudentRepository studentRepository, ParentMapper parentMapper) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
        this.parentMapper = parentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParentResponseDTO> findAllParent() {
        List<Parent> parents = this.parentRepository.findAllWithStudent();
        return parents.stream()
                .map(parent -> parentMapper.toDTO(parent))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ParentResponseDTO findParentById(Long id) {
        Parent parent = this.parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The parent with id:" + id + " isn't existing"));
        return parentMapper.toDTO(parent);
    }

    @Override
    @Transactional(readOnly = true)
    public ParentResponseDTO findByStudentId(Long studentId) {
        return this.parentRepository.findByStudentId(studentId)
                .map(parent -> parentMapper.toDTO(parent))
                .orElse(null);
    }

    @Override
    @Transactional
    public ParentResponseDTO saveParent(Long studentId, Parent parent) {
        Student student = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("The student with id:" + studentId + " isn't existing"));
        student.setParent(parent);
        parent.setStudent(student);
        return parentMapper.toDTO(this.parentRepository.save(parent));
    }

    @Override
    @Transactional
    public ParentResponseDTO updateParentById(Long id, Parent parent) {
        Parent parentDb = this.parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The parent with id:" + id + " isn't existing"));
        parentDb.setName(parent.getName());
        parentDb.setAddress(parent.getAddress());
        parentDb.setPhoneNumber(parent.getPhoneNumber());
        return parentMapper.toDTO(this.parentRepository.save(parentDb));
    }

    @Override
    @Transactional
    public void deleteParentById(Long id) {
        Parent parent = this.parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The parent with id:" + id + " isn't existing"));
        this.parentRepository.delete(parent);
    }
}