package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.EmployeeDetailRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.EmployeeRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Employee;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.EmployeeDetail;
import java.util.List;

@Service
public class EmployeeDetailServiceImpl implements EmployeeDetailService {

    private EmployeeDetailRepository employeeDetailRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeDetailServiceImpl(EmployeeDetailRepository employeeDetailRepository,
                                     EmployeeRepository employeeRepository) {
        this.employeeDetailRepository = employeeDetailRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDetail findEmployeeDetailById(Long id) {
        return this.employeeDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public List<EmployeeDetail> findAllEmployeeDetail() {
        return this.employeeDetailRepository.findAll();
    }

    @Override
    @Transactional
    public EmployeeDetail saveEmployeeDetail(Long employeeId, EmployeeDetail employeeDetail) {
        Employee employee = this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException());
        employee.setEmployeeDetail(employeeDetail);
        employeeDetail.setEmployee(employee);
        return this.employeeDetailRepository.save(employeeDetail);
    }

    @Override
    @Transactional
    public EmployeeDetail updateEmployeeDetailById(Long id, EmployeeDetail employeeDetail) {
        EmployeeDetail employeeDetailDb = this.employeeDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        employeeDetailDb.setAddress(employeeDetail.getAddress());
        employeeDetailDb.setPhoneNumber(employeeDetail.getPhoneNumber());
        employeeDetailDb.setDayOfBirth(employeeDetail.getDayOfBirth());
        return this.employeeDetailRepository.save(employeeDetailDb);
    }

    @Override
    @Transactional
    public void deleteEmployeeDetailById(Long id) {
        EmployeeDetail employeeDetail = this.employeeDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.employeeDetailRepository.delete(employeeDetail);
    }
}