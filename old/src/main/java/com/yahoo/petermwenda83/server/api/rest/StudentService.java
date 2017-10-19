/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.yahoo.petermwenda83.bean.exam.SysConfig;
import com.yahoo.petermwenda83.bean.money.StudentFee;
import com.yahoo.petermwenda83.bean.otherfee.OtherFee;
import com.yahoo.petermwenda83.bean.otherfee.RevertedMoney;
import com.yahoo.petermwenda83.bean.otherfee.StudentOtherFee;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.StudentPrimary;
import com.yahoo.petermwenda83.bean.student.StudentSubject;
import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.OtherFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.RevertedMoneyDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.persistence.student.PrimaryDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.persistence.student.StudentSubjectDAO;
import com.yahoo.petermwenda83.persistence.subject.SubjectDAO;
import com.yahoo.petermwenda83.server.api.filter.StudentFilter;
import com.yahoo.petermwenda83.server.api.rest.bean.APIOtherFee;
import com.yahoo.petermwenda83.server.api.rest.bean.APIParentPrimary;
import com.yahoo.petermwenda83.server.api.rest.bean.APIStudent;
import com.yahoo.petermwenda83.server.api.rest.bean.APIStudentFee;
import com.yahoo.petermwenda83.server.api.rest.bean.APIStudentOtherFee;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiSubject;
import com.yahoo.petermwenda83.server.api.rest.bean.ChangeClass;
import com.yahoo.petermwenda83.server.api.rest.bean.GoKeMoney;
import com.yahoo.petermwenda83.server.api.rest.bean.Response;
import com.yahoo.petermwenda83.server.api.rest.bean.APIRevertGoKeFee;
import com.yahoo.petermwenda83.server.api.rest.bean.APIRevertFee;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentFeeAPI;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentInfo;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentPage;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentPayFee;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.StudentStatus;
import com.yahoo.petermwenda83.server.api.rest.bean.UpdateFee;
import com.yahoo.petermwenda83.server.servlet.finance.FeeConstants;
import com.yahoo.petermwenda83.server.servlet.finance.StudentBalance;
import com.yahoo.petermwenda83.server.servlet.util.SecurityUtil;

/**
 * @author peter
 *
 */
public class StudentService {

	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;
	private static StaffDAO staffDAO;
	private static StreamDAO streamDAO;
	private static StudentFeeDAO studentFeeDAO;
	private static OtherFeeDAO otherFeeDAO;
	private static StudentOtherFeeDAO studentOtherFeeDAO;
	private static RevertedMoneyDAO revertedMoneyDAO;

	private static SysConfigDAO sysConfigDAO;
	private static EmailValidator emailValidator;

	private static PrimaryDAO primaryDAO;
	private static ParentsDAO parentsDAO;

	private static StudentSubjectDAO studentSubjectDAO;

	private static SubjectDAO subjectDAO;

	private static FeeBreakdownDAO feeBreakdownDAO;

	private static final String DATA_DIRECTORY = "/home/" + System.getProperty("user.name") + "/school/uploads/";

