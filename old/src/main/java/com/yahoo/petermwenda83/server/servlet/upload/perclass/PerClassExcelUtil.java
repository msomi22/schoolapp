package com.yahoo.petermwenda83.server.servlet.upload.perclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.CatOne;
import com.yahoo.petermwenda83.bean.exam.CatTwo;
import com.yahoo.petermwenda83.bean.exam.EndTerm;
import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.exam.PaperOne;
import com.yahoo.petermwenda83.bean.exam.PaperThree;
import com.yahoo.petermwenda83.bean.exam.PaperTwo;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.subject.Subject;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamEgineDAO;
import com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;

public class PerClassExcelUtil {

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

	/**
	 * 
	 */
	protected PerClassExcelUtil() {
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
	protected String inspectResultFile(File file,String schooluuid,String staffId,StreamDAO streamDAO,SubjectDAO subjectDAO,TeacherSubClassDAO teacherSubClassDAO,StudentDAO studentDAO) throws IOException {

		String feedback = PerClassUploadExam.UPLOAD_SUCCESS;
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

				String classroom = "";
				String exam = "";

				String filename = file.getName().replaceAll("_", " "); 

				String [] parts = filename.split("\\.");
				classroom = parts[0]; 
				exam = parts[1];

				if(streamDAO.getroomByRoomName(schooluuid, classroom) ==null){
					return ("Class code \"" + classroom + "\" not found! ");
				}

				String code = StringUtils.lowerCase(StringUtils.trimToEmpty(exam));
				if(!examcodeList.contains(code)) {
					return ("Invalid exam code \"" + code.toUpperCase()+"\"");
				}
				
				//System.out.println("classroom "+ classroom + " code " + code + " exam " + exam);

				String admnostr = "";
				//subjects
				String engscorestr = "";
				String kisscorestr = "";
				String mathscorestr = "";

				String physcorestr = "";
				String bioscorestr = "";
				String chemscorestr = "";

				String bsscorestr = "";
				String agrscorestr = "";
				String compscorestr = "";
				String homescscorestr = "";

				String geoscorestr = "";
				String crescorestr = "";
				String histscorestr = "";

				//subjects out of
				String engscorestroutof = "";
				String kisscorestroutof = "";
				String mathscorestroutof = "";

				String physcorestroutof = "";
				String bioscorestroutof = "";
				String chemscorestroutof = "";

				String bsscorestroutof = "";
				String agrscorestroutof = "";
				String compscorestroutof = "";
				String homescscorestroutof = "";

				String geoscorestroutof = "";
				String crescorestroutof = "";
				String histscorestroutof = "";


				//subjects code
				String engscode = "";
				String kisscode = "";
				String mathcode = "";

				String phycode = "";
				String biocode = "";
				String chemcode = "";

				String bscode = "";
				String agrcode = "";
				String compcode = "";
				String hsccode = "";

				String geocode = "";
				String crecode = "";
				String histcode = "";
				//score


				String eng = ""; 
				String kis = ""; 
				String mat = ""; 

				String phy = ""; 
				String bio = ""; 
				String chm = ""; 

				String bs = ""; 
				String agr = ""; 
				String cmp = ""; 
				String hsc = ""; 

				String geo = ""; 
				String cre = ""; 
				String hst = ""; 


				int count = 1;
				int totalColumn = 0;
				for(int i=0; i<=totalRow; i++){
					XSSFRow row = mySheet.getRow(i);

					if(row !=null){
						totalColumn = row.getLastCellNum();
					}

					for(int j=0; j<totalColumn; j++){
						if(i==0){
							
							if(totalColumn != 15){
								return ("Invalid Number of comlumns on line \"" + count);
							}


							XSSFCell engcell = row.getCell((short)2);
							XSSFCell kisscell = row.getCell((short)3);
							XSSFCell matcell = row.getCell((short)4);

							XSSFCell phycell = row.getCell((short)5);
							XSSFCell bioscell = row.getCell((short)6);
							XSSFCell chmcell = row.getCell((short)7);

							XSSFCell bscell = row.getCell((short)8);
							XSSFCell agrscell = row.getCell((short)9);
							XSSFCell cmpcell = row.getCell((short)10);
							XSSFCell hsccell = row.getCell((short)11);

							XSSFCell geocell = row.getCell((short)12);
							XSSFCell crecell = row.getCell((short)13);
							XSSFCell hstcell = row.getCell((short)14);


							eng = engcell + ""; 
							kis = kisscell + ""; 
							mat = matcell + ""; 

							phy = phycell + ""; 
							bio = bioscell + ""; 
							chm = chmcell + ""; 

							bs = bscell + ""; 
							agr = agrscell + ""; 
							cmp = cmpcell + ""; 
							hsc = hsccell + ""; 

							geo = geocell + ""; 
							cre = crecell + ""; 
							hst = hstcell + ""; 
							
							if(!StringUtils.contains(eng, "/")){
								return "Invalid format for " + eng.toUpperCase() + " on line " + count + ", try ENG/outof e.g. ENG/50 ";
							}
							if(!StringUtils.contains(kis, "/")){
								return "Invalid format for " + kis.toUpperCase() + " on line " + count + ", try KIS/outof e.g. KIS/50 ";
							}
							if(!StringUtils.contains(mat, "/")){
								return "Invalid format for " + mat.toUpperCase() + " on line " + count + ", try MAT/outof e.g. MAT/50 ";
							}
							
							
							if(!StringUtils.contains(phy, "/")){
								return "Invalid format for " + phy.toUpperCase() + " on line " + count + ", try PHY/outof e.g. PHY/50 ";
							}
							if(!StringUtils.contains(bio, "/")){
								return "Invalid format for " + bio.toUpperCase() + " on line " + count + ", try BIO/outof e.g. BIO/50 ";
							}
							if(!StringUtils.contains(chm, "/")){
								return "Invalid format for " + chm.toUpperCase() + " on line " + count + ", try CHE/outof e.g. CHE/50 ";
							}
							
							
							if(!StringUtils.contains(bs, "/")){
								return "Invalid format for " + bs.toUpperCase() + " on line " + count + ", try BS/outof e.g. BS/50 ";
							}
							if(!StringUtils.contains(agr, "/")){
								return "Invalid format for " + agr.toUpperCase() + " on line " + count + ", try AGR/outof e.g. AGR/50 ";
							}
							if(!StringUtils.contains(cmp, "/")){
								return "Invalid format for " + cmp.toUpperCase() + " on line " + count + ", try COM/outof e.g. COM/50 ";
							}
							if(!StringUtils.contains(hsc, "/")){
								return "Invalid format for " + hsc.toUpperCase() + " on line " + count + ", try HSC/outof e.g. HSC/50 ";
							}
							
							if(!StringUtils.contains(geo, "/")){
								return "Invalid format for " + geo.toUpperCase() + " on line " + count + ", try GEO/outof e.g. GEO/50 ";
							}if(!StringUtils.contains(cre, "/")){
								return "Invalid format for " + cre.toUpperCase() + " on line " + count + ", try CRE/outof e.g. CRE/50 ";
							}if(!StringUtils.contains(hst, "/")){
								return "Invalid format for " + hst.toUpperCase() + " on line " + count + ", try BS/outof e.g. BS/50 ";
							}

							String[] engparts = eng.split("/") ;
							String[] kisparts = kis.split("/") ;
							String[] matparts = mat.split("/") ;

							String[] phyparts = phy.split("/") ;
							String[] bioparts = bio.split("/") ;
							String[] chmparts = chm.split("/") ;

							String[] bsparts = bs.split("/") ;
							String[] agrparts = agr.split("/") ;
							String[] cmpparts = cmp.split("/") ;
							String[] hscparts = hsc.split("/") ;

							String[] geoparts = geo.split("/") ;
							String[] creparts = cre.split("/") ;
							String[] hstparts = hst.split("/") ;

							engscorestroutof = engparts[1]; 
							kisscorestroutof = kisparts[1]; 
							mathscorestroutof = matparts[1]; 

							physcorestroutof = phyparts[1]; 
							bioscorestroutof = bioparts[1]; 
							chemscorestroutof = chmparts[1]; 

							bsscorestroutof = bsparts[1]; 
							agrscorestroutof = agrparts[1]; 
							compscorestroutof = cmpparts[1]; 
							homescscorestroutof = hscparts[1]; 

							geoscorestroutof = geoparts[1]; 
							crescorestroutof = creparts[1]; 
							histscorestroutof = hstparts[1]; 


							engscode = engparts[0]; 
							kisscode = kisparts[0]; 
							mathcode = matparts[0]; 

							phycode = phyparts[0]; 
							biocode = bioparts[0]; 
							chemcode = chmparts[0]; 

							bscode = bsparts[0]; 
							agrcode = agrparts[0]; 
							compcode = cmpparts[0]; 
							hsccode = hscparts[0]; 

							geocode = geoparts[0]; 
							crecode = creparts[0]; 
							histcode = hstparts[0]; 


							if(subjectDAO.getSubjects(engscode) == null){
								return ("Invalid Subject code " + engscode + " on line " + count);
							}if(subjectDAO.getSubjects(kisscode) == null){
								return ("Invalid Subject code " + kisscode + " on line " + count);
							}if(subjectDAO.getSubjects(mathcode) == null){
								return ("Invalid Subject code " + mathcode + " on line " + count);
							}

							if(subjectDAO.getSubjects(phycode) == null){
								return ("Invalid Subject code " + phycode + " on line " + count);
							}if(subjectDAO.getSubjects(biocode) == null){
								return ("Invalid Subject code " + biocode + " on line " + count);
							}if(subjectDAO.getSubjects(chemcode) == null){
								return ("Invalid Subject code " + chemcode + " on line " + count);
							}

							if(subjectDAO.getSubjects(bscode) == null){
								return ("Invalid Subject code " + bscode + " on line " + count);
							}if(subjectDAO.getSubjects(agrcode) == null){
								return ("Invalid Subject code " + agrcode + " on line " + count);
							}if(subjectDAO.getSubjects(compcode) == null){
								return ("Invalid Subject code " + compcode + " on line " + count);
							}if(subjectDAO.getSubjects(hsccode) == null){
								return ("Invalid Subject code " + hsccode + " on line " + count);
							}

							if(subjectDAO.getSubjects(geocode) == null){
								return ("Invalid Subject code " + geocode + " on line " + count);
							}if(subjectDAO.getSubjects(crecode) == null){
								return ("Invalid Subject code " + crecode + " on line " + count);
							}if(subjectDAO.getSubjects(histcode) == null){
								return ("Invalid Subject code " + histcode + " on line " + count);
							}



						}else if(i >0){

							admnostr = row.getCell(1)+"";
							admnostr = (int)Double.parseDouble(admnostr) + "";  
							//System.out.println("adm no = " + admnostr);
							engscorestr =  row.getCell(2)+"";
							kisscorestr =  row.getCell(3)+"";
							mathscorestr =  row.getCell(4)+"";

							physcorestr =  row.getCell(5)+"";
							bioscorestr =  row.getCell(6)+"";
							chemscorestr =  row.getCell(7)+"";

							bsscorestr =  row.getCell(8)+"";
							agrscorestr =  row.getCell(9)+"";
							compscorestr =  row.getCell(10)+"";
							homescscorestr =  row.getCell(11)+"";

							geoscorestr =  row.getCell(12)+"";
							crescorestr =  row.getCell(13)+"";
							histscorestr =  row.getCell(14)+"";



							if(StringUtils.equals(engscorestr, "null") || !isNumeric(engscorestr)){
								return "Blank or invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(kisscorestr, "null")|| !isNumeric(kisscorestr)){
								return "Blank or invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(mathscorestr, "null")|| !isNumeric(mathscorestr)){
								return "Blank or invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}

							if(StringUtils.equals(bioscorestr, "null")|| !isNumeric(bioscorestr)){
								return "Blank or invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(physcorestr, "null")|| !isNumeric(physcorestr)){
								return "Blank or invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(chemscorestr, "null")|| !isNumeric(chemscorestr)){
								return "Blank or invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}

							if(StringUtils.equals(bsscorestr, "null")|| !isNumeric(bsscorestr)){
								return "Blank or invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(agrscorestr, "null")|| !isNumeric(agrscorestr)){
								return "Blank or invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(compscorestr, "null")|| !isNumeric(compscorestr)){
								return "Blank or invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(homescscorestr, "null")|| !isNumeric(homescscorestr)){
								return "Blank or invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}

							if(StringUtils.equals(geoscorestr, "null")|| !isNumeric(geoscorestr)){
								return "Blank or invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(crescorestr, "null")|| !isNumeric(crescorestr)){
								return "Blank or invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
							}if(StringUtils.equals(histscorestr, "null")|| !isNumeric(histscorestr)){
								return "Blank or invalid score not allowed, replace with \"0\" without quotes,check score on line " + count; 
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




						}//end if


					}

					if(!StringUtils.isBlank(admnostr)){

							if(Double.parseDouble(engscorestr) > Double.parseDouble(engscorestroutof)){ 
								return "Invalid score " + engscorestr + " on line " + count + " . Hint : score " + engscorestr + " is greater that ( > ) the \"out of\" mark " + engscorestroutof + " , this is impossible .";
							}if(Double.parseDouble(kisscorestr) > Double.parseDouble(kisscorestroutof)){ 
								return "Invalid score " + kisscorestr + " on line " + count + " . Hint : score " + kisscorestr + " is greater that ( > ) the \"out of\" mark " + kisscorestroutof + " , this is impossible .";
							}if(Double.parseDouble(mathscorestr) > Double.parseDouble(mathscorestroutof)){ 
								return "Invalid score " + mathscorestr + " on line " + count + " . Hint : score " + mathscorestr + " is greater that ( > ) the \"out of\" mark " + mathscorestroutof + " , this is impossible .";
							}

							if(Double.parseDouble(physcorestr) > Double.parseDouble(physcorestroutof)){ 
								return "Invalid score " + physcorestr + " on line " + count + " . Hint : score " + physcorestr + " is greater that ( > ) the \"out of\" mark " + physcorestroutof + " , this is impossible .";
							}if(Double.parseDouble(bioscorestr) > Double.parseDouble(bioscorestroutof)){ 
								return "Invalid score " + bioscorestr + " on line " + count + " . Hint : score " + bioscorestr + " is greater that ( > ) the \"out of\" mark " + bioscorestroutof + " , this is impossible .";
							}if(Double.parseDouble(chemscorestr) > Double.parseDouble(chemscorestroutof)){ 
								return "Invalid score " + chemscorestr + " on line " + count + " . Hint : score " + chemscorestr + " is greater that ( > ) the \"out of\" mark " + chemscorestroutof + " , this is impossible .";
							}

							if(Double.parseDouble(bsscorestr) > Double.parseDouble(bsscorestroutof)){ 
								return "Invalid score " + bsscorestr + " on line " + count + " . Hint : score " + bsscorestr + " is greater that ( > ) the \"out of\" mark " + bsscorestroutof + " , this is impossible .";
							}if(Double.parseDouble(agrscorestr) > Double.parseDouble(agrscorestroutof)){ 
								return "Invalid score " + agrscorestr + " on line " + count + " . Hint : score " + agrscorestr + " is greater that ( > ) the \"out of\" mark " + agrscorestroutof + " , this is impossible .";
							}if(Double.parseDouble(compscorestr) > Double.parseDouble(compscorestroutof)){ 
								return "Invalid score " + compscorestr + " on line " + count + " . Hint : score " + compscorestr + " is greater that ( > ) the \"out of\" mark " + compscorestroutof + " , this is impossible .";
							}if(Double.parseDouble(homescscorestr) > Double.parseDouble(homescscorestroutof)){ 
								return "Invalid score " + homescscorestr + " on line " + count + " . Hint : score " + homescscorestr + " is greater that ( > ) the \"out of\" mark " + homescscorestroutof + " , this is impossible .";
							}

							if(Double.parseDouble(geoscorestr) > Double.parseDouble(geoscorestroutof)){ 
								return "Invalid score " + geoscorestr + " on line " + count + " . Hint : score " + geoscorestr + " is greater that ( > ) the \"out of\" mark " + geoscorestroutof + " , this is impossible .";
							}if(Double.parseDouble(crescorestr) > Double.parseDouble(crescorestroutof)){ 
								return "Invalid score " + crescorestr + " on line " + count + " . Hint : score " + crescorestr + " is greater that ( > ) the \"out of\" mark " + crescorestroutof + " , this is impossible .";
							}if(Double.parseDouble(histscorestr) > Double.parseDouble(histscorestroutof)){ 
								return "Invalid score " + histscorestr + " on line " + count + " . Hint : score " + histscorestr + " is greater that ( > ) the \"out of\" mark " + histscorestroutof + " , this is impossible .";
							}
						
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
	 * @param uploadedFile
	 * @param stffID
	 * @param school
	 * @param examEgineDAO
	 * @param studentDAO
	 * @param streamDAO
	 * @param subjectDAO
	 * @param sysConfigDAO
	 * @throws IOException
	 */
	public void saveResults(File uploadedFile,String stffID, Account school, ExamEgineDAO examEgineDAO,
			StudentDAO studentDAO, StreamDAO streamDAO, SubjectDAO subjectDAO,SysConfigDAO sysConfigDAO) throws IOException{

		if(uploadedFile !=null){
			FileInputStream myInput =  new FileInputStream(uploadedFile);

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

				String classroom = "";
				String exam = "";

				String filename = uploadedFile.getName().replaceAll("_", " "); 
				SysConfig  sysConfig = sysConfigDAO.getExamConfig(school.getUuid());
				String [] parts = filename.split("\\.");
				classroom = parts[0]; 
				exam = parts[1];


				String admnostr = "";
				//subjects
				String engscorestr = "";
				String kisscorestr = "";
				String mathscorestr = "";

				String physcorestr = "";
				String bioscorestr = "";
				String chemscorestr = "";

				String bsscorestr = "";
				String agrscorestr = "";
				String compscorestr = "";
				String homescscorestr = "";

				String geoscorestr = "";
				String crescorestr = "";
				String histscorestr = "";

				//subjects out of
				String engscorestroutof = "";
				String kisscorestroutof = "";
				String mathscorestroutof = "";

				String physcorestroutof = "";
				String bioscorestroutof = "";
				String chemscorestroutof = "";

				String bsscorestroutof = "";
				String agrscorestroutof = "";
				String compscorestroutof = "";
				String homescscorestroutof = "";

				String geoscorestroutof = "";
				String crescorestroutof = "";
				String histscorestroutof = "";


				//subjects code
				String engscode = "";
				String kisscode = "";
				String mathcode = "";

				String phycode = "";
				String biocode = "";
				String chemcode = "";

				String bscode = "";
				String agrcode = "";
				String compcode = "";
				String hsccode = "";

				String geocode = "";
				String crecode = "";
				String histcode = "";
				//score


				String eng = ""; 
				String kis = ""; 
				String mat = ""; 

				String phy = ""; 
				String bio = ""; 
				String chm = ""; 

				String bs = ""; 
				String agr = ""; 
				String cmp = ""; 
				String hsc = ""; 

				String geo = ""; 
				String cre = ""; 
				String hst = ""; 


				//int count = 1;
				int totalColumn = 0;
				String studentuuid = "";
				for(int i=0; i<=totalRow; i++){

					Student student = new Student();
					XSSFRow row = mySheet.getRow(i);
					if(row !=null){
						totalColumn = row.getLastCellNum();
					}

					for(int j=0; j<totalColumn; j++){
						if(i==0){

							XSSFCell engcell = row.getCell((short)2);
							XSSFCell kisscell = row.getCell((short)3);
							XSSFCell matcell = row.getCell((short)4);

							XSSFCell phycell = row.getCell((short)5);
							XSSFCell bioscell = row.getCell((short)6);
							XSSFCell chmcell = row.getCell((short)7);

							XSSFCell bscell = row.getCell((short)8);
							XSSFCell agrscell = row.getCell((short)9);
							XSSFCell cmpcell = row.getCell((short)10);
							XSSFCell hsccell = row.getCell((short)11);

							XSSFCell geocell = row.getCell((short)12);
							XSSFCell crecell = row.getCell((short)13);
							XSSFCell hstcell = row.getCell((short)14);


							eng = engcell + ""; 
							kis = kisscell + ""; 
							mat = matcell + ""; 

							phy = phycell + ""; 
							bio = bioscell + ""; 
							chm = chmcell + ""; 

							bs = bscell + ""; 
							agr = agrscell + ""; 
							cmp = cmpcell + ""; 
							hsc = hsccell + ""; 

							geo = geocell + ""; 
							cre = crecell + ""; 
							hst = hstcell + ""; 

							String[] engparts = eng.split("/") ;
							String[] kisparts = kis.split("/") ;
							String[] matparts = mat.split("/") ;

							String[] phyparts = phy.split("/") ;
							String[] bioparts = bio.split("/") ;
							String[] chmparts = chm.split("/") ;

							String[] bsparts = bs.split("/") ;
							String[] agrparts = agr.split("/") ;
							String[] cmpparts = cmp.split("/") ;
							String[] hscparts = hsc.split("/") ;

							String[] geoparts = geo.split("/") ;
							String[] creparts = cre.split("/") ;
							String[] hstparts = hst.split("/") ;
							

							engscorestroutof = engparts[1]; 
							kisscorestroutof = kisparts[1]; 
							mathscorestroutof = matparts[1]; 

							physcorestroutof = phyparts[1]; 
							bioscorestroutof = bioparts[1]; 
							chemscorestroutof = chmparts[1]; 

							bsscorestroutof = bsparts[1]; 
							agrscorestroutof = agrparts[1]; 
							compscorestroutof = cmpparts[1]; 
							homescscorestroutof = hscparts[1]; 

							geoscorestroutof = geoparts[1]; 
							crescorestroutof = creparts[1]; 
							histscorestroutof = hstparts[1]; 
							

							engscode = engparts[0]; 
							kisscode = kisparts[0]; 
							mathcode = matparts[0]; 

							phycode = phyparts[0]; 
							biocode = bioparts[0]; 
							chemcode = chmparts[0]; 

							bscode = bsparts[0]; 
							agrcode = agrparts[0]; 
							compcode = cmpparts[0]; 
							hsccode = hscparts[0]; 

							geocode = geoparts[0]; 
							crecode = creparts[0]; 
							histcode = hstparts[0]; 



						}else if(i >0){

							admnostr = row.getCell(1)+"";
							admnostr = (int)Double.parseDouble(admnostr) + "";  
							engscorestr =  row.getCell(2)+"";
							kisscorestr =  row.getCell(3)+"";
							mathscorestr =  row.getCell(4)+"";

							physcorestr =  row.getCell(5)+"";
							bioscorestr =  row.getCell(6)+"";
							chemscorestr =  row.getCell(7)+"";

							bsscorestr =  row.getCell(8)+"";
							agrscorestr =  row.getCell(9)+"";
							compscorestr =  row.getCell(10)+"";
							homescscorestr =  row.getCell(11)+"";

							geoscorestr =  row.getCell(12)+"";
							crescorestr =  row.getCell(13)+"";
							histscorestr =  row.getCell(14)+"";

							if(admnostr !=null){
								student = studentDAO.getStudentObjByadmNo(school.getUuid(),admnostr);
								if(student !=null){
									studentuuid = student.getUuid();
								}

							}



						}//end if


					}
					
					if(!StringUtils.isBlank(studentuuid)){ 
						


						String finaleng,finalkis,finalmat = "";
						String finalbio,finalphy,finalchm = "";
						String finalbs,finalagr,finalcmp,finalhsc = "";
						String finalgeo,finalcre,finalhst = "";

						List<Subject>  subjectlist = new ArrayList<>();
						subjectlist = subjectDAO.getAllSubjects();
						ClassRoom clssRoom = new ClassRoom();
						clssRoom = streamDAO.getroomByRoomName(school.getUuid(), classroom); 
						if(StringUtils.contains(clssRoom.getRoomName(), FORMONE) ){ 
							classesuuid = FORM1;
						}else if(StringUtils.contains(clssRoom.getRoomName(), FORMTWO)){
							classesuuid = FORM2;
						}else if(StringUtils.contains(clssRoom.getRoomName(), FORMTHREE)){
							classesuuid = FORM3;
						}else if(StringUtils.contains(clssRoom.getRoomName(), FORMFOUR)){
							classesuuid = FORM4;
						}
						
						

						//System.out.println("eng " + engscorestr + " out of " + engscorestroutof); 

						
						

						for(Subject sub : subjectlist){


							if(StringUtils.equalsIgnoreCase(exam, "c1")){
								//System.out.println("exam c1 = " + exam);

								finaleng = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(engscorestr)/Double.parseDouble(engscorestroutof))*30)));
								finalkis = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(kisscorestr)/Double.parseDouble(kisscorestroutof))*30)));
								finalmat = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(mathscorestr)/Double.parseDouble(mathscorestroutof))*30)));
								finalbio = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(bioscorestr)/Double.parseDouble(bioscorestroutof))*30)));
								finalphy = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(physcorestr)/Double.parseDouble(physcorestroutof))*30)));
								finalchm = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(chemscorestr)/Double.parseDouble(chemscorestroutof))*30)));
								finalbs = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(bsscorestr)/Double.parseDouble(bsscorestroutof))*30)));
								finalagr = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(agrscorestr)/Double.parseDouble(agrscorestroutof))*30)));
								finalcmp = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(compscorestr)/Double.parseDouble(compscorestroutof))*30)));
								finalhsc = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(homescscorestr)/Double.parseDouble(homescscorestroutof))*30)));
								finalgeo = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(geoscorestr)/Double.parseDouble(geoscorestroutof))*30)));
								finalcre = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(crescorestr)/Double.parseDouble(crescorestroutof))*30)));
								finalhst = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(histscorestr)/Double.parseDouble(histscorestroutof))*30)));

								CatOne catOne = new CatOne();
								catOne.setSchoolAccountUuid(school.getUuid());
								catOne.setTeacherUuid(stffID);
								catOne.setClassRoomUuid(clssRoom.getUuid()); 
								catOne.setClassesUuid(classesuuid);
								catOne.setStudentUuid(studentuuid);
								catOne.setTerm(sysConfig.getTerm());
								catOne.setYear(sysConfig.getYear()); 

								if(StringUtils.equals(sub.getSubjectCode(), engscode)){

									catOne.setSubjectUuid(sub.getUuid()); 
									catOne.setCatOne(Double.parseDouble(finaleng));
									examEgineDAO.putScore(catOne,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), kisscode)){
									
									catOne.setSubjectUuid(sub.getUuid()); 
									catOne.setCatOne(Double.parseDouble(finalkis));
									examEgineDAO.putScore(catOne,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), mathcode)){
									
									catOne.setSubjectUuid(sub.getUuid()); 
									catOne.setCatOne(Double.parseDouble(finalmat));
									examEgineDAO.putScore(catOne,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), phycode)){
									
									catOne.setSubjectUuid(sub.getUuid()); 
									catOne.setCatOne(Double.parseDouble(finalphy));
									examEgineDAO.putScore(catOne,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), biocode)){
									
									catOne.setSubjectUuid(sub.getUuid()); 
									catOne.setCatOne(Double.parseDouble(finalbio));
									examEgineDAO.putScore(catOne,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), chemcode)){
									
									catOne.setSubjectUuid(sub.getUuid()); 
									catOne.setCatOne(Double.parseDouble(finalchm));
									examEgineDAO.putScore(catOne,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), bscode)){
									
									catOne.setSubjectUuid(sub.getUuid()); 
									catOne.setCatOne(Double.parseDouble(finalbs));
									examEgineDAO.putScore(catOne,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), agrcode)){
									
									catOne.setSubjectUuid(sub.getUuid()); 
									catOne.setCatOne(Double.parseDouble(finalagr));
									examEgineDAO.putScore(catOne,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), compcode)){
									
									catOne.setSubjectUuid(sub.getUuid()); 
									catOne.setCatOne(Double.parseDouble(finalcmp));
									examEgineDAO.putScore(catOne,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), hsccode)){
									
									catOne.setSubjectUuid(sub.getUuid()); 
									catOne.setCatOne(Double.parseDouble(finalhsc));
									examEgineDAO.putScore(catOne,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), geocode)){
									
									catOne.setSubjectUuid(sub.getUuid()); 
									catOne.setCatOne(Double.parseDouble(finalgeo));
									examEgineDAO.putScore(catOne,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), crecode)){
									
									catOne.setSubjectUuid(sub.getUuid()); 
									catOne.setCatOne(Double.parseDouble(finalcre));
									examEgineDAO.putScore(catOne,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), histcode)){
									
									catOne.setSubjectUuid(sub.getUuid()); 
									catOne.setCatOne(Double.parseDouble(finalhst));
									examEgineDAO.putScore(catOne,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}



							}else if(StringUtils.equalsIgnoreCase(exam, "c2")){
								//System.out.println("exam c2= " + exam);



								finaleng = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(engscorestr)/Double.parseDouble(engscorestroutof))*30)));
								finalkis = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(kisscorestr)/Double.parseDouble(kisscorestroutof))*30)));
								finalmat = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(mathscorestr)/Double.parseDouble(mathscorestroutof))*30)));
								finalbio = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(bioscorestr)/Double.parseDouble(bioscorestroutof))*30)));
								finalphy = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(physcorestr)/Double.parseDouble(physcorestroutof))*30)));
								finalchm = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(chemscorestr)/Double.parseDouble(chemscorestroutof))*30)));
								finalbs = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(bsscorestr)/Double.parseDouble(bsscorestroutof))*30)));
								finalagr = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(agrscorestr)/Double.parseDouble(agrscorestroutof))*30)));
								finalcmp = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(compscorestr)/Double.parseDouble(compscorestroutof))*30)));
								finalhsc = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(homescscorestr)/Double.parseDouble(homescscorestroutof))*30)));
								finalgeo = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(geoscorestr)/Double.parseDouble(geoscorestroutof))*30)));
								finalcre = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(crescorestr)/Double.parseDouble(crescorestroutof))*30)));
								finalhst = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(histscorestr)/Double.parseDouble(histscorestroutof))*30)));

								CatTwo catwo = new CatTwo();
								catwo.setSchoolAccountUuid(school.getUuid());
								catwo.setTeacherUuid(stffID);
								catwo.setClassRoomUuid(clssRoom.getUuid()); 
								catwo.setClassesUuid(classesuuid);
								catwo.setStudentUuid(studentuuid);
								catwo.setTerm(sysConfig.getTerm());
								catwo.setYear(sysConfig.getYear()); 

								if(StringUtils.equals(sub.getSubjectCode(), engscode)){

									catwo.setSubjectUuid(sub.getUuid()); 
									catwo.setCatTwo(Double.parseDouble(finaleng));
									examEgineDAO.putScore(catwo,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), kisscode)){
									
									catwo.setSubjectUuid(sub.getUuid()); 
									catwo.setCatTwo(Double.parseDouble(finalkis));
									examEgineDAO.putScore(catwo,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), mathcode)){
									
									catwo.setSubjectUuid(sub.getUuid()); 
									catwo.setCatTwo(Double.parseDouble(finalmat));
									examEgineDAO.putScore(catwo,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), phycode)){
									
									catwo.setSubjectUuid(sub.getUuid()); 
									catwo.setCatTwo(Double.parseDouble(finalphy));
									examEgineDAO.putScore(catwo,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), biocode)){
									
									catwo.setSubjectUuid(sub.getUuid()); 
									catwo.setCatTwo(Double.parseDouble(finalbio));
									examEgineDAO.putScore(catwo,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), chemcode)){
									
									catwo.setSubjectUuid(sub.getUuid()); 
									catwo.setCatTwo(Double.parseDouble(finalchm));
									examEgineDAO.putScore(catwo,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), bscode)){
									
									catwo.setSubjectUuid(sub.getUuid()); 
									catwo.setCatTwo(Double.parseDouble(finalbs));
									examEgineDAO.putScore(catwo,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), agrcode)){
									
									catwo.setSubjectUuid(sub.getUuid()); 
									catwo.setCatTwo(Double.parseDouble(finalagr));
									examEgineDAO.putScore(catwo,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), compcode)){
									
									catwo.setSubjectUuid(sub.getUuid()); 
									catwo.setCatTwo(Double.parseDouble(finalcmp));
									examEgineDAO.putScore(catwo,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), hsccode)){
									
									catwo.setSubjectUuid(sub.getUuid()); 
									catwo.setCatTwo(Double.parseDouble(finalhsc));
									examEgineDAO.putScore(catwo,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), geocode)){
									
									catwo.setSubjectUuid(sub.getUuid()); 
									catwo.setCatTwo(Double.parseDouble(finalgeo));
									examEgineDAO.putScore(catwo,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), crecode)){
									
									catwo.setSubjectUuid(sub.getUuid()); 
									catwo.setCatTwo(Double.parseDouble(finalcre));
									examEgineDAO.putScore(catwo,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), histcode)){
									
									catwo.setSubjectUuid(sub.getUuid()); 
									catwo.setCatTwo(Double.parseDouble(finalhst));
									examEgineDAO.putScore(catwo,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}





							}else if(StringUtils.equalsIgnoreCase(exam, "et")){

								finaleng = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(engscorestr)/Double.parseDouble(engscorestroutof))*70)));
								finalkis = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(kisscorestr)/Double.parseDouble(kisscorestroutof))*70)));
								finalmat = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(mathscorestr)/Double.parseDouble(mathscorestroutof))*70)));
								finalbio = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(bioscorestr)/Double.parseDouble(bioscorestroutof))*70)));
								finalphy = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(physcorestr)/Double.parseDouble(physcorestroutof))*70)));
								finalchm = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(chemscorestr)/Double.parseDouble(chemscorestroutof))*70)));
								finalbs = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(bsscorestr)/Double.parseDouble(bsscorestroutof))*70)));
								finalagr = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(agrscorestr)/Double.parseDouble(agrscorestroutof))*70)));
								finalcmp = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(compscorestr)/Double.parseDouble(compscorestroutof))*70)));
								finalhsc = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(homescscorestr)/Double.parseDouble(homescscorestroutof))*70)));
								finalgeo = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(geoscorestr)/Double.parseDouble(geoscorestroutof))*70)));
								finalcre = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(crescorestr)/Double.parseDouble(crescorestroutof))*70)));
								finalhst = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(histscorestr)/Double.parseDouble(histscorestroutof))*70)));

								EndTerm endterm = new EndTerm();
								endterm.setSchoolAccountUuid(school.getUuid());
								endterm.setTeacherUuid(stffID);
								endterm.setClassRoomUuid(clssRoom.getUuid()); 
								endterm.setClassesUuid(classesuuid);
								endterm.setStudentUuid(studentuuid);
								endterm.setTerm(sysConfig.getTerm());
								endterm.setYear(sysConfig.getYear()); 

								if(StringUtils.equals(sub.getSubjectCode(), engscode)){

									endterm.setSubjectUuid(sub.getUuid()); 
									endterm.setEndTerm(Double.parseDouble(finaleng));
									examEgineDAO.putScore(endterm,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), kisscode)){
									//System.out.println(sub.getSubjectCode() + " = "+ finalkis);
									endterm.setSubjectUuid(sub.getUuid()); 
									endterm.setEndTerm(Double.parseDouble(finalkis));
									examEgineDAO.putScore(endterm,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), mathcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalmat);
									endterm.setSubjectUuid(sub.getUuid()); 
									endterm.setEndTerm(Double.parseDouble(finalmat));
									examEgineDAO.putScore(endterm,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), phycode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalphy);
									endterm.setSubjectUuid(sub.getUuid()); 
									endterm.setEndTerm(Double.parseDouble(finalphy));
									examEgineDAO.putScore(endterm,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), biocode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalbio);
									endterm.setSubjectUuid(sub.getUuid()); 
									endterm.setEndTerm(Double.parseDouble(finalbio));
									examEgineDAO.putScore(endterm,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), chemcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalchm);
									endterm.setSubjectUuid(sub.getUuid()); 
									endterm.setEndTerm(Double.parseDouble(finalchm));
									examEgineDAO.putScore(endterm,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), bscode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalbs);
									endterm.setSubjectUuid(sub.getUuid()); 
									endterm.setEndTerm(Double.parseDouble(finalbs));
									examEgineDAO.putScore(endterm,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), agrcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalagr);
									endterm.setSubjectUuid(sub.getUuid()); 
									endterm.setEndTerm(Double.parseDouble(finalagr));
									examEgineDAO.putScore(endterm,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), compcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalcmp);
									endterm.setSubjectUuid(sub.getUuid()); 
									endterm.setEndTerm(Double.parseDouble(finalcmp));
									examEgineDAO.putScore(endterm,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), hsccode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalhsc);
									endterm.setSubjectUuid(sub.getUuid()); 
									endterm.setEndTerm(Double.parseDouble(finalhsc));
									examEgineDAO.putScore(endterm,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), geocode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalgeo);
									endterm.setSubjectUuid(sub.getUuid()); 
									endterm.setEndTerm(Double.parseDouble(finalgeo));
									examEgineDAO.putScore(endterm,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), crecode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalcre);
									endterm.setSubjectUuid(sub.getUuid()); 
									endterm.setEndTerm(Double.parseDouble(finalcre));
									examEgineDAO.putScore(endterm,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), histcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalhst);
									endterm.setSubjectUuid(sub.getUuid()); 
									endterm.setEndTerm(Double.parseDouble(finalhst));
									examEgineDAO.putScore(endterm,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}


							}else if(StringUtils.equalsIgnoreCase(exam, "p1")){

								finaleng = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(engscorestr)/Double.parseDouble(engscorestroutof))*60)));
								finalkis = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(kisscorestr)/Double.parseDouble(kisscorestroutof))*60)));
								finalmat = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(mathscorestr)/Double.parseDouble(mathscorestroutof))*100)));
								finalbio = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(bioscorestr)/Double.parseDouble(bioscorestroutof))*80)));
								finalphy = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(physcorestr)/Double.parseDouble(physcorestroutof))*80)));
								finalchm = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(chemscorestr)/Double.parseDouble(chemscorestroutof))*80)));
								finalbs = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(bsscorestr)/Double.parseDouble(bsscorestroutof))*100)));
								finalagr = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(agrscorestr)/Double.parseDouble(agrscorestroutof))*80)));
								finalcmp = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(compscorestr)/Double.parseDouble(compscorestroutof))*80)));
								finalhsc = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(homescscorestr)/Double.parseDouble(homescscorestroutof))*80)));
								finalgeo = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(geoscorestr)/Double.parseDouble(geoscorestroutof))*100)));
								finalcre = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(crescorestr)/Double.parseDouble(crescorestroutof))*100)));
								finalhst = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(histscorestr)/Double.parseDouble(histscorestroutof))*100)));

								PaperOne p1 = new PaperOne();
								p1.setSchoolAccountUuid(school.getUuid());
								p1.setTeacherUuid(stffID);
								p1.setClassRoomUuid(clssRoom.getUuid()); 
								p1.setClassesUuid(classesuuid);
								p1.setStudentUuid(studentuuid);
								p1.setTerm(sysConfig.getTerm());
								p1.setYear(sysConfig.getYear()); 

								if(StringUtils.equals(sub.getSubjectCode(), engscode)){

									p1.setSubjectUuid(sub.getUuid()); 
									p1.setPaperOne(Double.parseDouble(finaleng));
									examEgineDAO.putScore(p1,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), kisscode)){
									//System.out.println(sub.getSubjectCode() + " = "+ finalkis);
									p1.setSubjectUuid(sub.getUuid()); 
									p1.setPaperOne(Double.parseDouble(finalkis));
									examEgineDAO.putScore(p1,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), mathcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalmat);
									p1.setSubjectUuid(sub.getUuid()); 
									p1.setPaperOne(Double.parseDouble(finalmat));
									examEgineDAO.putScore(p1,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), phycode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalphy);
									p1.setSubjectUuid(sub.getUuid()); 
									p1.setPaperOne(Double.parseDouble(finalphy));
									examEgineDAO.putScore(p1,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), biocode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalbio);
									p1.setSubjectUuid(sub.getUuid()); 
									p1.setPaperOne(Double.parseDouble(finalbio));
									examEgineDAO.putScore(p1,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), chemcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalchm);
									p1.setSubjectUuid(sub.getUuid()); 
									p1.setPaperOne(Double.parseDouble(finalchm));
									examEgineDAO.putScore(p1,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), bscode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalbs);
									p1.setSubjectUuid(sub.getUuid()); 
									p1.setPaperOne(Double.parseDouble(finalbs));
									examEgineDAO.putScore(p1,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), agrcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalagr);
									p1.setSubjectUuid(sub.getUuid()); 
									p1.setPaperOne(Double.parseDouble(finalagr));
									examEgineDAO.putScore(p1,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), compcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalcmp);
									p1.setSubjectUuid(sub.getUuid()); 
									p1.setPaperOne(Double.parseDouble(finalcmp));
									examEgineDAO.putScore(p1,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), hsccode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalhsc);
									p1.setSubjectUuid(sub.getUuid()); 
									p1.setPaperOne(Double.parseDouble(finalhsc));
									examEgineDAO.putScore(p1,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), geocode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalgeo);
									p1.setSubjectUuid(sub.getUuid()); 
									p1.setPaperOne(Double.parseDouble(finalgeo));
									examEgineDAO.putScore(p1,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), crecode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalcre);
									p1.setSubjectUuid(sub.getUuid()); 
									p1.setPaperOne(Double.parseDouble(finalcre));
									examEgineDAO.putScore(p1,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), histcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalhst);
									p1.setSubjectUuid(sub.getUuid()); 
									p1.setPaperOne(Double.parseDouble(finalhst));
									examEgineDAO.putScore(p1,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	
								}

							}else if(StringUtils.equalsIgnoreCase(exam, "p2")){
								//System.out.println("eng " + engscorestr + "  out of " + engscorestroutof);
								finaleng = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(engscorestr)/Double.parseDouble(engscorestroutof))*80)));
								finalkis = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(kisscorestr)/Double.parseDouble(kisscorestroutof))*80)));
								finalmat = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(mathscorestr)/Double.parseDouble(mathscorestroutof))*100)));
								finalbio = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(bioscorestr)/Double.parseDouble(bioscorestroutof))*80)));
								finalphy = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(physcorestr)/Double.parseDouble(physcorestroutof))*80)));
								finalchm = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(chemscorestr)/Double.parseDouble(chemscorestroutof))*80)));
								finalbs = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(bsscorestr)/Double.parseDouble(bsscorestroutof))*100)));
								finalagr = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(agrscorestr)/Double.parseDouble(agrscorestroutof))*80)));
								finalcmp = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(compscorestr)/Double.parseDouble(compscorestroutof))*80)));
								finalhsc = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(homescscorestr)/Double.parseDouble(homescscorestroutof))*80)));
								finalgeo = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(geoscorestr)/Double.parseDouble(geoscorestroutof))*100)));
								finalcre = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(crescorestr)/Double.parseDouble(crescorestroutof))*100)));
								finalhst = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(histscorestr)/Double.parseDouble(histscorestroutof))*100)));

								PaperTwo p2 = new PaperTwo();
								p2.setSchoolAccountUuid(school.getUuid());
								p2.setTeacherUuid(stffID);
								p2.setClassRoomUuid(clssRoom.getUuid()); 
								p2.setClassesUuid(classesuuid);
								p2.setStudentUuid(studentuuid);
								p2.setTerm(sysConfig.getTerm());
								p2.setYear(sysConfig.getYear()); 

								if(StringUtils.equals(sub.getSubjectCode(), engscode)){

									p2.setSubjectUuid(sub.getUuid()); 
									p2.setPaperTwo(Double.parseDouble(finaleng));
									examEgineDAO.putScore(p2,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), kisscode)){
									//System.out.println(sub.getSubjectCode() + " = "+ finalkis);
									p2.setSubjectUuid(sub.getUuid()); 
									p2.setPaperTwo(Double.parseDouble(finalkis));
									examEgineDAO.putScore(p2,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), mathcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalmat);
									p2.setSubjectUuid(sub.getUuid()); 
									p2.setPaperTwo(Double.parseDouble(finalmat));
									examEgineDAO.putScore(p2,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), phycode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalphy);
									p2.setSubjectUuid(sub.getUuid()); 
									p2.setPaperTwo(Double.parseDouble(finalphy));
									examEgineDAO.putScore(p2,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), biocode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalbio);
									p2.setSubjectUuid(sub.getUuid()); 
									p2.setPaperTwo(Double.parseDouble(finalbio));
									examEgineDAO.putScore(p2,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), chemcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalchm);
									p2.setSubjectUuid(sub.getUuid()); 
									p2.setPaperTwo(Double.parseDouble(finalchm));
									examEgineDAO.putScore(p2,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), bscode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalbs);
									p2.setSubjectUuid(sub.getUuid()); 
									p2.setPaperTwo(Double.parseDouble(finalbs));
									examEgineDAO.putScore(p2,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), agrcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalagr);
									p2.setSubjectUuid(sub.getUuid()); 
									p2.setPaperTwo(Double.parseDouble(finalagr));
									examEgineDAO.putScore(p2,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), compcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalcmp);
									p2.setSubjectUuid(sub.getUuid()); 
									p2.setPaperTwo(Double.parseDouble(finalcmp));
									examEgineDAO.putScore(p2,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), hsccode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalhsc);
									p2.setSubjectUuid(sub.getUuid()); 
									p2.setPaperTwo(Double.parseDouble(finalhsc));
									examEgineDAO.putScore(p2,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), geocode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalgeo);
									p2.setSubjectUuid(sub.getUuid()); 
									p2.setPaperTwo(Double.parseDouble(finalgeo));
									examEgineDAO.putScore(p2,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), crecode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalcre);
									p2.setSubjectUuid(sub.getUuid()); 
									p2.setPaperTwo(Double.parseDouble(finalcre));
									examEgineDAO.putScore(p2,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), histcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalhst);
									p2.setSubjectUuid(sub.getUuid()); 
									p2.setPaperTwo(Double.parseDouble(finalhst));
									examEgineDAO.putScore(p2,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}

							}else if(StringUtils.equalsIgnoreCase(exam, "p3")){

								finaleng = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(engscorestr)/Double.parseDouble(engscorestroutof))*60)));
								finalkis = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(kisscorestr)/Double.parseDouble(kisscorestroutof))*60)));
								
								finalbio = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(bioscorestr)/Double.parseDouble(bioscorestroutof))*40)));
								finalphy = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(physcorestr)/Double.parseDouble(physcorestroutof))*40)));
								finalchm = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(chemscorestr)/Double.parseDouble(chemscorestroutof))*40)));
								
								finalagr = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(agrscorestr)/Double.parseDouble(agrscorestroutof))*40)));
								finalcmp = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(compscorestr)/Double.parseDouble(compscorestroutof))*40)));
								finalhsc = rf2.format(Double.parseDouble(rf.format((Double.parseDouble(homescscorestr)/Double.parseDouble(homescscorestroutof))*40)));
								//System.out.println("eng " + finaleng + " kis " + finalkis);

								PaperThree p3 = new PaperThree();
								p3.setSchoolAccountUuid(school.getUuid());
								p3.setTeacherUuid(stffID);
								p3.setClassRoomUuid(clssRoom.getUuid()); 
								p3.setClassesUuid(classesuuid);
								p3.setStudentUuid(studentuuid);
								p3.setTerm(sysConfig.getTerm());
								p3.setYear(sysConfig.getYear()); 

								if(StringUtils.equals(sub.getSubjectCode(), engscode)){

									p3.setSubjectUuid(sub.getUuid()); 
									p3.setPaperThree(Double.parseDouble(finaleng));
									examEgineDAO.putScore(p3,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), kisscode)){
									//System.out.println(sub.getSubjectCode() + " = "+ finalkis);
									p3.setSubjectUuid(sub.getUuid()); 
									p3.setPaperThree(Double.parseDouble(finalkis));
									examEgineDAO.putScore(p3,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), phycode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalphy);
									p3.setSubjectUuid(sub.getUuid()); 
									p3.setPaperThree(Double.parseDouble(finalphy));
									examEgineDAO.putScore(p3,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), biocode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalbio);
									p3.setSubjectUuid(sub.getUuid()); 
									p3.setPaperThree(Double.parseDouble(finalbio));
									examEgineDAO.putScore(p3,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), chemcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalchm);
									p3.setSubjectUuid(sub.getUuid()); 
									p3.setPaperThree(Double.parseDouble(finalchm));
									examEgineDAO.putScore(p3,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), agrcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalagr);
									p3.setSubjectUuid(sub.getUuid()); 
									p3.setPaperThree(Double.parseDouble(finalagr));
									examEgineDAO.putScore(p3,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), compcode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalcmp);
									p3.setSubjectUuid(sub.getUuid()); 
									p3.setPaperThree(Double.parseDouble(finalcmp));
									examEgineDAO.putScore(p3,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}else if(StringUtils.equals(sub.getSubjectCode(), hsccode)){
									//System.out.println(sub.getSubjectCode() + " = " + finalhsc);
									p3.setSubjectUuid(sub.getUuid()); 
									p3.setPaperThree(Double.parseDouble(finalhsc));
									examEgineDAO.putScore(p3,school.getUuid(),clssRoom.getUuid(),studentuuid,sub.getUuid(),sysConfig.getTerm(),sysConfig.getYear());	


								}
							}

						}

					
						
					}// end if(!StringUtils.isBlank(studentuuid)){ 


					//count++;
				}
			}


			catch (Exception e){

			}finally {
				myInput.close();

			}
		}
	}
}
