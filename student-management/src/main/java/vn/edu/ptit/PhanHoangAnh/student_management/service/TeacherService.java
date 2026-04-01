package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dao.TeacherRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Teacher;

import java.util.List;

public interface TeacherService {
    public Teacher findTeacherById(Long id);
    public List<Teacher> findAllTeacher();
    public Teacher saveTeacher(Long EmployeeId, Teacher teacher);
    public Teacher updateTeacherById(Long id, Teacher teacher);
    public Teacher updateTeacherByIdWithSubject(Long id, Teacher teacher);
    public Teacher updateTeacherByIdWithClazz(Long id, Teacher teacher);
    public void deleteTeacherById(Long id);
}
