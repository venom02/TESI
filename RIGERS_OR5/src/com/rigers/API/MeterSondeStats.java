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
	private ArrayList<Integer> luminositaList = new ArrayList<Integer>();
	private ArrayList<Integer> sismografoList = new ArrayList<Integer>();
	private ArrayList<Integer> tempEstList = new ArrayList<Integer>();
	private ArrayList<Integer> tempIntList = new ArrayList<Integer>();
	private ArrayList<Integer> tempDayList = new ArrayList<Integer>();
	private ArrayList<Integer> solarimetroList = new ArrayList<Integer>();
	private MeterSonde meterSonde;

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
	 * 
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
	 * Metodo di riempimento liste
	 * 
	 * @param element
	 */
	private void fillLists(MeterSonde element) {
		luminositaList.add(element.getLuminosita());
		sismografoList.add(element.getSismografo());
		tempEstList.add(element.getTempEsterna());
		tempIntList.add(element.getTempLocali());
		tempDayList.add(element.getTempGiorno());
		solarimetroList.add(element.getSolarimetro());
	}

	private void clearLists() {
		luminositaList.clear();
		sismografoList.clear();
		tempEstList.clear();
		tempIntList.clear();
		tempDayList.clear();
		solarimetroList.clear();
	}

	private void checkLists() {
		if (luminositaList.isEmpty()) {
			luminositaList.add(0);
		}
		if (sismografoList.isEmpty()) {
			sismografoList.add(0);
		}
		if (solarimetroList.isEmpty()) {
			solarimetroList.add(0);
		}
		if (tempEstList.isEmpty()) {
			tempEstList.add(0);
		}
		if (tempIntList.isEmpty()) {
			tempIntList.add(0);
		}
		if (tempDayList.isEmpty()) {
			tempDayList.add(0);
		}
	}

	private void fillMeterAvg() {
		meterSonde.setLuminosita(average(luminositaList));
		meterSonde.setSismografo(average(sismografoList));
		meterSonde.setSolarimetro(average(solarimetroList));
		meterSonde.setTempEsterna(average(tempEstList));
		meterSonde.setTempGiorno(average(tempDayList));
		meterSonde.setTempLocali(average(tempIntList));
	}

	private void fillMeterMax() {
		checkLists();
		meterSonde.setLuminosita(Collections.max(luminositaList));
		meterSonde.setSismografo(Collections.max(sismografoList));
		meterSonde.setSolarimetro(Collections.max(solarimetroList));
		meterSonde.setTempEsterna(Collections.max(tempEstList));
		meterSonde.setTempGiorno(Collections.max(tempDayList));
		meterSonde.setTempLocali(Collections.max(tempIntList));
	}

	private void fillMeterMin() {
		checkLists();
		meterSonde.setLuminosita(Collections.min(luminositaList));
		meterSonde.setSismografo(Collections.min(sismografoList));
		meterSonde.setSolarimetro(Collections.min(solarimetroList));
		meterSonde.setTempEsterna(Collections.min(tempEstList));
		meterSonde.setTempGiorno(Collections.min(tempDayList));
		meterSonde.setTempLocali(Collections.min(tempIntList));
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
	 * Media Mensile. ritorna oggetto meterSonde contenenti i valori di media
	 * mensile
	 * 
	 * @param month
	 * @return
	 */
	public MeterSonde monthAverage(int year, int month) {
		meterSonde = new MeterSonde();
		clearLists();

		for (MeterSonde element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterAvg();

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
		meterSonde = new MeterSonde();
		clearLists();

		for (MeterSonde element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterMax();

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
		meterSonde = new MeterSonde();
		clearLists();

		for (MeterSonde element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterSonde;
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
		meterSonde = new MeterSonde();
		clearLists();

		for (MeterSonde element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

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
		meterSonde = new MeterSonde();
		clearLists();

		for (MeterSonde element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

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
		meterSonde = new MeterSonde();
		clearLists();

		for (MeterSonde element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterAvg();

		return meterSonde;
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
		meterSonde = new MeterSonde();
		clearLists();

		for (MeterSonde element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterAvg();

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
		meterSonde = new MeterSonde();
		clearLists();

		for (MeterSonde element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMax();

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
		meterSonde = new MeterSonde();
		clearLists();

		for (MeterSonde element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

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
			String SOL = myFormat.format(list.get(i).getSolarimetro());
			String TIN = myFormat.format(list.get(i).getTempLocali());
			String TEX = myFormat.format(list.get(i).getTempEsterna());
			String TDY = myFormat.format(list.get(i).getTempGiorno());

			dayCRVStrings[i] = "Luminosita: " + LUM + "\t Sismografo: " + SIS
					+ "\t Solarimetro: " + SOL + "\t Temp Locali: " + TIN
					+ "\t Temp Esterna: " + TEX + "\t Temp Giorno: " + TDY;
		}

		return dayCRVStrings;
	}

}
