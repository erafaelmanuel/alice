package com.remswork.project.alice.model;

import com.remswork.project.alice.model.support.Link;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@Entity
@Table(name = "tblactivityresult")
public class ActivityResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int score;
    @OneToOne
    @JoinColumn(name = "activityId")
    private Activity activity;
    @OneToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @Transient
    private List<Link> links;

    public ActivityResult() {
        links = new ArrayList<>();
    }

    public ActivityResult(int score) {
        this.score = score;
    }

    public ActivityResult(long id, int score) {
        this.id = id;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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
