package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private School school;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "employee-detail")
    private EmployeeDetail employeeDetail;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "teacher-employee")
    private Teacher teacher;

    public Employee(String name, String role, School school, EmployeeDetail employeeDetail) {
        this.name = name;
        this.role = role;
        this.school = school;
        this.employeeDetail = employeeDetail;
    }

    public void addTeacher(Teacher teacher) {
        this.setTeacher(teacher);
        teacher.setEmployee(this);
    }
}
