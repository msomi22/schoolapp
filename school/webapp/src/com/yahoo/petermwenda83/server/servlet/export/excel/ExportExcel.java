package com.yahoo.petermwenda83.server.servlet.export.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.exam.ExamConfig;
import com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount;
import com.yahoo.petermwenda83.bean.student.House;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.StudentHouse;
import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO;
import com.yahoo.petermwenda83.persistence.student.StudentHouseDAO;
import com.yahoo.petermwenda83.persistence.student.HouseDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.cache.CacheVariables;
import com.yahoo.petermwenda83.server.session.SessionConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class ExportExcel extends HttpServlet{

    private final String SPREADSHEET_NAME = ".xlsx";
    private static final long serialVersionUID = 3896751907947782599L;
    private static int pageno;
    
    HashMap<String, String> roomHash = new HashMap<String, String>();
    HashMap<String, String> studentHouseHash = new HashMap<String, String>();
    HashMap<String, String> houseHash = new HashMap<String, String>();

    private Cache schoolaccountCache;
    ExamConfig examConfig;
   

    private static StudentDAO studentDAO;
    private static StudentHouseDAO studentHouseDAO;
    private static RoomDAO roomDAO;
    private static ExamConfigDAO examConfigDAO;
    private static PrimaryDAO primaryDAO;
    private static HouseDAO houseDAO;
    private String classroomuuid = "";
    String schoolusername = "";
    private ServletOutputStream out;
    
    
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
	    
	    studentDAO = StudentDAO.getInstance();
	    examConfigDAO = ExamConfigDAO.getInstance();
	    studentHouseDAO = StudentHouseDAO.getInstance();
	    primaryDAO = PrimaryDAO.getInstance();
	    roomDAO = RoomDAO.getInstance();
	    houseDAO = HouseDAO.getInstance();
	    studentHouseDAO = StudentHouseDAO.getInstance();
	    
    }

    /**    
     *
     * @param request
     * @param response
     * @throws ServletException, IOException
     * @throws java.io.IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {

        out = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Cache-Control", "cache, must-revalidate");
        response.setHeader("Pragma", "public");

        HttpSession session = request.getSession(false); 
        
        classroomuuid = StringUtils.trimToEmpty(request.getParameter("classroomuuid"));
       
        SchoolAccount school = new SchoolAccount();
	
		   if(session !=null){
		   schoolusername = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
		  
		      }
		   net.sf.ehcache.Element element;
		   
		   element = schoolaccountCache.get(schoolusername);
		   if(element !=null){
		   school = (SchoolAccount) element.getObjectValue();
	 
		   }
               
		    examConfig = examConfigDAO.getExamConfig(school.getUuid());
		    
		   
		    List<Student> studentList = new ArrayList<>();
	        studentList = studentDAO.getAllStudents(school.getUuid(), classroomuuid);
	        
	        List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
	          classroomList = roomDAO.getAllRooms(school.getUuid()); 
	           for(ClassRoom c : classroomList){
	                roomHash.put(c.getUuid() , c.getRoomName());
	          }
	           
	           List<StudentHouse> studentHouseList = new ArrayList<StudentHouse>(); 
	               studentHouseList = studentHouseDAO.getHouseList();
		           for(StudentHouse sh : studentHouseList){
		        	   studentHouseHash.put(sh.getStudentUuid() , sh.getHouseUuid());
		          }
		           
		        List<House> houseList = new ArrayList<House>(); 
		        houseList = houseDAO.getHouseList(school.getUuid());
			           for(House h : houseList){
			        	   houseHash.put(h.getUuid() , h.getHouseName()); 
			     }
	           
            String fileName = new StringBuffer(StringUtils.trimToEmpty("StudentsList"))
                .append("_")
                .append(roomHash.get(classroomuuid))
                .append(SPREADSHEET_NAME)
                .toString();

        response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");     
        createExcelSheets(studentList,school);
       }
    
    
    
    /**
     * Returns MS Excel file of the data specified for exporting.
     * @param school 
     * @param List<IncomingLog>
     * Method create excelSheets and sends them
     ****/    
    public void createExcelSheets(List<Student>studentList, SchoolAccount school) throws IOException{    	
    	 
        XSSFWorkbook xf = new XSSFWorkbook();
        XSSFCreationHelper ch =xf.getCreationHelper();
      
        XSSFSheet s =xf.createSheet();
        s.setColumnWidth(0, 1000); 
        s.setColumnWidth(1, 2500); 
        s.setColumnWidth(2, 2500); 
        s.setColumnWidth(3, 2500); 
        s.setColumnWidth(4, 2500); 
        s.setColumnWidth(5, 1500); 
        s.setColumnWidth(6, 3000); 
        s.setColumnWidth(7, 4000); 
        s.setColumnWidth(8, 3000); 
        s.setColumnWidth(9, 1500); 
        s.setColumnWidth(10,1500);
       
        CellStyle style = xf.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        //style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
       // style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        
        CellStyle style2 = xf.createCellStyle();
        style2.setAlignment(CellStyle.ALIGN_LEFT);
       // style2.setFillForegroundColor(IndexedColors.AQUA.getIndex()); 
       // style2.setFillPattern(CellStyle.DIAMONDS); 
        
        XSSFFont font = xf.createFont();
        font.setFontName(XSSFFont.DEFAULT_FONT_NAME); 
        font.setFontHeightInPoints((short)12);
        //font.setColor(IndexedColors.MAROON.getIndex()); 
        style.setFont(font); 
        
        XSSFRow r0 = s.createRow(0);
        XSSFCell cell = r0.createCell((short) 0);
	    cell.setCellValue(ch.createRichTextString(school.getSchoolName()+" : "+roomHash.get(classroomuuid)+" Students List,  TERM " + examConfig.getTerm()+"  "+ examConfig.getYear()));
	    cell.setCellStyle(style);
	    
	    s.addMergedRegion(new CellRangeAddress(0,0,10,0));//row from,row to,col from, col to
       
        //create the first row
        XSSFRow r1 = s.createRow(1);
  	            XSSFCell c11 = r1.createCell(0);
  	                  c11.setCellValue(ch.createRichTextString("*")); 
  	                  c11.setCellStyle(style2);
  	                  
	            XSSFCell c12 = r1.createCell(1);
	                  c12.setCellValue(ch.createRichTextString("Adm No"));
	                  c12.setCellStyle(style2);
	                  
	            XSSFCell c13 = r1.createCell(2);
	                  c13.setCellValue(ch.createRichTextString("First name"));
	                  c13.setCellStyle(style2);
	                  
	            XSSFCell c14 = r1.createCell(3);
	                  c14.setCellValue(ch.createRichTextString("Middle name"));
	                  c14.setCellStyle(style2);
	                  
	            XSSFCell c15 = r1.createCell(4);
	                  c15.setCellValue(ch.createRichTextString("Last name"));
	                  c15.setCellStyle(style2);
	                  
	            XSSFCell c16 = r1.createCell(5);
	                  c16.setCellValue(ch.createRichTextString("Gender"));
	                  c16.setCellStyle(style2);
	                  
	            XSSFCell c17 = r1.createCell(6);
	                  c17.setCellValue(ch.createRichTextString("House"));
	                  c17.setCellStyle(style2);
	                  
	            XSSFCell c18 = r1.createCell(7);
	                  c18.setCellValue(ch.createRichTextString("Primary"));
	                  c18.setCellStyle(style2);
	                  
	            XSSFCell c19 = r1.createCell(8);
	                  c19.setCellValue(ch.createRichTextString("Index"));
	                  c19.setCellStyle(style2);
	                  
	            XSSFCell c20 = r1.createCell(9);
	                  c20.setCellValue(ch.createRichTextString("Year"));
	                  c20.setCellStyle(style2);
	                  
	            XSSFCell c21 = r1.createCell(10);
	                  c21.setCellValue(ch.createRichTextString("Mark"));
	                  c21.setCellStyle(style2);
	            
        
        int i=2;
        int count = 1;
         
        String formatedFirstname = "";
        String formatedLastname = "";
        String formatedSurname = "";
        //create other rows
        if(studentList != null){
          for(Student stu :studentList){
        	 final String STATUS_ACTIVE = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
        	  if(StringUtils.equals(stu.getStatusUuid(), STATUS_ACTIVE)){
        		  //System.out.println("status="+stu.getStatusUuid());
        	  String schoolname = "";
        	  String index = "";
        	  String year = "";
        	  String mark = "";
        	  String room = "";
        	  StudentPrimary primary = primaryDAO.getPrimary(stu.getUuid());
        	  if(primary !=null){
        		  schoolname = primary.getSchoolname(); 
        		  index = primary.getIndex();
        		  year = primary.getKcpeyear();
        		  mark = primary.getKcpemark();
        	  }
        	 
        	  if(houseHash.get(studentHouseHash.get(stu.getUuid()))!=null){
        		  room = houseHash.get(studentHouseHash.get(stu.getUuid()));
        	  }
        	  
        	  
				formatedFirstname =  StringUtils.capitalize(stu.getFirstname().toLowerCase());
				formatedLastname = StringUtils.capitalize(stu.getLastname().toLowerCase());
				formatedSurname = StringUtils.capitalize(stu.getSurname().toLowerCase());
        	  
        	  
        	  
        	  XSSFRow r = s.createRow(i);
        	  //row number
        	  XSSFCell c1 = r.createCell(0);
        	      c1.setCellValue(count+pageno);
        	      
        	    //get message  
        	  XSSFCell c2 = r.createCell(1);        	
        	      c2.setCellValue(ch.createRichTextString(stu.getAdmno()));
        	      
        	 //get phone numbers
        	      XSSFCell c3 = r.createCell(2);
        	     
        		  //else{ 
        			 c3.setCellValue(ch.createRichTextString(formatedFirstname));        		   	     
        	     
        	      
        	   //get destination   
        	  XSSFCell c4 = r.createCell(3);
        	      c4.setCellValue(ch.createRichTextString(formatedLastname));
        	      
        	  //get network name    
        	   XSSFCell c5 = r.createCell(4);
        	      c5.setCellValue(ch.createRichTextString(formatedSurname)); 
        	   
        	      //get date 
        	  XSSFCell c6 = r.createCell(5);    
        	  String gender = " ";
        	  if(StringUtils.equalsIgnoreCase(stu.getGender(), "FEMALE")) {
        		  gender = "F";
        	  }else{
        		  gender = "M";
        	  }
        	     c6.setCellValue(ch.createRichTextString(gender));
        	              	      
        	      
        	  XSSFCell c7 = r.createCell(6); 
        	       c7.setCellValue(ch.createRichTextString(room)); 
        	       
        	  XSSFCell c8 = r.createCell(7); 
        	       c8.setCellValue(ch.createRichTextString(schoolname)); 
        	       
        	  XSSFCell c9 = r.createCell(8); 
        	       c9.setCellValue(ch.createRichTextString(index)); 
        	       
        	  XSSFCell c10 = r.createCell(9); 
        	       c10.setCellValue(ch.createRichTextString(year)); 
        	       
        	  XSSFCell c111 = r.createCell(10); 
        	       c111.setCellValue(ch.createRichTextString(mark)); 
        	       
        	       
        	       
        	  i++;
        	  count++;      	  
          }//end if status
    }//end for
  }//end if
          xf.write(out);
          out.flush();          
          out.close();
    }
    
    
    
   
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException, IOException
     * @throws java.io.IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
   
}
