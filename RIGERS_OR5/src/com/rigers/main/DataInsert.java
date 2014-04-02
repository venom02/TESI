package com.rigers.main;

import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.hibernate.Session;

import com.rigers.db.*;
import com.rigers.persistence.HibernateUtil;

public class DataInsert {

	/**
	 * Inserisce nel database un Compartimento
	 * 
	 * @param idCompartimento
	 *            String per ottimizzare l'inserimento tramite elemento Spinner.
	 * @param nomeCompartimento
	 * @return Boolean. ritorna true se l'inserimento è andato a buon fine.
	 */
	public static boolean insertComp(String idCompartimento,
			String nomeCompartimento) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		int value = Integer.parseInt(idCompartimento);

		List<Compartimento> compList = session.createQuery("from Compartimento").list();

		for (int i = 0; i < compList.size(); i++) {
			System.out.println(compList.get(i).getIdCompartimento());
			Boolean compare = compList.get(i).getIdCompartimento() == value;
			if (compare) {
				System.out.print(compare);
				session.getTransaction().rollback();
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

	public static boolean insertEdificio(String idEdificio, int indexCompartimento, String indirizzo) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		/**
		 * Check esistenza IdEdificio
		 */
		int idEdifInt = Integer.parseInt(idEdificio);
		List<Edificio> ediList = session.createQuery("from Edificio").list();
		List<Compartimento> compList = session.createQuery("from Compartimento").list();
		
		for(int i = 0; i<ediList.size(); i++){
			Boolean compare = ediList.get(i).getIdEdificio() == idEdifInt;
			if (compare) {
				System.out.print("idEdificio already existing");
				session.getTransaction().rollback();
				return false;
			}
		}
		
		Edificio edificio = new Edificio();
		edificio.setIdEdificio(idEdifInt);
		edificio.setIndirizzo(indirizzo);
		
		Compartimento compartimento = ediList.get(indexCompartimento).getCompartimento();
		System.out.println("Compartimento "+compartimento.getIdCompartimento());
		edificio.setCompartimento(compartimento);
		
		session.save(edificio);
		session.getTransaction().commit();
		return true;
	}

}