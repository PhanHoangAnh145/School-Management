package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.ClassRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.EmployeeRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.SubjectRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.TeacherRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.TeacherDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Employee;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Subject;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Teacher;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.TeacherMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepository teacherRepository;
    private EmployeeRepository employeeRepository;
    private ClassRepository classRepository;
    private SubjectRepository subjectRepository;
    private TeacherMapper teacherMapper;

    @Autowired
    public TeacherServiceImpl (TeacherRepository teacherRepository, EmployeeRepository employeeRepository, ClassRepository classRepository, SubjectRepository subjectRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.employeeRepository = employeeRepository;
        this.classRepository = classRepository;
        this.subjectRepository = subjectRepository;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherDTO findTeacherById(Long id) {
        return teacherMapper.toDTO(this.teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The teacher with id:" + id + " isn't existing")));
    }

    @Override
    public List<TeacherDTO> findAllTeacher() {
        return this.teacherRepository.findAll()
                .stream()
                .map(teacher -> teacherMapper.toDTO(teacher))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TeacherDTO saveTeacher(Long EmployeeId, Teacher teacher) {
        Employee employee = this.employeeRepository.findById(EmployeeId).orElseThrow(() -> new EntityNotFoundException("The employee with id:" + EmployeeId + " isn't existing"));
        employee.addTeacher(teacher);
        return teacherMapper.toDTO(this.teacherRepository.save(teacher));
    }

    @Override
    @Transactional
    public TeacherDTO updateTeacherById(Long id, Teacher teacher) {
        Teacher teacherDb = this.teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The teacher with id:" + id + " isn't existing"));
        return teacherMapper.toDTO(this.teacherRepository.save(teacherDb));
    }

    @Override
    @Transactional
    public TeacherDTO updateTeacherByIdWithSubject(Long id, List<Long> subjectIfList) {
        Teacher teacherDb = this.teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The teacher with id:" + id + " isn't existing"));
        teacherDb.clearSubjectList();
        if (subjectIfList != null && !subjectIfList.isEmpty()) {
            for (Long subjectId : subjectIfList) {
                Subject subject1 = subjectRepository.findById(subjectId).orElseThrow(() -> new EntityNotFoundException("The subject with id:" + subjectId + " isn't existing"));
                teacherDb.addSubject(subject1);
            }
        }
        return teacherMapper.toDTO(this.teacherRepository.save(teacherDb));
    }

    @Override
    @Transactional
    public TeacherDTO updateTeacherByIdWithClazz(Long id, List<Long> classIdList) {
        Teacher teacherDb = this.teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The teacher with id:" + id + " isn't existing"));
        teacherDb.clearClazzList();
        if (classIdList != null && !classIdList.isEmpty()) {
            for (Long classId : classIdList) {
                Clazz clazz1 = classRepository.findById(classId).orElseThrow(() -> new EntityNotFoundException("The class with id:" + classId + " isn't existing"));
                // Validation: ensure class belongs to the same school as the teacher
                if (!clazz1.getSchool().getId().equals(teacherDb.getEmployee().getSchool().getId())) {
                    throw new IllegalArgumentException("Cannot assign a class from a different school to this teacher");
                }
                teacherDb.addClazz(clazz1);
            }
        }
        return teacherMapper.toDTO(this.teacherRepository.save(teacherDb));
    }

    @Override
    @Transactional
    public void deleteTeacherById(Long id) {
        Teacher teacher = this.teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The teacher with id:" + id + " isn't existing"));
        this.teacherRepository.delete(teacher);
    }
}
