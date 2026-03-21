package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.Transcription;
import java.util.List;

public interface TranscriptionService {
    Transcription findTranscriptionById(int id);
    List<Transcription> findAllTranscription();
    Transcription saveTranscription(int studentId, Transcription transcription);
    Transcription updateTranscriptionById(int id, Transcription transcription);
    void deleteTranscriptionById(int id);
}