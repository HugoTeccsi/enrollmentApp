package com.pe.hatv.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document("student_enrollment")
public class StudentEnrollment {

	@EqualsAndHashCode.Include
	@Id
	private String id;

	@NotNull
	private LocalDateTime dateTime;

	@NotNull
	private Student student;

	@NotNull
	private List<Course> courses;

	private Boolean status;
}
