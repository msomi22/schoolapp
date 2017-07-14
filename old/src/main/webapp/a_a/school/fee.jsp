
<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.money.StudentFeeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.money.StudentFee"%>

<%@page import="com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee"%>

<%@page import="com.yahoo.petermwenda83.persistence.othermoney.OtherFeeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.otherfee.OtherFee"%>


<%@page import="com.yahoo.petermwenda83.persistence.money.TermFeeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.money.TermFee"%>

<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>


<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
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
    

             
      
    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
   

     
    Locale locale = new Locale("en","KE"); 
    NumberFormat nf = NumberFormat.getCurrencyInstance(locale);


   
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
        <li><a href="../resources/passwords.pdf" target="_blank">Password file</a>   </li>
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
           
         </small></h4>   
       </div>






      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
                 FEE MANAGEMENT FOR: 
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

                              <form name="view" method="POST" action="findStudentFee"> 

                               <td width="8%" class="center">                              
                              <p><b>Student Admission Number:</b><p>                                                    
                               </td> 

                                <td width="10%" class="center">                              
                                   <input class="input-xlarge focused" id="receiver" type="text" name="AdmNo" 
                                    value=""  required>                                                    
                               </td> 

                               <td width="10%" class="center">                                
                               <input type="hidden" name="schooluuid" value="<%=""%>">
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
                        <th>Statement</th>
                    </tr>
                </thead>   
                <tbody >
                    <%  
                               out.println("<tr>"); 
                               out.println("<td width=\"10%\" class=\"center\">" + "" + "</td>");  
                               out.println("<td width=\"10%\" class=\"center\">" + "" + "</td>");    
                             
                    %> 
                              
                               <td width="10%" class="center">    
                               <form name="view" method="POST" action="printStatement" target="_blank">                             
                               <input type="hidden" name="studentuuid" value="<%="" %>">
                               <input class="btn btn-success" type="submit" name="view" id="submit" value="Print" />
                               </form>      
                               </td>
                                                                  
                              

                </tbody>                  
            </table>  

       

           <h3><i class="icon-edit"></i>School Fee </h3> 


             <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr >             
                        <th>*</th>
                        <th>Ammount Paid</th>
                        <th>Receipt Number</th>
                        <th>Date Received</th>
                        <th>Update</th>
                        
                    </tr>
                </thead>   
                <tbody >
                    
                </tbody>                                 
            </table>  

            

            
            <table class="table  ">
                <thead>
                    <tr >             
                        <th>Totals</th>
                    </tr>
                </thead>   
                <tbody >
                   

                </tbody>


                                   
            </table>  




            <table class="table  ">
                <thead>
                    <tr >             
                        <th>Total paid</th>
                        <th>Balance</th>
                    </tr>
                </thead>   
                <tbody >
                    <% /* 
                               double mybalance = 0.0;   

                               if(studentAmount !=null){
                                    mybalance =  studentAmount.getAmount();
                                  }


                               double termfee = termFee.getTermAmount();
                               double balance =  termfee - mybalance;
                               out.println("<tr>"); 
                               out.println("<td width=\"10%\" class=\"center\">" + nf.format(mybalance) + "</td>");  
                               out.println("<td width=\"10%\" class=\"center\">" + nf.format(balance) + "</td>");       */
                             
                    %> 

                </tbody>


                                   
            </table>  



                 <h3><i class="icon-edit"></i>Other Payments </h3> 


            <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr >             
                        <th>*</th>
                        <th>Item Type</th>
                        <th>Item Cost</th>
                        <th>Item Term </th>
                        <th>Item Year </th>
                        <th>Amount Paid</th>
                        <th>Term Paid</th>
                        <th>Year Paid</th>
                        <th>Revert</th>
                       
                        
                        
                        
                        
                    </tr>
                </thead>   
                <tbody >
                    
                               

                </tbody>                                 

             
                  
               </table>

    </div>  
  </div> <!-- end panel body -->
                               <div class="panel-footer">
                                        <div class="row">
                                          <div class="col col-xs-4"> <small> <i>Niaje! Wanafunzi walipe dooh.</i> </small>
                                          </div>
                                        </div>
                              </div>
</div> <!-- end panel -->
</div>
</div>
</div>


<jsp:include page="footer.jsp" />





