
<%@page import="com.yahoo.petermwenda83.persistence.staff.TeacherSubjectDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.TeacherSubject"%>

<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.StreamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.persistence.subject.SubjectDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.subject.Subject"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>


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


<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
        <li> <a href="schoolIndex.jsp">Home</a></li>
        
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
         <li> <a href="classTeachers.jsp">Back</a> </li>
       </div>

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
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(addErrStr);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.STAFF_FIND_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(addsuccessStr);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.STAFF_FIND_SUCCESS, null);
                                  } 
                                    
                    
                                  

                           String stffNumber =""; 
                           String firstname ="";
                           String lastname =""; 
                           String surname ="";  
                           String staffUuid = paramHash.get("staffuuid"); 

                          if(StringUtils.isEmpty(paramHash.get("staffNumber"))){
                           stffNumber = " ";
                          }else{
                           stffNumber = paramHash.get("staffNumber"); 
                           }

                           if(StringUtils.isEmpty(paramHash.get("firstname"))){
                           firstname = " ";
                          }else{
                           firstname = paramHash.get("firstname"); 
                           }


                           if(StringUtils.isEmpty(paramHash.get("lastname"))){
                           lastname = " ";
                          }else{
                           lastname = paramHash.get("lastname"); 
                           }


                           if(StringUtils.isEmpty(paramHash.get("surname"))){
                           surname = " ";
                          }else{
                           surname = paramHash.get("surname"); 
                           }



                     %>



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> CLASS TEACHERS REGISTRATION : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> </h3>
            </div>
            <div class="panel-body">
              <div class="table-responsive ">

              <table class="table table-striped  ">
                <thead>
                    <tr >             
                        <th></th>
                        <th></th>
                        <th>Search</th>
                    </tr>
                </thead>   

                <tbody >

                              <form name="view" method="POST" action="findClassTeacher"> 

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
                        <th>Teacher Name</th>
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

            


            <h3><i class="icon-edit"></i> Assign class to Teacher:</h3>  

               <form  class="form-horizontal"   action="addClassTeacher" method="POST" >
               <fieldset>


                        <div class="form-group" id="divid">
                            <label class="col-sm-3 control-label" for="class">Class</label>
                            <div class="col-sm-9">
                                <select name="classId" class="form-control" required>
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


                            <div class="form-group">
                                  <div class="col-sm-9 col-sm-offset-3">
                                  <input type="hidden" name="staffid" value="<%=staffUuid%>">
                                  <input type="hidden" name="systemuser" value="<%=staffUsername%>">
                                  <button type="submit" name="Find" value="Find"   class="btn btn-primary  btn-block">Assign</button> 
                                  </div>
                            </div>



                 </fieldset>
                 </form>

      
         </div>  
       </div>  <!-- end panel body -->
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




