/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.student.soap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @author peter
 *
 */
public class TestGetStudentAPI {

	/**
	 * 
	 */
	public TestGetStudentAPI() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//DEFINE PARAMETERS.
		String schoolUsername  = "jabbss"; 
		String admNo  = "3388"; 
		String server = "http://localhost:8080/School/school/getStudentAPI";

		try {

			//DEFINE CONNECTION.
			HttpURLConnection   connection = (HttpURLConnection) ( new URL(server).openConnection() );
			connection.setDoOutput       (true);
			connection.setDoInput        (true);
			connection.setRequestMethod  ("POST");
			connection.setRequestProperty("SOAPAction", server);

			//CREATE REQUEST.
			String  xml = "";            
			xml += "<?xml version='1.0'?>                     \n"; 
			xml += "<SOAP-ENV:Envelope>                       \n";            
			xml += "  <SOAP-ENV:Body>                         \n";
			xml += "    <Username type='xsi:string'>          \n"; 
			xml += "      "+schoolUsername+"                  \n"; 
			xml += "    </Username>                           \n";
			xml += "    <admNo type='xsi:string'>             \n"; 
			xml += "      "+admNo+"                           \n"; 
			xml += "    </admNo>                              \n";
			xml += "  </SOAP-ENV:Body>                        \n";
			xml += "</SOAP-ENV:Envelope>                      \n";

			//SEND REQUEST.

			OutputStream        out  = connection.getOutputStream();
			OutputStreamWriter  wout = new OutputStreamWriter(out, "UTF-8");
			wout.write(xml);
			wout.flush();
			out .close();

			//READ RESPONSE.
			InputStream in = connection.getInputStream();
			int c;
			String response = "";
			while ((c = in.read()) != -1) { response += (char) c; }
			//System.out.println(response);
			
			XMLParser xmlParser = new XMLParser();
			Map<String,String> myxml = null; 
			System.out.println("response: " + response); 
			myxml = xmlParser.xmlToMap(response); 
			System.out.println("MAP: " + myxml); 
			System.out.println("studentName: " + myxml.get("studentName"));  
			System.out.println("feeBalance: " + myxml.get("feeBalance"));  

			//EXTRACT RESULT.
			int startTag_name  = response.indexOf("<studentName>");
			int endTag_name    = response.indexOf("</studentName>");
			String name = response.substring(startTag_name,endTag_name).replaceAll("<studentName>","");     
			name = name.trim();

			int startTag_bal  = response.indexOf("<feeBalance>");
			int endTag_bal    = response.indexOf("</feeBalance>");
			String balance = response.substring(startTag_bal,endTag_bal).replaceAll("<feeBalance>","");     
			balance = balance.trim();

			//DISPLAY RESULT.
			//System.out.println("studentName = " + name);
			//System.out.println("balance = " + balance);

			//CLOSE ALL.
			in        .close();
			out       .close();
			connection.disconnect();
		}
		catch (IOException e) { System.out.println(e.toString()); } 
	}

}
