package io.ermdev.alice.entity;

import io.ermdev.alice.dto.CurriculumDto;
import io.ermdev.alice.dto.SubjectDto;
import io.ermdev.mapfierj.MapTo;
import io.ermdev.mapfierj.NoRepeat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoRepeat
@Table(name="tblterm")
@Entity
public class Term {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private Integer semester;
    private Integer year;

    @MapTo(CurriculumDto.class)
    @ManyToOne
    private Curriculum curriculum;

    @MapTo(value = SubjectDto.class, collection = true)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tblterm_subject", joinColumns = @JoinColumn(name = "termId"),
            inverseJoinColumns = @JoinColumn(name="subjectId"))
    private List<Subject> subjects = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
