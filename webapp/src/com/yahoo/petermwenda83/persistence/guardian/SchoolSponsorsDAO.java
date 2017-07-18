/*************************************************************
 * ##########################################################
 * ##########################################################
 * ### This is My Forth Year Project#########################
 * ####### Maasai Mara University############################
 * ####### Year:2015-2016 ###################################
 * ####### Although this software is open source, No one
 * ###### should assume it ownership and copy paste 
 * ###### the code herein without the owner's approval.
 * ###################################################
 * ##########################################################
 * ##### SchoolAccount Management System ###########################
 * ##### Uses MVC Model, Postgres database, ant for 
 * ##### project management and other technologies.
 * ##### It consist Desktop application and a web
 * #### application all sharing the same DB.
 * ##########################################################
 * 
 */
package com.yahoo.petermwenda83.persistence.guardian;

import java.util.List;

import com.yahoo.petermwenda83.bean.student.guardian.StudentSponsor;

/**
 * @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolSponsorsDAO {
	

	 /**
	  * 
	  * @param uuid
	  * @return StudentSponsor
	  */
	public StudentSponsor getSponsor(String studentUuid);
	
	 
	
	/**
	  * 
	  * @param studentSuper
	  * @param sponser
	  *
	  */
	public boolean putSponsor(StudentSponsor sponser);
	
	/**
	  * 
	  * @param studentSuper
	  * @param sponser
	  * 
	  */
	public boolean updateSponsor(StudentSponsor sponsor);
	
	
	 /**
	  * 
	  * @param studentSuper
	  * @param sponser
	  * 
	  */
	public boolean deleteSponsor(StudentSponsor sponser);
	
	
	 /**
	  * 
	  * @return AllStudentSponser
	  */
	public List<StudentSponsor> getStudentSponsorList();
	/**
	 * 
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public List<StudentSponsor> getStudentSponsorList(int startIndex , int endIndex);
	

}
