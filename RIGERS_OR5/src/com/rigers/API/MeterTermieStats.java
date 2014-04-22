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
import com.rigers.db.MeterTermie;
import com.rigers.persistence.HibernateUtil;

public class MeterTermieStats extends Tools {

	private Edificio edificio;
	private ArrayList<Integer> currEnerList = new ArrayList<Integer>();		// Current Energy 
	private ArrayList<Integer> ctvList = new ArrayList<Integer>();			// Current Total Volume
	private ArrayList<Integer> currFlowList = new ArrayList<Integer>();		// Current Flow
	private ArrayList<Integer> currPerfList = new ArrayList<Integer>();		// Current Performance
	private ArrayList<Integer> rftList = new ArrayList<Integer>();			// Return Flow Temperature
	private ArrayList<Integer> tempDiffList = new ArrayList<Integer>();		// Temperature Difference
	private ArrayList<Integer> currImp1List = new ArrayList<Integer>();		// Current Impulse Count 1
	private ArrayList<Integer> currImp2List = new ArrayList<Integer>();		// Current Impulse Count 2
	private MeterTermie meterTermie;

	/**
	 * Costruttore. ottiene parametro edificio
	 * 
	 * @param edificio
	 */
	public MeterTermieStats(Edificio edificio) {
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
	private List<MeterTermie> queryList(Date dateFrom, Date dateTo) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String queryStr = "SELECT m " + "FROM MeterTermie as m "
				+ "WHERE m.idLettura IN (select l.idLettura "
				+ "from LetturaDispositivo as l "
				+ "WHERE l.dataLettura< :dateTo "
				+ "AND l.dataLettura>= :dateFrom "
				+ "AND l.dispositivo.edificio.idEdificio= :edificio)";
		Query query = session.createQuery(queryStr);
		query.setParameter("dateTo", dateTo);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("edificio", edificio.getIdEdificio());

		List<MeterTermie> list = query.list();
		session.getTransaction().commit();
		return list;
	}

	private void fillLists(MeterTermie e) {
		currEnerList.add(e.getCurrentEnergy());
		ctvList.add(e.getCurrentTotalVolume());
		currFlowList.add(e.getCurrentFlow());
		currPerfList.add(e.getCurrentPerformance());
		rftList.add(e.getReturnFlowTemperature());
		tempDiffList.add(e.getTemperatureDifference());
		currImp1List.add(e.getCurrentImpulseCount1());
		currImp2List.add(e.getCurrentImpulseCount2());

	}

	private void clearLists() {
		currEnerList.clear();
		ctvList.clear();
		currFlowList.clear();
		currPerfList.clear();
		rftList.clear();
		tempDiffList.clear();
		currImp1List.clear();
		currImp2List.clear();
	}

	private void checkLists() {
		chk(currEnerList);
		chk(ctvList);
		chk(currFlowList);
		chk(currPerfList);
		chk(rftList);
		chk(tempDiffList);
		chk(currImp1List);
		chk(currImp2List);
	}

	private void fillMeterAvg() {
		meterTermie.setCurrentEnergy(average(currEnerList));
		meterTermie.setCurrentTotalVolume(average(ctvList));
		meterTermie.setCurrentFlow(average(currFlowList));
		meterTermie.setCurrentPerformance(average(currPerfList));
		meterTermie.setReturnFlowTemperature(average(rftList));
		meterTermie.setTemperatureDifference(average(tempDiffList));
		meterTermie.setCurrentImpulseCount1(average(currImp1List));
		meterTermie.setCurrentImpulseCount2(average(currImp2List));
	}

	private void fillMeterMax() {
		checkLists();
		meterTermie.setCurrentEnergy(Collections.max(currEnerList));
		meterTermie.setCurrentTotalVolume(Collections.max(ctvList));
		meterTermie.setCurrentFlow(Collections.max(currFlowList));
		meterTermie.setCurrentPerformance(Collections.max(currPerfList));
		meterTermie.setReturnFlowTemperature(Collections.max(rftList));
		meterTermie.setTemperatureDifference(Collections.max(tempDiffList));
		meterTermie.setCurrentImpulseCount1(Collections.max(currImp1List));
		meterTermie.setCurrentImpulseCount2(Collections.max(currImp2List));
	}

	private void fillMeterMin() {
		checkLists();
		meterTermie.setCurrentEnergy(Collections.min(currEnerList));
		meterTermie.setCurrentTotalVolume(Collections.min(ctvList));
		meterTermie.setCurrentFlow(Collections.min(currFlowList));
		meterTermie.setCurrentPerformance(Collections.min(currPerfList));
		meterTermie.setReturnFlowTemperature(Collections.min(rftList));
		meterTermie.setTemperatureDifference(Collections.min(tempDiffList));
		meterTermie.setCurrentImpulseCount1(Collections.min(currImp1List));
		meterTermie.setCurrentImpulseCount2(Collections.min(currImp2List));
	}

	/**
	 * genera una lista di oggetti MeterTermie appartenenti solo al mese dato e
	 * all'edificio prescelto
	 * 
	 * @param month
	 * @return
	 */
	private List<MeterTermie> getMonthList(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		Date dateTo = cal.getTime();
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		Date dateFrom = cal.getTime();

		List<MeterTermie> list = queryList(dateFrom, dateTo);

		return list;
	}

