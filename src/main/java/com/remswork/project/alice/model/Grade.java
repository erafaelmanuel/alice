package com.remswork.project.alice.model;

import com.remswork.project.alice.model.support.Link;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@Entity
@Table(name = "tblgrade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double score;
    @OneToOne
    @JoinColumn(name = "termId")
    private Term term;
    @OneToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @OneToOne
    @JoinColumn(name = "classId")
    private Class _class;
    @Transient
    private List<Link> links;

    public Grade() {
        links = new ArrayList<>();
    }


    public Grade(double score) {
        this();
        this.score = score;
    }
    public Grade(long id, double score) {
        this();
        this.id = id;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Class get_class() {
        return _class;
    }

    public void set_class(Class _class) {
        this._class = _class;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
        boolean isExist = false;
        for (Link eachLink : links) {
            if(eachLink.getRel().equalsIgnoreCase(link.getRel())) {
                isExist = true;
                break;
            }
        }
        if(!isExist)
            links.add(link);
    }
}
