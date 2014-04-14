package com.rigers.GUI;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import com.ibm.icu.util.Calendar;
import com.rigers.API.MeterAcquaStats;
import com.rigers.API.MeterRipartitoreCaloreStats;
import com.rigers.db.Edificio;
import com.rigers.db.MeterAcqua;
import com.rigers.db.MeterRipartitoreCalore;
import com.rigers.main.DataView;

public class StatsGUI {

	protected Shell shell;
	private Composite composite;
	private Combo comboComp;
	private Combo comboEdif;
	private DateTime dateTime;
	private String[] compItems = DataView.CompItems();
	private String[] edifItems = DataView.EdifItems();
	private int year;
	private int month;
	private int date;
	private Edificio edificio;
	private Text textCRVavgMonth;
	private Text textCRVmaxMonth;
	private Text textCRVminMonth;
	private Text textCRVavgWeek;
	private Text textCRVmaxWeek;
	private Text textCRVminWeek;
	private Text textPRVavgMonth;
	private Text textPRVmaxMonth;
	private Text textPRVminMonth;
	private Text textPRVavgWeek;
	private Text textPRVavgDay;
	private Text textPRVmaxWeek;
	private Text textPRVminWeek;
	private Text textUCavgMonth;
	private Text textUCmaxMonth;
	private Text textUCminMonth;
	private Text textUCavgWeek;
	private Text textUCmaxWeek;
	private Text textUCminWeek;
	private Text textUCactual;
	private Text textCRVavgDay;
	private Text textCRVmaxDay;
	private Text textCRVminDay;
	private Text textPRVmaxDay;
	private Text textPRVminDay;
	private List listPRD;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StatsGUI window = new StatsGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setMinimumSize(new Point(800, 600));
		shell.setSize(450, 300);
		shell.setText("RIGERS - Statistiche");
		shell.setLayout(new GridLayout(1, false));

