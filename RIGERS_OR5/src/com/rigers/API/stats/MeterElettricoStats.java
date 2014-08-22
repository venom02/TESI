package com.rigers.API.stats;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rigers.db.Edificio;
import com.rigers.db.MeterElettrico;
import com.rigers.persistence.HibernateUtil;

public class MeterElettricoStats extends Tools {

	private Edificio edificio;
	private MeterElettrico meterElettrico;
	private ArrayList<Integer> currentReadoutValueList = new ArrayList<Integer>();
	private ArrayList<Integer> periodicReadoutValueList = new ArrayList<Integer>();
	private List<MeterElettrico> list;

	/**
	 * Costruttore. ottiene parametro edificio
	 * 
	 * @param edificio
	 */
	public MeterElettricoStats(Edificio edificio) {
		this.edificio = edificio;
	}

	/**
	 * Query di ricerca letture comprese nelle date assegnate come parametri
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	private List<MeterElettrico> queryList(Date dateFrom, Date dateTo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			String queryStr = "SELECT m " + "FROM MeterElettrico as m "
					+ "WHERE m.idLettura IN (select l.idLettura "
					+ "from LetturaDispositivo as l "
					+ "WHERE l.dataLettura< :dateTo "
					+ "AND l.dataLettura>= :dateFrom "
					+ "AND l.dispositivo.edificio.idEdificio= :edificio)";
			Query query = session.createQuery(queryStr);
			query.setDate("dateTo", dateTo);
			query.setDate("dateFrom", dateFrom);
			query.setParameter("edificio", edificio.getIdEdificio());
			list = query.list();

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
			return list;
		}

	}

	/**
	 * Metodo di riempimento liste
	 * 
	 * @param element
	 */
	private void fillLists(MeterElettrico element) {
		
	}

	private void clearLists() {
	}

	private void checkLists() {
		
	}

	private void fillMeterAvg() {
		
	}

	private void fillMeterMax() {
		
	}

	private void fillMeterMin() {
		
	}

	/**
	 * genera una lista di oggetti MeterElettrico appartenenti solo al mese dato e
	 * all'edificio prescelto
	 * 
	 * @param month
	 * @return
	 */
	public List<MeterElettrico> getMonthList(int year, int month) {

		List<Date> dates = new Tools().monthDates(year, month);
		List<MeterElettrico> list = queryList(dates.get(0), dates.get(1));
		return list;
	}

	/**
	 * Ritorna lista oggetti MeterElettrico solo della settimana appartenente al
	 * giorno dato
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private List<MeterElettrico> getWeekList(int year, int month, int date) {

		List<Date> dates = new Tools().weekDates(year, month, date);
		List<MeterElettrico> list = queryList(dates.get(0), dates.get(1));
		return list;
	}

	/**
	 * Ritorna lista oggetti MeterElettrico appartenenti al giorno dato
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private List<MeterElettrico> getDayList(int year, int month, int date) {
		List<Date> dates = new Tools().dayDates(year, month, date);
		List<MeterElettrico> list = queryList(dates.get(0), dates.get(1));
		return list;
	}

	/**
	 * Media Mensile. ritorna oggetto meterElettrico contenenti i valori di media
	 * mensile
	 * 
	 * @param month
	 * @return
	 */
	public MeterElettrico monthAverage(int year, int month) {
		meterElettrico = new MeterElettrico();
		clearLists();

		for (MeterElettrico element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterAvg();

		return meterElettrico;
	}

	/**
	 * Massimo mensile. ritorna oggetto MeterElettrico contenente tutti i valori
	 * massimi del mese
	 * 
	 * @param month
	 * @return
	 */
	public MeterElettrico monthMax(int year, int month) {
		meterElettrico = new MeterElettrico();
		clearLists();

		for (MeterElettrico element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterMax();

		return meterElettrico;
	}

	/**
	 * Minimo mensile. ritorna oggetto MeterElettrico contenente tutti i valori
	 * minimi del mese
	 * 
	 * @param month
	 * @return
	 */
	public MeterElettrico monthMin(int year, int month) {
		meterElettrico = new MeterElettrico();
		clearLists();

		for (MeterElettrico element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterElettrico;
	}

	/**
	 * Minimo Settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterElettrico weekMin(int year, int month, int date) {
		meterElettrico = new MeterElettrico();
		clearLists();

		for (MeterElettrico element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterElettrico;
	}

	/**
	 * Massimo settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterElettrico weekMax(int year, int month, int date) {
		meterElettrico = new MeterElettrico();
		clearLists();

		for (MeterElettrico element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterElettrico;
	}

	/**
	 * Media settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterElettrico weekAverage(int year, int month, int date) {
		meterElettrico = new MeterElettrico();
		clearLists();

		for (MeterElettrico element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterAvg();

		return meterElettrico;
	}

	/**
	 * media giornaliera
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterElettrico dayAverage(int year, int month, int date) {
		meterElettrico = new MeterElettrico();
		clearLists();

		for (MeterElettrico element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterAvg();

		return meterElettrico;
	}

	/**
	 * Massimo giornaliero
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterElettrico dayMax(int year, int month, int date) {
		meterElettrico = new MeterElettrico();
		clearLists();

		for (MeterElettrico element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMax();

		return meterElettrico;
	}

	/**
	 * Minimo giornaliero
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterElettrico dayMin(int year, int month, int date) {
		meterElettrico = new MeterElettrico();
		clearLists();

		for (MeterElettrico element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterElettrico;
	}

	/**
	 * Ritorna array di stringhe contenenti i dati della lettura giornaliera
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public String[] dayReadings(int year, int month, int date) {
		List<MeterElettrico> list = getDayList(year, month, date);
		String[] dayCRVStrings = new String[list.size()];

		return dayCRVStrings;
	}

}
