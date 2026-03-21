package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dao.TeacherRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Teacher;

import java.util.List;

public interface TeacherService {
    public Teacher findTeacherById(int id);
    public List<Teacher> findAllTeacher();
    public Teacher saveTeacher(int EmployeeId, Teacher teacher);
    public Teacher updateTeacherById(int id, Teacher teacher);
    public Teacher updateTeacherByIdWithSubject(int id, Teacher teacher);
    public Teacher updateTeacherByIdWithClazz(int id, Teacher teacher);
    public void deleteTeacherById(int id);
}
