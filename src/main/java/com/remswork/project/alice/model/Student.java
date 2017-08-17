package com.remswork.project.alice.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="tlbstudent")
public class Student {

    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private long id;
    private long studentNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    @OneToOne
    @JoinColumn(name="userDetailId")
    private UserDetail userDetail;

    public Student(){
        super();
    }

    public Student(long studentNumber, String firstName, String lastName, String middleName) {
        this();
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public Student(long id, long studentNumber, String firstName, String lastName, String middleName) {
        this(studentNumber, firstName, lastName, middleName);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
}
