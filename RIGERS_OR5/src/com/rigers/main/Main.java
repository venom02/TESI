package com.rigers.main;

import java.util.Calendar;
import java.util.Date;

import com.rigers.API.MeterAcquaStats;
import com.rigers.GUI.GUI;
import com.rigers.GUI.StatsGUI;
import com.rigers.db.Edificio;
import com.rigers.db.MeterAcqua;

public class Main {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
//		GUI.main(null);
		StatsGUI.main(null);
//		testMeterAcquaStats();
			
	}
	
	//TEST TEST TEST
	private static void testMeterAcquaStats(){
		//TEST METERACQUASTATS
		Edificio edificio = new Edificio();
		edificio.setIdEdificio(1);
		int year = 2014;
		int month = 10;
		int date = 13;
		MeterAcquaStats meterAcquaStats = new MeterAcquaStats(edificio);
		MeterAcqua mediaMon = meterAcquaStats.monthAverage(month);
		MeterAcqua maxMon = meterAcquaStats.monthMax(month);
		MeterAcqua minMon = meterAcquaStats.monthMin(month);
		MeterAcqua mediaWeek = meterAcquaStats.weekAverage(year, month, date);
		MeterAcqua maxWeek = meterAcquaStats.weekMax(year, month, date);
		MeterAcqua minWeek = meterAcquaStats.weekMin(year, month, date);
		MeterAcqua actual = meterAcquaStats.actual(year, month, date);
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);
		System.out.println(cal.getTime());
		System.out.println("Current Readout Value");
		System.out.println("AVG MONTH: " + mediaMon.getCurrentReadoutValue() 
				+ "\tMAX MONTH: " + maxMon.getCurrentReadoutValue() 
				+ "\tMIN MONTH: " + minMon.getCurrentReadoutValue());
		System.out.println("AVG WEEK: " + mediaWeek.getCurrentReadoutValue() 
				+ "\tMAX WEEK: " + maxWeek.getCurrentReadoutValue() 
				+ "\tMIN WEEK: " + minWeek.getCurrentReadoutValue());
		System.out.println("Periodic Readout Value");
		System.out.println("AVG MONTH: " + mediaMon.getPeriodicReadoutValue() 
				+ "\tMAX MONTH: " + maxMon.getPeriodicReadoutValue() 
				+ "\tMIN MONTH: " + minMon.getPeriodicReadoutValue());
		System.out.println("AVG WEEK: " + mediaWeek.getPeriodicReadoutValue() 
				+ "\tMAX WEEK: " + maxWeek.getPeriodicReadoutValue() 
				+ "\tMIN WEEK: " + minWeek.getPeriodicReadoutValue());
		System.out.println("ACTUAL: " + actual.getCurrentReadoutValue());
	}
}
