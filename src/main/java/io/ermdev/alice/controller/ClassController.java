package io.ermdev.alice.controller;

import io.ermdev.alice.entity.Class;
import io.ermdev.alice.repository.ClassRepository;
import io.ermdev.alice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Class getById(@PathVariable("classId") Long classId) {
        return classRepository.findOne(classId);
    }

    @GetMapping("class/all")
    public List<Class> getAll() {
        return classRepository.findAll();
    }

    @PostMapping("class/add")
    public Class add(Class _class) {
        return classRepository.save(_class);
    }

    @PutMapping("class/update/{classId}")
    public Class updateById(@PathVariable("classId") Long classId, Class new_class) {
        Class _class = classRepository.findOne(classId);
        return classRepository.save(_class);
    }

    @DeleteMapping("class/delete/{classId}")
    public Class deleteById(@PathVariable("classId") Long classId) {
        Class _class = classRepository.findOne(classId);
        _class.getStudents().clear();

        classRepository.save(_class);
        classRepository.delete(classId);
        return _class;
    }
}
