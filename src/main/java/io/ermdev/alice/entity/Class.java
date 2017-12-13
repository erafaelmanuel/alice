package io.ermdev.alice.entity;

import io.ermdev.alice.dto.StudentDto;
import io.ermdev.alice.dto.SubjectDto;
import io.ermdev.mapfierj.MapTo;

import javax.persistence.*;

@Table(name="tblclass")
@Entity
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @MapTo(StudentDto.class)
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    @MapTo(SubjectDto.class)
    @ManyToOne
    @JoinColumn(name = "subjectId")
    private Subject subject;

    public Class(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}