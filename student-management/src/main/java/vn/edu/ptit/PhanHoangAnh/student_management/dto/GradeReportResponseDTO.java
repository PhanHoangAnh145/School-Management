package vn.edu.ptit.PhanHoangAnh.student_management.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeReportResponseDTO {
    private Long id;
    private String name;
    private double tenMark;
    private Long transcriptionId;
    private Integer transcriptionYear;
}
