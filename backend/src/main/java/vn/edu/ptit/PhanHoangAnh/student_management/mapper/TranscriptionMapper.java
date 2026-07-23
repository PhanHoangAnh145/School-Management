package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.TranscriptionResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Transcription;

@Component
public class TranscriptionMapper {

    public TranscriptionResponseDTO toDTO(Transcription transcription) {
        if (transcription == null) {
            return null;
        }

        return TranscriptionResponseDTO.builder()
                .id(transcription.getId())
                .year(transcription.getYear())
                .rate(transcription.getRate())
                .mark(transcription.getMark())
                .studentId(transcription.getStudent() != null ? transcription.getStudent().getId() : null)
                .studentName(transcription.getStudent() != null ? transcription.getStudent().getName() : "N/A")
                .build();
    }
}
