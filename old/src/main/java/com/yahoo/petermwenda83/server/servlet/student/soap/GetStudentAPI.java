package com.yahoo.petermwenda83.server.servlet.student.soap;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.servlet.money.StudentBalance;

public class GetStudentAPI extends HttpServlet {

	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static StudentOtherMoniesDAO studentOtherMoniesDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static SysConfigDAO sysConfigDAO;
	private static TermFeeDAO termFeeDAO;


	/**  
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();

		studentOtherMoniesDAO = StudentOtherMoniesDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();

	}

	public void service(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		//GET REQUEST BODY.       
		ServletInputStream inputstream = request.getInputStream();
		int c = 0;
		String xmlstr = "";
		while((c = inputstream.read()) != -1 ){ xmlstr += (char)c; }

		//EXTRACT SCHOOL USERNAME
		int startTag_username = xmlstr.indexOf("type='xsi:string'>");
		int endTag_username   = xmlstr.indexOf("</Username>");

		int startTag_admno = xmlstr.indexOf("<admNo type='xsi:string'>");
		int endTag_admno   = xmlstr.indexOf("</admNo>");

		String username = xmlstr.substring(startTag_username,endTag_username).replaceAll("type='xsi:string'>","");
		username = username.trim(); 

		String admNumber = xmlstr.substring(startTag_admno,endTag_admno).replaceAll("<admNo type='xsi:string'>","");
		admNumber = admNumber.trim(); 

		//get student
		String schoolusername = new String(username).toString();
		String admno = new String(admNumber).toString();

		Account school;
		Student student = new Student();
		String fullname = "fullname";
		Date admdate = null;
		String admterm = ""; 
		String studentuuid = "";
		int finalyear = 0;

		String schooluuid = "";
		String xml = ""; 

		if(accountDAO.getSchoolByUsername(schoolusername)!=null){
			school = accountDAO.getSchoolByUsername(schoolusername);
			schooluuid = school.getUuid();
				if(studentDAO.getStudentObjByadmNo(schooluuid, admno) !=null){
					student = studentDAO.getStudentObjByadmNo(schooluuid, admno);

					fullname = StringUtils.capitalize(student.getFirstname()) + " "+ StringUtils.capitalize(student.getLastname()) + " "+ StringUtils.capitalize(student.getSurname());
					admdate = student.getAdmissionDate();
					admterm = student.getRegTerm();
					studentuuid = student.getUuid();
					finalyear = student.getFinalYear();

					Locale locale = new Locale("en","KE"); 
					NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
					double balance = 0;
					String feebalance = "";
					StudentBalance studentBal = new StudentBalance();
					balance = studentBal.findBalance(termFeeDAO,sysConfigDAO,studentFeeDAO,studentOtherMoniesDAO,admdate,admterm,studentuuid,schooluuid,finalyear); 
					feebalance = nf.format(balance);

					//PREPARE OUTPUT.     
					xml += "<?xml version= '1.0' encoding= 'utf-8'?> \n"; 
					xml += "<SOAP-ENV:Envelope>     \n";
					xml += "  <SOAP-ENV:Body>       \n";
					xml += "    <studentName>       \n";
					xml += "      "+fullname +"     \n";
					xml += "    </studentName>      \n";
					xml += "    <feeBalance>        \n";
					xml += "      "+feebalance +"   \n";
					xml += "    </feeBalance>       \n";
					xml += "  </SOAP-ENV:Body>      \n";
					xml += "</SOAP-ENV:Envelope>    \n";

				
				}else{
					String msg = "Student admission number not found.";
					xml += "<?xml version=\"1.0\"?> \n";
					xml += "<SOAP-ENV:Envelope>     \n";
					xml += "  <SOAP-ENV:Body>       \n";
					xml += "    <SOAP-ENV:Fault>    \n";
					xml += "      <faultcode xsi:type='xsd:string'>SOAP-ENV:Client</faultcode> \n";
					xml += "         <faultstring xsi:type='xsd:string'>  \n";
					xml += "              "+msg+"  \n";
					xml += "         </faultstring>         \n";
					xml += "    </SOAP-ENV:Fault>      \n";
					xml += "  </SOAP-ENV:Body>      \n";
					xml += "</SOAP-ENV:Envelope>    \n";
				}
				

		}else{
			String msg = "School username incorrect!";
			xml += "<?xml version=\"1.0\"?> \n";
			xml += "<SOAP-ENV:Envelope>     \n";
			xml += "  <SOAP-ENV:Body>       \n";
			xml += "    <SOAP-ENV:Fault>    \n";
			xml += "      <faultcode xsi:type='xsd:string'>SOAP-ENV:Client</faultcode> \n";
			xml += "         <faultstring xsi:type='xsd:string'>  \n";
			xml += "              "+msg+"  \n";
			xml += "         </faultstring>         \n";
			xml += "    </SOAP-ENV:Fault>      \n";
			xml += "  </SOAP-ENV:Body>      \n";
			xml += "</SOAP-ENV:Envelope>    \n";
		}


		//RETURN RESPONSE.    
		response.getWriter().println(xml);

	} 


	/**
	 * 
	 */
	private static final long serialVersionUID = -4106878754612026456L;

}
