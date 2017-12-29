package io.ermdev.alice.entity;

import io.ermdev.alice.dto.TermDto;
import io.ermdev.mapfierj.core.MapTo;
import io.ermdev.mapfierj.core.NoRepeat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoRepeat
@Table(name="tblcurriculum")
@Entity
public class Curriculum {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @MapTo(value = TermDto.class, collection = true)
    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
    private List<Term> terms = new ArrayList<>();

}
