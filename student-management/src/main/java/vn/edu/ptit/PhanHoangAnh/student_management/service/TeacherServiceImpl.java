package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.ClassRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.EmployeeRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.SubjectRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.TeacherRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Employee;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Subject;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Teacher;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepository teacherRepository;
    private EmployeeRepository employeeRepository;
    private ClassRepository classRepository;
    private SubjectRepository subjectRepository;

    @Autowired
    public TeacherServiceImpl (TeacherRepository teacherRepository, EmployeeRepository employeeRepository, ClassRepository classRepository, SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.employeeRepository = employeeRepository;
        this.classRepository = classRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Teacher findTeacherById(Long id) {
        return this.teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The teacher with id:" + id + " isn't existing"));
    }

    @Override
    public List<Teacher> findAllTeacher() {
        return this.teacherRepository.findAll();
    }

    @Override
    @Transactional
    public Teacher saveTeacher(Long EmployeeId, Teacher teacher) {
        Employee employee = this.employeeRepository.findById(EmployeeId).orElseThrow(() -> new EntityNotFoundException("The employee with id:" + EmployeeId + " isn't existing"));
        employee.addTeacher(teacher);
        return this.teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public Teacher updateTeacherById(Long id, Teacher teacher) {
        Teacher teacherDb = this.teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The teacher with id:" + id + " isn't existing"));
        // TODO: Implement update logic here
        return this.teacherRepository.save(teacherDb);
    }

    @Override
    @Transactional
    public Teacher updateTeacherByIdWithSubject(Long id, Teacher teacherRq) {
        Teacher teacherDb = this.teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The teacher with id:" + id + " isn't existing"));
        teacherDb.clearSubjectList();
        if (teacherRq.getSubjectList() != null) {
            for (Subject subject : teacherRq.getSubjectList()) {
                Subject subject1 = subjectRepository.findById(subject.getId()).orElseThrow(() -> new EntityNotFoundException("The subject with id:" + subject.getId() + " isn't existing"));
                teacherDb.addSubject(subject1);
            }
        }
        return this.teacherRepository.save(teacherDb);
    }

    @Override
    @Transactional
    public Teacher updateTeacherByIdWithClazz(Long id, Teacher teacherRq) {
        Teacher teacherDb = this.teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The teacher with id:" + id + " isn't existing"));
        teacherDb.clearClazzList();
        if (teacherRq.getClazzList() != null) {
            for (Clazz clazz : teacherRq.getClazzList()) {
                Clazz clazz1 = classRepository.findById(clazz.getId()).orElseThrow(() -> new EntityNotFoundException("The class with id:" + clazz.getId() + " isn't existing"));
                teacherDb.addClazz(clazz1);
            }
        }
        return this.teacherRepository.save(teacherDb);
    }

    @Override
    @Transactional
    public void deleteTeacherById(Long id) {
        Teacher teacher = this.teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The teacher with id:" + id + " isn't existing"));
        this.teacherRepository.delete(teacher);
    }
}
