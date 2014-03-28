package com.rigers.main;

import java.util.List;

import org.hibernate.Session;

import com.rigers.db.*;
import com.rigers.persistence.HibernateUtil;

public class DataInsert {

	public static boolean insertComp(String idCompartimento, String nomeCompartimento){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		int value = Integer.parseInt(idCompartimento);
		
		List<Compartimento> compList = session
				.createQuery("from Compartimento").list();
		
		for(int i=0; i<compList.size(); i++){
			System.out.println(compList.get(i).getIdCompartimento());
			Boolean compare = compList.get(i).getIdCompartimento() == value;
			if(compare){
				System.out.print(compare);
				session.getTransaction().commit();
				return false;
			}
		}
		
		Compartimento comp = new Compartimento();
		comp.setIdCompartimento(value);
		comp.setNomeCompartimento(nomeCompartimento);
		session.save(comp);
		session.getTransaction().commit();
		return true;
	}
}
