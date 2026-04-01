package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonBackReference(value = "school-employee")
    public School school;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "employee-detail")
    private EmployeeDetail employeeDetail;

    @OneToOne(mappedBy = "employee")
    @JsonManagedReference(value = "teacher-employee")
    public Teacher teacher;

    public Employee() {
    }

    public Employee(String name, String role, School school, EmployeeDetail employeeDetail) {
        this.name = name;
        this.role = role;
        this.school = school;
        this.employeeDetail = employeeDetail;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public School getSchool() {
        return school;
    }

    public EmployeeDetail getEmployeeDetail() {
        return employeeDetail;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setEmployeeDetail(EmployeeDetail employeeDetail) {
        this.employeeDetail = employeeDetail;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }


    public void addTeacher(Teacher teacher) {
        this.setTeacher(teacher);
        teacher.setEmployee(this);
    }
}
