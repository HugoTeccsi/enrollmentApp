package com.pe.hatv.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Document(collection = "students")
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
	private int age;

}
