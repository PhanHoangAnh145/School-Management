package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dto.SchoolResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.School;

import java.util.List;

public interface SchoolService {
    public SchoolResponseDTO findSchoolById(Long id);
    public List<SchoolResponseDTO> findAllSchool();
    public SchoolResponseDTO saveSchool(School school);
    public SchoolResponseDTO updateSchoolById(Long id, School school);
    public void deleleSchoolById(Long id);
}
