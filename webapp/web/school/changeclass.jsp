
<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>


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
     
     
     RoomDAO roomDAO = RoomDAO.getInstance();
     List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
     classroomList = roomDAO.getAllRooms(accountuuid); 

                                      

 %>






<jsp:include page="header.jsp" />


<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :Change Class: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="classTeachers.jsp">Back</a> <span class="divider">/</span>
        </li>

        
        
    </ul>
</div>





<div class="row-fluid sortable">


    <div class="box span12">
        <div class="box-content">


               
            


            <h3><i class="icon-edit"></i>Change class:</h3>  


            <table class="table ">
                <thead>
                    <tr >             
                        <th>Teacher</th>
                        <th>Current Class</th>
                    </tr>
                </thead>   
                <tbody >
                    <%  
                               out.println("<tr>"); 
                               out.println("<td width=\"10%\" class=\"center\">" + request.getParameter("teachername") + "</td>");  
                               out.println("<td width=\"10%\" class=\"center\">" + request.getParameter("classroomname")+ "</td>");    
                             
                    %> 

                </tbody>                  
               </table>  

               <form  class="form-horizontal"   action="changeClassTeacher" method="POST" >
               <fieldset>


                      <h3><i class="icon-edit"></i>CHANGE TO:</h3>  

                     <div class="control-group" id="divid">
                        <label class="control-label" for="class">Class</label>
                        <div class="controls">
                            <select name="newclassuuid" >
                                <option value="">select one</option>
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
                                   <input type="hidden" name="currentclassuuid" value="<%=request.getParameter("currentclassroomuuid")%>">
                                   <input type="hidden" name="currentclssteacher" value="<%=request.getParameter("teacheruuid")%>">
                                   <button type="submit" name="Find" value="promote"   class="btn btn-primary">Change</button> 
                            </div>



                 </fieldset>
                 </form>






               


        
       


    </div>

</div>


<jsp:include page="footer.jsp" />
