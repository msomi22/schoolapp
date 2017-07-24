
package com.yahoo.petermwenda83.persistence.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.GenericDAO;


/**
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class StudentDAO extends GenericDAO implements SchoolStudentDAO {
     
	private static StudentDAO studentDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	public static StudentDAO getInstance(){
		
		if(studentDAO == null){
			studentDAO = new StudentDAO();		
		}
		return studentDAO;
	}
	
	/**
	 * 
	 */
	public StudentDAO() {
		super();
	}
	
	/**
	 * 
	 */
	public StudentDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getStudent(java.lang.String)
	 */
	
	@Override
	public Student getStudentByuuid(String schoolaccountUuid,String Uuid) {
		Student student = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE SchoolAccountUuid =? AND Uuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, schoolaccountUuid);
        	 pstmt.setString(2, Uuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 student  = beanProcessor.toBean(rset,Student.class);
	   }
        		
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting an users with uuid: " + Uuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
       
		return student; 
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getStudentADmNo(java.lang.String)
	 */
	@Override
	public Student getStudentADmNo(String schoolaccountUuid) {
		Student student = new Student();
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT admNo FROM Student WHERE SchoolAccountUuid = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, schoolaccountUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 student  = beanProcessor.toBean(rset,Student.class);
	   }
        		
        }catch(SQLException e){
        	 logger.error("SQL Exception when student admno for school " + schoolaccountUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
        int admno = NumberUtils.toInt(student.getAdmno());
        if(admno <=0){
        	//System.out.println("admno less equal zero="+admno);
        }else{
        	//System.out.println("admno greater zero="+admno);
        }
        
		return student; 
	}


	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getStudents(java.lang.String)
	 */
	
	@Override
	public Student getStudentObjByadmNo(String schoolaccountUuid,String admno) {
		Student student = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE SchoolAccountUuid =? AND admno = ?;");       
        		
        		){
        	
        	 pstmt.setString(1, schoolaccountUuid);
        	 pstmt.setString(2, admno);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 student  = beanProcessor.toBean(rset,Student.class);
	   }
        	
        	
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting an users with admno: " + admno);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
        
		return student; 
	}
	

	
	

	
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getStudentByName(com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount, java.lang.String)
	 */
	public List<Student> getStudentByName(String accountUuid, String studentUuid) {
		List<Student> list = new ArrayList<>();

        try (
        	   Connection conn = dbutils.getConnection();
     	       PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE SchoolAccountUuid = ? AND Uuid = ?;");    		   
     	   ) {
         	   pstmt.setString(1, accountUuid);           
         	   pstmt.setString(2, studentUuid);
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Student.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Student of " + accountUuid  +
            " and student studentUuid '" + studentUuid +  "'"); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
                
        Collections.sort(list);
        return list;
	}
	

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getStudentAdmNo(com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount, java.lang.String)
	 */
	@Override
	public List<Student> getStudentByAdmNo(String schoolaccountUuid, String searchStr ) {
		List<Student> list = null;

        try (
        		 Connection conn = dbutils.getConnection();
     	       PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Student WHERE SchoolAccountUuid = ? "
     	       		+ "AND admno ILIKE ? OR firstname ILIKE ? OR lastname ILIKE ? OR surname ILIKE ? OR gender ILIKE ?"
     	       		+ " OR classRoomUuid ILIKE ?  ORDER BY classRoomUuid, admno ASC LIMIT ? OFFSET ?;");    		   
     	   ) {
         	   pstmt.setString(1, schoolaccountUuid);           
         	   pstmt.setString(2, "%" + searchStr.toUpperCase() + "%");
         	   pstmt.setString(3, "%" + searchStr.toUpperCase() + "%");
         	   pstmt.setString(4, "%" + searchStr.toUpperCase() + "%");
         	   pstmt.setString(5, "%" + searchStr.toUpperCase() + "%");
         	   pstmt.setString(6, "%" + searchStr.toUpperCase() + "%");
         	   pstmt.setString(7, "%" + searchStr.toUpperCase() + "%");
         	   pstmt.setInt(8, 15);
         	   pstmt.setInt(9, 0);
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Student.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Student of " + schoolaccountUuid  +
            " and student searchStr '" + searchStr +  "'");
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
                
        Collections.sort(list);
        return list;
	}
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#putStudents(com.yahoo.petermwenda83.bean.student.Student)
	 */
	@Override
	public boolean putStudents(Student student) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Student" 
			        		+"(Uuid,SchoolAccountUuid,StatusUuid,ClassRoomUuid,AdmNo,Firstname, Lastname,Surname,"
			        		+ "Gender,DOB,Bcertno,County,RegTerm,finalYear,FinalTerm,SysUser,StudentType,AdmissionDate)"
			        		+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		){
			   
	            pstmt.setString(1, student.getUuid());
	            pstmt.setString(2, student.getSchoolAccountUuid());
	            pstmt.setString(3, student.getStatusUuid());
	            pstmt.setString(4, student.getClassRoomUuid());
	            pstmt.setString(5, student.getAdmno());
	            pstmt.setString(6, student.getFirstname());
	            pstmt.setString(7, student.getLastname());
	            pstmt.setString(8, student.getSurname());
	            pstmt.setString(9, student.getGender());
	            pstmt.setString(10, student.getdOB());
	            pstmt.setString(11, student.getBcertno());
	            pstmt.setString(12, student.getCounty());
	            pstmt.setString(13, student.getRegTerm());
	            pstmt.setInt(14, student.getFinalYear());
	            pstmt.setInt(15, student.getFinalTerm());
	            pstmt.setString(16, student.getSysUser());
	            pstmt.setString(17, student.getStudentType());
	            pstmt.setTimestamp(18, new Timestamp(student.getAdmissionDate().getTime()));
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put Student: "+student);
             logger.error(ExceptionUtils.getStackTrace(e)); 
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		
		return success;
	}

	
	
	
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#editStudents(com.yahoo.petermwenda83.bean.student.Student)
	 */
	@Override
	public boolean updateStudents(Student student) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE Student SET ClassRoomUuid =?, Firstname =?," 
			        +"Lastname =?,Surname =?,Gender =?,DOB =?,"
			        + "Bcertno =?,County =?,SysUser=?,StatusUuid =?,Admno =?,finalYear =?,FinalTerm =?,StudentType =? WHERE Uuid = ? AND SchoolAccountUuid = ?;");
		){
			  
			    pstmt.setString(1, student.getClassRoomUuid());
	            pstmt.setString(2, student.getFirstname());
	            pstmt.setString(3, student.getLastname());
	            pstmt.setString(4, student.getSurname());
	            pstmt.setString(5, student.getGender());
	            pstmt.setString(6, student.getdOB());
	            pstmt.setString(7, student.getBcertno());
	            pstmt.setString(8, student.getCounty());
	            pstmt.setString(9, student.getSysUser());
	            pstmt.setString(10, student.getStatusUuid());
	            pstmt.setString(11, student.getAdmno());
	            pstmt.setInt(12, student.getFinalYear());
	            pstmt.setInt(13, student.getFinalTerm());
	            pstmt.setString(14, student.getStudentType());
	            pstmt.setString(15, student.getUuid());
	            pstmt.setString(16, student.getSchoolAccountUuid());
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
			 logger.error("SQL Exception trying to put Student: "+student);
             logger.error(ExceptionUtils.getStackTrace(e));  
             System.out.println(ExceptionUtils.getStackTrace(e));
             success = false;
		 }
		 
		
		
		return success;
	}
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#deleteStudents(com.yahoo.petermwenda83.bean.student.Student)
	 */
	@Override
	public boolean deleteStudents(Student student) {
		 boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Student"
	         	     + " WHERE Admno = ? AND SchoolAccountUuid =?; ");       
	      		
	      		){
	      	
	      	 pstmt.setString(1, student.getAdmno());
	      	 pstmt.setString(2, student.getSchoolAccountUuid());
		     pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting student : " +student);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}
	
	/** Get all students in a particular classroom
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getAllStudents(java.lang.String, java.lang.String)
	 * @return List of students per class
	 */
	public List<Student> getAllStudents(String schoolaccountUuid,String classRoomUuid) {
	List<Student> list = null;

	 try(   
  		Connection conn = dbutils.getConnection();
  		PreparedStatement  pstmt = conn.prepareStatement("SELECT * FROM Student WHERE SchoolAccountUuid = ? AND classRoomUuid =? ORDER BY Admno ASC;");   
		) {
		 pstmt.setString(1,schoolaccountUuid);
		 pstmt.setString(2,classRoomUuid);
		 
		 try(ResultSet rset = pstmt.executeQuery();){
				
			 list = beanProcessor.toBeanList(rset, Student.class);
			}
        

  } catch(SQLException e){
   	 logger.error("SQL Exception when getting all Student");
     logger.error(ExceptionUtils.getStackTrace(e));
     System.out.println(ExceptionUtils.getStackTrace(e)); 
  }

	
	return list;
	}

	
	/**
	 * @param schoolaccount
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public List<Student> getStudentList (SchoolAccount schoolaccount , int startIndex , int endIndex){
		List<Student> studentList = new ArrayList<>();
		
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Student WHERE "
						+ "SchoolAccountUuid = ? ORDER BY classRoomUuid, admno DESC LIMIT ? OFFSET ? ;");
				) {
			psmt.setString(1, schoolaccount.getUuid());
			psmt.setInt(2, endIndex - startIndex);
			psmt.setInt(3, startIndex);
			
			try(ResultSet rset = psmt.executeQuery();){
			
				studentList = beanProcessor.toBeanList(rset, Student.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Student List with an index and offset.");
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return studentList;		
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getAllStudentList(java.lang.String)
	 */
	public List<Student> getAllStudentList(String schoolaccountUuid) {
     List<Student> studentList = new ArrayList<>();
		try(
				Connection conn = dbutils.getConnection();
				PreparedStatement psmt= conn.prepareStatement("SELECT * FROM Student WHERE "
						+ "SchoolAccountUuid = ?  ORDER BY Admno ASC;");
				) {
			psmt.setString(1, schoolaccountUuid);
			try(ResultSet rset = psmt.executeQuery();){
			
				studentList = beanProcessor.toBeanList(rset, Student.class);
			}
		} catch (SQLException e) {
			logger.error("SQLException when trying to get a Student List for school"+schoolaccountUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e)); 
	    }
		
		return studentList;
	}
	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getStudentCount(java.lang.String, java.lang.String)
	 */
	@Override
	public int getStudentCount(String statusuuid,String schoolaccountUuid) {
		int count = 0;
		ResultSet rset = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Student WHERE statusuuid =? AND schoolaccountUuid =?;");    		   
 	    ) {
        	pstmt.setString(1, statusuuid);
        	pstmt.setString(2, schoolaccountUuid);
        	rset = pstmt.executeQuery();
        	
        	while(rset.next()){
   	       		count = rset.getInt("count");
          	  }
        } catch (SQLException e) {
            logger.error("SQLException Student count for schoolaccountUuid " + schoolaccountUuid);
            logger.error(ExceptionUtils.getStackTrace(e));
        }
		
		return count;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.student.SchoolStudentDAO#getStudentCount(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int getStudentCount(String statusuuid, String studenttype, String schoolaccountUuid) {
		int count = 0;
		ResultSet rset = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM Student WHERE statusuuid =? AND studenttype =? AND schoolaccountUuid =?;");    		   
 	    ) {
        	pstmt.setString(1, statusuuid);
        	pstmt.setString(2, studenttype);
        	pstmt.setString(3, schoolaccountUuid);
        	rset = pstmt.executeQuery();
        	
        	while(rset.next()){
   	       		count = rset.getInt("count");
          	  }
        } catch (SQLException e) {
            logger.error("SQLException Student count for studenttype " + studenttype);
            logger.error(ExceptionUtils.getStackTrace(e));
        }
		
		return count;
	}




}
