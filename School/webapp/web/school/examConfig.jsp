<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.GradingSystem"%>

<%@page import="com.yahoo.petermwenda83.persistence.money.TermFeeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.money.TermFee"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.Exam"%>

<%@page import="com.yahoo.petermwenda83.persistence.schoolaccount.MiscellanousDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.Miscellanous"%>

<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.*"%>
<%@page import="java.util.stream.Collectors"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>

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
    TermFeeDAO termFeeDAO = TermFeeDAO.getInstance();
    MiscellanousDAO miscellanousDAO = MiscellanousDAO.getInstance();
    ExamDAO examDAO = ExamDAO.getInstance();

    List<TermFee> termlist = new ArrayList<TermFee>();
    termlist = termFeeDAO.getTermFeeList(accountuuid);

     List<Miscellanous> misclist = new ArrayList<Miscellanous>();
     misclist = miscellanousDAO.getMiscellanousList(accountuuid);

    List<Exam> examlist = new ArrayList<Exam>();
    examlist = examDAO.getExamList(accountuuid);
     

    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);

    GradingSystemDAO gradingSystemDAO = GradingSystemDAO.getInstance();
    GradingSystem gradingSystem = gradingSystemDAO.getGradingSystem(accountuuid);

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
    
      
      int sessiontime = SessionConstants.SESSION_TIMEOUT;
      //out.println(sessiontime);

     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);

    Locale locale = new Locale("en","KE"); 
    NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
    
 %>






<jsp:include page="header.jsp" />


<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :EXAM CONFIGURATION PANEL: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <span class="divider">/</span>

      <li>
            <a href="changeTermYear.jsp">Update Term/Year</a> <span class="divider">/</span>
     </li>

     <li>
            <a href="smsApi.jsp">SMS API</a> <span class="divider">/</span>
     </li>
      
        
    </ul>
</div>

