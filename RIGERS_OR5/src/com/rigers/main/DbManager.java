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

import com.ibm.icu.util.Calendar;
import com.rigers.db.*;
import com.rigers.persistence.HibernateUtil;

public class DbManager {

	Edificio edificio;

	public DbManager(Edificio edificio) {
		this.edificio = edificio;
	}

	/**
	 * Riempie con dati casuali il database rigers
	 */
	public static void fillDb(Edificio edificio) {
		// Apertura sessione
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		// flushTables(session);
		//
		// fillCompartimento(session, 20);
		//
		// fillEdificio(session, 20);
		//
		// fillLetturaRipartitoreCalore(session);

		// fillLetturaAcqua(session);

		// fillLetturaSonde(session);

		session.getTransaction().commit();
	}

	public void fillMonth(int month) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Calendar cal = Calendar.getInstance();
		cal.set(2014, month, 1, 0, 0, 0);
		for (int i = 0; i < 30; i++) {
			cal.set(Calendar.HOUR, 0);
			cal.add(Calendar.DATE, 1);
			for (int j = 0; j < 4; j++) {
				cal.add(Calendar.HOUR, 6);
				Date date = cal.getTime();
				// fillLetturaAcqua(session, date);
				fillLetturaSonde(session, date);
			}
		}
		session.getTransaction().commit();
	}

	/**
	 * Inserisce nelal tabella Meter_Sonde una lettura per ogni edificio
	 * esistente
	 * 
	 * @param session
	 * @param date
	 */
	private void fillLetturaSonde(Session session, Date date) {
		int idMeter = 4; // Id meter sonde sempre uguale a 4;

		// Generatore Valori Casuali
		Random generator = new Random();

		LetturaDispositivo lettDisp = generateLettura(session, idMeter,	edificio);

		// PK Meter Sonde

		// INSERT Ripartitore Calore
		MeterSonde meterSonde = new MeterSonde(lettDisp);
		meterSonde.setIdLettura(lettDisp.getIdLettura());
		meterSonde.setLuminosita(generator.nextInt(10));
		meterSonde.setSismografo(generator.nextInt(100));
		meterSonde.setTempEsterna(generator.nextInt(40));
		meterSonde.setTempLocali(generator.nextInt(7) + 18);
		session.save(meterSonde);
	}

	/**
	 * Inserisce nella tabella Meter_ripartitore_calore una lettura per ogni
	 * edificio esistente
	 * 
	 * @param session
	 */
	private void fillLetturaAcqua(Session session, Date date) {
		int idMeter = 0; // Id meter acqua sempre uguale a 0;

		List<Edificio> ediList = session.createQuery("from Edificio").list();

		// Generatore Valori Casuali
		Random generator = new Random();

		LetturaDispositivo lettDisp = generateLettura(session, idMeter,
				edificio, date);

		// INSERT MeterAcqua
		MeterAcqua meterAcqua = new MeterAcqua(lettDisp);
		meterAcqua.setIdLettura(lettDisp.getIdLettura());
		meterAcqua.setCurrentReadoutValue(generator.nextInt(50));
		meterAcqua.setPeriodicReadoutValue(generator.nextInt(20));
		meterAcqua.setPeriodicReadingDate(new Date());
		session.save(meterAcqua);

	}

	/**
	 * Inserisce nella tabella Meter_ripartitore_calore una lettura per ogni
	 * edificio esistente
	 * 
	 * @param session
	 */
	private void fillLetturaRipartitoreCalore(Session session) {
		int idMeter = 3; // ogni Meter Ripartitore Calore ha id = 3

		List<Edificio> ediList = session.createQuery("from Edificio").list();

		// Valori Casuali Unita di consumo
		Random unitaConsumo = new Random();

		for (int i = 0; i < ediList.size(); i++) {
			LetturaDispositivo lettDisp = generateLettura(session, idMeter,
					ediList.get(i));

			// INSERT Meter Ripartitore Calore
			MeterRipartitoreCalore ripMeterRipCal = new MeterRipartitoreCalore(
					lettDisp, unitaConsumo.nextInt(500));
			ripMeterRipCal.setIdLettura(lettDisp.getIdLettura());
			session.save(ripMeterRipCal);
		}
	}

	/**
	 * Genera una entry in LetturaDispositivo con data casuale.
	 * 
	 * @param session
	 * @param idDispositivo
	 * @param edificio
	 * @return
	 * 
	 */
	private LetturaDispositivo generateLettura(Session session,
			int idDispositivo, Edificio edificio) {

		// generazione Random Data
		Random generator = new Random();
		int day = generator.nextInt(30) + 1;
		int month = generator.nextInt(12);
		int hour = generator.nextInt(24);
		int minute = generator.nextInt(60);
		int second = generator.nextInt(60);
		Calendar cal = Calendar.getInstance();
		cal.set(2014, month, day, hour, minute, second);
		Date date = cal.getTime();

		// Creazione oggetto dispositivo senza richiamare una ulteriore query
		DispositivoId id = new DispositivoId(idDispositivo,
				edificio.getIdEdificio());
		Dispositivo dispositivo = new Dispositivo(id, edificio);

		// INSERT lettura dispositivo
		LetturaDispositivo lettDisp = new LetturaDispositivo(dispositivo, date);
		session.save(lettDisp);
		return lettDisp;
	}

	/**
	 * Genera una entry in LetturaDispositivo
	 * 
	 * @param session
	 * @param idDispositivo
	 * @param edificio
	 * @param date
	 * @return
	 */
	private LetturaDispositivo generateLettura(Session session,
			int idDispositivo, Edificio edificio, Date date) {

		// Creazione oggetto dispositivo senza richiamare una ulteriore query
		DispositivoId id = new DispositivoId(idDispositivo,
				edificio.getIdEdificio());
		Dispositivo dispositivo = new Dispositivo(id, edificio);

		// INSERT lettura dispositivo
		LetturaDispositivo lettDisp = new LetturaDispositivo(dispositivo, date);
		session.save(lettDisp);
		return lettDisp;
	}

	/**
	 * Elimina tutte le entry nelle tabelle
	 * 
	 * @param session
	 */
	private void flushTables(Session session) {
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
	 * genera un numero "range" di edifici casuali
	 * 
	 * @param session
	 * @param range
	 */
	private void fillEdificio(Session session, int range) {
		int RANGE_EDIFICI = range;
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
	 * Genera un numero "RANGE" di compartimenti random
	 * 
	 * @param session
	 * @param range
	 */
	private void fillCompartimento(Session session, int range) {
		// generatore di numeri causuali in RANGE
		int RANGE_COMPARTIMENTI = range;
		int RANGE = 100;
		final List<Integer> sack = new ArrayList<>(RANGE);
		for (int i = 0; i < RANGE; i++)
			sack.add(i);
		Collections.shuffle(sack);
		// riempimento compartimenti
		for (int i = 0; i < RANGE_COMPARTIMENTI; i++) {
			Compartimento comp = new Compartimento();
			comp.setIdCompartimento(sack.get(i));
			comp.setNomeCompartimento("Comp Number " + sack.get(i));
			// compList.add(comp);
			session.save(comp);
		}
	}
}