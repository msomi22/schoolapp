<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>



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
          <li>  <a href="parents.jsp">Back</a> </li>
       </div>

     
                <%
                    HashMap<String, String> paramHash = (HashMap<String, String>) session.getAttribute(SessionConstants.PARENT_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }

                    HashMap<String, String> fatherMotherParamHash = (HashMap<String, String>) session.getAttribute(SessionConstants.FATHER_MOTHER_PARAM);

                        if (fatherMotherParamHash == null) {
                             fatherMotherParamHash = new HashMap<String, String>();
                            }
                             
                       

                                String findError = "";
                                String findsuccess = "";
                                String addError = "";
                                String addsuccess = "";

                                session = request.getSession(false);
                                findError = (String) session.getAttribute(SessionConstants.STUDENT_FIND_ERROR);
                                findsuccess = (String) session.getAttribute(SessionConstants.STUDENT_FIND_SUCCESS); 
                                addError = (String) session.getAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR);
                                addsuccess = (String) session.getAttribute(SessionConstants.FATHER_MOTHER_ADD_SUCCESS); 


                                if (StringUtils.isNotEmpty(findError)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(findError);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(findsuccess)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(findsuccess);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.STUDENT_FIND_SUCCESS, null);
                                  } 


                                   if (StringUtils.isNotEmpty(addError)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(addError);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccess)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(addsuccess);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.FATHER_MOTHER_ADD_SUCCESS, null);
                                  } 




                           String admNumber =""; 

                           String fullname = "";

                           String firstname ="";
                           String lastname =""; 
                           String surname ="";  

                           String formatedFirstname = "";
                           String formatedLastname = "";
                           String formatedSurname = "";

                          if(StringUtils.isEmpty(paramHash.get("admNumber"))){
                           admNumber = " ";
                          }else{
                           admNumber = paramHash.get("admNumber"); 
                           }

                           if(StringUtils.isEmpty(paramHash.get("firstname"))){
                           firstname = " ";
                          }else{
                           firstname = paramHash.get("firstname"); 
                           String firstNameLowecase = firstname.toLowerCase();
                            formatedFirstname = firstNameLowecase.substring(0,1).toUpperCase()+firstNameLowecase.substring(1);
                           }


                           if(StringUtils.isEmpty(paramHash.get("lastname"))){
                           lastname = " ";
                          }else{
                           lastname = paramHash.get("lastname"); 
                            String lastNameLowecase = lastname.toLowerCase();
                            formatedLastname = lastNameLowecase.substring(0,1).toUpperCase()+lastNameLowecase.substring(1);
                           }


                           if(StringUtils.isEmpty(paramHash.get("surname"))){
                           surname = " ";
                          }else{
                           surname = paramHash.get("surname"); 
                           String surNameLowecase = surname.toLowerCase();
                           formatedSurname = surNameLowecase.substring(0,1).toUpperCase()+surNameLowecase.substring(1);

                           }
                           fullname = formatedFirstname+" "+formatedLastname+" "+formatedSurname;

                     %>




      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">(PARENT/GUARDIAN INFORMATION): TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%></h3>
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

                              <form name="view" method="POST" action="findStudentP"> 

                               <td width="8%" class="center">                              
                              <p><b>Student Admission Number:</b><p>                                                    
                               </td> 

                                <td width="10%" class="center">                              
                                   <input class="form-control"  type="text" name="AdmNo" 
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
            </div>

                <form  class="form-horizontal"   action="addStudentParent" method="POST" >
              
                        <h3><i class="icon-edit"></i>Parent 1 (Parent to receive SMS-Father) :</h3> 

                        <div class="form-group">
                                <label class="col-sm-3 control-label" for="FatherName">Parent 1 Full Name*:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="FatherName" required
                                      value='<%=StringUtils.trimToEmpty(fatherMotherParamHash.get("FatherName"))%>' style="text-transform: capitalize;" />
                                </div>
                            </div> 

                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="FatherPhone">Parent 1 Phone*:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="FatherPhone" required
                                      value='<%=StringUtils.trimToEmpty(fatherMotherParamHash.get("FatherPhone"))%>' >
                                </div>
                            </div> 

                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="FatherOccupation">Parent 1 Occupation:</label>
                                <div class="col-sm-9">
                                <input class="form-control" type="text" name="FatherOccupation"
                                      value='<%= StringUtils.trimToEmpty(fatherMotherParamHash.get("FatherOccupation"))%>' style="text-transform: capitalize;" >
                                </div>
                            </div> 


                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="FatherID">Parent 1 ID No:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="FatherID"
                                      value='<%=StringUtils.trimToEmpty(fatherMotherParamHash.get("FatherID"))%>'  >
                                </div>
                            </div> 

                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="FatherEmail">Parent 1 Email:</label>
                                <div class="col-sm-9">
                                <input class="form-control" type="text" name="FatherEmail"
                                      value='<%=StringUtils.trimToEmpty(fatherMotherParamHash.get("FatherEmail"))%>'  >
                                </div>
                            </div> 


                         <h3><i class="icon-edit"></i>Parent 2 (Optional-Mother) :</h3> 

                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="MotherName">Parent 2 Full Name:</label>
                                <div class="col-sm-9">
                                <input class="form-control" type="text" name="MotherName"
                                      value='<%= StringUtils.trimToEmpty(fatherMotherParamHash.get("MotherName"))%>' style="text-transform: capitalize;" >
                                </div>
                            </div> 

                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="MotherPhone">Parent 2 Phone:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="MotherPhone"
                                      value='<%= StringUtils.trimToEmpty(fatherMotherParamHash.get("MotherPhone"))%>' >
                                </div>
                            </div> 

                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="MotherOccupation">Parent 2 Occupation:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="MotherOccupation"
                                      value='<%= StringUtils.trimToEmpty(fatherMotherParamHash.get("MotherOccupation"))%>' style="text-transform: capitalize;" >
                                </div>
                            </div> 

                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="MotherEmail">Parent 2 Email:</label>
                                <div class="col-sm-9">
                                <input class="form-control" type="text" name="MotherEmail"
                                      value='<%=StringUtils.trimToEmpty(fatherMotherParamHash.get("MotherEmail"))%>'  >
                                </div>
                            </div> 

                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="MotherID">Parent 2 ID No:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="MotherID"
                                      value='<%=StringUtils.trimToEmpty(fatherMotherParamHash.get("MotherID"))%>'  >
                                </div>
                            </div> 

                    <h3><i class="icon-edit"></i>Person to be contacted if Parents are not available</h3>  

                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="RelativeName">Full Name:</label>
                                <div class="col-sm-9">
                                <input class="form-control" type="text" name="RelativeName"
                                      value='<%=StringUtils.trimToEmpty(fatherMotherParamHash.get("RelativeName"))%>' style="text-transform: capitalize;" >
                                </div>
                            </div> 

                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="RelativePhone"> Phone Number:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="RelativePhone"
                                      value='<%= StringUtils.trimToEmpty(fatherMotherParamHash.get("RelativePhone"))%>'  >
                                </div>
                            </div> 

                            
                            <div class="form-group">
                               <div class="col-sm-9 col-sm-offset-3">
                                  <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                   <input type="hidden" name="studentUuid" value='<%= StringUtils.trimToEmpty(paramHash.get("studentuuid")) %>'>
                                  <button type="submit" class="btn btn-primary btn-block">Register</button>
                                </div>
                            </div> 

              </form>

    </div> <!-- end panel body-->
     <div class="panel-footer">
        <div class="row">
          <div class="col col-xs-4"> <small> <i>Life like there is no tomorrow.</i> </small>
        </div>
    </div>
    </div>
  </div>
</div>
</div>
</div>


<jsp:include page="footer.jsp" />






































