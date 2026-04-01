package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.ClassLogbookRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.ClassRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.ClassLogbook;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Clazz;
import java.util.List;

@Service
public class ClassLogbookServiceImpl implements ClassLogbookService {

    private ClassLogbookRepository classLogbookRepository;
    private ClassRepository classRepository;

    @Autowired
    public ClassLogbookServiceImpl(ClassLogbookRepository classLogbookRepository,
                                   ClassRepository classRepository) {
        this.classLogbookRepository = classLogbookRepository;
        this.classRepository = classRepository;
    }

    @Override
    public ClassLogbook findClassLogbookById(Long id) {
        return this.classLogbookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public List<ClassLogbook> findAllClassLogbook() {
        return this.classLogbookRepository.findAll();
    }

    @Override
    @Transactional
    public ClassLogbook saveClassLogbook(Long classId, ClassLogbook classLogbook) {
        Clazz clazz = this.classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException());
        clazz.setClassLogbook(classLogbook);
        classLogbook.setClazz(clazz);
        return this.classLogbookRepository.save(classLogbook);
    }

    @Override
    @Transactional
    public ClassLogbook updateClassLogbookById(Long id, ClassLogbook classLogbook) {
        ClassLogbook classLogbookDb = this.classLogbookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        classLogbookDb.setDescription(classLogbook.getDescription());
        return this.classLogbookRepository.save(classLogbookDb);
    }

    @Override
    @Transactional
    public void deleteClassLogbookById(Long id) {
        ClassLogbook classLogbook = this.classLogbookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.classLogbookRepository.delete(classLogbook);
    }
}