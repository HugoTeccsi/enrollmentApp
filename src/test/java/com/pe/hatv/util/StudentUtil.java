package com.pe.hatv.util;

import com.pe.hatv.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

public final class StudentUtil {

	private StudentUtil(){

	}

	public static List<Student> mockFindAll(){
		List<Student> list = new ArrayList<>();
		var student1 = new Student();
		student1.setId("1");
		student1.setNames("Hugo");
		student1.setSurnames("Teccsi Veredas");
		student1.setDni("11112222");
		student1.setAge(33);

		var student2 = new Student();
		student2.setId("2");
		student2.setNames("Alexander");
		student2.setSurnames("Teccsi Veredas");
		student2.setDni("33334444");
		student2.setAge(33);

		list.add(student1);
		list.add(student2);

		return list;
	}

	public static Student mockCreate(){
		var student = new Student();
		student.setId("1");
		student.setNames("Hugo");
		student.setSurnames("Teccsi Veredas");
		student.setDni("11112222");
		student.setAge(33);

		return student;
	}

}
