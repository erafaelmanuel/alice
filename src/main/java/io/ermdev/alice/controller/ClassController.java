package io.ermdev.alice.controller;

import io.ermdev.alice.dto.ClassDto;
import io.ermdev.alice.dto.StudentDto;
import io.ermdev.alice.dto.SubjectDto;
import io.ermdev.alice.entity.Class;
import io.ermdev.alice.entity.Student;
import io.ermdev.alice.entity.Subject;
import io.ermdev.alice.repository.ClassRepository;
import io.ermdev.alice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClassController {

    private ClassRepository classRepository;
    private StudentRepository studentRepository;

    @Autowired
    public ClassController(ClassRepository classRepository, StudentRepository studentRepository) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("class/{classId}")
    public ClassDto getById(@PathVariable("classId") Long classId) {
        Class _class = classRepository.findById(classId);

        Student student = _class.getStudent();
        StudentDto studentDto = new StudentDto(student.getId(), student.getFirstName(), student.getLastName());

        Subject subject = _class.getSubject();
        SubjectDto subjectDto = new SubjectDto();

        return new ClassDto(_class.getId(), studentDto, subjectDto);
    }

    @GetMapping("class/all")
    public List<ClassDto> getAll() {
        List<ClassDto> classes = new ArrayList<>();
        classRepository.findAll().parallelStream().forEach(_class->{
            Student student = _class.getStudent();
            System.out.println(student);
            StudentDto studentDto = new StudentDto(student.getId(), student.getFirstName(), student.getLastName());

            Subject subject = _class.getSubject();
            SubjectDto subjectDto = new SubjectDto();

            classes.add(new ClassDto(_class.getId(), studentDto, subjectDto));
        });
        return classes;
    }

    @PostMapping("class/add")
    public ClassDto add(@RequestParam(value = "studentId", required = false) Long studentId,
                     @RequestParam(value = "subjectId", required = false) Long subjectId, @RequestBody Class _class) {
        Student student = studentRepository.findById(studentId);
        StudentDto studentDto = new StudentDto(student.getId(), student.getFirstName(), student.getLastName());

        Subject subject = new Subject();
        SubjectDto subjectDto = new SubjectDto();

        _class.setStudent(student);
        //_class.setSubject(subject);
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

        subject = _class.getSubject();
        SubjectDto subjectDto = new SubjectDto();

        _class.setStudent(student);
        //_class.setSubject(subject);

        return new ClassDto(classId, studentDto, subjectDto);
    }

    @DeleteMapping("class/delete/{classId}")
    public Class deleteById(@PathVariable("classId") Long classId) {
        Class _class = classRepository.findOne(classId);
        _class.setStudent(null);
        _class.setSubject(null);

        classRepository.save(_class);
        classRepository.delete(classId);
        return _class;
    }
}