	static {
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		staffDAO = StaffDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		studentFeeDAO = StudentFeeDAO.getInstance();
		otherFeeDAO = OtherFeeDAO.getInstance();
		studentOtherFeeDAO = StudentOtherFeeDAO.getInstance();
		revertedMoneyDAO = RevertedMoneyDAO.getInstance();

		sysConfigDAO = SysConfigDAO.getInstance();
		emailValidator = EmailValidator.getInstance();

		parentsDAO = ParentsDAO.getInstance();
		primaryDAO = PrimaryDAO.getInstance();

		studentSubjectDAO = StudentSubjectDAO.getInstance();

		subjectDAO = SubjectDAO.getInstance();

		feeBreakdownDAO = FeeBreakdownDAO.getInstance();
	}

	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @return
	 */
	public Object getStudentById(String accountId, String studentId) {

		ApiResponse apiResponse = new ApiResponse();

		APIStudent apiStudent = new APIStudent();

		if (studentDAO.getStudentById(accountId, studentId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("studentId is invalid!");

		} else {

			try {

				// TODO
				BeanUtils.copyProperties(apiStudent, studentDAO.getStudentById(accountId, studentId));
				apiStudent.setPassport(getB64Image(studentDAO.getStudentById(accountId, studentId).getPassport()));

				APIParentPrimary apiParentPrimary = new APIParentPrimary();

				// primary
				if (primaryDAO.getStudentPrimary(accountId, studentId) != null) {

					StudentPrimary primary = primaryDAO.getStudentPrimary(accountId, studentId);

					apiParentPrimary.setSchoolName(primary.getSchoolName());
					apiParentPrimary.setIndex(primary.getIndex());
					apiParentPrimary.setKcpemark(primary.getKcpemark());
					apiParentPrimary.setKcpeyear(primary.getKcpeyear());
				}

				// parent
				if (parentsDAO.getParent(accountId, studentId) != null) {

					StudentParent studentParent = parentsDAO.getParent(accountId, studentId);

					apiParentPrimary.setParentName(studentParent.getName());
					apiParentPrimary.setParentEmail(studentParent.getEmail());
					apiParentPrimary.setParentMobile(studentParent.getMobile());
				}

				apiStudent.setApiParentPrimary(apiParentPrimary);

			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		}

		return apiStudent;
	}

	/**
	 * @param sreamId
	 * @return
	 */
	public List<APIStudent> getStudentPerStream(String accountId, String sreamId) {

		List<Student> students = studentDAO.getStudentByStream(accountId, sreamId);
		List<APIStudent> streamStudents = new ArrayList<APIStudent>();
		students.forEach(student -> {

			APIStudent apiStudent = new APIStudent();
			apiStudent.setUuid(student.getUuid());
			apiStudent.setAccountId(student.getAccountId());
			apiStudent.setCurrentStream(student.getCurrentStream());
			apiStudent.setRegStream(student.getRegStream());
			apiStudent.setIsActive(student.getIsActive());
			apiStudent.setIsAlumni(student.getIsAlumni());
			apiStudent.setIsBoarding(student.getIsBoarding());
			apiStudent.setIsGoKFeeEligibe(student.getIsGoKFeeEligibe()); 
			apiStudent.setRegNo(student.getRegNo());
			apiStudent.setFirstname(student.getFirstname());
			apiStudent.setMiddlename(student.getMiddlename());
			apiStudent.setLastname(student.getLastname());
			apiStudent.setGender(student.getGender());
			apiStudent.setCounty(student.getCounty());
			apiStudent.setBcertNo(student.getBcertNo());
			apiStudent.setDob(student.getDob());
			apiStudent.setRegTerm(student.getRegTerm());
			// TODO
			apiStudent.setPassport(getB64Image(student.getPassport()));
			apiStudent.setLastUpdated(student.getLastUpdated());
			apiStudent.setFinalTerm(student.getFinalTerm());
			apiStudent.setFinalYear(student.getFinalYear());
			apiStudent.setAdmissionDate(student.getAdmissionDate().toString());

			streamStudents.add(apiStudent);

		});

		return streamStudents;
	}

	/**
	 * 
	 * @param studentPayFee
	 * @return
	 */
	public Object payFee(StudentPayFee studentPayFee) {

		Response response = new Response();

		// validation
		if (StringUtils.isEmpty(studentPayFee.getAccountId())) {

			response.setMessage("error");
			response.setDescription("AccountId is invalid!");
			return response;

		} else if (accountDAO.getAccountById(studentPayFee.getAccountId()) == null) {

			response.setMessage("error");
			response.setDescription("AccountId is invalid!");
			return response;

		} else if (StringUtils.isEmpty(studentPayFee.getStaffId())) {

			response.setMessage("error");
			response.setDescription("Staff is invalid!");
			return response;

		} else if (staffDAO.getStaff(studentPayFee.getAccountId(), studentPayFee.getStaffId()) == null) {

			response.setMessage("error");
			response.setDescription("Staff is invalid!");
			return response;

		} else if (studentDAO.getStudentByregNo(studentPayFee.getAccountId(), studentPayFee.getRegNo()) == null) {

			response.setMessage("error");
			response.setDescription("Student RegNo is invalid!");
			return response;

		} else if (studentDAO.getStudentById(studentPayFee.getAccountId(), studentPayFee.getStudentId()) == null) {

			response.setMessage("error");
			response.setDescription("Student StudentId is invalid!");
			return response;

		} else if (!StringUtils.equals(
				studentDAO.getStudentByregNo(studentPayFee.getAccountId(), studentPayFee.getRegNo()).getUuid(),
				studentDAO.getStudentById(studentPayFee.getAccountId(), studentPayFee.getStudentId()).getUuid())) {

			response.setMessage("error");
			response.setDescription("Student RegNo-Id mismatch!");
			return response;

		} else if (!FeeConstants.validFee(Integer.valueOf(studentPayFee.getAmount()))) {

			response.setMessage("error");
			response.setDescription("Amount is invalid!");
			return response;

		} else {

			Student student = studentDAO.getStudentByregNo(studentPayFee.getAccountId(), studentPayFee.getRegNo());
			SysConfig sysConfig = sysConfigDAO.getSysConfig(studentPayFee.getAccountId());

			Staff staff = staffDAO.getStaff(studentPayFee.getAccountId(), studentPayFee.getStaffId());
			staff.getAcessLevelId();

			if (!staffAllowedToAlterFee(staff.getUuid(), staff.getAcessLevelId())) {
				response.setMessage("error");
				response.setDescription("Staff not allowed to alter with fee!");

			} else {

				StudentFee studentFee = new StudentFee();
				studentFee.setAccountId(studentPayFee.getAccountId());
				studentFee.setStudentId(student.getUuid());
				studentFee.setAmountPaid(Integer.valueOf(studentPayFee.getAmount()));
				studentFee.setPayMode(studentPayFee.getPaymentMode());
				studentFee.setTransactionId(studentPayFee.getTransactionId());
				studentFee.setPaidHas(student.getIsBoarding());
				studentFee.setTermPiad(sysConfig.getTerm());
				studentFee.setYearPaid(sysConfig.getYear());
				studentFee.setTransactingStaffId(staff.getUuid());

				if (studentFeeDAO.putStudentFee(studentFee)) {
					response.setMessage("success");
					response.setDescription("Fee paid successsfully.");

				} else {
					response.setMessage("error");
					response.setDescription("Something went wrong, contact Admin!");

				}

			}

		}

		return response;
	}

	/**
	 * 
	 * @param updateFeeObj
	 * @return
	 */
	public Object updateFeeInfo(UpdateFee updateFeeObj) {

		Response response = new Response();

		if (studentFeeDAO.getStudentFee(updateFeeObj.getAccountId(), updateFeeObj.getStudentId(),
				updateFeeObj.getPaymentId()) == null) {
			response.setMessage("error");
			response.setDescription("Payment record not found!");
			return response;

		} else {

			StudentFee studentFee = studentFeeDAO.getStudentFee(updateFeeObj.getAccountId(),
					updateFeeObj.getStudentId(), updateFeeObj.getPaymentId());

			if (studentFee.getAmountPaid() != updateFeeObj.getPreviousAmount()) {
				response.setMessage("error");
				response.setDescription("Previous amount incorrect!");
				return response;

			} else if (!FeeConstants.validFee(updateFeeObj.getCorrectAmount())) {
				response.setMessage("error");
				response.setDescription("Amount is invalid!");
				return response;

			} else if (staffDAO.getStaff(updateFeeObj.getAccountId(), updateFeeObj.getTransactingStaffId()) != null) {
				response.setMessage("error");
				response.setDescription("Staff is invalid!");
				return response;

			} else if (accountDAO.getAccountById(updateFeeObj.getAccountId()) == null) {
				response.setMessage("error");
				response.setDescription("SchoolId is invalid!");
				return response;

			} else if (!StringUtils.equals(accountDAO.getAccountById(updateFeeObj.getAccountId()).getPassword(),
					SecurityUtil.getMD5Hash(updateFeeObj.getSchoolSecret()))) {
				response.setMessage("error");
				response.setDescription("SchoolSecret is invalid!");
				return response;

			} else {

				studentFee.setAmountPaid(updateFeeObj.getCorrectAmount());
				Staff staff = staffDAO.getStaff(updateFeeObj.getAccountId(), updateFeeObj.getTransactingStaffId());

				if (!staffAllowedToAlterFee(updateFeeObj.getTransactingStaffId(), staff.getAcessLevelId())) {
					response.setMessage("error");
					response.setDescription("Staff not allowed to alter with fee!");
					return response;

				} else {

					if (studentFeeDAO.updateStudentFee(studentFee)) {
						response.setMessage("success");
						response.setDescription("Amount updated sucessfully!");
						return response;

					} else {
						response.setMessage("error");
						response.setDescription("Something went wrong, conatct Admin.");
						return response;

					}

				}

			}
		}

	}

	/**
	 * 
	 * @param apiOtherFee
	 * @return
	 */
	public Object assignOtherFee(APIOtherFee apiOtherFee) {

		Response response = new Response();

		if (studentOtherFeeDAO.getStudentOtherFee(apiOtherFee.getAccountId(), apiOtherFee.getOtherFeeId(),
				apiOtherFee.getOtherFeeId()) != null) {
			response.setMessage("error");
			response.setDescription("Amount already assigned!");
			return response;

		} else {

			StudentOtherFee studentOtherFee = new StudentOtherFee();
			studentOtherFee.setAccountId(apiOtherFee.getAccountId());
			studentOtherFee.setStudentId(apiOtherFee.getStudentId());
			studentOtherFee.setOtherFeeId(apiOtherFee.getOtherFeeId());
			studentOtherFee.setTerm(apiOtherFee.getTerm());

			if (studentOtherFeeDAO.putStudentOtherFee(studentOtherFee)) {
				response.setMessage("success");
				response.setDescription("Amount assigned sucessfully!");
				return response;

			} else {
				response.setMessage("error");
				response.setDescription("Something went wrong, try again later.");
				return response;

			}

		}

	}

	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @param otherFeeId
	 * @return
	 */
	public Object revertOtheFee(APIOtherFee apiOtherFee) {

		Response response = new Response();

		if (studentOtherFeeDAO.getStudentOtherFee(apiOtherFee.getAccountId(), apiOtherFee.getStudentId(),
				apiOtherFee.getOtherFeeId()) == null) {
			response.setMessage("error");
			response.setDescription("Nothing to delete!");
			return response;

		} else {

			if (studentOtherFeeDAO.revertStudentOtherFee(apiOtherFee.getAccountId(), apiOtherFee.getStudentId(),
					apiOtherFee.getOtherFeeId())) {

				RevertedMoney revertedMoney = new RevertedMoney();
				revertedMoney.setAccountId(apiOtherFee.getAccountId());
				revertedMoney.setStudentId(apiOtherFee.getStudentId());
				revertedMoney.setOtherFeeId(apiOtherFee.getOtherFeeId());

				if (revertedMoneyDAO.putRevertedMoney(revertedMoney)) {
					response.setMessage("success");
					response.setDescription("Fee reverted sucessfully!");
					return response;

				} else {

					studentOtherFeeDAO.putStudentOtherFee(studentOtherFeeDAO.getStudentOtherFee(
							apiOtherFee.getAccountId(), apiOtherFee.getStudentId(), apiOtherFee.getOtherFeeId()));

					response.setMessage("error");
					response.setDescription("Something went wrong, try again later.");
					return response;

				}

			} else {
				response.setMessage("error");
				response.setDescription("Something went wrong, try again later.");
				return response;

			}

		}
	}

	/**
	 * 
	 * @param goKeMoney
	 * @return
	 */
	public Object asignStudentGoKeMoney(GoKeMoney goKeMoney) {

		Response response = new Response();

		if (studentDAO.getStudentById(goKeMoney.getAccountId(), goKeMoney.getStudentId()) == null) {
			response.setMessage("error");
			response.setDescription("Invalid studentId!");
			return response;

		} else if (sysConfigDAO.getSysConfig(goKeMoney.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Unexpected error occured, contact Admin!");
			return response;

		}
		if (feeBreakdownDAO.getFeeBreakdown(goKeMoney.getAccountId(), FeeConstants.GVMT_MONEY_CODE,
				sysConfigDAO.getSysConfig(goKeMoney.getAccountId()).getTerm(),
				sysConfigDAO.getSysConfig(goKeMoney.getAccountId()).getYear(),
				FeeConstants.GVMT_MONEY_STATUS_ACTIVE) == null) {
			response.setMessage("error");
			response.setDescription("Term/Year not set or GoKe money inactive! Contact Admin.");
			return response;

		} else {

			Student student = studentDAO.getStudentById(goKeMoney.getAccountId(), goKeMoney.getStudentId());
			SysConfig sysConfig = sysConfigDAO.getSysConfig(goKeMoney.getAccountId());

			String feeBreakdownId = feeBreakdownDAO
					.getFeeBreakdown(goKeMoney.getAccountId(), FeeConstants.GVMT_MONEY_CODE, sysConfig.getTerm(),
							sysConfig.getYear(), FeeConstants.GVMT_MONEY_STATUS_ACTIVE)
					.getUuid();

			StudentFee studentFee = new StudentFee();
			studentFee.setAccountId(goKeMoney.getAccountId());
			studentFee.setStudentId(student.getUuid());
			studentFee.setAmountPaid((int) FeeConstants.getGoKeFee(goKeMoney.getAccountId(), feeBreakdownId));
			studentFee.setPayMode(FeeConstants.GVMT_MONEY_CODE);
			studentFee.setTransactionId(FeeConstants.GVMT_MONEY_CODE + RandomStringUtils.randomAlphabetic(5));
			studentFee.setPaidHas(student.getIsBoarding());
			studentFee.setTermPiad(sysConfig.getTerm());
			studentFee.setYearPaid(sysConfig.getYear());

			if (studentFeeDAO.getStudentFee(goKeMoney.getAccountId(), student.getUuid(), FeeConstants.GVMT_MONEY_CODE,
					sysConfig.getTerm(), sysConfig.getYear()) == null) {

				if (studentFeeDAO.putStudentFee(studentFee)) {
					response.setMessage("success");
					response.setDescription("GoKe Fee paid successsfully.");
					return response;

				} else {
					response.setMessage("error");
					response.setDescription("Something went wrong, contact Admin!");
					return response;

				}

			} else {
				response.setMessage("error");
				response.setDescription("GoKe money already assigned!");
				return response;
			}

		}

	}

	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @return
	 */
	public Object revertGoKeMoney(APIRevertGoKeFee revertGoKeFee) {

		Response response = new Response();

		if (sysConfigDAO.getSysConfig(revertGoKeFee.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Term-Year not set!");
			return response;
		}

		SysConfig sysConfig = sysConfigDAO.getSysConfig(revertGoKeFee.getAccountId());

		if (studentFeeDAO.getStudentFee(revertGoKeFee.getAccountId(), revertGoKeFee.getStudentId(),
				FeeConstants.GVMT_MONEY_CODE, sysConfig.getTerm(), sysConfig.getYear()) == null) {

			response.setMessage("error");
			response.setDescription("Nothing to delete!");
			return response;

		} else {

			if (studentFeeDAO.revertStudentGokeFee(revertGoKeFee.getAccountId(), revertGoKeFee.getStudentId(),
					revertGoKeFee.getTermPiad(), revertGoKeFee.getYearPaid(), FeeConstants.GVMT_MONEY_CODE)) {
				response.setMessage("success");
				response.setDescription("GoKe Money reverted successfully.");
				return response;

			} else {
				response.setMessage("error");
				response.setDescription("Please contact Admin!");
				return response;

			}

		}

	}


	/**
	 * 
	 * @param accountId
	 * @param regNo
	 * @return
	 */
	public Object getStudentFee(String accountId, String regNo) {

		StudentResponse studentResponse = new StudentResponse();

		if (studentDAO.getStudentByregNo(accountId, regNo) == null) {
			Response response = new Response();
			response.setMessage("error");
			response.setDescription("RegNo/AccountId Not found!");

			return response;

		} else {

			Student student = studentDAO.getStudentByregNo(accountId, regNo);

			StudentFeeAPI studentFeeAPI = new StudentFeeAPI();

			studentFeeAPI.setRegNo(student.getRegNo());
			studentFeeAPI.setStudentId(student.getUuid());
			studentFeeAPI.setFirstname(student.getFirstname());
			studentFeeAPI.setMiddlename(student.getMiddlename());
			studentFeeAPI.setLastname(student.getLastname());
			studentFeeAPI.setStream(streamDAO.getStream(accountId, student.getCurrentStream()).getDescription());
			studentFeeAPI.setIsBoarding(student.getIsBoarding());

			// basic info

			// fee balance
			Locale locale = new Locale("en", "KE");
			NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

			StudentBalance balance = new StudentBalance();
			double feeBalance = balance.findBalance(accountId, student.getUuid());

			String feeBal = nf.format(feeBalance);

			studentFeeAPI.setBalance(feeBal);

			// fee history
			String term = sysConfigDAO.getSysConfig(accountId).getTerm();
			String year = sysConfigDAO.getSysConfig(accountId).getYear();

			List<StudentFee> feeHistory = new ArrayList<>();
			List<APIStudentFee> apiStudentFeeList = new ArrayList<>();
			if (studentFeeDAO.getStudentFeeList(accountId, student.getUuid(), term, year) != null) {
				feeHistory = studentFeeDAO.getStudentFeeList(accountId, student.getUuid(), term, year);
				feeHistory.forEach(feeHist -> {
					APIStudentFee apiStudentFee = new APIStudentFee();

					try {
						BeanUtils.copyProperties(apiStudentFee, feeHist);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}

					apiStudentFeeList.add(apiStudentFee);
				});

			}

			studentFeeAPI.setFeeHistory(apiStudentFeeList);

			List<APIStudentOtherFee> otherfeeHistory = new ArrayList<>();
			List<APIRevertFee> revertedFeeList = new ArrayList<>();

			if (studentOtherFeeDAO.getStudentOtherFeeList(accountId, student.getUuid()) != null) {

				List<StudentOtherFee> list = studentOtherFeeDAO.getStudentOtherFeeList(accountId, student.getUuid());

				for (StudentOtherFee otherfee : list) {
					APIStudentOtherFee studentOtherFee = new APIStudentOtherFee();

					if (otherFeeDAO.getOtherFee(accountId, otherfee.getOtherFeeId()) != null) {

						OtherFee otherFee = otherFeeDAO.getOtherFee(accountId, otherfee.getOtherFeeId());

						studentOtherFee.setAmount(String.valueOf(otherFee.getAmount()));
						studentOtherFee.setDateAllocated(otherfee.getDateAllocated().toString());
						studentOtherFee.setOtherFeeId(otherFee.getDescription());
						studentOtherFee.setTermPiad(otherfee.getTerm());

						otherfeeHistory.add(studentOtherFee);
					}

				}

			}

			if (revertedMoneyDAO.getRevertedMoneyList(accountId, student.getUuid()) != null) {

				List<RevertedMoney> revertedMoneyList = revertedMoneyDAO.getRevertedMoneyList(accountId,
						student.getUuid());



				for (RevertedMoney revertedMoney : revertedMoneyList) {

					APIRevertFee revertedFee = new APIRevertFee();

					if (otherFeeDAO.getOtherFee(accountId, revertedMoney.getOtherFeeId()) != null) {

						OtherFee otherFee = otherFeeDAO.getOtherFee(accountId, revertedMoney.getOtherFeeId());

						revertedFee.setAmount(String.valueOf(otherFee.getAmount()));
						revertedFee.setOtherFeeId(otherFee.getDescription());
						revertedFee.setDateReverted(revertedMoney.getDateReverted().toString());
						revertedFeeList.add(revertedFee);
					}
				}

			}

			studentFeeAPI.setOtherfeeHistory(otherfeeHistory);
			studentFeeAPI.setRevertedFeeList(revertedFeeList);

			studentResponse.setStudentFeeAPI(studentFeeAPI);

		}

		return studentResponse;
	}



	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @return
	 */
	public Object getStudentOtherFeeLatsRecord(String accountId,String studentId,int size) {

		Response response = new Response();

		if(accountDAO.getAccountById(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response;

		}else if(studentDAO.getStudentById(accountId, studentId) == null) {
			response.setMessage("error");
			response.setDescription("Student not found!");
			return response;

		}else if(studentOtherFeeDAO.getStudentOtherFeeList(accountId, studentId, 0, size).isEmpty()){
			response.setMessage("error");
			response.setDescription("Nothing to display!");
			return response;
		}
		else {

			System.out.println(studentOtherFeeDAO.getStudentOtherFeeList(accountId, studentId, 0, size)); 

			List<APIStudentOtherFee> list = new ArrayList<>();

			studentOtherFeeDAO.getStudentOtherFeeList(accountId, studentId, 0, size).forEach(studentotherfee -> {

				OtherFee ofee = otherFeeDAO.getOtherFee(accountId, studentotherfee.getOtherFeeId());

				APIStudentOtherFee studentOtherFee = new APIStudentOtherFee();
				studentOtherFee.setAmount(ofee.getAmount()+"");
				studentOtherFee.setDateAllocated(studentotherfee.getDateAllocated().toString());
				studentOtherFee.setDescription(ofee.getDescription());
				studentOtherFee.setOtherFeeId(studentotherfee.getOtherFeeId());
				studentOtherFee.setTermPiad(ofee.getTerm());
				list.add(studentOtherFee);
			});


			return list;

		}

	}

	/**
	 * 
	 * @param accountId
	 * @param student
	 * @return
	 */
	public Object addNewStudent(String accountId, StudentInfo student) {

		Response apiResponse = new Response();

		if (sysConfigDAO.getSysConfig(accountId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("System Config not set!");
			return apiResponse;

		} else if (accountDAO.getAccountById(accountId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account Id is invalid.");
			return apiResponse;

		} else if (!validaLength(student.getRegNo())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("RegNo is invalid.");
			return apiResponse;

		} else if (studentDAO.getStudentByregNo(accountId, student.getRegNo()) != null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("RegNo exist!");
			return apiResponse;

		} else if (streamDAO.getStream(accountId, student.getRegStream()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Registration stream is invalid.");
			return apiResponse;

		} else if (streamDAO.getStream(accountId, student.getCurrentStream()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Current stream is invalid.");
			return apiResponse;

		} else if (!validStatus(student.getIsBoarding())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("IsBoarding not set.");
			return apiResponse;

		} else if (!validaLength(student.getFirstname())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Firstname is invalid.");
			return apiResponse;

		} else if (!validaLength(student.getMiddlename())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Middlename is invalid.");
			return apiResponse;

		} else if (!validGender(student.getGender())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Gender is invalid.");
			return apiResponse;

		} else if (StringUtils.isBlank(student.getDob())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("DOB is invalid.");
			return apiResponse;

		} else if (checkHasStatus(student.getHasParent())) {

			if (!validaLength(student.getMiddlename())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Parent name is invalid.");
				return apiResponse;

			} else if (!emailValidator.isValid(student.getParentEmail())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Parent email is invalid.");
				return apiResponse;

			} else if (!validMobile(student.getParentMobile())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Parent mobile is invalid.");
				return apiResponse;

			}else {
				apiResponse.setMessage("success");
				apiResponse.setDescription(newStudentData(student, accountId));
				//return apiResponse;

			}

		} else if (checkHasStatus(student.getHasPrimary())) {

			if (!validaLength(student.getSchoolName())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Primary school name is invalid.");
				return apiResponse;

			} else if (StringUtils.isBlank(student.getIndex())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Primary school index is invalid.");
				return apiResponse;

			} else if (student.getKcpemark().length() != 4) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("K.C.P.E year is invalid.");
				return apiResponse;

			} else if (!validKcpeMark(student.getKcpemark())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("K.C.P.E makrs invalid.");
				return apiResponse;

			}else {
				apiResponse.setMessage("success");
				apiResponse.setDescription(newStudentData(student, accountId));
				//return apiResponse;
			}
		} else {




			apiResponse.setMessage("success this one");
			apiResponse.setDescription(newStudentData(student, accountId));

			return apiResponse;

		}

		return apiResponse;
	}

	/**
	 * 
	 * @param accountId
	 * @param student
	 * @return
	 */
	public Object updateStudent(String accountId, StudentInfo student) {

		Response apiResponse = new Response();

		if (sysConfigDAO.getSysConfig(accountId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("System Config not set!");
			return apiResponse;

		} else if (accountDAO.getAccountById(accountId) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account Id is invalid.");
			return apiResponse;

		} else if (studentDAO.getStudentById(accountId, student.getUuid()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Student Id is invalid.");
			return apiResponse;

		} else if (!validaLength(student.getRegNo())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("RegNo is invalid.");
			return apiResponse;

		} else if (hasDuplicate(student.getRegNo(), accountId)) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Student RegNo duplicated not allowed!");
			return apiResponse;

		} else if (streamDAO.getStream(accountId, student.getRegStream()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Registration stream is invalid.");
			return apiResponse;

		} else if (streamDAO.getStream(accountId, student.getCurrentStream()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Current stream is invalid.");
			return apiResponse;

		} else if (!validStatus(student.getIsBoarding())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("IsBoarding not set.");
			return apiResponse;

		}else if (!validStatus(student.getIsGoKFeeEligibe())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("IsGoKFeeEligibe not set.");
			return apiResponse;

		} else if (!validStatus(student.getIsActive())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("IsActive not set.");
			return apiResponse;

		} else if (!validStatus(student.getIsAlumni())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("IsAlumni not set.");
			return apiResponse;

		} else if (!validaLength(student.getFirstname())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Firstname is invalid.");
			return apiResponse;

		} else if (!validaLength(student.getMiddlename())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Middlename is invalid.");
			return apiResponse;

		} else if (!validGender(student.getGender())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Gender is invalid.");
			return apiResponse;

		} else if (StringUtils.isBlank(student.getDob())) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("DOB is invalid.");
			return apiResponse;

		} else if (checkHasStatus(student.getHasParent())) {

			if (!validaLength(student.getMiddlename())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Parent name is invalid.");
				return apiResponse;

			} else if (!emailValidator.isValid(student.getParentEmail())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Parent email is invalid.");
				return apiResponse;

			} else if (!validMobile(student.getParentMobile())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Parent mobile is invalid.");
				return apiResponse;

			} else {
				apiResponse.setMessage("success");
				apiResponse.setDescription(updateStudentData(student, accountId));
				return apiResponse;
			}

		} else if (checkHasStatus(student.getHasPrimary())) {

			if (!validaLength(student.getSchoolName())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Primary school name is invalid.");
				return apiResponse;

			} else if (StringUtils.isBlank(student.getIndex())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Primary school index is invalid.");
				return apiResponse;

			} else if (student.getKcpeyear().trim().length() != 4) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("K.C.P.E year is invalid.");
				return apiResponse;

			} else if (!validKcpeMark(student.getKcpemark())) {
				apiResponse.setMessage("error");
				apiResponse.setDescription("K.C.P.E makrs invalid.");
				return apiResponse;

			} else {
				apiResponse.setMessage("success");
				apiResponse.setDescription(updateStudentData(student, accountId));
				return apiResponse;

			}

		} else {

			apiResponse.setMessage("success");
			apiResponse.setDescription(updateStudentData(student, accountId));

			return apiResponse;

		}

		// return apiResponse;
	}

	private String updateStudentData(StudentInfo student, String accountId) {

		SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);

		// basic
		Student newstudent = studentDAO.getStudentById(accountId, student.getUuid());
		newstudent.setRegStream(student.getRegStream());
		newstudent.setCurrentStream(student.getCurrentStream());
		newstudent.setIsActive(student.getIsActive());
		newstudent.setIsAlumni(student.getIsAlumni());
		newstudent.setIsBoarding(student.getIsBoarding());
		newstudent.setIsGoKFeeEligibe(student.getIsGoKFeeEligibe()); 
		newstudent.setRegNo(student.getRegNo());
		newstudent.setFirstname(student.getFirstname());
		newstudent.setMiddlename(student.getMiddlename());
		newstudent.setLastname(student.getLastname());
		newstudent.setGender(student.getGender().toUpperCase());
		newstudent.setDob(student.getDob());
		newstudent.setBcertNo(student.getBcertNo());
		newstudent.setCounty(student.getCounty());
		newstudent.setRegTerm(sysConfig.getTerm());
		newstudent.setFinalYear(student.getFinalYear());
		newstudent.setFinalTerm(student.getFinalTerm());
		// TODO
		newstudent.setPassport(renameImage(student.getPassport(), student.getRegNo()));
		newstudent.setLastUpdated(new Date().toString());

		String response = "Test";

		if (studentDAO.updateStudent(newstudent)) {

			response = "Student basic info updated successfully.\n";

			// parent
			if (checkHasStatus(student.getHasParent())) {
				StudentParent studentParent = parentsDAO.getParent(accountId, student.getUuid());
				if (studentParent == null) {
					studentParent = new StudentParent();
					studentParent.setAccountId(accountId);
					studentParent.setStudentId(student.getUuid());
					studentParent.setName(student.getParentName());
					studentParent.setMobile(student.getParentMobile());
					studentParent.setEmail(student.getParentEmail());
					if (parentsDAO.putParent(studentParent)) {

						response += "Student parent info added successfully.\n";

					} else {

						response += "Student parent info NOT added.\n";

					}

				} else {
					studentParent.setName(student.getParentName());
					studentParent.setMobile(student.getParentMobile());
					studentParent.setEmail(student.getParentEmail());

					if (parentsDAO.updateParent(studentParent)) {

						response += "Student parent info updated successfully.\n";

					} else {

						response += "Student parent info NOT updated.\n";

					}
				}
			}

			// primary
			if (checkHasStatus(student.getHasPrimary())) {
				StudentPrimary studentPrimary = primaryDAO.getStudentPrimary(accountId, student.getUuid());

				if (studentPrimary == null) {
					studentPrimary = new StudentPrimary();
					studentPrimary.setAccountId(accountId);
					studentPrimary.setStudentId(student.getUuid());
					studentPrimary.setSchoolName(student.getSchoolName());
					studentPrimary.setIndex(student.getIndex());
					studentPrimary.setKcpemark(student.getKcpemark());
					studentPrimary.setKcpeyear(student.getKcpeyear());
					if (primaryDAO.putStudentPrimary(studentPrimary)) {

						response += "Student primary info added successfully.\n";

					} else {

						response += "Student primary info NOT added.\n";

					}

				} else {
					studentPrimary.setSchoolName(student.getSchoolName());
					studentPrimary.setIndex(student.getIndex());
					studentPrimary.setKcpemark(student.getKcpemark());
					studentPrimary.setKcpeyear(student.getKcpeyear());

					if (primaryDAO.updateStudentPrimary(studentPrimary)) {

						response += "Student primary info updated successfully.";

					} else {

						response += "Student primary info NOT updated.";

					}
				}
			}

		}

		return response;

	}

	/**
	 * 
	 * @param student
	 * @param accountId
	 * @return
	 */


	private String newStudentData(StudentInfo student, String accountId) {

		SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);
		int year = Calendar.getInstance().get(Calendar.YEAR);

		// basic
		Student newstudent = new Student();
		newstudent.setAccountId(accountId);
		newstudent.setRegStream(student.getRegStream());
		newstudent.setCurrentStream(student.getCurrentStream());
		newstudent.setIsActive("1");
		newstudent.setIsAlumni("0");
		newstudent.setIsBoarding(student.getIsBoarding());
		newstudent.setIsGoKFeeEligibe("0"); 
		newstudent.setRegNo(student.getRegNo());
		newstudent.setFirstname(student.getFirstname());
		newstudent.setMiddlename(student.getMiddlename());
		newstudent.setLastname(student.getLastname());
		newstudent.setGender(student.getGender().toUpperCase());
		newstudent.setDob(student.getDob());
		newstudent.setBcertNo(student.getBcertNo());
		newstudent.setCounty(student.getCounty());
		newstudent.setRegTerm(sysConfig.getTerm());
		newstudent.setFinalYear(year + 3);
		newstudent.setFinalTerm(3);
		// TODO
		newstudent.setPassport(renameImage(student.getPassport(), student.getRegNo()));
		newstudent.setLastUpdated(new Date().toString());


		String response = "";

		if (studentDAO.putStudent(newstudent)) {

			response = "Student basic info saved successfully.\n";
			// TODO

			subjectDAO.getSubjects(accountId).forEach(subject -> {
				ApiSubject apiSubject = new ApiSubject();
				apiSubject.setAccountId(accountId);
				apiSubject.setStudentId(newstudent.getUuid());
				apiSubject.setSubjectId(subject.getUuid());
				assignSubject(apiSubject);

			});

			// parent
			if (checkHasStatus(student.getHasParent())) {
				StudentParent studentParent = new StudentParent();
				studentParent.setAccountId(accountId);
				studentParent.setStudentId(newstudent.getUuid());
				studentParent.setName(student.getParentName());
				studentParent.setMobile(student.getParentMobile());
				studentParent.setEmail(student.getParentEmail());

				if (parentsDAO.putParent(studentParent)) {

					response += "Student parent info saved successfully.\n";

				} else {

					response += "Student parent info NOT saved.\n";

				}
			}

			// primary
			if (checkHasStatus(student.getHasPrimary())) {
				StudentPrimary studentPrimary = new StudentPrimary();
				studentPrimary.setAccountId(accountId);
				studentPrimary.setStudentId(newstudent.getUuid());
				studentPrimary.setSchoolName(student.getSchoolName());
				studentPrimary.setIndex(student.getIndex());
				studentPrimary.setKcpemark(student.getKcpemark());
				studentPrimary.setKcpeyear(student.getKcpeyear());

				if (primaryDAO.putStudentPrimary(studentPrimary)) {

					response += "Student primary info saved successfully.\n";

				} else {

					response += "Student primary info NOT saved.\n";

				}
			}

		}



		return response;



	}

	/**
	 * 
	 * @param accountId
	 * @param id
	 * @return
	 */
	public Object deleteSubject(String accountId, String uuid) {

		ApiResponse apiResponse = new ApiResponse();

		if (studentSubjectDAO.getSubjectById(accountId, uuid) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Nothing to delete!");
			return apiResponse;

		} else {

			if (studentSubjectDAO.deleteSubject(accountId, uuid)) {
				apiResponse.setMessage("success");
				apiResponse.setDescription("Subject was deleted successfully.");

			} else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Something went horribly wrong, contact admin.");
			}

		}

		return apiResponse;
	}

	/**
	 * 
	 * @param apiSubject
	 * @return
	 */
	public Object getListofSubjects(String accountId) {

		Response response = new Response();

		if (subjectDAO.getSubjects(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Invalid accountId!");
			return response;

		} else {

			List<ApiSubject> apiSubjectList = new ArrayList<>();

			subjectDAO.getSubjects(accountId).forEach(subject -> {

				ApiSubject apiSubject = new ApiSubject();
				apiSubject.setAccountId(accountId);
				apiSubject.setDescription(subject.getDescription());
				apiSubject.setSubjectId(subject.getUuid());

				apiSubjectList.add(apiSubject);
			});

			return apiSubjectList;

		}

	}

	/**
	 * 
	 * @param apiSubject
	 * @return
	 */
	public Object assignSubject(ApiSubject apiSubject) {

		ApiResponse apiResponse = new ApiResponse();

		if (studentSubjectDAO.getstudentSubject(apiSubject.getStudentId(), apiSubject.getSubjectId()) != null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Subject already assigned!");
			return apiResponse;

		} else if (subjectDAO.getSubjectById(apiSubject.getAccountId(), apiSubject.getSubjectId()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("SubjectId not found!");
			return apiResponse;

		} else {
			StudentSubject studentsub = new StudentSubject();
			studentsub.setAccountId(apiSubject.getAccountId());
			studentsub.setStudentId(apiSubject.getStudentId());
			studentsub.setSubjectId(apiSubject.getSubjectId());

			if (studentSubjectDAO.putStudentSubject(studentsub)) {
				apiResponse.setMessage("success");
				apiResponse.setDescription("Subject assiged successfully.");

			} else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Subject not assiged!");

			}

		}

		return apiResponse;
	}

	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @return
	 */
	public List<Object> getSubjects(String accountId, String studentId) {
		List<StudentSubject> subjectlist = new ArrayList<>();
		if (studentSubjectDAO.getStudentSubjects(studentId) != null) {
			subjectlist = studentSubjectDAO.getStudentSubjects(studentId);
		}

		List<ApiSubject> apiSubjectList = new ArrayList<>();

		subjectlist.forEach(sub -> {
			ApiSubject apiSubject = new ApiSubject();
			apiSubject.setAccountId(sub.getAccountId());
			apiSubject.setStudentId(sub.getStudentId());
			apiSubject.setSubjectId(subjectDAO.getSubjectById(accountId, sub.getSubjectId()).getUuid());
			apiSubject.setUuid(sub.getUuid());
			apiSubject.setDescription(subjectDAO.getSubjectById(accountId, sub.getSubjectId()).getDescription());

			apiSubjectList.add(apiSubject);

		});

		return apiSubjectList.stream().collect(Collectors.toList());
	}

	/**
	 * 
	 * @param accountId
	 * @param action
	 * @param studentIds
	 * @return
	 */
	public Object studentStatus(String accountId, String action, List<StudentStatus> students) {

		ApiResponse response = new ApiResponse();
		boolean update = false;

		if (StringUtils.equals(action, "activate")) {

			for (StudentStatus studentid : students) {

				if (studentDAO.getStudentById(accountId, studentid.getUuid()) != null) {
					Student student = studentDAO.getStudentById(accountId, studentid.getUuid());
					student.setIsActive("1");
					student.setIsAlumni("0");
					update = studentDAO.updateStudent(student);
				}
			}

			if (update) {
				response.setMessage("success");
				response.setDescription("Student(s) activated successfully.");

			} else {
				response.setMessage("error");
				response.setDescription("Something went wrong, try again later.");

			}

		} else if (StringUtils.equals(action, "inactivate")) {

			for (StudentStatus studentid : students) {

				if (studentDAO.getStudentById(accountId, studentid.getUuid()) != null) {
					Student student = studentDAO.getStudentById(accountId, studentid.getUuid());
					student.setIsActive("0");
					student.setIsAlumni("1");
					update = studentDAO.updateStudent(student);
				}
			}

			if (update) {
				response.setMessage("success");
				response.setDescription("Student(s) inactivated successfully.");

			} else {
				response.setMessage("error");
				response.setDescription("Something went wrong, try again later.");

			}

		} else {

			response.setMessage("error");
			response.setDescription("Invalid action '" + action + "'");

		}

		return response;
	}

	/**
	 * 
	 * @param accountId
	 * @param changeClass
	 * @return
	 */
	public Object changeClass(String accountId, List<ChangeClass> changeClass) {

		ApiResponse response = new ApiResponse();
		boolean update = false;

		for (ChangeClass stream : changeClass) {

			if (studentDAO.getStudentById(accountId, stream.getStudentId()) != null) {
				Student student = studentDAO.getStudentById(accountId, stream.getStudentId());
				student.setCurrentStream(stream.getNewClassId());
				update = studentDAO.updateStudent(student);
			}

		}

		if (update) {
			response.setMessage("success");
			response.setDescription("Class changed successfully.");

		} else {
			response.setMessage("error");
			response.setDescription("Something went wrong, try again later.");

		}

		return response;
	}


	/**
	 * 
	 * @param accountId
	 * @param status
	 * @return
	 */
	public Object getActiveStudents(String accountId, String status) {

		Response response = new Response();

		if(accountDAO.getAccountById(accountId) == null) {
			//AccountId not found
			response.setMessage("error");
			response.setDescription("AccountId not found!"); 
			return response;

		}else if(studentDAO.activeCount(accountId, status) <= 0) {
			//No students
			response.setMessage("error");
			response.setDescription("No students found!"); 
			return response;

		}else if(!validStatus(status)) {
			//No students
			response.setMessage("error");
			response.setDescription("Status invalid!"); 
			return response;

		}else {
			int totalActive = studentDAO.activeCount(accountId, status); 
			String msg = "";
			if(StringUtils.equals(status, "1")) {
				msg = "Active";
			}else {
				msg = "Inactive"; 
			}
			response.setMessage("sucess");
			response.setDescription("Total " + msg + " student(s)  | " + totalActive);  
			return response;

		}
	}


	/** TODO
	 * 
	 * @param accountId
	 * @param currentPage
	 * @param pageSize
	 * @param whatPage
	 * @return
	 */


	public Object studentPagination(String accountId, int currentPage , int pageSize , String whatPage) {
		String isActive = "1";
		Response response = new Response();
		pageSize = 4;

		if(accountDAO.getAccountById(accountId) == null) {
			response.setMessage("error");
			response.setDescription("AccountId not found!"); 
			return response;

		} else if(studentDAO.activeCount(accountId, isActive) <= 0){
			response.setMessage("error");
			response.setDescription("No student to diplay!"); 
			return response;
		}

		else {

			StudentPage studentPage = new StudentPage();
			List<StudentInfo> studentInfoList = new ArrayList<>();

			int total = studentDAO.activeCount(accountId, isActive);
			int pages = total / pageSize ;
			int rem = total % pageSize;
			if(rem >= 1) {
				pages = pages + 1;
			}

			switch (whatPage) {

			case "first":

				currentPage = 1;

				//StudentPage studentPage = new StudentPage();
				studentPage.setCurrentPage(currentPage);
				studentPage.setPages(pages);
				studentPage.setPageSize(pageSize);
				studentPage.setTotal(total);

				if(!studentDAO.getAllStudent(accountId, isActive, pageSize, 0).isEmpty()) {
					studentDAO.getAllStudent(accountId, isActive, pageSize, 0).
					parallelStream().
					forEach(student -> {

						StudentInfo studentInfo = new StudentInfo();
						try {
							BeanUtils.copyProperties(studentInfo, student);
							studentInfoList.add(studentInfo);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}

					});

					studentPage.setContents(studentInfoList); 
				}

				return studentPage;

			case "next":

				int nextPage = currentPage + 1;
				int nextIndex = (nextPage * pageSize) - pageSize;

				studentPage.setCurrentPage(currentPage);
				studentPage.setPages(pages);
				studentPage.setPageSize(pageSize);
				studentPage.setTotal(total);


				if(!studentDAO.getAllStudent(accountId, isActive,  pageSize, nextIndex).isEmpty()) {
					studentDAO.getAllStudent(accountId, isActive,  pageSize, nextIndex)
					.parallelStream()
					.forEach(student -> {

						StudentInfo studentInfo = new StudentInfo();
						try {
							BeanUtils.copyProperties(studentInfo, student);
							studentInfoList.add(studentInfo);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}

					});

					studentPage.setContents(studentInfoList); 
				}

				return studentPage;

			case "prev":

				int prevPage = currentPage - 1;
				int prevIndex = (prevPage * pageSize) - pageSize;

				studentPage.setCurrentPage(currentPage);
				studentPage.setPages(pages);
				studentPage.setPageSize(pageSize);
				studentPage.setTotal(total);

				if(!studentDAO.getAllStudent(accountId, isActive,  pageSize, prevIndex).isEmpty()) {
					studentDAO.getAllStudent(accountId, isActive,  pageSize, prevIndex).
					parallelStream().
					forEach(student -> {

						StudentInfo studentInfo = new StudentInfo();
						try {
							BeanUtils.copyProperties(studentInfo, student);
							studentInfoList.add(studentInfo);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}

					});

					studentPage.setContents(studentInfoList); 
				}

				return studentPage;

			case "last":

				studentPage.setCurrentPage(currentPage);
				studentPage.setPages(pages);
				studentPage.setPageSize(pageSize);
				studentPage.setTotal(total);

				if(!studentDAO.getAllStudent(accountId, isActive,  pageSize, total-pageSize).isEmpty()) {
					studentDAO.getAllStudent(accountId, isActive,  pageSize, total-pageSize).
					parallelStream()
					.forEach(student -> {

						StudentInfo studentInfo = new StudentInfo();
						try {
							BeanUtils.copyProperties(studentInfo, student);
							studentInfoList.add(studentInfo);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}

					});

					studentPage.setContents(studentInfoList); 
				}

				return studentPage;

			default:
				currentPage = 1;

				//StudentPage studentPage = new StudentPage();
				studentPage.setCurrentPage(currentPage);
				studentPage.setPages(pages);
				studentPage.setPageSize(pageSize);
				studentPage.setTotal(total);

				if(!studentDAO.getAllStudent(accountId, isActive, pageSize, 0).isEmpty()) {
					studentDAO.getAllStudent(accountId, isActive, pageSize, 0).
					parallelStream().
					forEach(student -> {

						StudentInfo studentInfo = new StudentInfo();
						try {
							BeanUtils.copyProperties(studentInfo, student);
							studentInfoList.add(studentInfo);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}

					});

					studentPage.setContents(studentInfoList); 
				}

				return studentPage;

			}

		}

	}

	/**
	 * 
	 * @param accountId
	 * @param filter
	 * @return
	 */

	public Object getStudentFilter(String accountId, StudentFilter filter) {

		List<StudentInfo> studentInfoList = new ArrayList<>();

		if (filter.getStart() >= 0 && filter.getSize() > 0) {

			studentDAO.getAllStudent(accountId, filter.getStart(), filter.getSize()).forEach(student -> {

				StudentInfo studentInfo = new StudentInfo();

				try {
					BeanUtils.copyProperties(studentInfo, student);
					// TODO
					studentInfo.setPassport(getB64Image(student.getPassport()));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				studentInfoList.add(studentInfo);
			});

		} else if (!StringUtils.isBlank(filter.getQuery())) {

			studentDAO.searchStudent(accountId, filter.getQuery()).forEach(student -> {
				StudentInfo studentInfo = new StudentInfo();

				try {
					BeanUtils.copyProperties(studentInfo, student);
					// TODO
					studentInfo.setPassport(getB64Image(student.getPassport()));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				studentInfoList.add(studentInfo);
			});
		} else if (!StringUtils.isBlank(filter.getCurrentStream())) {

			studentDAO.getStudentByStream(accountId, filter.getCurrentStream()).forEach(student -> {
				StudentInfo studentInfo = new StudentInfo();

				try {
					BeanUtils.copyProperties(studentInfo, student);
					// TODO
					studentInfo.setPassport(getB64Image(student.getPassport()));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				studentInfoList.add(studentInfo);
			});

		} else {
			studentDAO.getAllStudent(accountId, 0, 15).forEach(student -> {
				StudentInfo studentInfo = new StudentInfo();

				try {
					BeanUtils.copyProperties(studentInfo, student);
					// TODO
					studentInfo.setPassport(getB64Image(student.getPassport()));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				studentInfoList.add(studentInfo);
			});

		}

		return studentInfoList;
	}

	/**
	 * to detect duplicate value
	 * 
	 * @param value
	 * @return
	 */
	private boolean hasDuplicate(String value, String accountId) {
		List<Student> accountList = new ArrayList<>();
		// if not account with such a key, return true and proceed

		if (studentDAO.findDuplicate(accountId, value) == null) {
			return false;
		} else {
			accountList = studentDAO.findDuplicate(accountId, value);
			// System.out.println("size: " + accountList.size() + " key: " + value );
			// if only one account has such a key, return true and proceed
			if (accountList.size() == 1) {
				return false;

				// if you reach here, there are more than one accounts sharing the provided key,
				// return false.
			} else if (accountList.size() > 1) {

				return true;

			} else if (accountList.size() == 0) {
				return false;

			} else {
				return false;

			}
		}
	}

	/**
	 * 
	 * @param gender
	 * @return
	 */
	private boolean validGender(String gender) {
		String[] allowed = { "M", "F", "m", "f" };
		List<String> allowedList = new ArrayList<>();
		allowedList = Arrays.asList(allowed);
		if (allowedList.contains(gender)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param param
	 * @return
	 */
	private boolean checkHasStatus(String param) {

		return StringUtils.equals(param, "true") ? true : false;
	}

	/**
	 * 
	 * @param initalName
	 * @param regNo
	 * @return
	 */
	private String renameImage(String initalName, String regNo) {
		String renamed = initalName;

		File passport = new File(DATA_DIRECTORY + initalName);

		if (passport.renameTo(new File(DATA_DIRECTORY + regNo + ".png")))
			renamed = regNo + ".png";
		return renamed;

	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	private boolean validaLength(String value) {
		if (value.length() < 3) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param mobile
	 * @return
	 */
	private boolean validMobile(String mobile) {
		boolean valid = false;

		if (mobile.length() == 9 && StringUtils.isNumeric(mobile)) {
			valid = true;
		}

		return valid;
	}

	/**
	 * 
	 * @param isBoarding
	 * @return
	 */
	private boolean validStatus(String isBoarding) {
		String[] allowed = { "1", "0" };
		List<String> allowedList = new ArrayList<>();
		allowedList = Arrays.asList(allowed);
		if (allowedList.contains(isBoarding)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param uuid
	 * @param acessLevelId
	 * @return
	 */
	private boolean staffAllowedToAlterFee(String uuid, String acessLevelId) {
		// Principal_Bursar
		String[] allowed = { "C3915245-00EE-4EF4-9898-ACE59683DD60", "0DE968C9-7309-C481-58F7-AB6CDB1011EF" };
		List<String> allowedList = new ArrayList<>();
		allowedList = Arrays.asList(allowed);
		if (allowedList.contains(acessLevelId)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param path
	 *            image path
	 * @return
	 */
	public static String getB64Image(String path) {
		String b64 = "";
		int width = 963; // width of the image
		int height = 640; // height of the image
		BufferedImage image = null;
		File f = null;
		String dir = DATA_DIRECTORY;
		String fullpath = dir + path;
		// read image
		try {
			f = new File(fullpath); // image file path

			if (f.exists()) {
				image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				image = ImageIO.read(f);

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "png", baos);
				baos.flush();
				byte[] imageInByteArray = baos.toByteArray();
				baos.close();
				b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);

				return b64;
			} else {
				return b64;
			}

		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param kcpemark
	 * @return
	 */
	private boolean validKcpeMark(String kcpemark) {

		boolean valid = true;

		if (!StringUtils.isNumeric(kcpemark)) {
			valid = false;
		} else if (Integer.valueOf(kcpemark) < 100) {
			valid = false;
		} else if (Integer.valueOf(kcpemark) > 500) {
			valid = false;
		}

		return valid;
	}

}
