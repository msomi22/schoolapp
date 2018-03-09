
/*************************************************************
 * Online School Management System                           *
 * Forth Year Project                                        *
 * Maasai Mara University                                    *
 * Bachelor of Science(Computer Science)                     *
 * Year:2015-2016                                            *
 * Name: Njeru Mwenda Peter                                  *
 * ADM NO : BS02/009/2012                                    *
 *                                                           *
 *************************************************************/
package ke.co.qubintel.school.server.bean.money;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author peter
 *
 */
public class Withdraw extends PocketMoney{

	
	private Timestamp withdrawDate;
	
	/** 
	 * 
	 */
	public Withdraw() {
		super();
		withdrawDate = new Timestamp(new Date().getTime());
	}


	
	/**
	 * @return the withdrawDate
	 */
	public Timestamp getWithdrawDate() {
		return withdrawDate;
	}

	/**
	 * @param withdrawDate the withdrawDate to set
	 */
	public void setWithdrawDate(Timestamp withdrawDate) {
		this.withdrawDate = withdrawDate;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Withdraw [withdrawDate=" + withdrawDate + ", getStudentId()=" + getStudentId() + ", getAmount()="
				+ getAmount() + ", getUuid()=" + getUuid() + ", getAccountId()=" + getAccountId() + "]";
	}



	private static final long serialVersionUID = -7496104242563750614L;
}
