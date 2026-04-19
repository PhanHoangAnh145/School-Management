package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.EmployeeDetailRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.EmployeeRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.EmployeeDetailDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Employee;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.EmployeeDetail;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.EmployeeMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeDetailServiceImpl implements EmployeeDetailService {

    private EmployeeDetailRepository employeeDetailRepository;
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;
    @Autowired
    public EmployeeDetailServiceImpl(EmployeeDetailRepository employeeDetailRepository,
                                     EmployeeRepository employeeRepository,EmployeeMapper employeeMapper) {
        this.employeeDetailRepository = employeeDetailRepository;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDetailDTO findEmployeeDetailById(Long id) {
        return employeeMapper.toDetailDTO(this.employeeDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException()));
    }

    @Override
    public EmployeeDetailDTO findByEmployeeId(Long employeeId) {
        return this.employeeDetailRepository.findByEmployeeId(employeeId)
                .map(employeeMapper::toDetailDTO)
                .orElse(null);
    }

    @Override
    public List<EmployeeDetailDTO> findAllEmployeeDetail() {
        return this.employeeDetailRepository.findAll()
                .stream()
                .map(employeeDetail -> employeeMapper.toDetailDTO(employeeDetail))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeDetailDTO saveEmployeeDetail(Long employeeId, EmployeeDetail employeeDetail) {
        Employee employee = this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException());
        employee.setEmployeeDetail(employeeDetail);
        employeeDetail.setEmployee(employee);
        return employeeMapper.toDetailDTO(this.employeeDetailRepository.save(employeeDetail));
    }

    @Override
    @Transactional
    public EmployeeDetailDTO updateEmployeeDetailById(Long id, EmployeeDetail employeeDetail) {
        EmployeeDetail employeeDetailDb = this.employeeDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        employeeDetailDb.setAddress(employeeDetail.getAddress());
        employeeDetailDb.setPhoneNumber(employeeDetail.getPhoneNumber());
        employeeDetailDb.setDayOfBirth(employeeDetail.getDayOfBirth());
        return employeeMapper.toDetailDTO(this.employeeDetailRepository.save(employeeDetailDb));
    }

    @Override
    @Transactional
    public void deleteEmployeeDetailById(Long id) {
        EmployeeDetail employeeDetail = this.employeeDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        this.employeeDetailRepository.delete(employeeDetail);
    }
}