package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "class_logbook")
public class ClassLogbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "class_id")
    @JsonBackReference
    private Clazz clazz;

    public ClassLogbook() {
    }

    public ClassLogbook(String description, Clazz clazz) {
        this.description = description;
        this.clazz = clazz;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "ClassLogbook{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", clazz=" + clazz +
                '}';
    }
}
