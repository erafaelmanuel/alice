package io.ermdev.alice.controller;

import io.ermdev.alice.dto.*;
import io.ermdev.alice.entity.Class;
import io.ermdev.alice.entity.Student;
import io.ermdev.alice.entity.Subject;
import io.ermdev.alice.entity.Term;
import io.ermdev.alice.repository.ClassRepository;
import io.ermdev.alice.repository.SubjectRepository;
import io.ermdev.alice.repository.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SubjectController {

    private SubjectRepository subjectRepository;
    private TermRepository termRepository;
    private ClassRepository classRepository;

    @Autowired
    public SubjectController(SubjectRepository subjectRepository, TermRepository termRepository,
                             ClassRepository classRepository) {
        this.subjectRepository = subjectRepository;
        this.termRepository = termRepository;
        this.classRepository = classRepository;
    }

    @GetMapping("subject/{subjectId}")
    public SubjectDto getSubjectById(@PathVariable("subjectId") Long subjectId) {
        List<TermDto> terms = new ArrayList<>();
        Subject subject = subjectRepository.findById(subjectId);
        subject.getTerms().parallelStream().forEach(term -> {
            CurriculumDto curriculum = new CurriculumDto();
            terms.add(new TermDto(term.getId(), term.getSemester(), term.getYear()));
        });
        return new SubjectDto(subject.getId(), subject.getName(), subject.getDescription(), subject.getUnit(), terms);
    }

    @GetMapping("subject/all")
    public List<SubjectDto> getAllSubject() {
        List<SubjectDto> subjects = new ArrayList<>();
        subjectRepository.findAll().parallelStream().forEach(subject -> {
            List<TermDto> terms = new ArrayList<>();
            subject.getTerms().parallelStream().forEach(term -> {
                CurriculumDto curriculum = new CurriculumDto();
                terms.add(new TermDto(term.getId(), term.getSemester(), term.getYear()));
            });
            subjects.add(new SubjectDto(subject.getId(), subject.getName(), subject.getDescription(), subject.getUnit(), terms));
        });
        return subjects;
    }

    @PostMapping("subject/add")
    public SubjectDto add(@RequestParam(value = "termIds", required = false) List<Long> termIds,
                          @RequestParam(value = "classIds", required = false) List<Long> classIds,
                          @RequestBody Subject subject) {
        List<TermDto> terms = new ArrayList<>();
        List<ClassDto> classes = new ArrayList<>();

        if(termIds != null && termIds.size() > 0) {
            termIds.parallelStream().forEach(termId-> {
                Term term = termRepository.findById(termId);
                terms.add(new TermDto(term.getId(), term.getSemester(), term.getYear()));
            });
        }
        if(classIds != null && classIds.size() > 0) {
            classIds.parallelStream().forEach(classId -> {
                Class _class = classRepository.findById(classId);
                Student student = _class.getStudent();
                StudentDto studentDto = new StudentDto(student.getId(), student.getFirstName(), student.getLastName());
                classes.add(new ClassDto(_class.getId(), studentDto));
            });
        }
        return new SubjectDto(subject.getId(), subject.getName(), subject.getDescription(), subject.getUnit(), terms);
    }

    @PutMapping("subject/update/{subjectId}")
    public SubjectDto updateById(@PathVariable("subjectId") Long subjectId) {
        return null;
    }
}
