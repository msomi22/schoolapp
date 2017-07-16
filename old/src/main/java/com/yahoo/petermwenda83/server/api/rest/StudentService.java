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
			apiStudent.setUuid(student.getUuid());
			apiStudent.setAccountId(student.getAccountId());
			apiStudent.setCurrentStream(student.getCurrentStream());
			apiStudent.setRegStream(student.getRegStream());
			apiStudent.setIsActive(student.getIsActive());
			apiStudent.setIsAlumni(student.getIsAlumni());
			apiStudent.setIsBoarding(student.getIsBoarding());
			apiStudent.setRegNo(student.getRegNo());
			apiStudent.setFirstname(student.getFirstname());
			apiStudent.setMiddlename(student.getMiddlename());
			apiStudent.setLastname(student.getLastname());
			apiStudent.setGender(student.getGender());
			apiStudent.setCounty(student.getCounty());
			apiStudent.setBcertNo(student.getBcertNo());
			apiStudent.setDob(student.getDob());
			apiStudent.setRegTerm(student.getRegTerm());
			apiStudent.setPassport(student.getPassport());
			apiStudent.setLastUpdated(student.getLastUpdated()); 
			apiStudent.setFinalTerm(student.getFinalTerm());
			apiStudent.setFinalYear(student.getFinalYear());
			apiStudent.setAdmissionDate(student.getAdmissionDate());
			
			streamStudents.add(apiStudent); 
			
		});
		
		return streamStudents; 
	}

}
