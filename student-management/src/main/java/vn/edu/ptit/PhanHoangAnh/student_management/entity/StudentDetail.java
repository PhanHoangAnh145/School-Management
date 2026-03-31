package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Blob;

@Entity
@Table(name = "student_detail")
public class StudentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "full_name")
    private int fullName;

    @Lob
    @Column(name = "avatar")
    private Blob avatar;

    @Column(name = "address")
    private int address;

    @Column(name = "hobby")
    private int hobby;

    @OneToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference(value = "student-detail")
    private Student student;

    public StudentDetail() {
    }

    public StudentDetail(int fullName, Blob avatar, int address, int hobby, Student student) {
        this.fullName = fullName;
        this.avatar = avatar;
        this.address = address;
        this.hobby = hobby;
        this.student = student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFullName() {
        return fullName;
    }

    public void setFullName(int fullName) {
        this.fullName = fullName;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getHobby() {
        return hobby;
    }

    public void setHobby(int hobby) {
        this.hobby = hobby;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


}
