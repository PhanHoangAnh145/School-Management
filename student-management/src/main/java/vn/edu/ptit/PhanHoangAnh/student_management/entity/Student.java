package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Locale;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

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

    public Student() {
    }

    public Student(String name, String dateOfBirth, Clazz clazz, StudentRecord studentRecord, StudentDetail studentDetail, Parent parent, Transcription transcription) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.clazz = clazz;
        this.studentRecord = studentRecord;
        this.studentDetail = studentDetail;
        this.parent = parent;
        this.transcription = transcription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public StudentRecord getStudentRecord() {
        return studentRecord;
    }

    public void setStudentRecord(StudentRecord studentRecord) {
        this.studentRecord = studentRecord;
    }

    public StudentDetail getStudentDetail() {
        return studentDetail;
    }

    public void setStudentDetail(StudentDetail studentDetail) {
        this.studentDetail = studentDetail;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Transcription getTranscription() {
        return transcription;
    }

    public void setTranscription(Transcription transcription) {
        this.transcription = transcription;
    }


}
