package com.rigers.db;

// Generated 7-apr-2014 11.05.46 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Edificio generated by hbm2java
 */
public class Edificio implements java.io.Serializable {

	private int idEdificio;
	private Compartimento compartimento;
	private String indirizzo;
	private Set dispositivos = new HashSet(0);

	public Edificio() {
	}

	public Edificio(int idEdificio, Compartimento compartimento) {
		this.idEdificio = idEdificio;
		this.compartimento = compartimento;
	}

	public Edificio(int idEdificio, Compartimento compartimento,
			String indirizzo, Set dispositivos) {
		this.idEdificio = idEdificio;
		this.compartimento = compartimento;
		this.indirizzo = indirizzo;
		this.dispositivos = dispositivos;
	}

	public int getIdEdificio() {
		return this.idEdificio;
	}

	public void setIdEdificio(int idEdificio) {
		this.idEdificio = idEdificio;
	}

	public Compartimento getCompartimento() {
		return this.compartimento;
	}

	public void setCompartimento(Compartimento compartimento) {
		this.compartimento = compartimento;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public Set getDispositivos() {
		return this.dispositivos;
	}

	public void setDispositivos(Set dispositivos) {
		this.dispositivos = dispositivos;
	}

}
