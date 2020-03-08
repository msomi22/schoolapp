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
package ke.co.qubintel.school.util.performance.comparator;

import java.util.Comparator;

import ke.co.qubintel.school.server.servlet.reports.Performance2;

/**
 * @author peter
 *
 */
public class PointsComparator implements Comparator<Performance2>{

	@Override
	public int compare(Performance2 o1, Performance2 o2) {
		if(o1.getTotalPoint() > o2.getTotalPoint()){
			return 1;
		}else if(o1.getTotalPoint() < o2.getTotalPoint()){ 
			return -1;
		}else{
			return 0;
		}
	}

}

