/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.api.rest.StudentService;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiSubject;

/**
 * @author peter
 *
 */
public class ImportUtil {

	private String[] genderArray;
	private List<String> genderList;
	private String[] categoryArray;
	private List<String> categoryList;
	
	private static SubjectDAO subjectDAO;
	
	static {
		subjectDAO = SubjectDAO.getInstance();
	}

	public ImportUtil(){
		genderArray = new String[] {"M", "F", "m", "f"};
		genderList = Arrays.asList(genderArray);
		categoryArray = new String[] {"Day", "Boarder"};
		categoryList = Arrays.asList(categoryArray);
	}


	/**
	 * @param uploadedFile
	 * @param accounId
	 * @param studentDAO
	 * @param streamDAO
	 * @return
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException 
	 */
	public String processUploadedFiles(File uploadedFile, String accounId, StudentDAO studentDAO, StreamDAO streamDAO) throws EncryptedDocumentException, InvalidFormatException, IOException {

		String feedback = ImportStudent.UPLOAD_SUCCESS;

		if(uploadedFile !=null){

			FileInputStream myInput =  new FileInputStream(uploadedFile);

			Workbook myWorkBook = WorkbookFactory.create(myInput);
			XSSFSheet mySheet = (XSSFSheet) myWorkBook.getSheetAt(0);
			int totalRow = mySheet.getLastRowNum();

			try{

				String stream = "";
				String regNo = "";
				String firstName = "";
				String middleName = "";
				String gender = "";
				String kcpe = "";
				String isDay = "";

				int count = 1;
				int totalColumn = 0;
				for(int i=0; i<=totalRow; i++){
					XSSFRow row = mySheet.getRow(i);
					if(row !=null){
						totalColumn = row.getLastCellNum();
					}

					for(int j=0; j<totalColumn; j++){
						if(i==0){
							XSSFCell streamCell = row.getCell((short)0);
							stream = streamCell+""; 

							if(totalColumn >6){
								return ("Invalid Number of comlumns on line \"" + count);
							}

						}else if(i > 1){

							regNo = row.getCell(0)+"";
							firstName =  row.getCell(1)+"";
							middleName =  row.getCell(2)+"";
							gender =  row.getCell(3)+"";
							kcpe =  row.getCell(4)+"";
							isDay = row.getCell(5)+"";

							if (StringUtils.isBlank(regNo) || StringUtils.equalsIgnoreCase(regNo, "null")) {
								return ("Invalid/blank regNo " + regNo + " on line " + count);
							}

							if (StringUtils.isBlank(firstName) || StringUtils.equalsIgnoreCase(firstName, "null")) {
								return ("Invalid/blank firstName " + firstName + " on line " + count);
							}

							if (StringUtils.isBlank(middleName) || StringUtils.equalsIgnoreCase(middleName, "null")) {
								return ("Invalid/blank middleName " + middleName + " on line " + count);
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

							if(!StringUtils.isNumeric(kcpe.replace(".0", "")) ){
								return ("Invalid K.C.P.E marks " + kcpe.replace(".0", "") + " on line " + count);
							}

							if(Integer.parseInt(kcpe.replace(".0", "")) < 100 || Integer.parseInt( kcpe.replace(".0", "")) > 500 ){ 
								return ("Invalid (out of range) K.C.P.E marks " + kcpe.replace(".0", "") + " on line " + count);	
							}

							if(studentDAO.getStudentByregNo(accounId, regNo.replace(".0", "")) != null){
								return ("Student with admission number " + regNo.replace(".0", "") + " on line " + count + " already exist.");
							}

							if(streamDAO.getStreamByDesc(accounId, stream) ==null){
								return ("Class " + stream + " not found."); 
							}
							if(!categoryList.contains(isDay)) {
								return ("Invalid category " + isDay + " on line " + count);
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
	 * @param uploadedFile
	 * @param accounId
	 * @param studentDAO
	 * @param primaryDAO
	 * @param streamDAO
	 * @param sysConfigDAO 
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException 
	 */
	public void saveStudent(File uploadedFile, String accountId, StudentDAO studentDAO, PrimaryDAO primaryDAO,
			StreamDAO streamDAO, SysConfigDAO sysConfigDAO) throws IOException, EncryptedDocumentException, InvalidFormatException {


		if(uploadedFile !=null){

			FileInputStream myInput =  new FileInputStream(uploadedFile);

			Workbook myWorkBook = WorkbookFactory.create(myInput);
			XSSFSheet mySheet = (XSSFSheet) myWorkBook.getSheetAt(0);
			int totalRow = mySheet.getLastRowNum();

			try{

				String stream = "";
				String regNo = "";
				String firstName = "";
				String middleName = "";
				String gender = "";
				String kcpe = "";
				String isDay = "";
				String status = "";
				

				int totalColumn = 0;
				for(int i=0; i<=totalRow; i++){
					XSSFRow row = mySheet.getRow(i);
					
					if(row !=null){
						totalColumn = row.getLastCellNum();
					}

					for(int j=0; j<totalColumn; j++){
						if(i==0){
							XSSFCell streamCell = row.getCell((short)0);
							stream = streamCell+""; 


						}else if(i > 1){

							regNo = row.getCell(0)+"";
							firstName =  row.getCell(1)+"";
							middleName =  row.getCell(2)+"";
							gender =  row.getCell(3)+"";
							kcpe =  row.getCell(4)+"";
							isDay = row.getCell(5)+"";
							
							
							//"Day", "Boarder"
							////boarders = 1, day = 0
							if(StringUtils.equalsIgnoreCase(isDay, "Day")) {
								status = "0";
								
							}else {
								status = "1";
							}
							
							regNo = regNo.replace(".0", "");
							kcpe = kcpe.replace(".0", "");

							
							


						}//end if

						

					}
					
					if(i>1) {//skip the first line (header) 
						/**
						System.out.println("regNo : " + regNo + " , firstName: " + firstName + " , middleName: " + middleName +
								" , gender:" + gender + " , kcpe:" + kcpe + " , isDay: " + isDay);
						
						System.out.println("stream : " + stream);*/
						
						Student student = new Student();
						student.setAccountId(accountId);
						student.setIsActive("1");
						student.setIsAlumni("0");
						student.setIsBoarding(status);
						student.setIsGoKFeeEligibe("0");  
						
						student.setCurrentStream(streamDAO.getStreamByDesc(accountId, stream).getUuid());
						student.setRegStream(streamDAO.getStreamByDesc(accountId, stream).getUuid()); 
						
						String regterm = "";
						String regyear = "";
						if(sysConfigDAO.getSysConfig(accountId) != null) {
							SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);
							regterm = sysConfig.getTerm();
							regyear = sysConfig.getYear();
						}
						
						int finaly = Integer.valueOf(regyear) + 3;
						
						
						student.setRegTerm(regterm);
						student.setFinalTerm(3); 
						student.setFinalYear(finaly); 
						
						student.setRegNo(regNo);
						student.setFirstname(firstName);
						student.setMiddlename(middleName);
						student.setGender(gender);
						
						student.setLastUpdated(new Date().toString()); 
						
						if(studentDAO.putStudent(student)) {
							StudentService studentService = new StudentService();
							subjectDAO.getSubjects(accountId).forEach(subject -> {
								ApiSubject apiSubject = new ApiSubject();
								apiSubject.setAccountId(accountId);
								apiSubject.setStudentId(student.getUuid()); 
								apiSubject.setSubjectId(subject.getUuid());
								studentService.assignSubject(apiSubject);
								
							});
						}
						
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
