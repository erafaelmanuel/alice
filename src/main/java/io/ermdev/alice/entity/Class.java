package io.ermdev.alice.entity;

import io.ermdev.alice.dto.StudentDto;
import io.ermdev.alice.dto.SubjectDto;
import io.ermdev.alice.dto.TeacherDto;
import io.ermdev.mapfierj.MapTo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @MapTo(TeacherDto.class)
    @ManyToOne
    @JoinColumn(name = "teacherId")
    private Teacher teacher;

    @OneToMany(mappedBy = "_class")
    private List<Schedule> schedules = new ArrayList<>();

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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}