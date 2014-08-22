package com.rigers.API;

import java.util.Date;
import java.util.List;
import java.util.Random;

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
	 * @return Boolean. ritorna true se l'inserimento e' andato a buon fine.
	 */
	public static boolean insertComp(String idCompartimento,
			String nomeCompartimento) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		int value = Integer.parseInt(idCompartimento);

		List<Compartimento> compList = session
				.createQuery("from Compartimento").list();

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

	/**
	 * Inserisce un edificio nel database
	 * 
	 * @param idEdificio
	 *            id dell'Edificio
	 * @param indexCompartimento
	 *            indice nella lista Compartimenti
	 * @param indirizzo
	 *            indirizzo dell'edificio
	 * @return True se l'edificio non esiste e False se l'edificio è già
	 *         esistente
	 */
	public static boolean insertEdificio(String idEdificio,
			int indexCompartimento, String indirizzo) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		/**
		 * Check esistenza IdEdificio
		 */
		int idEdifInt = Integer.parseInt(idEdificio);
		List<Edificio> ediList = session.createQuery("from Edificio").list();
		List<Compartimento> compList = session
				.createQuery("from Compartimento").list();

		for (int i = 0; i < ediList.size(); i++) {
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

		Compartimento compartimento = ediList.get(indexCompartimento)
				.getCompartimento();
		System.out.println("Compartimento "
				+ compartimento.getIdCompartimento());
		edificio.setCompartimento(compartimento);

		session.save(edificio);
		session.getTransaction().commit();
		return true;
	}

	/**
	 * Inserisce una lettura in Ripartitore Calore
	 * 
	 * @param indexEdificio
	 *            Indice della lista Edifici
	 * @param date
	 *            Data Inserimento
	 * @param unitaConsumo
	 *            Valore
	 * @return true
	 */
	public static boolean insertMeterRipCal(int indexEdificio, Date date,
			int unitaConsumo) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		int idMeter = 3; // ogni Meter Ripartitore Calore ha id = 3

		List<Edificio> ediList = session.createQuery("from Edificio").list();

		LetturaDispositivo lettDisp = generateLettura(session, idMeter,
				ediList.get(indexEdificio), date);

		// INSERT Meter Ripartitore Calore
		MeterRipartitoreCalore ripMeterRipCal = new MeterRipartitoreCalore(
				lettDisp, unitaConsumo);
		ripMeterRipCal.setIdLettura(lettDisp.getIdLettura());
		session.save(ripMeterRipCal);

		session.getTransaction().commit();
		return true;
	}

	/**
	 * Inserisce una lettura in Meter Acqua
	 * 
	 * @param indexEdificio
	 * @param date
	 * @param crvVal
	 * @param prvVal
	 * @param prdVal
	 * @return
	 */
	public static boolean insertMeterAcqua(int indexEdificio, Date date,
			int crvVal, int prvVal, Date prdVal) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		int idMeter = 0; // Id meter acqua sempre uguale a 0;

		List<Edificio> ediList = session.createQuery("from Edificio").list();

		LetturaDispositivo lettDisp = generateLettura(session, idMeter,
				ediList.get(indexEdificio), date);

		// INSERT MeterAcqua
		MeterAcqua meterAcqua = new MeterAcqua(lettDisp);
		meterAcqua.setIdLettura(lettDisp.getIdLettura());
		meterAcqua.setCurrentReadoutValue(crvVal);
		meterAcqua.setPeriodicReadoutValue(prvVal);
		meterAcqua.setPeriodicReadingDate(prdVal);

		session.save(meterAcqua);
		session.getTransaction().commit();
		return true;
	}

	/**
	 * Inserisce una lettura in Meter Sonde
	 * 
	 * @param indexEdificio
	 * @param date
	 * @param lum
	 * @param sis
	 * @param tExt
	 * @param tLoc
	 * @param tDay
	 * @param sol
	 * @return
	 */
	public static boolean insertMeterSonde(int indexEdificio, Date date,
			int lum, int sis, int tExt, int tLoc, int tDay, int sol) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		int idMeter = 4; // Id meter sonde sempre uguale a 4;

		List<Edificio> ediList = session.createQuery("from Edificio").list();

		LetturaDispositivo lettDisp = generateLettura(session, idMeter,
				ediList.get(indexEdificio), date);

		// INSERT Ripartitore Calore
		MeterSonde meterSonde = new MeterSonde(lettDisp);
		meterSonde.setIdLettura(lettDisp.getIdLettura());
		meterSonde.setLuminosita(lum);
		meterSonde.setSismografo(sis);
		meterSonde.setTempEsterna(tExt);
		meterSonde.setTempLocali(tLoc);
		meterSonde.setTempGiorno(tDay);
		meterSonde.setSolarimetro(sol);

		session.save(meterSonde);
		session.getTransaction().commit();
		return true;
	}

	/**
	 *  Inserisce una lettura in Meter Elettrico
	 * @return
	 */
	public static boolean insertMeterElettrico() {
		return true;
	}

	/**
	 *  Inserisce una lettura in Meter Gas
	 * @return
	 */
	public static boolean insertMeterGas() {
		return true;
	}

	/**
	 *  Inserisce una lettura in Meter Termie
	 * @return
	 */
	public static boolean insertMeterTermie() {
		return true;
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
	private static LetturaDispositivo generateLettura(Session session,
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
}