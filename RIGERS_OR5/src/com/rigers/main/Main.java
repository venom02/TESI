package com.rigers.main;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rigers.API.DbManager;
import com.rigers.GUI.StatsGUI;
import com.rigers.db.Dispositivo;
import com.rigers.db.DispositivoId;
import com.rigers.db.Edificio;
import com.rigers.db.LetturaDispositivo;
import com.rigers.persistence.HibernateUtil;

public class Main {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
//		GUI.main(null);
		StatsGUI.main(null);
//		testMeterAcquaStats();
//		fillMonth(7);
//		fillDb();

		System.exit(0);
	}
	
	public static void fillMonth(int id){
		Edificio edificio = new Edificio();
		edificio.setIdEdificio(id);
		DbManager db = new DbManager(edificio);
		db.fillMonth(1);
	};
	
	
	public static void fillDb(){
		DbManager db = new DbManager();
		db.fillDb();
	}

	public static void testGenerateLet(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;  
		try{
			tx = session.beginTransaction();
	
			Date date = new GregorianCalendar(2014, 01, 01).getTime();
					
			//CODE
			
			Edificio edificio = new Edificio();
			edificio.setIdEdificio(7);
			
			// Creazione oggetto dispositivo senza richiamare una ulteriore query
			DispositivoId id = new DispositivoId(3,
					edificio.getIdEdificio());
			Dispositivo dispositivo = new Dispositivo(id, edificio);

			// INSERT lettura dispositivo
			LetturaDispositivo lettDisp = new LetturaDispositivo(dispositivo, generateRandomDate());
			session.save(lettDisp);
			
	
			
			tx.commit();
		}
		 catch (Exception e) {
		     if (tx!=null) tx.rollback();
		     throw e;
		 }
		finally{
			session.close();
		}
	}
	
	public static Date generateRandomDate(){
		Date date;
		
		Random generator = new Random();
		int day = generator.nextInt(30) + 1;
		int month = generator.nextInt(12);
		int hours = generator.nextInt(24);
		int minutes = generator.nextInt(60);
		int seconds = generator.nextInt(60);
		
		date = new GregorianCalendar(2014, month, day).getTime();
		date.setMinutes(minutes);
		date.setSeconds(seconds);
		date.setHours(hours);
		
		return date;
	};
}