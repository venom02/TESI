package com.rigers.API;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rigers.db.Edificio;
import com.rigers.db.LetturaDispositivo;
import com.rigers.db.MeterAcqua;
import com.rigers.persistence.HibernateUtil;

public class MeterAcquaStats extends Tools {

	private Edificio edificio;

	/**
	 * Costruttore. ottiene parametro edificio
	 * 
	 * @param edificio
	 */
	public MeterAcquaStats(Edificio edificio) {
		this.edificio = edificio;
	}

	/**
	 * genera una lista di oggetti MeterAcqua appartenenti solo al mese dato e
	 * all'edificio prescelto
	 * 
	 * @param month
	 * @return
	 */
	private List<MeterAcqua> getMonthList(int year, int month) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		Date dateTo = cal.getTime();
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		Date dateFrom = cal.getTime();

		String queryStr = "SELECT m " + "FROM MeterAcqua as m "
				+ "WHERE m.idLettura IN (select l.idLettura "
				+ "from LetturaDispositivo as l "
				+ "WHERE l.dataLettura< :dateTo "
				+ "AND l.dataLettura>= :dateFrom "
				+ "AND l.dispositivo.edificio.idEdificio= :edificio)";
		Query query = session.createQuery(queryStr);
		query.setParameter("dateTo", dateTo);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("edificio", edificio.getIdEdificio());
		List<MeterAcqua> list = query.list();

		session.getTransaction().commit();
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
		MeterAcqua meterAcqua = new MeterAcqua();

		ArrayList<Integer> currentReadoutValueList = new ArrayList<Integer>();
		ArrayList<Integer> periodicReadoutValueList = new ArrayList<Integer>();

		for (MeterAcqua element : getMonthList(year, month)) {
			currentReadoutValueList.add(element.getCurrentReadoutValue());
			periodicReadoutValueList.add(element.getPeriodicReadoutValue());
		}

		if (!currentReadoutValueList.isEmpty()) {
			meterAcqua.setCurrentReadoutValue(average(currentReadoutValueList));
		} else
			meterAcqua.setCurrentReadoutValue(0);
		if (!periodicReadoutValueList.isEmpty()) {
			meterAcqua
					.setPeriodicReadoutValue(average(periodicReadoutValueList));
		} else {
			meterAcqua.setPeriodicReadoutValue(0);
		}

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
		MeterAcqua meterAcqua = new MeterAcqua();

		ArrayList<Integer> currentReadoutValueList = new ArrayList<Integer>();
		ArrayList<Integer> periodicReadoutValueList = new ArrayList<Integer>();

		for (MeterAcqua element : getMonthList(year, month)) {
			currentReadoutValueList.add(element.getCurrentReadoutValue());
			periodicReadoutValueList.add(element.getPeriodicReadoutValue());
		}

		if (!currentReadoutValueList.isEmpty()) {
			meterAcqua.setCurrentReadoutValue(Collections
					.max(currentReadoutValueList));
		} else
			meterAcqua.setCurrentReadoutValue(0);
		if (!periodicReadoutValueList.isEmpty()) {
			meterAcqua.setPeriodicReadoutValue(Collections
					.max(periodicReadoutValueList));
		} else {
			meterAcqua.setPeriodicReadoutValue(0);
		}
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
		MeterAcqua meterAcqua = new MeterAcqua();

		ArrayList<Integer> currentReadoutValueList = new ArrayList<Integer>();
		ArrayList<Integer> periodicReadoutValueList = new ArrayList<Integer>();

		for (MeterAcqua element : getMonthList(year, month)) {
			currentReadoutValueList.add(element.getCurrentReadoutValue());
			periodicReadoutValueList.add(element.getPeriodicReadoutValue());
		}

