
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%-- <%@page import="com.yahoo.petermwenda83.persistence"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%> --%>


<%
	/* StaffDAO staffDAO= StaffDAO.getInstance();
	
	 List<Staff> staffList = new ArrayList<>();
	 if(staffDAO.getStaff(accountId) != null){
		 staffList = staffDAO.getStaff(accountId);
	 }
	 
	 int staffCount = 0; */
%>
<jsp:include page="header2.jsp" />






<!-- page content -->
<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h2>Accelerometer Readings</h2>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_content">





						<!-- <h1>Test the Api call via jquery</h1>


						<button class="btn btn-primary btn-block" onclick="StaffApiCall()">
							Test API call</button> -->


						<div class="row ">

							<div class="col-md-4">
								<h3 class="">Degrees Readings</h3>

							</div>

						</div>

						<div class="row" id="preload">

							<div
								class="col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 col-xs-6 col-xs-offset-3">


								<img src="../school/images/Preload.gif" class="img-responsive" />


							</div>

						</div>






						<div class="table-responsive">
							<table class="table table-striped jambo_table bulk_action"
								id="accel">
								<!-- <thead>
									<tr class="headings secondary-assent">

										<th class="column-title">#</th>
										<th class="column-title">Roll</th>
										<th class="column-title">Pitch</th>
										<th class="column-title">Yaw</th>



									</tr>
								</thead> -->

								<tbody class='tablebody'>

									<%--  <%
                  for(Staff staff : staffList){                   
                    %> --%>
									<%-- 
									<tr class="tabledit" style='color: black;'>

										<td width="5%"><%=staffCount %></td>
										<td class="center"><%=staff.getStaffNo() %></td>
										<td class="center"><%=staff.getFirstname() %></td>
										<td class="center hidden"><%=staff.getMiddlename() %></td>
										<td class="center"><%=staff.getLastname() %></td>
										<td class="center"><%=staff.getGender() %></td>
										<td class="center"><%=staff.getMobile() %></td>
										<td class="center"><%=staff.getEmail() %></td>
										<td class="center"><%=staff.getUsername() %></td>
										
										
										<td>
										 
										<button class="btn btn-warning editStaff" id="edit" onclick="StaffModal(this.id)"> Edit  <span class="fa fa-edit"></span></button>
										<button class="btn btn-danger" id="Code Code" onclick="disableStaff(this.id)"> Disable  <span class="fa fa-chain-broken"></span></button>
										</td>

									</tr>
									
									
							  <%      
                    staffCount++;
                  }
                  
                  %> --%>

									<!-- <tr class="tabledit">

										<td class="center">#</td>
										<td class="center btn btn-info">7.77</td>
										<td class="center btn btn-warning">40.888</td>

										<td class="center btn btn-warning">42.77</td>


									</tr> -->


									<!-- <tr class="tabledit" style='color: black;'>

										<td width="5%">#</td>
										<td class="center">
											<button class="btn btn-info btn-block" >
												7.77</button>
										</td>
										<td class="center">
											<button class="btn btn-warning btn-block" >
												40.88</button>
										</td>
										<td class="center">
											<button class="btn btn-warning btn-block"
												>
												42.77
											</button></td>





									</tr> -->
								</tbody>


							</table>




							<!--  <table id="example" class="display" width="100%">
							 
							 <thead>
							 <tr class="headings secondary-assent">
							  <th class="column-title hidden">uuid</th>
							 <th class="column-title">addDate</th>
										<th class="column-title">pitch</th>
										<th class="column-title">roll</th>
										<th class="column-title">yaw</th>
							 
							 </tr>
							 
							 </thead>
							 </table> -->

						</div>

















					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /page content -->







<!-- State Modal -->
<jsp:include page="modals/statemodals.html" />

<!-- footer -->
<jsp:include page="footer2.jsp" />




<script>
	
</script>
