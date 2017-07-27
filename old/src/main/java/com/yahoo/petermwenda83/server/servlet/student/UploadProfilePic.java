package com.yahoo.petermwenda83.server.servlet.student;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class UploadProfilePic
 */
@WebServlet("/UploadProfilePic")
@MultipartConfig
public class UploadProfilePic extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USER_SYSTEM = System.getProperty("user.name");
	private static final String DATA_DIRECTORY = "/home/"+USER_SYSTEM+"/school/uploads/";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadProfilePic() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		OutputStream out = response.getOutputStream();
		response.setContentType("application/json;charset=UTF-8");
		
		
		Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setPrettyPrinting().serializeNulls().create();

		
		JsonObject jsonObject = new JsonObject();

		
		// Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Sets the size threshold beyond which files are written directly to
        // disk.
        factory.setSizeThreshold(MAX_MEMORY_SIZE);

        // Sets the directory used to temporarily store files that are larger
        // than the configured size threshold. We use temporary directory for
        // java
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

       

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        upload.setSizeMax(MAX_REQUEST_SIZE);

        try {
            // Parse the request
        	String fileName="failed";
            List<FileItem> items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField()) {
                	fileName= new File(item.getName()).getName();
                    String filePath = DATA_DIRECTORY;
                    File uploadedFile = new File(filePath,fileName);
                    System.out.println(filePath);
                    // saves the file to upload directory
                    item.write(uploadedFile);
                }
            }
            
            
            jsonObject.addProperty("state", 200);
    		jsonObject.addProperty("message", "Success");
    		jsonObject.addProperty("result", DATA_DIRECTORY+fileName);

           

        } catch (FileUploadException ex) {
        	
        	jsonObject.addProperty("state", 500);
     		jsonObject.addProperty("message", "Directory error"+ ex.getMessage());
     		jsonObject.addProperty("result", "images/user.png");
           
        } catch (Exception ex) {
        	
        	 jsonObject.addProperty("state", 500);
     		jsonObject.addProperty("message", "Error : "+ex.getMessage());
     		jsonObject.addProperty("result", "images/dcclxxvii.png");
           // throw new ServletException(ex);
        }

		

		

		out.write(gson.toJson(jsonObject).getBytes());
		out.flush();
		out.close();
	}

}
