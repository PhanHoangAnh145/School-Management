package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.School;

import java.util.List;

public interface SchoolService {
    public School findSchoolById(int id);
    public List<School> findAllSchool();
    public School saveSchool(School school);
    public School updateSchoolById(int id, School school);
    public void deleleSchoolById(int id);
}
