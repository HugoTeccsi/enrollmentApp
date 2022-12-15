package com.pe.hatv.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Document(collection = "students")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {

	@EqualsAndHashCode.Include
	@Id
	private String id;

	@NotNull
	private String names;

	@NotNull
	private String surnames;

	@NotNull
	private String dni;

	@NotNull
	private Integer age;

}
