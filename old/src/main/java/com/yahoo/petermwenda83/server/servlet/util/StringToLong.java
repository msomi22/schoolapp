package com.yahoo.petermwenda83.server.servlet.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class StringToLong {
	
	  public static void main(String[] args) throws ParseException {
		
		  Long date = getLongDate("2016-05-07T11:28:43");//2016-05-07T11:28:43
		  //2016-05-07T11:28:43
		  System.out.println(date); 
		  String name = "test123";//cc03e747a6afbbcbf8be7668acfebee5
		  System.out.println("MD5 str for " + name + " is " + SecurityUtil.getMD5Hash(name));  
		  
	    
	  }
	  
	  public static Long getLongDate(String datestr) throws ParseException{
		    SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		    isoFormat.setTimeZone(TimeZone.getTimeZone("EAT"));
		    Date date = isoFormat.parse(datestr);
		    long dateLong = date.getTime();
		  return dateLong;
	  }
	}
