/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.money.FeeBreakdown;
import com.yahoo.petermwenda83.bean.money.FeeBreakdownDesc;
import com.yahoo.petermwenda83.bean.money.TermFee;
import com.yahoo.petermwenda83.bean.otherfee.OtherFee;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDescDAO;
import com.yahoo.petermwenda83.persistence.money.GokeMoneyUsageDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.OtherFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.api.rest.bean.GokeMoneyUsageCheck;
import com.yahoo.petermwenda83.server.api.rest.bean.Response;
import com.yahoo.petermwenda83.server.servlet.finance.FeeConstants;

/**
 * @author peter
 *
 */
public class FinanceRestService {

	private static FeeBreakdownDescDAO feeBreakdownDescDAO;
	private static FeeBreakdownDAO feeBreakdownDAO;
	private static TermFeeDAO termFeeDAO;
	private static OtherFeeDAO otherFeeDAO;
	private static AccountDAO accountDAO;
	private static GokeMoneyUsageDAO gokeMoneyUsageDAO;
	private static SysConfigDAO sysConfigDAO;
	private static StudentDAO studentDAO;

	private static StudentFeeDAO studentFeeDAO;

	static {
		feeBreakdownDescDAO = FeeBreakdownDescDAO.getInstance();
		feeBreakdownDAO = FeeBreakdownDAO.getInstance();
		termFeeDAO = TermFeeDAO.getInstance();
		otherFeeDAO = OtherFeeDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		gokeMoneyUsageDAO = GokeMoneyUsageDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
		studentDAO = StudentDAO.getInstance();

		studentFeeDAO = StudentFeeDAO.getInstance();
	}


	/**
	 * 
	 * @param accountId
	 * @return
	 */

