/**
 * 
 */
package com.rigers.API;

import java.util.ArrayList;
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
}