package com.rigers.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rigers.db.Compartimento;
import com.rigers.db.Dispositivo;
import com.rigers.db.DispositivoId;
import com.rigers.db.Edificio;
import com.rigers.db.LetturaDispositivo;
import com.rigers.db.LetturaDispositivoId;
import com.rigers.db.MeterGas;
import com.rigers.db.MeterGasId;
import com.rigers.db.MeterRipartitoreCalore;
import com.rigers.db.MeterRipartitoreCaloreId;
import com.rigers.persistence.HibernateUtil;

public class DbManager {

	public static void fillDb() {

		// Apertura sessione
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		// flushTables(session);
		//
		// fillCompartimento(session);
		//
		// fillEdificio(session);
		//
		// fillDispositivo(session);

		fillLetturaRipartitoreCalore(session);

		session.getTransaction().commit();
	}

	private static void fillLetturaRipartitoreCalore(Session session) {
		int idDispMeterRipartitoreCalore = 3; // ogni Meter Ripartitore Calore
												// ha id=3

		List<Edificio> ediList = session.createQuery("from Edificio").list();

		// Valori Casuali Unità di consumo
		Random unitaConsumo = new Random();

		for (int i = 0; i < ediList.size(); i++) {
			LetturaDispositivo lettDisp = generateLettura(session,
					idDispMeterRipartitoreCalore, ediList.get(i).getIdEdificio());

			// PK Meter Ripartitore Calore
			// MeterRipartitoreCaloreId ripCalId = new
			// MeterRipartitoreCaloreId();
			// ripCalId.setDataLettura(lettDisp.getId().getDataLettura());
			// ripCalId.setIdDispositivo(lettDisp.getId().getIdDispositivo());
			// ripCalId.setIdEdificio(lettDisp.getId().getIdEdificio());

			// INSERT Ripartitore Calore
			MeterRipartitoreCalore ripCal = new MeterRipartitoreCalore(
					lettDisp, unitaConsumo.nextInt(500));
			session.save(ripCal);
		}
	}

	private static LetturaDispositivo generateLettura(Session session,
			int idDispositivo, int idEdificio) {

		
		/**
		 * COMPLETARE QUA!
		 */
		List<Dispositivo> dispList = session.createQuery("from Dispositivo where Edificio.idEdificio = :id")
				.list();
		List<Edificio> ediList = session.createQuery("from Edificio").list();
		
		//generazione Random Data
		Random generator = new Random();
		int dd = generator.nextInt(30) + 1;
		int mm = generator.nextInt(12);
		int hh = generator.nextInt(24);
		int mmin = generator.nextInt(60);
		int ss = generator.nextInt(60);
		Date date = new Date(114, mm, dd, hh, mmin, ss);

		// PK Lettura Dispositovo
		LetturaDispositivoId lettDispId = new LetturaDispositivoId(date, idDispositivo, idEdificio);

		// INSERT lettura dispositivo
		LetturaDispositivo lettDisp = new LetturaDispositivo(lettDispId, Dispositivo);
		session.save(lettDisp);
		return lettDisp;
	}

	private static void flushTables(Session session) {
		Query q = null;

		q = session.createQuery("delete from MeterRipartitoreCalore");
		q.executeUpdate();
		q = session.createQuery("delete from LetturaDispositivo");
		q.executeUpdate();
		q = session.createQuery("delete from Dispositivo");
		q.executeUpdate();
		q = session.createQuery("delete from Edificio");
		q.executeUpdate();
		q = session.createQuery("delete from Compartimento");
		q.executeUpdate();

	}

	private static void fillEdificio(Session session) {
		List<Edificio> ediList = null;
		List<String> meters = Arrays.asList("Meter Acqua", "Meter Elettrico",
				"Meter Gas", "Meter Ripartitore Calore", "Meter Sonde",
				"Meter Termie");
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
			edificio.setIndirizzo("Via " + i + " Number " + sack.get(i));
			ediList.add(edificio);
			session.save(edificio);
		}

		// riempimento dispositivi
		for (int i = 0; i < ediList.size(); i++) {
			for (int k = 0; k < meters.size(); k++) {
				DispositivoId dispId = new DispositivoId(k, ediList.get(i)
						.getIdEdificio());
				Dispositivo disp = new Dispositivo(dispId, ediList.get(i));
				disp.setNomeDispositivo(meters.get(k) + " Edificio "
						+ ediList.get(i).getIdEdificio());
				session.save(disp);
			}
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