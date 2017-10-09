/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.reports.fee;

import java.util.List;

import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.persistence.exam.SysConfigDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDAO;
import com.yahoo.petermwenda83.persistence.money.FeeBreakdownDescDAO;
import com.yahoo.petermwenda83.persistence.money.StudentFeeDAO;
import com.yahoo.petermwenda83.persistence.money.TermFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.OtherFeeDAO;
import com.yahoo.petermwenda83.persistence.othermoney.StudentOtherFeeDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.servlet.finance.StudentBalance;

/**
 * @author peter
 *
 */
public class FeeBalancePerStream {

	private static StudentDAO studentDAO;
	private static AccountDAO accountDAO;

	private static StudentFeeDAO studentFeeDAO;
	private static OtherFeeDAO otherFeeDAO;
	private static StudentOtherFeeDAO studentOtherFeeDAO;
	//private static RevertedMoneyDAO revertedMoneyDAO;
	private static FeeBreakdownDAO feeBreakdownDAO;
	private static FeeBreakdownDescDAO feeBreakdownDescDAO;

	private static TermFeeDAO termFeeDAO;
	private static SysConfigDAO sysConfigDAO;


	static{
		studentDAO = StudentDAO.getInstance();
		accountDAO = AccountDAO.getInstance();

		studentFeeDAO = StudentFeeDAO.getInstance();
		otherFeeDAO = OtherFeeDAO.getInstance();
		studentOtherFeeDAO = StudentOtherFeeDAO.getInstance();
		///revertedMoneyDAO = RevertedMoneyDAO.getInstance();
		feeBreakdownDAO = FeeBreakdownDAO.getInstance();
		feeBreakdownDescDAO = FeeBreakdownDescDAO.getInstance();

		termFeeDAO = TermFeeDAO.getInstance();
		sysConfigDAO = SysConfigDAO.getInstance();
	}




	public static void getFeeBalance(String accountId, String streamId, int threshold) {

		if(studentDAO.getStudentByStream(accountId, streamId) != null) {

			List<Student> studentslist = studentDAO.getStudentByStream(accountId, streamId); 

			studentslist.parallelStream().forEach(student -> {

				StudentBalance balance = new StudentBalance();
				
				double feeBalance1 = new StudentBalance(accountId, student.getUuid()).build();
				double feeBalance = balance.findBalance(accountId, student.getUuid());

				if( (int)feeBalance > threshold) {
					//add student to list
				}

			});



		}



	}

}
