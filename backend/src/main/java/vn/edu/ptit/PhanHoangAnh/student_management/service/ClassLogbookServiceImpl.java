package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.ClassLogbookRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.ClassRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.ClassLogbookResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.ClassLogbook;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.ClassLogbookMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassLogbookServiceImpl implements ClassLogbookService {

    private ClassLogbookRepository classLogbookRepository;
    private ClassRepository classRepository;
    private ClassLogbookMapper classLogbookMapper;

    @Autowired
    public ClassLogbookServiceImpl(ClassLogbookRepository classLogbookRepository,
                                   ClassRepository classRepository,
                                   ClassLogbookMapper classLogbookMapper) {
        this.classLogbookRepository = classLogbookRepository;
        this.classRepository = classRepository;
        this.classLogbookMapper = classLogbookMapper;
    }

    @Override
    public ClassLogbookResponseDTO findClassLogbookById(Long id) {
        ClassLogbook classLogbook = this.classLogbookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        return this.classLogbookMapper.toDTO(classLogbook);
    }

    @Override
    public ClassLogbookResponseDTO findClassLogbookByClassId(Long classId) {
        ClassLogbook classLogbook = this.classLogbookRepository.findByClazzId(classId)
                .orElse(null);
        return classLogbook != null ? this.classLogbookMapper.toDTO(classLogbook) : null;
    }

    @Override
    public List<ClassLogbookResponseDTO> findAllClassLogbook() {
        List<ClassLogbook> classLogbooks = this.classLogbookRepository.findAll();
        return classLogbooks.stream()
                .map(this.classLogbookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClassLogbookResponseDTO saveClassLogbook(Long classId, ClassLogbook classLogbook) {
        Clazz clazz = this.classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException());
        clazz.setClassLogbook(classLogbook);
        classLogbook.setClazz(clazz);
        ClassLogbook savedClassLogbook = this.classLogbookRepository.save(classLogbook);
        return this.classLogbookMapper.toDTO(savedClassLogbook);
    }

    @Override
    @Transactional
    public ClassLogbookResponseDTO updateClassLogbookById(Long id, ClassLogbook classLogbook) {
        ClassLogbook classLogbookDb = this.classLogbookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        classLogbookDb.setDescription(classLogbook.getDescription());
        ClassLogbook updatedClassLogbook = this.classLogbookRepository.save(classLogbookDb);
        return this.classLogbookMapper.toDTO(updatedClassLogbook);
    }

    @Override
    @Transactional
    public void deleteClassLogbookById(Long id) {
        ClassLogbook classLogbook = this.classLogbookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.classLogbookRepository.delete(classLogbook);
    }
}