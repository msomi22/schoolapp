<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.StaffDetails"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.PositionDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Position"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>
<%@page import="javax.servlet.ServletContext"%>

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

<%@page import="java.text.SimpleDateFormat"%>


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


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);


     StaffDetails staffdetail = new StaffDetails();
     HashMap<String, StaffDetails> staffHash = new HashMap<String, StaffDetails>();
     StaffDetailsDAO staffDetailsDAO = StaffDetailsDAO.getInstance();
     List<StaffDetails> staffdetailList = new ArrayList<StaffDetails>(); 
     staffdetailList = staffDetailsDAO.getSStaffDetailList();
         
         if(staffdetailList !=null){
      for(StaffDetails sd : staffdetailList){
          staffHash.put(sd.getStaffUuid(), sd);
         }
     }
     
     HashMap<String, String> positionHash = new HashMap<String, String>();
     PositionDAO positionDAO = PositionDAO.getInstance();
     List<Position> positionList = new ArrayList<Position>(); 
     positionList = positionDAO.getPositionList();
        if(positionList !=null){
     for(Position pp : positionList){
      positionHash.put(pp.getUuid(),pp.getPosition());  
       }
   }
    
     StaffDAO staffDAO = StaffDAO.getInstance();
     List<Staff> staffList = new ArrayList<Staff>(); 
     staffList = staffDAO.getStaffList(accountuuid);

     
    
    
       //date format
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat timezoneFormatter = new SimpleDateFormat("z");  

   
     ServletContext context = getServletContext();
     Map<String,String> online =  (HashMap<String,String>)context.getAttribute("onlineUsersMap");
     String value_sessionId = "";
     String staff_status = "";
     
 %>






<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :STAFF MANAGENENT PANEL: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="addStaff.jsp">New Staff</a> <span class="divider">/</span>
        </li>

         <li>
            <a href="classTeachers.jsp">Class Teachers</a> <span class="divider">/</span>
        </li>

         <li>
            <a href="teacherSubjects.jsp">Assign Subject</a> <span class="divider">/</span>
        </li>

        
    </ul>
</div>




