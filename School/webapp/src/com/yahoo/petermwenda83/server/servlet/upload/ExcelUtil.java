package com.yahoo.petermwenda83.server.servlet.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.CatOne;
import com.yahoo.petermwenda83.bean.exam.CatTwo;
import com.yahoo.petermwenda83.bean.exam.EndTerm;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.exam.PaperOne;
import com.yahoo.petermwenda83.bean.exam.PaperThree;
import com.yahoo.petermwenda83.bean.exam.PaperTwo;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.staff.TeacherSubClass;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamEgineDAO;
import com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;

public class ExcelUtil {

	//private static Logger logger = Logger.getLogger(StudentExcelUtil.class);
	private String[] examcodeArray;
	private List<String> examcodeList;
	
	final String FORM1 = "C143978A-E021-4015-BC67-5A00D6C910D1";
	final String FORM2 = "3E22E428-3155-42F5-B73E-66553ED501C9";
	final String FORM3 = "A4BFC2BD-262F-4207-99C8-057D6ADF80C7";
	final String FORM4 = "14E56350-08DA-45CC-97D9-C225AF74A7AD";
	
	final String FORMONE = "FORM 1";
	final String FORMTWO = "FORM 2";
	final String FORMTHREE = "FORM 3";
	final String FORMFOUR = "FORM 4";
	String classesuuid = "";
	
	
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
	 */
	protected ExcelUtil() {
		examcodeArray = new String[] {"c1", "c2", "et", "p1","p2","p3"};
		examcodeList = Arrays.asList(examcodeArray);
	} 
	
	
	/**
	 * Checks that an uploaded results file is in proper order.
	 * 
	 * @param file
	 * @return the feedback of having inspected the file, whether it was proper
	 * @throws IOException 
	 */
	protected String inspectResultFile(File file,String schooluuid,String staffId,RoomDAO roomDAO,SubjectDAO subjectDAO,TeacherSubClassDAO teacherSubClassDAO,StudentDAO studentDAO) throws IOException {
		
		String feedback = UploadExam.UPLOAD_SUCCESS;
		// Creating Input Stream 
		
		if(file !=null){
		FileInputStream myInput =  new FileInputStream(file);
		
		XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		int totalRow = mySheet.getLastRowNum();

		DecimalFormat rf = new DecimalFormat("0.0"); 
		rf.setRoundingMode(RoundingMode.HALF_UP);

		DecimalFormat rf2 = new DecimalFormat("0"); 
		rf2.setRoundingMode(RoundingMode.UP);

		DecimalFormat rf3 = new DecimalFormat("0"); 
		rf3.setRoundingMode(RoundingMode.DOWN);

		try{

			String subject = "";
			String classroom = "";
			String exam = "";

			String filename = file.getName().replaceAll("_", " "); 

			String [] parts = filename.split("\\.");
			subject = parts[0];
			classroom = parts[1]; 
			exam = parts[2]; 

			if(subjectDAO.getSubjects(subject) ==null){
				return ("subject code \"" + subject + "\" not found! ");
			}

			if(roomDAO.getroomByRoomName(schooluuid, classroom) ==null){
				return ("class code \"" + classroom + "\" not found! ");
			}

			String code = StringUtils.lowerCase(StringUtils.trimToEmpty(exam) );
			if(!examcodeList.contains(code)) {
				return ("Invalid exam code \"" + code.toUpperCase()+"\"");
			}
             
			String outof = "";
			String admnostr ="";
			String scorestr ="";
			int count = 1;
			int totalColumn = 0;
			for(int i=0; i<=totalRow; i++){
				XSSFRow row = mySheet.getRow(i);
				if(row !=null){
				  totalColumn = row.getLastCellNum();
				}
				
				for(int j=0; j<totalColumn; j++){
	    			if(i==0){
	    				XSSFCell cell1_score = row.getCell((short)1);
	    				
	    				String score_cell = cell1_score+""; 
	    				
	    				String [] score_cell_parts;
	    				score_cell_parts = score_cell.split("-");
	    				String part1_score = "";
	    				String part2_score = "";
	    				part1_score = score_cell_parts[0];   
	    				part2_score = score_cell_parts[1];  
	    				outof = part2_score;
	    				
						if (IsNull(part1_score)) { 
							return ("Invalid/blank Character on line one ");
						 }
						
						if (IsNull(part2_score)) { 
							return ("Invalid/blank outof ");
						 }
	    				
	    				if(totalColumn >2){
	    					return ("Invalid Number of comlumns on line \"" + count);
	    				}
	    				
	    			}else if(i >0){
	    				
					admnostr = row.getCell(0)+"";
					scorestr =  row.getCell(1)+"";
					

					if (StringUtils.isBlank(scorestr)) {
						return ("Invalid/blank score " + scorestr + " on line \"" + count);
					 }
					
					if(!isNumeric(outof)){
						return ("Invalid outof " + outof);
					}
					

					if(!isNumeric(scorestr)){
						return ("Invalid score " + scorestr + " on line \"" + count);
					}
					

					if(StringUtils.isBlank(scorestr) ||StringUtils.isEmpty(scorestr) || scorestr == null){
						return ("Blank score " + scorestr + " on line \"" + count);
					}


					String studentuuid = "";
					Student student = new Student();
					if(admnostr !=null){
						student = studentDAO.getStudentObjByadmNo(schooluuid,admnostr);
						if(student !=null){
							studentuuid = student.getUuid();
						}

					}

					if(studentDAO.getStudentByuuid(schooluuid,studentuuid)==null) {
						return ("Student with admNo \"" + admnostr + "\" on line \"" + count + "\" was not found in the System");
					}


					ClassRoom clss = roomDAO.getroomByRoomName(schooluuid, classroom);
					TeacherSubClass ts = new TeacherSubClass();  
					ts.setClassRoomUuid(clss.getUuid());
					ts.setTeacherUuid(staffId);
					Subject sub = subjectDAO.getSubjects(subject);
					ts.setSubjectUuid(sub.getUuid()); 

					if(teacherSubClassDAO.getSubjectClass(ts) ==null){
						//return ("Error! Confirm  that Subject \"" + subject +"\" or Class Room \"" + classroom + "\" really belongs to you.");
					}

				}//end if
	    			
	    			
				}
				
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
	 * @param string
	 * @return
	 */
	private static boolean IsNull(String string) {
		boolean isnull = false;
		if(StringUtils.isEmpty(string) || StringUtils.isBlank(string)){
			isnull = true;
		}
		return isnull; 
	}


	/**
	 * @param uploadedFile
	 * @param stffID
	 * @param school
	 * @param examEgineDAO
	 * @param studentDAO
	 * @param roomDAO
	 * @param subjectDAO
	 * @param examConfigDAO
	 * @throws IOException
	 */
	public void saveResults(File uploadedFile,String stffID, SchoolAccount school, ExamEgineDAO examEgineDAO,
			StudentDAO studentDAO, RoomDAO roomDAO, SubjectDAO subjectDAO,ExamConfigDAO examConfigDAO) throws IOException{
		
		if(uploadedFile !=null){
		
		
		String subject = "";
   		String classroom = "";
   		String exam = "";
   	    String studentuuid = "";
   	    String formated = "";
   	    String outof = "";
   	    
   	    
   	    ExamConfig  examConfig = examConfigDAO.getExamConfig(school.getUuid());
        String filename = uploadedFile.getName().replaceAll("_", " "); 
   		
   		String [] parts = filename.split("\\.");
   		subject = parts[0];
   		classroom = parts[1]; 
   		exam = parts[2]; 
   		
   		FileInputStream myInput =  new FileInputStream(uploadedFile);
        XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
   		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
   		int totalRow = mySheet.getLastRowNum();
		String scorestr = "";
		String admnostr  = "";
	
		
		try {
			
			int totalColumn = 0;
		for(int i=0; i<=totalRow; i++){
			XSSFRow row = mySheet.getRow(i);
			if(row !=null){
			  totalColumn = row.getLastCellNum();
			}
		
			for(int j=0; j<totalColumn; j++){
    			if(i==0){
    				XSSFCell cell1_score = row.getCell((short)1);
    				String score_cell = cell1_score+""; 
    				String [] score_cell_parts;
    				score_cell_parts = score_cell.split("-");
    				String part2_score = "";
    				part2_score = score_cell_parts[1];  
    				outof = part2_score;
    			}else if(i >0){
    				
				admnostr = row.getCell(0)+"";
				scorestr =  row.getCell(1)+"";
				
				DecimalFormat rf = new DecimalFormat("0.0"); 
				rf.setRoundingMode(RoundingMode.HALF_UP);
					
			    DecimalFormat rf2 = new DecimalFormat("0"); 
			    rf2.setRoundingMode(RoundingMode.UP);
			    
			    DecimalFormat rf3 = new DecimalFormat("0"); 
			    rf3.setRoundingMode(RoundingMode.DOWN);
				
				String score = (String) scorestr;
				double toformat = Double.parseDouble(score);
				double dblescore = Double.parseDouble(rf2.format((double)Math.round(Double.parseDouble(rf.format(toformat)))));
			    formated = rf3.format(dblescore);
			   
    			 }
    			
    			
			 
			}  
			    if(!StringUtils.equals(formated, "") && !StringUtils.equals(admnostr, "")){ 
			    	//System.out.println("formated : "+formated);
			    	//System.out.println("admnostr : "+admnostr);
			    	   Student student  = new Student();
			           double score = 0;
			           score = Double.parseDouble(formated);
			          // System.out.println("score="+score);
			           
			           ClassRoom room = new ClassRoom();
			           Subject subjct = new Subject();
			    	

					   String admno = StringUtils.trimToEmpty(admnostr);
					  // System.out.println("admno="+admno);
					   
					    if(admno !=null){
						    student = studentDAO.getStudentObjByadmNo(school.getUuid(),admno);
						   }
						    	
			               
			                
			                
			                
						     if(classroom !=null){
						    	 room = roomDAO.getroomByRoomName(school.getUuid(), classroom); 
						     }
						     if(subject !=null){
						    	subjct = subjectDAO.getSubjects(subject);	
						     }
						     
						     if(student !=null){
						    	 studentuuid = student.getUuid();
						    	}
						     
						    // System.out.println("studentuuid="+studentuuid);
						    
						     String classuuid = room.getUuid();
						     String roomname = room.getRoomName();
						     String subjectuuid = subjct.getUuid();
						     
						     if(StringUtils.contains(roomname, FORMONE) ){ 
			                    classesuuid = FORM1;
						     }else if(StringUtils.contains(roomname, FORMTWO)){
						    	 classesuuid = FORM2;
						     }else if(StringUtils.contains(roomname, FORMTHREE)){
						    	 classesuuid = FORM3;
						     }else if(StringUtils.contains(roomname, FORMFOUR)){
						    	 classesuuid = FORM4;
						     }
						
			    	   // System.out.println("outof = "+outof); 
			    	 
			    	    /*This is cat1 score, it should be out of 30 , else converted out of 30.
			    	     *  eg score/outof*30
			    	     *  
			    	     *   30/30*30 =30
			    	     *   
			    	     *   32/40*30 = 
			    	     *   20/40*30 = 
			    	     */
						     
						int i_outof = Integer.parseInt(outof); 


						if(StringUtils.equalsIgnoreCase(exam, "c1")){
							
							if(score > i_outof){
								score = 0;
							}
							
							CatOne catOne = new CatOne();
							catOne.setSchoolAccountUuid(school.getUuid());
							catOne.setTeacherUuid(stffID);
							catOne.setClassRoomUuid(classuuid); 
							catOne.setClassesUuid(classesuuid);
							catOne.setSubjectUuid(subjectuuid);
							catOne.setStudentUuid(studentuuid);
							catOne.setCatOne((score/i_outof)*30);  // convert to 30
							catOne.setTerm(examConfig.getTerm());
							catOne.setYear(examConfig.getYear()); 
							examEgineDAO.putScore(catOne,school.getUuid(),classuuid,studentuuid,subjectuuid,examConfig.getTerm(),examConfig.getYear());	
							//System.out.println("catOne="+catOne);
						}else if(StringUtils.equalsIgnoreCase(exam, "c2")){
							
							if(score > i_outof){
								score = 0;
							}
							
							CatTwo catwo = new CatTwo();
							catwo.setSchoolAccountUuid(school.getUuid());
							catwo.setTeacherUuid(stffID);
							catwo.setClassRoomUuid(classuuid);  
							catwo.setClassesUuid(classesuuid); 
							catwo.setSubjectUuid(subjectuuid);
							catwo.setStudentUuid(studentuuid);
							catwo.setCatTwo((score/i_outof)*30); // convert to 30
							catwo.setTerm(examConfig.getTerm());
							catwo.setYear(examConfig.getYear()); 
							examEgineDAO.putScore(catwo,school.getUuid(),classuuid,studentuuid,subjectuuid,examConfig.getTerm(),examConfig.getYear());

						}else if(StringUtils.equalsIgnoreCase(exam, "et")){
							
							if(score > i_outof){
								score = 0;
							}
							
							EndTerm endterm = new EndTerm();
							endterm.setSchoolAccountUuid(school.getUuid());
							endterm.setTeacherUuid(stffID);
							endterm.setClassRoomUuid(classuuid);  
							endterm.setClassesUuid(classesuuid); 
							endterm.setSubjectUuid(subjectuuid);
							endterm.setStudentUuid(studentuuid);
							endterm.setEndTerm((score/i_outof)*70); //convert to 70
							endterm.setTerm(examConfig.getTerm());
							endterm.setYear(examConfig.getYear()); 
							examEgineDAO.putScore(endterm,school.getUuid(),classuuid,studentuuid,subjectuuid,examConfig.getTerm(),examConfig.getYear());

						}else if(StringUtils.equalsIgnoreCase(exam, "p1")){
							
							if(score > i_outof){
								score = 0;
							}
							
	                        if(StringUtils.equals(subjectuuid, ENG_UUID)){
								  if(score > 60){
										score = 0;
								  }
							  }else if(StringUtils.equals(subjectuuid, KISWA_UUID)){
								  if(score > 60){
										score = 0;
								  }
							  }else if(StringUtils.equals(subjectuuid, MATH_UUID)){
								    if(score >100){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, PHY_UUID)){
								   if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, BIO_UUID)){
								   if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, CHEM_UUID)){
								  if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, BS_UUID)){
								  if(score >100){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, COMP_UUID)){
								  if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, H_S)){
								  if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, AGR_UUID)){
								  if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, GEO_UUID)){
								  if(score >100){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, CRE_UUID)){
								  if(score >100){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, HIST_UUID)){
								  if(score >100){
										score = 0;
									}
							  }
							
							
							PaperOne p1 = new PaperOne();
							p1.setSchoolAccountUuid(school.getUuid());
							p1.setTeacherUuid(stffID);
							p1.setClassRoomUuid(classuuid);  
							p1.setClassesUuid(classesuuid); 
							p1.setSubjectUuid(subjectuuid);
							p1.setStudentUuid(studentuuid);
							p1.setPaperOne(score); 
							p1.setTerm(examConfig.getTerm());
							p1.setYear(examConfig.getYear()); 
							examEgineDAO.putScore(p1,school.getUuid(),classuuid,studentuuid,subjectuuid,examConfig.getTerm(),examConfig.getYear());

						}else if(StringUtils.equalsIgnoreCase(exam, "p2")){
							
							if(score > i_outof){
								score = 0;
							}
							
							if(StringUtils.equals(subjectuuid, ENG_UUID)){
								  if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, KISWA_UUID)){
								  if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, MATH_UUID)){
								  if(score >100){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, PHY_UUID)){
								  if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, BIO_UUID)){
								  if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, CHEM_UUID)){
								  if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, BS_UUID)){
								  if(score >100){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, COMP_UUID)){
								  if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, H_S)){
								  if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, AGR_UUID)){
								  if(score >80){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, GEO_UUID)){
								  if(score >100){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, CRE_UUID)){
								  if(score >100){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, HIST_UUID)){
								  if(score >100){
										score = 0;
									}
							  }
							
							PaperTwo p2 = new PaperTwo();
							p2.setSchoolAccountUuid(school.getUuid());
							p2.setTeacherUuid(stffID);
							p2.setClassRoomUuid(classuuid);
							p2.setClassesUuid(classesuuid); 
							p2.setSubjectUuid(subjectuuid);
							p2.setStudentUuid(studentuuid);
							p2.setPaperTwo(score); 
							p2.setTerm(examConfig.getTerm());
							p2.setYear(examConfig.getYear()); 
							examEgineDAO.putScore(p2,school.getUuid(),classuuid,studentuuid,subjectuuid,examConfig.getTerm(),examConfig.getYear());

						}else if(StringUtils.equalsIgnoreCase(exam, "p3")){
							
							if(score > i_outof){
								score = 0;
							}
							
	                          if(StringUtils.equals(subjectuuid, ENG_UUID)){
								  if(score > 60){
										score = 0;
									}
									
							  }else if(StringUtils.equals(subjectuuid, KISWA_UUID)){
								  if(score > 60){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, MATH_UUID)){
								   if(score > 0){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, PHY_UUID)){
								  if(score > 40){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, BIO_UUID)){
								  if(score > 40){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, CHEM_UUID)){
								  if(score > 40){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, BS_UUID)){

							  }else if(StringUtils.equals(subjectuuid, COMP_UUID)){
								  if(score > 40){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, H_S)){
								  if(score > 40){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, AGR_UUID)){
								  if(score > 40){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, GEO_UUID)){
								  if(score > 0){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, CRE_UUID)){
								  if(score > 0){
										score = 0;
									}
							  }else if(StringUtils.equals(subjectuuid, HIST_UUID)){
								  if(score > 0){
										score = 0;
									}
							  }
							
							PaperThree p3 = new PaperThree();
							p3.setSchoolAccountUuid(school.getUuid());
							p3.setTeacherUuid(stffID);
							p3.setClassRoomUuid(classuuid);  
							p3.setClassesUuid(classesuuid); 
							p3.setSubjectUuid(subjectuuid);
							p3.setStudentUuid(studentuuid);
							p3.setPaperThree(score); 
							p3.setTerm(examConfig.getTerm());
							p3.setYear(examConfig.getYear()); 
							examEgineDAO.putScore(p3,school.getUuid(),classuuid,studentuuid,subjectuuid,examConfig.getTerm(),examConfig.getYear());

						}




			    }


		      }
		
			
			
		} finally {
			if(myInput != null) {
				myInput.close();
			}		   
		}
		
	  }
	}
}