		composite = new Composite(shell, SWT.NONE);
		ViewDataTab();

	}

	private void ViewDataTab() {

		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.verticalSpacing = 0;
		gl_composite.marginWidth = 0;
		gl_composite.horizontalSpacing = 0;
		composite.setLayout(gl_composite);

		Group grpFilters = new Group(composite, SWT.NONE);
		grpFilters.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		grpFilters.setText("Filtri");
		grpFilters.setLayout(new GridLayout(4, false));

		Label lblComp = new Label(grpFilters, SWT.NONE);
		lblComp.setText("Compartimento");

		Label lblEdificio = new Label(grpFilters, SWT.NONE);
		lblEdificio.setText("Edificio");

		Label lblDa = new Label(grpFilters, SWT.NONE);
		lblDa.setText("Data:");
		new Label(grpFilters, SWT.NONE);

		comboComp = new Combo(grpFilters, SWT.READ_ONLY);
		comboComp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboComp.getText().equals("*")) {
					comboEdif.setItems(DataView.EdifItems());
				} else {
					comboEdif.setItems(DataView.EdifItems(comboComp.getText()));
				}
			}
		});
		GridData gd_comboComp = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_comboComp.widthHint = 150;
		comboComp.setLayoutData(gd_comboComp);
		comboComp.setItems(compItems);
		comboComp.add("*");

		comboEdif = new Combo(grpFilters, SWT.READ_ONLY);
		GridData gd_comboEdif = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_comboEdif.widthHint = 150;
		comboEdif.setLayoutData(gd_comboEdif);

		dateTime = new DateTime(grpFilters, SWT.BORDER);
		dateTime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false,
				1, 1));
		dateTime.setDate(2014, 0, 1);

		Button btnGo = new Button(grpFilters, SWT.NONE);
		btnGo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				year = dateTime.getYear();
				month = dateTime.getMonth();
				date = dateTime.getDay();
				edificio = new Edificio();
				edificio.setIdEdificio(Integer.parseInt(comboEdif.getText().substring(
						0, comboEdif.getText().indexOf(":"))));

				meterAcquaTab();
				meterRipartitoreCaloreTab();

			}
		});

		GridData gd_btnGo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1,
				1);
		gd_btnGo.widthHint = 70;
		btnGo.setLayoutData(gd_btnGo);
		btnGo.setText("GO");

		TabFolder tabResults = new TabFolder(composite, SWT.NONE);
		tabResults.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));

		TabItem tbtmMeterAcqua = new TabItem(tabResults, SWT.NONE);
		tbtmMeterAcqua.setText("Meter Acqua");

		TabItem tbtmMeterElettrico = new TabItem(tabResults, SWT.NONE);
		tbtmMeterElettrico.setText("Meter Elettrico");

		TabItem tbtmMeterGas = new TabItem(tabResults, SWT.NONE);
		tbtmMeterGas.setText("Meter Gas");

		Composite compositeTabMeterAcqua = new Composite(tabResults, SWT.NONE);
		tbtmMeterAcqua.setControl(compositeTabMeterAcqua);
		compositeTabMeterAcqua.setLayout(new GridLayout(5, false));
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);

		Label lblMedia = new Label(compositeTabMeterAcqua, SWT.NONE);
		GridData gd_lblMedia = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_lblMedia.widthHint = 100;
		lblMedia.setLayoutData(gd_lblMedia);
		lblMedia.setText("Media");

		Label lblMax = new Label(compositeTabMeterAcqua, SWT.NONE);
		GridData gd_lblMax = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_lblMax.widthHint = 100;
		lblMax.setLayoutData(gd_lblMax);
		lblMax.setText("Max");

		Label lblMin = new Label(compositeTabMeterAcqua, SWT.NONE);
		GridData gd_lblMin = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_lblMin.widthHint = 100;
		lblMin.setLayoutData(gd_lblMin);
		lblMin.setText("Min");

		Label lblCurrentReadoutValue = new Label(compositeTabMeterAcqua,
				SWT.NONE);
		lblCurrentReadoutValue.setText("Current Readout Value");
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);

		Label lblMese = new Label(compositeTabMeterAcqua, SWT.NONE);
		GridData gd_lblMese = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_lblMese.widthHint = 100;
		lblMese.setLayoutData(gd_lblMese);
		lblMese.setText("Mese");

		textCRVavgMonth = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textCRVavgMonth.setEditable(false);
		textCRVavgMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textCRVmaxMonth = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textCRVmaxMonth.setEditable(false);
		textCRVmaxMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textCRVminMonth = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textCRVminMonth.setEditable(false);
		textCRVminMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(compositeTabMeterAcqua, SWT.NONE);

		Label lblSettimana = new Label(compositeTabMeterAcqua, SWT.NONE);
		GridData gd_lblSettimana = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_lblSettimana.widthHint = 100;
		lblSettimana.setLayoutData(gd_lblSettimana);
		lblSettimana.setText("Settimana");

		textCRVavgWeek = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textCRVavgWeek.setEditable(false);
		textCRVavgWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textCRVmaxWeek = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textCRVmaxWeek.setEditable(false);
		textCRVmaxWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textCRVminWeek = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textCRVminWeek.setEditable(false);
		textCRVminWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(compositeTabMeterAcqua, SWT.NONE);

		Label lblDay = new Label(compositeTabMeterAcqua, SWT.NONE);
		lblDay.setText("Giorno");

		textCRVavgDay = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textCRVavgDay.setEditable(false);
		textCRVavgDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textCRVmaxDay = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textCRVmaxDay.setEditable(false);
		textCRVmaxDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textCRVminDay = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textCRVminDay.setEditable(false);
		textCRVminDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Label lblPeriodicReadoutValue = new Label(compositeTabMeterAcqua,
				SWT.NONE);
		lblPeriodicReadoutValue.setText("Periodic Readout Value");
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);

		Label lblMese_1 = new Label(compositeTabMeterAcqua, SWT.NONE);
		lblMese_1.setText("Mese");

		textPRVavgMonth = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVavgMonth.setEditable(false);
		textPRVavgMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textPRVmaxMonth = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVmaxMonth.setEditable(false);
		textPRVmaxMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textPRVminMonth = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVminMonth.setEditable(false);
		textPRVminMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(compositeTabMeterAcqua, SWT.NONE);

		Label lblSettimana_1 = new Label(compositeTabMeterAcqua, SWT.NONE);
		lblSettimana_1.setText("Settimana");

		textPRVavgWeek = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVavgWeek.setEditable(false);
		textPRVavgWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textPRVmaxWeek = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVmaxWeek.setEditable(false);
		textPRVmaxWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textPRVminWeek = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVminWeek.setEditable(false);
		textPRVminWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(compositeTabMeterAcqua, SWT.NONE);

		Label lblAttuale_1 = new Label(compositeTabMeterAcqua, SWT.NONE);
		lblAttuale_1.setText("Giorno");

		textPRVavgDay = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVavgDay.setEditable(false);
		textPRVavgDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textPRVmaxDay = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVmaxDay.setEditable(false);
		textPRVmaxDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textPRVminDay = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVminDay.setEditable(false);
		textPRVminDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Label lblPeriodicReadingDate = new Label(compositeTabMeterAcqua,
				SWT.NONE);
		lblPeriodicReadingDate.setText("Letture del giorno");
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		
				listPRD = new List(compositeTabMeterAcqua, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
				GridData gd_listPRD = new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1);
				gd_listPRD.heightHint = 70;
				listPRD.setLayoutData(gd_listPRD);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);


		TabItem tbtmMeterRipartitoreCalore = new TabItem(tabResults, SWT.NONE);
		tbtmMeterRipartitoreCalore.setText("Meter Ripartitore Calore");

		Composite compositeTabRipCalore = new Composite(tabResults, SWT.NONE);
		tbtmMeterRipartitoreCalore.setControl(compositeTabRipCalore);
		compositeTabRipCalore.setLayout(new GridLayout(5, false));
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);

		Label label = new Label(compositeTabRipCalore, SWT.NONE);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label.widthHint = 100;
		label.setLayoutData(gd_label);
		label.setText("Media");

		Label label_1 = new Label(compositeTabRipCalore, SWT.NONE);
		GridData gd_label_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_1.widthHint = 100;
		label_1.setLayoutData(gd_label_1);
		label_1.setText("Max");

		Label label_2 = new Label(compositeTabRipCalore, SWT.NONE);
		GridData gd_label_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_2.widthHint = 100;
		label_2.setLayoutData(gd_label_2);
		label_2.setText("Min");

		Label lblUnitaConsumo = new Label(compositeTabRipCalore, SWT.NONE);
		lblUnitaConsumo.setText("Unita Consumo");
		new Label(compositeTabRipCalore, SWT.NONE);

		Label label_4 = new Label(compositeTabRipCalore, SWT.NONE);
		GridData gd_label_4 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_label_4.widthHint = 100;
		label_4.setLayoutData(gd_label_4);
		label_4.setText("Mese");

		textUCavgMonth = new Text(compositeTabRipCalore, SWT.BORDER);
		textUCavgMonth.setEditable(false);
		textUCavgMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textUCmaxMonth = new Text(compositeTabRipCalore, SWT.BORDER);
		textUCmaxMonth.setEditable(false);
		textUCmaxMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textUCminMonth = new Text(compositeTabRipCalore, SWT.BORDER);
		textUCminMonth.setEditable(false);
		textUCminMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(compositeTabRipCalore, SWT.NONE);

		Label label_5 = new Label(compositeTabRipCalore, SWT.NONE);
		GridData gd_label_5 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_label_5.widthHint = 100;
		label_5.setLayoutData(gd_label_5);
		label_5.setText("Settimana");

		textUCavgWeek = new Text(compositeTabRipCalore, SWT.BORDER);
		textUCavgWeek.setEditable(false);
		textUCavgWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textUCmaxWeek = new Text(compositeTabRipCalore, SWT.BORDER);
		textUCmaxWeek.setEditable(false);
		textUCmaxWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		textUCminWeek = new Text(compositeTabRipCalore, SWT.BORDER);
		textUCminWeek.setEditable(false);
		textUCminWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(compositeTabRipCalore, SWT.NONE);

		Label label_6 = new Label(compositeTabRipCalore, SWT.NONE);
		label_6.setText("Attuale");

		textUCactual = new Text(compositeTabRipCalore, SWT.BORDER);
		textUCactual.setEditable(false);
		textUCactual.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);

		TabItem tbtmSonde = new TabItem(tabResults, SWT.NONE);
		tbtmSonde.setText("Sonde");

		TabItem tbtmMeterTermie = new TabItem(tabResults, SWT.NONE);
		tbtmMeterTermie.setText("Meter Termie");
	}

	private void meterAcquaTab() {
		MeterAcquaStats meterAcquaStats = new MeterAcquaStats(edificio);

		MeterAcqua avgMon = meterAcquaStats.monthAverage(year, month);
		MeterAcqua maxMon = meterAcquaStats.monthMax(year, month);
		MeterAcqua minMon = meterAcquaStats.monthMin(year, month);

		MeterAcqua avgWeek = meterAcquaStats.weekAverage(year, month, date);
		MeterAcqua maxWeek = meterAcquaStats.weekMax(year, month, date);
		MeterAcqua minWeek = meterAcquaStats.weekMin(year, month, date);

		MeterAcqua avgDay = meterAcquaStats.dayAverage(year, month, date);
		MeterAcqua maxDay = meterAcquaStats.dayMax(year, month, date);
		MeterAcqua minDay = meterAcquaStats.dayMin(year, month, date);

		textCRVavgMonth.setText(avgMon.getCurrentReadoutValue().toString());
		textCRVmaxMonth.setText(maxMon.getCurrentReadoutValue().toString());
		textCRVminMonth.setText(minMon.getCurrentReadoutValue().toString());

		textCRVminWeek.setText(minWeek.getCurrentReadoutValue().toString());
		textCRVmaxWeek.setText(maxWeek.getCurrentReadoutValue().toString());
		textCRVavgWeek.setText(avgWeek.getCurrentReadoutValue().toString());

		textCRVavgDay.setText(avgDay.getCurrentReadoutValue().toString());
		textCRVmaxDay.setText(maxDay.getCurrentReadoutValue().toString());
		textCRVminDay.setText(minDay.getCurrentReadoutValue().toString());

		textPRVavgMonth.setText(avgMon.getPeriodicReadoutValue().toString());
		textPRVmaxMonth.setText(maxMon.getPeriodicReadoutValue().toString());
		textPRVminMonth.setText(minMon.getPeriodicReadoutValue().toString());

		textPRVavgWeek.setText(avgWeek.getPeriodicReadoutValue().toString());
		textPRVmaxWeek.setText(maxWeek.getPeriodicReadoutValue().toString());
		textPRVminWeek.setText(minWeek.getPeriodicReadoutValue().toString());

		textPRVavgDay.setText(avgDay.getPeriodicReadoutValue().toString());
		textPRVmaxDay.setText(maxDay.getPeriodicReadoutValue().toString());
		textPRVminDay.setText(minDay.getPeriodicReadoutValue().toString());

		listPRD.setItems(meterAcquaStats.dayReadings(year, month, date));

	}

	protected void meterRipartitoreCaloreTab() {
		MeterRipartitoreCaloreStats meterRipCalStats = new MeterRipartitoreCaloreStats(edificio);

		MeterRipartitoreCalore actual = meterRipCalStats.actual(year, month, date);

		MeterRipartitoreCalore avgMonth = meterRipCalStats.monthAverage(month);
		MeterRipartitoreCalore maxMonth = meterRipCalStats.monthMax(month);
		MeterRipartitoreCalore minMonth = meterRipCalStats.monthMin(month);

		MeterRipartitoreCalore avgWeek = meterRipCalStats.weekAverage(year, month, date);
		MeterRipartitoreCalore maxWeek = meterRipCalStats.weekMax(year, month, date);
		MeterRipartitoreCalore minWeek = meterRipCalStats.weekMin(year, month, date);

		textUCactual.setText(actual.getUnitaConsumo().toString());

		textUCavgMonth.setText(avgMonth.getUnitaConsumo().toString());
		textUCmaxMonth.setText(maxMonth.getUnitaConsumo().toString());
		textUCminMonth.setText(minMonth.getUnitaConsumo().toString());

		textUCavgWeek.setText(avgWeek.getUnitaConsumo().toString());
		textUCmaxWeek.setText(maxWeek.getUnitaConsumo().toString());
		textUCminWeek.setText(minWeek.getUnitaConsumo().toString());


	}
}
