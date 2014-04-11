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
import com.rigers.db.MeterRipartitoreCalore;
import com.rigers.persistence.HibernateUtil;

public class MeterRipartitoreCaloreStats extends Tools {

	private Edificio edificio;

	/**
	 * Costruttore. ottiene parametro edificio
	 * @param edificio
	 */
	public MeterRipartitoreCaloreStats(Edificio edificio){
		this.edificio = edificio;
	}

	/**
	 * genera una lista di oggetti MeterAcqua appartenenti solo al mese dato e all'edificio prescelto
	 * @param month
	 * @return
	 */
	private List<MeterRipartitoreCalore> getMonthList(int month){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();		

		Calendar cal = Calendar.getInstance();
		cal.set(com.ibm.icu.util.Calendar.MONTH, month);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		Date dateTo = cal.getTime();
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		Date dateFrom = cal.getTime();

		String queryStr = "SELECT m " 
				+ "FROM MeterRipartitoreCalore as m "
				+ "WHERE m.idLettura IN (select l.idLettura "
				+ "from LetturaDispositivo as l "
				+ "WHERE l.dataLettura< :dateTo "
				+ "AND l.dataLettura> :dateFrom "
				+ "AND l.dispositivo.edificio.idEdificio= :edificio)";
		Query query = session.createQuery(queryStr);
		query.setParameter("dateTo", dateTo);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("edificio", edificio.getIdEdificio());
		List<MeterRipartitoreCalore> list = query.list();

		session.getTransaction().commit();
		return list;
	}

	/**
	 * Media Mensile. ritorna oggetto meterAcqua contenenti i valori di media mensile
	 * @param month
	 * @return
	 */
	public MeterRipartitoreCalore monthAverage(int month) {
		MeterRipartitoreCalore meterRipCal = new MeterRipartitoreCalore(); 

		ArrayList<Integer> unitaConsumoList = new ArrayList<Integer>();
	
		for(MeterRipartitoreCalore element : getMonthList(month)){
			unitaConsumoList.add(element.getUnitaConsumo());
		}

		if(!unitaConsumoList.isEmpty()){
			meterRipCal.setUnitaConsumo(average(unitaConsumoList));
		}else
			meterRipCal.setUnitaConsumo(0);

		return meterRipCal;
	}

	/**
	 * Massimo mensile. ritorna oggetto MeterAcqua contenente tutti i valori massimi del mese
	 * @param month
	 * @return
	 */
	public MeterRipartitoreCalore monthMax(int month){
		MeterRipartitoreCalore meterRipCal = new MeterRipartitoreCalore(); 

		ArrayList<Integer> unitaConsumoList = new ArrayList<Integer>();
		
		for(MeterRipartitoreCalore element : getMonthList(month)){
			unitaConsumoList.add(element.getUnitaConsumo());
		}

		if(!unitaConsumoList.isEmpty()){
			meterRipCal.setUnitaConsumo(Collections.max(unitaConsumoList));
		}else
			meterRipCal.setUnitaConsumo(0);

		return meterRipCal;
	}

	/**
	 * Minimo mensile. ritorna oggetto MeterAcqua contenente tutti i valori minimi del mese
	 * @param month
	 * @return
	 */
	public MeterRipartitoreCalore monthMin(int month){
		MeterRipartitoreCalore meterRipCal = new MeterRipartitoreCalore(); 

		ArrayList<Integer> unitaConsumoList = new ArrayList<Integer>();
		
		for(MeterRipartitoreCalore element : getMonthList(month)){
			unitaConsumoList.add(element.getUnitaConsumo());
		}

		if(!unitaConsumoList.isEmpty()){
			meterRipCal.setUnitaConsumo(Collections.min(unitaConsumoList));
		}else
			meterRipCal.setUnitaConsumo(0);

		return meterRipCal;
	}

