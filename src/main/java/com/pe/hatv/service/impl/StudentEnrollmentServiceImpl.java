package com.pe.hatv.service.impl;

import com.pe.hatv.model.entity.StudentEnrollment;
import com.pe.hatv.repository.GenericRepository;
import com.pe.hatv.repository.StudentEnrollmentRepository;
import com.pe.hatv.service.StudentEnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentEnrollmentServiceImpl extends OperationCrudServiceImpl<StudentEnrollment, String> implements StudentEnrollmentService {

	private final StudentEnrollmentRepository repository;

	@Override
	protected GenericRepository<StudentEnrollment, String> getRepository() {
		return this.repository;
	}

}
