
<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.money.StudentFeeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.money.StudentFee"%>

<%@page import="com.yahoo.petermwenda83.persistence.othermoney.StudentOtherMoniesDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.othermoney.StudentOtherMonies"%>

<%@page import="com.yahoo.petermwenda83.persistence.othermoney.OtherstypeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.othermoney.Otherstype"%>

<%@page import="com.yahoo.petermwenda83.persistence.othermoney.TermOtherMoniesDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.othermoney.TermOtherMonies"%>

<%@page import="com.yahoo.petermwenda83.persistence.money.TermFeeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.money.TermFee"%>

<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

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
    

    SchoolAccount school = new SchoolAccount();
    Element element;
   

    int incount = 0;  // Generic counter
    String accountuuid = "";
    String schoolname = "";
   

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }
     
     if(school !=null){
      accountuuid = school.getUuid();
      schoolname = school.getSchoolName();
      }

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = new ExamConfig();
    if(examConfigDAO.getExamConfig(accountuuid) !=null){
        examConfig = examConfigDAO.getExamConfig(accountuuid);
       }



     StudentOtherMoniesDAO studentOtherMoniesDAO = StudentOtherMoniesDAO.getInstance();
     List<StudentOtherMonies> stuOthermoniList = new ArrayList<StudentOtherMonies>(); 

     TermOtherMoniesDAO termOtherMoniesDAO = TermOtherMoniesDAO.getInstance();
     List<TermOtherMonies> termOtherMoniesList = new ArrayList<TermOtherMonies>(); 
     termOtherMoniesList = termOtherMoniesDAO.getTermOtherMoniesList(accountuuid);  

      HashMap<String, Double> tomHash = new HashMap<String, Double>(); 
     for(TermOtherMonies toml: termOtherMoniesList){
       tomHash.put(toml.getOtherstypeUuid(),toml.getAmount());
       }


      
      
      
     OtherstypeDAO otherstypeDAO = OtherstypeDAO.getInstance();
     List<Otherstype> othertypeList = new ArrayList<Otherstype>(); 
     othertypeList = otherstypeDAO.gettypeList(accountuuid);  

      HashMap<String, String> moneytypeHash = new HashMap<String, String>(); 
      HashMap<String, String> termHash = new HashMap<String, String>(); 
      HashMap<String, String> yearHash = new HashMap<String, String>(); 

      
    TermFeeDAO termFeeDAO = TermFeeDAO.getInstance();

    TermFee termFee  = new TermFee();

    if(termFeeDAO.getFee(accountuuid,examConfig.getTerm(),examConfig.getYear()) !=null){
           termFee = termFeeDAO.getFee(accountuuid,examConfig.getTerm(),examConfig.getYear());
       }
   

     if(othertypeList !=null){
     for(Otherstype om : othertypeList){
         moneytypeHash.put(om.getUuid(),om.getType());
         termHash.put(om.getUuid(),om.getTerm());
         yearHash.put(om.getUuid(),om.getYear());
         }
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



<div>
    <ul class="breadcrumb">    
     <li> <b> <%=schoolname%> :FEE MANAGEMENT PANEL FOR: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear() %>  <%=schoolfee%>  <b> </li> <br>

        <li>
            <a href="addFee.jsp">Pay Fee</a> <span class="divider">/</span>
        </li>

         <li>
            <a href="newPayment.jsp">Add new Payment</a> <span class="divider">/</span>
         </li>

          <li>
            <a href="pocketM.jsp">Pocket Money</a> <span class="divider">/</span>
         </li>

          <li>
            <a href="studentClearance.jsp">Student Clearance</a> <span class="divider">/</span>
         </li>

          <li>
            <a href="feeList.jsp">Fee Balance List</a> <span class="divider">/</span>
         </li>

         <li>
            <a href="changeTermYear.jsp">Update Term/Year</a> <span class="divider">/</span>
         </li>

          <li>
            <a <a href="../resources/passwords.pdf" target="_blank">Password file</a> <span class="divider">/</span>
         </li>

         
        
    </ul>
</div>



<div class="row-fluid sortable">




        <div class="box span12">
        <div class="box-content">

         <%
                   
                    HashMap<String, Student> studentParamHash = (HashMap<String, Student>) session.getAttribute(SessionConstants.STUENT_PARAM_F);
                    HashMap<String, List<StudentFee> > feeParamHash = (HashMap<String, List<StudentFee> >) session.getAttribute(SessionConstants.FEE_PARAM);

                            if (studentParamHash == null) {
                             studentParamHash = new HashMap<String, Student>();
                            }

                            if (feeParamHash == null) {
                             feeParamHash = new HashMap<String, List<StudentFee> >();
                            }


                            Student student = new Student();
                            student = studentParamHash.get("studentObj");
                              

                            List<StudentFee> studentfeeList = new ArrayList<StudentFee>();
                            studentfeeList = feeParamHash.get("studentfeeList");
                            

                               
                         
                         
                               String fullname = "";

                               String formatedFirstname = "";
					                     String formatedLastname = "";
					                     String formatedSurname = "";
                               String studentType = "";

					         

					                      String admNumber =""; 
                               String studentuuid =""; 	                          

					           if(student !=null){

					               admNumber = student.getAdmno();
                         studentuuid = student.getUuid();
                         studentType = student.getStudentType();
                                   
					               formatedFirstname = StringUtils.capitalize(student.getFirstname().toLowerCase());
					               formatedLastname = StringUtils.capitalize(student.getLastname().toLowerCase());
					               formatedSurname = StringUtils.capitalize(student.getSurname().toLowerCase()); 

					               fullname = formatedFirstname +" "+formatedLastname+" "+formatedSurname + "(" + studentType + ")";
 
					             }
					           
					            

                                String addError = "";
                                String addsuccess = "";
                                session = request.getSession(false);
                                addError = (String) session.getAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR);
                                addsuccess = (String) session.getAttribute(SessionConstants.STUDENT_FEE_ADD_SUCCESS); 

                                                    


                                if (StringUtils.isNotEmpty(addError)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addError);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STUDENT_FEE_ADD_SUCCESS, null);
                                  } 

                   
                       
                           
                     %>












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
                        <th>Statement</th>
                    </tr>
                </thead>   
                <tbody >
                    <%  
                               out.println("<tr>"); 
                               out.println("<td width=\"10%\" class=\"center\">" + admNumber + "</td>");  
                               out.println("<td width=\"10%\" class=\"center\">" + fullname + "</td>");    
                             
                    %> 
                              
                               <td width="10%" class="center">    
                               <form name="view" method="POST" action="printStatement" target="_blank">                             
                               <input type="hidden" name="studentuuid" value="<%=studentuuid%>">
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
                    <%     
                              double total = 0;
                              double totalpaid = 0;
                             
                               int count = 1;
		                     if(studentfeeList !=null){
		                   for(StudentFee sfee : studentfeeList){

                               total = sfee.getAmountPaid();
                               totalpaid = totalpaid + total;
                               out.println("<tr>"); 
                               out.println("<td width=\"3%\" >" + count + "</td>"); 
                               out.println("<td class=\"center\">" +  nf.format(total) + "</td>"); 
                               out.println("<td class=\"center\">" +  sfee.getTransactionID() + "</td>");
                               out.println("<td class=\"center\">" +  sfee.getDatePaid() + "</td>"); 
                               %>
                               <td class="center">
                                <form name="view" method="POST" action="updatefee.jsp"> 
                                <input type="hidden" name="transactionid" value="<%=sfee.getTransactionID()%>">
                                <input type="hidden" name="amountpaid" value="<%=sfee.getAmountPaid()%>">
                                <input type="hidden" name="studentuuid" value="<%=sfee.getStudentUuid()%>">
                                <input type="hidden" name="fullname" value="<%=fullname%>">
                                 <input type="hidden" name="admNumber" value="<%=admNumber%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Edit" /> 
                                </form>                          
                               </td>    

                                  



          
                                <%
                               count++;
                            
		                       }		                       
                           }                   
                  
                             %> 

                               

                </tbody>                                 
            </table>  

            



 


            
            <table class="table  ">
                <thead>
                    <tr >             
                        <th>Totals</th>
                    </tr>
                </thead>   
                <tbody >
                    <%  
                              
                               out.println("<tr>"); 
                               out.println("<td class=\"center\">" +  nf.format(totalpaid) + "</td>"); 
                             
                    %> 

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
                    <%   
                         
                         

                          String othermoney ="";
                          String itemterm ="";
                          String itemyear ="";

                          double itemcost = 0;
                          double amountpaid = 0;
                          double amountpaidTotal = 0;
                          double mysombalance = 0;
                          double mysombalancetotal = 0;
                        
                            
             List<StudentOtherMonies> stuOthermoniDistinctList = new ArrayList<StudentOtherMonies>(); 
              stuOthermoniDistinctList = studentOtherMoniesDAO.getStudentOtherMoniesDistinct(studentuuid);
                    if(stuOthermoniDistinctList !=null){

                      int count22 = 1;
                       for(StudentOtherMonies somdisticnt : stuOthermoniDistinctList){                               
                          
                           if(studentOtherMoniesDAO.getStudentOtherMoniesList(studentuuid,somdisticnt.getOtherstypeUuid()) !=null){
                              stuOthermoniList = studentOtherMoniesDAO.getStudentOtherMoniesList(studentuuid,somdisticnt.getOtherstypeUuid());
                                
                                 amountpaid = 0;
                                 amountpaidTotal = 0;
                                 itemcost = 0;
                                 mysombalance = 0;
                                 mysombalancetotal = 0;

                                
                                for(StudentOtherMonies sotheO : stuOthermoniList){

                                   itemterm = termHash.get(sotheO.getOtherstypeUuid());
                                   itemyear = yearHash.get(sotheO.getOtherstypeUuid());
                                  
                                   amountpaid = sotheO.getAmountPiad();
                                   amountpaidTotal+=amountpaid;
                                   othermoney = moneytypeHash.get(sotheO.getOtherstypeUuid());
                                   itemcost = tomHash.get(sotheO.getOtherstypeUuid());
                                   mysombalance = itemcost - amountpaidTotal;
                                   mysombalancetotal+=mysombalance;

                                   out.println("<tr>"); 
                                   out.println("<td width=\"3%\" >" + count22 + "</td>"); 
                                   out.println("<td class=\"center\">" + othermoney  + "</td>"); 
                                   out.println("<td class=\"center\">" + itemcost + "</td>");
                                   out.println("<td class=\"center\">" + itemterm + "</td>");
                                   out.println("<td class=\"center\">" + itemyear + "</td>");
                                   out.println("<td class=\"center\">" + amountpaid + "</td>");
                                   out.println("<td class=\"center\">" + sotheO.getTerm() + "</td>");
                                   out.println("<td class=\"center\">" + sotheO.getYear() + "</td>");

                                   %>
                               <td class="center">
                               <form name="view" method="POST" action="revert">                             
                               <input type="hidden" name="studentuuid" value="<%=studentuuid%>">
                               <input type="hidden" name="typeuuid" value="<%=sotheO.getOtherstypeUuid()%>">
                               <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                               <input type="hidden" name="amount" value="<%=itemcost%>">
                               <input type="hidden" name="term" value="<%=itemterm%>">
                               <input type="hidden" name="year" value="<%=itemyear%>">
                               <input class="btn btn-success" type="submit" name="view" id="submit" value="Revert" />
                               </form>  
                               </td>   </tr>

                                   <%
                                  

                                   

                                     }            
                                 }
                                  count22++;
                              }
                          }

                             %> 

                               

                </tbody>                                 
            </table>  



                                      
         <%
            //feeParamHash.clear();
         %>
            
       


    </div>

</div>



<jsp:include page="footer.jsp" />
