package com.rigers.API;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rigers.db.Compartimento;
import com.rigers.db.Dispositivo;
import com.rigers.db.Edificio;
import com.rigers.db.LetturaDispositivo;
import com.rigers.persistence.HibernateUtil;

public class DataView {

	public static String[] CompItems() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Compartimento> compList = session
				.createQuery("from Compartimento").list();

		String[] compItems = new String[compList.size()];
		for (int i = 0; i < compList.size(); i++) {
			compItems[i] = Integer.toString(compList.get(i)
					.getIdCompartimento())
					+ ": "
					+ compList.get(i).getNomeCompartimento();
		}
		session.getTransaction().commit();
		return compItems;
	}

	public static String[] EdifItems() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Edificio> edifList = session.createQuery("from Edificio").list();

		String[] edifItems = new String[edifList.size()];
		for (int i = 0; i < edifList.size(); i++) {
			edifItems[i] = Integer.toString(edifList.get(i).getIdEdificio())
					+ ": " + edifList.get(i).getIndirizzo();
		}
		session.getTransaction().commit();
		return edifItems;
	}

	public static String[] LettureItems(int edifId, int dispId, Date dateFrom,
			Date dateTo) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query = session
				.createQuery("from LetturaDispositivo where dataLettura > :dateFrom and dataLettura < :dateTo and idEdificio = :edifId and idDispositivo = :dispId order by dataLettura desc");
		query.setParameter("dispId", dispId);
		query.setParameter("edifId", edifId);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateTo", dateTo);
		List<LetturaDispositivo> lettList = query.list();

		String[] lettItems = new String[lettList.size()];
		for (int i = 0; i < lettList.size(); i++) {
			lettItems[i] = "Data: "
					+ lettList.get(i).getDataLettura().toString()
					+ "\t Edificio: "
					+ lettList.get(i).getDispositivo().getEdificio()
							.getIndirizzo() + "\t\t     Dispositivo: "
					+ lettList.get(i).getDispositivo().getNomeDispositivo();
		}

		session.getTransaction().commit();
		return lettItems;
	}

	/**
	 * Genera lista edifici appartenenti ad Compartimento con idComp dato
	 * 
	 * @param idComp
	 * @return
	 */
	public static String[] EdifItems(String idComp) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		int id = Integer.parseInt(idComp.substring(0, idComp.indexOf(":")));
		Query query = session
				.createQuery("from Edificio where idCompartimento = :id");
		query.setParameter("id", id);
		List<Edificio> edifList = query.list();

		String[] edifItems = new String[edifList.size()];
		for (int i = 0; i < edifList.size(); i++) {
			edifItems[i] = Integer.toString(edifList.get(i).getIdEdificio())
					+ ": " + edifList.get(i).getIndirizzo();
		}
		session.getTransaction().commit();
		return edifItems;
	}

	/**
	 * Riporta stringa Letture Dispositivi senza avere selezionato l'edificio
	 * @param dispId
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public static String[] LettureItems(int dispId, Date dateFrom, Date dateTo) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query = session
				.createQuery("from LetturaDispositivo "
						+ "where dataLettura > :dateFrom "
						+ "and dataLettura < :dateTo "
						+ "and idDispositivo = :dispId "
						+ "order by dataLettura desc");
		query.setParameter("dispId", dispId);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateTo", dateTo);
		List<LetturaDispositivo> lettList = query.list();

		String[] lettItems = new String[lettList.size()];
		for (int i = 0; i < lettList.size(); i++) {
			lettItems[i] = "Data: "
					+ lettList.get(i).getDataLettura().toString()
					+ "\t Edificio: "
					+ lettList.get(i).getDispositivo().getEdificio()
							.getIndirizzo() + "\t\t     Dispositivo: "
					+ lettList.get(i).getDispositivo().getNomeDispositivo();
		}

		session.getTransaction().commit();
		return lettItems;
	}

	public static String[] LettureItems(Date dateFrom, Date dateTo) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query = session
				.createQuery("from LetturaDispositivo where dataLettura > :dateFrom and dataLettura < :dateTo order by dataLettura desc");
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateTo", dateTo);
		List<LetturaDispositivo> lettList = query.list();

		String[] lettItems = new String[lettList.size()];
		for (int i = 0; i < lettList.size(); i++) {
			lettItems[i] = "Data: "
					+ lettList.get(i).getDataLettura().toString()
					+ "\t Edificio: "
					+ lettList.get(i).getDispositivo().getEdificio()
							.getIndirizzo() + "\t\t     Dispositivo: "
					+ lettList.get(i).getDispositivo().getNomeDispositivo();
		}

		session.getTransaction().commit();
		return lettItems;
	}
}