<div class="row-fluid sortable">
    <div class="box span12">
        <div class="box-content">

        <%

                                String updateErrStr = "";
                                String updatesuccessStr = "";
                                session = request.getSession(false);
                                     updateErrStr = (String) session.getAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR);
                                     updatesuccessStr = (String) session.getAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS); 

                                if(session != null) {
                                    updateErrStr = (String) session.getAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR);
                                    updatesuccessStr = (String) session.getAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(updateErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updatesuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updatesuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS,null);
                                  } 

                                String gSyupdateErrStr = "";
                                String gSyupdatesuccessStr = "";
                                session = request.getSession(false);
                                     gSyupdateErrStr = (String) session.getAttribute(SessionConstants.GRADE_ADD_ERROR);
                                     gSyupdatesuccessStr = (String) session.getAttribute(SessionConstants.GRADE_ADD_SUCCESS); 

                                if(session != null) {
                                    gSyupdateErrStr = (String) session.getAttribute(SessionConstants.GRADE_ADD_ERROR);
                                    gSyupdatesuccessStr = (String) session.getAttribute(SessionConstants.GRADE_ADD_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(gSyupdateErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + gSyupdateErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.GRADE_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(gSyupdatesuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + gSyupdatesuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.GRADE_ADD_SUCCESS,null);
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


                                   String delErrStr = "";
                                    String delsuccessStr = "";
                             
                                 session = request.getSession(false);
                                     delErrStr = (String) session.getAttribute(SessionConstants.STUENT_DELETE_ERROR);
                                     delsuccessStr = (String) session.getAttribute(SessionConstants.STUENT_DELETE_SUCCESS); 
                                    

                                if (StringUtils.isNotEmpty(delErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + delErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STUENT_DELETE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(delsuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + delsuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STUENT_DELETE_SUCCESS, null);
                                  } 




        %>
            
   <h3><i class="icon-edit"></i> Exam Configurations :</h3>  
         <p>
             ET F1: stands for, end of term form one, when ON only end term exam will be counted, affects FORM 1 only<br>
             ET: means end term, when ON it means only end term exam is considered, all cats will be excluded, affects F1-F4. <br>
             ET C2: when ON  means only end term and cat 2 exams are included, cat 1 will be discarded, affects F1-F4. <br>
             ET C1 C2: when ON means cat 1 , cat 2 and end term exams will be counted, affects F1-F4. <br>
             Mode: when ON the system will compute   P1,P2 and P3 , otherwise C1,C2 and ET. <br>

             C1: stands for cat 1 , C2 cat 2, ET end term , P1 paper 1, P2 paper 2 and P3 paper 3. <br>



         </p>


<table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr>
                       
                        <th>Term</th>
                        <th>Year</th> 
                        <th>Exam</th>                
                        <th>Mode</th>
                        <th>SMS send</th>
                        <th>ET F1</th>

                        <th>ET</th>
                        <th>ET C2</th>
                        <th>ET C1 C2</th>
                        <th>C1 C2</th>
                        
                        <th>Action</th>
                       
                    </tr>
                </thead>   
                <tbody>
                    
                    <tr>
                         <td class="center"><%=examConfig.getTerm() %></td>
                         <td class="center"><%=examConfig.getYear() %></td>
                         <td class="center"><%=examConfig.getExam() %></td>
                         <td class="center"><%=examConfig.getExamMode() %></td> 
                         <td class="center"><%=examConfig.getSendSMS() %></td>   
                         <td class="center"><%=examConfig.geteTFone()%></td>  
                         <td class="center"><%=examConfig.geteT() %></td>  
                         <td class="center"><%=examConfig.geteTCtwo() %></td>  
                         <td class="center"><%=examConfig.geteTConetwo() %></td>  
                         <td class="center"><%=examConfig.getC1c2() %></td>  
                        
                         <td class="center">
                                <form name="edit" method="POST" action="updateExamConfig.jsp" > 
                                <input type="hidden" name="schoolUuid" value="<%=examConfig.getSchoolAccountUuid()%>">
                                <input type="hidden" name="term" value="<%=examConfig.getTerm()%>">
                                <input type="hidden" name="year" value="<%=examConfig.getYear()%>">
                                <input type="hidden" name="exam" value="<%=examConfig.getExam()%>">
                                <input type="hidden" name="exammode" value="<%=examConfig.getExamMode()%>">
                                <input type="hidden" name="eTFone" value="<%=examConfig.geteTFone()%>">
                                <input type="hidden" name="eT" value="<%=examConfig.geteT()%>">
                                <input type="hidden" name="eTCtwo" value="<%=examConfig.geteTCtwo()%>">
                                <input type="hidden" name="eTConetwo" value="<%=examConfig.geteTConetwo()%>">
                                <input type="hidden" name="c1c2" value="<%=examConfig.getC1c2()%>">
                                <input type="hidden" name="sendSmsEnable" value="<%=examConfig.getSendSMS()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Update" /> 
                                </form>                          
                         </td>  
                        
                    </tr>

                </tbody>
            </table> 




             <h3><i class="icon-edit"></i> Grading Scale:</h3>  
            <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr>
                       
                        <th>A</th>
                        <th>A-</th> 
                        <th>B+</th>                
                        <th>B</th>
                        <th>B-</th>
                        <th>C+</th>
                        <th>C</th>
                        <th>C-</th>
                        <th>D+</th>
                        <th>D</th>
                        <th>D-</th>
                        <th>E</th>
                        <th>Action</th>
                    
                    </tr>
                </thead>   
                <tbody>
                    
                    <tr>
                         <td class="center"> 100 - <%=gradingSystem.getGradeAplain()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeAplain()-1%> - <%=gradingSystem.getGradeAminus()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeAminus()-1%> - <%=gradingSystem.getGradeBplus()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeBplus()-1%> - <%=gradingSystem.getGradeBplain()%> </td>  
                         <td class="center"> <%=gradingSystem.getGradeBplain()-1%> - <%=gradingSystem.getGradeBminus()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeBminus()-1%> - <%=gradingSystem.getGradeCplus()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeCplus()-1%> - <%=gradingSystem.getGradeCplain()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeCplain()-1%> - <%=gradingSystem.getGradeCminus()%> </td>  
                         <td class="center"> <%=gradingSystem.getGradeCminus()-1%> - <%=gradingSystem.getGradeDplus()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeDplus()-1%> - <%=gradingSystem.getGradeDplain()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeDplain()-1%> - <%=gradingSystem.getGradeDminus()%> </td>
                         <td class="center"> <%=gradingSystem.getGradeDminus()-1%> - <%=gradingSystem.getGradeE()%> </td>  
                         <td class="center">
                                <form name="edit" method="POST" action="updateGradingScale.jsp" > 
                                <input type="hidden" name="schoolUuid" value="<%=gradingSystem.getSchoolAccountUuid()%>">
                                <input type="hidden" name="A" value="<%=gradingSystem.getGradeAplain()%>">
                                <input type="hidden" name="Am" value="<%=gradingSystem.getGradeAminus()%>">
                                <input type="hidden" name="Bp" value="<%=gradingSystem.getGradeBplus()%>">
                                <input type="hidden" name="B" value="<%=gradingSystem.getGradeBplain()%>">
                                <input type="hidden" name="Bm" value="<%=gradingSystem.getGradeBminus()%>">
                                <input type="hidden" name="Cp" value="<%=gradingSystem.getGradeCplus()%>">
                                <input type="hidden" name="C" value="<%=gradingSystem.getGradeCplain()%>">
                                <input type="hidden" name="Cm" value="<%=gradingSystem.getGradeCminus()%>">
                                <input type="hidden" name="Dp" value="<%=gradingSystem.getGradeDplus()%>">
                                <input type="hidden" name="D" value="<%=gradingSystem.getGradeDplain()%>">
                                <input type="hidden" name="Dm" value="<%=gradingSystem.getGradeDminus()%>">
                                <input type="hidden" name="E" value="<%=gradingSystem.getGradeE()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Update" /> 
                                </form>                          
                         </td>  

                    </tr>

                </tbody>
            </table>  


           



             <h3><i class="icon-edit"></i> Basic Variables:</h3>  
             <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr >
                        <th>*</th>
                        <th>Key</th>
                        <th>Value</th>              
                        
                     
                    </tr>
                </thead>   
                <tbody>
                    <%  //misclist   Miscellanous
                     int misccount = 1;
                    for(Miscellanous misc : misclist){

                     %>

                    <tr>
                         <td width="3%"><%=misccount%></td>
                         <td class="center"><%=misc.getKey()%> </td>   
                         <td class="center"> 

                         <%if(StringUtils.contains(misc.getKey(),"CARD")){%>

                          <input class="jscolor" value="<%=misc.getValue()%>" readonly> 

                          <%}else{%>

                           <%=misc.getValue()%> 

                          <%}%>

                         </td>

                          <td class="center">
                                <form name="edit" method="POST" action="updateMisc.jsp" > 
                                <input type="hidden" name="schoolUuid" value="<%=misc.getSchoolAccountUuid()%>">
                                <input type="hidden" name="miscUuid" value="<%=misc.getUuid()%>">
                                <input type="hidden" name="key" value="<%=misc.getKey()%>">
                                <input type="hidden" name="value" value="<%=misc.getValue()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Update" /> 
                                </form>                          
                         </td>  
                    </tr>

                    <%
                          misccount++;
                       }
                            
                    %>
                </tbody>
            </table>  




             <h3><i class="icon-edit"></i> Exam out of settings:</h3>  
             <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr >
                        <th>*</th>
                        <th>Exam type</th>
                        <th>Out-of</th>  
                        <th>Action</th>            
                        
                     
                    </tr>
                </thead>   
                <tbody>
                    <%  //examlist Exam
                     int examcount = 1;
                    for(Exam exm : examlist){

                     %>

                    <tr>
                         <td width="3%"><%=examcount%></td>
                         <td class="center"><%=exm.getExamName()%> </td>   
                         <td class="center"> <%= exm.getOutOf()%> </td>
                          <td class="center">
                                <form name="edit" method="POST" action="updateExam.jsp" >  
                                <input type="hidden" name="schoolUuid" value="<%=exm.getSchoolAccountUuid()%>">
                                <input type="hidden" name="examUuid" value="<%=exm.getUuid()%>">
                                <input type="hidden" name="examName" value="<%=exm.getExamName()%>">
                                <input type="hidden" name="examOutOf" value="<%=exm.getOutOf()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Update" /> 
                                </form>                          
                         </td>  
                    </tr>

                    <%
                          examcount++;
                       }
                            
                    %>
                </tbody>
            </table>  


             <h3><i class="icon-edit"></i> Delete student's exam data (for this term  only ):</h3>  


             <table class="table table-striped  ">

                <thead>
                    <tr >             
                        <th></th>
                        <th></th>
                        <th>Search</th>
                    </tr>
                </thead>   

                <tbody >

                              <form name="view" method="POST" action="findStudentToDelete"> 

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

            <%


                                


              HashMap<String, Student> paramHash = (HashMap<String, Student>) session.getAttribute(SessionConstants.STUENT_DELETE_PARAM);

                           if (paramHash == null) {
                             paramHash = new HashMap<String, Student>();
                            }

                            Student student = new Student();
                            student = paramHash.get("studentTOdelete");


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

                         fullname = formatedFirstname +" "+formatedLastname+" "+formatedSurname ;
 
                       }




            %>



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


            <form  class="form-horizontal"   action="deleteStudent" method="POST" autocomplete="off">
                  <fieldset>
                              
                              <div class="form-actions">
                                   <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                   <input type="hidden" name="studentuuid" value="<%=studentUuid%>">
                                  <button type="submit" class="btn btn-primary">Delete</button>
                              </div> 

              </fieldset>
              </form>


      


    </div>

</div>


<jsp:include page="footer.jsp" />
