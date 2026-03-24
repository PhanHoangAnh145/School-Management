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
    public Transcription findTranscriptionById(int id) {
        return this.transcriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public List<Transcription> findAllTranscription() {
        return this.transcriptionRepository.findAll();
    }

    @Override
    @Transactional
    public Transcription saveTranscription(int studentId, Transcription transcription) {
        Student student = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException());
        student.setTranscription(transcription);
        transcription.setStudent(student);
        return this.transcriptionRepository.save(transcription);
    }

    @Override
    @Transactional
    public Transcription updateTranscriptionById(int id, Transcription transcription) {
        Transcription transcriptionDb = this.transcriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        transcriptionDb.setYear(transcription.getYear());
        transcriptionDb.setRate(transcription.getRate());
        transcriptionDb.setMark(transcription.getMark());
        return this.transcriptionRepository.save(transcriptionDb);
    }

    @Override
    @Transactional
    public void deleteTranscriptionById(int id) {
        Transcription transcription = this.transcriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.transcriptionRepository.delete(transcription);
    }
}