/**
 * 
 */
package com.rigers.API;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author VenturiEffect
 * 
 */
public class Tools {

	/**
	 * calcola numero della settimana
	 * 
	 * @param date
	 * @return
	 */
	protected int getWeekNumber(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		return week;
	}

	/**
	 * Calcola media lista interi
	 * 
	 * @param marks
	 * @return
	 */
	protected int average(List<Integer> marks) {
		int sum = 0;

		if (!marks.isEmpty()) {
			for (Integer mark : marks) {
				sum += mark;
			}
			return sum / marks.size();
		}
		return sum;
	}

	/**
	 * controlla se la lista è vuota ed inserisce un valore 0
	 * @param list
	 */
	protected void chk(ArrayList list) {
		if (list.isEmpty()) {
			list.add(0);
		}
	}
	
	protected List<Date> weekDates(int year, int month, int date){
//		// Get calendar set to given date and time
		Calendar cal = new GregorianCalendar(year, month, date).getInstance();
//		// Set the calendar to monday and sunday of the given week
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date dateFrom = cal.getTime();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date dateTo = cal.getTime();
		
		List<Date> list = new ArrayList<Date>();
		list.add(dateFrom);
		list.add(dateTo);
		return list;
	}
	
	protected List<Date> monthDates(int year, int month){
		Calendar cal = new GregorianCalendar(year, month, 1).getInstance();
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		Date dateTo = cal.getTime();
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		Date dateFrom = cal.getTime();
		
		List<Date> list = new ArrayList<Date>();
		list.add(dateFrom);
		list.add(dateTo);
		return list;
	}
	
	protected List<Date> dayDates(int year, int month, int date){
		Calendar cal = new GregorianCalendar(year, month, date).getInstance();
		Date dateFrom = cal.getTime();
		cal.add(Calendar.DATE, 1);
		Date dateTo = cal.getTime();
		
		List<Date> list = new ArrayList<Date>();
		list.add(dateFrom);
		list.add(dateTo);
		return list;
	}
	
}