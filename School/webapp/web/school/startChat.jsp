<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.StaffDetails"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%>

<%@page import="com.yahoo.petermwenda83.bean.chat.Chat"%>
<%@page import="com.yahoo.petermwenda83.persistence.chat.ChatDAO"%>
<%@page import="com.yahoo.petermwenda83.util.performance.comparator.DateComparator"%>

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
   

    int incount = 0;  // Generic counter

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }


    accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();

    ChatDAO chatDAO = ChatDAO.getInstance();
    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();

    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);


     StaffDetails staffdetail = new StaffDetails();
     HashMap<String, String> staffHash = new HashMap<String, String>();
     StaffDetailsDAO staffDetailsDAO = StaffDetailsDAO.getInstance();
     List<StaffDetails> staffdetailList = new ArrayList<StaffDetails>(); 
     staffdetailList = staffDetailsDAO.getSStaffDetailList();
         
         if(staffdetailList !=null){
      for(StaffDetails sd : staffdetailList){
          staffHash.put(sd.getStaffUuid(), StringUtils.capitalize(sd.getFirstName().toLowerCase()) +" "+ StringUtils.capitalize(sd.getLastName().toLowerCase()) );
         }
     }
    
    
     StaffDAO staffDAO = StaffDAO.getInstance();
     List<Staff> staffList = new ArrayList<Staff>(); 
     staffList = staffDAO.getStaffList(accountuuid);

       //date format
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat timezoneFormatter = new SimpleDateFormat("z");  

   
     ServletContext context = getServletContext();
     Map<String,String> online =  (HashMap<String,String>)context.getAttribute("onlineUsersMap");
     
     String receipientId = request.getParameter("RecepientID");
     String senderId = request.getParameter("senderID");

     Chat thechat = new Chat();
     thechat.setSenderUuid(senderId);
     thechat.setReceiverUuid(receipientId); 

     List<Chat> chatList  = new ArrayList<Chat>(); 
     if(chatDAO.getChatList(thechat) !=null){
        chatList = chatDAO.getChatList(thechat);
      }

      Collections.sort(chatList, new DateComparator());
      //Collections.reverse(chatList);
 %>


<script type="text/javascript">

          function pressed(e){
          if(e.keyCode == 13){
              
              var thisUser = "<%=stffID%>";
              var senderId = "<%=senderId%>";
			        var receiverId = "<%=receipientId%>";
              var msg = $('#messageTextArea').val();
              //alert("message is " + msg);
              if($.trim(msg) !=''){
              // alert(msg);
              $.ajax({
                 url:"putChat",
                 method:"POST",
                 data:{messageTextArea:msg,senderId:senderId,receiverId:receiverId},
                 dataType:"text",
                 success:function(data){
                   $('#messageTextArea').val(""); 
                   var you = "You: ";
                   $('#chatMessage').append( $("<p class='sender'>" + you + msg +"</p>"));
                   
                 }
              });

             }

              //document.getElementById('messageTextArea').scrollTop = document.getElementById('messageTextArea').scrollHeight;
              document.getElementById('messageTextArea').value ="";
              e.preventDefault();
           }
       }
    
</script>



<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :START CHAT : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="chat.jsp">Back</a> <span class="divider">/</span>
        </li>
        
    </ul>
</div>




<div class="row-fluid sortable">

               
    <div class="box span12">S
    <div class="box-content">

      <!-- start chart -->
         <p> Hit enter to send </p>
             

                        <div id="chatWindow"> 
                          <div id="chatHeader">
                           <span class="name"> <%=staffHash.get(receipientId)%> </span>
                           <span class="lastSeen"> last seen: </span>  Mon/06/2016
                          </div>


                          <div id="chatMessage">
                           <% 
                             String you = "You: ";
                           for(Chat chat : chatList){

                                if(StringUtils.equals(chat.getSenderUuid(), senderId)){
                                  %>
                                   <p class="sender"> <%=you + chat.getMessage()%> </p>
                                   <%
                               }else if(StringUtils.equals(chat.getSenderUuid(), receipientId)){
                                 %>
                                    <p class="receiver"> <%=chat.getMessage()%>  </p>
                                 <%
                                 }
                                
                             }
                          %>
                            
                          </div> 

                          <form name="StartChat" method="post" action="">
                          <div id="chatBottom"> <textarea name="messageTextArea" onkeydown="pressed(event);" id="messageTextArea"></textarea>      
                          </div>
                          </form> 

                        </div>

    </div>
    </div>

</div>

   
<jsp:include page="footer.jsp" />
