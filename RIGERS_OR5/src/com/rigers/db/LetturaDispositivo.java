package com.rigers.db;

// Generated 7-apr-2014 11.05.46 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * LetturaDispositivo generated by hbm2java
 */
public class LetturaDispositivo implements java.io.Serializable {

	private Integer idLettura;
	private Dispositivo dispositivo;
	private Date dataLettura;
	private MeterGas meterGas;
	private MeterSonde meterSonde;
	private MeterAcqua meterAcqua;
	private MeterElettrico meterElettrico;
	private MeterRipartitoreCalore meterRipartitoreCalore;
	private MeterTermie meterTermie;

	public LetturaDispositivo() {
	}

	public LetturaDispositivo(Dispositivo dispositivo, Date dataLettura) {
		this.dispositivo = dispositivo;
		this.dataLettura = dataLettura;
	}

	public LetturaDispositivo(Dispositivo dispositivo, Date dataLettura,
			MeterGas meterGas, MeterSonde meterSonde, MeterAcqua meterAcqua,
			MeterElettrico meterElettrico,
			MeterRipartitoreCalore meterRipartitoreCalore,
			MeterTermie meterTermie) {
		this.dispositivo = dispositivo;
		this.dataLettura = dataLettura;
		this.meterGas = meterGas;
		this.meterSonde = meterSonde;
		this.meterAcqua = meterAcqua;
		this.meterElettrico = meterElettrico;
		this.meterRipartitoreCalore = meterRipartitoreCalore;
		this.meterTermie = meterTermie;
	}

	public Integer getIdLettura() {
		return this.idLettura;
	}

	public void setIdLettura(Integer idLettura) {
		this.idLettura = idLettura;
	}

	public Dispositivo getDispositivo() {
		return this.dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public Date getDataLettura() {
		return this.dataLettura;
	}

	public void setDataLettura(Date dataLettura) {
		this.dataLettura = dataLettura;
	}

	public MeterGas getMeterGas() {
		return this.meterGas;
	}

	public void setMeterGas(MeterGas meterGas) {
		this.meterGas = meterGas;
	}

	public MeterSonde getMeterSonde() {
		return this.meterSonde;
	}

	public void setMeterSonde(MeterSonde meterSonde) {
		this.meterSonde = meterSonde;
	}

	public MeterAcqua getMeterAcqua() {
		return this.meterAcqua;
	}

	public void setMeterAcqua(MeterAcqua meterAcqua) {
		this.meterAcqua = meterAcqua;
	}

	public MeterElettrico getMeterElettrico() {
		return this.meterElettrico;
	}

	public void setMeterElettrico(MeterElettrico meterElettrico) {
		this.meterElettrico = meterElettrico;
	}

	public MeterRipartitoreCalore getMeterRipartitoreCalore() {
		return this.meterRipartitoreCalore;
	}

	public void setMeterRipartitoreCalore(
			MeterRipartitoreCalore meterRipartitoreCalore) {
		this.meterRipartitoreCalore = meterRipartitoreCalore;
	}

	public MeterTermie getMeterTermie() {
		return this.meterTermie;
	}

	public void setMeterTermie(MeterTermie meterTermie) {
		this.meterTermie = meterTermie;
	}

}
