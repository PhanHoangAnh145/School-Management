package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "course_registrations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_class_id")
    private CourseClass courseClass;

    @Column(name = "registration_time")
    private LocalDateTime registrationTime;

}