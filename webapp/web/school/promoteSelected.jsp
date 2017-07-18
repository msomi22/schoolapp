
<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

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
     <li> <b> <%=schoolname%> :STUDENT PROMOTION PANEL: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="promote.jsp">Back</a> <span class="divider">/</span>
        </li>

    </ul>
</div>





<div class="row-fluid sortable">


    <div class="box span12">
        <div class="box-content">


                <%
                  
                    

                                String addErrStr = "";
                                String addsuccessStr = "";
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.PROMOTE_CALSS_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.PROMOTE_CALSS_SUCCESS); 
                                    
                    

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.PROMOTE_CALSS_SUCCESS, null);
                                  } 

           HashMap<String, List<Student>> paramHash = (HashMap<String, List<Student>>) session.getAttribute(SessionConstants.SHOW_SELECTED_STUDENT_MAP);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, List<Student>>();
                            }

                   List<Student> studentList = new ArrayList<Student>();
                   studentList = paramHash.get("currentclassId");
                                  

                           
                     %>

                 
                    <form method="POST" action="showSelected">
                     <div class="control-group">
                        <label class="control-label" for="class">Class</label>
                        <div class="controls">
                            <select name="selectedclassId"> 
                            <option value="">select one</option>
                       
                                 <%
                                    int count = 1;
                                    if (classroomList != null) {
                                        for (ClassRoom cc : classroomList) {
                                          %>

		                                <option value="<%= cc.getUuid()%>">
		                                    <%=cc.getRoomName()%>
		                                </option>
                                 
                                         <%
                                            count++;
                                        }
                                    }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                        <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                         Show
                         </button> 
                    </div>
                    </form>

                 
                
                <form method="POST" action="promoteSelected">
				<table class="table table-striped table-bordered">
				    	<thead>
				    		 <tr>
				    		 	<th>*</th>
				    		 	<th>AdmNo</th>
				    		 	<th>Firstname</th>
				    		 	<th>Lastname</th>
				    		 	<th>
                                <input type="checkbox" onchange="checkAll(this)" name="chk[]">
                                    
                                </th>
				    		 </tr>
				    	</thead>
				    	<tbody>
				    	<%
				    	if(studentList !=null){
				    	  int scount = 1;
				    	  for(Student stu : studentList){
 
				    	%>
				    	<tr>
				    		<td><%=scount%></td>
				    		<td><%=stu.getAdmno()%></td>
				    		<td><%=stu.getFirstname()%></td>
				    		<td><%=stu.getLastname()%></td>
				    		<td> 
					    	 <div class="checkbox">		
					    	   <input type="hidden" name="studentId[]" value="<%=stu.getUuid()%>">		
							   <label> 
							   <input type="checkbox" name="studentCheck[]" value="<%=stu.getUuid()%>">Check
							   </label>
							  </div>				
				    		</td>
				    	</tr>

				    	<%
				    	   scount++;
				    	   }
                          }
				    	%>

				    	</tbody>
				    </table>

				    <h3><i class="icon-edit"></i>PROMOTE TO:</h3>  

                     <div class="control-group" id="divid">
                        <label class="control-label" for="class">Class</label>
                        <div class="controls">
                            <select name="newclassId" >
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
                        <button type="submit" class="btn btn-primary">
                        <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                         Promote
                         </button> 
                    </div>
				    </form>


  


    </div>

</div>


<script type="text/javascript">
   
   function checkAll(ele) {
     var checkboxes = document.getElementsByTagName('input');
     if (ele.checked) {
         for (var i = 0; i < checkboxes.length; i++) {
             if (checkboxes[i].type == 'checkbox') {
                 checkboxes[i].checked = true;
             }
         }
     } else {
         for (var i = 0; i < checkboxes.length; i++) {
             console.log(i)
             if (checkboxes[i].type == 'checkbox') {
                 checkboxes[i].checked = false;
             }
         }
     }
 }

 
</script>


<jsp:include page="footer2.jsp" />
