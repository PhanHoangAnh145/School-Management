package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.EmployeeDetail;
import java.util.List;

public interface EmployeeDetailService {
    EmployeeDetail findEmployeeDetailById(Long id);
    List<EmployeeDetail> findAllEmployeeDetail();
    EmployeeDetail saveEmployeeDetail(Long employeeId, EmployeeDetail employeeDetail);
    EmployeeDetail updateEmployeeDetailById(Long id, EmployeeDetail employeeDetail);
    void deleteEmployeeDetailById(Long id);
}