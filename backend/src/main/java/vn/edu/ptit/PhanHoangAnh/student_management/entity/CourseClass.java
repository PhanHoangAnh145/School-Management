package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_class")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "class_code")
    private String classCode;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "max_students")
    private Long maxStudents;

    @Column(name = "current_students")
    private Long currentStudents;
}
