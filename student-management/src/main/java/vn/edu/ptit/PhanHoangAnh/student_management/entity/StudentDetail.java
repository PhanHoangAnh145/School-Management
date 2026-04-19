package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Table(name = "student_detail")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Lob
    @Column(name = "avatar")
    private Blob avatar;

    @Column(name = "address")
    private String address;

    @Column(name = "hobby")
    private String hobby;

    @OneToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference(value = "student-detail")
    private Student student;

}
