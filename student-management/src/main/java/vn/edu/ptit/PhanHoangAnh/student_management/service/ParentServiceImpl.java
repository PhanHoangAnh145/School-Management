package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.ParentRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Parent;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;
import java.util.List;

@Service
public class ParentServiceImpl implements ParentService {

    private ParentRepository parentRepository;
    private StudentRepository studentRepository;

    @Autowired
    public ParentServiceImpl(ParentRepository parentRepository,
                             StudentRepository studentRepository) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Parent findParentById(Long id) {
        return this.parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The parent with id:" + id + " isn't existing"));
    }

    @Override
    public List<Parent> findAllParent() {
        return this.parentRepository.findAll();
    }

    @Override
    @Transactional
    public Parent saveParent(Long studentId, Parent parent) {
        Student student = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("The student with id:" + studentId + " isn't existing"));
        student.setParent(parent);
        parent.setStudent(student);
        return this.parentRepository.save(parent);
    }

    @Override
    @Transactional
    public Parent updateParentById(Long id, Parent parent) {
        Parent parentDb = this.parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The parent with id:" + id + " isn't existing"));
        parentDb.setName(parent.getName());
        parentDb.setAddress(parent.getAddress());
        parentDb.setPhoneNumber(parent.getPhoneNumber());
        return this.parentRepository.save(parentDb);
    }

    @Override
    @Transactional
    public void deleteParentById(Long id) {
        Parent parent = this.parentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The parent with id:" + id + " isn't existing"));
        this.parentRepository.delete(parent);
    }
}