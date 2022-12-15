package com.pe.hatv.router;

import com.pe.hatv.router.handler.StudentEnrollmentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class RouterStudentEnrollmentConfig {

	@Bean
	public RouterFunction<ServerResponse> routeStudentEnrollment(StudentEnrollmentHandler handler){
		return route(POST("/student-enrollment"), handler::create);
	}

}
