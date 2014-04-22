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
import org.eclipse.swt.custom.ScrolledComposite;
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
import com.rigers.API.MeterSondeStats;
import com.rigers.db.Edificio;
import com.rigers.db.MeterAcqua;
import com.rigers.db.MeterRipartitoreCalore;
import com.rigers.db.MeterSonde;
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
	private Text textCRVavgDay;
	private Text textCRVmaxDay;
	private Text textCRVminDay;
	private Text textPRVmaxDay;
	private Text textPRVminDay;
	private List listMeterAcqua;
	private Text textUCavgMonth;
	private Text textUCmaxMonth;
	private Text textUCminMonth;
	private Text textUCavgWeek;
	private Text textUCmaxWeek;
	private Text textUCminWeek;
	private Text textUCavgDay;
	private Text textUCmaxDay;
	private Text textUCminDay;
	private List listRipCal;
	private Text textLUMavgMonth;
	private Text textLUMmaxMonth;
	private Text textLUMminMonth;
	private Text textLUMavgWeek;
	private Text textLUMmaxWeek;
	private Text textLUMminWeek;
	private Text textLUMavgDay;
	private Text textLUMmaxDay;
	private Text textLUMminDay;
	private Text textSISavgMonth;
	private Text textSISmaxMonth;
	private Text textSISminMonth;
	private Text textSISavgWeek;
	private Text textSISmaxWeek;
	private Text textSISminWeek;
	private Text textSISavgDay;
	private Text textSISmaxDay;
	private Text textSISminDay;
	private Label lblSolarimetro;
	private Label label_14;
	private Label label_19;
	private Label label_20;
	private Text textSOLavgMonth;
	private Text textSOLavgWeek;
	private Text textSOLavgDay;
	private Text textSOLmaxMonth;
	private Text textSOLmaxWeek;
	private Text textSOLmaxDay;
	private Text textSOLminMonth;
	private Text textSOLminWeek;
	private Text textSOLminDay;
	private Label label_9;
	private Label label_24;
	private Label label_25;
	private Label label_26;
	private Label lblTemperaturaInterna;
	private Label label_28;
	private Label label_29;
	private Label label_30;
	private Label lblTemperaturaGiorno;
	private Label label_32;
	private Label label_33;
	private Label label_34;
	private Text textTEXavgMonth;
	private Text textTEXavgWeek;
	private Text textTEXavgDay;
	private Text textTINavgMonth;
	private Text textTINavgWeek;
	private Text textTINavgDay;
	private Text textTDYavgMonth;
	private Text textTDYavgWeek;
	private Text textTDYavgDay;
	private Text textTEXmaxMonth;
	private Text textTEXmaxWeek;
	private Text textTEXmaxDay;
	private Text textTINmaxMonth;
	private Text textTINmaxWeek;
	private Text textTINmaxDay;
	private Text textTDYmaxMonth;
	private Text textTDYmaxWeek;
	private Text textTDYmaxDay;
	private Text textTEXminMonth;
	private Text textTEXminWeek;
	private Text textTEXminDay;
	private Text textTINminMonth;
	private Text textTINminWeek;
	private Text textTINminDay;
	private Text textTDYminMonth;
	private Text textTDYminWeek;
	private Text textTDYminDay;
	private List listMeterSonde;
	private ScrolledComposite scrolled_composite_2;
	private TabFolder tabResults;

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
		shell.setSize(800, 600);
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
				edificio.setIdEdificio(Integer.parseInt(comboEdif.getText()
						.substring(0, comboEdif.getText().indexOf(":"))));

				buttonGoActions();

			}
		});

		GridData gd_btnGo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1,
				1);
		gd_btnGo.widthHint = 70;
		btnGo.setLayoutData(gd_btnGo);
		btnGo.setText("GO");

		tabResults = new TabFolder(composite, SWT.NONE);
		tabResults.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));

		tabMeterAcqua();
		
		tabMeterRipartitoreCalore();
		
		tabMeterSonde();

		

		TabItem tbtmMeterTermie = new TabItem(tabResults, SWT.NONE);
		tbtmMeterTermie.setText("Meter Termie");

		TabItem tbtmMeterElettrico = new TabItem(tabResults, SWT.NONE);
		tbtmMeterElettrico.setText("Meter Elettrico");

		TabItem tbtmMeterGas = new TabItem(tabResults, SWT.NONE);
		tbtmMeterGas.setText("Meter Gas");
	}

	private void tabMeterSonde() {
		TabItem tbtmSonde = new TabItem(tabResults, SWT.NONE);
		tbtmSonde.setText("Sonde");

		scrolled_composite_2 = new ScrolledComposite(tabResults, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		scrolled_composite_2.setExpandHorizontal(true);
		scrolled_composite_2.setExpandVertical(true);

		Composite composite_2 = new Composite(tabResults, SWT.NONE);
		composite_2.setLayout(new GridLayout(5, false));
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		tbtmSonde.setControl(composite_2);
		scrolled_composite_2.setContent(composite_2);

		Label label_3 = new Label(composite_2, SWT.NONE);
		GridData gd_label_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_label_3.widthHint = 100;
		label_3.setLayoutData(gd_label_3);
		label_3.setText("Media");

		Label label_7 = new Label(composite_2, SWT.NONE);
		GridData gd_label_7 = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_label_7.widthHint = 100;
		label_7.setLayoutData(gd_label_7);
		label_7.setText("Max");

		Label label_8 = new Label(composite_2, SWT.NONE);
		GridData gd_label_8 = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_label_8.widthHint = 100;
		label_8.setLayoutData(gd_label_8);
		label_8.setText("Min");

		Label lblLuminosita = new Label(composite_2, SWT.NONE);
		lblLuminosita.setText("Luminosita");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		Label label_10 = new Label(composite_2, SWT.NONE);
		GridData gd_label_10 = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_label_10.widthHint = 100;
		label_10.setLayoutData(gd_label_10);
		label_10.setText("Mese");

		textLUMavgMonth = new Text(composite_2, SWT.BORDER);
		textLUMavgMonth.setEditable(false);
		textLUMavgMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textLUMmaxMonth = new Text(composite_2, SWT.BORDER);
		textLUMmaxMonth.setEditable(false);
		textLUMmaxMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textLUMminMonth = new Text(composite_2, SWT.BORDER);
		textLUMminMonth.setEditable(false);
		textLUMminMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_2, SWT.NONE);

		Label label_12 = new Label(composite_2, SWT.NONE);
		GridData gd_label_12 = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_label_12.widthHint = 100;
		label_12.setLayoutData(gd_label_12);
		label_12.setText("Settimana");

		textLUMavgWeek = new Text(composite_2, SWT.BORDER);
		textLUMavgWeek.setEditable(false);
		textLUMavgWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textLUMmaxWeek = new Text(composite_2, SWT.BORDER);
		textLUMmaxWeek.setEditable(false);
		textLUMmaxWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textLUMminWeek = new Text(composite_2, SWT.BORDER);
		textLUMminWeek.setEditable(false);
		textLUMminWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_2, SWT.NONE);

		Label label_13 = new Label(composite_2, SWT.NONE);
		label_13.setText("Giorno");

		textLUMavgDay = new Text(composite_2, SWT.BORDER);
		textLUMavgDay.setEditable(false);
		textLUMavgDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textLUMmaxDay = new Text(composite_2, SWT.BORDER);
		textLUMmaxDay.setEditable(false);
		textLUMmaxDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textLUMminDay = new Text(composite_2, SWT.BORDER);
		textLUMminDay.setEditable(false);
		textLUMminDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		Label lblSismografo = new Label(composite_2, SWT.NONE);
		lblSismografo.setText("Sismografo");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		Label label_15 = new Label(composite_2, SWT.NONE);
		label_15.setText("Mese");

		textSISavgMonth = new Text(composite_2, SWT.BORDER);
		textSISavgMonth.setEditable(false);
		textSISavgMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textSISmaxMonth = new Text(composite_2, SWT.BORDER);
		textSISmaxMonth.setEditable(false);
		textSISmaxMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textSISminMonth = new Text(composite_2, SWT.BORDER);
		textSISminMonth.setEditable(false);
		textSISminMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_2, SWT.NONE);

		Label label_16 = new Label(composite_2, SWT.NONE);
		label_16.setText("Settimana");

		textSISavgWeek = new Text(composite_2, SWT.BORDER);
		textSISavgWeek.setEditable(false);
		textSISavgWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textSISmaxWeek = new Text(composite_2, SWT.BORDER);
		textSISmaxWeek.setEditable(false);
		textSISmaxWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textSISminWeek = new Text(composite_2, SWT.BORDER);
		textSISminWeek.setEditable(false);
		textSISminWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_2, SWT.NONE);

		Label label_17 = new Label(composite_2, SWT.NONE);
		label_17.setText("Giorno");

		textSISavgDay = new Text(composite_2, SWT.BORDER);
		textSISavgDay.setEditable(false);
		textSISavgDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textSISmaxDay = new Text(composite_2, SWT.BORDER);
		textSISmaxDay.setEditable(false);
		textSISmaxDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textSISminDay = new Text(composite_2, SWT.BORDER);
		textSISminDay.setEditable(false);
		textSISminDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		lblSolarimetro = new Label(composite_2, SWT.NONE);
		lblSolarimetro.setText("Solarimetro");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		label_14 = new Label(composite_2, SWT.NONE);
		label_14.setText("Mese");

		textSOLavgMonth = new Text(composite_2, SWT.BORDER);
		textSOLavgMonth.setEditable(false);
		textSOLavgMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textSOLmaxMonth = new Text(composite_2, SWT.BORDER);
		textSOLmaxMonth.setEditable(false);
		textSOLmaxMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textSOLminMonth = new Text(composite_2, SWT.BORDER);
		textSOLminMonth.setEditable(false);
		textSOLminMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_2, SWT.NONE);

		label_19 = new Label(composite_2, SWT.NONE);
		label_19.setText("Settimana");

		textSOLavgWeek = new Text(composite_2, SWT.BORDER);
		textSOLavgWeek.setEditable(false);
		textSOLavgWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textSOLmaxWeek = new Text(composite_2, SWT.BORDER);
		textSOLmaxWeek.setEditable(false);
		textSOLmaxWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textSOLminWeek = new Text(composite_2, SWT.BORDER);
		textSOLminWeek.setEditable(false);
		textSOLminWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_2, SWT.NONE);

		label_20 = new Label(composite_2, SWT.NONE);
		label_20.setText("Giorno");

		textSOLavgDay = new Text(composite_2, SWT.BORDER);
		textSOLavgDay.setEditable(false);
		textSOLavgDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textSOLmaxDay = new Text(composite_2, SWT.BORDER);
		textSOLmaxDay.setEditable(false);
		textSOLmaxDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textSOLminDay = new Text(composite_2, SWT.BORDER);
		textSOLminDay.setEditable(false);
		textSOLminDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		label_9 = new Label(composite_2, SWT.NONE);
		label_9.setText("Temperatura Esterna");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		label_24 = new Label(composite_2, SWT.NONE);
		label_24.setText("Mese");

		textTEXavgMonth = new Text(composite_2, SWT.BORDER);
		textTEXavgMonth.setEditable(false);
		textTEXavgMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTEXmaxMonth = new Text(composite_2, SWT.BORDER);
		textTEXmaxMonth.setEditable(false);
		textTEXmaxMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTEXminMonth = new Text(composite_2, SWT.BORDER);
		textTEXminMonth.setEditable(false);
		textTEXminMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_2, SWT.NONE);

		label_25 = new Label(composite_2, SWT.NONE);
		label_25.setText("Settimana");

		textTEXavgWeek = new Text(composite_2, SWT.BORDER);
		textTEXavgWeek.setEditable(false);
		textTEXavgWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTEXmaxWeek = new Text(composite_2, SWT.BORDER);
		textTEXmaxWeek.setEditable(false);
		textTEXmaxWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTEXminWeek = new Text(composite_2, SWT.BORDER);
		textTEXminWeek.setEditable(false);
		textTEXminWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_2, SWT.NONE);

		label_26 = new Label(composite_2, SWT.NONE);
		label_26.setText("Giorno");

		textTEXavgDay = new Text(composite_2, SWT.BORDER);
		textTEXavgDay.setEditable(false);
		textTEXavgDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTEXmaxDay = new Text(composite_2, SWT.BORDER);
		textTEXmaxDay.setEditable(false);
		textTEXmaxDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTEXminDay = new Text(composite_2, SWT.BORDER);
		textTEXminDay.setEditable(false);
		textTEXminDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		lblTemperaturaInterna = new Label(composite_2, SWT.NONE);
		lblTemperaturaInterna.setText("Temperatura Locali");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		label_28 = new Label(composite_2, SWT.NONE);
		label_28.setText("Mese");

		textTINavgMonth = new Text(composite_2, SWT.BORDER);
		textTINavgMonth.setEditable(false);
		textTINavgMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTINmaxMonth = new Text(composite_2, SWT.BORDER);
		textTINmaxMonth.setEditable(false);
		textTINmaxMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTINminMonth = new Text(composite_2, SWT.BORDER);
		textTINminMonth.setEditable(false);
		textTINminMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_2, SWT.NONE);

		label_29 = new Label(composite_2, SWT.NONE);
		label_29.setText("Settimana");

		textTINavgWeek = new Text(composite_2, SWT.BORDER);
		textTINavgWeek.setEditable(false);
		textTINavgWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTINmaxWeek = new Text(composite_2, SWT.BORDER);
		textTINmaxWeek.setEditable(false);
		textTINmaxWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTINminWeek = new Text(composite_2, SWT.BORDER);
		textTINminWeek.setEditable(false);
		textTINminWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_2, SWT.NONE);

		label_30 = new Label(composite_2, SWT.NONE);
		label_30.setText("Giorno");

		textTINavgDay = new Text(composite_2, SWT.BORDER);
		textTINavgDay.setEditable(false);
		textTINavgDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTINmaxDay = new Text(composite_2, SWT.BORDER);
		textTINmaxDay.setEditable(false);
		textTINmaxDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTINminDay = new Text(composite_2, SWT.BORDER);
		textTINminDay.setEditable(false);
		textTINminDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		lblTemperaturaGiorno = new Label(composite_2, SWT.NONE);
		lblTemperaturaGiorno.setText("Temperatura Giorno");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		label_32 = new Label(composite_2, SWT.NONE);
		label_32.setText("Mese");

		textTDYavgMonth = new Text(composite_2, SWT.BORDER);
		textTDYavgMonth.setEditable(false);
		textTDYavgMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTDYmaxMonth = new Text(composite_2, SWT.BORDER);
		textTDYmaxMonth.setEditable(false);
		textTDYmaxMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTDYminMonth = new Text(composite_2, SWT.BORDER);
		textTDYminMonth.setEditable(false);
		textTDYminMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_2, SWT.NONE);

		label_33 = new Label(composite_2, SWT.NONE);
		label_33.setText("Settimana");

		textTDYavgWeek = new Text(composite_2, SWT.BORDER);
		textTDYavgWeek.setEditable(false);
		textTDYavgWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTDYmaxWeek = new Text(composite_2, SWT.BORDER);
		textTDYmaxWeek.setEditable(false);
		textTDYmaxWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTDYminWeek = new Text(composite_2, SWT.BORDER);
		textTDYminWeek.setEditable(false);
		textTDYminWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_2, SWT.NONE);

		label_34 = new Label(composite_2, SWT.NONE);
		label_34.setText("Giorno");

		textTDYavgDay = new Text(composite_2, SWT.BORDER);
		textTDYavgDay.setEditable(false);
		textTDYavgDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTDYmaxDay = new Text(composite_2, SWT.BORDER);
		textTDYmaxDay.setEditable(false);
		textTDYmaxDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textTDYminDay = new Text(composite_2, SWT.BORDER);
		textTDYminDay.setEditable(false);
		textTDYminDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		Label label_18 = new Label(composite_2, SWT.NONE);
		label_18.setText("Letture del giorno");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		listMeterSonde = new List(composite_2, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL);
		GridData gd_listMeterSonde = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 5, 1);
		gd_listMeterSonde.heightHint = 70;
		listMeterSonde.setLayoutData(gd_listMeterSonde);
	}

	private void tabMeterRipartitoreCalore() {
		TabItem tbtmMeterRipartitoreCalore = new TabItem(tabResults, SWT.NONE);
		tbtmMeterRipartitoreCalore.setText("Meter Ripartitore Calore");

		Composite composite_1 = new Composite(tabResults, SWT.NONE);
		tbtmMeterRipartitoreCalore.setControl(composite_1);
		composite_1.setLayout(new GridLayout(5, false));
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		Label label = new Label(composite_1, SWT.NONE);
		GridData gd_label = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1,
				1);
		gd_label.widthHint = 100;
		label.setLayoutData(gd_label);
		label.setText("Media");

		Label label_1 = new Label(composite_1, SWT.NONE);
		GridData gd_label_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_label_1.widthHint = 100;
		label_1.setLayoutData(gd_label_1);
		label_1.setText("Max");

		Label label_2 = new Label(composite_1, SWT.NONE);
		GridData gd_label_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_label_2.widthHint = 100;
		label_2.setLayoutData(gd_label_2);
		label_2.setText("Min");

		Label lblUnitaConsumo = new Label(composite_1, SWT.NONE);
		lblUnitaConsumo.setText("Unita Consumo");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		Label label_4 = new Label(composite_1, SWT.NONE);
		GridData gd_label_4 = new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1);
		gd_label_4.widthHint = 100;
		label_4.setLayoutData(gd_label_4);
		label_4.setText("Mese");

		textUCavgMonth = new Text(composite_1, SWT.BORDER);
		textUCavgMonth.setEditable(false);
		textUCavgMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textUCmaxMonth = new Text(composite_1, SWT.BORDER);
		textUCmaxMonth.setEditable(false);
		textUCmaxMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textUCminMonth = new Text(composite_1, SWT.BORDER);
		textUCminMonth.setEditable(false);
		textUCminMonth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_1, SWT.NONE);

		Label label_5 = new Label(composite_1, SWT.NONE);
		GridData gd_label_5 = new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1);
		gd_label_5.widthHint = 100;
		label_5.setLayoutData(gd_label_5);
		label_5.setText("Settimana");

		textUCavgWeek = new Text(composite_1, SWT.BORDER);
		textUCavgWeek.setEditable(false);
		textUCavgWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textUCmaxWeek = new Text(composite_1, SWT.BORDER);
		textUCmaxWeek.setEditable(false);
		textUCmaxWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textUCminWeek = new Text(composite_1, SWT.BORDER);
		textUCminWeek.setEditable(false);
		textUCminWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(composite_1, SWT.NONE);

		Label label_6 = new Label(composite_1, SWT.NONE);
		label_6.setText("Giorno");

		textUCavgDay = new Text(composite_1, SWT.BORDER);
		textUCavgDay.setEditable(false);
		textUCavgDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textUCmaxDay = new Text(composite_1, SWT.BORDER);
		textUCmaxDay.setEditable(false);
		textUCmaxDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textUCminDay = new Text(composite_1, SWT.BORDER);
		textUCminDay.setEditable(false);
		textUCminDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		Label label_11 = new Label(composite_1, SWT.NONE);
		label_11.setText("Letture del giorno");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		listRipCal = new List(composite_1, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL);
		GridData gd_listRipCal = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 5, 1);
		gd_listRipCal.heightHint = 70;
		listRipCal.setLayoutData(gd_listRipCal);
		
	}

	private void tabMeterAcqua() {
		TabItem tbtmMeterAcqua = new TabItem(tabResults, SWT.NONE);
		tbtmMeterAcqua.setText("Meter Acqua");

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
		textCRVavgDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textCRVmaxDay = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textCRVmaxDay.setEditable(false);
		textCRVmaxDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textCRVminDay = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textCRVminDay.setEditable(false);
		textCRVminDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

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
		textPRVmaxWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textPRVminWeek = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVminWeek.setEditable(false);
		textPRVminWeek.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		new Label(compositeTabMeterAcqua, SWT.NONE);

		Label lblAttuale_1 = new Label(compositeTabMeterAcqua, SWT.NONE);
		lblAttuale_1.setText("Giorno");

		textPRVavgDay = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVavgDay.setEditable(false);
		textPRVavgDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textPRVmaxDay = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVmaxDay.setEditable(false);
		textPRVmaxDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		textPRVminDay = new Text(compositeTabMeterAcqua, SWT.BORDER);
		textPRVminDay.setEditable(false);
		textPRVminDay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		Label lblPeriodicReadingDate = new Label(compositeTabMeterAcqua,
				SWT.NONE);
		lblPeriodicReadingDate.setText("Letture del giorno");
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);

		listMeterAcqua = new List(compositeTabMeterAcqua, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_listMeterAcqua = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 5, 1);
		gd_listMeterAcqua.heightHint = 70;
		listMeterAcqua.setLayoutData(gd_listMeterAcqua);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		new Label(compositeTabMeterAcqua, SWT.NONE);
		
	}

	protected void buttonGoActions() {
		meterAcquaTab();
		meterRipartitoreCaloreTab();
		meterSondeTab();
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

		/* Current Readout Value */
		textCRVavgMonth.setText(avgMon.getCurrentReadoutValue().toString());
		textCRVmaxMonth.setText(maxMon.getCurrentReadoutValue().toString());
		textCRVminMonth.setText(minMon.getCurrentReadoutValue().toString());

		textCRVminWeek.setText(minWeek.getCurrentReadoutValue().toString());
		textCRVmaxWeek.setText(maxWeek.getCurrentReadoutValue().toString());
		textCRVavgWeek.setText(avgWeek.getCurrentReadoutValue().toString());

		textCRVavgDay.setText(avgDay.getCurrentReadoutValue().toString());
		textCRVmaxDay.setText(maxDay.getCurrentReadoutValue().toString());
		textCRVminDay.setText(minDay.getCurrentReadoutValue().toString());

		/* Periodic Readout value */
		textPRVavgMonth.setText(avgMon.getPeriodicReadoutValue().toString());
		textPRVmaxMonth.setText(maxMon.getPeriodicReadoutValue().toString());
		textPRVminMonth.setText(minMon.getPeriodicReadoutValue().toString());

		textPRVavgWeek.setText(avgWeek.getPeriodicReadoutValue().toString());
		textPRVmaxWeek.setText(maxWeek.getPeriodicReadoutValue().toString());
		textPRVminWeek.setText(minWeek.getPeriodicReadoutValue().toString());

		textPRVavgDay.setText(avgDay.getPeriodicReadoutValue().toString());
		textPRVmaxDay.setText(maxDay.getPeriodicReadoutValue().toString());
		textPRVminDay.setText(minDay.getPeriodicReadoutValue().toString());

		listMeterAcqua.setItems(meterAcquaStats.dayReadings(year, month, date));

	}

	private void meterRipartitoreCaloreTab() {
		MeterRipartitoreCaloreStats meterRipCalStats = new MeterRipartitoreCaloreStats(
				edificio);

		MeterRipartitoreCalore avgMonth = meterRipCalStats.monthAverage(year,
				month);
		MeterRipartitoreCalore maxMonth = meterRipCalStats
				.monthMax(year, month);
		MeterRipartitoreCalore minMonth = meterRipCalStats
				.monthMin(year, month);

		MeterRipartitoreCalore avgWeek = meterRipCalStats.weekAverage(year,
				month, date);
		MeterRipartitoreCalore maxWeek = meterRipCalStats.weekMax(year, month,
				date);
		MeterRipartitoreCalore minWeek = meterRipCalStats.weekMin(year, month,
				date);

		MeterRipartitoreCalore avgDay = meterRipCalStats.dayAverage(year,
				month, date);
		MeterRipartitoreCalore maxDay = meterRipCalStats.dayMax(year, month,
				date);
		MeterRipartitoreCalore minDay = meterRipCalStats.dayMin(year, month,
				date);

		/* Unita Consumo */
		textUCavgMonth.setText(avgMonth.getUnitaConsumo().toString());
		textUCmaxMonth.setText(maxMonth.getUnitaConsumo().toString());
		textUCminMonth.setText(minMonth.getUnitaConsumo().toString());

		textUCavgWeek.setText(avgWeek.getUnitaConsumo().toString());
		textUCmaxWeek.setText(maxWeek.getUnitaConsumo().toString());
		textUCminWeek.setText(minWeek.getUnitaConsumo().toString());

		textUCavgDay.setText(avgDay.getUnitaConsumo().toString());
		textUCmaxDay.setText(maxDay.getUnitaConsumo().toString());
		textUCminDay.setText(minDay.getUnitaConsumo().toString());

		listRipCal.setItems(meterRipCalStats.dayReadings(year, month, date));
	}

	private void meterSondeTab() {
		MeterSondeStats meterSondeStats = new MeterSondeStats(edificio);

		MeterSonde avgMonth = meterSondeStats.monthAverage(year, month);
		MeterSonde maxMonth = meterSondeStats.monthMax(year, month);
		MeterSonde minMonth = meterSondeStats.monthMin(year, month);

		MeterSonde avgWeek = meterSondeStats.weekAverage(year, month, date);
		MeterSonde maxWeek = meterSondeStats.weekMax(year, month, date);
		MeterSonde minWeek = meterSondeStats.weekMin(year, month, date);

		MeterSonde avgDay = meterSondeStats.dayAverage(year, month, date);
		MeterSonde maxDay = meterSondeStats.dayMax(year, month, date);
		MeterSonde minDay = meterSondeStats.dayMin(year, month, date);

		/* Luminosità stats */
		textLUMavgMonth.setText(avgMonth.getLuminosita().toString());
		textLUMmaxMonth.setText(maxMonth.getLuminosita().toString());
		textLUMminMonth.setText(minMonth.getLuminosita().toString());

		textLUMavgWeek.setText(avgWeek.getLuminosita().toString());
		textLUMmaxWeek.setText(maxWeek.getLuminosita().toString());
		textLUMminWeek.setText(minWeek.getLuminosita().toString());

		textLUMavgDay.setText(avgDay.getLuminosita().toString());
		textLUMmaxDay.setText(maxDay.getLuminosita().toString());
		textLUMminDay.setText(minDay.getLuminosita().toString());

		/* Sismografo Stats */
		textSISavgMonth.setText(avgMonth.getSismografo().toString());
		textSISmaxMonth.setText(maxMonth.getSismografo().toString());
		textSISminMonth.setText(minMonth.getSismografo().toString());

		textSISavgWeek.setText(avgWeek.getSismografo().toString());
		textSISmaxWeek.setText(maxWeek.getSismografo().toString());
		textSISminWeek.setText(minWeek.getSismografo().toString());

		textSISavgDay.setText(avgDay.getSismografo().toString());
		textSISmaxDay.setText(maxDay.getSismografo().toString());
		textSISminDay.setText(minDay.getSismografo().toString());

		/* Solarimetro Stats */
		textSOLavgMonth.setText(avgMonth.getSolarimetro().toString());
		textSOLmaxMonth.setText(maxMonth.getSolarimetro().toString());
		textSOLminMonth.setText(minMonth.getSolarimetro().toString());

		textSOLavgWeek.setText(avgWeek.getSolarimetro().toString());
		textSOLmaxWeek.setText(maxWeek.getSolarimetro().toString());
		textSOLminWeek.setText(minWeek.getSolarimetro().toString());

		textSOLavgDay.setText(avgDay.getSolarimetro().toString());
		textSOLmaxDay.setText(maxDay.getSolarimetro().toString());
		textSOLminDay.setText(minDay.getSolarimetro().toString());

		/* Temperatura Esterna Stats */
		textTEXavgMonth.setText(avgMonth.getTempEsterna().toString());
		textTEXmaxMonth.setText(maxMonth.getTempEsterna().toString());
		textTEXminMonth.setText(minMonth.getTempEsterna().toString());

		textTEXavgWeek.setText(avgWeek.getTempEsterna().toString());
		textTEXmaxWeek.setText(maxWeek.getTempEsterna().toString());
		textTEXminWeek.setText(minWeek.getTempEsterna().toString());

		textTEXavgDay.setText(avgDay.getTempEsterna().toString());
		textTEXmaxDay.setText(maxDay.getTempEsterna().toString());
		textTEXminDay.setText(minDay.getTempEsterna().toString());

		/* Temperatura Locali Stats */
		textTINavgMonth.setText(avgMonth.getTempLocali().toString());
		textTINmaxMonth.setText(maxMonth.getTempLocali().toString());
		textTINminMonth.setText(minMonth.getTempLocali().toString());

		textTINavgWeek.setText(avgWeek.getTempLocali().toString());
		textTINmaxWeek.setText(maxWeek.getTempLocali().toString());
		textTINminWeek.setText(minWeek.getTempLocali().toString());

		textTINavgDay.setText(avgDay.getTempLocali().toString());
		textTINmaxDay.setText(maxDay.getTempLocali().toString());
		textTINminDay.setText(minDay.getTempLocali().toString());

		/* Temperatura Giorno Stats */
		textTDYavgMonth.setText(avgMonth.getTempGiorno().toString());
		textTDYmaxMonth.setText(maxMonth.getTempGiorno().toString());
		textTDYminMonth.setText(minMonth.getTempGiorno().toString());

		textTDYavgWeek.setText(avgWeek.getTempGiorno().toString());
		textTDYmaxWeek.setText(maxWeek.getTempGiorno().toString());
		textTDYminWeek.setText(minWeek.getTempGiorno().toString());

		textTDYavgDay.setText(avgDay.getTempGiorno().toString());
		textTDYmaxDay.setText(maxDay.getTempGiorno().toString());
		textTDYminDay.setText(minDay.getTempGiorno().toString());

		listMeterSonde.setItems(meterSondeStats.dayReadings(year, month, date));
	}
}
