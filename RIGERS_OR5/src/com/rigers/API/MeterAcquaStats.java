package com.rigers.API;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rigers.db.Edificio;
import com.rigers.db.MeterAcqua;
import com.rigers.persistence.HibernateUtil;

public class MeterAcquaStats extends Tools {

	private Edificio edificio;
	private MeterAcqua meterAcqua;
	private ArrayList<Integer> currentReadoutValueList = new ArrayList<Integer>();
	private ArrayList<Integer> periodicReadoutValueList = new ArrayList<Integer>();

	/**
	 * Costruttore. ottiene parametro edificio
	 * 
	 * @param edificio
	 */
	public MeterAcquaStats(Edificio edificio) {
		this.edificio = edificio;
	}

	/**
	 * Query di ricerca letture comprese nelle date assegnate come parametri
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	private List<MeterAcqua> queryList(Date dateFrom, Date dateTo) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

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

		session.getTransaction().commit();
		return list;
	}

	
	/**
	 * Metodo di riempimento liste
	 * 
	 * @param element
	 */
	private void fillLists(MeterAcqua element) {
		currentReadoutValueList.add(element.getCurrentReadoutValue());
		periodicReadoutValueList.add(element.getPeriodicReadoutValue());
	}

	private void clearLists() {
		currentReadoutValueList.clear();
		periodicReadoutValueList.clear();
	}

	private void checkLists() {
		if (currentReadoutValueList.isEmpty()) {
			currentReadoutValueList.add(0);
		}
		if (periodicReadoutValueList.isEmpty()) {
			periodicReadoutValueList.add(0);
		}
	}

	private void fillMeterAvg() {
		meterAcqua.setCurrentReadoutValue(average(currentReadoutValueList));
		meterAcqua.setPeriodicReadoutValue(average(periodicReadoutValueList));
	}

	private void fillMeterMax() {
		checkLists();
		meterAcqua.setCurrentReadoutValue(Collections.max(currentReadoutValueList));
		meterAcqua.setPeriodicReadoutValue(Collections.max(periodicReadoutValueList));
	}

	private void fillMeterMin() {
		checkLists();
		meterAcqua.setCurrentReadoutValue(Collections.min(currentReadoutValueList));
		meterAcqua.setPeriodicReadoutValue(Collections.min(periodicReadoutValueList));
	}
	
	/**
	 * genera una lista di oggetti MeterAcqua appartenenti solo al mese dato e
	 * all'edificio prescelto
	 * 
	 * @param month
	 * @return
	 */
	private List<MeterAcqua> getMonthList(int year, int month) {	
		List<Date> dates = new Tools().monthDates(year, month);
		List<MeterAcqua> list = queryList(dates.get(0), dates.get(1));
		return list;
	}

	/**
	 * Ritorna lista oggetti MeterAcqua solo della settimana appartenente al
	 * giorno dato
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private List<MeterAcqua> getWeekList(int year, int month, int date) {	
		List<Date> dates = new Tools().weekDates(year, month, date);
		List<MeterAcqua> list = queryList(dates.get(0), dates.get(1));
		return list;
	}

	/**
	 * Ritorna lista oggetti MeterAcqua appartenenti al giorno dato
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private List<MeterAcqua> getDayList(int year, int month, int date) {
		List<Date> dates = new Tools().dayDates(year, month, date);
		List<MeterAcqua> list = queryList(dates.get(0), dates.get(1));
		return list;
	}

	/**
	 * Media Mensile. ritorna oggetto meterAcqua contenenti i valori di media
	 * mensile
	 * 
	 * @param month
	 * @return
	 */
	public MeterAcqua monthAverage(int year, int month) {
		meterAcqua = new MeterAcqua();
		clearLists();

		for (MeterAcqua element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterAvg();

		return meterAcqua;
	}

	/**
	 * Massimo mensile. ritorna oggetto MeterAcqua contenente tutti i valori
	 * massimi del mese
	 * 
	 * @param month
	 * @return
	 */
	public MeterAcqua monthMax(int year, int month) {
		meterAcqua = new MeterAcqua();
		clearLists();

		for (MeterAcqua element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterMax();

		return meterAcqua;
	}

	/**
	 * Minimo mensile. ritorna oggetto MeterAcqua contenente tutti i valori
	 * minimi del mese
	 * 
	 * @param month
	 * @return
	 */
	public MeterAcqua monthMin(int year, int month) {
		meterAcqua = new MeterAcqua();
		clearLists();

		for (MeterAcqua element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterAcqua;
	}

	/**
	 * Minimo Settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterAcqua weekMin(int year, int month, int date) {
		meterAcqua = new MeterAcqua();
		clearLists();

		for (MeterAcqua element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterAcqua;
	}

	/**
	 * Massimo settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterAcqua weekMax(int year, int month, int date) {
		meterAcqua = new MeterAcqua();
		clearLists();

		for (MeterAcqua element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterAcqua;
	}

	/**
	 * Media settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterAcqua weekAverage(int year, int month, int date) {
		meterAcqua = new MeterAcqua();
		clearLists();

		for (MeterAcqua element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterAvg();

		return meterAcqua;
	}

	/**
	 * media giornaliera
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterAcqua dayAverage(int year, int month, int date) {
		meterAcqua = new MeterAcqua();
		clearLists();

		for (MeterAcqua element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterAvg();

		return meterAcqua;
	}

	/**
	 * Massimo giornaliero
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterAcqua dayMax(int year, int month, int date) {
		meterAcqua = new MeterAcqua();
		clearLists();

		for (MeterAcqua element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMax();

		return meterAcqua;
	}

	/**
	 * Minimo giornaliero
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterAcqua dayMin(int year, int month, int date) {
		meterAcqua = new MeterAcqua();
		clearLists();

		for (MeterAcqua element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterAcqua;
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
		List<MeterAcqua> list = getDayList(year, month, date);
		String[] dayCRVStrings = new String[list.size()];

		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setMinimumIntegerDigits(2);

		for (int i = 0; i < list.size(); i++) {
			String CRV = myFormat.format(list.get(i).getCurrentReadoutValue());
			String PRV = myFormat.format(list.get(i).getPeriodicReadoutValue());
			String PRD = list.get(i).getPeriodicReadingDate().toString();

			dayCRVStrings[i] = "Current Reading Value: " + CRV
					+ "\t Periodic Reading Value: " + PRV
					+ "\t Periodic Reading Date: " + PRD;
		}

		return dayCRVStrings;
	}

}
