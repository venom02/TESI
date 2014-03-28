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

import com.rigers.db.*;
import com.rigers.persistence.HibernateUtil;

public class DbManager {

	/**
	 * Riempie con dati casuali il database rigers
	 */
	public static void fillDb() {

		// Apertura sessione
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		// flushTables(session);

		// fillCompartimento(session);

		// fillEdificio(session);

		//fillLetturaRipartitoreCalore(session);
		
		//fillLetturaAcqua(session);
		
		//fillLetturaSonde(session);

		session.getTransaction().commit();
	}

	/**
	 * Inserisce nelal tabella Meter_Sonde una lettura per ogni edificio esistente
	 * @param session
	 */
	private static void fillLetturaSonde(Session session) {
		int idMeter = 4; //Id meter sonde sempre uguale a 4;

		List<Edificio> ediList = session.createQuery("from Edificio").list();

		// Generatore Valori Casuali
		Random generator = new Random();

		for (int i = 0; i < ediList.size(); i++) {
			LetturaDispositivo lettDisp = generateLettura(session,
					idMeter, ediList.get(i));

			// PK Meter Sonde
			MeterSondeId pkMeterSonde = new MeterSondeId();
			pkMeterSonde.setDataLettura(lettDisp.getId().getDataLettura());
			pkMeterSonde.setIdDispositivo(lettDisp.getId().getIdDispositivo());
			pkMeterSonde.setIdEdificio(lettDisp.getId().getIdEdificio());

			// INSERT Ripartitore Calore
			MeterSonde meterSonde = new MeterSonde(lettDisp);
			meterSonde.setId(pkMeterSonde);
			meterSonde.setLuminosita(generator.nextInt(10));
			meterSonde.setSismografo(generator.nextInt(100));
			meterSonde.setTempEsterna(generator.nextInt(40));
			meterSonde.setTempLocali(generator.nextInt(7)+18);
			session.save(meterSonde);
		}
		
	}

	/**
	 * Inserisce nella tabella Meter_ripartitore_calore una lettura per ogni edificio esistente
	 * @param session 
	 */
	private static void fillLetturaAcqua(Session session) {
		int idMeter = 0; //Id meter acqua sempre uguale a 0;

		List<Edificio> ediList = session.createQuery("from Edificio").list();

		// Generatore Valori Casuali
		Random generator = new Random();

		for (int i = 0; i < ediList.size(); i++) {
			LetturaDispositivo lettDisp = generateLettura(session,
					idMeter, ediList.get(i));

			// PK Meter Acqua
			MeterAcquaId pkMeterAcqua = new MeterAcquaId();
			pkMeterAcqua.setDataLettura(lettDisp.getId().getDataLettura());
			pkMeterAcqua.setIdDispositivo(lettDisp.getId().getIdDispositivo());
			pkMeterAcqua.setIdEdificio(lettDisp.getId().getIdEdificio());

			// INSERT MeterAcqua
			MeterAcqua meterAcqua = new MeterAcqua(lettDisp);
			meterAcqua.setId(pkMeterAcqua);
			meterAcqua.setCurrentReadoutValue(generator.nextInt(50));
			meterAcqua.setPeriodicReadoutValue(generator.nextInt(20));
			meterAcqua.setPeriodicReadingDate(new Date());
			session.save(meterAcqua);
		}
	}

	/**
	 * Inserisce nella tabella Meter_ripartitore_calore una lettura per ogni edificio esistente
	 * @param session 
	 */
	private static void fillLetturaRipartitoreCalore(Session session) {
		int idMeter = 3; // ogni Meter Ripartitore Calore ha id = 3

		List<Edificio> ediList = session.createQuery("from Edificio").list();

		// Valori Casuali Unita di consumo
		Random unitaConsumo = new Random();

		for (int i = 0; i < ediList.size(); i++) {
			LetturaDispositivo lettDisp = generateLettura(session,
					idMeter, ediList.get(i));

			// PK Meter Ripartitore Calore
			MeterRipartitoreCaloreId pkMeterRipCal = new MeterRipartitoreCaloreId();
			pkMeterRipCal.setDataLettura(lettDisp.getId().getDataLettura());
			pkMeterRipCal.setIdDispositivo(lettDisp.getId().getIdDispositivo());
			pkMeterRipCal.setIdEdificio(lettDisp.getId().getIdEdificio());

			// INSERT Meter Ripartitore Calore
			MeterRipartitoreCalore ripMeterRipCal = new MeterRipartitoreCalore(
					lettDisp, unitaConsumo.nextInt(500));
			ripMeterRipCal.setId(pkMeterRipCal);
			session.save(ripMeterRipCal);
		}
	}

	/**
	 * Genera una entry in Lettura se viene creata una entry in uno dei meter.
	 * @param session
	 * @param idDispositivo
	 * @param edificio
	 * @return
	 * 
	 */
	private static LetturaDispositivo generateLettura(Session session,
			int idDispositivo, Edificio edificio) {

		// generazione Random Data
		Random generator = new Random();
		int dd = generator.nextInt(30) + 1;
		int mm = generator.nextInt(12);
		int hh = generator.nextInt(24);
		int mmin = generator.nextInt(60);
		int ss = generator.nextInt(60);
		Date date = new Date(114, mm, dd, hh, mmin, ss);

		// PK Lettura Dispositovo
		LetturaDispositivoId lettDispId = new LetturaDispositivoId(date,
				idDispositivo, edificio.getIdEdificio());

		// Creazione oggetto dispositivo senza richiamare una ulteriore query
		DispositivoId id = new DispositivoId(idDispositivo,
				edificio.getIdEdificio());
		Dispositivo dispositivo = new Dispositivo(id, edificio);

		// INSERT lettura dispositivo
		LetturaDispositivo lettDisp = new LetturaDispositivo(lettDispId,
				dispositivo);
		session.save(lettDisp);
		return lettDisp;
	}

	/**
	 * Elimina tutte le entry nelle tabelle
	 * @param session
	 */
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

	/**
	 * Genera 20 edifici random
	 * @param session
	 */
	private static void fillEdificio(Session session) {
		int RANGE_EDIFICI = 20;
		List<Edificio> ediList = new ArrayList<Edificio>(RANGE_EDIFICI);
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
		for (int i = 0; i < RANGE_EDIFICI; i++) {
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

	/**
	 * Genera 20 compartimenti random
	 * @param session
	 */
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