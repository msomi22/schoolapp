
<%@page import="com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.TeacherSubClass"%>

<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.persistence.subject.SubjectDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.subject.Subject"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>


<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>


<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.*"%>
<%@page import="java.util.stream.Collectors"%>


<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="java.util.HashSet"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>

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
    Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
    SessionStatistics statistics = new SessionStatistics();
    

    SchoolAccount school = new SchoolAccount();
    Element element;
   

    int incount = 0;  // Generic counter

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }

    String accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     

     
     TeacherSubClassDAO teacherSubClassDAO = TeacherSubClassDAO.getInstance();
     List<TeacherSubClass> teachersubclassList = new ArrayList<TeacherSubClass>(); 
    
     
     
    HashMap<String, String> subjectHash = new HashMap<String, String>();
    HashMap<String, String> subjectCodeHash = new HashMap<String, String>();
     
     SubjectDAO subjectDAO = SubjectDAO.getInstance();
     List<Subject> subjectList = new ArrayList<Subject>(); 
     subjectList = subjectDAO.getAllSubjects(); 
      for(Subject s : subjectList){
           subjectHash.put(s.getUuid() , s.getSubjectName());  
           subjectCodeHash.put(s.getUuid() , s.getSubjectCode());
            }
     

     HashMap<String, String> roomHash = new HashMap<String, String>();
     RoomDAO roomDAO = RoomDAO.getInstance();
     List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
     classroomList = roomDAO.getAllRooms(accountuuid); 

      for(ClassRoom c : classroomList){
           roomHash.put(c.getUuid() , c.getRoomName());

            }
      

    
                                        

 %>






<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :TEACHER-SUBJECT ASSIGNMENT PANEL : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="staff.jsp">Back</a> <span class="divider">/</span>
        </li>

        
    </ul>
</div>

<div class="row-fluid sortable">

    <div class="box span12">
        <div class="box-content">


                <%
                    HashMap<String, String> paramHash = (HashMap<String, String>) session.getAttribute(SessionConstants.STAFF_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }

                    

                                String addErrStr = "";
                                String addsuccessStr = "";
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.STAFF_FIND_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.STAFF_FIND_SUCCESS); 
                                    
                    

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STAFF_FIND_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STAFF_FIND_SUCCESS, null);
                                  } 
                                  

                           String stffNumber =""; 
                           String firstname ="";
                           String lastname =""; 
                           String surname ="";  

                          if(StringUtils.isEmpty(paramHash.get("staffNumber"))){
                           stffNumber = " ";
                          }else{
                           stffNumber = StringUtils.capitalize(paramHash.get("staffNumber").toLowerCase()); 
                           }

                           if(StringUtils.isEmpty(paramHash.get("firstname"))){
                           firstname = " ";
                          }else{
                           firstname = StringUtils.capitalize(paramHash.get("firstname").toLowerCase()); 
                           }


                           if(StringUtils.isEmpty(paramHash.get("lastname"))){
                           lastname = " ";
                          }else{
                           lastname = StringUtils.capitalize(paramHash.get("lastname").toLowerCase()); 
                           }


                           if(StringUtils.isEmpty(paramHash.get("surname"))){
                           surname = " ";
                          }else{
                           surname = paramHash.get("surname"); 
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

                              <form name="view" method="POST" action="finderStaff"> 

                               <td width="8%" class="center">                              
                              <p><b>Employee Number:</b><p>                                                    
                               </td> 

                                <td width="10%" class="center">                              
                                   <input class="input-xlarge focused" id="receiver" type="text" name="employeeNumber" 
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
                        <th>Employee Number</th>
                        <th>Staff Name</th>
                    </tr>
                </thead>   
                <tbody >
                    <%  
                               out.println("<tr>"); 
                               out.println("<td width=\"10%\" class=\"center\">" + stffNumber + "</td>");  
                               out.println("<td width=\"10%\" class=\"center\">" + firstname +" "+ lastname +" " + surname + "</td>");    
                             
                    %> 

                </tbody>                  
            </table>  





               <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>Subject name </th>
                        <th>Subject code </th>
                        <th>Class</th>
                        <th>Action</th>
                       
                       
                        
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                         String staffUuid = paramHash.get("staffuuid");     
                         teachersubclassList = teacherSubClassDAO.getSubjectsANDClassesList(staffUuid); 

                       int count3 = 1;
                       for(TeacherSubClass cs : teachersubclassList) {
                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + count3 + "</td>"); 
                             out.println("<td class=\"center\">" + subjectHash.get(cs.getSubjectUuid()) + "</td>");  
                             out.println("<td class=\"center\">" + subjectCodeHash.get(cs.getSubjectUuid()) + "</td>"); 
                             out.println("<td class=\"center\">" + roomHash.get(cs.getClassRoomUuid()) + "</td>"); 
                           
                             %> 

                                <td class="center">
                                <form name="update" method="POST" action="deleteTeacherSubject"> 
                                <input type="hidden" name="subjectId" value="<%=cs.getSubjectUuid()%>">
                                <input type="hidden" name="staffid" value="<%=staffUuid%>">
                                <input type="hidden" name="classid" value="<%=cs.getClassRoomUuid()%>">
                                <input class="btn btn-success" type="submit" name="delete" id="submit" value="Delete" /> 
                                </form>                          
                                </td>   

                               

                        <%      
                        count3++;
                      } 
                    %>
                    
                    </tbody>
            </table> 







            <h3><i class="icon-edit"></i> Assign Subject to Teacher:</h3>  

               <form  class="form-horizontal"   action="assignSubjectClass" method="POST" >
               <fieldset>

               <div class="control-group" id="divid">
                        <label class="control-label" for="subject">Subject</label>
                        <div class="controls">
                            <select name="subjectId" >
                               <option value="">select one</option>
                                 <%
                                    int count = 1;
                                    if (subjectList != null) {
                                        for (Subject s : subjectList) {
                                %>
                                <option value="<%=s.getUuid()%>"><%=s.getSubjectName()%></option>
                                <%
                                            count++;
                                        }
                                    }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 


                    <div class="control-group" id="divid">
                        <label class="control-label" for="class">Class</label>
                        <div class="controls">
                            <select name="classId" multiple>
                               
                                 <%
                                    int count2 = 1;
                                    if (classroomList != null) {
                                        for (ClassRoom cc : classroomList) {
                                %>
                                <option value="<%= cc.getUuid()%>"><%=cc.getRoomName()%></option>
                                <%
                                            count2++;
                                        }
                                    }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 


                            <div class="form-actions">
                                  <input type="hidden" name="staffid" value="<%=staffUuid%>">
                                  <input type="hidden" name="systemuser" value="<%=staffUsername%>">
                                  <button type="submit" name="Find" value="Find"   class="btn btn-primary">Assign</button> 
                            </div>



                 </fieldset>
                 </form>






               


        
       


    </div>

</div>


<jsp:include page="footer.jsp" />
