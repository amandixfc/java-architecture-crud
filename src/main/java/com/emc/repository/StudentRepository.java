package com.emc.repository;

import static com.emc.util.FileUtil.createFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.emc.model.Student;
import com.emc.util.FileUtil;
import com.mila.exceptions.StudentNotFoundExcepction;

public class StudentRepository {

	static final Logger logger = Logger.getLogger(StudentRepository.class);
	static Properties prop = null;
	static InputStream input = null;

	static {
		prop = new Properties();
		try {
			// https://howtoprogram.xyz/2017/01/17/read-file-and-resource-in-junit-test/
			// Lee el fichero de properties de src/main/resources
			// Si el programa se ejecuta desde el main
			// Lee el fichero de properties de src/test/resources
			// Si el programa se ejecuta desde el test
			input = StudentRepository.class
					.getResourceAsStream("/config.properties");
			prop.load(input);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new ExceptionInInitializerError(e); // wrapping exceptions
		}
	}

	public Student add(Student student) throws 
		IOException, StudentNotFoundExcepction {

		String fileName = prop.getProperty("filename");

		createFile(fileName);

		try (FileWriter fileWriter = new FileWriter(fileName, true);
				BufferedWriter bufferWriter = new BufferedWriter(fileWriter)) {
			bufferWriter.write(student.toString());
			bufferWriter.write(System.lineSeparator());
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return getLastStudentByID(student.getIdStudent());

	}

	private Student getLastStudentByID(int id) throws 
		IOException, StudentNotFoundExcepction {

		BufferedReader buffredReader = null;
		Student student = null;

		try {
			buffredReader = new BufferedReader(
					new FileReader(prop.getProperty("filename")));
			String linea;
			boolean found = false;

			while ((linea = buffredReader.readLine()) != null) {

				String[] datos = linea.split(",", 4);
				if (datos[0].equals(String.valueOf(id))) {
					found = true;
					student = new Student();
					student.setIdStudent(id);
					student.setName(datos[1]);
					student.setSurname(datos[2]);
					student.setAge(Integer.parseInt(datos[3]));
					break;
				} 
			}
			
			if (!found) {
				throw new StudentNotFoundExcepction("Student not found");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		} finally {

			if (buffredReader != null)
				buffredReader.close();
		}

		return student;
	}
	
	public Student update(Student student) throws 
		IOException, StudentNotFoundExcepction {
		
		List<Student> listStudents = new ArrayList<Student>();
		int index;
		boolean trobat;
		
		try {
			
			listStudents = getAll();
			index = 0;
			trobat = false; 
			
			while (index < listStudents.size() && !trobat) {
				
				if (listStudents.get(index).getIdStudent() == student.getIdStudent()) trobat = true;
				else index++;
			}
			
			if(trobat) {
				
				listStudents.get(index).setName(student.getName());
				listStudents.get(index).setSurname(student.getSurname());
				listStudents.get(index).setAge(student.getAge());
				
				FileUtil.deleteFile();
				
				String fileName = prop.getProperty("filename");

				createFile(fileName);

				try (FileWriter fileWriter = new FileWriter(fileName, true);
						BufferedWriter bufferWriter = new BufferedWriter(fileWriter)) {
					
					listStudents.forEach(s -> {
						try {
							bufferWriter.write(s.toString());
							bufferWriter.write(System.lineSeparator());
						} catch (IOException e) {
							logger.error(e.getMessage());
							//e.printStackTrace();
						}
					});

				} catch (IOException e) {
					logger.error(e.getMessage());
					throw e;
				}

				return getLastStudentByID(student.getIdStudent());
				
			} else {
				throw new StudentNotFoundExcepction("Student not found");
			}
			
			
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		}
		
		//throw new UnsupportedOperationException("The method is not yet implemented");
	}
	
	public boolean delete(int studentId) throws 
		IOException, StudentNotFoundExcepction {
		
		List<Student> listStudents = new ArrayList<Student>();
		int index;
		boolean trobat;
		
		try {
			
			listStudents = getAll();
			index = 0;
			trobat = false;
			
			while (index < listStudents.size() && !trobat) {
				
				if (listStudents.get(index).getIdStudent() == studentId) trobat = true;
				else index++;
			}
			
			if(trobat) {
				
				listStudents.remove(index);
				
				FileUtil.deleteFile();
				
				String fileName = prop.getProperty("filename");

				createFile(fileName);

				try (FileWriter fileWriter = new FileWriter(fileName, true);
						BufferedWriter bufferWriter = new BufferedWriter(fileWriter)) {
					
					listStudents.forEach(s -> {
						try {
							bufferWriter.write(s.toString());
							bufferWriter.write(System.lineSeparator());
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
					});

				} catch (IOException e) {
					logger.error(e.getMessage());
					throw e;
				}
				
			} else {
				return false;
				//throw new StudentNotFoundExcepction("Student not found");
			}
			
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		}
		
		return trobat;
		// throw new UnsupportedOperationException("The method is not yet implemented");
	}
	
	public List<Student> getAll() throws IOException {
		
		List<Student> studentsAll= new ArrayList<Student>();
		
		BufferedReader buffredReader = null;
		Student student = null;
		
		try {
			
			buffredReader = new BufferedReader(
					new FileReader(prop.getProperty("filename")));
			String linea;
			
			student = null;

			while ((linea = buffredReader.readLine()) != null) {

				String[] datos = linea.split(",", 4);
				student = new Student(Integer.parseInt(datos[0]), datos[1], datos[2],Integer.parseInt(datos[3]));
				studentsAll.add(student);
			}
			
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		} finally {

			if (buffredReader != null)
				buffredReader.close();
		} 
		
		return studentsAll;
		//throw new UnsupportedOperationException("The method is not yet implemented");
	}
	
}
