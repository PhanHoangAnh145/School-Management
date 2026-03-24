package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "grade_report")
public class GradeReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "ten_mark")
    private double tenMark;

    @ManyToOne
    @JoinColumn(name = "transcription_id")
    @JsonBackReference
    private Transcription transcription;

    public GradeReport() {
    }

    public GradeReport(String name, double tenMark, Transcription transcription) {
        this.name = name;
        this.tenMark = tenMark;
        this.transcription = transcription;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getTenMark() {
        return tenMark;
    }

    public Transcription getTranscription() {
        return transcription;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTenMark(double tenMark) {
        this.tenMark = tenMark;
    }

    public void setTranscription(Transcription transcription) {
        this.transcription = transcription;
    }


}
