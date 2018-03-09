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

import ke.co.qubintel.school.server.bean.chat.Chat;


/** 
 * Comparator class that sort a collection object of {@link Chat} by date  
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class ChatDateComparator implements Comparator<Chat> {

	/**
	 * Indicates whether some other object is "equal to" this comparator.
	 * 
	 * @param obj
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		return false;				
	}

	@Override
	public int compare(Chat chat1, Chat chat2) {
		return chat1.getDateSent().compareTo(chat2.getDateSent()); 
	}
}
