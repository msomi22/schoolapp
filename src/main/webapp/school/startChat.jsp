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

 <style type="text/css">
  #chatMessage{
   height: 300px;
   width: 900px;
   overflow: scroll;
  }
</style>


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
                   $('#chatMessage').append( $("<p class='sender text-left' style='background-color: #f3ecec; '>" + you + msg +"</p>"));
                   
                 }
              });

             }

              document.getElementById('messageTextArea').value ="";
              e.preventDefault();
           }
       }
    
</script>

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
        <li>   <a href="chat.jsp">Back</a> </li>
       </div>

      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
               <%=staffHash.get(receipientId)%>  last seen:  Mon/06/2016
              </h3>
            </div>
            <div class="panel-body">
              <!-- start chart -->
                      <p> Hit enter to send </p>
                        <div id="chatWindow"> 
                          <div id="chatHeader">
                          </div>

                          <div id="chatMessage" >
                           <% 
                             String you = "You: ";
                           for(Chat chat : chatList){

                                if(StringUtils.equals(chat.getSenderUuid(), senderId)){
                                  %>
                                   <p class="sender text-left" style="background-color: #f3ecec; padding-right: 4px;margin-right: 2px;"> <%=you + chat.getMessage()%> </p>
                                   <%
                               }else if(StringUtils.equals(chat.getSenderUuid(), receipientId)){
                                 %>
                                    <p class="text-right" style="background-color: #ddf5c7; padding-right: 4px;margin-right: 2px;"> <%=chat.getMessage()%>  </p>
                                 <%
                                 }
                                
                             }
                          %>
                            
                          </div> 

                          <form class="form-horizontal" name="StartChat" method="post" action="">
                            <div class="form-group">
                              <div class="col-sm-9">
                                 <textarea class="form-control" name="messageTextArea" onkeydown="pressed(event);" id="messageTextArea"></textarea>   
                              </div>
                              </div>
                          </form> 

                        </div>


   
    </div>  <!-- end panel body-->
    <div class="panel-footer">
        <div class="row">
          <div class="col col-xs-4"> <small> <i>Live like there is no tomorrow.</i> </small>
        </div>
    </div>
</div>
    </div>  
    </div>  
    </div> 
  </div>


<jsp:include page="footer.jsp" />


