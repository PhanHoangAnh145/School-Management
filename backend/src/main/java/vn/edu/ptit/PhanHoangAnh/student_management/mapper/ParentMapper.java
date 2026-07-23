package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.ParentResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Parent;

@Component
public class ParentMapper {

    public ParentResponseDTO toDTO(Parent parent) {
        if (parent == null) {
            return null;
        }

        ParentResponseDTO dto = new ParentResponseDTO();
        dto.setId(parent.getId());
        dto.setName(parent.getName());
        dto.setAddress(parent.getAddress());
        dto.setPhoneNumber(parent.getPhoneNumber());
        
        if (parent.getStudent() != null) {
            dto.setStudentId(parent.getStudent().getId());
            String name = parent.getStudent().getName();
            if (name != null && !name.trim().isEmpty()) {
                dto.setStudentName(name);
            } else {
                dto.setStudentName("Student ID: " + parent.getStudent().getId() + " (Name Empty)");
            }
        } else {
            dto.setStudentName("N/A (No Student Linked)");
        }
        
        return dto;
    }
}
