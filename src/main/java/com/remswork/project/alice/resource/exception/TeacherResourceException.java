package com.remswork.project.alice.resource.exception;

import com.remswork.project.alice.model.Teacher;

public class TeacherResourceException extends RuntimeException {

    public TeacherResourceException(){
        super();
    }

    public TeacherResourceException(final String message) {
        super(message);
    }
}
