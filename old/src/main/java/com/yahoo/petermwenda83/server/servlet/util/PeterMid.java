/**
 * 
 */
package com.yahoo.petermwenda83.server.servlet.util;

/**
 * @author peter
 *
 */
public class PeterMid {
	
   
	/**
	 * @param number1
	 * @param number2
	 * @param number3
	 * @return
	 */
	public static double computeMiddle(double number1, double number2,double number3){
		double middle = 0;
		
		//number 1
		if(number1>number2&&number1>number3){
			if(number2>number3){
				middle = number2;
			}else{middle = number3;}
		}
		
		//number 2
		if(number2>number1&&number2>number3){
				if(number1>number3){
					middle = number1;
				}else{middle = number3;}	
		}
		//number 3
		if(number3>number1&&number3>number2){
			if(number1>number2){
				middle = number1;
			}else{middle = number2;}	
	   }
		
		if(number1==number2 || number1==number3){
			middle = number1;
		}else if(number2==number1 || number2==number3){
			middle = number2;
		}else if(number3==number1 || number3==number2){
			middle = number3;
		}
		
		return middle;
	}
	
	
	public static double computeMax(double number1, double number2,double number3){
		return Math.max(Math.max((int)number1, (int)number2), (int)number3);
	}
	
	
}
