
<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.money.StudentAmountDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.money.StudentAmount"%>

<%@page import="com.yahoo.petermwenda83.persistence.money.TermFeeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.money.TermFee"%>

<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.bean.money.StudentAmount"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>


<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

 <%

   
  if (session == null) {
       response.sendRedirect("../index.jsp");
      
    }

    String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("../index.jsp");
       
    }
     
    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
   
       double amountpaid = 0;
       double balance = 0;
       double termfee = 0;
       double grandtotal = 0;  
       double theTotal = 0;  
       double totalDue = 0;

    SchoolAccount school = new SchoolAccount();
    Element element;
    String accountuuid = "";
    String schoolname = "";
   

    int incount = 0;  // Generic counter

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }
     
     if(school !=null){
      accountuuid = school.getUuid();
      schoolname = school.getSchoolName();
      }
   


    Student student = new Student();
    HashMap<String, Student> studentHash = new HashMap<String, Student>();
    StudentDAO studentDAO = StudentDAO.getInstance();
    List<Student> studentList = new ArrayList(); 

    int studentCount = 0;

   
    studentList = studentDAO.getAllStudentList(accountuuid); 
     if(studentList !=null){
    for(Student stu : studentList) {
       studentHash.put(stu.getUuid(),stu);
       studentCount ++;
      }
     }

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = new ExamConfig();

    if(examConfigDAO.getExamConfig(accountuuid) !=null){
    if(examConfigDAO.getExamConfig(accountuuid)!=null){
        examConfig = examConfigDAO.getExamConfig(accountuuid);
        }
       }
    

    TermFeeDAO termFeeDAO = TermFeeDAO.getInstance();

    TermFee termFee  = new TermFee();

    if(termFeeDAO.getTermFee(accountuuid,examConfig.getTerm()) !=null){
    if(termFeeDAO.getTermFee(accountuuid,examConfig.getTerm())!=null){
           termFee = termFeeDAO.getTermFee(accountuuid,examConfig.getTerm());
           }
       }
   

    List<StudentAmount> feeList = new ArrayList<StudentAmount>();
    StudentAmountDAO studentAmountDAO = StudentAmountDAO.getInstance();

    if(studentAmountDAO.getAmountList(accountuuid) !=null){
         if(studentAmountDAO.getAmountList(accountuuid)!=null){
           feeList = studentAmountDAO.getAmountList(accountuuid);
           }
     }
    


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
   

      Locale locale = new Locale("en","KE"); 
      NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

      
        if(termFee !=null){
         termfee = termFee.getTermAmount();  
          }
      
   
   
 %>






<jsp:include page="header.jsp" />



<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :FINANCE MANAGEMENT PANEL : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear() %>  TERM FEE : <%=termFee.getTermAmount()%><b> </li> <br>

        <li>
            <a href="fee.jsp">STUDENTS FEE</a> <span class="divider">/</span>
        </li>
    
        <li>
            <a href="pocketM.jsp">STUDENTS POCKET MONEY</a> <span class="divider">/</span>
        </li>

         
        
    </ul>
</div>



<div class="row-fluid sortable">




        <div class="box span12">
        <div class="box-content">



         <table class="table ">
                <thead>
                    <tr >             
                        <th>Total Paid</th>
                        <th>Actual Totals</th>
                        <th>Total Dues</th>
                        <th>Total OverPayments</th>
                        <th>Total Expected</th>
                        <th>Action</th>
                        
                    </tr>
                </thead>   
                <tbody >
                    <%  
                                          
                                         double totalExpected = 0;    
                                         double realDues = 0;
                                         double overPays = 0;
                                         double aBSoverPays = 0;
                                         double thisBalance = 0;

                                  if(feeList !=null){
                              for(StudentAmount fees : feeList){

                               theTotal = fees.getAmount();  
                               thisBalance =  termfee - theTotal;

                               grandtotal = grandtotal+theTotal;

                               balance = termfee - amountpaid;
                               totalDue = totalDue + balance;

                                if(thisBalance < 0){
                                   overPays +=thisBalance;
                                  }else{
                                    realDues +=thisBalance;
                                  }

                                 
                                
                                  }
                                }

                                totalExpected = termfee*studentCount;
                                aBSoverPays = Math.abs(overPays);

                               out.println("<tr>"); 
                               out.println("<td class=\"center\">" + nf.format(grandtotal)  + "</td>");
                               out.println("<td class=\"center\">" + nf.format(grandtotal-aBSoverPays)  + "</td>");
                               out.println("<td class=\"center\">" + nf.format(realDues) + "</td>"); 
                               out.println("<td class=\"center\">" + nf.format(aBSoverPays) + "</td>"); 
                               out.println("<td class=\"center\">" + nf.format(totalExpected) + "</td>"); 
                            
                             
                    %> 
                              <td class="center">
                                <form name="view" method="POST" action="financeReport" target="_blank"> 
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Print" /> 
                                </form>                          
                               </td>   



                </tbody>                  
            </table>  

           
            <div>
                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr >             
                        <th>S.N</th>
                        <th>Adm No</th>
                        <th>Name</th>
                        <th>Ammount Paid</th>
                        <th>Balance</th>
                       
                    </tr>
                </thead>   
                <tbody >
                    <%     
                             

                             
                               String admno = "";
                               String firstname = "";
                               String lastname = "";
  

                               int count = 1;
		                     if(feeList !=null){
		                   for(StudentAmount fee : feeList){
                               amountpaid = fee.getAmount(); 
                               balance = termfee - amountpaid;

                               if(studentHash.get(fee.getStudentUuid()) !=null){
                                  
                                  if(studentHash.get(fee.getStudentUuid()) !=null){
                                  student = studentHash.get(fee.getStudentUuid());
                                    }

                                  if(student !=null){
                                  admno = student.getAdmno();
                                  firstname = student.getFirstname();
                                  lastname = student.getLastname();
                                  }
                               }
                               
                               
                               out.println("<tr>"); 
                               out.println("<td width=\"3%\" >" + count + "</td>"); 
                               out.println("<td class=\"center\">" + admno  + "</td>"); 
                               out.println("<td class=\"center\">" + firstname +" "+ lastname + "</td>"); 
                               out.println("<td class=\"center\">" + nf.format(amountpaid)  + "</td>");
                               out.println("<td class=\"center\">" + nf.format(balance) + "</td>"); 
                               
                               count++;
                            
		                         }		                       
                           }                   
                  
                             %> 

                               

                </tbody>                                 
            </table>  
   </div>




    </div>

</div>



<jsp:include page="footer.jsp" />
