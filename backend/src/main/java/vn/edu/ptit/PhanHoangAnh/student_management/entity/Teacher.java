package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher{
    @Id
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
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference(value = "teacher-employee")
    private Employee employee;

    public void addClazz(Clazz clazzRq) {
        if (clazzList == null) {
            clazzList = new ArrayList<>();
        }
        if (!this.clazzList.contains(clazzRq)) {
            clazzList.add(clazzRq);
            if (clazzRq.getTeacherList() == null)
                clazzRq.setTeacherList(new ArrayList<>());
            clazzRq.getTeacherList().add(this);
        }
    }

    public void addSubject(Subject subjectRq) {
        if (subjectList == null) {
            subjectList = new ArrayList<>();
        }
        if (!this.subjectList.contains(subjectRq)) {
            subjectList.add(subjectRq);
            if (subjectRq.getTeacherList() == null)
                subjectRq.setTeacherList(new ArrayList<>());
            subjectRq.getTeacherList().add(this);
        }
    }

    public void clearClazzList() {
        if (this.clazzList != null) {
            for (Clazz clazz : new ArrayList<>(this.clazzList)) {
                if (clazz.getTeacherList() != null)
                    clazz.getTeacherList().remove(this);
            }
            this.clazzList.clear();
        }
    }

    public void clearSubjectList() {
        if (this.subjectList != null) {
            for (Subject subject : new ArrayList<>(this.subjectList)) {
                if (subject.getTeacherList() != null)
                    subject.getTeacherList().remove(this);
            }
            this.subjectList.clear();
        }
    }
}
