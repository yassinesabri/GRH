/**
 * 
 */
package com.grh.utilities;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checks {
	public static boolean isValidNumber(String number){		
		 try  
		  {  
		    @SuppressWarnings("unused")
			int d = Integer.parseInt(number);  
		  }  
		  catch(NumberFormatException e)  
		  {  
		    return false;  
		  }  
		  return true;
	}
	public static boolean isValidPhoneNumber(String phone){
			String pattern = "^((\\+|00)212|0)6\\d{8}$";
	        Pattern p = Pattern.compile(pattern);
	        Matcher m = p.matcher(phone);
	        return m.matches();
	}
	public static boolean isValidEmail(String email) {
		String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(email);
        return m.matches();
  
	}
	public static boolean isLessThenCurrentDate(String date){
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String nowString = formatter.format(now);
		LocalDate currentDate = LocalDate.parse(nowString);
		LocalDate testDate = LocalDate.parse(date);
		//System.out.println(currentDate.compareTo(testDate));
		return currentDate.compareTo(testDate) >= 0;
	}

}
