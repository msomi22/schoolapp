package com.yahoo.petermwenda83.server.servlet.student;


import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javassist.bytecode.analysis.Util;


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
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		OutputStream out = response.getOutputStream();
		response.setContentType("application/json;charset=UTF-8");
		
		
		Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setPrettyPrinting().serializeNulls().create();

		//process the upload and return json response
		out.write(gson.toJson(cropAndUploadImage(request)).getBytes());
		out.flush();
		out.close();
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	private JsonElement cropAndUploadImage(HttpServletRequest request) {
		
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
        	String data="none";
        	File uploadedFile = null;
        	String filePath= DATA_DIRECTORY;
        	String b64= "";
            List<FileItem> items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField()) {
                	fileName= new File(item.getName()).getName();
                	fileName= StringUtils.replace(fileName, ".jpeg", "");
                	fileName= StringUtils.replace(fileName, ".jpg", "");
                	fileName= StringUtils.replace(fileName, ".png", "");
                	fileName= StringUtils.replace(fileName, ".gif", "");
                	fileName= fileName+".png";
                	filePath = DATA_DIRECTORY;
                    uploadedFile = new File(filePath,fileName);
                    System.out.println(filePath);
                    
                   
                    // saves the file to upload directory
                    item.write(uploadedFile);
                }
               if( StringUtils.equalsIgnoreCase(item.getFieldName(),"avatar_data")) {
                	
                	data= item.getString();
                	JSONObject imageData= new JSONObject(data);
                	
                	 //cropp the image submitted
                	
                	
                	
                     Image src = ImageIO.read(uploadedFile);

                      int x = imageData.getInt("x"), y = imageData.getInt("y"), w = imageData.getInt("width"), h = imageData.getInt("height");

                     
                      //rotate image
                      double rotationRequired = Math.toRadians (imageData.getDouble("rotate"));
                
                      AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired,w , h);
                      AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                      
                      BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                      
                      
                      dst.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
                      
                      dst=op.filter(dst, null);

                      //ovorride the upload
                      ImageIO.write(dst, "png", new File(filePath,fileName));
                      
                      //return the cropped image in base64 format
                      
                      
                      ByteArrayOutputStream baos = new ByteArrayOutputStream();
                      ImageIO.write( dst, "png", baos );
                      baos.flush();
                      byte[] imageInByteArray = baos.toByteArray();
                      baos.close();                                   
                      b64  = DatatypeConverter.printBase64Binary(imageInByteArray);
                	
                	
                	
                }
            }
           
    		
            
            
            jsonObject.addProperty("state", 200);
    		jsonObject.addProperty("message", "Success");
    		jsonObject.addProperty("result", fileName);
    		jsonObject.addProperty("Data", data);
    		jsonObject.addProperty("newImage", "data:image/jpg;base64,"+b64);

           

        } catch (FileUploadException ex) {
        	
        	jsonObject.addProperty("state", 500);
     		jsonObject.addProperty("message", "Directory error"+ ex.getMessage());
     		jsonObject.addProperty("result", "images/user.png");
           
        } catch (Exception ex) {
        	
        	 jsonObject.addProperty("state", 500);
     		jsonObject.addProperty("message", "Error : "+ex.getMessage());
     		jsonObject.addProperty("result", "images/user.png");
           // throw new ServletException(ex);
        }

	
		return jsonObject;
		
		
	}
	
	

}
