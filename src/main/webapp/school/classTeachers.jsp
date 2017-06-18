<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

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




<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"><a href="schoolIndex.jsp">Home</a></li>
       
      </ul><br>
      <div class="input-group">
        <input type="text" class="form-control" placeholder="Search Anything...">
        <span class="input-group-btn">
          <button class="btn btn-default" type="button">
            <span class="glyphicon glyphicon-search"></span>
          </button>
        </span>
      </div>
       <hr>
      <div id="myCarousel" class="carousel slide">
          <!-- Carousel indicators -->
            <ol class="carousel-indicators">
              <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
                  <li data-target="#myCarousel" data-slide-to="2"></li>
                  </ol>
                  <!-- Carousel items -->
                  <div class="carousel-inner">
                  <div class="item active">
                  <img src="../img/slide/slide1.jpg" alt="First slide">
                  <div class="carousel-caption">This Caption 1</div>
                  </div>
                  <div class="item">
                  <img src="../img/slide/slide2.jpg" alt="Second slide">
                  <div class="carousel-caption">This Caption 2</div>
                  </div>
                  <div class="item">
                  <img src="../img/slide/slide3.jpg" alt="Third slide">
                  <div class="carousel-caption">This Caption 3</div>
                  </div>
                  </div>
                  <!-- Carousel nav -->
              <a class="carousel-control left" href="#myCarousel"
                  data-slide="prev">&lsaquo;</a>
              <a class="carousel-control right" href="#myCarousel"
                  data-slide="next">&rsaquo;</a>
          </div>
          <hr>
    </div>

    <div class="col-sm-9">
        <div class="breadcrumb">
            <li> <a href="staff.jsp">Back</a> </li>
            <li> <a href="addclassTecaher.jsp">New ClassTeacher</a> </li>
       </div>




                <%             
                                String addErrStr = "";
                                String addsuccessStr = "";
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.PROMOTE_CALSS_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.PROMOTE_CALSS_SUCCESS);                         

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    %>
                                    <div class="alert alert-warning">
                                    <a href="#" class="close" data-dismiss="alert">
                                          &times;
                                    </a>
                                    <strong>Warning!</strong> 
                                           <%
                                          out.println("error: " + addErrStr);
                                           %>
                                    </div>

                                    <%                                 
                                    session.setAttribute(SessionConstants.PROMOTE_CALSS_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                   %>
                                    <div class="alert alert-success">
                                    <a href="#" class="close" data-dismiss="alert">
                                          &times;
                                    </a>
                                    <strong>Success!</strong> 
                                           <%
                                           out.println(": " + addsuccessStr);
                                           %>
                                    </div>

                                    <%                                
                                    session.setAttribute(SessionConstants.PROMOTE_CALSS_SUCCESS,null);
                                  } 



                      %>
      



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">Class-teachers : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> </h3>
            </div>
            <div class="panel-body">
              
              <div class="table-responsive ">
                    <table class="table table-bordered bootstrap-datatable datatable">
                    <thead>
                        <tr>
                        <th>*</th>
                        <th>Teacher</th>
                        <th>Class </th>
                        <th>Students </th>
                        <th>Results </th>
                        <th>Class List </th>
                        <th>Mark Sheet </th>
                        <th>FeeList </th>
                        <th>Action</th>
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
                              

                                
                                 ClassRoom cr = roomDAO.getroom(accountuuid, classuuid);
                                  if(cr !=null){
                                  room = cr.getRoomName(); 
                                   }
                                   
                                    final String FORM1 = "FORM 1";
                                    final String FORM2 = "FORM 2";
                                    final String FORM3 = "FORM 3";
                                    final String FORM4 = "FORM 4";



                                if(StringUtils.contains(room, FORM1)){ 
                                            %>
                                    <td class="center" width="5%">
                                    <form name="edit" method="POST" action="exam.jsp"> 
                                    <input type="hidden" name="classroomuuid" value="<%=ct.getClassRoomUuid()%>">
                                    <input type="hidden" name="staffid" value="<%=ct.getTeacherUuid()%>">
                                    <input class="btn btn-success" type="submit" name="edit" id="submit" value="Results" /> 
                                    </form>  
                                    </td>  
                                           <%
                                  }else if(StringUtils.contains(room, FORM2)){
                                   %>

                                <td class="center" width="5%">
                                <form name="edit" method="POST" action="exam2.jsp"> 
                                <input type="hidden" name="classroomuuid" value="<%=ct.getClassRoomUuid()%>">
                                <input type="hidden" name="staffid" value="<%=ct.getTeacherUuid()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Results" /> 
                                </form>  
                                </td>  

                               <%

                                }else if(StringUtils.contains(room, FORM3)){
                                   %>

                                <td class="center" width="5%">
                                <form name="edit" method="POST" action="examIndex.jsp"> 
                                <input type="hidden" name="classroomuuid" value="<%=ct.getClassRoomUuid()%>">
                                <input type="hidden" name="staffid" value="<%=ct.getTeacherUuid()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Results" /> 
                                </form>  
                                </td>  

                               <%

                                }else if(StringUtils.contains(room, FORM4)){
              
                                %>

                                <td class="center" width="5%">
                                <form name="edit" method="POST" action="examIndex4.jsp"> 
                                <input type="hidden" name="classroomuuid" value="<%=ct.getClassRoomUuid()%>">
                                <input type="hidden" name="staffid" value="<%=ct.getTeacherUuid()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Results" /> 
                                </form>  
                                </td>  

                                  <%
                                }




                               %> 
                                <td class="center" width="5%">
                                <form name="edit" method="POST" action="exportExcel" target="_blank"> 
                                <input type="hidden" name="classroomuuid" value="<%=ct.getClassRoomUuid()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Class List" /> 
                                </form>                          
                                </td>  


                                <td class="center" width="5%">
                                <form name="edit" method="POST" action="exportStudentPerClass"> 
                                <input type="hidden" name="classroomuuid" value="<%=ct.getClassRoomUuid()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Mark Sheet" /> 
                                </form>                          
                                </td>  

                                <td class="center" width="5%">
                                <form name="edit" method="POST" action="perClassFinanceReport" target="_blank"> 
                                <input type="hidden" name="classroomuuid" value="<%=ct.getClassRoomUuid()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Fee List" /> 
                                </form>                          
                                </td>  


                                 <td class="center" width="5%">
                                <form name="edit" method="POST" action="deleteTeacherClasss"> 
                                <input type="hidden" name="currentclassroomuuid" value="<%=ct.getClassRoomUuid()%>">
                                <input type="hidden" name="teacheruuid" value="<%=ct.getTeacherUuid()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Remove" /> 
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
        </div>   <!-- end panel body-->
                       <div class="panel-footer">
                              <div class="row">
                            <div class="col col-xs-4"> <small> <i>Respect for class-teachers! </i> </small>
                            </div>
                          </div>
                    </div>
      </div>  <!-- end panel -->
    </div> 
  </div>
</div>

<jsp:include page="footer.jsp" />

