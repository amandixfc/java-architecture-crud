package com.emc.repository;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.emc.model.Student;
import com.emc.util.FileUtil;
import com.mila.exceptions.StudentNotFoundExcepction;

public class StudentRepositoryTest {
	
	@After
	public void afterEachTestMethod() {
		FileUtil.deleteFile();
	}
	
	@Test
	public void testAdd() throws 
		IOException, StudentNotFoundExcepction {
		
		StudentRepository studentRepository = new StudentRepository();
		
		Student student = new Student();
		student.setIdStudent(1);
		student.setName("Pepet");
		student.setSurname("Soto");
		student.setAge(44);
		
		Student expectedStudent = studentRepository.add(student);
		
		assertTrue(student.equals(expectedStudent));
		
	}
	
	@Test
	public void testUpdate() throws 
		IOException, StudentNotFoundExcepction {
		
		StudentRepository studentRepository = new StudentRepository();
		
		Student s1 = new Student(1,"Pepet","Soto",44);
		Student s2 = new Student(2,"Maria","Lopez",33);
		Student s3 = new Student(3,"Ricard","Superlopez",39);
		
		studentRepository.add(s1);
		studentRepository.add(s2);
		studentRepository.add(s3);
		
		s2.setName("Marieta");
		
		Student expectedStudent = studentRepository.update(s2);
		
		assertTrue(s2.equals(expectedStudent));
		assertTrue(s2.getName().equals(expectedStudent.getName()));
	}
	
	@Test
	public void testDelete() throws 
		IOException, StudentNotFoundExcepction { 
		
		StudentRepository studentRepository = new StudentRepository();
		boolean expectedResult;
		
		Student s1 = new Student(1,"Pepet","Soto",44);
		Student s2 = new Student(2,"Maria","Lopez",33);
		Student s3 = new Student(3,"Ricard","Superlopez",39);
		
		studentRepository.add(s1);
		studentRepository.add(s2);
		studentRepository.add(s3);
		
		assertTrue(studentRepository.getAll().size() == 3);
		expectedResult = studentRepository.delete(2);
		assertTrue(expectedResult == true);
		assertTrue(studentRepository.getAll().size() == 2);
		
		assertTrue(studentRepository.delete(5) == false);

	}
	
	@Test
	public void testGetAll() throws 
		IOException, StudentNotFoundExcepction { 
		
		StudentRepository studentRepository = new StudentRepository();
		List<Student> expectedStudents = new ArrayList<Student>();
		List<Student> listStudents = new ArrayList<Student>();
		
		Student s1 = new Student(1,"Pepet","Soto",44);
		Student s2 = new Student(2,"Maria","Lopez",33);
		Student s3 = new Student(3,"Ricard","Superlopez",39);
		
		listStudents.add(s1);
		listStudents.add(s2);
		listStudents.add(s3);
		
		studentRepository.add(s1);
		studentRepository.add(s2);
		studentRepository.add(s3);
		expectedStudents = studentRepository.getAll();
		
		assertTrue(listStudents.equals(expectedStudents));
		assertTrue(expectedStudents.size() == 3);
		assertTrue(expectedStudents.get(2).getName().equals("Ricard"));
	}
	
}
