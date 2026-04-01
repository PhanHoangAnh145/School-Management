package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.Transcription;
import java.util.List;

public interface TranscriptionService {
    Transcription findTranscriptionById(Long id);
    List<Transcription> findAllTranscription();
    Transcription saveTranscription(Long studentId, Transcription transcription);
    Transcription updateTranscriptionById(Long id, Transcription transcription);
    void deleteTranscriptionById(Long id);
}