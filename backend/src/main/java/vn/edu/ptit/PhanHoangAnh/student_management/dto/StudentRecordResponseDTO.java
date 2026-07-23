package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRecordResponseDTO {
    private Long id;
    private String description;
    private Long studentId;
    private String studentName;
}
