/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.excel;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/** 
 * @author peter
 *
 */
public class ImportStudent extends HttpServlet{

	
	public final static String UPLOAD_FEEDBACK = "UploadFeedback";
	public final static String UPLOAD_SUCCESS = "Student's successfully uploaded.";

	private ImportUtil importUtil;
	private Logger logger = Logger.getLogger(this.getClass());

	public String USER = "";
	public String UPLOAD_DIR = "";
	
	private static StudentDAO studentDAO;
	private static PrimaryDAO primaryDAO;
	private static StreamDAO streamDAO;
	private static SysConfigDAO sysConfigDAO;

	/**
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		File repository = FileUtils.getTempDirectory();
		factory.setRepository(repository);

		USER = System.getProperty("user.name");
		UPLOAD_DIR =  "/home/"+USER+"/school/exams"; 

		importUtil = new ImportUtil();
		
		studentDAO = StudentDAO.getInstance();
		primaryDAO = PrimaryDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();

	}



	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		File uploadedFile = null;
		HttpSession session = request.getSession(false);


		String accounId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID);
		String user = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set up where the files will be stored on disk
		File repository = new File(UPLOAD_DIR);
		FileUtils.forceMkdir(repository); 
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// Parse the request  
		
		JsonObject jsonObject = new JsonObject();
		
		OutputStream out = response.getOutputStream();
		response.setContentType("application/json;charset=UTF-8");
		
		Gson gson = new GsonBuilder().disableHtmlEscaping()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setPrettyPrinting().serializeNulls().create();

		try {

			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();

			FileItem item;
			
			if(!iter.hasNext()) {
				
				jsonObject.addProperty("responseMessage", "Upload failed, no data supplied");
			}

			while (iter.hasNext()) {
				item = iter.next();

				if (!item.isFormField()) {
					if(item!=null){
						uploadedFile = processUploadedFiles(item,user);

						String feedback = importUtil.processUploadedFiles(uploadedFile,accounId, studentDAO, streamDAO);
						
						
						jsonObject.addProperty("responseMessage", feedback);

						session.setAttribute(UPLOAD_FEEDBACK,"<p class='error'>"+feedback+"<p>");

						// Process the file into the database if it is ok
						if(StringUtils.equals(feedback, UPLOAD_SUCCESS)) {
							importUtil.saveStudent(uploadedFile, accounId, studentDAO, primaryDAO, streamDAO,sysConfigDAO);
						}

					}	
				} 
			}// end 'while (iter.hasNext())'

		} catch (FileUploadException | EncryptedDocumentException | InvalidFormatException e) {
			logger.error("FileUploadException while getting File Items.");
			logger.error(e);
		} 


	//	response.sendRedirect("***.jsp");
	//	return;
		
		out.write(gson.toJson(jsonObject).getBytes());
		
		//out.write(jsonObject);
		
		
		out.flush();
		out.close();

	}



	/**
	 * @param item
	 * @return the file handle
	 */
	private File processUploadedFiles(FileItem item, String user) {
		// A specific folder in the system
		String folder = UPLOAD_DIR + File.separator + user;
		File file = null;

		try {
			FileUtils.forceMkdir(new File(folder));
			file = new File(folder + File.separator + item.getName());
			item.write(file); 

		} catch (IOException e) {
			logger.error("IOException while processUploadedFile: " + item.getName());
			logger.error(e);

		} catch (Exception e) {
			logger.error("Exception while processUploadedFile: " + item.getName());
			logger.error(e);
		} 
		//System.out.println("Saved File = "+file+"\n");
		return file;
	}

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4474214849472252006L;

}
