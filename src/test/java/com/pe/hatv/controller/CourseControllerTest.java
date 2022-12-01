package com.pe.hatv.controller;

import com.pe.hatv.model.entity.Course;
import com.pe.hatv.repository.CourseRepository;
import com.pe.hatv.router.RouterCourseConfig;
import com.pe.hatv.router.handler.CourseHandler;
import com.pe.hatv.service.impl.CourseServiceImpl;
import com.pe.hatv.util.CourseUtil;
import com.pe.hatv.validator.RequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CourseHandler.class)
@ContextConfiguration(classes = {RouterCourseConfig.class, CourseHandler.class, RequestValidator.class})
@Import({CourseServiceImpl.class})
public class CourseControllerTest {

	@Autowired
	private ApplicationContext context;

	private WebTestClient client;

	@MockBean
	private CourseRepository repository;

	@BeforeEach
	public void setup() {
		this.client = WebTestClient.bindToApplicationContext(context).build();
	}

	@Test
	public void shouldReturnStatusOkWhenListAllCourse() {

		var mockList = CourseUtil.mockListCourse();

		when(repository.findAll()).thenReturn(Flux.fromIterable(mockList));

		client.get()
				.uri("/courses")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBodyList(Course.class)
				.hasSize(2);
	}

	@Test
	public void shouldReturnStatusCreatedWhenCreateCourse(){

		var mockCourse = CourseUtil.mockCourse();

		when(repository.save(any())).thenReturn(Mono.just(mockCourse));

		client.post()
				.uri("/courses")
				.body(Mono.just(mockCourse), Course.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isCreated()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.name").isEqualTo("Spring WebFlux")
				.jsonPath("$.acronym").isEqualTo("SWF")
				.jsonPath("$.status").isBoolean();
	}

	@Test
	public void shouldReturnStatusOkWhenUpdateCourse(){

		var mockCourse = CourseUtil.mockCourse();

		when(repository.findById(anyString())).thenReturn(Mono.just(mockCourse));

		var mockCourseUpdate = CourseUtil.mockCourse();
		mockCourseUpdate.setName("Spring Boot");
		mockCourseUpdate.setAcronym("SPB");

		when(repository.save(any())).thenReturn(Mono.just(mockCourseUpdate));

		client.put()
				.uri("/courses/1")
				.body(Mono.just(mockCourseUpdate), Course.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.name").isEqualTo("Spring Boot")
				.jsonPath("$.acronym").isEqualTo("SPB");
	}

	@Test
	public void shouldReturnNoContentWhenDeleteCourse(){

		var mockCourse = CourseUtil.mockCourse();

		when(repository.findById(anyString())).thenReturn(Mono.just(mockCourse));

		when(repository.deleteById(anyString())).thenReturn(Mono.empty());

		client.delete()
				.uri("/courses/1")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNoContent();
	}
}
