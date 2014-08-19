package com.rigers.API;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rigers.db.Edificio;
import com.rigers.db.MeterRipartitoreCalore;
import com.rigers.persistence.HibernateUtil;

public class MeterRipartitoreCaloreStats extends Tools {

	private Edificio edificio;
	private ArrayList<Integer> unitaConsumoList = new ArrayList<Integer>();
	private MeterRipartitoreCalore meterRipCal;

	/**
	 * Costruttore. ottiene parametro edificio
	 * 
	 * @param edificio
	 */
	public MeterRipartitoreCaloreStats(Edificio edificio) {
		this.edificio = edificio;
	}

	/**
	 * genera la lista di oggetti conenuti nelle due date indicate come
	 * parametri
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	private List<MeterRipartitoreCalore> queryList(Date dateFrom, Date dateTo) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String queryStr = "SELECT m " + "FROM MeterRipartitoreCalore as m "
				+ "WHERE m.idLettura IN (select l.idLettura "
				+ "from LetturaDispositivo as l "
				+ "WHERE l.dataLettura< :dateTo "
				+ "AND l.dataLettura>= :dateFrom "
				+ "AND l.dispositivo.edificio.idEdificio= :edificio)";
		Query query = session.createQuery(queryStr);
		query.setDate("dateTo", dateTo);
		query.setDate("dateFrom", dateFrom);
		query.setParameter("edificio", edificio.getIdEdificio());

		List<MeterRipartitoreCalore> list = query.list();
		session.getTransaction().commit();
		return list;
	}

	private void fillLists(MeterRipartitoreCalore element) {
		unitaConsumoList.add(element.getUnitaConsumo());
	}

	private void clearLists() {
		unitaConsumoList.clear();
	}

	private void checkLists() {
		if (unitaConsumoList.isEmpty()) {
			unitaConsumoList.add(0);
		}
	}

	private void fillMeterAvg() {
		meterRipCal.setUnitaConsumo(average(unitaConsumoList));
	}

	private void fillMeterMax() {
		checkLists();
		meterRipCal.setUnitaConsumo(Collections.max(unitaConsumoList));
	}

	private void fillMeterMin() {
		checkLists();
		meterRipCal.setUnitaConsumo(Collections.min(unitaConsumoList));
	}
	
	/**
	 * genera una lista di oggetti MeterRipartitoreCalore appartenenti solo al
	 * mese dato e all'edificio prescelto
	 * 
	 * @param month
	 * @return
	 */
	private List<MeterRipartitoreCalore> getMonthList(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		Date dateTo = cal.getTime();
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		Date dateFrom = cal.getTime();

		List<MeterRipartitoreCalore> list = queryList(dateFrom, dateTo);

		return list;
	}

	/**
	 * Ritorna lista oggetti MeterRipartitoreCalore appartenenti al giorno dato
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private List<MeterRipartitoreCalore> getDayList(int year, int month,
			int date) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date, 0, 0, 0);
		Date dateFrom = cal.getTime();
		cal.add(Calendar.DATE, 1);
		Date dateTo = cal.getTime();

		List<MeterRipartitoreCalore> list = queryList(dateFrom, dateTo);
		return list;
	}

	/**
	 * Ritorna lista oggetti MeterRipartitoreCalore solo della settimana
	 * appartenente al giorno dato
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private List<MeterRipartitoreCalore> getWeekList(int year, int month,
			int date) {
		// Get calendar set to given date and time
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);
		// Set the calendar to monday and sunday of the given week
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date dateFrom = cal.getTime();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date dateTo = cal.getTime();

		List<MeterRipartitoreCalore> list = queryList(dateFrom, dateTo);
		return list;
	}

	/**
	 * Media Mensile. ritorna oggetto MeterRipartitoreCalore contenenti i valori
	 * di media mensile
	 * 
	 * @param month
	 * @return
	 */
	public MeterRipartitoreCalore monthAverage(int year, int month) {
		meterRipCal = new MeterRipartitoreCalore();
		clearLists();
		
		for (MeterRipartitoreCalore element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterAvg();
		
		return meterRipCal;
	}

	/**
	 * Massimo mensile. ritorna oggetto MeterRipartitoreCalore contenente tutti
	 * i valori massimi del mese
	 * 
	 * @param month
	 * @return
	 */
	public MeterRipartitoreCalore monthMax(int year, int month) {
		meterRipCal = new MeterRipartitoreCalore();
		clearLists();
		
		for (MeterRipartitoreCalore element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterMax();
		
		return meterRipCal;
	}

	/**
	 * Minimo mensile. ritorna oggetto MeterRipartitoreCalore contenente tutti i
	 * valori minimi del mese
	 * 
	 * @param month
	 * @return
	 */
	public MeterRipartitoreCalore monthMin(int year, int month) {
		meterRipCal = new MeterRipartitoreCalore();
		clearLists();
		
		for (MeterRipartitoreCalore element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterMin();
		
		return meterRipCal;
	}

	/**
	 * Minimo Settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterRipartitoreCalore weekMin(int year, int month, int date) {
		meterRipCal = new MeterRipartitoreCalore();
		clearLists();
		
		for (MeterRipartitoreCalore element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();
		
		return meterRipCal;
	}

	/**
	 * Massimo settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterRipartitoreCalore weekMax(int year, int month, int date) {
		meterRipCal = new MeterRipartitoreCalore();
		clearLists();
		
		for (MeterRipartitoreCalore element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMax();
		
		return meterRipCal;
	}

	/**
	 * Media settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterRipartitoreCalore weekAverage(int year, int month, int date) {
		meterRipCal = new MeterRipartitoreCalore();
		clearLists();
		
		for (MeterRipartitoreCalore element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterAvg();
		
		return meterRipCal;
	}

	/**
	 * media giornaliera
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterRipartitoreCalore dayAverage(int year, int month, int date) {
		meterRipCal = new MeterRipartitoreCalore();
		clearLists();
		
		for (MeterRipartitoreCalore element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterAvg();
		
		return meterRipCal;
	}

	/**
	 * Massimo giornaliero
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterRipartitoreCalore dayMax(int year, int month, int date) {
		meterRipCal = new MeterRipartitoreCalore();
		clearLists();
		
		for (MeterRipartitoreCalore element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMax();
		
		return meterRipCal;
	}

	/**
	 * Minimo giornaliero
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterRipartitoreCalore dayMin(int year, int month, int date) {
		meterRipCal = new MeterRipartitoreCalore();
		clearLists();
		
		for (MeterRipartitoreCalore element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();
		
		return meterRipCal;
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
		List<MeterRipartitoreCalore> list = getDayList(year, month, date);
		String[] dayCRVStrings = new String[list.size()];

		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setMinimumIntegerDigits(3);

		for (int i = 0; i < list.size(); i++) {
			String UC = list.get(i).getUnitaConsumo().toString();
			dayCRVStrings[i] = "Unità Consumo: " + UC;
		}

		return dayCRVStrings;
	}

}
