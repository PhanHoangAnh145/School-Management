package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.SchoolRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.School;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService{
    private SchoolRepository schoolRepository;

    @Autowired
    public SchoolServiceImpl (SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public School findSchoolById(int id) {
        return  this.schoolRepository.findById(id).orElseThrow(() -> new RuntimeException("The school with id:" + id + " isn't existing"));
    }

    @Override
    public List<School> findAllSchool() {
        return  this.schoolRepository.findAll();
    }

    @Transactional
    @Override
    public School saveSchool(School school) {
        return  this.schoolRepository.save(school);
    }

    @Transactional
    @Override
    public School updateSchoolById(int id, School school) {
        School updateSchool =  this.schoolRepository.findById(id).orElseThrow(() -> new RuntimeException("School with id:" + id + "isn't existing"));
        updateSchool.setName(school.getName());
        updateSchool.setPhoneNumber(school.getPhoneNumber());
        updateSchool.setAddress(school.getAddress());
        updateSchool.setGrade(school.getGrade());

        return this.schoolRepository.saveAndFlush(updateSchool);
    }

    @Transactional
    @Override
    public void deleleSchoolById(int id) {
        School updateSchool =  this.schoolRepository.findById(id).orElseThrow(() -> new RuntimeException("School with id:" + id + "isn't existing"));
        this.schoolRepository.delete(updateSchool);
    }
}
