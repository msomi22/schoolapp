/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.classroom.Classes;
import com.yahoo.petermwenda83.bean.exam.CatOne;
import com.yahoo.petermwenda83.bean.exam.CatTwo;
import com.yahoo.petermwenda83.bean.exam.EndTerm;
import com.yahoo.petermwenda83.bean.exam.Exam;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.exam.PaperOne;
import com.yahoo.petermwenda83.bean.exam.PaperThree;
import com.yahoo.petermwenda83.bean.exam.PaperTwo;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.classroom.ClassesDAO;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamEgineDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/** 
 * @author peter
 *
 */
public class SaveScore extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 886356988312421205L;
	private Cache schoolaccountCache;
	private static ExamEgineDAO examEgineDAO;
	private static StudentDAO studentDAO;
	private static ExamConfigDAO examConfigDAO;
	private static RoomDAO roomDAO;
	private static ClassesDAO classesDAO;
	private static ExamDAO examDAO;
	
	   //languages
		final String ENG_UUID = "D0F7EC32-EA25-7D32-8708-2CC132446";
		final String KISWA_UUID = "66027e51-b1ad-4b10-8250-63af64d23323";
		//sciences
		final String MATH_UUID = "4f59580d-1a16-4669-9ed5-4b89615d6903";
		final String PHY_UUID = "44f23b3c-e066-4b45-931c-0e8073d3a93a";
		final String BIO_UUID = "de0c86be-9bcb-4d3b-8098-b06687536c1f";
		final String CHEM_UUID = "552c0a24-6038-440f-add5-2dadfb9a23bd";
		//techinicals
		final String BS_UUID = "e1729cc2-524a-4069-b4a4-be5aec8473fe";
		final String COMP_UUID = "F1972BF2-C788-4F41-94FE-FBA1869C92BC";
		final String H_S = "C1F28FF4-1A18-4552-822A-7A4767643643";
		final String AGR_UUID = "b9bbd718-b32f-4466-ab34-42f544ff900e";
		//humanities 
		final String GEO_UUID = "0e5dc1c6-f62f-4a36-a1ec-064173332694";
		final String CRE_UUID = "f098e943-26fd-4dc0-b6a0-2d02477004a4";
		final String HIST_UUID = "c9caf109-c27d-4062-9b9f-ac4268629e27";


	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		CacheManager mgr = CacheManager.getInstance();
		schoolaccountCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
		examEgineDAO = ExamEgineDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		examConfigDAO = ExamConfigDAO.getInstance();
		roomDAO = RoomDAO.getInstance(); 
		classesDAO = ClassesDAO.getInstance();
		examDAO = ExamDAO.getInstance();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		HttpSession session = request.getSession(false);

		SchoolAccount school = new SchoolAccount();
		Exam exam = new Exam();
		int outOf = 0;

		String schoolusername = "";
		String stffID = "";
		
		if(session !=null){
			schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
			stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);

		}


		net.sf.ehcache.Element element;

		element = schoolaccountCache.get(schoolusername);
		if(element !=null){
			school = (SchoolAccount) element.getObjectValue();
		}
		if(school !=null){
			
			

			//String object = request.getParameter("object");
			
			String score = "";
			String admNo = "";
			String classid = "";
			String subjectid = "";

		
			score = StringUtils.trimToEmpty(request.getParameter("score"));
			admNo = StringUtils.trimToEmpty(request.getParameter("admNumber"));
			classid = StringUtils.trimToEmpty(request.getParameter("classuuid"));
			subjectid = StringUtils.trimToEmpty(request.getParameter("subjectuuid"));
			
			if(!StringUtils.isBlank(score) && !StringUtils.isBlank(admNo) && !StringUtils.isBlank(classid) && !StringUtils.isBlank(subjectid)){
				
				String classroom = "";
				if(roomDAO.getroom(school.getUuid(), classid) !=null){
					ClassRoom classRoom = roomDAO.getroom(school.getUuid(), classid);
					classroom = classRoom.getRoomName();
				}

				List<Classes> classesList = new ArrayList<>();
				classesList = classesDAO.getClassList();
				String classesUuid = "";
				for(Classes clss : classesList){
					if(StringUtils.contains(classroom, clss.getClassName())){
						classesUuid = clss.getUuid();
					}
				}

				String studentUuid = "";
				if(studentDAO.getStudentObjByadmNo(school.getUuid(),admNo) !=null){
					Student student = studentDAO.getStudentObjByadmNo(school.getUuid(), admNo);
					studentUuid = student.getUuid();
				}

				String term = "",year = "" , examtype = "";
				if(examConfigDAO.getExamConfig(school.getUuid()) !=null){
					ExamConfig examConfig = examConfigDAO.getExamConfig(school.getUuid());
					term = examConfig.getTerm();
					year = examConfig.getYear();
					examtype = examConfig.getExam();
					exam = examDAO.getExamByName(examConfig.getExam()); 
					if(exam !=null){
					   outOf = exam.getOutOf();
					    }
				}

				if(!isNumeric(score) ){
					score = "0";
				}else{
					if(Integer.parseInt(StringUtils.trimToEmpty(score)) < 0){
						score = "0";
					}else{


						//save to database

						double scoreint = Double.parseDouble(StringUtils.trimToEmpty(score));
						
						if(StringUtils.equalsIgnoreCase(examtype, "C1")){

							if(scoreint > outOf){
								scoreint = 0;
							}

							CatOne catOne = new CatOne();
							catOne.setSchoolAccountUuid(school.getUuid());
							catOne.setTeacherUuid(stffID);
							catOne.setClassRoomUuid(classid); 
							catOne.setClassesUuid(classesUuid);
							catOne.setSubjectUuid(subjectid);
							catOne.setStudentUuid(studentUuid);
							catOne.setCatOne((scoreint/outOf)*30);  // convert to 30
							catOne.setTerm(term);
							catOne.setYear(year);
							examEgineDAO.putScore(catOne,school.getUuid(),classid,studentUuid,subjectid,term,year);	
	                       // System.out.println("score = "+catOne.getCatOne()); 
						}else if(StringUtils.equalsIgnoreCase(examtype, "C2")){

							if(scoreint > outOf){
								scoreint = 0;
							}

							CatTwo catwo = new CatTwo();
							catwo.setSchoolAccountUuid(school.getUuid());
							catwo.setTeacherUuid(stffID);
							catwo.setClassRoomUuid(classid); 
							catwo.setClassesUuid(classesUuid);
							catwo.setSubjectUuid(subjectid);
							catwo.setStudentUuid(studentUuid);
							catwo.setCatTwo((scoreint/outOf)*30); // convert to 30
							catwo.setTerm(term);
							catwo.setYear(year); 
							examEgineDAO.putScore(catwo,school.getUuid(),classid,studentUuid,subjectid,term,year);

						}else if(StringUtils.equalsIgnoreCase(examtype, "ET")){

							if(scoreint > outOf){
								scoreint = 0;
							}

							EndTerm endterm = new EndTerm();
							endterm.setSchoolAccountUuid(school.getUuid());
							endterm.setTeacherUuid(stffID);
							endterm.setClassRoomUuid(classid); 
							endterm.setClassesUuid(classesUuid);
							endterm.setSubjectUuid(subjectid);
							endterm.setStudentUuid(studentUuid);
							endterm.setEndTerm((scoreint/outOf)*70); //convert to 70
							endterm.setTerm(term);
							endterm.setYear(year); 
							examEgineDAO.putScore(endterm,school.getUuid(),classid,studentUuid,subjectid,term,year);

						}else if(StringUtils.equalsIgnoreCase(examtype, "P1")){

							
							  if(StringUtils.equals(subjectid, ENG_UUID)){
								  if(scoreint > 60){
										scoreint = 0;
								  }
							  }else if(StringUtils.equals(subjectid, KISWA_UUID)){
								  if(scoreint > 60){
										scoreint = 0;
								  }
							  }else if(StringUtils.equals(subjectid, MATH_UUID)){
								    if(scoreint >100){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, PHY_UUID)){
								   if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, BIO_UUID)){
								   if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, CHEM_UUID)){
								  if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, BS_UUID)){
								  if(scoreint >100){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, COMP_UUID)){
								  if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, H_S)){
								  if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, AGR_UUID)){
								  if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, GEO_UUID)){
								  if(scoreint >100){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, CRE_UUID)){
								  if(scoreint >100){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, HIST_UUID)){
								  if(scoreint >100){
										scoreint = 0;
									}
							  }
							
							

							PaperOne p1 = new PaperOne();
							p1.setSchoolAccountUuid(school.getUuid());
							p1.setTeacherUuid(stffID);
							p1.setClassRoomUuid(classid); 
							p1.setClassesUuid(classesUuid);
							p1.setSubjectUuid(subjectid);
							p1.setStudentUuid(studentUuid);
							p1.setPaperOne(scoreint); 
							p1.setTerm(term);
							p1.setYear(year); 
							examEgineDAO.putScore(p1,school.getUuid(),classid,studentUuid,subjectid,term,year);

						}else if(StringUtils.equalsIgnoreCase(examtype, "P2")){

							
							  if(StringUtils.equals(subjectid, ENG_UUID)){
								  if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, KISWA_UUID)){
								  if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, MATH_UUID)){
								  if(scoreint >100){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, PHY_UUID)){
								  if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, BIO_UUID)){
								  if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, CHEM_UUID)){
								  if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, BS_UUID)){
								  if(scoreint >100){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, COMP_UUID)){
								  if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, H_S)){
								  if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, AGR_UUID)){
								  if(scoreint >80){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, GEO_UUID)){
								  if(scoreint >100){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, CRE_UUID)){
								  if(scoreint >100){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, HIST_UUID)){
								  if(scoreint >100){
										scoreint = 0;
									}
							  }

							PaperTwo p2 = new PaperTwo();
							p2.setSchoolAccountUuid(school.getUuid());
							p2.setTeacherUuid(stffID);
							p2.setClassRoomUuid(classid); 
							p2.setClassesUuid(classesUuid);
							p2.setSubjectUuid(subjectid);
							p2.setStudentUuid(studentUuid);
							p2.setPaperTwo(scoreint); 
							p2.setTerm(term);
							p2.setYear(year); 
							examEgineDAO.putScore(p2,school.getUuid(),classid,studentUuid,subjectid,term,year);

						}else if(StringUtils.equalsIgnoreCase(examtype, "P3")){

							
							  if(StringUtils.equals(subjectid, ENG_UUID)){
								  if(scoreint > 60){
										scoreint = 0;
									}
									
							  }else if(StringUtils.equals(subjectid, KISWA_UUID)){
								  if(scoreint > 60){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, MATH_UUID)){
								   if(scoreint > 0){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, PHY_UUID)){
								  if(scoreint > 40){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, BIO_UUID)){
								  if(scoreint > 40){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, CHEM_UUID)){
								  if(scoreint > 40){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, BS_UUID)){

							  }else if(StringUtils.equals(subjectid, COMP_UUID)){
								  if(scoreint > 40){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, H_S)){
								  if(scoreint > 40){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, AGR_UUID)){
								  if(scoreint > 40){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, GEO_UUID)){
								  if(scoreint > 0){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, CRE_UUID)){
								  if(scoreint > 0){
										scoreint = 0;
									}
							  }else if(StringUtils.equals(subjectid, HIST_UUID)){
								  if(scoreint > 0){
										scoreint = 0;
									}
							  }

							PaperThree p3 = new PaperThree();
							p3.setSchoolAccountUuid(school.getUuid());
							p3.setTeacherUuid(stffID);
							p3.setClassRoomUuid(classid); 
							p3.setClassesUuid(classesUuid);
							p3.setSubjectUuid(subjectid);
							p3.setStudentUuid(studentUuid);
							p3.setPaperThree(scoreint); 
							p3.setTerm(term);
							p3.setYear(year); 
							examEgineDAO.putScore(p3,school.getUuid(),classid,studentUuid,subjectid,term,year);
						}

						//saved

					}
				}
			}
			
			

		}// end if school !=null
		
		   return;
          
	}


	/**
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {  
		try  
		{  
			double d = Double.parseDouble(str);  

		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
