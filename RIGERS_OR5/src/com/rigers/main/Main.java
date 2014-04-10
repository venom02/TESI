package com.rigers.main;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rigers.API.MeterAcquaStats;
import com.rigers.GUI.GUI;
import com.rigers.db.Edificio;
import com.rigers.db.LetturaDispositivo;
import com.rigers.persistence.HibernateUtil;

public class Main {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
//		GUI.main(null);
		Edificio edificio = new Edificio();
		edificio.setIdEdificio(1);
		MeterAcquaStats meterAcquaStats = new MeterAcquaStats(edificio);
		meterAcquaStats.monthAverage(7);
		
	}
	
}
