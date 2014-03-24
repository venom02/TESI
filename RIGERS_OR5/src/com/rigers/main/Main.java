package com.rigers.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.rigers.db.Compartimento;

public class Main {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// Creo due utenti
		Compartimento c1 = new Compartimento();
		Compartimento c2 = new Compartimento();
		//setto username e password dei due utenti
		c1.setIdCompartimento(1);
		c1.setNomeCompartimento("Lunetta");
		c1.setIdCompartimento(2);
		c2.setNomeCompartimento("Due Pini");
		//Costruisco la session factory
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		//istanzio una Connessione al db
		Session session = factory.openSession();
		
		Transaction tx = null;
		try {
			//dichiaro che sta per iniziare un interazione con il db
			tx = session.beginTransaction();
			session.save(c1);
			session.save(c2);
			//rendo definitive le modifiche sul DB
			tx.commit();
		} //in caso d’eccezione è importante annullare le modifche effettuate
		catch (Exception e) {
			if (tx!=null) tx.rollback();
			throw e;
		}
		finally { //in ogni caso  chiudere la connessione
			session.close();
		}
	}//chiusura del main
}