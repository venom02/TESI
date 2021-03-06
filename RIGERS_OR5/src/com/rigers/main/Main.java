package com.rigers.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rigers.API.*;
import com.rigers.GUI.AdminUI;
import com.rigers.GUI.UserUI;
import com.rigers.db.Dispositivo;
import com.rigers.db.DispositivoId;
import com.rigers.db.Edificio;
import com.rigers.db.LetturaDispositivo;
import com.rigers.db.MeterAcqua;
import com.rigers.persistence.HibernateUtil;

public class Main {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
//		AdminUI.main(null);
		UserUI.main(null);
//		fillMonth(0);
//		fillMonth(1);
//		fillDb();
		System.exit(0);
	}

	public static void fillMonth(int month) {
		DbManager db = new DbManager();
		db.fillMonth(month);
	};

	public static void fillDb() {
		DbManager db = new DbManager();
		db.fillDb();
	}

}