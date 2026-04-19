package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dao.TeacherRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.TeacherDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Teacher;

import java.util.List;

public interface TeacherService {
    public TeacherDTO findTeacherById(Long id);
    public List<TeacherDTO> findAllTeacher();
    public TeacherDTO saveTeacher(Long EmployeeId, Teacher teacher);
    public TeacherDTO updateTeacherById(Long id, Teacher teacher);
    public TeacherDTO updateTeacherByIdWithSubject(Long id, List<Long> subjectIdList);
    public TeacherDTO updateTeacherByIdWithClazz(Long id, List<Long> classIdList);
    public void deleteTeacherById(Long id);
}
