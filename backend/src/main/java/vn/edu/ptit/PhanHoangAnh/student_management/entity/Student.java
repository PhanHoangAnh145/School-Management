package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@Entity
@Table(name = "student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @JsonBackReference(value = "class-student")
    private Clazz clazz;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "student-record")
    private StudentRecord studentRecord;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "student-detail")
    private StudentDetail studentDetail;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "student-parent")
    private Parent parent;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "student-transcript")
    private Transcription transcription;


    public Student(String name, String dateOfBirth, Clazz clazz, StudentRecord studentRecord, StudentDetail studentDetail, Parent parent, Transcription transcription) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.clazz = clazz;
        this.studentRecord = studentRecord;
        this.studentDetail = studentDetail;
        this.parent = parent;
        this.transcription = transcription;
    }

    public void addTranscription(Transcription transcription) {
        this.setTranscription(transcription);
        transcription.setStudent(this);
    }

}
