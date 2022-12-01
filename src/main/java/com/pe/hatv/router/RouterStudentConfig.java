package com.pe.hatv.router;

import com.pe.hatv.router.handler.StudentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class RouterStudentConfig {

	@Bean
	public RouterFunction<ServerResponse> routeStudents(StudentHandler handler){
		return route(GET("/students"), handler::findAll)
				.andRoute(GET("/students/{id}"), handler::findById)
				.andRoute(POST("/students"), handler::create)
				.andRoute(PUT("/students/{id}"), handler::update)
				.andRoute(DELETE("/students/{id}"), handler::delete);
	}

}
