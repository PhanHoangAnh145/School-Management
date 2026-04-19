package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "school")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "phone_number")
    private String phoneNumber;


    @Column(name = "address")
    private String address;


    @Column(name = "grade")
    private int grade;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "school-class")
    public List<Clazz> clazzList;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "school-employee")
    public List<Employee> employeeList;

    public School() {
    }

    public School(String name, String phoneNumber, String address, int grade, List<Clazz> clazzList, List<Employee> employeeList) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.grade = grade;
        this.clazzList = clazzList;
        this.employeeList = employeeList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<Clazz> getClazzList() {
        return clazzList;
    }

    public void setClazzList(List<Clazz> clazzList) {
        this.clazzList = clazzList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }



    public void addClass(Clazz clazz) {
        if (this.clazzList == null) {
            this.clazzList = new ArrayList<>();
        }
        this.clazzList.add(clazz);
        clazz.setSchool(this);
    }

    public void addEmployee(Employee employee) {
        if (this.employeeList == null) {
            this.employeeList = new ArrayList<>();
        }
        this.employeeList.add(employee);
        employee.setSchool(this);
    }
}
