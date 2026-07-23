package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.SchoolRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.SchoolResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.School;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.SchoolMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolServiceImpl implements SchoolService{
    private SchoolRepository schoolRepository;
    private SchoolMapper schoolMapper;
    @Autowired
    public SchoolServiceImpl (SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    @Override
    public SchoolResponseDTO findSchoolById(Long id) {
        return schoolMapper.toDTO(this.schoolRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The school with id:" + id + " isn't existing")));
    }

    @Override
    public List<SchoolResponseDTO> findAllSchool() {
        return this.schoolRepository.findAll()
                .stream()
                .map(school -> schoolMapper.toDTO(school))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public SchoolResponseDTO saveSchool(School school) {
        return  schoolMapper.toDTO(this.schoolRepository.save(school));
    }

    @Transactional
    @Override
    public SchoolResponseDTO updateSchoolById(Long id, School school) {
        School updateSchool =  this.schoolRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("School with id:" + id + " isn't existing"));
        updateSchool.setName(school.getName());
        updateSchool.setPhoneNumber(school.getPhoneNumber());
        updateSchool.setAddress(school.getAddress());
        updateSchool.setGrade(school.getGrade());

        return schoolMapper.toDTO(this.schoolRepository.saveAndFlush(updateSchool));
    }

    @Transactional
    @Override
    public void deleleSchoolById(Long id) {
        School updateSchool =  this.schoolRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("School with id:" + id + " isn't existing"));
        this.schoolRepository.delete(updateSchool);
    }
}
