package com.rigers.main;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;

import com.rigers.db.Compartimento;
import com.rigers.persistence.HibernateUtil;

public class DbManager {

	public static void addCompartimento() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Random rand = new Random();
		int i = rand.nextInt(99);

		Compartimento comp = new Compartimento();
		comp.setIdCompartimento(i);
		comp.setNomeCompartimento("Comp Number" + i);

		session.save(comp);

		session.getTransaction().commit();
	}

	public static void getCompartimento() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List comps = session
				.createSQLQuery(
						"select * from Compartimento order by idCompartimento")
				.addEntity(Compartimento.class).list();

		System.out.println(comps.size() + " Compartimento(i) found: ");
		for (Iterator iter = comps.iterator(); iter.hasNext();) {
			Compartimento comp = (Compartimento) iter.next();
			System.out.println(comp.getIdCompartimento() +"\t"+ comp.getNomeCompartimento());
		}
		session.getTransaction().commit();
	}
}