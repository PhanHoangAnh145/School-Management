package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dto.SubjectResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Subject;

import java.util.List;

public interface SubjectService {
    public SubjectResponseDTO findSubjectById(Long id);
    public List<SubjectResponseDTO> findAllSubject();
    public SubjectResponseDTO saveSubject(Long TeacherId, Subject subject);
    public SubjectResponseDTO updateSubjectById(Long id, Subject subject);
    public void deleteSubjectById(Long id);
}
