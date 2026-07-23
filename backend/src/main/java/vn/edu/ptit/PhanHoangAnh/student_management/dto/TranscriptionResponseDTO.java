package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TranscriptionResponseDTO {
    private Long id;
    private int year;
    private String rate;
    private double mark;
    private Long studentId;
    private String studentName;
}
