

/*************************************************************
 * Online School Management System                           *
 * Forth Year Project                                        *
 * Maasai Mara University                                    *
 * Bachelor of Science(Computer Science)                     *
 * Year:2015-2016                                            *
 * Name: Njeru Mwenda Peter                                  *
 * ADM NO : BS02/009/2012                                    *
 *                                                           *
 *************************************************************/
package com.yahoo.petermwenda83.persistence.exam;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.persistence.GenericDAO;


/**
 *  Persistence implementation for {@link SchoolExamEngineDAO}
 *  
 *  Copyright (c) FasTech Solutions Ltd., Dec 02, 2015
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ExamEgineDAO extends GenericDAO implements SchoolExamEngineDAO {
	
	private static ExamEgineDAO examEgineDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static ExamEgineDAO getInstance(){
		
		if(examEgineDAO == null){ 
			examEgineDAO = new ExamEgineDAO();		
		}
		return examEgineDAO;
	}
	
	/**
	 * 
	 */
	public ExamEgineDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public ExamEgineDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	

}
