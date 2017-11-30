package io.ermdev.alice.controller;

import io.ermdev.alice.model.Class;
import io.ermdev.alice.model.Student;
import io.ermdev.alice.repository.ClassRepository;
import io.ermdev.alice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SampleController {

    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("hello")
    public List<Class> hello() {
        return classRepository.findAll();
    }

    @GetMapping("done")
    public String done() {
        Class c = new Class();
        Class c2 = new Class();

        Student s = new Student();
        s.setFirstName("Rafael");
        s.setLastName("Manuel");

        Student s2 = new Student();
        s2.setFirstName("Rafael");
        s2.setLastName("Manuel");

        studentRepository.save(s);
        studentRepository.save(s2);

        c.getStudents().add(s);
        c.getStudents().add(s2);

        c2.getStudents().add(s);
        c2.getStudents().add(s2);


        classRepository.save(c);
        classRepository.save(c2);
        return "done";
    }

    @GetMapping("delete")
    public String delete() {
        Class c = classRepository.getOne(1L);
        c.getStudents().remove(0);
        classRepository.save(c);
        return "done";
    }
}
