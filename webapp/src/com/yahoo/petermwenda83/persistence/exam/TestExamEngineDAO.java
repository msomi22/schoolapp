/**
 * 
 */
package com.yahoo.petermwenda83.persistence.exam;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.exam.CatOne;
import com.yahoo.petermwenda83.bean.exam.CatTwo;
import com.yahoo.petermwenda83.bean.exam.Common;
import com.yahoo.petermwenda83.bean.exam.EndTerm;
import com.yahoo.petermwenda83.bean.exam.PaperOne;
import com.yahoo.petermwenda83.bean.exam.PaperThree;
import com.yahoo.petermwenda83.bean.exam.PaperTwo;

/**
 * @author peter
 *
 */
public class TestExamEngineDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD",
			     SCHOOL_UUID_NEW = "3A387B8B-A0D8-4F27-B9D2-E329619DF055";
	
	final String FORM_ONE_N = "4DA86139-6A72-4089-8858-6A3A613FDFE6";
	
	final String CLASS = "C143978A-E021-4015-BC67-5A00D6C910D1";
	
	final String STUDENT_UUID = "4F218688-6DE5-4E69-8690-66FBA2F0DC9F",
			     STUDENT_UUID_NEW="4F218688-6DE5-4E69-8690-66FBA2F0DC9F";
	
	final String SUBJECT_UUID = "D0F7EC32-EA25-7D32-8708-2CC132446",
			     SUBJECT_UUID_NEW="c9caf109-c27d-4062-9b9f-ac4268629e27";
	
	final String TAECHER_UUID = "F49DB775-4952-4915-B978-9D9F3E36D6E9",
		         TEACHER_UUID_NEW="F49DB775-4952-4915-B978-9D9F3E36D6E9";
	
	final String TERM = "1",
	             TERM_NEW="2";
	
	final String YEAR = "2014",
	             YEAR_NEW="2016";
	
	final Date SUBMIT_DATE = new Date(new Long("141941056800") );

	
	final Double SCORE = 23.2;
	
	private ExamEgineDAO store;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.ExamEgineDAO#hasCatOne(com.yahoo.petermwenda83.bean.exam.Perfomance, java.lang.Double)}.
	 */
	@Ignore
	@Test
	public void testChecker() {
		store = new ExamEgineDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Common p = new Common();
		p.setSchoolAccountUuid(SCHOOL_UUID);
		p.setClassRoomUuid(FORM_ONE_N);
		p.setStudentUuid(STUDENT_UUID);
		p.setSubjectUuid(SUBJECT_UUID); 
		assertTrue(store.Checker(SCHOOL_UUID,FORM_ONE_N,STUDENT_UUID,SUBJECT_UUID, TERM, YEAR)); 
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.ExamEgineDAO#putCatOne(com.yahoo.petermwenda83.bean.exam.Perfomance, java.lang.Double)}.
	 */
	//@Ignore
	@Test
	public void testPutCatOne() {
		store = new ExamEgineDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		
		
		CatOne catone = new CatOne();
		catone.setSchoolAccountUuid(SCHOOL_UUID);
		catone.setClassRoomUuid(FORM_ONE_N);
		catone.setClassesUuid("C143978A-E021-4015-BC67-5A00D6C910D1");
		catone.setStudentUuid(STUDENT_UUID_NEW);
		catone.setSubjectUuid(SUBJECT_UUID); 
		
		catone.setTeacherUuid(TEACHER_UUID_NEW);
		catone.setTerm("1");
		catone.setYear("2018");
		catone.setCatOne(SCORE);
		//assertTrue(store.putScore(catone));
		
		CatTwo cattwo = new CatTwo();
		cattwo.setSchoolAccountUuid(SCHOOL_UUID);
		cattwo.setClassRoomUuid(FORM_ONE_N);
		cattwo.setClassesUuid("C143978A-E021-4015-BC67-5A00D6C910D1");
		cattwo.setStudentUuid(STUDENT_UUID_NEW);
		cattwo.setSubjectUuid(SUBJECT_UUID); 
		
		cattwo.setTeacherUuid(TEACHER_UUID_NEW);
		cattwo.setTerm("1");
		cattwo.setYear("2019");
		cattwo.setCatTwo(SCORE); 
		//assertTrue(store.putScore(cattwo));
		
		EndTerm endterm = new EndTerm();
		
		endterm.setSchoolAccountUuid(SCHOOL_UUID);
		endterm.setClassRoomUuid(FORM_ONE_N);
		cattwo.setClassesUuid("C143978A-E021-4015-BC67-5A00D6C910D1");
		endterm.setStudentUuid(STUDENT_UUID_NEW);
		endterm.setSubjectUuid(SUBJECT_UUID); 
		
		endterm.setTeacherUuid(TEACHER_UUID_NEW);
		endterm.setTerm("1");
		endterm.setYear("2018");
		endterm.setEndTerm(SCORE);
		//assertTrue(store.putScore(endterm)); 
		
		
		
		
		PaperOne paper1 = new PaperOne();
		
		paper1.setSchoolAccountUuid(SCHOOL_UUID);
		paper1.setClassRoomUuid(FORM_ONE_N);
		paper1.setClassesUuid(CLASS); 
		paper1.setStudentUuid(STUDENT_UUID_NEW);
		paper1.setSubjectUuid(SUBJECT_UUID); 
		
		paper1.setTeacherUuid(TEACHER_UUID_NEW);
		paper1.setTerm(TERM_NEW);
		paper1.setYear(YEAR_NEW);
		paper1.setPaperOne(SCORE);
		//assertTrue(store.putScore(paper1)); 
		
		PaperTwo paper2 = new PaperTwo();
		paper2.setSchoolAccountUuid(SCHOOL_UUID);
		paper2.setClassRoomUuid(FORM_ONE_N);
		paper2.setClassesUuid(CLASS); 
		paper2.setStudentUuid(STUDENT_UUID_NEW);
		paper2.setSubjectUuid(SUBJECT_UUID); 
		
		paper2.setTeacherUuid(TEACHER_UUID_NEW);
		paper2.setTerm(TERM_NEW);
		paper2.setYear(YEAR_NEW);
		paper2.setPaperTwo(SCORE); 
		//assertTrue(store.putScore(paper2)); 
		
		PaperThree paper3 = new PaperThree();
		paper3.setSchoolAccountUuid(SCHOOL_UUID);
		paper3.setClassRoomUuid(FORM_ONE_N);
		paper3.setClassesUuid(CLASS); 
		paper3.setStudentUuid(STUDENT_UUID_NEW);
		paper3.setSubjectUuid(SUBJECT_UUID); 
		
		paper3.setTeacherUuid(TEACHER_UUID_NEW);
		paper3.setTerm(TERM_NEW);
		paper3.setYear(YEAR_NEW);
		paper3.setPaperThree(SCORE);
		//assertTrue(store.putScore(paper3)); 
		
		
		
	}  
	
	
	@Ignore
	@Test
	public void testGetcatoneList() {
		store = new ExamEgineDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		/*List<CatOne> list = store.getcatoneList(SCHOOL_UUID,FORM_ONE_N);
		for(CatOne p : list){
			System.out.println(p);
		}*/
	
	}
	
	@Ignore
	@Test
	public void testGetcatwoList() {
		store = new ExamEgineDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		/*List<CatTwo> list = store.getcatwoList(SCHOOL_UUID,FORM_ONE_N);
		for(CatTwo p : list){
			System.out.println(p);
		}*/
	
	}
	
	@Ignore
	@Test
	public void testGetendtermList() {
		store = new ExamEgineDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		/*List<EndTerm> list = store.getendtermList(SCHOOL_UUID,FORM_ONE_N);
		for(EndTerm p : list){
			System.out.println(p);
		}*/
	
	}
	
	@Ignore
	@Test
	public void testGetPaperOneList() {
		store = new ExamEgineDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		/*List<PaperOne> list = store.getPaperOneList(SCHOOL_UUID,FORM_ONE_N);
		for(PaperOne p : list){
			System.out.println(p);
		}*/
	
	}
	
	@Ignore
	@Test
	public void testGetPaperTwoList() {
		store = new ExamEgineDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		/*List<PaperTwo> list = store.getPaperTwoList(SCHOOL_UUID,FORM_ONE_N);
		for(PaperTwo p : list){
			System.out.println(p);
		}*/
	
	}
	
	@Ignore
	@Test
	public void testGetpaperThreeList() {
		store = new ExamEgineDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		/*List<PaperThree> list = store.getpaperThreeList(SCHOOL_UUID,FORM_ONE_N);
		for(PaperThree p : list){
			System.out.println(p);
		}*/
	
	}
	

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.exam.ExamEgineDAO#getCatOne(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public void testGetCatOne() {
		store = new ExamEgineDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}


}
