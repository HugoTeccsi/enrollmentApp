package com.pe.hatv.router;

import com.pe.hatv.router.handler.CourseHandler;
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
public class RouterCourseConfig {

	@Bean
	public RouterFunction<ServerResponse> routeCourses(CourseHandler handler){
		return route(GET("/courses"), handler::findAll)
				.andRoute(GET("/courses/{id}"), handler::findById)
				.andRoute(POST("/courses"), handler::create)
				.andRoute(PUT("/courses/{id}"), handler::update)
				.andRoute(DELETE("/courses/{id}"), handler::delete);
	}
}
