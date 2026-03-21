package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.Subject;

import java.util.List;

public interface SubjectService {
    public Subject findSubjectById(int id);
    public List<Subject> findAllSubject();
    public Subject saveSubject(int TeacherId, Subject subject);
    public Subject updateSubjectById(int id, Subject subject);
    public void deleteSubjectById(int id);
}
