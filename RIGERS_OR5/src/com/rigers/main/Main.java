package com.rigers.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rigers.API.DbManager;
import com.rigers.GUI.GUI;
import com.rigers.GUI.StatsGUI;
import com.rigers.db.Dispositivo;
import com.rigers.db.DispositivoId;
import com.rigers.db.Edificio;
import com.rigers.db.LetturaDispositivo;
import com.rigers.db.MeterAcqua;
import com.rigers.persistence.HibernateUtil;

public class Main {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// GUI.main(null);
		 StatsGUI.main(null);
		// fillMonth(7);
		// fillDb();
//		testGenerateLet(2014, 1);
		System.exit(0);
	}

	public static void fillMonth(int id) {
		Edificio edificio = new Edificio();
		edificio.setIdEdificio(id);
		DbManager db = new DbManager(edificio);
		db.fillMonth(1);
	};

	public static void fillDb() {
		DbManager db = new DbManager();
		db.fillDb();
	}

	public static void testGenerateLet(int year, int month) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Edificio edificio = new Edificio();
			edificio.setIdEdificio(7);

			Calendar cal = new GregorianCalendar(year, month, 1).getInstance();
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
			java.util.Date utilDate = cal.getTime();
			java.sql.Date dateTo = new java.sql.Date(utilDate.getTime());
			cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
			utilDate = cal.getTime();
			java.sql.Date dateFrom = new java.sql.Date(utilDate.getTime());

			System.out.println(dateFrom + " - " + dateTo);
			// CODE

			String queryStr = "SELECT m " + "FROM MeterAcqua as m "
					+ "WHERE m.idLettura IN (select l.idLettura "
					+ "from LetturaDispositivo as l "
					+ "WHERE l.dataLettura< :dateTo "
					 + "AND l.dataLettura>= :dateFrom "
					+ "AND l.dispositivo.edificio.idEdificio= :edificio)";
			Query query = session.createQuery(queryStr);
			query.setDate("dateTo", dateTo);
			 query.setDate("dateFrom", dateFrom);
			query.setParameter("edificio", edificio.getIdEdificio());
			List<MeterAcqua> list = query.list();

			if (!list.isEmpty()) {
				for (MeterAcqua E : list) {
					System.out.println(E.getIdLettura());
				}
			} else {
				System.out.println("empty set");
			}

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public static Date generateRandomDate() {
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