	public Object getFeeBreakDown(String accountId) {

		Response response = new Response();

		if(sysConfigDAO.getSysConfig(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Term/Year not set!");

		}else if(feeBreakdownDAO.getFeeBreakdown(accountId) == null) {
			//error
			response.setMessage("error");
			response.setDescription("Fee breakdown List not found!");
		}else {

			String term = sysConfigDAO.getSysConfig(accountId).getTerm();
			String year = sysConfigDAO.getSysConfig(accountId).getYear();

			return feeBreakdownDAO.getFeeBreakdown(accountId, FeeConstants.GVMT_MONEY_CODE, term, year);

		}

		return response;
	}
	/** TODO
	 * 
	 * @param feeBreakdown
	 * @return
	 */
	public Object addFeeBreakdown(FeeBreakdown feeBreakdown) {

		Response response = new Response();

		if(feeBreakdownDAO.getFeeBreakdown(feeBreakdown.getAccountId(), feeBreakdown.getFeeCategory()) != null) { 
			response.setMessage("error");
			response.setDescription("GoKe Fee already added!");
			return response;

		}else if(!FeeConstants.validTerm(feeBreakdown.getTerm())){
			response.setMessage("error");
			response.setDescription("Invalid term!");
			return response;

		}else if(!FeeConstants.validYear(feeBreakdown.getYear())){
			response.setMessage("error");
			response.setDescription("Invalid year!");
			return response;

		}else if(!FeeConstants.validGoKeFee(feeBreakdown.getAmount())){
			response.setMessage("error");
			response.setDescription("Invalid Amount!");
			return response;

		}else if(!FeeConstants.validStatus(feeBreakdown.getStatus())){
			response.setMessage("error");
			response.setDescription("Invalid status!");
			return response;

		}else {

			feeBreakdown.setUuid(new FeeBreakdown().getUuid()); 
			feeBreakdown.setFeeCategory("GO_KE");

			if(feeBreakdownDAO.putFeeBreakdown(feeBreakdown)){
				response.setMessage("success");
				response.setDescription("Info added successfully.!"); 
				return response;

			}else {
				response.setMessage("error");
				response.setDescription("Contact Admin please!");
				return response;
			}

		}

	}
	/**
	 * 
	 * @param feeBreakdown
	 * @return
	 */
	public Object updateFeeBreakdown(FeeBreakdown feeBreakdown) {

		Response response = new Response();

		if(feeBreakdownDAO.getFeeBreakdown(feeBreakdown.getAccountId(), feeBreakdown.getFeeCategory()) == null) { 
			response.setMessage("error");
			response.setDescription("GoKe Fee not found!");
			return response;

		}else if(!FeeConstants.validTerm(feeBreakdown.getTerm())){
			response.setMessage("error");
			response.setDescription("Invalid term!");
			return response;

		}else if(!FeeConstants.validYear(feeBreakdown.getYear())){
			response.setMessage("error");
			response.setDescription("Invalid year!");
			return response;

		}else if(!FeeConstants.validGoKeFee(feeBreakdown.getAmount())){
			response.setMessage("error");
			response.setDescription("Invalid Amount!");
			return response;

		}else if(!FeeConstants.validStatus(feeBreakdown.getStatus())){
			response.setMessage("error");
			response.setDescription("Invalid status!");
			return response;

		}else {


			feeBreakdown.setFeeCategory("GO_KE");
			if(feeBreakdownDAO.updateFeeBreakdown(feeBreakdown)){ 
				response.setMessage("success");
				response.setDescription("Info updated successfully.!"); 
				return response;

			}else {
				response.setMessage("error");
				response.setDescription("Contact Admin please!");
				return response;
			}


		}

	}

	/**
	 * 
	 * @param accountId
	 * @param feeBreakdownId
	 * @return
	 */
	public Object getGoKeMoney(String accountId, String feeBreakdownId) {

		Response response = new Response();

		if(feeBreakdownDescDAO.getFeeBreakdownDescList(accountId, feeBreakdownId) == null) {
			//error
			response.setMessage("error");
			response.setDescription("GoKe Fee breakdown not found!");
		}else {

			/*TODO
			 * 
			 * feeBreakdownDescDAO.getFeeBreakdownDescList(accountId, feeBreakdownId).parallelStream().forEach(breakdown ->{

				FeeConstants.formatFee(breakdown.getAmount());


			});*/

			return feeBreakdownDescDAO.getFeeBreakdownDescList(accountId, feeBreakdownId);

		}

		return response;
	}


	/**
	 * 
	 * @param feeBreakdownDesc
	 * @return
	 */
	public Object putGoKeMoney(FeeBreakdownDesc feeBreakdownDesc) {

		Response response = new Response();

		if(feeBreakdownDesc.getFeeDescription().length() < 3) {
			response.setMessage("error");
			response.setDescription("Invalid description!");
			return response;

		}else if(!FeeConstants.validFee(feeBreakdownDesc.getAmount())) { 
			response.setMessage("error");
			response.setDescription("Invalid Amount!");
			return response;

		}else if(feeBreakdownDescDAO.getFeeBreakdownDesc(feeBreakdownDesc.getAccountId(), feeBreakdownDesc.getFeeBreakdownId(), 
				feeBreakdownDesc.getFeeCode()) != null) {
			response.setMessage("error");
			response.setDescription("Fee Code is in use!");
			return response;

		}else if(feeBreakdownDescDAO.getFeeBreakdownDesc(feeBreakdownDesc.getAccountId(), feeBreakdownDesc.getFeeBreakdownId(), 
				feeBreakdownDesc.getFeeDescription()) != null) {
			response.setMessage("error");
			response.setDescription("Fee Description is in use!");
			return response;

		}else {

			feeBreakdownDesc.setUuid(new FeeBreakdownDesc().getUuid()); 
			feeBreakdownDesc.setFeeCode(RandomStringUtils.random(4)); 


			if(feeBreakdownDescDAO.putFeeBreakdownDesc(feeBreakdownDesc)) {
				response.setMessage("success");
				response.setDescription("Info added successfully.!"); 
				return response;

			}else {
				response.setMessage("error");
				response.setDescription("Contact Admin please!");
				return response;

			}

		}

	}

	/**
	 * 
	 * @return
	 */
	public Object useTemplate(String accountId) {

		Response response = new Response();

		if(accountDAO.getAccountById(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response;

		}else if(sysConfigDAO.getSysConfig(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Term/Year not found!");
			return response;

		}else if(feeBreakdownDAO.getFeeBreakdown(accountId, "GO_KE", sysConfigDAO.getSysConfig(accountId).getTerm(), 
				sysConfigDAO.getSysConfig(accountId).getYear()) != null) {
			response.setMessage("error");
			response.setDescription("Looks like you have alredy used the template!");
			return response;

		}else {

			String feeBreakdownId = "";

			int[] feeCode = {100,101,102,103,104,105,106};
			String[] feeDescription = {"R.M.I","E.W. & C","Administration costs","L.T. & T.","P.E.", "Activity ","Others(Specify)"};
			int[] amount = {90,130,130,90,830,130,100};

			SysConfig config = sysConfigDAO.getSysConfig(accountId);

			FeeBreakdown feeBreakdown;
			if(feeBreakdownDAO.getFeeBreakdown(accountId, "GO_KE", config.getTerm(), config.getYear()) == null) {
				feeBreakdown = new FeeBreakdown();
				feeBreakdownId = feeBreakdown.getUuid();
				feeBreakdown.setUuid(feeBreakdownId); 
				feeBreakdown.setAccountId(accountId);
				feeBreakdown.setFeeCategory("GO_KE");
				feeBreakdown.setTerm(config.getTerm());
				feeBreakdown.setYear(config.getYear());
				feeBreakdown.setStatus("0");
				feeBreakdown.setAmount(0);
			}else {
				feeBreakdown = feeBreakdownDAO.getFeeBreakdown(accountId, "GO_KE", config.getTerm(), config.getYear());
				feeBreakdownId = feeBreakdown.getUuid();

			}


			boolean success = false;

			if(feeBreakdownDAO.putFeeBreakdown(feeBreakdown)) {

				for(int count=0;count<feeCode.length;count++) {
					FeeBreakdownDesc feeBreakdownDesc = new FeeBreakdownDesc();
					feeBreakdownDesc.setAccountId(accountId);
					feeBreakdownDesc.setFeeBreakdownId(feeBreakdownId);
					feeBreakdownDesc.setFeeCode(String.valueOf(feeCode[count])); 
					feeBreakdownDesc.setFeeDescription(feeDescription[count]);
					feeBreakdownDesc.setAmount(amount[count]); 
					success = feeBreakdownDescDAO.putFeeBreakdownDesc(feeBreakdownDesc);
				}

			}

			if(success) {
				response.setMessage("success");
				response.setDescription("Template used successfully!");
				return response;

			}else {
				response.setMessage("error");
				response.setDescription("Please contact Admin!"); 
				return response;
			}

		}

	}


	/**
	 * 
	 * @param feeBreakdownDesc
	 * @return
	 */
	public Object updatedGoKeMoney(FeeBreakdownDesc feeBreakdownDesc) {

		Response response = new Response();


		if(feeBreakdownDescDAO.getFeeBreakdownDesc(feeBreakdownDesc.getAccountId(), feeBreakdownDesc.getUuid()) == null) {
			response.setMessage("error");
			response.setDescription("Fee not found!");
			return response;

		}/*else if(feeBreakdownDesc.getFeeCode().length() < 2) {
			response.setMessage("error");
			response.setDescription("Invalid code!");
			return response;

		}*/else if(feeBreakdownDesc.getFeeDescription().length() < 3) {
			response.setMessage("error");
			response.setDescription("Invalid description!");
			return response;

		}else if(!FeeConstants.validFee(feeBreakdownDesc.getAmount())) { 
			response.setMessage("error");
			response.setDescription("Invalid Amount!");
			return response;

		}else if(gokehasDuplicate(feeBreakdownDesc.getAccountId(), feeBreakdownDesc.getFeeBreakdownId(),
				feeBreakdownDesc.getFeeCode(),feeBreakdownDesc.getUuid())) { 
			response.setMessage("error");
			response.setDescription("Code duplicate not allowed!");
			return response;

		}else if(gokehasDuplicate(feeBreakdownDesc.getAccountId(), feeBreakdownDesc.getFeeBreakdownId(),
				feeBreakdownDesc.getFeeDescription(),feeBreakdownDesc.getUuid())) { 
			response.setMessage("error");
			response.setDescription("Description duplicate not allowed!");
			return response;

		}else {

			if(feeBreakdownDescDAO.updateFeeBreakdownDesc(feeBreakdownDesc)) {
				response.setMessage("success");
				response.setDescription("Info updated successfully.!"); 
				return response;

			}else {
				response.setMessage("error");
				response.setDescription("Contact Admin please!");
				return response;

			}

		}




	}

	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @return
	 */
	public Object deleteGoKeMoney(String accountId, String uuid) {

		Response response = new Response();

		if(feeBreakdownDescDAO.getFeeBreakdownDesc(accountId, uuid) == null) {

			response.setMessage("error");
			response.setDescription("Nothing to delete!");
			return response;

		}else {

			if(feeBreakdownDescDAO.deleteFeeBreakdownDesc(accountId, uuid)) {
				response.setMessage("success");
				response.setDescription("Info deleted successfully.!"); 
				return response;

			}else {
				response.setMessage("error");
				response.setDescription("Contact Admin please!");
				return response;

			}
		}
	}




	/**
	 * 
	 * @param accountId
	 * @param term
	 * @param year
	 * @return
	 */
	public Object getTermFee(String accountId, String term, String year) {

		Response response = new Response();

		if(termFeeDAO.getFee(accountId, term, year) == null) {
			response.setMessage("error");
			response.setDescription("Term fee not found!");
			return response;

		}else {

			return termFeeDAO.getFee(accountId, term, year);
		}

	}


	/**
	 * 
	 * @param accountId
	 * @param year
	 * @return
	 */

	public Object getTermFeePerYear(String accountId,String year) {

		Response response = new Response();

		if(termFeeDAO.getTermFeeList(accountId, year) == null) {
			response.setMessage("error");
			response.setDescription("Term fee not found!");
			return response;

		}else {

			return termFeeDAO.getTermFeeList(accountId, year);
		}

	}
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public Object getTermFees(String accountId) {

		Response response = new Response();

		if(termFeeDAO.getTermFeeList(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Term fee not found!");
			return response;

		}else {
			return termFeeDAO.getTermFeeList(accountId);

		}
	}

	/**
	 * 
	 * @param termFee
	 * @return
	 */
	public Object putTermFee(TermFee termFee) {

		Response response = new Response();

		if(sysConfigDAO.getSysConfig(termFee.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Term/Year not set.");
			return response;

		}else if(!FeeConstants.validFee(termFee.getBoaderAmount())) {
			response.setMessage("error");
			response.setDescription("Invalid BoaderAmount!");
			return response;

		}else if(!FeeConstants.validFee(termFee.getDayAmount())) {
			response.setMessage("error");
			response.setDescription("Invalid DayAmount!");
			return response;

		}else if(termFeeDAO.getFee(termFee.getAccountId(), termFee.getTerm(), termFee.getYear()) != null) {
			response.setMessage("error");
			response.setDescription("Term fee already added!");
			return response;

		}else {

			SysConfig config = sysConfigDAO.getSysConfig(termFee.getAccountId());
			termFee.setTerm(config.getTerm());
			termFee.setYear(config.getYear()); 
			termFee.setUuid(new TermFee().getUuid()); 

			if(termFeeDAO.putFee(termFee, termFee.getAccountId(), termFee.getTerm(), termFee.getYear())) {
				response.setMessage("success");
				response.setDescription("Term Fee added successfully."); 
				return response;

			}else { 
				response.setMessage("error");
				response.setDescription("Contact Admin please.");
				return response;

			}

		}

	}
	/**
	 * 
	 * @param termFee
	 * @return
	 */
	public Object updateTermFee(TermFee termFee) {

		Response response = new Response();

		if(sysConfigDAO.getSysConfig(termFee.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Term/Year not set.");
			return response;

		}else if(!FeeConstants.validFee(termFee.getBoaderAmount())) {
			response.setMessage("error");
			response.setDescription("Invalid BoaderAmount!");
			return response;

		}else if(!FeeConstants.validFee(termFee.getDayAmount())) {
			response.setMessage("error");
			response.setDescription("Invalid DayAmount!");
			return response;

		}else {

			SysConfig config = sysConfigDAO.getSysConfig(termFee.getAccountId());
			termFee.setTerm(config.getTerm());
			termFee.setYear(config.getYear()); 

			if(termFeeDAO.getFee(termFee.getAccountId(), termFee.getTerm(), termFee.getYear()) == null) {
				response.setMessage("error");
				response.setDescription("Invalid term/year!");
				return response;

			}else {

				if(termFeeDAO.updateFee(termFee)) {

					response.setMessage("success");
					response.setDescription("Term Fee updated successfully."); 
					return response;

				}else {
					response.setMessage("error");
					response.setDescription("Contact Admin please.");
					return response;

				}
			}


		}

	}

	/**
	 * 
	 * @param accountId
	 * @param term
	 * @param year
	 * @return
	 */
	public Object getOtherFee(String accountId, String term, String year) {

		Response response = new Response();

		if(sysConfigDAO.getSysConfig(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Term/Year not set.");
			return response;

		}else if(otherFeeDAO.getOtherFeeList(accountId, sysConfigDAO.getSysConfig(accountId).getTerm(), sysConfigDAO.getSysConfig(accountId).getYear()).isEmpty()) {
			response.setMessage("error");
			response.setDescription("Fee not found!");
			return response;
		}else {

			return otherFeeDAO.getOtherFeeList(accountId, sysConfigDAO.getSysConfig(accountId).getTerm(), sysConfigDAO.getSysConfig(accountId).getYear()); 
		}

	}

	/**
	 * 
	 * @param otherFee
	 * @return
	 */
	public Object putOtherFee(OtherFee otherFee) {

		Response response = new Response();


		if(otherFee.getDescription().length() < 3) { 
			response.setMessage("error");
			response.setDescription("Invalid Description.");
			return response;

		}else if(!FeeConstants.validFee(otherFee.getAmount())) {
			response.setMessage("error");
			response.setDescription("Invalid Amount.");
			return response;

		}else if(accountDAO.getAccountById(otherFee.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response;

		}else if(sysConfigDAO.getSysConfig(otherFee.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Term/Year not set.");
			return response;

		}else {

			otherFee.setUuid(new OtherFee().getUuid()); 

			SysConfig config = sysConfigDAO.getSysConfig(otherFee.getAccountId());
			otherFee.setTerm(config.getTerm());
			otherFee.setYear(config.getYear()); 

			if(otherFeeDAO.queryOtherFee(otherFee.getAccountId(), otherFee.getDescription(), otherFee.getTerm(), otherFee.getYear()) != null) {
				response.setMessage("error");
				response.setDescription("Description exist!");
				return response;

			}else {


				if(otherFeeDAO.putOtherFee(otherFee)) {

					response.setMessage("sucess");
					response.setDescription("Fee added successfully."); 
					return response;

				}else {
					response.setMessage("error");
					response.setDescription("Contact Admin please.");
					return response;

				}



			}




		}

	}
	/**
	 * 
	 * @param otherFee
	 * @return
	 */
	public Object updateOtherFee(OtherFee otherFee) {

		Response response = new Response();

		if(otherFee.getDescription().length() < 3) {
			response.setMessage("error");
			response.setDescription("Invalid Description.");
			return response;

		}else if(!FeeConstants.validFee(otherFee.getAmount())) {
			response.setMessage("error");
			response.setDescription("Invalid Amount.");
			return response;

		}else if(accountDAO.getAccountById(otherFee.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response;

		}else if(sysConfigDAO.getSysConfig(otherFee.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Term/Year not set.");
			return response;

		}else if(otherFeeDAO.getOtherFee(otherFee.getAccountId(), otherFee.getUuid()) == null) {
			response.setMessage("error");
			response.setDescription("Fee not found!");
			return response;

		}else if(hasDuplicate(otherFee)) {
			response.setMessage("error");
			response.setDescription("No duplicates!");
			return response;

		}else {

			SysConfig config = sysConfigDAO.getSysConfig(otherFee.getAccountId());
			otherFee.setTerm(config.getTerm());
			otherFee.setYear(config.getYear()); 

			if(otherFeeDAO.updateOtherFee(otherFee)) {
				response.setMessage("sucess");
				response.setDescription("Fee updated successfully."); 
				return response;

			}else {
				response.setMessage("error");
				response.setDescription("Contact Admin please.");
				return response;

			}

		}
	}




	/**  TODO
	 * 
	 * @param accountId
	 * @param term
	 * @param year
	 * @return
	 */

	public Object getGokMoneyUsage(String accountId, String  term, String  year) {

		Response response = new Response();

		if(gokeMoneyUsageDAO.getGokeMoneyUsage(accountId, term, year) == null) {
			response.setMessage("error");
			response.setDescription("No record found!");
			return response;

		}else {

			return gokeMoneyUsageDAO.getGokeMoneyUsage(accountId, term, year);
		}
	}

	/**
	 * 
	 * @param accountId
	 * @param year
	 * @return
	 */
	public Object getGokMoneyUsage(String accountId, String  year) {

		Response response = new Response();

		if(gokeMoneyUsageDAO.getGokeMoneyUsageList(accountId, year) == null) {
			response.setMessage("error");
			response.setDescription("No record found!");
			return response;

		}else {

			return gokeMoneyUsageDAO.getGokeMoneyUsageList(accountId, year);

		}
	}

	//TODO
	/**
	 * 
	 * @param accountId
	 * @param amount
	 * @return
	 */
	public Object canCommitGokMoney(String accountId, int amount) {

		Response response = new Response();

		if(accountDAO.getAccountById(accountId)== null) {
			response.setMessage("error");
			response.setDescription("Invalid accountId!");
			return response;

		}else if(sysConfigDAO.getSysConfig(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Term/Year not set!");
			return response;

		}else if(amount < 100) { 
			response.setMessage("error");
			response.setDescription("Amount not valid!  i.e.  < 100 ");
			return response;

		}else {

			SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);

			if(feeBreakdownDAO.getFeeBreakdown(accountId, 
					FeeConstants.GVMT_MONEY_CODE,
					sysConfig.getTerm(),
					sysConfig.getYear()
					) != null){

				FeeBreakdown feeBreakdown = feeBreakdownDAO.getFeeBreakdown(accountId, FeeConstants.GVMT_MONEY_CODE, sysConfig.getTerm(),
						sysConfig.getYear());

				int amountToEachStudent = (int)FeeConstants.getGoKeFee(accountId, feeBreakdown.getUuid());

				AtomicInteger scount = new AtomicInteger();

				if(!studentDAO.getActiveStudents(accountId, "1","1").isEmpty()) {

					studentDAO.getActiveStudents(accountId, "1","1").parallelStream().forEach(st -> {

						if(studentFeeDAO.getStudentFee(accountId, st.getUuid(), 
								FeeConstants.GVMT_MONEY_CODE,
								sysConfig.getTerm(),
								sysConfig.getYear()) == null) {

							scount.getAndIncrement();

						}

					});

				}



				double totalAmount = amount;
				//int no_of_students = studentDAO.activeAndGoKEligibleCount(accountId, "1","1"); 
				int no_of_students = scount.get();
				double balance = 0;

				if(no_of_students > 0) {

					balance = totalAmount - (amountToEachStudent * no_of_students);

					GokeMoneyUsageCheck gokeMoneyUsageCheck = new GokeMoneyUsageCheck();
					gokeMoneyUsageCheck.setTotalAmount((int)totalAmount);
					gokeMoneyUsageCheck.setExpectedAmount((int) no_of_students * amountToEachStudent);   
					gokeMoneyUsageCheck.setNumberOfStudents(no_of_students);
					gokeMoneyUsageCheck.setAmountPerStudent(amountToEachStudent);
					gokeMoneyUsageCheck.setBalance((int)balance);
					gokeMoneyUsageCheck.setTerm(sysConfig.getTerm());
					gokeMoneyUsageCheck.setYear(sysConfig.getYear());

					return gokeMoneyUsageCheck;

				}else {

					response.setMessage("error");
					response.setDescription("Seems like all students are already allocated Government money!");
					return response;
				}



			}else {
				response.setMessage("error");
				response.setDescription("No record found!");
				return response;

			}

		}

	}

	/**
	 * 
	 * @param accountId
	 * @param obj
	 * @return
	 */
	public Object commitGokMoney(String accountId, Object obj) {

		Response response = new Response();
		int amount = (Integer) obj;
		String status = "1";
	

		if(accountDAO.getAccountById(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Invalid accountId!");
			return response;
		}else if(sysConfigDAO.getSysConfig(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Term/Year not set!");
			return response;

		}else if(amount < 100) {
			response.setMessage("error");
			response.setDescription("Amount not valid!  i.e.  < 100 ");
			return response;
		}else {


			SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);


			if(feeBreakdownDAO.getFeeBreakdown(accountId, 
					FeeConstants.GVMT_MONEY_CODE, 
					sysConfig.getTerm(), 
					sysConfig.getYear()) == null) {
				
				response.setMessage("error");
				response.setDescription("Government money not set!");
				return response;

			}else {

				FeeBreakdown feeBreakdown = feeBreakdownDAO.getFeeBreakdown(accountId, 
						FeeConstants.GVMT_MONEY_CODE, 
						sysConfig.getTerm(), 
						sysConfig.getYear());

				feeBreakdown.setAmount(amount);
				feeBreakdown.setStatus(status); 
				
				if(feeBreakdownDAO.updateFeeBreakdown(feeBreakdown)) {
					response.setMessage("success");
					response.setDescription("Government money allocated initiated!"); 
					return response;
					
				}else {
					
					response.setMessage("error");
					response.setDescription("Please contact Admin!");
					return response;
					
				}

			}

		}

	}
























	/**
	 * 
	 * @param otherFee
	 * @return
	 */
	private boolean hasDuplicate(OtherFee otherFee) {

		boolean hasduplicate = true;

		if(otherFeeDAO.findDuplicate(otherFee.getAccountId(), otherFee.getDescription(), otherFee.getTerm(), otherFee.getYear()).size() == 0) {
			hasduplicate = false;

		}else if(otherFeeDAO.findDuplicate(otherFee.getAccountId(), otherFee.getDescription(), otherFee.getTerm(), otherFee.getYear()).size() == 1) {

			String id = otherFeeDAO.queryOtherFee(otherFee.getAccountId(), otherFee.getDescription(), otherFee.getTerm(), otherFee.getYear()).getUuid();

			if(StringUtils.equals(otherFee.getUuid(), id)) {
				hasduplicate = false;

			}else {
				hasduplicate = true;
			}

		}

		return hasduplicate;

	}

	/**
	 * 
	 * @param accountId
	 * @param feeBreakdownId
	 * @param query
	 * @param uuid
	 * @return
	 */

	private boolean gokehasDuplicate(String accountId, String feeBreakdownId, String query, String uuid) {
		boolean hasduplicate = true;

		if(feeBreakdownDescDAO.findDuplicate(accountId, feeBreakdownId, query).size() == 0) {
			hasduplicate = false;

		}else if(feeBreakdownDescDAO.findDuplicate(accountId, feeBreakdownId, query).size() == 1) {

			String id = feeBreakdownDescDAO.getFeeBreakdownDesc(accountId, feeBreakdownId, query).getUuid(); 

			if(StringUtils.equals(uuid, id)) {
				hasduplicate = false;

			}else {
				hasduplicate = true;
			}

		}

		return hasduplicate;
	}



	/**
	 * 
	 * @param accountId
	 * @param term
	 * @param year
	 * @param uuid
	 * @return
	 */
	public boolean termfeehasDuplicate(String accountId, String term, String year, String uuid) {
		boolean hasduplicate = true;

		if(termFeeDAO.findDuplicate(accountId, term, year).size() == 0) {
			hasduplicate = false;

		}else if(termFeeDAO.findDuplicate(accountId, term, year).size() == 1) {

			String id = termFeeDAO.getFee(accountId, term, year).getUuid(); 

			if(StringUtils.equals(uuid, id)) {
				hasduplicate = false;

			}else {
				hasduplicate = true;
			}

		}

		return hasduplicate;
	}


}
