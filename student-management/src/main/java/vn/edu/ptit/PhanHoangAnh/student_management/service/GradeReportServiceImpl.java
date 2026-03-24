package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.GradeReportRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.TranscriptionRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.GradeReport;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Transcription;
import java.util.List;

@Service
public class GradeReportServiceImpl implements GradeReportService {

    private GradeReportRepository gradeReportRepository;
    private TranscriptionRepository transcriptionRepository;

    @Autowired
    public GradeReportServiceImpl(GradeReportRepository gradeReportRepository,
                                  TranscriptionRepository transcriptionRepository) {
        this.gradeReportRepository = gradeReportRepository;
        this.transcriptionRepository = transcriptionRepository;
    }

    @Override
    public GradeReport findGradeReportById(int id) {
        return this.gradeReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public List<GradeReport> findAllGradeReport() {
        return this.gradeReportRepository.findAll();
    }

    @Override
    @Transactional
    public GradeReport saveGradeReport(int transcriptionId, GradeReport gradeReport) {
        Transcription transcription = this.transcriptionRepository.findById(transcriptionId)
                .orElseThrow(() -> new RuntimeException());
        transcription.addGradeReport(gradeReport);
        return this.gradeReportRepository.save(gradeReport);
    }

    @Override
    @Transactional
    public GradeReport updateGradeReportById(int id, GradeReport gradeReport) {
        GradeReport gradeReportDb = this.gradeReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        gradeReportDb.setName(gradeReport.getName());
        gradeReportDb.setTenMark(gradeReport.getTenMark());
        return this.gradeReportRepository.save(gradeReportDb);
    }

    @Override
    @Transactional
    public void deleteGradeReportById(int id) {
        GradeReport gradeReport = this.gradeReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.gradeReportRepository.delete(gradeReport);
    }
}