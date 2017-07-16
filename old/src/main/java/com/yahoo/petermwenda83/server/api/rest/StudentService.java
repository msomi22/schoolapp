/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.util.ArrayList;
import java.util.List;

import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;

/**
 * @author peter
 *
 */
public class StudentService {

	private static StudentDAO studentDAO;
	

	static{
		studentDAO = StudentDAO.getInstance();
	}
	
	/**
	 * @param sreamId
	 * @return
	 */
	public List<APIStudent> getStudentPerStream(String sreamId) { 
		String accountId = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
		List<Student> students = studentDAO.getStudentByStream(accountId, sreamId);
		List<APIStudent> streamStudents = new ArrayList<>();
		students.forEach(student -> {
			APIStudent apiStudent = new APIStudent();
			streamStudents.add(apiStudent); 
		});
		System.out.println("*******************************************************************************************************************");
		System.out.println(streamStudents.size());
		System.out.println("*******************************************************************************************************************");
		return streamStudents; 
	}

}
