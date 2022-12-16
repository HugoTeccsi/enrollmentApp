package com.pe.hatv.router.handler;

import com.pe.hatv.model.entity.StudentEnrollment;
import com.pe.hatv.service.StudentEnrollmentService;
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
public class StudentEnrollmentHandler {

	private final StudentEnrollmentService service;

	private final RequestValidator requestValidator;

	public Mono<ServerResponse> create(ServerRequest req) {
		Mono<StudentEnrollment> studentEnrollment = req.bodyToMono(StudentEnrollment.class);

		return studentEnrollment
				.flatMap(requestValidator::validate)
				.flatMap(service::save)
				.flatMap(student -> ServerResponse
						.created(URI.create(req.uri().toString().concat("/").concat(student.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromValue(student))
				);
	}

}
