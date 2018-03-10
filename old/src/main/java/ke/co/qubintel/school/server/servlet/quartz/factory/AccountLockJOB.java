/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.servlet.quartz.factory;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import ke.co.qubintel.school.server.persistence.schoolaccount.AccountDAO;
import ke.co.qubintel.school.server.persistence.staff.StaffDAO;
import ke.co.qubintel.school.server.servlet.util.SYS_COSTANTS;
import ke.co.qubintel.school.server.servlet.util.SecurityUtil;
/**
 * 
 * @author peter
 *
 */
public class AccountLockJOB implements Job {

	private static AccountDAO accountDAO;
	private static StaffDAO staffDAO;

	static {
		accountDAO = AccountDAO.getInstance();
		staffDAO = StaffDAO.getInstance();
	}

	public void execute(JobExecutionContext context){
		lockAccount();
	}

	
	private void lockAccount() {
		accountDAO.getAccounts().parallelStream().forEach(sch -> {
			sch.setIsActive(SYS_COSTANTS.STATUS_INACTIVE);  
			sch.setUsername("lock"); 
			sch.setPassword("lock123"); 
			accountDAO.updateAccount(sch);
			
			staffDAO.getStaff(sch.getUuid()).parallelStream().forEach(staff -> {
				staff.setIsActive(SYS_COSTANTS.STATUS_INACTIVE); 
				staff.setPassword(SecurityUtil.getMD5Hash("lock123"));   
				staffDAO.updateStaff(staff);
			});
			System.out.println("Account locked!"); 
		});
		
	}

}
