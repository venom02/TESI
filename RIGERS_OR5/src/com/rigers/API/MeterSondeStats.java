package com.rigers.API;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ibm.icu.text.NumberFormat;
import com.rigers.db.Edificio;
import com.rigers.db.MeterSonde;
import com.rigers.persistence.HibernateUtil;

public class MeterSondeStats extends Tools {

	private Edificio edificio;

	/**
	 * Costruttore. ottiene parametro edificio
	 * 
	 * @param edificio
	 */
	public MeterSondeStats(Edificio edificio) {
		this.edificio = edificio;
	}

	/**
	 * query di ricerca letture comprese nelle date assegnate come parametri
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	private List<MeterSonde> queryList(Date dateFrom, Date dateTo) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String queryStr = "SELECT m " + "FROM MeterSonde as m "
				+ "WHERE m.idLettura IN (select l.idLettura "
				+ "from LetturaDispositivo as l "
				+ "WHERE l.dataLettura< :dateTo "
				+ "AND l.dataLettura>= :dateFrom "
				+ "AND l.dispositivo.edificio.idEdificio= :edificio)";
		Query query = session.createQuery(queryStr);
		query.setParameter("dateTo", dateTo);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("edificio", edificio.getIdEdificio());
		List<MeterSonde> list = query.list();

		session.getTransaction().commit();
		return list;
	}

	/**
	 * genera una lista di oggetti MeterSonde appartenenti solo al mese dato e
	 * all'edificio prescelto
	 * 
	 * @param month
	 * @return
	 */
	private List<MeterSonde> getMonthList(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		Date dateTo = cal.getTime();
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		Date dateFrom = cal.getTime();

		List<MeterSonde> list = queryList(dateFrom, dateTo);

		return list;
	}

	/**
	 * Media Mensile. ritorna oggetto meterSonde contenenti i valori di media
	 * mensile
	 * 
	 * @param month
	 * @return
	 */
	public MeterSonde monthAverage(int year, int month) {
		MeterSonde meterSonde = new MeterSonde();

		ArrayList<Integer> luminositaList = new ArrayList<Integer>();
		ArrayList<Integer> sismografoList = new ArrayList<Integer>();

		for (MeterSonde element : getMonthList(year, month)) {
			luminositaList.add(element.getLuminosita());
			sismografoList.add(element.getSismografo());
		}

		if (!luminositaList.isEmpty()) {
			meterSonde.setLuminosita(average(luminositaList));
		} else
			meterSonde.setLuminosita(0);
		if (!sismografoList.isEmpty()) {
			meterSonde
					.setSismografo(average(sismografoList));
		} else {
			meterSonde.setSismografo(0);
		}

		return meterSonde;
	}

	/**
	 * Massimo mensile. ritorna oggetto MeterSonde contenente tutti i valori
	 * massimi del mese
	 * 
	 * @param month
	 * @return
	 */
	public MeterSonde monthMax(int year, int month) {
		MeterSonde meterSonde = new MeterSonde();

		ArrayList<Integer> luminositaList = new ArrayList<Integer>();
		ArrayList<Integer> sismografoList = new ArrayList<Integer>();

		for (MeterSonde element : getMonthList(year, month)) {
			luminositaList.add(element.getLuminosita());
			sismografoList.add(element.getSismografo());
		}

		if (!luminositaList.isEmpty()) {
			meterSonde.setLuminosita(Collections
					.max(luminositaList));
		} else
			meterSonde.setLuminosita(0);
		if (!sismografoList.isEmpty()) {
			meterSonde.setSismografo(Collections
					.max(sismografoList));
		} else {
			meterSonde.setSismografo(0);
		}
		return meterSonde;
	}

	/**
	 * Minimo mensile. ritorna oggetto MeterSonde contenente tutti i valori
	 * minimi del mese
	 * 
	 * @param month
	 * @return
	 */
	public MeterSonde monthMin(int year, int month) {
		MeterSonde meterSonde = new MeterSonde();

		ArrayList<Integer> luminositaList = new ArrayList<Integer>();
		ArrayList<Integer> sismografoList = new ArrayList<Integer>();

		for (MeterSonde element : getMonthList(year, month)) {
			luminositaList.add(element.getLuminosita());
			sismografoList.add(element.getSismografo());
		}

		if (!luminositaList.isEmpty()) {
			meterSonde.setLuminosita(Collections
					.min(luminositaList));
		} else
			meterSonde.setLuminosita(0);
		if (!sismografoList.isEmpty()) {
			meterSonde.setSismografo(Collections
					.min(sismografoList));
		} else {
			meterSonde.setSismografo(0);
		}

		return meterSonde;
	}

