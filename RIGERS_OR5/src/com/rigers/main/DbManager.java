package com.rigers.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rigers.db.Compartimento;
import com.rigers.db.Dispositivo;
import com.rigers.db.DispositivoId;
import com.rigers.db.Edificio;
import com.rigers.persistence.HibernateUtil;

public class DbManager {

	public static void fillDb() {

		// Apertura sessione
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		flushTables(session);

		fillCompartimento(session);

		fillEdificio(session);

		fillDispositivo(session);

		session.getTransaction().commit();
	}

	private static void flushTables(Session session) {
		Query q = null; 
		
		q = session.createQuery("delete from Dispositivo");
		q.executeUpdate();
		q = session.createQuery("delete from Edificio");
		q.executeUpdate();
		q = session.createQuery("delete from Compartimento");
		q.executeUpdate();

	}

	private static void fillDispositivo(Session session) {
		List<String> meters = Arrays.asList( "Meter Acqua", "Meter Elettrico", "Meter Gas",
				"Meter Ripartitore Calore", "Meter Sonde", "Meter Termie" );
		
		// get lista edifici creati
		List<Edificio> ediList = session.createQuery("from Edificio").list();
		System.out.println(ediList.size());

		// riempimento dispositivi
		for (int i = 0; i < ediList.size(); i++) {
			for (int k = 0; k < meters.size(); k++) {
				DispositivoId dispId = new DispositivoId(k, ediList.get(i)
						.getIdEdificio());
				Dispositivo disp = new Dispositivo(dispId, ediList.get(i));
				disp.setNomeDispositivo(meters.get(k) + " Edificio "+ ediList.get(i).getIdEdificio());
				session.save(disp);
			}
		}
	}

	private static void fillEdificio(Session session) {
		// get lista compartimenti creati
		List<Compartimento> compList = session
				.createQuery("from Compartimento").list();

		int RANGE = 100;
		final List<Integer> sack = new ArrayList<>(RANGE);
		for (int i = 0; i < RANGE; i++)
			sack.add(i);
		Collections.shuffle(sack);

		// riempimento edifici
		for (int i = 0; i < 20; i++) {
			Edificio edificio = new Edificio();
			edificio.setIdEdificio(sack.get(i));
			edificio.setCompartimento(compList.get(i));
			edificio.setIndirizzoEdificio("Via " + i + " Number " + sack.get(i));
			session.save(edificio);
		}

	}

	private static void fillCompartimento(Session session) {
		// generatore di numeri causuali in RANGE
		int RANGE = 100;
		final List<Integer> sack = new ArrayList<>(RANGE);
		for (int i = 0; i < RANGE; i++)
			sack.add(i);
		Collections.shuffle(sack);
		// riempimento compartimenti
		for (int i = 0; i < 20; i++) {
			Compartimento comp = new Compartimento();
			comp.setIdCompartimento(sack.get(i));
			comp.setNomeCompartimento("Comp Number " + sack.get(i));
			// compList.add(comp);
			session.save(comp);
		}
	}
}