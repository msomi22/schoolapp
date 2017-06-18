/**
 * 
 */
package com.yahoo.petermwenda83.server.util.magic;

/**
 * @author peter
 *
 */
public class MiddleNumberFor3Nos {
   
	public double ComputeMiddle(double number1, double number2,double number3){
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
	
}
