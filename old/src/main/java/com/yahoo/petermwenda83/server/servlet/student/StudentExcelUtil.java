/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.student;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.bean.student.StudentSubject;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;


/**
 * @author peter
 *
 */
public class StudentExcelUtil {
	
	private String[] genderArray;
	private List<String> genderList;
	private String[] categoryArray;
	private List<String> categoryList;
	final String STATUS_ACTIVE = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
	
	/**
	 * 
	 */
	protected StudentExcelUtil() {
		genderArray = new String[] {"M", "F", "m", "f"};
		genderList = Arrays.asList(genderArray);
		categoryArray = new String[] {"Day", "Boarder"};
		categoryList = Arrays.asList(categoryArray);
	} 

	
	/**
	 * @param file
	 * @param schooluuid
	 * @param studentDAO
	 * @param streamDAO
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException 
	 */
	String processUploadedFiles(File file,String schooluuid,StudentDAO studentDAO, StreamDAO streamDAO) throws IOException, InvalidFormatException {
		String feedback = UploadExcel.UPLOAD_SUCCESS;
		
		if(file !=null){
			FileInputStream myInput =  new FileInputStream(file);
			
			Workbook myWorkBook = WorkbookFactory.create(myInput);
			XSSFSheet mySheet = (XSSFSheet) myWorkBook.getSheetAt(0);
			int totalRow = mySheet.getLastRowNum();
			
			try{
				
				String classroom = "";
				String admno = "";
				String firstname = "";
				String middlename = "";
				String gender = "";
				String kcpe = "";
				String stydentType = "";

				int count = 1;
				int totalColumn = 0;
				for(int i=0; i<=totalRow; i++){
					XSSFRow row = mySheet.getRow(i);
					if(row !=null){
					  totalColumn = row.getLastCellNum();
					}
					
					for(int j=0; j<totalColumn; j++){
		    			if(i==0){
		    				XSSFCell classroomCell = row.getCell((short)0);
		    				classroom = classroomCell+""; 
		              
		    				if(totalColumn >6){
		    					return ("Invalid Number of comlumns on line \"" + count);
		    				}
		    				
		    			}else if(i > 1){
		    				
		    				admno = row.getCell(0)+"";
		    				firstname =  row.getCell(1)+"";
		    				middlename =  row.getCell(2)+"";
		    				gender =  row.getCell(3)+"";
		    				kcpe =  row.getCell(4)+"";
		    				stydentType = row.getCell(5)+"";
		    
						if (StringUtils.isBlank(admno) || StringUtils.equalsIgnoreCase(admno, "null")) {
							return ("Invalid/blank admno " + admno + " on line " + count);
						 }
						
						if (StringUtils.isBlank(firstname) || StringUtils.equalsIgnoreCase(firstname, "null")) {
							return ("Invalid/blank firstname " + firstname + " on line " + count);
						 }
						
						if (StringUtils.isBlank(middlename) || StringUtils.equalsIgnoreCase(middlename, "null")) {
							return ("Invalid/blank middlename " + middlename + " on line " + count);
						 }
						
						if (StringUtils.isBlank(gender) || StringUtils.equalsIgnoreCase(gender, "null")) {
							return ("Invalid/blank gender " + gender + " on line " + count);
						 }
						
						if(!genderList.contains(gender)) {
							return ("Invalid gender " + gender + " on line " + count);
						}
						
						if (StringUtils.isBlank(kcpe) || StringUtils.equalsIgnoreCase(kcpe, "null")) {
								return ("Blank K.C.P.E marks " + kcpe.replace(".0", "") + " on line " + count);
						} 
						
						if(!isNumeric(kcpe) ){
							return ("Invalid K.C.P.E marks " + kcpe.replace(".0", "") + " on line " + count);
						}

						if(Integer.parseInt(kcpe.replace(".0", "")) < 100 || Integer.parseInt( kcpe.replace(".0", "")) > 500 ){ 
							return ("Invalid (out of range) K.C.P.E marks " + kcpe.replace(".0", "") + " on line " + count);	
						}
						
						if(studentDAO.getStudentObjByadmNo(schooluuid, admno.replace(".0", "")) != null){
							return ("Student with admission number " + admno.replace(".0", "") + " on line " + count + " already exist.");
						}
						
						if(streamDAO.getroomByRoomName(schooluuid, classroom) ==null){
							return ("Classroom " + classroom + " not found.");
						}
						if(!categoryList.contains(stydentType)) {
							return ("Invalid category " + stydentType + " on line " + count);
						}
						
						
			
					}//end if
		    			
		    			
					}
					
    				//System.out.println("classroom = " + classroom + ",admno = " + admno + ",firstname = " + firstname + ",middlename = " + middlename + ",gender = " + gender + ",kcpe = " + kcpe);
    	
					count++;
				}
				
				
			}
			
		
			catch (Exception e){

			}finally {
				myInput.close();

			}
		}

		return feedback;
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
	 * @param uploadedFile
	 * @param school
	 * @param streamDAO
	 * @param primaryDAO
	 * @param studentDAO
	 * @param studentSubjectDAO
	 * @param subjectDAO
	 * @param sysConfigDAO
	 * @throws IOException
	 * @throws InvalidFormatException 
	 */
	public void saveResults(File uploadedFile,Account school, StreamDAO streamDAO, PrimaryDAO primaryDAO,
			StudentDAO studentDAO, StudentSubjectDAO studentSubjectDAO, SubjectDAO subjectDAO,SysConfigDAO sysConfigDAO) throws IOException, InvalidFormatException{

		if(uploadedFile !=null){
			
        FileInputStream myInput =  new FileInputStream(uploadedFile); 
			
			Workbook myWorkBook = WorkbookFactory.create(myInput);
			XSSFSheet mySheet = (XSSFSheet) myWorkBook.getSheetAt(0);
			int totalRow = mySheet.getLastRowNum();
			
			SysConfig sysConfig = new SysConfig();
			sysConfig = sysConfigDAO.getExamConfig(school.getUuid()); 
			try{
				
				String classroom = "";
				String classroomuuid = "";
				String admno = "";
				String firstname = "";
				String middlename = "";
				String gender = "";
				String kcpe = "";
				String studentType = "";
				
				Calendar calendar = Calendar.getInstance();
				final int YEAR = calendar.get(Calendar.YEAR);

				int totalColumn = 0;
				for(int i=0; i<=totalRow; i++){
					XSSFRow row = mySheet.getRow(i);
					if(row !=null){
					  totalColumn = row.getLastCellNum();
					}
					
					for(int j=0; j<totalColumn; j++){
		    			if(i==0){
		    				XSSFCell classroomCell = row.getCell((short)0);
		    				classroom = classroomCell+""; 
		    				ClassRoom classRoom = new ClassRoom();
		    				
		    				if(streamDAO.getroomByRoomName(school.getUuid(), classroom) !=null){
		    				 classRoom = streamDAO.getroomByRoomName(school.getUuid(), classroom);
		    				}
		    				if(classRoom !=null){
		    				 classroomuuid = classRoom.getUuid();
		    				}
		              
		    				
		    			}else if(i > 1){

		    				admno = row.getCell(0)+"";
		    				firstname =  row.getCell(1)+"";
		    				middlename =  row.getCell(2)+"";
		    				gender =  row.getCell(3)+"";
		    				kcpe =  row.getCell(4)+"";
		    				studentType = row.getCell(5)+"";
		    				

		    			}//end if
		    			
		    			
					}
					
					//save details
					if(!StringUtils.isBlank(admno) && !StringUtils.isBlank(firstname) && !StringUtils.isBlank(middlename) && !StringUtils.isBlank(gender) && !StringUtils.isBlank(kcpe)){
					
						admno = admno.replace(".0", "");
	    				kcpe = kcpe.replace(".0", "");
	    				

	    				if(StringUtils.equalsIgnoreCase(gender, "m")){
	    					gender = "MALE";
	    				}else{
	    					gender = "FEMALE";
	    				}
	    				
	    				if(!categoryList.contains(studentType)) {
	    					studentType = "Boarder";
						}

						Student student = new Student();
	    				student.setSchoolAccountUuid(school.getUuid()); 
	    				student.setStatusUuid(STATUS_ACTIVE); 
	    				student.setClassRoomUuid(classroomuuid);  
	    				student.setAdmno(admno);
	    				student.setFirstname(StringUtils.capitalize(firstname.toLowerCase()));
	    				student.setLastname(StringUtils.capitalize(middlename.toLowerCase()));
	    				student.setGender(gender);
	    				student.setdOB("1"+"/"+"12"+"/"+YEAR);
	    				student.setRegTerm(sysConfig.getTerm());  
	    				student.setFinalYear(YEAR+3); 
	    				student.setFinalTerm(3); 
	    				student.setSysUser("ADMIN");  
	    				student.setStudentType(studentType); 

	    				if(studentDAO.getStudentObjByadmNo(school.getUuid(), admno) == null){ 
	    				  
	    				   if(studentDAO.putStudents(student)){
	    					 //save primary details
	   	    				StudentPrimary sprimary = new StudentPrimary();
	   	    				sprimary.setStudentUuid(student.getUuid());
	   	    				sprimary.setSchoolname("notset");
	   	    				sprimary.setIndex("notset");
	   	    				sprimary.setKcpemark(kcpe);
	   	    				sprimary.setKcpeyear("notset"); 
	   	    				if(primaryDAO.getPrimary(student.getUuid()) == null){
	   	    				primaryDAO.putPrimary(sprimary); 
	   	    				}
	   	    				
	   	    			   //assign subject

		    				List<Subject> subjectList = new ArrayList<>();
		    				subjectList = subjectDAO.getAllSubjects();

		    				for(Subject sub : subjectList){
		    					StudentSubject sb = new StudentSubject(); 
		    					sb.setSubjectUuid(sub.getUuid()); 
		    					sb.setStudentUuid(student.getUuid());
		    					sb.setSysUser("ADMIN");
		    					if(studentSubjectDAO.getsubject(student.getUuid(),sub.getUuid()) == null){
		    					   studentSubjectDAO.putstudentSub(sb); 
		    					}

		    				}
	                       //subjects assigned
							
	   	    				
	   	    				  
	    				   }
	    				   
	    				}
	    				
						//System.out.println("classroom = " + classroom + ",admno = " + admno + ",firstname = " + firstname + ",middlename = " + middlename + ",gender = " + gender + ",kcpe = " + kcpe);
					}
    		
				}
				
				
			}
			
		
			catch (Exception e){

			}finally {
				myInput.close();

			}
		}
		
	}
	
}
