/**
 * 
 */
package com.yahoo.petermwenda83.util.performance.comparator;

import java.util.Comparator;

import com.yahoo.petermwenda83.bean.exam.ClassMean;

/**
 * @author peter
 *
 */
public class ClassMeanComparator implements Comparator<ClassMean>{

	@Override
	public int compare(ClassMean o1, ClassMean o2) {
		if(o1.getStreammean() > o2.getStreammean()){
			return 1;
		}else if(o1.getStreammean() < o2.getStreammean()){ 
			return -1;
		}else{
			return 0;
		}
	}

}
