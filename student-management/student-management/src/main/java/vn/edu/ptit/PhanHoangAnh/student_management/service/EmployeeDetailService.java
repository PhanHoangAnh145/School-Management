package vn.edu.ptit.PhanHoangAnh.student_management.service;

import vn.edu.ptit.PhanHoangAnh.student_management.entity.EmployeeDetail;
import java.util.List;

public interface EmployeeDetailService {
    EmployeeDetail findEmployeeDetailById(int id);
    List<EmployeeDetail> findAllEmployeeDetail();
    EmployeeDetail saveEmployeeDetail(int employeeId, EmployeeDetail employeeDetail);
    EmployeeDetail updateEmployeeDetailById(int id, EmployeeDetail employeeDetail);
    void deleteEmployeeDetailById(int id);
}