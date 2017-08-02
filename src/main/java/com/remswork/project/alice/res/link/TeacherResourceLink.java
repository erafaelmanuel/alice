package com.remswork.project.alice.res.link;

import com.remswork.project.alice.model.Link;
import com.remswork.project.alice.res.TeacherResource;

import javax.ws.rs.core.UriInfo;

public class TeacherResourceLink {

    private UriInfo uriInfo;

    public TeacherResourceLink(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link self(int teacherId){
        String rel =  "Self";
        String uri = uriInfo.getBaseUriBuilder()
                .path(TeacherResource.class)
                .path(Integer.toString(teacherId))
                .build().toString();
        return new Link(uri, rel);
    }
}
