package com.rigers.API;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import com.rigers.db.*;
import com.rigers.persistence.HibernateUtil;

public class MeterAcquaStats extends Tools {
	
	private Edificio edificio;
	
	public MeterAcquaStats(Edificio edificio){
		this.edificio = edificio;
	}
	
	public MeterAcqua monthAverage(int month) {
		MeterAcqua meterAcqua = null; 
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();		

		Calendar cal = Calendar.getInstance();
		cal.set(com.ibm.icu.util.Calendar.MONTH, month);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		Date dateTo = cal.getTime();
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		Date dateFrom = cal.getTime();
		
		String queryStr = "SELECT m " 
				+ "FROM MeterAcqua as m "
				+ "WHERE m.idLettura IN (select l.idLettura "
									+ "from LetturaDispositivo as l "
									+ "WHERE l.dataLettura< :dateTo "
									+ "AND l.dataLettura> :dateFrom "
									+ "AND l.dispositivo.edificio.idEdificio= :edificio)";
		Query query = session.createQuery(queryStr);
		query.setParameter("dateTo", dateTo);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("edificio", edificio.getIdEdificio());
		List<MeterAcqua> list = query.list();
		int i = 0;
		ArrayList<Integer> curReadValList = new ArrayList<Integer>();
		for(MeterAcqua element : list){
			curReadValList.add(element.getCurrentReadoutValue());
			System.out.println(curReadValList.get(i));
			i++;
		}
		
		meterAcqua.setCurrentReadoutValue(calculateAverage(curReadValList));
		System.out.println(meterAcqua.getCurrentReadoutValue());
		
		return meterAcqua;
	}
}