	/**
	 * Ritorna lista oggetti MeterTermie appartenenti al giorno dato
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private List<MeterTermie> getDayList(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date, 0, 0, 0);
		Date dateFrom = cal.getTime();
		cal.add(Calendar.DATE, 1);
		Date dateTo = cal.getTime();

		List<MeterTermie> list = queryList(dateFrom, dateTo);
		return list;
	}

	/**
	 * Ritorna lista oggetti MeterTermie solo della settimana appartenente al
	 * giorno dato
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private List<MeterTermie> getWeekList(int year, int month, int date) {
		// Get calendar set to given date and time
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);
		// Set the calendar to monday and sunday of the given week
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date dateFrom = cal.getTime();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date dateTo = cal.getTime();

		List<MeterTermie> list = queryList(dateFrom, dateTo);
		return list;
	}

	/**
	 * Media Mensile. ritorna oggetto MeterTermie contenenti i valori di media
	 * mensile
	 * 
	 * @param month
	 * @return
	 */
	public MeterTermie monthAverage(int year, int month) {
		meterTermie = new MeterTermie();
		checkLists();

		for (MeterTermie element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterAvg();

		return meterTermie;
	}

	/**
	 * Massimo mensile. ritorna oggetto MeterTermie contenente tutti i valori
	 * massimi del mese
	 * 
	 * @param month
	 * @return
	 */
	public MeterTermie monthMax(int year, int month) {
		meterTermie = new MeterTermie();
		checkLists();

		for (MeterTermie element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterMax();

		return meterTermie;
	}

	/**
	 * Minimo mensile. ritorna oggetto MeterTermie contenente tutti i valori
	 * minimi del mese
	 * 
	 * @param month
	 * @return
	 */
	public MeterTermie monthMin(int year, int month) {
		meterTermie = new MeterTermie();
		checkLists();

		for (MeterTermie element : getMonthList(year, month)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterTermie;
	}

	/**
	 * Minimo Settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterTermie weekMin(int year, int month, int date) {
		meterTermie = new MeterTermie();
		checkLists();

		for (MeterTermie element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterTermie;
	}

	/**
	 * Massimo settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterTermie weekMax(int year, int month, int date) {
		meterTermie = new MeterTermie();
		checkLists();

		for (MeterTermie element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMax();

		return meterTermie;
	}

	/**
	 * Media settimanale
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterTermie weekAverage(int year, int month, int date) {
		meterTermie = new MeterTermie();
		checkLists();

		for (MeterTermie element : getWeekList(year, month, date)) {
			fillLists(element);
		}

		fillMeterAvg();

		return meterTermie;
	}

	/**
	 * media giornaliera
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterTermie dayAverage(int year, int month, int date) {
		meterTermie = new MeterTermie();
		checkLists();

		for (MeterTermie element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterAvg();

		return meterTermie;
	}

	/**
	 * Massimo giornaliero
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterTermie dayMax(int year, int month, int date) {
		meterTermie = new MeterTermie();
		checkLists();

		for (MeterTermie element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMax();

		return meterTermie;
	}

	/**
	 * Minimo giornaliero
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterTermie dayMin(int year, int month, int date) {
		meterTermie = new MeterTermie();
		checkLists();

		for (MeterTermie element : getDayList(year, month, date)) {
			fillLists(element);
		}

		fillMeterMin();

		return meterTermie;
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
		List<MeterTermie> list = getDayList(year, month, date);
		String[] dayCRVStrings = new String[list.size()];

		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setMinimumIntegerDigits(3);

		currEnerList.clear();
		ctvList.clear();
		currFlowList.clear();
		currPerfList.clear();
		rftList.clear();
		tempDiffList.clear();
		currImp1List.clear();
		currImp2List.clear();
		
		for (int i = 0; i < list.size(); i++) {
			String CE = myFormat.format(list.get(i).getCurrentEnergy());
			String CTV = myFormat.format(list.get(i).getCurrentTotalVolume());
			String CF = myFormat.format(list.get(i).getCurrentFlow());
			String CP = myFormat.format(list.get(i).getCurrentPerformance());
			String RFT = myFormat.format(list.get(i).getReturnFlowTemperature());
			String TD = myFormat.format(list.get(i).getTemperatureDifference());
			String CI1 = myFormat.format(list.get(i).getCurrentImpulseCount1());
			String CI2 = myFormat.format(list.get(i).getCurrentImpulseCount2());
			String EC = myFormat.format(list.get(i).getErrorCode());
			Date CDT = list.get(i).getCurrentDateTime();
			
			dayCRVStrings[i] = "Current Energy: " + CE 
					+ "\t Current Total Volume: " + CTV
					+ "\t Current Flow: " + CF
					+ "\t Current Performance: " + CP
					+ "\t Return Flow Temperature: " + RFT
					+ "\t Temperature Difference: " + TD
					+ "\t Current Impulse Count 1: " + CI1
					+ "\t Current Impulse Count 2: " + CI2
					+ "\t Error Code: " + EC
					+ "\t Current Date Time: " + CDT
					;
		}

		return dayCRVStrings;
	}
}
