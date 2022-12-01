package com.pe.hatv.util;

import com.pe.hatv.model.entity.Course;

import java.util.ArrayList;
import java.util.List;

public final class CourseUtil {

	private CourseUtil() {

	}

	public static List<Course> mockListCourse() {
		List<Course> list = new ArrayList<>();
		var course1 = new Course();
		course1.setId("1");
		course1.setName("Spring WebFlux");
		course1.setAcronym("SWF");
		course1.setStatus(true);

		var course2 = new Course();
		course2.setId("2");
		course2.setName("Spring MVC");
		course2.setAcronym("SMVC");
		course2.setStatus(true);

		list.add(course1);
		list.add(course2);

		return list;
	}

	public static Course mockCourse() {
		var course1 = new Course();
		course1.setId("1");
		course1.setName("Spring WebFlux");
		course1.setAcronym("SWF");
		course1.setStatus(true);

		return course1;
	}
}
