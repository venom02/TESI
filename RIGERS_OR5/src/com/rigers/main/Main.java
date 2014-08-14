package com.rigers.main;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.rigers.API.MeterAcquaStats;
import com.rigers.GUI.GUI;
import com.rigers.GUI.StatsGUI;
import com.rigers.db.Edificio;
import com.rigers.db.MeterAcqua;

public class Main {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
//		GUI.main(null);
//		StatsGUI.main(null);
//		testMeterAcquaStats();
		fillMonth(3);
//		System.exit(0);
	}
	
	public static void fillMonth(int id){
		Edificio edificio = new Edificio();
		edificio.setIdEdificio(id);
		DbManager db = new DbManager(edificio);
		db.fillMonth(0);
	};
	
	
	public static void fillDb(){
		DbManager db = new DbManager();
		db.fillDb();
	}
}