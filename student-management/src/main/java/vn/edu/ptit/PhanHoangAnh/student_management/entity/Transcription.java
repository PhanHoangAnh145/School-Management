package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transcription")
public class Transcription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "year")
    private int year;

    @Column(name = "rate")
    private String rate;

    @Column(name = "mark")
    private double mark;

    @OneToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference(value = "student-transcript")
    private Student student;

    @OneToMany(mappedBy = "transcription", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "grade-transcript")
    private List<GradeReport> gradeReportList;

    public Transcription() {
    }

    public Transcription(int year, String rate, double mark, Student student, List<GradeReport> gradeReportList) {
        this.year = year;
        this.rate = rate;
        this.mark = mark;
        this.student = student;
        this.gradeReportList = gradeReportList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<GradeReport> getGradeReportList() {
        return gradeReportList;
    }

    public void setGradeReportList(List<GradeReport> gradeReportList) {
        this.gradeReportList = gradeReportList;
    }



    public void addGradeReport (GradeReport gradeReport) {
        if (this.gradeReportList == null) {
            this.gradeReportList = new ArrayList<>();
        }
        this.gradeReportList.add(gradeReport);
        gradeReport.setTranscription(this);
    }
}
