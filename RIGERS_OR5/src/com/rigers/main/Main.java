package com.rigers.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.rigers.db.Compartimento;
import com.rigers.persistence.HibernateUtil;

public class Main {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		DbManager.fillDb();
		
	}
}
