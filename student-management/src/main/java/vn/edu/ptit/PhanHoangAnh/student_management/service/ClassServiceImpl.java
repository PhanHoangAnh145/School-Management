package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.ClassRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.SchoolRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.ClassResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.School;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.ClassMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService{
    private ClassRepository classRepository;
    private SchoolRepository schoolRepository;
    private ClassMapper classMapper;

    @Autowired
    public ClassServiceImpl(ClassRepository classRepository, SchoolRepository schoolRepository, ClassMapper classMapper) {
        this.classRepository = classRepository;
        this.schoolRepository = schoolRepository;
        this.classMapper = classMapper;
    }

    @Override
    public ClassResponseDTO findClassById(Long id) {
        return classMapper.toDTO(this.classRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The class with id:" + id + " isn't existing")));
    }

    @Override
    public List<ClassResponseDTO> findAllClass() {
        return this.classRepository.findAll()
                .stream()
                .map(clazz -> classMapper.toDTO(clazz))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ClassResponseDTO saveClass(Long SchoolId, Clazz clazz) {
        School school = this.schoolRepository.findById(SchoolId).orElseThrow(() -> new EntityNotFoundException("The school with id:" + SchoolId + " isn't existing"));
        school.addClass(clazz);

        return classMapper.toDTO(this.classRepository.save(clazz));
    }

    @Transactional
    @Override
    public ClassResponseDTO updateClassById(Long id, Clazz clazz) {
        Clazz clazz1 = this.classRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The class with id:" + id + " isn't existing"));
        clazz1.setName(clazz.getName());
        clazz1.setGrade(clazz.getGrade());
        clazz1.setYear(clazz.getYear());

        return classMapper.toDTO(this.classRepository.saveAndFlush(clazz1));
    }

    @Transactional
    @Override
    public void deleteClassById(Long id) {
        Clazz clazz = this.classRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The class with id:" + id + " isn't existing"));
        this.classRepository.delete(clazz);
    }
}
