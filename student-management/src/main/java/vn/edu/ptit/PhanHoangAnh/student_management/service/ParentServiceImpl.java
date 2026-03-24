package vn.edu.ptit.PhanHoangAnh.student_management.service;

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
    public Parent findParentById(int id) {
        return this.parentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public List<Parent> findAllParent() {
        return this.parentRepository.findAll();
    }

    @Override
    @Transactional
    public Parent saveParent(int studentId, Parent parent) {
        Student student = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException());
        student.setParent(parent);
        parent.setStudent(student);
        return this.parentRepository.save(parent);
    }

    @Override
    @Transactional
    public Parent updateParentById(int id, Parent parent) {
        Parent parentDb = this.parentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        parentDb.setName(parent.getName());
        parentDb.setAddress(parent.getAddress());
        parentDb.setPhoneNumber(parent.getPhoneNumber());
        return this.parentRepository.save(parentDb);
    }

    @Override
    @Transactional
    public void deleteParentById(int id) {
        Parent parent = this.parentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.parentRepository.delete(parent);
    }
}