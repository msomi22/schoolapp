/**
 * 
 */
package ke.co.qubintel.school.server.api.rest;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import io.swagger.annotations.Api;

/**
 * http://localhost:8080/school/webapi/reports/account
 * 
 * @author peter
 *
 */

/**
 * @author peter
 *
 */
@Path("/reports") 
@Api(value = "/reports") 
@Produces("application/pdf")
public class ReportResource {

	
	@GET
	@Path("/account")  
	@Produces("application/pdf")
	public Response generatePDFReports() {
		
		String FILE_PATH = "/home/peter/Downloads/Reports/test.pdf";
		File file = new File(FILE_PATH);
		
		String fileName = "FORM_1_N_CAT_1.pdf";
		
		ByteArrayOutputStream baosPDF = getDynamicPDF();

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename="+fileName); 
		
	
		return response.build();
	}

	private ByteArrayOutputStream getDynamicPDF() {
		Document doc = new Document();
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		PdfWriter docWriter = null;
		try {
			
			
			
			docWriter = PdfWriter.getInstance(doc, baosPDF);
			
			doc.open();
			
			doc.add(new Paragraph(
				    "This document was created by a class named: "
				    + this.getClass().getName()));

			doc.add(new Paragraph(
				    "This document was created on "
				    + new java.util.Date()));
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		doc.close();
		docWriter.close();
		return baosPDF;
	}
	
	
	
	

}
