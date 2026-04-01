package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.TranscriptionRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Transcription;
import java.util.List;

@Service
public class TranscriptionServiceImpl implements TranscriptionService {

    private TranscriptionRepository transcriptionRepository;
    private StudentRepository studentRepository;

    @Autowired
    public TranscriptionServiceImpl(TranscriptionRepository transcriptionRepository,
                                    StudentRepository studentRepository) {
        this.transcriptionRepository = transcriptionRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Transcription findTranscriptionById(Long id) {
        return this.transcriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public List<Transcription> findAllTranscription() {
        return this.transcriptionRepository.findAll();
    }

    @Override
    @Transactional
    public Transcription saveTranscription(Long studentId, Transcription transcription) {
        Student student = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException());
        student.addTranscription(transcription);

        return this.transcriptionRepository.save(transcription);
    }

    @Override
    @Transactional
    public Transcription updateTranscriptionById(Long id, Transcription transcription) {
        Transcription transcriptionDb = this.transcriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        transcriptionDb.setYear(transcription.getYear());
        transcriptionDb.setRate(transcription.getRate());
        transcriptionDb.setMark(transcription.getMark());
        return this.transcriptionRepository.save(transcriptionDb);
    }

    @Override
    @Transactional
    public void deleteTranscriptionById(Long id) {
        Transcription transcription = this.transcriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.transcriptionRepository.delete(transcription);
    }
}