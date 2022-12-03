package com.pe.hatv.service;

import com.pe.hatv.model.entity.Student;
import reactor.core.publisher.Flux;

import java.util.Optional;

public interface StudentService extends OperationCrudService<Student, String> {

	Flux<Student> findAllAndSortByAge(Optional<String> typeOrder);

}
