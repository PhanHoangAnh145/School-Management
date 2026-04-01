package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.Subject;

import java.util.List;

public interface SubjectService {
    public Subject findSubjectById(Long id);
    public List<Subject> findAllSubject();
    public Subject saveSubject(Long TeacherId, Subject subject);
    public Subject updateSubjectById(Long id, Subject subject);
    public void deleteSubjectById(Long id);
}
