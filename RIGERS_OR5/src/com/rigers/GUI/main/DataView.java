package com.rigers.GUI.main;

import java.util.List;

import org.hibernate.Session;

import com.rigers.persistence.HibernateUtil;

public class DataView {

	public static String[] CompIdItems(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List compList = session.createQuery(
				"select c.idCompartimento from Compartimento c").list();
		String[] compIdItems = new String[compList.size()];
		for (int i = 0; i < compList.size(); i++) {
			compIdItems[i] = compList.get(i).toString();
		}
		session.getTransaction().commit();
		return compIdItems;
	}
}
