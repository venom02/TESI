package com.rigers.GUI;

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
import com.rigers.db.Edificio;
import com.rigers.db.MeterAcqua;
import com.rigers.main.DataView;

public class StatsGUI {

	protected Shell shell;
	private Composite composite;
	private Combo comboComp;
	private Combo comboEdif;
	private DateTime dateTime;
	private String[] compItems = DataView.CompItems();
	private String[] edifItems = DataView.EdifItems();
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
	private Text textCRVactual;
	private Text textPRVactual;
	private Text textPRDactual;
	private Text textPRVmaxWeek;
	private Text textPRVminWeek;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;

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
		shell.setText("SWT Application");
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
				meterAcquaTab();

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

		Label lblAttuale = new Label(compositeTabMeterAcqua, SWT.NONE);
		lblAttuale.setText("Attuale");

		textCRVactual = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textCRVactual.setEditable(false);
		textCRVactual.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);

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
		lblAttuale_1.setText("Attuale");

		textPRVactual = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVactual.setEditable(false);
		textPRVactual.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);

		Label lblPeriodicReadingDate = new Label(compositeTabMeterAcqua,
				SWT.NONE);
		lblPeriodicReadingDate.setText("Periodic Reading Date");
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);

		Label lblAttuale_2 = new Label(compositeTabMeterAcqua, SWT.NONE);
		lblAttuale_2.setText("Attuale");

		textPRDactual = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRDactual.setEditable(false);
		textPRDactual.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 2, 1));
		new Label(compositeTabMeterAcqua, SWT.NONE);
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
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);
		new Label(compositeTabRipCalore, SWT.NONE);
		
		Label label_4 = new Label(compositeTabRipCalore, SWT.NONE);
		GridData gd_label_4 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_label_4.widthHint = 100;
		label_4.setLayoutData(gd_label_4);
		label_4.setText("Mese");
		
		text = new Text(compositeTabRipCalore, SWT.BORDER);
		text.setEditable(false);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		text_1 = new Text(compositeTabRipCalore, SWT.BORDER);
		text_1.setEditable(false);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		text_2 = new Text(compositeTabRipCalore, SWT.BORDER);
		text_2.setEditable(false);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(compositeTabRipCalore, SWT.NONE);
		
		Label label_5 = new Label(compositeTabRipCalore, SWT.NONE);
		GridData gd_label_5 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_label_5.widthHint = 100;
		label_5.setLayoutData(gd_label_5);
		label_5.setText("Settimana");
		
		text_3 = new Text(compositeTabRipCalore, SWT.BORDER);
		text_3.setEditable(false);
		text_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		text_4 = new Text(compositeTabRipCalore, SWT.BORDER);
		text_4.setEditable(false);
		text_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		text_5 = new Text(compositeTabRipCalore, SWT.BORDER);
		text_5.setEditable(false);
		text_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(compositeTabRipCalore, SWT.NONE);
		
		Label label_6 = new Label(compositeTabRipCalore, SWT.NONE);
		label_6.setText("Attuale");
		
		text_6 = new Text(compositeTabRipCalore, SWT.BORDER);
		text_6.setEditable(false);
		text_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
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
		int year = dateTime.getYear();
		int month = dateTime.getMonth();
		int date = dateTime.getDay();
		Edificio edificio = new Edificio();
		edificio.setIdEdificio(Integer.parseInt(comboEdif.getText().substring(
				0, comboEdif.getText().indexOf(":"))));

		MeterAcquaStats meterAcquaStats = new MeterAcquaStats(edificio);
		
		MeterAcqua actual = meterAcquaStats.actual(year, month, date);
		
		MeterAcqua avgMon = meterAcquaStats.monthAverage(month);
		MeterAcqua maxMon = meterAcquaStats.monthMax(month);
		MeterAcqua minMon = meterAcquaStats.monthMin(month);
		
		MeterAcqua avgWeek = meterAcquaStats.weekAverage(year, month, date);
		MeterAcqua maxWeek = meterAcquaStats.weekMax(year, month, date);
		MeterAcqua minWeek = meterAcquaStats.weekMin(year, month, date);

		textCRVavgMonth.setText(avgMon.getCurrentReadoutValue().toString());
		textCRVmaxMonth.setText(maxMon.getCurrentReadoutValue().toString());
		textCRVminMonth.setText(minMon.getCurrentReadoutValue().toString());

		textCRVminWeek.setText(minWeek.getCurrentReadoutValue().toString());
		textCRVmaxWeek.setText(maxWeek.getCurrentReadoutValue().toString());
		textCRVavgWeek.setText(avgWeek.getCurrentReadoutValue().toString());
		
		textPRVavgMonth.setText(avgMon.getPeriodicReadoutValue().toString());
		textPRVmaxMonth.setText(maxMon.getPeriodicReadoutValue().toString());
		textPRVminMonth.setText(minMon.getPeriodicReadoutValue().toString());
		
		textPRVavgWeek.setText(avgWeek.getPeriodicReadoutValue().toString());
		textPRVmaxWeek.setText(maxWeek.getPeriodicReadoutValue().toString());
		textPRVminWeek.setText(minWeek.getPeriodicReadoutValue().toString());
		
		textCRVactual.setText(actual.getCurrentReadoutValue().toString());
		textPRVactual.setText(actual.getPeriodicReadoutValue().toString());
		textPRDactual.setText(actual.getPeriodicReadingDate().toString());
	}
}
