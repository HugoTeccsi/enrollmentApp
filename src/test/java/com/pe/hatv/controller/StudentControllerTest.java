package com.pe.hatv.controller;

import com.pe.hatv.model.entity.Student;
import com.pe.hatv.repository.StudentRepository;
import com.pe.hatv.router.RouterStudentConfig;
import com.pe.hatv.router.handler.StudentHandler;
import com.pe.hatv.service.impl.StudentServiceImpl;
import com.pe.hatv.util.StudentUtil;
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
@WebFluxTest(StudentHandler.class)
@ContextConfiguration(classes = {RouterStudentConfig.class, StudentHandler.class, RequestValidator.class})
@Import({StudentServiceImpl.class})
public class StudentControllerTest {

	@Autowired
	private ApplicationContext context;

	private WebTestClient client;

	@MockBean
	private StudentRepository repository;

	@BeforeEach
	public void setup() {
		this.client = WebTestClient.bindToApplicationContext(context).build();
	}

	@Test
	public void shouldReturnStatusOkWhenListAllStudent(){

		var mockList = StudentUtil.mockFindAll();

		when(repository.findAll()).thenReturn(Flux.fromIterable(mockList));

		client.get()
				.uri("/students")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBodyList(Student.class)
				.hasSize(2);
	}

	@Test
	public void shouldReturnStatusCreatedWhenCreateStudent(){

		var mockStudent = StudentUtil.mockCreate();

		when(repository.save(any())).thenReturn(Mono.just(mockStudent));

		client.post()
				.uri("/students")
				.body(Mono.just(mockStudent), Student.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isCreated()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.names").isEqualTo("Hugo")
				.jsonPath("$.surnames").isEqualTo("Teccsi Veredas")
				.jsonPath("$.dni").isEqualTo("11112222")
				.jsonPath("$.age").isEqualTo(33);
	}

	@Test
	public void shouldReturnStatusOkWhenUpdateStudent(){

		var mockStudent = StudentUtil.mockCreate();

		when(repository.findById(anyString())).thenReturn(Mono.just(mockStudent));

		var mockStudentUpdate = StudentUtil.mockCreate();
		mockStudentUpdate.setNames("Hugox");
		mockStudentUpdate.setDni("44444444");

		when(repository.save(any())).thenReturn(Mono.just(mockStudentUpdate));

		client.put()
				.uri("/students/1")
				.body(Mono.just(mockStudentUpdate), Student.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.names").isEqualTo("Hugox")
				.jsonPath("$.surnames").isEqualTo("Teccsi Veredas")
				.jsonPath("$.dni").isEqualTo("44444444")
				.jsonPath("$.age").isEqualTo(33);
	}

	@Test
	public void shouldReturnNoContentWhenDeleteStudent(){

		var mockStudent = StudentUtil.mockCreate();

		when(repository.findById(anyString())).thenReturn(Mono.just(mockStudent));

		when(repository.deleteById(anyString())).thenReturn(Mono.empty());

		client.delete()
				.uri("/students/1")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNoContent();
	}
}
