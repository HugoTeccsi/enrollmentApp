package com.pe.hatv.service.impl;

import com.pe.hatv.model.entity.Student;
import com.pe.hatv.repository.GenericRepository;
import com.pe.hatv.repository.StudentRepository;
import com.pe.hatv.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends OperationCrudServiceImpl<Student, String> implements StudentService {

	private final StudentRepository studentRepository;

	@Override
	protected GenericRepository<Student, String> getRepository() {
		return studentRepository;
	}

}
