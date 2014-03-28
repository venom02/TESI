package com.rigers.main;

import com.rigers.GUI.*;
import com.rigers.GUI.main.GUI;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.rigers.db.Compartimento;
import com.rigers.persistence.HibernateUtil;

public class Main {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		//DbManager.fillDb();
		
		String idCompartimento = "4";
		String nomeCompartimento = "babau";
		
		DataInsert.insertComp(idCompartimento, nomeCompartimento);
		//GUI.main(null);	
	}
}
