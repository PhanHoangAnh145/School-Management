package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.EmployeeRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.SchoolRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Employee;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.EmployeeDetail;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.School;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private SchoolRepository schoolRepository;

    @Autowired
    public EmployeeServiceImpl (EmployeeRepository employeeRepository, SchoolRepository schoolRepository) {
        this.employeeRepository = employeeRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public Employee findEmployeeById(Long id) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The employee with id:" + id + " isn't existing"));

        return employee;
    }

    @Override
    public List<Employee> findAllEmployee() {
        return this.employeeRepository.findAll();
    }

    @Override
    @Transactional
    public Employee saveEmployee(Long SchoolId, Employee employee) {
        School school = this.schoolRepository.findById(SchoolId).orElseThrow(() -> new EntityNotFoundException("The school with id:" + SchoolId + " isn't existing"));

        school.addEmployee(employee);

        return this.employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Employee updateEmployeeById(Long id, Employee employeeRq) {
        Employee employeeDb = this.employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The employee with id:" + id + " isn't existing"));
        employeeDb.setName(employeeRq.getName());
        employeeDb.setRole(employeeRq.getRole());

//        if (employeeRq.getEmployeeDetail() != null) {
//            EmployeeDetail detailRq = employeeRq.getEmployeeDetail();
//            EmployeeDetail detailDb = employeeDb.getEmployeeDetail();
//
//            if (detailDb != null) {
//                detailDb.setDayOfBirth(detailRq.getDayOfBirth());
//                detailDb.setAddress(detailRq.getAddress());
//                detailDb.setPhoneNumber(detailRq.getPhoneNumber());
//            }
//            else {
//                employeeDb.setEmployeeDetail(detailRq);
//                detailRq.setEmployee(employeeDb);
//            }
//        }

        return this.employeeRepository.saveAndFlush(employeeDb);
    }

    @Override
    @Transactional
    public void deleteEmployeeById(Long id) {
        Employee employee1 = this.employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The employee with id:" + id + " isn't existing"));
        this.employeeRepository.delete(employee1);
    }
}
