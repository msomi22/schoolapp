/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.result;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.junit.Test;

/**
 * @author peter
 *
 */
public class TestReportFormF1 {
	
	final String CGI_URL = "http://localhost:8080/School/school/reportFormF3_4_c1_c2_et";

	final String classID = "46398A47-93F2-4591-B36F-1C28B03CC2F3";
	final String staffid = "F49DB775-4952-4915-B978-9D9F3E36D6E9";

	

	/**
	 * Test method for {@link com.yahoo.petermwenda83.server.servlet.result.ReportFormF1#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 */
	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() { 
	    try {
			System.out.println("response is :\n" + 
			getResponse(CGI_URL + "?" + "classID=" + URLEncoder.encode(classID,"UTF-8") + "&" +"staffid=" + URLEncoder.encode(staffid,"UTF-8") )); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			fail("Test to get result for classID " + classID + " and staffid " + staffid);
        	
		}
	    //file:///home/peter/test/form.html?phone=0718953974&email=mwendapeter72%40gmail.com&major=computer+science&units=7&submit=Register
}



	private String getResponse(String urlStr) {		
        URLConnection conn;
        URL url;
        BufferedReader reader;
		String line;
		StringBuffer stringBuff = new StringBuffer();
		
		try {            
            url = new URL(urlStr);
            conn = url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true); 
            
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
            while( (line = reader.readLine()) != null) {
            	stringBuff.append(line);
            }
            
            reader.close();
            
        } catch(MalformedURLException e) {
            System.err.println("MalformedURLException exception");
            e.printStackTrace();
            
        } catch(IOException e) {
            System.err.println("IOException exception");
            e.printStackTrace();
        }
        
		return stringBuff.toString();
	}


}
