package es.uma.sportjump.sjm.back.utils;

import java.util.Calendar;
import java.util.Date;

public class BackUtils {
	
	public static boolean isDateEquals(Date date1, Date date2){
		boolean res = false;
		
		if (date1 == null && date2 == null){
			res = true;
		}else if (date1 != null && date2 != null){
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			
			if ((cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) &&
				(cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) &&
				(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))){
				res = true;
			}else {
				res = false;
			}
			
		}else{
			res = false;
		}
		
		return res;
	}
	

}
