package com.pe.hatv.router.handler;

import com.pe.hatv.model.entity.Student;
import com.pe.hatv.service.StudentService;
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
public class StudentHandler {

	private final StudentService studentService;

	private final RequestValidator requestValidator;

	public Mono<ServerResponse> findAll(ServerRequest req) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(studentService.findAll(), Student.class);
	}

	public Mono<ServerResponse> findById(ServerRequest req) {
		String id = req.pathVariable("id");
		return studentService.findById(id)
				.flatMap(student -> ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromValue(student))
				)
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> create(ServerRequest req) {
		Mono<Student> studentMono = req.bodyToMono(Student.class);

		return studentMono
				.flatMap(requestValidator::validate)
				.flatMap(studentService::save)
				.flatMap(student -> ServerResponse
						.created(URI.create(req.uri().toString().concat("/").concat(student.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromValue(student))
				);

	}

	public Mono<ServerResponse> update(ServerRequest req) {
		String id = req.pathVariable("id");

		Mono<Student> monoStudent = req.bodyToMono(Student.class);
		Mono<Student> monoDB = studentService.findById(id);

		return monoDB
				.zipWith(monoStudent, (db, st) -> {
					db.setId(id);
					db.setNames(st.getNames());
					db.setSurnames(st.getSurnames());
					db.setDni(st.getDni());
					db.setAge(st.getAge());
					return db;
				})
				.flatMap(requestValidator::validate)
				.flatMap(studentService::update)
				.flatMap(student -> ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromValue(student))
				)
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> delete(ServerRequest req) {
		String id = req.pathVariable("id");

		return studentService.findById(id)
				.flatMap(student -> studentService.delete(student.getId())
						.then(ServerResponse.noContent().build())
				)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
}
