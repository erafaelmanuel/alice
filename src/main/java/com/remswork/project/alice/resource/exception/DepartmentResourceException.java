package com.remswork.project.alice.resource.exception;

public class DepartmentResourceException extends RuntimeException {

    public DepartmentResourceException(){
        super();
    }

    public DepartmentResourceException(final String message){
        super(message);
    }
}
