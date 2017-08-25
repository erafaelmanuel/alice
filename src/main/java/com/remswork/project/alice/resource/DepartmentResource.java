package com.remswork.project.alice.resource;

import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.resource.exception.DepartmentResourceException;
import com.remswork.project.alice.resource.links.DepartmentResourceLinks;
import com.remswork.project.alice.service.impl.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;


@Component
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("department")
public class DepartmentResource {

    @Autowired
    private DepartmentServiceImpl departmentService;
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("{departmentId}")
    public Response getDepartmentById(@PathParam("departmentId") long id){
        try{
            DepartmentResourceLinks resourceLinks = new DepartmentResourceLinks(uriInfo);
            Department department = departmentService.getDepartmentById(id);
            if(department == null)
                throw new DepartmentResourceException("No department resource found");
            department.addLink(resourceLinks.self(id));
            return Response
                    .status(Response.Status.OK)
                    .entity(department)
                    .build();
        }catch (DepartmentResourceException e){
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    public Response getDepartmentList(){
        try{
            DepartmentResourceLinks resourceLinks = new DepartmentResourceLinks(uriInfo);
            List<Department> departmentList = departmentService.getDepartmentList();
            if(departmentList.size() < 1)
                throw new DepartmentResourceException("No Department resource found");

            for(Department d : departmentList)
                d.addLink(resourceLinks.self(d.getId()));
            GenericEntity<List<Department>> entity = new GenericEntity<List<Department>>(departmentList){};
            return Response
                    .status(Response.Status.OK)
                    .entity(entity)
                    .build();
        }catch (DepartmentResourceException e){
            e.printStackTrace();
            return  Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(new GenericEntity<List<Department>>(new ArrayList<>()){})
                    .build();
        }
    }

    @POST
    public Response addDepartment(Department department){
        try{
            DepartmentResourceLinks resourceLinks = new DepartmentResourceLinks(uriInfo);
            department = departmentService.addDepartment(department);
            if(department == null)
                throw new DepartmentResourceException("No Department resource to display");
            department.addLink(resourceLinks.self(department.getId()));
            return Response
                    .status(Response.Status.OK)
                    .entity(department)
                    .build();
        }catch(DepartmentResourceException e){
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{departmentId}")
    public Response updateDepartmentById(@PathParam("departmentId") long id, Department newDepartment){
        try{
            DepartmentResourceLinks resourceLinks = new DepartmentResourceLinks(uriInfo);
            Department department = departmentService.updateDepartmentById(id, newDepartment);
            if(department == null)
                throw new DepartmentResourceException("No Department resource to display");
            department.addLink(resourceLinks.self(id));
            return Response
                    .status(Response.Status.OK)
                    .entity(department)
                    .build();
        }catch (DepartmentResourceException e){
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{departmentId}")
    public Response deleteDepartmentById(@PathParam("departmentId") long id){
        try{
            DepartmentResourceLinks resourceLinks = new DepartmentResourceLinks(uriInfo);
            Department department = departmentService.deleteDepartmentById(id);
            if(department == null)
                throw new DepartmentResourceException("No Department resource to display");
            department.addLink(resourceLinks.self(id));
            return Response
                    .status(Response.Status.OK)
                    .entity(department)
                    .build();
        }catch (DepartmentResourceException e){
            e.printStackTrace();
            return Response.status(Response.Status.OK).build();
        }
    }

}
