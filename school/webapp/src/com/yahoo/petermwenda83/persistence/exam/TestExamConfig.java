package com.yahoo.petermwenda83.persistence.exam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.exam.ExamConfig;

public class TestExamConfig {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String SCHOOL_UUID = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";
	
	final String UUID = "38E63D79-8A60-42CF-9D35-8BB55E1F1F3A",
			     UUID_NEW = "487AF368-D5DA-4DA7-823A-679C94AA433A";
	
	final String TERM = "1",
			     TERM_NEW = "new",
			     TERM_UPDATE = "update";
	
	final String YEAR = "2016",
			     YEAR_NEW = "new",
			     YEAR_UPDATE = "update";
	
	final String EXAM = "C1",
			     EXAM_NEW = "new",
			     EXAM_UPDATE = "update";
	
	final String EXAM_MODE = "ON",
		     EXAM_MODE_NEW = "new",
		     EXAM_MODE_UPDATE = "update";

	private ExamConfigDAO store;
    
	@Ignore
	@Test
	public void testGetExamConfig() {
		store = new ExamConfigDAO(databaseName, Host, databaseUsername, databasePassword, databasePort); 
		ExamConfig e = new ExamConfig();
		e = store.getExamConfig(SCHOOL_UUID);
		assertEquals(e.getUuid(),UUID);
		assertEquals(e.getSchoolAccountUuid(),SCHOOL_UUID);
		assertEquals(e.getTerm(),TERM);
		assertEquals(e.getYear(),YEAR);
		assertEquals(e.getExam(),EXAM);
		assertEquals(e.getExamMode(),EXAM_MODE);
	}
	@Ignore
	@Test
	public void testPutExamConfig() {
		store = new ExamConfigDAO(databaseName, Host, databaseUsername, databasePassword, databasePort); 
		ExamConfig e = new ExamConfig();
		e.setUuid(UUID_NEW);
		e.setSchoolAccountUuid(SCHOOL_UUID);
		e.setYear(YEAR_NEW);
		e.setTerm(TERM_NEW);
		e.setExam(EXAM_NEW); 
		e.setExamMode(EXAM_MODE_NEW); 
		assertTrue(store.putExamConfig(e));
	}
	@Ignore
	@Test
	public void testUpdateExamConfig() {
		store = new ExamConfigDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		ExamConfig e = new ExamConfig();
		e.setUuid(UUID_NEW);
		e.setSchoolAccountUuid(SCHOOL_UUID);
		e.setYear(YEAR_UPDATE);
		e.setTerm(TERM_UPDATE);
		e.setExam(EXAM_NEW); 
		e.setExamMode(EXAM_MODE_UPDATE); 
		assertTrue(store.updateExamConfig(e)); 
	}
	//@Ignore
	@Test
	public void testGetExamConfigList() {
		store = new ExamConfigDAO(databaseName, Host, databaseUsername, databasePassword, databasePort); 
		List<ExamConfig> list = store.getExamConfigList(SCHOOL_UUID);
		for (ExamConfig e : list) {
			System.out.println(e);
		}
	}

}
