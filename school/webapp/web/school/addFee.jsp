<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.persistence.money.TermFeeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.money.TermFee"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>


<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

 <%
     String accountuuid = "";

     if (session == null) {
       response.sendRedirect("../index.jsp");
      
    }

    String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("../index.jsp");
       
    }
     
    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
    Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
    SessionStatistics statistics = new SessionStatistics();
    

    SchoolAccount school = new SchoolAccount();
    Element element;
   

    int incount = 0;  // Generic counter

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }

    accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);


    TermFeeDAO termFeeDAO = TermFeeDAO.getInstance();

    TermFee termFee  = new TermFee();

    if(termFeeDAO.getFee(accountuuid,examConfig.getTerm(),examConfig.getYear()) !=null){
           termFee = termFeeDAO.getFee(accountuuid,examConfig.getTerm(),examConfig.getYear());
       }
   

   
    

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);

    Locale locale = new Locale("en","KE"); 
    NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

    String schoolfee = "";

    if(StringUtils.equalsIgnoreCase(school.getDayBoarding(), "YES")){
        schoolfee = " BOARDING FEE: " + nf.format(termFee.getTermAmount()) + " " + " DAY FEE: " + nf.format(termFee.getDayAmount());
     }else{
        schoolfee = " FEE: " + nf.format(termFee.getTermAmount());
    }
     

 %>
    






<jsp:include page="header.jsp" />


<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :FEE MANAGEMENT PANEL: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear() %>      <%=schoolfee%> <b> </li> <br>

        <li>
            <a href="fee.jsp">Back</a> <span class="divider">/</span>
        </li>

         
        
    </ul>
</div>


<div class="row-fluid sortable">



               



    <div class="box span12">
        <div class="box-content">
          
               <%
                    HashMap<String, Student> paramHash = (HashMap<String, Student>) session.getAttribute(SessionConstants.STUENT_PARAM_F);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, Student>();
                            }


                        HashMap<String, String> paramHash2 = (HashMap<String, String>) session.getAttribute(SessionConstants.STUENT_FEE_ADD_PARAM);

                        if (paramHash2 == null) {
                             paramHash2 = new HashMap<String, String>();
                            }    


                               Student student = new Student();
                               student = paramHash.get("studentObj");






                     String fullname = "";

                     String formatedFirstname = "";
                     String formatedLastname = "";
                     String formatedSurname = "";

                     String firstNameLowecase  = "";
                     String lastNameLowecase  = "";
                     String surNameLowecase  = "";

                     String admNumber ="";    
                     String studentUuid ="";
                     String studentType = "";                            

                     if(student !=null){

                         admNumber = student.getAdmno();
                         studentUuid = student.getUuid();
                         studentType = student.getStudentType();

                         firstNameLowecase = student.getFirstname().toLowerCase();
                         lastNameLowecase =student.getLastname().toLowerCase();
                         surNameLowecase = student.getSurname().toLowerCase();

                         formatedFirstname = firstNameLowecase;
                         formatedLastname = lastNameLowecase;
                         formatedSurname = surNameLowecase; 

                         fullname = formatedFirstname +" "+formatedLastname+" "+formatedSurname + "(" + studentType + ")";
 
                       }



                    
                                String addErrStr = "";
                                String addsuccessStr = "";
                             
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.STUDENT_FIND_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.STUDENT_FIND_SUCCESS); 
                                    

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STUDENT_FIND_SUCCESS, null);
                                  } 
                                 


                     %>



            <table class="table table-striped  ">

                <thead>
                    <tr >             
                        <th></th>
                        <th></th>
                        <th>Search</th>
                    </tr>
                </thead>   

                <tbody >

                              <form name="view" method="POST" action="findStudent"> 

                               <td width="8%" class="center">                              
                              <p><b>Student Admission Number:</b><p>                                                    
                               </td> 

                                <td width="10%" class="center">                              
                                   <input class="input-xlarge focused" id="receiver" type="text" name="AdmNo" 
                                    value=""  >                                                    
                               </td> 

                               <td width="10%" class="center">                                
                            <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Find" />                                                         
                               </td> 
                               </form> 


                </tbody>                  
            </table>  



             <table class="table ">
                <thead>
                    <tr >             
                        <th>Student AdmNo</th>
                        <th>Student name</th>
                    </tr>
                </thead>   
                <tbody >
                    <%  
                               out.println("<tr>"); 
                               out.println("<td width=\"10%\" class=\"center\">" + admNumber + "</td>");  
                               out.println("<td width=\"10%\" class=\"center\">" + fullname + "</td>");    
                             
                    %> 

                </tbody>                  
            </table>  









                               
                             
                                <p>Fields marked with a * are compulsory.</p>

                  <form  class="form-horizontal"   action="addFeeDetails" method="POST" autocomplete="off">
                  <fieldset>
                              
                              <div class="control-group">
                                  <label class="control-label" for="name">Amount Paid*:</label>
                                  <div class="controls">
                                      <input class="input-xlarge focused" id="receiver" type="text" name="Amountpaid"
                                        value="<%= StringUtils.trimToEmpty(paramHash2.get("Amountpaid")) %>"  >
                                  </div>
                              </div> 

                              <div class="control-group">
                                  <label class="control-label" for="name">Bank Slip Number*:</label>
                                  <div class="controls">
                                      <input class="input-xlarge focused" id="receiver" type="text" name="slipNumber"
                                        value="<%= StringUtils.trimToEmpty(paramHash2.get("slipNumber")) %>"  >
                                  </div>
                              </div> 

                              <div class="control-group">
                                  <label class="control-label" for="schoolpassword">School Password*:</label>
                                  <div class="controls">
                                      <input class="input-xlarge focused" id="receiver" type="text" name="schoolpassword"
                                        value="" style="color: white;" autocomplete="off">
                                  </div>
                              </div> 

                               
                              
                              
                              <div class="form-actions">
                                   <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                   <input type="hidden" name="systemuser" value="<%=staffUsername%>">
                                   <input type="hidden" name="studentuuid" value="<%=studentUuid%>">
                                  <button type="submit" class="btn btn-primary">Save</button>
                              </div> 

              </fieldset>
              </form>
       


    </div>
     </div>

</div>


<jsp:include page="footer.jsp" />
