package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "day_of_birth")
    private String dayOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference(value = "employee-detail")
    private Employee employee;


    public EmployeeDetail(String dayOfBirth, String address, String phoneNumber, Employee employee) {
        this.dayOfBirth = dayOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.employee = employee;
    }

}
