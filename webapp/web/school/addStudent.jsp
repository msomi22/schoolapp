<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="org.apache.commons.lang3.math.NumberUtils"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@ page import="java.util.Calendar" %>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>


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
    String isDayBoarding = "";//YES,NO-B,NO-D
   

    int incount = 0;  // Generic counter

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }

    accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();
    isDayBoarding = school.getDayBoarding();

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);

    RoomDAO roomDAO = RoomDAO.getInstance();
    List<ClassRoom> classList = new ArrayList<ClassRoom>();
    classList = roomDAO.getAllRooms(accountuuid);

     StudentDAO studentDAO = StudentDAO.getInstance();
     Student student = studentDAO.getStudentADmNo(accountuuid);

      int admno = 0; 
      String studentadm = student.getAdmno();
      admno = NumberUtils.toInt(studentadm);
      if(admno <=0){
          final  String INITIAL_ADM_NO =(String)  PropertiesConfig.getConfigValue("INITIAL_ADM_NO");
          admno = NumberUtils.toInt(INITIAL_ADM_NO);
       }else{
            admno = NumberUtils.toInt(studentadm);
        }
     
     admno = admno + 1;
     String newAdmno = ""+admno;
      
    
    
    

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     

    Calendar calendar = Calendar.getInstance();
    final int DAYS_IN_MONTH = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
    final int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
    final int MONTH = calendar.get(Calendar.MONTH) + 1;
    final int YEAR = calendar.get(Calendar.YEAR)-18;
    final int YEAR_COUNT = YEAR + 10;

    final int YEAR2 = calendar.get(Calendar.YEAR)-10;
    final int YEAR_COUNT2 = YEAR2 + 10;

   

 %>






<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :STUDENT REGISTRATION PANEL (BASIC INFORMATION): TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="studentIndex.jsp">Back</a> <span class="divider">/</span>
        </li>

        
    </ul>
</div>



