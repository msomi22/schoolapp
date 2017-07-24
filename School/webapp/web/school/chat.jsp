<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.StaffDetails"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%>

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
    
    
     StaffDAO staffDAO = StaffDAO.getInstance();
     List<Staff> staffList = new ArrayList<Staff>(); 
     staffList = staffDAO.getStaffList(accountuuid);
   
     ServletContext context = getServletContext();
     Map<String,String> online =  (HashMap<String,String>)context.getAttribute("onlineUsersMap");
     String value_sessionId = "";
     String staff_status = "";
     
 %>






<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :CHAT : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="profile.jsp">Back</a> <span class="divider">/</span>
        </li>
        
    </ul>
</div>




<div class="row-fluid sortable">

               
    <div class="box span12">S
        <div class="box-content">
            

            <div>
                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                       int count = 1;
                       String gender = "";
                       String formatedFirstname = " ";
                       String formatedLastname = " ";
                       

                        if(staffList !=null){
                       for(Staff s : staffList) { 

                           staff_status = "";
                            
                         if(online.get(s.getUuid()) !=null){
                            value_sessionId = online.get(s.getUuid());
                            staff_status = "Online";
                            }else{
                            value_sessionId = "";
                             staff_status = "Offline";
                           }  

                           if(!StringUtils.equalsIgnoreCase(stffID,s.getUuid()))   {                          
  
                               
                             if(staffHash.get(s.getUuid()) !=null){

                                   staffdetail = staffHash.get(s.getUuid());
                                   formatedFirstname = StringUtils.capitalize(staffdetail.getFirstName().toLowerCase());
                                   formatedLastname = StringUtils.capitalize(staffdetail.getLastName().toLowerCase());
                        
                                     }

                               if(staffdetail !=null ){

                                     out.println("<tr>"); 
                                     out.println("<td width=\"2%\" >" + count + "</td>"); 
                                     out.println("<td width=\"5%\" class=\"center\">" + formatedFirstname + " " +formatedLastname + "</td>"); 
                             
                                            }  %>
                                  
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

                                <td class="center" width="5%">
                                    <form name="update" method="POST" action="startChat.jsp"> 
                                    <input type="hidden" name="senderID" value="<%=stffID%>">
                                    <input type="hidden" name="RecepientID" value="<%=s.getUuid()%>">
                                    <input class="btn btn-success" type="submit" name="update" id="submit" value="Start Chat" /> 
                                    </form>                          
                                </td>   

                             <%

                           count++;
                          } 
                        }
                     }
                    %>
                    
                    </tbody>
            </table> 
            
            </div>
       


    </div>

</div>

<jsp:include page="footer.jsp" />
