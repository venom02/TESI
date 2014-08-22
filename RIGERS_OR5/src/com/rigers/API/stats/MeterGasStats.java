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
import com.rigers.db.MeterGas;
import com.rigers.persistence.HibernateUtil;

public class MeterGasStats extends Tools {

	private Edificio edificio;
	private MeterGas meterGas;
	private ArrayList<Integer> currentReadoutValueList = new ArrayList<Integer>();
	private ArrayList<Integer> periodicReadoutValueList = new ArrayList<Integer>();
	private List<MeterGas> list;

	/**
	 * Costruttore. ottiene parametro edificio
	 * 
	 * @param edificio
	 */
	public MeterGasStats(Edificio edificio) {
		this.edificio = edificio;
	}

	/**
	 * Query di ricerca letture comprese nelle date assegnate come parametri
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	private List<MeterGas> queryList(Date dateFrom, Date dateTo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			String queryStr = "SELECT m " + "FROM MeterGas as m "
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
	private void fillLists(MeterGas element) {
		
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
	 * genera una lista di oggetti MeterGas appartenenti solo al mese dato e
	 * all'edificio prescelto
	 * 
	 * @param month
	 * @return
	 */
	public List<MeterGas> getMonthList(int year, int month) {

		List<Date> dates = new Tools().monthDates(year, month);
		List<MeterGas> list = queryList(dates.get(0), dates.get(1));
		return list;
	}

	/**
	 * Ritorna lista oggetti MeterGas solo della settimana appartenente al
	 * giorno dato
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private List<MeterGas> getWeekList(int year, int month, int date) {

		List<Date> dates = new Tools().weekDates(year, month, date);
		List<MeterGas> list = queryList(dates.get(0), dates.get(1));
		return list;
	}

	/**
	 * Ritorna lista oggetti MeterGas appartenenti al giorno dato
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private List<MeterGas> getDayList(int year, int month, int date) {
		List<Date> dates = new Tools().dayDates(year, month, date);
		List<MeterGas> list = queryList(dates.get(0), dates.get(1));
		return list;
	}

	/**
	 * Media Mensile. ritorna oggetto meterGas contenenti i valori di media
	 * mensile
	 * 
	 * @param month
	 * @return
	 */
	public MeterGas monthAverage(int year, int month) {
		meterGas = new MeterGas();
		clearLists();

		for (MeterGas element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterAvg();

		return meterGas;
	}

	/**
	 * Massimo mensile. ritorna oggetto MeterGas contenente tutti i valori
	 * massimi del mese
	 * 
	 * @param month
	 * @return
	 */
	public MeterGas monthMax(int year, int month) {
		meterGas = new MeterGas();
		clearLists();

		for (MeterGas element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterMax();

		return meterGas;
	}

	/**
	 * Minimo mensile. ritorna oggetto MeterGas contenente tutti i valori
	 * minimi del mese
	 * 
	 * @param month
	 * @return
	 */
	public MeterGas monthMin(int year, int month) {
		meterGas = new MeterGas();
		clearLists();

		for (MeterGas element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterGas;
	}

	/**
	 * Minimo Settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterGas weekMin(int year, int month, int date) {
		meterGas = new MeterGas();
		clearLists();

		for (MeterGas element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterGas;
	}

	/**
	 * Massimo settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterGas weekMax(int year, int month, int date) {
		meterGas = new MeterGas();
		clearLists();

		for (MeterGas element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterGas;
	}

	/**
	 * Media settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterGas weekAverage(int year, int month, int date) {
		meterGas = new MeterGas();
		clearLists();

		for (MeterGas element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterAvg();

		return meterGas;
	}

	/**
	 * media giornaliera
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterGas dayAverage(int year, int month, int date) {
		meterGas = new MeterGas();
		clearLists();

		for (MeterGas element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterAvg();

		return meterGas;
	}

	/**
	 * Massimo giornaliero
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterGas dayMax(int year, int month, int date) {
		meterGas = new MeterGas();
		clearLists();

		for (MeterGas element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMax();

		return meterGas;
	}

	/**
	 * Minimo giornaliero
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterGas dayMin(int year, int month, int date) {
		meterGas = new MeterGas();
		clearLists();

		for (MeterGas element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterGas;
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
		List<MeterGas> list = getDayList(year, month, date);
		String[] dayCRVStrings = new String[list.size()];

		return dayCRVStrings;
	}

}
