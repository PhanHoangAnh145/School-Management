package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.GradeReportRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.TranscriptionRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.GradeReportResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.GradeReport;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Transcription;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.GradeReportMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeReportServiceImpl implements GradeReportService {

    private GradeReportRepository gradeReportRepository;
    private TranscriptionRepository transcriptionRepository;
    private GradeReportMapper gradeReportMapper;

    @Autowired
    public GradeReportServiceImpl(GradeReportRepository gradeReportRepository,
                                  TranscriptionRepository transcriptionRepository,
                                  GradeReportMapper gradeReportMapper) {
        this.gradeReportRepository = gradeReportRepository;
        this.transcriptionRepository = transcriptionRepository;
        this.gradeReportMapper = gradeReportMapper;
    }

    @Override
    public GradeReportResponseDTO findGradeReportById(Long id) {
        GradeReport gradeReport = this.gradeReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        return this.gradeReportMapper.toDTO(gradeReport);
    }

    @Override
    public List<GradeReportResponseDTO> findAllGradeReport() {
        List<GradeReport> gradeReports = this.gradeReportRepository.findAll();
        return gradeReports.stream()
                .map(this.gradeReportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GradeReportResponseDTO saveGradeReport(Long transcriptionId, GradeReport gradeReport) {
        Transcription transcription = this.transcriptionRepository.findById(transcriptionId)
                .orElseThrow(() -> new RuntimeException());
        transcription.addGradeReport(gradeReport);
        GradeReport savedGradeReport = this.gradeReportRepository.save(gradeReport);
        return this.gradeReportMapper.toDTO(savedGradeReport);
    }

    @Override
    @Transactional
    public GradeReportResponseDTO updateGradeReportById(Long id, GradeReport gradeReport) {
        GradeReport gradeReportDb = this.gradeReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        gradeReportDb.setName(gradeReport.getName());
        gradeReportDb.setTenMark(gradeReport.getTenMark());
        GradeReport updatedGradeReport = this.gradeReportRepository.save(gradeReportDb);
        return this.gradeReportMapper.toDTO(updatedGradeReport);
    }

    @Override
    @Transactional
    public void deleteGradeReportById(Long id) {
        GradeReport gradeReport = this.gradeReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.gradeReportRepository.delete(gradeReport);
    }
}