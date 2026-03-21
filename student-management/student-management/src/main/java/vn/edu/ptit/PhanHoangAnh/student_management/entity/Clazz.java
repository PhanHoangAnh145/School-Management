package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "class")
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "name")
    private String name;

    @Column(name = "grade")
    private int grade;

    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonBackReference
    private School school;

    @ManyToMany(mappedBy = "clazzList")
    @JsonBackReference
    private List<Teacher> teacherList;

    @OneToOne(mappedBy = "clazz", cascade = CascadeType.ALL)
    @JsonManagedReference
    private ClassLogbook classLogbook;

    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Student> studentList;

    public Clazz() {
    }

    public Clazz(String name, int grade, int year, School school, List<Teacher> teacherList, ClassLogbook classLogbook, List<Student> studentList) {
        this.name = name;
        this.grade = grade;
        this.year = year;
        this.school = school;
        this.teacherList = teacherList;
        this.classLogbook = classLogbook;
        this.studentList = studentList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }

    public int getYear() {
        return year;
    }

    public School getSchool() {
        return school;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public ClassLogbook getClassLogbook() {
        return classLogbook;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public void setClassLogbook(ClassLogbook classLogbook) {
        this.classLogbook = classLogbook;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", year=" + year +
                ", school=" + school +
                ", teacherList=" + teacherList +
                ", classLogbook=" + classLogbook +
                ", studentList=" + studentList +
                '}';
    }
    public void addStudent(Student student) {
        if (this.studentList == null) {
            this.studentList = new ArrayList<>();
        }
        this.studentList.add(student);
        student.setClazz(this);
    }
}
