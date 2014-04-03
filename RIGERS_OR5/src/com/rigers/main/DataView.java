package com.rigers.main;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.rigers.db.Compartimento;
import com.rigers.db.Edificio;
import com.rigers.db.LetturaDispositivo;
import com.rigers.db.LetturaDispositivoId;
import com.rigers.persistence.HibernateUtil;

public class DataView {

	public static String[] CompItems(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Compartimento> compList = session.createQuery(
				"from Compartimento").list();
		
		String[] compItems = new String[compList.size()];
		for (int i = 0; i < compList.size(); i++) {
			compItems[i] = Integer.toString(compList.get(i).getIdCompartimento()) + ": "+compList.get(i).getNomeCompartimento();
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
			edifItems[i] = Integer.toString(edifList.get(i).getIdEdificio()) +": "+ edifList.get(i).getIndirizzo();
		}
		session.getTransaction().commit();
		return edifItems;
	}
	
	public static String[] LettureItems(int compIndex, int edifIndex, int dispIndex, Date dateFrom, Date dateTo){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List<LetturaDispositivo> lettList = session.createQuery(
				"from LetturaDispositivo").list();
		
		String[] lettItems = null;
		
		session.getTransaction().commit();
		return lettItems;
	}

	public static String[] allLettureItems() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List<LetturaDispositivoId> lettList = session.createQuery(
				"from LetturaDispositivo").list();
		
		String[] lettItems = new String[lettList.size()];
		for (int i = 0; i < lettList.size(); i++) {
			String string = "Data: " +lettList.get(i).getDataLettura().toString() + " \tDispositivo: " + lettList.get(i).getIdDispositivo() + " \tEdificio: " + lettList.get(i).getIdEdificio();
			lettItems[i] = string;
		}
		session.getTransaction().commit();
		return lettItems;
	}
}