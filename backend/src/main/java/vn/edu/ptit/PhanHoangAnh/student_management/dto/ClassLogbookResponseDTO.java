package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassLogbookResponseDTO {
    private Long id;
    private String description;
    private Long classId;
    private String className;
}
