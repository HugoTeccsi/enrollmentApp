package com.pe.hatv.service.impl;

import com.pe.hatv.model.entity.Student;
import com.pe.hatv.repository.GenericRepository;
import com.pe.hatv.repository.StudentRepository;
import com.pe.hatv.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends OperationCrudServiceImpl<Student, String> implements StudentService {

	private final StudentRepository studentRepository;

	@Override
	protected GenericRepository<Student, String> getRepository() {
		return studentRepository;
	}

	@Override
	public Flux<Student> findAllAndSortByAge(Optional<String> typeOrder) {

		var listStudent = studentRepository.findAll();

		if (typeOrder.isEmpty()){
			return listStudent;
		}
		return Flux.fromIterable(List.of(listStudent)).flatMap(st->
			st.sort(resolverSort(typeOrder.get()))
		);
	}

	private Comparator<Student> resolverSort(String typeOrder){
		if ("asc".equals(typeOrder)){
			return Comparator.comparing(Student::getAge);
		}
		if ("desc".equals(typeOrder)){
			return Comparator.comparing(Student::getAge).reversed();
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
	}

}
