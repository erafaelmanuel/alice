package io.ermdev.alice.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name="tblsubject")
@Entity
public class Subject {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToMany(mappedBy = "subjects", cascade = CascadeType.ALL)
    private List<Term> terms = new ArrayList<>();

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Class> classes = new ArrayList<>();

}