	/**
	 * Ritorna oggetto MeterAcqua solo della settimana appartenente al giorno dato
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private List<MeterRipartitoreCalore> getWeekList(int year, int month, int date){
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

		String queryStr = "SELECT m " 
				+ "FROM MeterRipartitoreCalore as m "
				+ "WHERE m.idLettura IN (select l.idLettura "
				+ "from LetturaDispositivo as l "
				+ "WHERE l.dataLettura< :dateTo "
				+ "AND l.dataLettura> :dateFrom "
				+ "AND l.dispositivo.edificio.idEdificio= :edificio)";
		Query query = session.createQuery(queryStr);
		query.setParameter("dateTo", dateTo);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("edificio", edificio.getIdEdificio());

		List<MeterRipartitoreCalore> list = query.list();
		session.getTransaction().commit();
		return list;
	}

	/**
	 * Minimo Settimanale
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterRipartitoreCalore weekMin(int year, int month, int date){
		MeterRipartitoreCalore meterRipCal = new MeterRipartitoreCalore(); 

		ArrayList<Integer> unitaConsumoList = new ArrayList<Integer>();
		
		for(MeterRipartitoreCalore element : getWeekList(year, month, date)){
			unitaConsumoList.add(element.getUnitaConsumo());
		}

		if(!unitaConsumoList.isEmpty()){
			meterRipCal.setUnitaConsumo(Collections.min(unitaConsumoList));
		}else
			meterRipCal.setUnitaConsumo(0);

		return meterRipCal;
	}

	/**
	 * Massimo Settimanale
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterRipartitoreCalore weekMax(int year, int month, int date){
		MeterRipartitoreCalore meterRipCal = new MeterRipartitoreCalore(); 

		ArrayList<Integer> unitaConsumoList = new ArrayList<Integer>();
		
		for(MeterRipartitoreCalore element : getWeekList(year, month, date)){
			unitaConsumoList.add(element.getUnitaConsumo());
		}

		if(!unitaConsumoList.isEmpty()){
			meterRipCal.setUnitaConsumo(Collections.max(unitaConsumoList));
		}else
			meterRipCal.setUnitaConsumo(0);

		return meterRipCal;
	}

	/**
	 * Media Settimanale
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public MeterRipartitoreCalore weekAverage(int year, int month, int date) {
		MeterRipartitoreCalore meterRipCal = new MeterRipartitoreCalore(); 

		ArrayList<Integer> unitaConsumoList = new ArrayList<Integer>();
		
		for(MeterRipartitoreCalore element : getWeekList(year, month, date)){
			unitaConsumoList.add(element.getUnitaConsumo());
		}

		if(!unitaConsumoList.isEmpty()){
			meterRipCal.setUnitaConsumo(average(unitaConsumoList));
		}else
			meterRipCal.setUnitaConsumo(0);

		return meterRipCal;
	}

	public MeterRipartitoreCalore actual(int year, int month, int day){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();	

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, 0, 0, 0);
		Date dateFrom = cal.getTime();
		cal.set(year, month, day+1, 0, 0, 0);
		Date dateTo = cal.getTime();

		String queryStr = "SELECT m " 
				+ "FROM MeterRipartitoreCalore as m "
				+ "WHERE m.idLettura IN (select l.idLettura "
				+ "from LetturaDispositivo as l "
				+ "WHERE l.dataLettura< :dateTo "
				+ "AND l.dataLettura> :dateFrom "
				+ "AND l.dispositivo.edificio.idEdificio= :edificio)";
		Query query = session.createQuery(queryStr);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateTo", dateTo);
		query.setParameter("edificio", edificio.getIdEdificio());
		query.setMaxResults(1);
		List<MeterRipartitoreCalore> meterRipartitoreCalore = query.list();
		session.getTransaction().commit();
				
		if (!meterRipartitoreCalore.isEmpty()) {
			return meterRipartitoreCalore.get(0);
		} else{
			return new MeterRipartitoreCalore(new LetturaDispositivo(),0);
		}
	}

}
