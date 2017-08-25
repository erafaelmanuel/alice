package com.remswork.project.alice.model.support;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {

    private int status;
    private String message;

    public Message() {
        super();
    }

    public Message(int status, String message) {
        this();
        this.status = status;
        this.message = message;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