<div class="row-fluid sortable">





    <div class="box span12">
        <div class="box-content">
          
               <%
                    HashMap<String, String> paramHash = (HashMap<String, String>) session.getAttribute(SessionConstants.STUDENT_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }
                             

                                String addErrStr = "";
                                String addsuccessStr = "";
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.STUDENT_ADD_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.STUDENT_ADD_SUCCESS); 

                                if(session != null) {
                                    addErrStr = (String) session.getAttribute(SessionConstants.STUDENT_ADD_ERROR);
                                    addsuccessStr = (String) session.getAttribute(SessionConstants.STUDENT_ADD_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STUDENT_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STUDENT_ADD_SUCCESS, null);
                                  } 


                     %>


                    <p>Fields marked with a * are compulsory.</p>
                    <form  class="form-horizontal"   action="addStudentBacic" method="POST" >
                    <fieldset>

                                     <div class="control-group">
                                        <label class="control-label" for="Classroom">Classroom*:</label>
                                         <div class="controls">
                                            <select name="classroomUuid" >

                                            <option value="">Please select one</option> 
                                                 <%
                                                    int count = 1;
                                                    if (classList != null) {
                                                        for (ClassRoom cl : classList) {
                                                %>
                                                <option value="<%=cl.getUuid()%>"><%=cl.getRoomName()%></option>
                                                <%
                                                            count++;
                                                        }
                                                    }
                                                %>
                                                
                                            </select>                           
                                          
                                        </div>
                                    </div> 


                                  
                                    <div class="control-group">
                                        <label class="control-label" for="name">Admission Number*:</label>
                                        <div class="controls">
                                         <input class="input-xlarge focused" id="receiver" type="text" name="admNO" 
                                            value="<%=newAdmno%>" > 

                                        </div>
                                    </div>  


                                     <div class="control-group">
                                        <label class="control-label" for="name">First name*:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="firstname" 
                                             value="<%= StringUtils.trimToEmpty(paramHash.get("firstname")) %>" style="text-transform: capitalize;" >                                    
                                        </div>
                                    </div> 


                                    <div class="control-group">
                                        <label class="control-label" for="name">Middle name*:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="lastname"
                                              value="<%=StringUtils.trimToEmpty(paramHash.get("lastname")) %>" style="text-transform: capitalize;" >
                                        </div>
                                    </div> 


                                     <div class="control-group">
                                        <label class="control-label" for="name">Last name:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="surname"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("surname")) %>" style="text-transform: capitalize;" >
                                        </div>
                                    </div> 

                                    <div class="control-group">
                                        <label class="control-label" for="gender">Gender*:</label>
                                         <div class="controls">
                                            <select name="gender" >
                                               <option value='<%=StringUtils.trimToEmpty(paramHash.get("gender"))%>'> <%=StringUtils.trimToEmpty(paramHash.get("gender"))%> </option> 
                                                <option value="MALE">Male</option>
                                              <option value="FEMALE">Female</option> 
                                                
                                            </select>                           
                                          
                                        </div>
                                    </div> 
                                     

                                    <div class="control-group">
                                        <label class="control-label" for="name">DOB (DD-MM-YYYY)*:</label>
                                        <div class="controls">

                                                  <select name="dobaddDay" id="input" style="max-width:8%;">


                                                        <%
                                                            for (int j = 1; j < DAYS_IN_MONTH; j++) {
                                                                if (j == DAY_OF_MONTH) {
                                                                    out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                                                } else {
                                                                    out.println("<option value=\"" + j + "\">" + j + "</option>");
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                                   <select name="dobaddMonth" id="input" style="max-width:8%;" >
                                                  
                                                        <%
                                                            for (int j = 1; j < 13; j++) {
                                                                if (j == MONTH) {
                                                                    out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                                                } else {
                                                                    out.println("<option value=\"" + j + "\">" + j + "</option>");
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                                    <select name="dobaddYear" id="input" style="max-width:8%;">
                                                     
                                                        <%
                                                            for (int j = YEAR; j < YEAR_COUNT; j++) {
                                                                if (j == YEAR) {
                                                                    out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                                                } else {
                                                                    out.println("<option value=\"" + j + "\">" + j + "</option>");
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                        </div>
                                        </div>


                                    

                                    <div class="control-group">
                                        <label class="control-label" for="name">Birth Cert:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="BcertNo"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("BcertNo")) %>"  >
                                        </div>
                                    </div> 

                                     <div class="control-group">
                                        <label class="control-label" for="name">County:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="County"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("County")) %>" style="text-transform: capitalize;" >
                                        </div>
                                    </div> 

                                   <h3><i class="icon-edit"></i> Primary School Details:</h3>  

                                     <div class="control-group">
                                        <label class="control-label" for="name">Primary School:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="primary"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("primary")) %>"  style="text-transform: capitalize;">
                                        </div>
                                    </div> 


                                     <div class="control-group">
                                        <label class="control-label" for="name">Index Number:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="indexno"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("indexno")) %>"  >
                                        </div>
                                    </div> 

                                     <div class="control-group">
                                        <label class="control-label" for="name">KCPE Year:</label>
                                         <div class="controls">
                                                <select name="kcpeaddYear" id="input" style="max-width:8%;">

                                                        <%
                                                            for (int j = YEAR2; j < YEAR_COUNT2; j++) {
                                                                if (j == YEAR2) {
                                                                    out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                                                } else {
                                                                    out.println("<option value=\"" + j + "\">" + j + "</option>");
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                            </div>
                                    </div> 

                                     <div class="control-group">
                                        <label class="control-label" for="name">KCPE Marks*:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="kcpemark"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("kcpemark")) %>"  >
                                        </div>
                                    </div> 

                                    <div class="control-group">
                                        <label class="control-label" for="StydentType">StydentType*:</label>
                                         <div class="controls">
                                            <select name="StydentType" >

                                   <option value='<%=StringUtils.trimToEmpty(paramHash.get("StydentType"))%>'> <%=StringUtils.trimToEmpty(paramHash.get("StydentType"))%> </option> 

                               <%if(StringUtils.equals(isDayBoarding,"YES")){%>

                                              <option value="Boarder">Boarder</option> 
                                              <option value="Day">Day</option> 

                                <%}else if(StringUtils.equals(isDayBoarding,"NO-B")){%>

                                               <option value="Boarder" selected>Boarder</option> 
                                               
                                <%}else if(StringUtils.equals(isDayBoarding,"NO-D")){%>

                                                <option value="Day" selected>Day</option> 

                                              <%}%>
                                                
                                            </select>                           
                                          
                                        </div>
                                    </div> 

                                    

                                    
                                    <div class="form-actions">
                                        <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                         <input type="hidden" name="systemuser" value="<%=staffUsername%>">
                                        <button type="submit" class="btn btn-primary">Register</button>
                                    </div> 

              </fieldset>
              </form>
       


    </div>
     </div>

</div>


<jsp:include page="footer.jsp" />
