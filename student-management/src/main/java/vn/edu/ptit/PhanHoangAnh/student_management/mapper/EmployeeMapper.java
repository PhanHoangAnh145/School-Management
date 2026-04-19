package vn.edu.ptit.PhanHoangAnh.student_management.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.EmployeeDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.EmployeeDetailDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.TeacherDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Employee;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.EmployeeDetail;

@Component
public class EmployeeMapper {

    @Autowired
    private TeacherMapper teacherMapper;

    public EmployeeDTO toDTO(Employee employee) {
        if (employee == null) return null;

        EmployeeDTO dto = EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .role(employee.getRole())
                .build();

        if (employee.getTeacher() != null) {
            dto.setTeacher(teacherMapper.toDTO(employee.getTeacher()));
        }

        return dto;
    }

    public EmployeeDetailDTO toDetailDTO(EmployeeDetail employeeDetail) {
        if (employeeDetail == null)
            return null;
        Employee employee = employeeDetail.getEmployee();
        EmployeeDetailDTO dto = EmployeeDetailDTO.builder()
                .id(employeeDetail.getId())
                .name(employee.getName())
                .role(employee.getRole())
                .address(employeeDetail.getAddress())
                .phoneNumber(employeeDetail.getPhoneNumber())
                .dayOfBirth(employeeDetail.getDayOfBirth())
                .build();

        return dto;
    }
}
