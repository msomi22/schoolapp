/**
 * 
 */
package com.yahoo.petermwenda83;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.exam.Perfomance;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.servlet.upload.ExcelUtil;

/**
 * @author pmnjeru
 *
 */
public class DuplicateChecker implements Runnable {

	private static StudentDAO studentDAO;
	private static SubjectDAO subjectDAO;
	private static ExamConfigDAO examConfigDAO;
	private static AccountDAO accountDAO;
	private static PerfomanceDAO perfomanceDAO;
	private static RoomDAO roomDAO;
	final String STATUS_INACTIVE = "6C03705B-E05E-420B-B5B8-C7EE36643E60";
	final String STATUS_ACTIVE = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";

	public void run() {
		System.out.println("Sleeping for 20 secods to allow db pooling ");
		ExcelUtil.sleep(20); 
		System.out.println("Publicate checker executed");
		studentDAO = StudentDAO.getInstance();
		subjectDAO = SubjectDAO.getInstance();
		examConfigDAO = ExamConfigDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		perfomanceDAO = PerfomanceDAO.getInstance();
		roomDAO = RoomDAO.getInstance();
		removeExamDuplicates();
	}

	private void removeExamDuplicates() {
		System.out.println("... running");
		List<Subject> subjectList = new ArrayList<>();
		subjectList = subjectDAO.getAllSubjects();
		while (true) {
			System.out.println("Duplicates check is running... listing accounts");
			for (SchoolAccount account : accountDAO.getAllSchools(STATUS_ACTIVE)) {
				System.out.println("Get account username: " + account.getUsername());

				ExamConfig sysConfig = new ExamConfig();
				String term = "", year = "";
				if (examConfigDAO.getExamConfig(account.getUuid()) != null) {
					sysConfig = examConfigDAO.getExamConfig(account.getUuid());
					term = sysConfig.getTerm();
					year = sysConfig.getYear();
				}
				//ClassRoom
				
				List<ClassRoom> classrooms = roomDAO.getAllRooms(account.getUuid());
				Map<String,String> roomMap = new HashMap<>();
				for(ClassRoom classroom : classrooms) {
					roomMap.put(classroom.getUuid(), classroom.getRoomName()); 
				}
				
				if (account.getUsername().equals("allamano") || account.getUsername().equals("chuka")) {
					System.out.println("Deactivating : " + account.getUsername());
					account.setStatusUuid(STATUS_INACTIVE);
					accountDAO.update(account);
				} else {
					//ExcelUtil.sleep(1);
					List<Student> studentList = new ArrayList<>();
					studentList = studentDAO.getAllStudentList(account.getUuid(), STATUS_ACTIVE);
					if (studentList != null) {
						for (Student student : studentList) {
							System.out.println("********************************************************************\n"
                                     +"" + roomMap.get(student.getClassRoomUuid()) 
									+ "\n" + " Student : " + student.getAdmno()
									+ "\n**********************************************************************");
							
							for(Subject subject : subjectList) {
								System.out.println("Subject : " + subject.getSubjectName());								
								List<Perfomance> performanceList = perfomanceDAO.getPerformance(account.getUuid(),
										student.getClassRoomUuid(), student.getUuid(), subject.getUuid(), term, year);
								System.out.println("Performance list..." + performanceList.size());
								
								if (!performanceList.isEmpty()) {
									int size = performanceList.size();
									if (size > 1) {
										for (int i = 1; i < size; i++) {
											System.out.println(
													"###########################################################\n "
															+ "DUPLICATE FOUND -- DELETING "
															+ " student: " + student.getAdmno() + " "
														    + " subject:  " + subject.getSubjectName() 
														    + " class: "+ roomMap.get(student.getClassRoomUuid()) 
															+ "\n##################################################################");
											ExcelUtil.sleep(2);
											deleteDuplicate(performanceList.get(i));
											ExcelUtil.sleep(3);
										}
									}
								} else {
									System.out.println("No duplicates for account " + student.getAdmno() + " and " + subject.getSubjectName());
								}
								System.out.println("### Done with Subject : " + subject.getSubjectName());	
								//ExcelUtil.sleep(1);
							}
							System.out.println("******************** done for student: " + student.getAdmno() + " class " + roomMap.get(student.getClassRoomUuid())  +"\n"
									+ "******************************************");
							//ExcelUtil.sleep(1);
						}

					}
				}
			}
			
			System.out.println("Duplicates check is sleeping for 5 minutes");
			ExcelUtil.sleep(300);
		}

	}

	/**
	 * 
	 * @param perfomance
	 */
	private void deleteDuplicate(Perfomance perfomance) {
		System.out.println("%%%%%%% = " + perfomanceDAO.deleteDuplicate(perfomance));
	}
}
