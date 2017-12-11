package io.ermdev.alice.controller;

import io.ermdev.alice.dto.ClassDto;
import io.ermdev.alice.dto.StudentDto;
import io.ermdev.alice.dto.SubjectDto;
import io.ermdev.alice.entity.Class;
import io.ermdev.alice.entity.Student;
import io.ermdev.alice.entity.Subject;
import io.ermdev.alice.repository.ClassRepository;
import io.ermdev.alice.repository.StudentRepository;
import io.ermdev.alice.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClassController {

    private ClassRepository classRepository;
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;

    @Autowired
    public ClassController(ClassRepository classRepository, StudentRepository studentRepository,
                           SubjectRepository subjectRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("class/{classId}")
    public ClassDto getById(@PathVariable("classId") Long classId) {
        Class _class = classRepository.findById(classId);

        Student student = _class.getStudent();
        StudentDto studentDto = new StudentDto(student.getId(), student.getFirstName(), student.getLastName());

        Subject subject = _class.getSubject();
        SubjectDto subjectDto = new SubjectDto(subject.getId(), subject.getName(), subject.getDescription(), subject.getUnit());

        return new ClassDto(_class.getId(), studentDto, subjectDto);
    }

    @GetMapping("class/all")
    public List<ClassDto> getAll() {
        List<ClassDto> classes = new ArrayList<>();
        classRepository.findAll().parallelStream().forEach(_class->{
            Student student = _class.getStudent();
            StudentDto studentDto = new StudentDto(student.getId(), student.getFirstName(), student.getLastName());

            Subject subject = _class.getSubject();
            SubjectDto subjectDto = new SubjectDto(subject.getId(), subject.getName(), subject.getDescription(), subject.getUnit());

            classes.add(new ClassDto(_class.getId(), studentDto, subjectDto));
        });
        return classes;
    }

    @PostMapping("class/add")
    public ClassDto add(@RequestParam(value = "studentId", required = true) Long studentId,
                     @RequestParam(value = "subjectId", required = true) Long subjectId, @RequestBody Class _class) {
        Student student = studentRepository.findById(studentId);
        StudentDto studentDto = new StudentDto(student.getId(), student.getFirstName(), student.getLastName());

        Subject subject = subjectRepository.findById(subjectId);
        SubjectDto subjectDto = new SubjectDto(subject.getId(), subject.getName(), subject.getDescription(), subject.getUnit());

        _class.setStudent(student);
        _class.setSubject(subject);
        _class = classRepository.save(_class);
        return new ClassDto(_class.getId(), studentDto, subjectDto);
    }

    @PutMapping("class/update/{classId}")
    public ClassDto updateById(@PathVariable("classId") Long classId,
                               @RequestParam(value = "studentId", required = false) Long studentId,
                               @RequestParam(value = "subjectId", required = false) Long subjectId,
                               @RequestBody Class _class) {
        Class currentClass = classRepository.findById(classId);
        Student student = currentClass.getStudent();
        Subject subject = currentClass.getSubject();

        if(studentId != null) {
            student = studentRepository.findById(studentId);
        }
        StudentDto studentDto = new StudentDto(student.getId(), student.getFirstName(), student.getLastName());

        if(subjectId != null) {
            subject = subjectRepository.findById(subjectId);
        }
        SubjectDto subjectDto = new SubjectDto(subject.getId(), subject.getName(), subject.getDescription(), subject.getUnit());

        _class.setId(classId);
        _class.setStudent(student);
        _class.setSubject(subject);
        classRepository.save(_class);

        return new ClassDto(classId, studentDto, subjectDto);
    }

    @DeleteMapping("class/delete/{classId}")
    public ClassDto deleteById(@PathVariable("classId") Long classId) {
        Class _class = classRepository.findOne(classId);

        Student student = _class.getStudent();
        StudentDto studentDto = new StudentDto(student.getId(), student.getFirstName(), student.getLastName());

        Subject subject = _class.getSubject();
        SubjectDto subjectDto = new SubjectDto(subject.getId(), subject.getName(), subject.getDescription(), subject.getUnit());

        _class.setStudent(null);
        _class.setSubject(null);

        classRepository.save(_class);
        classRepository.delete(classId);
        return new ClassDto(classId, studentDto, subjectDto);
    }
}
