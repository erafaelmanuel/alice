package com.remswork.project.alice.config;

import com.remswork.project.alice.resource.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		register(TeacherResource.class);
		register(DepartmentResource.class);
		register(StudentResource.class);
		register(SubjectResource.class);
		register(ScheduleResource.class);
	}

}
