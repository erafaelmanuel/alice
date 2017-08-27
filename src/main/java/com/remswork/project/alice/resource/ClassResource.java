package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ClassResourceLinks;
import com.remswork.project.alice.service.impl.ClassServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("class")
public class ClassResource {

    @Autowired
    private ClassServiceImpl classService;
    @Autowired
    private ClassScheduleListResource classScheduleListResource;
    @Context
    private UriInfo uriInfo;
    @QueryParam("teacherId")
    private long teacherId;
    @QueryParam("subjectId")
    private long subjectId;
    @QueryParam("sectionId")
    private long sectionId;
    @QueryParam("scheduleId")
    private long scheduleId;

    @GET
    @Path("{classId}")
    public Response getClassById(@PathParam("classId") long id) {
        try{
            ClassResourceLinks resourceLinks = new ClassResourceLinks(uriInfo);
            Class _class = classService.getClassById(id);
            _class.addLink(resourceLinks.self(id));
            _class.addLink(resourceLinks.schedule(id));
            return Response.status(Response.Status.OK).entity(_class).build();
        } catch (ClassException e){
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getClassList() {
        try{
            ClassResourceLinks resourceLinks = new ClassResourceLinks(uriInfo);
            List<Class> classList = classService.getClassList();
            for (Class _class : classList) {
                _class.addLink(resourceLinks.self(_class.getId()));
                _class.addLink(resourceLinks.schedule(_class.getId()));
            }
            GenericEntity<List<Class>> entity = new GenericEntity<List<Class>>(classList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        } catch (ClassException e){
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addClass(Class _class) {
        try{
            ClassResourceLinks resourceLinks = new ClassResourceLinks(uriInfo);
            _class = classService.addClass(_class, teacherId, subjectId, sectionId);
            _class.addLink(resourceLinks.self(_class.getId()));
            _class.addLink(resourceLinks.schedule(_class.getId()));
            return Response.status(Response.Status.OK).entity(_class).build();
        } catch (ClassException e){
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{classId}")
    public Response updateClassById(@PathParam("classId")long id, Class newClass) {
        try{
            ClassResourceLinks resourceLinks = new ClassResourceLinks(uriInfo);
            Class _class = classService.updateClassId(id, newClass, teacherId, subjectId, sectionId);
            _class.addLink(resourceLinks.self(id));
            _class.addLink(resourceLinks.schedule(id));
            return Response.status(Response.Status.OK).entity(_class).build();
        } catch (ClassException e){
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{classId}")
    public Response deleteClassById(@PathParam("classId") long id) {
        try {
            ClassResourceLinks resourceLinks = new ClassResourceLinks(uriInfo);
            Class _class = classService.deleteClassById(id);
            _class.addLink(resourceLinks.self(id));
            _class.addLink(resourceLinks.schedule(id));
            return Response.status(Response.Status.OK).entity(_class).build();
        } catch (ClassException e){
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @Path("{classId}/schedule")
    public ClassScheduleListResource classScheduleListResource() {
        classScheduleListResource.setScheduleId(scheduleId);
        classScheduleListResource.setUriInfo(uriInfo);
        return classScheduleListResource;
    }
}
