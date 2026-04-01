package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher")
public class Teacher{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "teacher_class",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    @JsonManagedReference(value = "class-teacher")
    private List<Clazz> clazzList;

    @ManyToMany
    @JoinTable(
            name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    @JsonManagedReference(value = "teacher-subject")
    private List<Subject> subjectList;

    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference(value = "teacher-employee")
    private Employee employee;

    public Teacher() {
    }

    public Teacher(Long id, List<Clazz> clazzList, List<Subject> subjectList, Employee employee) {
        this.id = id;
        this.clazzList = clazzList;
        this.subjectList = subjectList;
        this.employee = employee;
    }

    public List<Clazz> getClazzList() {
        return clazzList;
    }

    public void setClazzList(List<Clazz> clazzList) {
        this.clazzList = clazzList;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }



    public void addClazz(Clazz clazzRq) {
        if (clazzList == null) {
            clazzList = new ArrayList<>();
        }
        if (!this.clazzList.contains(clazzRq)) {
            clazzList.add(clazzRq);
            clazzRq.getTeacherList().add(this);
        }
    }

    public void addSubject(Subject subjectRq) {
        if (subjectList == null) {
            subjectList = new ArrayList<>();
        }
        if (!this.subjectList.contains(subjectRq)) {
            subjectList.add(subjectRq);
            subjectRq.getTeacherList().add(this);
        }
    }

    public void clearClazzList() {
        if (this.clazzList != null) {
            for (Clazz clazz : new ArrayList<>(this.clazzList)) {
                clazz.getTeacherList().remove(this);
            }
            this.clazzList.clear();
        }
    }
    public void clearSubjectList() {
        if (this.subjectList != null) {
            for (Subject subject : new ArrayList<>(this.subjectList)) {
                subject.getTeacherList().remove(this);
            }
            this.subjectList.clear();
        }
    }
}