	/**
	 * Ritorna lista oggetti MeterSonde solo della settimana appartenente al
	 * giorno dato
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private List<MeterSonde> getWeekList(int year, int month, int date) {
		// Get calendar set to given date and time
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);
		// Set the calendar to monday and sunday of the given week
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date dateFrom = cal.getTime();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date dateTo = cal.getTime();

		List<MeterSonde> list = queryList(dateFrom, dateTo);
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
	public MeterSonde weekMin(int year, int month, int date) {
		MeterSonde meterSonde = new MeterSonde();

		ArrayList<Integer> luminositaList = new ArrayList<Integer>();
		ArrayList<Integer> sismografoList = new ArrayList<Integer>();

		for (MeterSonde element : getWeekList(year, month, date)) {
			luminositaList.add(element.getLuminosita());
			sismografoList.add(element.getSismografo());
		}

		if (!luminositaList.isEmpty()) {
			meterSonde.setLuminosita(Collections
					.min(luminositaList));
		} else
			meterSonde.setLuminosita(0);
		if (!sismografoList.isEmpty()) {
			meterSonde.setSismografo(Collections
					.min(sismografoList));
		} else {
			meterSonde.setSismografo(0);
		}

		return meterSonde;
	}

	/**
	 * Massimo settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterSonde weekMax(int year, int month, int date) {
		MeterSonde meterSonde = new MeterSonde();

		ArrayList<Integer> luminositaList = new ArrayList<Integer>();
		ArrayList<Integer> sismografoList = new ArrayList<Integer>();

		for (MeterSonde element : getWeekList(year, month, date)) {
			luminositaList.add(element.getLuminosita());
			sismografoList.add(element.getSismografo());
		}

		if (!luminositaList.isEmpty()) {
			meterSonde.setLuminosita(Collections
					.max(luminositaList));
		} else
			meterSonde.setLuminosita(0);
		if (!sismografoList.isEmpty()) {
			meterSonde.setSismografo(Collections
					.max(sismografoList));
		} else {
			meterSonde.setSismografo(0);
		}

		return meterSonde;
	}

	/**
	 * Media settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterSonde weekAverage(int year, int month, int date) {
		MeterSonde meterSonde = new MeterSonde();

		ArrayList<Integer> luminositaList = new ArrayList<Integer>();
		ArrayList<Integer> sismografoList = new ArrayList<Integer>();

		for (MeterSonde element : getWeekList(year, month, date)) {
			luminositaList.add(element.getLuminosita());
			sismografoList.add(element.getSismografo());
		}

		if (!luminositaList.isEmpty()) {
			meterSonde.setLuminosita(average(luminositaList));
		} else
			meterSonde.setLuminosita(0);
		if (!sismografoList.isEmpty()) {
			meterSonde
					.setSismografo(average(sismografoList));
		} else {
			meterSonde.setSismografo(0);
		}

		return meterSonde;
	}

	/**
	 * Ritorna lista oggetti MeterSonde appartenenti al giorno dato
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private List<MeterSonde> getDayList(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date, 0, 0, 0);
		Date dateFrom = cal.getTime();
		cal.add(Calendar.DATE, 1);
		Date dateTo = cal.getTime();

		List<MeterSonde> list = queryList(dateFrom, dateTo);
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
	public MeterSonde dayAverage(int year, int month, int date) {
		MeterSonde meterSonde = new MeterSonde();

		ArrayList<Integer> luminositaList = new ArrayList<Integer>();
		ArrayList<Integer> sismografoList = new ArrayList<Integer>();

		for (MeterSonde element : getDayList(year, month, date)) {
			luminositaList.add(element.getLuminosita());
			sismografoList.add(element.getSismografo());
		}

		if (!luminositaList.isEmpty()) {
			meterSonde.setLuminosita(average(luminositaList));
		} else
			meterSonde.setLuminosita(0);
		if (!sismografoList.isEmpty()) {
			meterSonde
					.setSismografo(average(sismografoList));
		} else {
			meterSonde.setSismografo(0);
		}

		return meterSonde;
	}

	/**
	 * Massimo giornaliero
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterSonde dayMax(int year, int month, int date) {
		MeterSonde meterSonde = new MeterSonde();

		ArrayList<Integer> luminositaList = new ArrayList<Integer>();
		ArrayList<Integer> sismografoList = new ArrayList<Integer>();

		for (MeterSonde element : getDayList(year, month, date)) {
			luminositaList.add(element.getLuminosita());
			sismografoList.add(element.getSismografo());
		}

		if (!luminositaList.isEmpty()) {
			meterSonde.setLuminosita(Collections
					.max(luminositaList));
		} else
			meterSonde.setLuminosita(0);
		if (!sismografoList.isEmpty()) {
			meterSonde.setSismografo(Collections
					.max(sismografoList));
		} else {
			meterSonde.setSismografo(0);
		}

		return meterSonde;
	}

	/**
	 * Minimo giornaliero
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterSonde dayMin(int year, int month, int date) {
		MeterSonde meterSonde = new MeterSonde();

		ArrayList<Integer> luminositaList = new ArrayList<Integer>();
		ArrayList<Integer> sismografoList = new ArrayList<Integer>();

		for (MeterSonde element : getDayList(year, month, date)) {
			luminositaList.add(element.getLuminosita());
			sismografoList.add(element.getSismografo());
		}

		if (!luminositaList.isEmpty()) {
			meterSonde.setLuminosita(Collections
					.min(luminositaList));
		} else
			meterSonde.setLuminosita(0);
		if (!sismografoList.isEmpty()) {
			meterSonde.setSismografo(Collections
					.min(sismografoList));
		} else {
			meterSonde.setSismografo(0);
		}

		return meterSonde;
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
		List<MeterSonde> list = getDayList(year, month, date);
		String[] dayCRVStrings = new String[list.size()];

		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setMinimumIntegerDigits(2);

		for (int i = 0; i < list.size(); i++) {
			String LUM = myFormat.format(list.get(i).getLuminosita());
			String SIS = myFormat.format(list.get(i).getSismografo());

			dayCRVStrings[i] = "Luminosita: " + LUM
					+ "\t Sismografo: " + SIS;
		}

		return dayCRVStrings;
	}

}
