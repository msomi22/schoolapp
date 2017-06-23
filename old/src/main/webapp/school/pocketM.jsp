
<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="com.yahoo.petermwenda83.bean.money.PocketMoney"%>
<%@page import="com.yahoo.petermwenda83.bean.money.Deposit"%>
<%@page import="com.yahoo.petermwenda83.bean.money.Withdraw"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>

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
   

     
     Locale locale = new Locale("en","KE"); 
     NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

     //date format
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
   
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
         <h4><small> 
            POCKET MOMNEY : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%>   
         </small></h4>   
       </div>

                 <%
                    HashMap<String, Student> paramHash = (HashMap<String, Student>) session.getAttribute(SessionConstants.STUENT_POCKET_MONEY_FIND_PARAM);

                    HashMap<String, PocketMoney> pocketMHash = (HashMap<String, PocketMoney>) session.getAttribute(SessionConstants.STUENT_POCKET_MONEY_PARAM);

                    HashMap<String, List<Deposit> > depositListHash = (HashMap<String, List<Deposit> >) session.getAttribute(SessionConstants.STUENT_PM_DEPOSITE_PARAM);
                    HashMap<String, List<Withdraw> > withdrawListHash = (HashMap<String, List<Withdraw> >) session.getAttribute(SessionConstants.STUENT_PM_WITHDRAW_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, Student>();
                            }

                       if (pocketMHash == null) {
                             pocketMHash = new HashMap<String, PocketMoney >();
                            }

                       if (depositListHash == null) {
                             depositListHash = new HashMap<String, List<Deposit> >();
                            }

                      if (withdrawListHash == null) {
                             withdrawListHash = new HashMap<String, List<Withdraw> >();
                            }
                   
                     

                   Student student = new Student();
                   student = paramHash.get("studentObj");

                    PocketMoney money = new PocketMoney();
                    money = pocketMHash.get("pocketmObject");
                    double balance = 0.0;

                    if(money !=null){
                            balance = money.getAmount();
                     }
                    

                    List<Deposit> depositList = new ArrayList<Deposit>();
                    depositList = depositListHash.get("depositList");
                   // out.print(depositList);  

                    List<Withdraw> withdrawList = new ArrayList<Withdraw>();
                    withdrawList = withdrawListHash.get("withdrawList");

                               

                         
                     String fullname = "";

                     String formatedFirstname = "";
                     String formatedLastname = "";
                     String formatedSurname = "";

                     String firstNameLowecase  = "";
                     String lastNameLowecase  = "";
                     String surNameLowecase  = "";

                     String admNumber ="";  
                     String studentuuid ="";                              

                               if(student !=null){

                                   admNumber = student.getAdmno();
                                   studentuuid = student.getUuid();
                                   firstNameLowecase = StringUtils.capitalize(student.getFirstname().toLowerCase());
                                   lastNameLowecase = StringUtils.capitalize(student.getLastname().toLowerCase());
                                   surNameLowecase = StringUtils.capitalize(student.getSurname().toLowerCase());

                                   fullname = firstNameLowecase +" "+lastNameLowecase+" "+surNameLowecase;
 
                                 }
                               
                                

                                String addError = "";
                                String addsuccess = "";
                                session = request.getSession(false);
                                addError = (String) session.getAttribute(SessionConstants.STUDENT_FIND_ERROR);
                                addsuccess = (String) session.getAttribute(SessionConstants.STUDENT_FIND_SUCCESS); 

                                  if (StringUtils.isNotEmpty(addError)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(addError);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccess)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(addsuccess);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.STUDENT_FIND_SUCCESS, null);
                                  }      

                          

                          double amountDeposited= 0.0;
                          String depositdate = "";
                          String deposituser = "";

                          double amountwithdrawn = 0.0;
                          String withdrawdate = "";
                          String withdrawuser = "";

                           double totalwithd = 0;
                           double totaldeposit = 0;
                       
                           
                     %>
      



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
                   POCKET MOMNEY : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%>
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

                              <form name="view" method="POST" action="findStudentPM"> 

                               <td width="8%" class="center">                              
                                  <p><b>Student Admission Number:</b><p>                                                    
                               </td> 

                                <td width="10%" class="center">                              
                                       <input class="input-xlarge focused" id="receiver" type="text" name="AdmNo" 
                                        value=""  required>                                                    
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


                <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr >
                        <th>Pocket Money Balance</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>   
                <tbody >
                    <%

                     out.println("<tr>"); 
                     out.println("<td width=\"10%\" class=\"center\">" + nf.format(balance) + "</td>");  
                    %>          
                               <td width="10%" class="center">    
                               <form name="view" method="POST" action="deposit.jsp">                          
                                <input type="hidden" name="admissionNo" value="<%=admNumber%>">
                                <input type="hidden" name="fullname" value="<%=fullname%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Deposit" />                                                         
                                </form> 
                                </td> 

                                <td width="10%" class="center">    
                               <form name="view" method="POST" action="withdraw.jsp">                          
                                <input type="hidden" name="admissionNo" value="<%=admNumber%>">
                                <input type="hidden" name="fullname" value="<%=fullname%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Withdraw" />                                                         
                                </form> 
                                </td> 
                </tbody>
            </table>  




            <p> Deposit History</p>
            <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr >
                        <th>*</th>
                        <th>Amount Deposited</th>                
                        <th>Date</th>
                        <th>System User</th>
                    </tr>
                </thead>   
                <tbody >
                    <%
                    int dcount = 1;
                    if(depositList !=null){
                    for(Deposit d : depositList){

                         amountDeposited = d.getAmount();
                         totaldeposit = totaldeposit + amountDeposited;
                         depositdate = dateFormatter.format(d.getDateCommitted());
                         deposituser = d.getSystemUser();
                         out.println("<tr>"); 
                         out.println("<td width=\"3%\" >" + dcount + "</td>"); 
                         out.println("<td width=\"10%\" class=\"center\">" + nf.format(amountDeposited) + "</td>"); 
                         out.println("<td width=\"10%\" class=\"center\">" + depositdate  + "</td>"); 
                         out.println("<td width=\"10%\" class=\"center\">" + deposituser + "</td>");  
                         dcount++;
                       }
                    }
                            
                    %>
                </tbody>
            </table>  


            <p> Withdraw History</p>
            <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr >
                        <th>*</th>
                        <th>Amount Withdrawn</th>                
                        <th>Date</th>
                        <th>System User</th>
                    </tr>
                </thead>   
                <tbody >
                    <%
                    int wcount = 1;
                    if(withdrawList !=null){
                    for(Withdraw w : withdrawList){
                        amountwithdrawn = w.getAmount();
                        totalwithd = totalwithd + amountwithdrawn; 
                        withdrawdate = dateFormatter.format(w.getDateCommitted());
                        withdrawuser = w.getSystemUser();
                         out.println("<tr>"); 
                         out.println("<td width=\"3%\" >" + wcount + "</td>"); 
                         out.println("<td width=\"10%\" class=\"center\">" + nf.format(amountwithdrawn) + "</td>"); 
                         out.println("<td width=\"10%\" class=\"center\">" + withdrawdate  + "</td>"); 
                         out.println("<td width=\"10%\" class=\"center\">" + withdrawuser + "</td>");  
                         wcount++; 
                        }
                    }
                            
                    %>
                </tbody>
            </table>  


            <p> Analysis </p>
            <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr >
                        <th>Total Deposited</th>  
                        <th>Total Withdrawn</th>                
                        <th>Balance</th>
                    </tr>
                </thead>   
                <tbody >
                    <%
                     out.println("<tr>"); 
                     out.println("<td width=\"10%\" class=\"center\">" + nf.format(totaldeposit) + "</td>"); 
                     out.println("<td width=\"10%\" class=\"center\">" + nf.format(totalwithd) + "</td>");  
                     out.println("<td width=\"10%\" class=\"center\">" + nf.format(balance) + "</td>");  
                            
                    %>
                </tbody>
            </table>  

           
  </div>
</div>  <!-- end panel body-->
                          <div class="panel-footer">
                                <div class="row">
                                    <div class="col col-xs-4"> <small> <i>No pocket money no comfort.</i> </small>
                                          </div>
                              </div>
                        </div>
</div>   <!-- end panel body-->
</div>
</div>
</div>


<jsp:include page="footer.jsp" />

