package com.pe.hatv.router.handler;

import com.pe.hatv.model.entity.Course;
import com.pe.hatv.service.CourseService;
import com.pe.hatv.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class CourseHandler {

	private final CourseService courseService;

	private final RequestValidator requestValidator;

	public Mono<ServerResponse> findAll(ServerRequest req) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(courseService.findAll(), Course.class);
	}

	public Mono<ServerResponse> findById(ServerRequest req) {
		String id = req.pathVariable("id");
		return courseService.findById(id)
				.flatMap(course -> ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromValue(course))
				)
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> create(ServerRequest req) {
		Mono<Course> courseMono = req.bodyToMono(Course.class);

		return courseMono
				.flatMap(requestValidator::validate)
				.flatMap(courseService::save)
				.flatMap(course -> ServerResponse
						.created(URI.create(req.uri().toString().concat("/").concat(course.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromValue(course))
				);
	}

	public Mono<ServerResponse> update(ServerRequest req) {
		String id = req.pathVariable("id");

		Mono<Course> courseMono = req.bodyToMono(Course.class);
		Mono<Course> courseBd = courseService.findById(id);

		return courseBd
				.zipWith(courseMono, (db, cs) -> {
					db.setId(id);
					db.setName(cs.getName());
					db.setAcronym(cs.getAcronym());
					db.setStatus(cs.getStatus());
					return db;
				})
				.flatMap(requestValidator::validate)
				.flatMap(courseService::update)
				.flatMap(course -> ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromValue(course))
				)
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> delete(ServerRequest req) {
		String id = req.pathVariable("id");

		return courseService.findById(id)
				.flatMap(course -> courseService.delete(course.getId())
						.then(ServerResponse.noContent().build())
				)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
}
