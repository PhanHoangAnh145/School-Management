package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.dto.EmployeeDetailDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.EmployeeDetail;
import java.util.List;

public interface EmployeeDetailService {
    public EmployeeDetailDTO findEmployeeDetailById(Long id);
    public EmployeeDetailDTO findByEmployeeId(Long employeeId);
    public List<EmployeeDetailDTO> findAllEmployeeDetail();
    public EmployeeDetailDTO saveEmployeeDetail(Long employeeId, EmployeeDetail employeeDetail);
    public EmployeeDetailDTO updateEmployeeDetailById(Long id, EmployeeDetail employeeDetail);
    public void deleteEmployeeDetailById(Long id);
}
