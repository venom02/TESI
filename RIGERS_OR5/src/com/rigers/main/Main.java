package com.rigers.main;

import com.rigers.API.MeterAcquaStats;
import com.rigers.db.Edificio;
import com.rigers.db.MeterAcqua;

public class Main {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
//		GUI.main(null);
		Edificio edificio = new Edificio();
		edificio.setIdEdificio(1);
		int month = 7;
		MeterAcquaStats meterAcquaStats = new MeterAcquaStats(edificio);
		MeterAcqua media = meterAcquaStats.monthAverage(month);
		MeterAcqua max = meterAcquaStats.monthMax(month);
		MeterAcqua min = meterAcquaStats.monthMin(month);
		
		System.out.println("Current Readout Value");
		System.out.println("AVG: " + media.getCurrentReadoutValue());
		System.out.println("MAX: " + max.getCurrentReadoutValue());
		System.out.println("MIN: " + min.getCurrentReadoutValue());
		System.out.println("Periodic Readout Value");
		System.out.println("AVG: " + media.getPeriodicReadoutValue());
		System.out.println("MAX: " + max.getPeriodicReadoutValue());
		System.out.println("MIN: " + min.getPeriodicReadoutValue());
	}
	
}
