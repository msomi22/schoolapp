<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.ClassTeacherDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.ClassTeacher"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.StaffDetails"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

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
   
     String stffID = "";
     String classuuid = "";
     String room  ="";
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
    
     ClassTeacherDAO classTeacherDAO = ClassTeacherDAO.getInstance();
     RoomDAO roomDAO = RoomDAO.getInstance();

     HashMap<String, String> staffHash = new HashMap<String, String>();
     StaffDetailsDAO staffDetailsDAO = StaffDetailsDAO.getInstance();
     List<StaffDetails> staffdetailList = new ArrayList<StaffDetails>(); 
     staffdetailList = staffDetailsDAO.getSStaffDetailList();
      for(StaffDetails sd : staffdetailList){
          staffHash.put(sd.getStaffUuid(), StringUtils.capitalize(sd.getFirstName().toLowerCase())+" "+StringUtils.capitalize(sd.getLastName().toLowerCase())+" "+StringUtils.capitalize(sd.getSurname().toLowerCase()));
         }

     List<ClassTeacher> classteacherList = new ArrayList<ClassTeacher>(); 
     classteacherList = classTeacherDAO.getClassTeacherList();
     
     HashMap<String, String> classHash = new HashMap<String, String>();
     for(ClassTeacher ct : classteacherList){
         classHash.put(ct.getTeacherUuid() ,ct.getClassRoomUuid());
      }
    
     StaffDAO staffDAO = StaffDAO.getInstance();
     List<Staff> staffList = new ArrayList<Staff>(); 
     staffList = staffDAO.getStaffList(accountuuid);

     HashMap<String, String> roomHash = new HashMap<String, String>();
     List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
     classroomList = roomDAO.getAllRooms(accountuuid); 
      for(ClassRoom c : classroomList){
           roomHash.put(c.getUuid() , c.getRoomName());
            
            }

     StudentDAO studentDAO = StudentDAO.getInstance();
     List<Student> studentList = new ArrayList(); 
     int studentCount =0;
    
    

    DecimalFormat df = new DecimalFormat("0.00"); 
    df.setRoundingMode(RoundingMode.DOWN);
    String status = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
                                        

 %>






<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :CLASS TEACHERS PANEL: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>
       
       <li>
            <a href="studentIndex.jsp">Back</a> <span class="divider">/</span>
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
                                  

                           
                     %>


            
            


            <div>
                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>Teacher</th>
                        <th>Class </th>
                        <th>Students </th>
                        <th>ClassList </th>
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                       int count = 1;
                           if(staffList !=null){
                       for(Staff s : staffList) {
                             for(ClassTeacher ct : classteacherList) {
                             if(StringUtils.equals(s.getUuid(), ct.getTeacherUuid())) {

                                 stffID = ct.getTeacherUuid();
                                  ClassTeacher ct2 = new ClassTeacher();
                                 if(stffID !=null){  
                                   ct2 = classTeacherDAO.getClassTeacherByteacherId(stffID); 
                                   if(ct2 !=null){
                                   classuuid = ct2.getClassRoomUuid();
                                      }
                                  }

                                    studentList = studentDAO.getAllStudents(accountuuid,classuuid); 
                                    
                                     for(Student stude : studentList){
                                       if(StringUtils.equals(stude.getStatusUuid(), status)){
                                        stude.getUuid();
                                        studentCount++;
                                        }
                                      }


                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + count + "</td>"); 
                             out.println("<td width=\"15%\" class=\"center\">" + staffHash.get(s.getUuid())  + "</td>"); 
                             out.println("<td width=\"8%\" class=\"center\">" + roomHash.get(classHash.get(s.getUuid())) + "</td>"); 
                             out.println("<td width=\"8%\" class=\"center\">" + studentCount + "</td>"); 
                             studentCount = 0;
                              
                                   %>

                                <td class="center" width="5%">
                                <form name="edit" method="POST" action="exportExcel" target="_blank"> 
                                <input type="hidden" name="classroomuuid" value="<%=ct.getClassRoomUuid()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="ClassList" /> 
                                </form>                          
                                </td>  

                               <%
                        
                              }
                          } count++;
                      } 
                   }
                    %>
                    
                    </tbody>
            </table> 
            
            </div>
       


    </div>

</div>


<jsp:include page="footer.jsp" />
