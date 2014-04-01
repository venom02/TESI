package com.rigers.main;

import java.util.List;

import org.hibernate.Session;

import com.rigers.db.Compartimento;
import com.rigers.db.Edificio;
import com.rigers.persistence.HibernateUtil;

public class DataView {

	public static String[] CompItems(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Compartimento> compList = session.createQuery(
				"from Compartimento").list();
		
		String[] compItems = new String[compList.size()];
		for (int i = 0; i < compList.size(); i++) {
			compItems[i] = Integer.toString(compList.get(i).getIdCompartimento());
		}
		session.getTransaction().commit();
		return compItems;
	}
	
	public static String[] EdifItems(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Edificio> edifList = session.createQuery(
				"from Edificio").list();
		
		String[] edifItems = new String[edifList.size()];
		for (int i = 0; i < edifList.size(); i++) {
			edifItems[i] = Integer.toString(edifList.get(i).getIdEdificio());
		}
		session.getTransaction().commit();
		return edifItems;
	}
}