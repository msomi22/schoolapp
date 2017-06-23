<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.persistence.money.TermFeeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.money.TermFee"%>

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

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>


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


    TermFeeDAO termFeeDAO = TermFeeDAO.getInstance();

    TermFee termFee  = new TermFee();

    if(termFeeDAO.getFee(accountuuid,examConfig.getTerm(),examConfig.getYear()) !=null){
           termFee = termFeeDAO.getFee(accountuuid,examConfig.getTerm(),examConfig.getYear());
       }
   

   
    

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);

    Locale locale = new Locale("en","KE"); 
    NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

    String schoolfee = "";

    if(StringUtils.equalsIgnoreCase(school.getDayBoarding(), "YES")){
        schoolfee = " BOARDING FEE: " + nf.format(termFee.getTermAmount()) + " " + " DAY FEE: " + nf.format(termFee.getDayAmount());
     }else{
        schoolfee = " FEE: " + nf.format(termFee.getTermAmount());
    }
     

 %>
    






<jsp:include page="header.jsp" />




<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
       <li class="active"><a href="schoolIndex.jsp">Home</a></li>
        <li>  <a href="addFee.jsp">Pay Fee</a> </li>
        <li> <a href="newPayment.jsp">Other Payments</a> </li>
        <li> <a href="pocketM.jsp">Pocket Money</a>  </li>
        <li> <a href="studentClearance.jsp">Student Clearance</a> </li>
        <li> <a href="feeList.jsp">Fee Balance List</a></li>
        <li> <a href="changeTermYear.jsp">Update Term/Year</a> </li>
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
         <h4><small> 
        FEE MANAGEMENT FOR: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear() %>  <%=schoolfee%>  
         </small></h4>   
       </div>


                    <%
                    HashMap<String, Student> paramHash = (HashMap<String, Student>) session.getAttribute(SessionConstants.STUENT_PARAM_F);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, Student>();
                            }


                        HashMap<String, String> paramHash2 = (HashMap<String, String>) session.getAttribute(SessionConstants.STUENT_FEE_ADD_PARAM);

                        if (paramHash2 == null) {
                             paramHash2 = new HashMap<String, String>();
                            }    


                               Student student = new Student();
                               student = paramHash.get("studentObj");






                     String fullname = "";

                     String formatedFirstname = "";
                     String formatedLastname = "";
                     String formatedSurname = "";

                     String firstNameLowecase  = "";
                     String lastNameLowecase  = "";
                     String surNameLowecase  = "";

                     String admNumber ="";    
                     String studentUuid ="";
                     String studentType = "";                            

                     if(student !=null){

                         admNumber = student.getAdmno();
                         studentUuid = student.getUuid();
                         studentType = student.getStudentType();

                         firstNameLowecase = student.getFirstname().toLowerCase();
                         lastNameLowecase =student.getLastname().toLowerCase();
                         surNameLowecase = student.getSurname().toLowerCase();

                         formatedFirstname = firstNameLowecase;
                         formatedLastname = lastNameLowecase;
                         formatedSurname = surNameLowecase; 

                         fullname = formatedFirstname +" "+formatedLastname+" "+formatedSurname + "(" + studentType + ")";
 
                       }



                    
                                String addErrStr = "";
                                String addsuccessStr = "";
                             
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.STUDENT_FIND_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.STUDENT_FIND_SUCCESS); 

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(addErrStr);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(addsuccessStr);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.STUDENT_FIND_SUCCESS, null);
                                  } 
                                    



                     %>
      



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
                 FEE MANAGEMENT PANEL FOR: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear() %>  <%=schoolfee%> 
              </h3>
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

                              <form name="view" method="POST" action="findStudent"> 

                               <td width="8%" class="center">                              
                              <p><b>Student Admission Number:</b><p>                                                    
                               </td> 

                                <td width="10%" class="center">                              
                                   <input class="input-xlarge focused" id="receiver" type="text" name="AdmNo" 
                                    value="" required >                                                    
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









                               
                             
                                <p>Fields marked with a * are compulsory.</p>

                  <form  class="form-horizontal"   action="addFeeDetails" method="POST" autocomplete="off"> <!-- addFeeDetails -->
                  <fieldset>
                              
                              <div class="control-group">
                                  <label class="control-label" for="name">Amount Paid*:</label>
                                  <div class="controls">
                                      <input class="input-xlarge focused" id="payAmount" type="text" name="Amountpaid"
                                        value='<%=StringUtils.trimToEmpty(paramHash2.get("Amountpaid"))%>' required >
                                  </div>
                              </div> 

                              <div class="control-group">
                                  <label class="control-label" for="name">Bank Slip Number*:</label>
                                  <div class="controls">
                                      <input class="input-xlarge focused" id="slipNumber" type="text" name="slipNumber"
                                        value='<%=StringUtils.trimToEmpty(paramHash2.get("slipNumber"))%>' required >
                                  </div>
                              </div> 

                              <div class="control-group">
                                  <label class="control-label" for="schoolpassword">School Password*:</label>
                                  <div class="controls">
                                      <input class="input-xlarge focused" id="schoolPassword" type="text" name="schoolpassword"
                                        value="" style="color: white;" autocomplete="off" required>
                                  </div>
                              </div> 

                               
                              
                              
                              <div class="form-actions">
                                   <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                   <input type="hidden" name="systemuser" value="<%=staffUsername%>">
                                   <input type="hidden" name="studentuuid" value="<%=studentUuid%>">
                                  <button type="submit" class="btn btn-primary" >Pay</button> <!-- onclick="save(event)" -->
                              </div> 

              </fieldset>
              </form>

    </div>  
    </div> <!-- end panel body-->  

                                  <div class="panel-footer">
                                        <div class="row">
                                          <div class="col col-xs-4"> <small> <i>Pesa ilipweeeee!</i> </small>
                                          </div>
                                        </div>
                                </div>

    </div>  <!-- end panel -->  
    </div> 
  </div>
</div>


<script type="text/javascript">
  function save(e){
     var pay_amount = document.getElementById('payAmount').value;
     var slip_number = document.getElementById('slipNumber').value;
     var school_password = document.getElementById('schoolPassword').value;
     var school_uuid = "<%=accountuuid%>";
     var system_user = "<%=staffUsername%>";
     var student_uuid = "<%=studentUuid%>";
     
     $.ajax({
            url:"addFeeDetails",
            method:"POST",
            data:{Amountpaid:pay_amount,slipNumber:slip_number,schoolpassword:school_password,schooluuid:school_uuid,systemuser:system_user,studentuuid:student_uuid},
            dataType:"text",
            success:function(response){
            $('#payAmount').val(""); 
            $('#slipNumber').val(""); 
            $('#schoolPassword').val("");  
            alert(response); 
              }
           });
    
     

  }
</script>
<jsp:include page="footer.jsp" />




