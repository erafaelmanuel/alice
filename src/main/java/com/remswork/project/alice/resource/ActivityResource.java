package com.remswork.project.alice.resource;

import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Activity;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.resource.links.ActivityResourceLinks;
import com.remswork.project.alice.resource.links.StudentResourceLinks;
import com.remswork.project.alice.resource.links.SubjectResourceLinks;
import com.remswork.project.alice.service.impl.ActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("activity")
public class ActivityResource {

    @Autowired
    private ActivityServiceImpl activityService;
    @Context
    private UriInfo uriInfo;
    @QueryParam("studentId")
    private long studentId;
    @QueryParam("subjectId")
    private long subjectId;

    @GET
    @Path("{activityId}")
    public Response getActivityById(@PathParam("activityId") long id) {
        try {
            ActivityResourceLinks resourceLinks = new ActivityResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            Activity activity = activityService.getActivityById(id);
            activity.addLink(resourceLinks.self(id));
            if(activity.getStudent() != null)
                activity.getStudent().addLink(studentResourceLinks.self(activity.getStudent().getId()));
            if(activity.getSubject() != null)
                activity.getSubject().addLink(subjectResourceLinks.self(activity.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(activity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @GET
    public Response getActivityList() {
        try {
            List<Activity> activityList;
            ActivityResourceLinks resourceLinks = new ActivityResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            if(studentId != 0 || subjectId != 0)
                activityList = activityService.getActivityListByStudentAndSubjectId(studentId, subjectId);
            else
                activityList = activityService.getActivityList();
            for(Activity activity : activityList) {
                activity.addLink(resourceLinks.self(activity.getId()));
                if(activity.getStudent() != null)
                    activity.getStudent().addLink(studentResourceLinks.self(activity.getStudent().getId()));
                if(activity.getSubject() != null)
                    activity.getSubject().addLink(subjectResourceLinks.self(activity.getSubject().getId()));
            }
            GenericEntity<List<Activity>> entity = new GenericEntity<List<Activity>>(activityList){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(404, "Not Found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }
    }

    @POST
    public Response addActivity(Activity activity) {
        try {
            ActivityResourceLinks resourceLinks = new ActivityResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            activity = activityService.addActivity(activity, studentId, subjectId);
            activity.addLink(resourceLinks.self(activity.getId()));
            if(activity.getStudent() != null)
                activity.getStudent().addLink(studentResourceLinks.self(activity.getStudent().getId()));
            if(activity.getSubject() != null)
                activity.getSubject().addLink(subjectResourceLinks.self(activity.getSubject().getId()));
            return Response.status(Response.Status.CREATED).entity(activity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @PUT
    @Path("{activityId}")
    public Response updateActivityById(@PathParam("activityId") long id, Activity newActivity) {
        try {
            ActivityResourceLinks resourceLinks = new ActivityResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            Activity activity = activityService.updateActivityById(id, newActivity, studentId, subjectId);
            activity.addLink(resourceLinks.self(activity.getId()));
            if(activity.getStudent() != null)
                activity.getStudent().addLink(studentResourceLinks.self(activity.getStudent().getId()));
            if(activity.getSubject() != null)
                activity.getSubject().addLink(subjectResourceLinks.self(activity.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(activity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }

    @DELETE
    @Path("{activityId}")
    public Response deleteActivityById(@PathParam("activityId") long id) {
        try {
            ActivityResourceLinks resourceLinks = new ActivityResourceLinks(uriInfo);
            StudentResourceLinks studentResourceLinks = new StudentResourceLinks(uriInfo);
            SubjectResourceLinks subjectResourceLinks = new SubjectResourceLinks(uriInfo);

            Activity activity = activityService.deleteActivityById(id);
            activity.addLink(resourceLinks.self(activity.getId()));
            if(activity.getStudent() != null)
                activity.getStudent().addLink(studentResourceLinks.self(activity.getStudent().getId()));
            if(activity.getSubject() != null)
                activity.getSubject().addLink(subjectResourceLinks.self(activity.getSubject().getId()));
            return Response.status(Response.Status.OK).entity(activity).build();
        }catch (GradingFactorException e) {
            e.printStackTrace();
            Message message = new Message(400, "Bad Request", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
    }
}
