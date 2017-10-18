<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@ page import="java.util.Calendar"%>

<%
	String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);

	CacheManager mgr = CacheManager.getInstance();
	Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
	Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);

	Account account = new Account();
	Element element;

	if ((element = accountsCache.get(username)) != null) {
		account = (Account) element.getObjectValue();
	}
%>





<!-- footer content -->
<footer class="mono whiteme">

	<div class="row">

		<div class="col-md-4 col-md-offset-2 col-sm-6">

			Contacts: (<%=account.getEmail() + " , " + account.getMobile()%>)
			Motto:
			<%=account.getMotto()%>

		</div>


		<div class="col-md-2 col-sm-6 col-md-offset-4">
			&copy; AppleTech Limited.
			<%=Calendar.getInstance().get(Calendar.YEAR)%>.
		</div>


	</div>


</footer>

<!-- /footer content -->
</div>
</div>

<!-- jQuery -->
<script src="../vendors/jquery/dist/jquery.min.js"></script>
<!-- <script src="../vendors/jquery-ui/jquery-ui.min.js"></script> -->


<!-- Bootstrap -->
<script src="../vendors/bootstrap/dist/js/bootstrap.min.js"></script>

<script src="js/registerNewStudent.js"></script>

<script
	src="../vendors/bootstrap-datetimepicker/bootstrap-datepicker.min.js"></script>


<script>
	$("#kcpeyear").datepicker({
		format : "yyyy",
		viewMode : "years",
		startDate : '-48m',
		endDate : 'dateToday',
		minViewMode : "years",
		clearBtn : true,
		autoclose : true

	});
	
	/* $("#inityear").datepicker({
		format : "yyyy",
		viewMode : "years",
		startDate : '-48m',
		endDate : 'dateToday',
		minViewMode : "years",
		clearBtn : true,
		autoclose : true

	}); */
	
	$(".yearConfig").datepicker({
		format : "yyyy",
		viewMode : "years",
		startDate : '-48m',
		endDate : 'dateToday',
		minViewMode : "years",
		clearBtn : true,
		autoclose : true

	});
</script>

<!-- Cropper -->
<!-- <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
<script src="js/cropper/cropper.min.js"></script>
<script src="js/cropper/main.js"></script>
<!-- FastClick -->
<script src="../vendors/fastclick/lib/fastclick.js"></script>
<!-- NProgress -->
<script src="../vendors/nprogress/nprogress.js"></script>

<!-- gauge.js -->
<script src="../vendors/gauge.js/dist/gauge.min.js"></script>
<!-- bootstrap-progressbar -->
<script
	src="../vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
<!-- iCheck -->
<script src="../vendors/iCheck/icheck.min.js"></script>
<!-- Skycons -->
<script src="../vendors/skycons/skycons.js"></script>
<!-- Flot -->
<script src="../vendors/Flot/jquery.flot.js"></script>
<script src="../vendors/Flot/jquery.flot.pie.js"></script>
<script src="../vendors/Flot/jquery.flot.time.js"></script>
<script src="../vendors/Flot/jquery.flot.stack.js"></script>
<script src="../vendors/Flot/jquery.flot.resize.js"></script>
<!-- Flot plugins -->
<script src="../vendors/flot.orderbars/js/jquery.flot.orderBars.js"></script>
<script src="../vendors/flot-spline/js/jquery.flot.spline.min.js"></script>
<script src="../vendors/flot.curvedlines/curvedLines.js"></script>
<!-- DateJS -->
<script src="../vendors/DateJS/build/date.js"></script>
<!-- JQVMap -->
<script src="../vendors/jqvmap/dist/jquery.vmap.js"></script>
<script src="../vendors/jqvmap/dist/maps/jquery.vmap.world.js"></script>
<script src="../vendors/jqvmap/examples/js/jquery.vmap.sampledata.js"></script>
<!-- bootstrap-daterangepicker -->
<script src="../vendors/moment/min/moment.min.js"></script>
<script src="../vendors/bootstrap-daterangepicker/daterangepicker.js"></script>



<!-- Datatables -->
<script src="../vendors/datatables.net/js/jquery.dataTables.min.js"></script>
<script
	src="../vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<script
	src="../vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
<script
	src="../vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
<script src="../vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
<script src="../vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
<script src="../vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
<script
	src="../vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
<script
	src="../vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
<script
	src="../vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
<script
	src="../vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
<script
	src="../vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
	
	<script
	src="../vendors/datatables.net-scroller/js/dataTables.scrollResize.js"></script>
	
	
	<script
	src="../vendors/datatables.net-scroller/js/jquery.scrollTo.min.js"></script>
	
	



<script src="../vendors/sumoselect/jquery.sumoselect.js"></script>


<!-- Json conversion -->

<script src="js/json/jquery.serializejson.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		window.asd = $('.SlectBox').SumoSelect({
			csvDispCount : 4,
			captionFormatAllSelected : "Selected all exams"
		});

		$('.SlectBox').on('sumo:opened', function(o) {
			console.log("dropdown opened", o)
		});

		$('.SlectBox').on('sumo:closed', function(o) {
			console.log("dropdown closed", o);
			validateExamSelected();
		});

	});
</script>

<script src="../build/js/custom.js"></script>
<!-- validate js -->

<script type="text/javascript" src="js/validate.js"></script>



<!-- Register new student -->

<script src="js/datepicker/moment.min.js"></script>
<script src="js/datepicker/pikaday.js"></script>
<script src="js/datepicker/pikaday.jquery.js"></script>


<script src="js/customReportJs.js"></script>


<!-- RootApiCall js -->
<script src="js/apiCalls/rootApiCall.js"></script>



<!-- RootApiCall Response Parser js -->
<script src="js/apiCalls/rootApisResponseParser.js"></script>


<!-- RootForm validator js -->
<script src="js/rootFormValidator.js"></script>


<!-- Class Streams Populator -->
<script src="js/apiCalls/classStreamsPopulator.js"></script>

<!-- password js -->
<script src="js/passwordUpdate.js"></script>










<script>
	var timepicker = new Pikaday({
		field : document.getElementById('dob'),
		firstDay : 1,
		minDate : new Date(1990, 0, 1),
		maxDate : new Date(2006, 12, 31),
		yearRange : [ 1990, 2006 ],
		showTime : true,
		autoClose : false,
		use24hour : false,
		format : 'YYYY-MM-DD'
	});

	$('#studentsList').DataTable({

		"bPaginate" : true,
		"bLengthChange" : true,
		searching : false

	});

	/* var yearpicker = new Pikaday({
		field : document.getElementById('kcpeyear'),
		firstYear : 1,
		minDate : moment().year(),
		maxDate : moment().year(),
		yearRange : [ moment().year('-3').toString(), moment().year().toString()],
		showTime : false,
		autoClose : true,
		use24hour : false,
		format : moment().format('YYYY')
	}); 
	
	 */

	
</script>



<!-- exam js -->
<script src="js/exam.js"></script>





<!-- excel import -->

<!-- uncomment the next line here and in xlsxworker.js for encoding support -->
<!--<script src="dist/cpexcel.js"></script>-->
<!-- <script src="js/excelImport/shim.js"></script>
<script src="js/excelImport/jszip.js"></script>
<script src="js/excelImport/xlsx.js"></script> -->
<!-- uncomment the next line here and in xlsxworker.js for ODS support -->
<!-- <script src="js/excelImport/ods.js"></script> -->

<script src="js/excelImport/excelImport.js"></script>











</body>
</html>


<!-- password Modal -->
<jsp:include page="modals/changePasswordModal.html" />
