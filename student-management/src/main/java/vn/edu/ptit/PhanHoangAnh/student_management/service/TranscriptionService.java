package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dto.TranscriptionResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Transcription;
import java.util.List;

public interface TranscriptionService {
    TranscriptionResponseDTO findTranscriptionById(Long id);
    TranscriptionResponseDTO findByStudentId(Long studentId);
    List<TranscriptionResponseDTO> findAllTranscription();
    TranscriptionResponseDTO saveTranscription(Long studentId, Transcription transcription);
    TranscriptionResponseDTO updateTranscriptionById(Long id, Transcription transcription);
    void deleteTranscriptionById(Long id);
}