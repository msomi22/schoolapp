
<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

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
       
      
    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
   

     
    Locale locale = new Locale("en","KE"); 
    NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

         String admnumber = "";
         String firstname = "";
         String middlename = "";
         String lastname = "";
         String admyear = "";
         String regterm = "";
         String finalyear = "";
         String finalterm = "";
         String classroom = "";
         String studentuuid = "";
         String studentType = "";
         double feeBal = 0;

    
   
 %>






<jsp:include page="header.jsp" />



<div>
    <ul class="breadcrumb">    
     <li> <b> <%=schoolname%> :STUDENT CLEARANCE PANEL : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear() %> <b> </li> <br>

        

          <li>
            <a href="fee.jsp">Back</a> <span class="divider">/</span>
        </li>

         
        
    </ul>
</div>



<div class="row-fluid sortable">




        <div class="box span12">
        <div class="box-content">

                           <%
                   
                    
					            

                                String addError = "";
                                String addsuccess = "";
                                session = request.getSession(false);
                                addError = (String) session.getAttribute(SessionConstants.CLEAR_ERROR);
                                addsuccess = (String) session.getAttribute(SessionConstants.CLEAR_SUCCESS); 

                                                    


                                if (StringUtils.isNotEmpty(addError)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addError);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.CLEAR_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.CLEAR_SUCCESS, null);
                                  } 

                        HashMap<String, String> paramHash = (HashMap<String, String>) session.getAttribute(SessionConstants.CLEAR_PARAM);
                        HashMap<String, Double> balParamHash = (HashMap<String, Double>) session.getAttribute(SessionConstants.FEE_BALANCE_PARAM);

                          if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }

                            if (balParamHash == null) {
                                 balParamHash = new HashMap<String, Double>();
                            }
                           
                           if(balParamHash.get("feeBalance") !=null){
                             feeBal =  balParamHash.get("feeBalance");
                           }
                            
                            if(paramHash.get("admnumber") != null){
                                 admnumber = paramHash.get("admnumber");
                              }
                           
                           if(paramHash.get("firstname") !=null){
                                 firstname = paramHash.get("firstname");
                              }
                             if(paramHash.get("middlename") !=null){
                                middlename = paramHash.get("middlename");
                              }

                              if(paramHash.get("lastname") !=null){
                                lastname = paramHash.get("lastname");
                               }
                               if(paramHash.get("admyear") !=null){
                                admyear = paramHash.get("admyear");
                               }

                           if(paramHash.get("regterm") !=null){
                            regterm = paramHash.get("regterm");
                              }

                           if(paramHash.get("finalyear") !=null){
                            finalyear = paramHash.get("finalyear");
                             }

                           if(paramHash.get("finalterm") !=null){
                            finalterm = paramHash.get("finalterm");
                             }

                            if(paramHash.get("classroom")!=null){
                             classroom = paramHash.get("classroom");
                              }

                          if(paramHash.get("studentuuid") !=null){
                            studentuuid = paramHash.get("studentuuid");
                             }

                             if(paramHash.get("studentType") !=null){
                            studentType = paramHash.get("studentType");
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

                              <form name="view" method="POST" action="findStudentBal"> 

                               <td width="8%" class="center">                              
		                          <p><b>Student Admission Number:</b><p>                                                    
                               </td> 

                                <td width="10%" class="center">                              
		                               <input class="input-xlarge focused" id="receiver" type="text" name="admissionNo" 
		                                value=""  >                                                    
                               </td> 

                               <td width="10%" class="center">                                
		                           <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Find" />                                                         
                               </td> 
                               </form> 

                              


                </tbody>                  
            </table>  


           <%             

               out.println("admission number = " + admnumber +"</br>");
               out.println("first name = " + firstname +"</br>");
               out.println("middle name = " + middlename +"</br>");
               out.println("last name = " + lastname +"</br>");
               out.println("admission year = " + admyear +"</br>");
               out.println("reg term = " + regterm +"</br>");
               out.println("final year = " + finalyear +"</br>");
               out.println("final term = " + finalterm +"</br>");
               out.println("classroom = " + classroom +"</br>");
               out.println("student type = " + studentType +"</br>");
             
                       



           %>
   <br>
   <p>_________________________________________________________________________________________</p>

   <p>Find Balance </p>
       <table class="table table-striped  ">

                <thead>
                    <tr >             
                       
                        <th></th>
                       
                    </tr>
                </thead>   

                <tbody >


                            <form name="view" method="POST" action="findBalance"> 
                              <td width="5%" class="center">        
                                 <input type="hidden" name="studentuuid" value="<%=studentuuid%>">
                                 <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                 <input type="hidden" name="finalyear" value="<%=finalyear%>">
                                 <input class="btn btn-success" type="submit" name="view" id="submit" value="Find Balance" />  
                              </td> 
                            </form>                                                     
                             

                              


                </tbody>                  
            </table>  


 <br>
   <p>_________________________________________________________________________________________</p>

   <p>Pay the balance </p>
       <table class="table table-striped  ">

                <thead>
                    <tr >             
                       
                        <th></th>
                        <th></th>
                        <th></th>
                       
                    </tr>
                </thead>   

                <tbody >


                            <form name="view" method="POST" action="clearStudent"> 
                                 <td width="5%" class="center"> 
                                <div class="control-group">
                                 <label class="control-label" for="clearingAmount">Amount:</label>
                                   <div class="controls">
                                      <input class="input-xlarge focused" id="receiver" type="text" name="clearingAmount"
                                      value=""  >
                                  </div>
                               </div> 
                                 </td>

                                 <td width="5%" class="center"> 
                                <div class="control-group">
                                 <label class="control-label" for="securityKey">Security key:</label>
                                   <div class="controls">
                                      <input class="input-xlarge focused" id="receiver" type="text" name="securityKey"
                                      value=""  style="color: white;" autocomplete="off">
                                  </div>
                               </div> 
                                 </td>  


                              <td width="5%" class="center">        
                                 <input type="hidden" name="studentuuid" value="<%=studentuuid%>">
                                 <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                 <input type="hidden" name="finalyear" value="<%=finalyear%>">
                                 <input class="btn btn-success" type="submit" name="view" id="submit" value="Pay" />  
                              </td> 
                            </form>                                                     
                             

                              


                </tbody>                  
            </table>  


      <br>
   <p>_________________________________________________________________________________________</p>

   <% if(feeBal <= 0 && studentuuid == "") {%> 

   <p>Print Clearance form </p>
       <table class="table table-striped  ">

                <thead>
                    <tr >             
                       
                        <th></th>
                        
                    </tr>
                </thead>   

                <tbody >


                            <form name="view" method="POST" action=""> 
                              <td width="5%" class="center">        
                                 <input type="hidden" name="studentuuid" value="<%=studentuuid%>">
                                 <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                 <input type="hidden" name="finalyear" value="<%=finalyear%>">
                                 <input class="btn btn-success" type="submit" name="view" id="submit" value="Download" />  
                              </td> 
                            </form>                                                     
                             

                              


                </tbody>                  
            </table>  


    <%}%>
            



                 
       


    </div>

</div>



<jsp:include page="footer.jsp" />
