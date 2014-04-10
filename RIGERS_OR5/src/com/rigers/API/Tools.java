/**
 * 
 */
package com.rigers.API;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author VenturiEffect
 *
 */
public class Tools {
		
	/**
	 * calcola numero della settimana
	 * @param date
	 * @return
	 */
	protected int getWeekNumber(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		return week;
	}
	
	/**
	 * Calcola media lista interi
	 * @param marks
	 * @return
	 */
	protected int calculateAverage(List <Integer> marks) {
		  Integer sum = 0;
		  if(!marks.isEmpty()) {
		    for (Integer mark : marks) {
		        sum += mark;
		    }
		    return sum / marks.size();
		  }
		  return sum;
		}
}
