package com.pe.hatv.service.impl;

import com.pe.hatv.model.entity.Course;
import com.pe.hatv.repository.CourseRepository;
import com.pe.hatv.repository.GenericRepository;
import com.pe.hatv.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends OperationCrudServiceImpl<Course, String> implements CourseService {

	private final CourseRepository courseRepository;
	@Override
	protected GenericRepository<Course, String> getRepository() {
		return courseRepository;
	}
}
