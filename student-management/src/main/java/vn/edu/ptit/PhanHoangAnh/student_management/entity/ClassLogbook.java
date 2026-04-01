package vn.edu.ptit.PhanHoangAnh.student_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "class_logbook")
public class ClassLogbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "class_id")
    @JsonBackReference(value = "class-logbook")
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }


}
