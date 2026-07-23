package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.TranscriptionRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.StudentRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.TranscriptionResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Student;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Transcription;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.TranscriptionMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranscriptionServiceImpl implements TranscriptionService {

    private TranscriptionRepository transcriptionRepository;
    private StudentRepository studentRepository;
    private TranscriptionMapper transcriptionMapper;

    @Autowired
    public TranscriptionServiceImpl(TranscriptionRepository transcriptionRepository,
                                    StudentRepository studentRepository,
                                    TranscriptionMapper transcriptionMapper) {
        this.transcriptionRepository = transcriptionRepository;
        this.studentRepository = studentRepository;
        this.transcriptionMapper = transcriptionMapper;
    }

    @Override
    public TranscriptionResponseDTO findTranscriptionById(Long id) {
        Transcription transcription = this.transcriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        return this.transcriptionMapper.toDTO(transcription);
    }

    @Override
    public TranscriptionResponseDTO findByStudentId(Long studentId) {
        return this.transcriptionRepository.findByStudentId(studentId)
                .map(transcriptionMapper::toDTO)
                .orElse(null);
    }

    @Override
    public List<TranscriptionResponseDTO> findAllTranscription() {
        List<Transcription> transcriptions = this.transcriptionRepository.findAll();
        return transcriptions.stream()
                .map(this.transcriptionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TranscriptionResponseDTO saveTranscription(Long studentId, Transcription transcription) {
        Student student = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException());
        student.addTranscription(transcription);

        Transcription savedTranscription = this.transcriptionRepository.save(transcription);
        return this.transcriptionMapper.toDTO(savedTranscription);
    }

    @Override
    @Transactional
    public TranscriptionResponseDTO updateTranscriptionById(Long id, Transcription transcription) {
        Transcription transcriptionDb = this.transcriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        transcriptionDb.setYear(transcription.getYear());
        transcriptionDb.setRate(transcription.getRate());
        transcriptionDb.setMark(transcription.getMark());
        Transcription updatedTranscription = this.transcriptionRepository.save(transcriptionDb);
        return this.transcriptionMapper.toDTO(updatedTranscription);
    }

    @Override
    @Transactional
    public void deleteTranscriptionById(Long id) {
        Transcription transcription = this.transcriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.transcriptionRepository.delete(transcription);
    }
}