		if (!currentReadoutValueList.isEmpty()) {
			meterAcqua.setCurrentReadoutValue(Collections
					.min(currentReadoutValueList));
		} else
			meterAcqua.setCurrentReadoutValue(0);
		if (!periodicReadoutValueList.isEmpty()) {
			meterAcqua.setPeriodicReadoutValue(Collections
					.min(periodicReadoutValueList));
		} else {
			meterAcqua.setPeriodicReadoutValue(0);
		}

		return meterAcqua;
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
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		// Get calendar set to given date and time
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);
		// Set the calendar to monday and sunday of the given week
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date dateFrom = cal.getTime();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date dateTo = cal.getTime();

		String queryStr = "SELECT m " + "FROM MeterAcqua as m "
				+ "WHERE m.idLettura IN (select l.idLettura "
				+ "from LetturaDispositivo as l "
				+ "WHERE l.dataLettura< :dateTo "
				+ "AND l.dataLettura>= :dateFrom "
				+ "AND l.dispositivo.edificio.idEdificio= :edificio)";
		Query query = session.createQuery(queryStr);
		query.setParameter("dateTo", dateTo);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("edificio", edificio.getIdEdificio());

		List<MeterAcqua> list = query.list();
		session.getTransaction().commit();
		return list;
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
		MeterAcqua meterAcqua = new MeterAcqua();

		ArrayList<Integer> currentReadoutValueList = new ArrayList<Integer>();
		ArrayList<Integer> periodicReadoutValueList = new ArrayList<Integer>();

		for (MeterAcqua element : getWeekList(year, month, date)) {
			currentReadoutValueList.add(element.getCurrentReadoutValue());
			periodicReadoutValueList.add(element.getPeriodicReadoutValue());
		}

		if (!currentReadoutValueList.isEmpty()) {
			meterAcqua.setCurrentReadoutValue(Collections
					.min(currentReadoutValueList));
		} else
			meterAcqua.setCurrentReadoutValue(0);
		if (!periodicReadoutValueList.isEmpty()) {
			meterAcqua.setPeriodicReadoutValue(Collections
					.min(periodicReadoutValueList));
		} else {
			meterAcqua.setPeriodicReadoutValue(0);
		}

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
		MeterAcqua meterAcqua = new MeterAcqua();

		ArrayList<Integer> currentReadoutValueList = new ArrayList<Integer>();
		ArrayList<Integer> periodicReadoutValueList = new ArrayList<Integer>();

		for (MeterAcqua element : getWeekList(year, month, date)) {
			currentReadoutValueList.add(element.getCurrentReadoutValue());
			periodicReadoutValueList.add(element.getPeriodicReadoutValue());
		}

		if (!currentReadoutValueList.isEmpty()) {
			meterAcqua.setCurrentReadoutValue(Collections
					.max(currentReadoutValueList));
		} else
			meterAcqua.setCurrentReadoutValue(0);
		if (!periodicReadoutValueList.isEmpty()) {
			meterAcqua.setPeriodicReadoutValue(Collections
					.max(periodicReadoutValueList));
		} else {
			meterAcqua.setPeriodicReadoutValue(0);
		}

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
		MeterAcqua meterAcqua = new MeterAcqua();

		ArrayList<Integer> currentReadoutValueList = new ArrayList<Integer>();
		ArrayList<Integer> periodicReadoutValueList = new ArrayList<Integer>();

		for (MeterAcqua element : getWeekList(year, month, date)) {
			currentReadoutValueList.add(element.getCurrentReadoutValue());
			periodicReadoutValueList.add(element.getPeriodicReadoutValue());
		}

		if (!currentReadoutValueList.isEmpty()) {
			meterAcqua.setCurrentReadoutValue(average(currentReadoutValueList));
		} else
			meterAcqua.setCurrentReadoutValue(0);
		if (!periodicReadoutValueList.isEmpty()) {
			meterAcqua
					.setPeriodicReadoutValue(average(periodicReadoutValueList));
		} else {
			meterAcqua.setPeriodicReadoutValue(0);
		}

		return meterAcqua;
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
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date, 0, 0, 0);
		Date dateFrom = cal.getTime();
		cal.add(Calendar.DATE, 1);
		Date dateTo = cal.getTime();

		String queryStr = "SELECT m " + "FROM MeterAcqua as m "
				+ "WHERE m.idLettura IN (select l.idLettura "
				+ "from LetturaDispositivo as l "
				+ "WHERE l.dataLettura< :dateTo "
				+ "AND l.dataLettura>= :dateFrom "
				+ "AND l.dispositivo.edificio.idEdificio= :edificio)";
		Query query = session.createQuery(queryStr);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateTo", dateTo);
		query.setParameter("edificio", edificio.getIdEdificio());

		List<MeterAcqua> list = query.list();
		session.getTransaction().commit();
		return list;
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
		MeterAcqua meterAcqua = new MeterAcqua();

		ArrayList<Integer> currentReadoutValueList = new ArrayList<Integer>();
		ArrayList<Integer> periodicReadoutValueList = new ArrayList<Integer>();

		for (MeterAcqua element : getDayList(year, month, date)) {
			currentReadoutValueList.add(element.getCurrentReadoutValue());
			periodicReadoutValueList.add(element.getPeriodicReadoutValue());
		}

		if (!currentReadoutValueList.isEmpty()) {
			meterAcqua.setCurrentReadoutValue(average(currentReadoutValueList));
		} else
			meterAcqua.setCurrentReadoutValue(0);
		if (!periodicReadoutValueList.isEmpty()) {
			meterAcqua
					.setPeriodicReadoutValue(average(periodicReadoutValueList));
		} else {
			meterAcqua.setPeriodicReadoutValue(0);
		}

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
		MeterAcqua meterAcqua = new MeterAcqua();

		ArrayList<Integer> currentReadoutValueList = new ArrayList<Integer>();
		ArrayList<Integer> periodicReadoutValueList = new ArrayList<Integer>();

		for (MeterAcqua element : getDayList(year, month, date)) {
			currentReadoutValueList.add(element.getCurrentReadoutValue());
			periodicReadoutValueList.add(element.getPeriodicReadoutValue());
		}

		if (!currentReadoutValueList.isEmpty()) {
			meterAcqua.setCurrentReadoutValue(Collections
					.max(currentReadoutValueList));
		} else
			meterAcqua.setCurrentReadoutValue(0);
		if (!periodicReadoutValueList.isEmpty()) {
			meterAcqua.setPeriodicReadoutValue(Collections
					.max(periodicReadoutValueList));
		} else {
			meterAcqua.setPeriodicReadoutValue(0);
		}

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
		MeterAcqua meterAcqua = new MeterAcqua();

		ArrayList<Integer> currentReadoutValueList = new ArrayList<Integer>();
		ArrayList<Integer> periodicReadoutValueList = new ArrayList<Integer>();

		for (MeterAcqua element : getDayList(year, month, date)) {
			currentReadoutValueList.add(element.getCurrentReadoutValue());
			periodicReadoutValueList.add(element.getPeriodicReadoutValue());
		}

		if (!currentReadoutValueList.isEmpty()) {
			meterAcqua.setCurrentReadoutValue(Collections
					.min(currentReadoutValueList));
		} else
			meterAcqua.setCurrentReadoutValue(0);
		if (!periodicReadoutValueList.isEmpty()) {
			meterAcqua.setPeriodicReadoutValue(Collections
					.min(periodicReadoutValueList));
		} else {
			meterAcqua.setPeriodicReadoutValue(0);
		}

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

		for (int i = 0; i < list.size(); i++) {
			dayCRVStrings[i] = "Current Reading Value: " + list.get(i).getCurrentReadoutValue()
					+ "\t Periodic Reading Value: " + list.get(i).getPeriodicReadoutValue()
					+ "\t Periodic Reading Date: " + list.get(i).getPeriodicReadingDate();
		}

		return dayCRVStrings;
	}

}
