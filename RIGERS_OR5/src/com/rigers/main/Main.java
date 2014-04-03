package com.rigers.main;

import java.util.List;

import com.rigers.GUI.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.rigers.db.Compartimento;
import com.rigers.db.LetturaDispositivo;
import com.rigers.db.LetturaDispositivoId;
import com.rigers.persistence.HibernateUtil;

public class Main {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
	GUI.main(null);
//		ShowEdifWindow.main(null);
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		session.beginTransaction();
//		
//		Query query = session.createSQLQuery("select * from lettura_dispositivo").addEntity(LetturaDispositivo.class);
//		List result = query.list();
//		
//		session.getTransaction().commit();
	}
}
