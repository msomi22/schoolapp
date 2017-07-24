
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
package com.yahoo.petermwenda83.bean.money;

/**
 * @author peter
 *
 */
public class Deposit extends PocketMoney {
	
	/** 
	 * 
	 */
	private static final long serialVersionUID = 7582945268897264721L;
	
	public Deposit() {
		super();
	}

	

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Deposit");
		builder.append("[getUuid() = ");
		builder.append(getUuid());
		builder.append(",getStudentUuid() =");
		builder.append(getStudentUuid());
		builder.append(", getAmount()=");
		builder.append(getAmount());
		builder.append(", getSystemUser()=");
		builder.append(getSystemUser());
		builder.append(", getDateCommitted()=");
		builder.append(getDateCommitted());
		builder.append(", getTerm()=");
		builder.append(getTerm());
		builder.append(", getYear()=");
		builder.append(getYear());
		builder.append("]");
		return builder.toString(); 
		}
}