<div class="row-fluid sortable">

               
    <div class="box span12">S
        <div class="box-content">
            
             <%
                                String updateErrStr = "";
                                String updatesuccessStr = "";
                                session = request.getSession(false);
                                     updateErrStr = (String) session.getAttribute(SessionConstants.STAFF_UPDATE_ERROR);
                                     updatesuccessStr = (String) session.getAttribute(SessionConstants.STAFF_UPDATE_SUCCESS); 

                                if(session != null) {
                                    updateErrStr = (String) session.getAttribute(SessionConstants.STAFF_UPDATE_ERROR);
                                    updatesuccessStr = (String) session.getAttribute(SessionConstants.STAFF_UPDATE_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(updateErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updatesuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updatesuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STAFF_UPDATE_SUCCESS, null);
                                  } 


                     %>



            <div>
                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>Position</th>
                        <th>Username </th>
                        <th>Emp No </th>
                        <th>First name</th>
                        <th>Middle name </th>
                        <th>Last name</th>
                        <th>Gender </th>
                        <th>Phone </th>
                        <th>ID No</th>
                        <th>Subjects </th>
                        <th>More </th>
                        <th>Update </th>
                        <th id ="statusCell">Status</th>

                        
                        
                        
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                       int count = 1;
                       String gender = "";
                       String formatedFirstname = " ";
                       String formatedLastname = " ";
                       String formatedSurname = " ";

                     
                       String staffid = "";
                       String staffCategory = "";
                          

                        if(staffList !=null){
                       for(Staff s : staffList) { 
                              staff_status = "";
                              staffid = s.getUuid();
                              staffCategory = s.getCategory();
					    if ((element = statisticsCache.get(accountuuid)) != null) {
					        statistics = (SessionStatistics) element.getObjectValue();
					    }

                         if(online.get(s.getUuid()) !=null){
					        value_sessionId = online.get(s.getUuid());
					        staff_status = "Online";
					        }else{
					        value_sessionId = "";
					         staff_status = "Offline";
					       }                               
  
                               
                             if(staffHash.get(s.getUuid()) !=null){
                             staffdetail = staffHash.get(s.getUuid());
                            formatedFirstname = StringUtils.capitalize(staffdetail.getFirstName().toLowerCase());
                            formatedLastname =StringUtils.capitalize(staffdetail.getLastName().toLowerCase());
                            formatedSurname = StringUtils.capitalize(staffdetail.getSurname().toLowerCase());
                                     }


                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + count + "</td>"); 
                             out.println("<td width=\"10%\" class=\"center\">" + positionHash.get(s.getPositionUuid())  + "</td>"); 
                             out.println("<td width=\"10%\" class=\"center\">" + s.getUserName() + "</td>"); 
                            if(staffdetail !=null){

                               if(StringUtils.equalsIgnoreCase(staffdetail.getGender(), "FEMALE")) {
                                gender = "F";
                                     }else{
                                    gender = "M";
                                 }

                           

                             out.println("<td width=\"8%\" class=\"center\">" + staffdetail.getEmployeeNo() + "</td>");
                             out.println("<td width=\"8%\" class=\"center\">" + formatedFirstname + "</td>"); 
                             out.println("<td width=\"8%\" class=\"center\">" + formatedLastname + "</td>");
                             out.println("<td width=\"8%\" class=\"center\">" + formatedSurname + "</td>");
                             out.println("<td width=\"5%\" class=\"center\">" + gender + "</td>"); 
                             out.println("<td width=\"8%\" class=\"center\">" + staffdetail.getPhone() + "</td>"); 
                             out.println("<td width=\"8%\" class=\"center\">" + staffdetail.getNationalID() + "</td>"); 
                            
                            
                                            }  %>


                             <%if(StringUtils.equalsIgnoreCase(staffCategory, "Teaching")) { %>

                                <td class="center" width="5%">
                                <form name="Subject" method="POST" action="mySubjects.jsp"> 
                                <input type="hidden" name="staffuuid" value="<%=s.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="Subject" id="submit" value="Subjects" /> 
                                </form>                          
                                </td>    
                                <%}else{%>   

                                <td class="center" width="5%">
                                <form name="Subject" method="POST" action=""> 
                                <input type="hidden" name="" value="<%=s.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="" id="submit" value="" /> 
                                </form>                          
                                </td>   

                                <%}%>      

                                <td class="center" width="5%">
                                <form name="view" method="POST" action="viewStaff.jsp"> 
                                <input type="hidden" name="staffuuid" value="<%=s.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="View" /> 
                                </form>                          
                                </td>   

                                <td class="center" width="5%">
                                <form name="update" method="POST" action="updateStaff.jsp"> 
                                <input type="hidden" name="staffuuid" value="<%=s.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="update" id="submit" value="Update" /> 
                                </form>                          
                                </td>   
                                  
                                  <%
                                    if(StringUtils.equalsIgnoreCase(staff_status,"Online")){
                                  %>

                                <td class="center" width="5%" >  
                                <%
                                    out.println("<p style='color:#FF4500;'>");                                 
                                    out.println(" " + staff_status);
                                    out.println("</p>");   
                                %>            
                                </td> 

                                <%
                                  }else{ %>

                                  <td class="center" width="5%" >  
                                  <%
                                    out.println("<p style='color:#8B4789;'>");                                 
                                    out.println(" " + staff_status);
                                    out.println("</p>");   
                                  %>            
                                 </td> 
 
                                <%}%> 



                             <%

                           count++;
                          } 
                     }
                    %>
                    
                    </tbody>
            </table> 
            
            </div>
       


    </div>

</div>

<script type="text/javascript">
	var statucell = staff_status;
	alert("statucels = " + statucell);
</script>


<jsp:include page="footer.jsp" />
