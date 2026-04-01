package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.ClassRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.SchoolRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.School;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService{
    private ClassRepository classRepository;
    private SchoolRepository schoolRepository;

    @Autowired
    public ClassServiceImpl(ClassRepository classRepository, SchoolRepository schoolRepository) {
        this.classRepository = classRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public Clazz findClassById(Long id) {
        return this.classRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The class with id:" + id + " isn't existing"));
    }

    @Override
    public List<Clazz> findAllClass() {
        return this.classRepository.findAll();
    }

    @Transactional
    @Override
    public Clazz saveClass(Long SchoolId, Clazz clazz) {
        School school = this.schoolRepository.findById(SchoolId).orElseThrow(() -> new EntityNotFoundException("The school with id:" + SchoolId + " isn't existing"));
        school.addClass(clazz);

        return this.classRepository.save(clazz);
    }

    @Transactional
    @Override
    public Clazz updateClassById(Long id, Clazz clazz) {
        Clazz clazz1 = this.classRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The class with id:" + id + " isn't existing"));
        clazz1.setName(clazz.getName());
        clazz1.setGrade(clazz.getGrade());
        clazz1.setYear(clazz.getYear());

        return this.classRepository.saveAndFlush(clazz1);
    }

    @Transactional
    @Override
    public void deleteClassById(Long id) {
        Clazz clazz = this.classRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The class with id:" + id + " isn't existing"));
        this.classRepository.delete(clazz);
    }
}
