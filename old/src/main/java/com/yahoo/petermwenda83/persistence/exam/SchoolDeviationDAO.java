package com.yahoo.petermwenda83.persistence.exam;

import com.yahoo.petermwenda83.bean.exam.Deviation;

public interface SchoolDeviationDAO {
	
	public Deviation getDev(String accountId,String studentId,String year);
	
	public boolean DevExist(String accountId,String studentId,String year);
	
	public boolean putDev(Deviation dev,String accountId,String studentId,String year);
	

}
