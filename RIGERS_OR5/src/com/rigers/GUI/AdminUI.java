package com.rigers.GUI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.rigers.API.DataInsert;
import com.rigers.API.DataView;

public class AdminUI {
	protected Shell shlRigers;
	private Text txtCompartimento;
	private Text textIndirizzoEdificio;
	private Text textCRV;
	private Text textPRV;
	private Text unitaConsumoText;
	private Spinner spinnerCompId;
	private Label lblCompInsert;
	private StackLayout compDevLayout;
	private Group grpMeterAcqua;
	private Composite compositeDevices;
	private Group grpMeterRipartitoreCalore;
	private Group grpMeterElettrico;
	private Group grpMeterGas;
	private Group grpMeterSonde;
	private Group grpMeterTermie;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_8;
	private Text text_9;
	private Spinner spinnerIdEdificio;
	private Combo comboSelectComp;
	private Label lblEdifInsert;
	private Combo comboEdif2;
	private Combo comboComp2;
	private Combo comboDisp2;
	private org.eclipse.swt.widgets.List listLettureDispositivo;
	private Combo comboComp;
	private Combo comboEdif;
	private Combo comboDisp;
	private DateTime dateTimeFrom;
	private DateTime dateTimeTo;
	private String[] compItems = DataView.CompItems();
	private String[] edifItems = DataView.EdifItems();
	private Composite compositeInsertData;
	private TabFolder tabFolder;
	private TabItem tbtmViewData;
	private String[] dispList = new String[] { "Meter Acqua",
			"Meter Elettrico", "Meter Gas", "Meter Ripartitore Calore",
			"Meter Sonde", "Meter Termie", "*" };
	private DateTime dateTime2;
	private Label ripCalOk;
	private DateTime dateTimePRD;
	private Label acquaOk;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AdminUI window = new AdminUI();
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
		shlRigers.open();
		shlRigers.layout();
		while (!shlRigers.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {

		shlRigers = new Shell();
		shlRigers.setMinimumSize(new Point(700, 200));
		shlRigers.setSize(717, 552);
		shlRigers.setText("RIGERS");
		shlRigers.setLayout(new FillLayout(SWT.VERTICAL));

		tabFolder = new TabFolder(shlRigers, SWT.NONE);

		InsertDataTab();
		ViewDataTab();
	}

	/**
	 * Tab Inserimento Dati
	 */
	private void InsertDataTab() {
		tbtmViewData = new TabItem(tabFolder, SWT.NONE);
		tbtmViewData.setToolTipText("");
		tbtmViewData.setText("Inserimento Letture");

		compositeInsertData = new Composite(tabFolder, SWT.NONE);
		tbtmViewData.setControl(compositeInsertData);
		GridLayout gl_composite_1 = new GridLayout(1, false);
		gl_composite_1.marginWidth = 1;
		gl_composite_1.horizontalSpacing = 1;
		gl_composite_1.verticalSpacing = 1;
		gl_composite_1.marginHeight = 1;
		compositeInsertData.setLayout(gl_composite_1);

		/**
		 * Gruppo inserimento compartimento
		 */
		final Group grpCompartimento = new Group(compositeInsertData, SWT.NONE);
		grpCompartimento.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		grpCompartimento.setText("Compartimento");
		grpCompartimento.setLayout(new GridLayout(7, false));

		Label lblId = new Label(grpCompartimento, SWT.NONE);
		lblId.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblId.setText("ID");

		spinnerCompId = new Spinner(grpCompartimento, SWT.BORDER);
		GridData gd_spinnerCompId = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_spinnerCompId.widthHint = 20;
		spinnerCompId.setLayoutData(gd_spinnerCompId);

		Label lblNome = new Label(grpCompartimento, SWT.NONE);
		lblNome.setText("Nome");

		txtCompartimento = new Text(grpCompartimento, SWT.BORDER);
		GridData gd_txtCompartimento = new GridData(SWT.LEFT, SWT.CENTER,
				false, false, 1, 1);
		gd_txtCompartimento.widthHint = 225;
		txtCompartimento.setLayoutData(gd_txtCompartimento);
		new Label(grpCompartimento, SWT.NONE);

		lblCompInsert = new Label(grpCompartimento, SWT.NONE);
		lblCompInsert.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_GREEN));
		lblCompInsert.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true,
				false, 1, 1));
		lblCompInsert.setText("OK");

		final int RANGE = 20;
		final List<Integer> sack = new ArrayList<>(RANGE);

		/**
		 * Bottone inserimento Compartimento
		 */
		Button btnInsertComp = new Button(grpCompartimento, SWT.NONE);
		btnInsertComp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (DataInsert.insertComp(spinnerCompId.getText(),
						txtCompartimento.getText())) {
					lblCompInsert.setForeground(SWTResourceManager
							.getColor(SWT.COLOR_DARK_GREEN));
					lblCompInsert.setText("OK");
					System.out.println("success!");
					comboSelectComp.setItems(DataView.CompItems());
					comboComp2.setItems(DataView.CompItems());
				} else {
					lblCompInsert.setForeground(SWTResourceManager
							.getColor(SWT.COLOR_DARK_RED));
					lblCompInsert.setText("NO");
					System.out.println("fail!");
				}
			}
		});
		btnInsertComp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		btnInsertComp.setText("INSERT");

		/**
		 * GRUPPO INSERIMENTO EDIFICI
		 */
		Group grpEdificio_1 = new Group(compositeInsertData, SWT.NONE);
		grpEdificio_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		grpEdificio_1.setText("Edificio");
		grpEdificio_1.setLayout(new GridLayout(8, false));

		Label lblId_1 = new Label(grpEdificio_1, SWT.NONE);
		lblId_1.setText("ID");

		spinnerIdEdificio = new Spinner(grpEdificio_1, SWT.BORDER);
		GridData gd_spinner_1 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_spinner_1.widthHint = 20;
		spinnerIdEdificio.setLayoutData(gd_spinner_1);

		Label lblCompartimento = new Label(grpEdificio_1, SWT.NONE);
		lblCompartimento.setText("Compartimento");

		comboSelectComp = new Combo(grpEdificio_1, SWT.READ_ONLY);
		GridData gd_comboSelectComp = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_comboSelectComp.widthHint = 120;
		comboSelectComp.setLayoutData(gd_comboSelectComp);

		comboSelectComp.setItems(compItems);

		Label lblIndirizzo = new Label(grpEdificio_1, SWT.NONE);
		lblIndirizzo.setText("Indirizzo");

		textIndirizzoEdificio = new Text(grpEdificio_1, SWT.BORDER);
		GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1,
				1);
		gd_text.widthHint = 200;
		textIndirizzoEdificio.setLayoutData(gd_text);

		lblEdifInsert = new Label(grpEdificio_1, SWT.NONE);
		lblEdifInsert.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true,
				false, 1, 1));
		lblEdifInsert.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_GREEN));
		lblEdifInsert.setText("OK");

		/**
		 * Bottone Inserimento Edificio
		 */
		Button btnInsertEdificio = new Button(grpEdificio_1, SWT.NONE);
		btnInsertEdificio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				System.out.println("comp Index:"
//						+ comboSelectComp.getSelectionIndex());
				if (DataInsert.insertEdificio(spinnerIdEdificio.getText(),
						comboSelectComp.getSelectionIndex(),
						textIndirizzoEdificio.getText())) {
					lblEdifInsert.setForeground(SWTResourceManager
							.getColor(SWT.COLOR_DARK_GREEN));
					lblEdifInsert.setText("OK");
					System.out.println("success!");
					comboEdif2.setItems(DataView.EdifItems());
				} else {
					lblEdifInsert.setForeground(SWTResourceManager
							.getColor(SWT.COLOR_DARK_RED));
					lblEdifInsert.setText("NO");
					System.out.println("fail!");
				}
			}
		});
		btnInsertEdificio.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		btnInsertEdificio.setText("INSERT");

		InserimentoLetture();
	}

	/**
	 * Tab Visualizzazione Dati
	 */
	private void ViewDataTab() {

		TabItem tbtmViewData_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmViewData_1.setText("Mostra Letture");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmViewData_1.setControl(composite);
		composite.setLayout(new GridLayout(1, false));

		Group grpFilters = new Group(composite, SWT.NONE);
		grpFilters.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		grpFilters.setText("Filtri");
		grpFilters.setLayout(new GridLayout(5, false));

		Label lblComp = new Label(grpFilters, SWT.NONE);
		lblComp.setText("Compartimento");

		Label lblEdificio = new Label(grpFilters, SWT.NONE);
		lblEdificio.setText("Edificio");

		Label lblDispositivo = new Label(grpFilters, SWT.NONE);
		lblDispositivo.setText("Dispositivo");

		Label lblDa = new Label(grpFilters, SWT.NONE);
		lblDa.setText("Da:");

		Label lblA = new Label(grpFilters, SWT.NONE);
		GridData gd_lblA = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1,
				1);
		gd_lblA.widthHint = 26;
		lblA.setLayoutData(gd_lblA);
		lblA.setText("A:");

		comboComp = new Combo(grpFilters, SWT.READ_ONLY);
		comboComp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboComp.getText().equals("*")) {
					comboEdif.setItems(DataView.EdifItems());
				} else {
					comboEdif.setItems(DataView.EdifItems(comboComp.getText()));
				}
				comboEdif.add("*");
			}
		});
		comboComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		comboComp.setItems(compItems);
		comboComp.add("*");

		comboEdif = new Combo(grpFilters, SWT.READ_ONLY);
		comboEdif.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		comboDisp = new Combo(grpFilters, SWT.READ_ONLY);
		comboDisp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		comboDisp.setItems(dispList);

		dateTimeFrom = new DateTime(grpFilters, SWT.BORDER);
		dateTimeFrom.setDate(2014, 0, 1);

		dateTimeTo = new DateTime(grpFilters, SWT.BORDER);
		dateTimeTo.setDate(2014, 11, 31);

		Button btnMostraCompartimenti = new Button(grpFilters, SWT.NONE);
		btnMostraCompartimenti.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CompListWindow.main(null);
			}
		});
		btnMostraCompartimenti.setLayoutData(new GridData(SWT.CENTER,
				SWT.CENTER, false, false, 1, 1));
		btnMostraCompartimenti.setText("MOSTRA COMPARTIMENTI");

		Button btnMostraEdifici = new Button(grpFilters, SWT.NONE);
		btnMostraEdifici.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,
				false, false, 1, 1));
		btnMostraEdifici.setText("MOSTRA EDIFICI");
		btnMostraEdifici.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EdifListWindow.main(null);
			}
		});
		new Label(grpFilters, SWT.NONE);

		Button btnReset = new Button(grpFilters, SWT.NONE);
		GridData gd_btnReset = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_btnReset.minimumWidth = 100;
		btnReset.setLayoutData(gd_btnReset);
		btnReset.setText("RESET");

		Button btnGo = new Button(grpFilters, SWT.NONE);
		btnGo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Date dateFrom = new Date(dateTimeFrom.getYear() - 1900,
						dateTimeFrom.getMonth(), dateTimeFrom.getDay());
				Date dateTo = new Date(dateTimeTo.getYear() - 1900, dateTimeTo
						.getMonth(), dateTimeTo.getDay());

				int dispIndex = comboDisp.getSelectionIndex();
				if (dispIndex == 6) {
					listLettureDispositivo.setItems(DataView.LettureItems(
							dateFrom, dateTo));
				} else {
					if (comboEdif.getText().equals("*")) {
						listLettureDispositivo.setItems(DataView.LettureItems(
								dispIndex, dateFrom, dateTo));
					} else {
						int edifId = Integer
								.parseInt(comboEdif.getText().substring(0,
										comboEdif.getText().indexOf(":")));
						listLettureDispositivo.setItems(DataView.LettureItems(
								edifId, dispIndex, dateFrom, dateTo));
					}
				}
			}
		});
		btnGo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1,
				1));
		btnGo.setText("GO");

		Group grpResults = new Group(composite, SWT.NONE);
		grpResults.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));
		grpResults.setText("Risultati");
		grpResults.setLayout(new FillLayout(SWT.HORIZONTAL));

		listLettureDispositivo = new org.eclipse.swt.widgets.List(grpResults,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		listLettureDispositivo.setItems(new String[] {});
	}

	/**
	 * Gruppo Inserimento Letture
	 */
	private void InserimentoLetture() {
		Group grpLettura = new Group(compositeInsertData, SWT.NONE);
		grpLettura.setLayout(new GridLayout(6, false));
		grpLettura.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				1, 1));
		grpLettura.setText("Lettura");

		Label lblEdificio_1 = new Label(grpLettura, SWT.NONE);
		lblEdificio_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblEdificio_1.setText("Compartimento");

		comboComp2 = new Combo(grpLettura, SWT.READ_ONLY);
		comboComp2.setItems(compItems);
		GridData gd_comboSelectComp2 = new GridData(SWT.FILL, SWT.CENTER,
				false, false, 1, 1);
		gd_comboSelectComp2.widthHint = 120;
		comboComp2.setLayoutData(gd_comboSelectComp2);
		comboComp2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comboEdif2.setItems(DataView.EdifItems(comboComp2.getText()));
			}
		});

		Label lblEdificio_2 = new Label(grpLettura, SWT.NONE);
		lblEdificio_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblEdificio_2.setText("Edificio");

		comboEdif2 = new Combo(grpLettura, SWT.READ_ONLY);
		comboEdif2.setItems(edifItems);
		GridData gd_comboSelectEdif = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1);
		gd_comboSelectEdif.widthHint = 120;
		comboEdif2.setLayoutData(gd_comboSelectEdif);

		Label lblDispositivo_1 = new Label(grpLettura, SWT.NONE);
		lblDispositivo_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblDispositivo_1.setText("Dispositivo");

		comboDisp2 = new Combo(grpLettura, SWT.READ_ONLY);

		comboDisp2.setItems(dispList);
		comboDisp2.remove(6);

		comboDisp2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		comboDisp2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switch (comboDisp2.getText()) {
				case "Meter Acqua":
					compDevLayout.topControl = grpMeterAcqua;
					compositeDevices.layout();
					break;
				case "Meter Elettrico":
					compDevLayout.topControl = grpMeterElettrico;
					compositeDevices.layout();
					break;
				case "Meter Gas":
					compDevLayout.topControl = grpMeterGas;
					compositeDevices.layout();
					break;
				case "Meter Termie":
					compDevLayout.topControl = grpMeterTermie;
					compositeDevices.layout();
					break;
				case "Meter Ripartitore Calore":
					compDevLayout.topControl = grpMeterRipartitoreCalore;
					compositeDevices.layout();
					break;
				case "Meter Sonde":
					compDevLayout.topControl = grpMeterSonde;
					compositeDevices.layout();
					break;
				}
			}
		});

		Label lblData = new Label(grpLettura, SWT.NONE);
		lblData.setText("Data:");

		dateTime2 = new DateTime(grpLettura, SWT.BORDER);
		new Label(grpLettura, SWT.NONE);
		new Label(grpLettura, SWT.NONE);
		new Label(grpLettura, SWT.NONE);
		new Label(grpLettura, SWT.NONE);

		/**
		 * Composite delle diverse finestre di inserimento
		 */
		compositeDevices = new Composite(compositeInsertData, SWT.NONE);
		compDevLayout = new StackLayout();
		compositeDevices.setLayout(compDevLayout);
		compositeDevices.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));

		Composite blankcomp = new Composite(compositeDevices, SWT.NONE);

		// -- Meter Acqua
		meter_acqua();

		// -- Meter Elettrico
		meter_elettrico();

		// -- Meter Ripartitore Calore
		meter_ripartitore_calore();

		// -- Meter Gas
		meter_gas();

		// -- Meter Sonde
		meter_sonde();

		// -- Meter Termie
		meter_termie();

	}

	private void meter_acqua() {
		grpMeterAcqua = new Group(compositeDevices, SWT.NONE);
		grpMeterAcqua.setText("Meter Acqua");
		grpMeterAcqua.setLayout(new GridLayout(5, false));

		Label lblCurrentReadoutValue = new Label(grpMeterAcqua, SWT.NONE);
		lblCurrentReadoutValue.setText("Current Readout Value");

		textCRV = new Text(grpMeterAcqua, SWT.BORDER);
		GridData gd_textCRV = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_textCRV.widthHint = 100;
		textCRV.setLayoutData(gd_textCRV);

		Label lblDal_1 = new Label(grpMeterAcqua, SWT.NONE);
		lblDal_1.setText("dal");
		new Label(grpMeterAcqua, SWT.NONE);
		new Label(grpMeterAcqua, SWT.NONE);

		Label lblPeriodicReadoutValue = new Label(grpMeterAcqua, SWT.NONE);
		lblPeriodicReadoutValue.setText("Periodic Readout Value");

		textPRV = new Text(grpMeterAcqua, SWT.BORDER);
		GridData gd_textPRV = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_textPRV.widthHint = 100;
		textPRV.setLayoutData(gd_textPRV);

		Label lblDal = new Label(grpMeterAcqua, SWT.NONE);
		lblDal.setText("dal");
		new Label(grpMeterAcqua, SWT.NONE);
		new Label(grpMeterAcqua, SWT.NONE);

		Label lblPeriodicReadingDate = new Label(grpMeterAcqua, SWT.NONE);
		lblPeriodicReadingDate.setText("Periodic Reading Date");

		dateTimePRD = new DateTime(grpMeterAcqua, SWT.BORDER);
		new Label(grpMeterAcqua, SWT.NONE);
		new Label(grpMeterAcqua, SWT.NONE);
		new Label(grpMeterAcqua, SWT.NONE);
		new Label(grpMeterAcqua, SWT.NONE);
		new Label(grpMeterAcqua, SWT.NONE);
		new Label(grpMeterAcqua, SWT.NONE);

		acquaOk = new Label(grpMeterAcqua, SWT.NONE);
		acquaOk.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false,
				1, 1));
		acquaOk.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		acquaOk.setText("OK");

		Button insertAcqua = new Button(grpMeterAcqua, SWT.NONE);
		insertAcqua.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				insertAcqua();
			}
		});
		insertAcqua.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		insertAcqua.setText("INSERT");

	}

	protected void insertAcqua() {
		int indexEdificio = comboEdif2.getSelectionIndex();
		Date date = new GregorianCalendar(dateTime2.getYear(), dateTime2.getMonth(), dateTime2.getDay()).getTime();
		date.setSeconds(new GregorianCalendar().getInstance().get(Calendar.SECOND));
		
		int crvVal = Integer.parseInt(textCRV.getText());
		int prvVal = Integer.parseInt(textPRV.getText());
		Date prdVal = new GregorianCalendar(dateTimePRD.getYear(), dateTimePRD.getMonth(), dateTimePRD.getDay()).getTime();
		
		boolean check = DataInsert.insertMeterAcqua(indexEdificio, date, crvVal, prvVal, prdVal);
		
		if (check) {
			acquaOk.setForeground(SWTResourceManager
					.getColor(SWT.COLOR_DARK_GREEN));
			acquaOk.setText("OK");
			System.out.println("success!");
		} else {
			acquaOk.setForeground(SWTResourceManager
					.getColor(SWT.COLOR_DARK_RED));
			acquaOk.setText("NO");
			System.out.println("fail!");
		}	
	}

	private void meter_elettrico() {
		grpMeterElettrico = new Group(compositeDevices, SWT.NONE);
		grpMeterElettrico.setText("Meter Elettrico");
		grpMeterElettrico.setLayout(new GridLayout(1, false));
	}

	private void meter_ripartitore_calore() {
		grpMeterRipartitoreCalore = new Group(compositeDevices, SWT.NONE);
		grpMeterRipartitoreCalore.setText("Meter Ripartitore Calore");
		grpMeterRipartitoreCalore.setLayout(new GridLayout(5, false));

		Label lblUnitConsumo = new Label(grpMeterRipartitoreCalore, SWT.NONE);
		lblUnitConsumo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblUnitConsumo.setText("Unita\u00A0 Consumo");
		new Label(grpMeterRipartitoreCalore, SWT.NONE);

		unitaConsumoText = new Text(grpMeterRipartitoreCalore, SWT.BORDER);
		GridData gd_unitaConsumoText = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_unitaConsumoText.widthHint = 100;
		unitaConsumoText.setLayoutData(gd_unitaConsumoText);
		new Label(grpMeterRipartitoreCalore, SWT.NONE);
		new Label(grpMeterRipartitoreCalore, SWT.NONE);
		new Label(grpMeterRipartitoreCalore, SWT.NONE);
		new Label(grpMeterRipartitoreCalore, SWT.NONE);
		new Label(grpMeterRipartitoreCalore, SWT.NONE);

		ripCalOk = new Label(grpMeterRipartitoreCalore, SWT.NONE);
		ripCalOk.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false,
				1, 1));
		ripCalOk.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		ripCalOk.setText("OK");

		Button insertRipCal = new Button(grpMeterRipartitoreCalore, SWT.NONE);
		insertRipCal.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				insertRipCal();
			}
		});
		insertRipCal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		insertRipCal.setText("INSERT");
	}

	protected void insertRipCal() {
		int indexEdificio = comboEdif2.getSelectionIndex();
		Date date = new GregorianCalendar(dateTime2.getYear(), dateTime2.getMonth(), dateTime2.getDay()).getTime();
		date.setSeconds(new GregorianCalendar().getInstance().get(Calendar.SECOND));
		
		int unitaConsumo = Integer.parseInt(unitaConsumoText.getText());
		
		boolean check = DataInsert.insertMeterRipCal(indexEdificio, date, unitaConsumo);
		
		if (check) {
			ripCalOk.setForeground(SWTResourceManager
					.getColor(SWT.COLOR_DARK_GREEN));
			ripCalOk.setText("OK");
			System.out.println("success!");
		} else {
			ripCalOk.setForeground(SWTResourceManager
					.getColor(SWT.COLOR_DARK_RED));
			ripCalOk.setText("NO");
			System.out.println("fail!");
		}		
	}

	private void meter_gas() {
		grpMeterGas = new Group(compositeDevices, SWT.NONE);
		grpMeterGas.setText("Meter Gas");
	}

	private void meter_sonde() {
		grpMeterSonde = new Group(compositeDevices, SWT.NONE);
		grpMeterSonde.setText("Meter Sonde");
		grpMeterSonde.setLayout(new GridLayout(4, false));

		Label lblTemperaturaLocali = new Label(grpMeterSonde, SWT.NONE);
		lblTemperaturaLocali.setText("Temperatura Locali");

		text_4 = new Text(grpMeterSonde, SWT.BORDER);
		GridData gd_text_4 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_text_4.widthHint = 100;
		text_4.setLayoutData(gd_text_4);
		new Label(grpMeterSonde, SWT.NONE);
		new Label(grpMeterSonde, SWT.NONE);

		Label lblTemperaturaGiorno = new Label(grpMeterSonde, SWT.NONE);
		lblTemperaturaGiorno.setText("Temperatura Giorno");

		text_5 = new Text(grpMeterSonde, SWT.BORDER);
		text_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		new Label(grpMeterSonde, SWT.NONE);
		new Label(grpMeterSonde, SWT.NONE);

		Label lblTemperaturaEsterna = new Label(grpMeterSonde, SWT.NONE);
		lblTemperaturaEsterna.setText("Temperatura Esterna");

		text_6 = new Text(grpMeterSonde, SWT.BORDER);
		text_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		new Label(grpMeterSonde, SWT.NONE);
		new Label(grpMeterSonde, SWT.NONE);

		Label lblLuminosita = new Label(grpMeterSonde, SWT.NONE);
		lblLuminosita.setText("Luminosita");

		text_7 = new Text(grpMeterSonde, SWT.BORDER);
		text_7.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		new Label(grpMeterSonde, SWT.NONE);
		new Label(grpMeterSonde, SWT.NONE);

		Label lblSolarimetro = new Label(grpMeterSonde, SWT.NONE);
		lblSolarimetro.setText("Solarimetro");

		text_8 = new Text(grpMeterSonde, SWT.BORDER);
		text_8.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		new Label(grpMeterSonde, SWT.NONE);
		new Label(grpMeterSonde, SWT.NONE);

		Label lblSismografo = new Label(grpMeterSonde, SWT.NONE);
		lblSismografo.setText("Sismografo");

		text_9 = new Text(grpMeterSonde, SWT.BORDER);
		text_9.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		new Label(grpMeterSonde, SWT.NONE);
		new Label(grpMeterSonde, SWT.NONE);
		new Label(grpMeterSonde, SWT.NONE);
		new Label(grpMeterSonde, SWT.NONE);

		Label lblOk_3 = new Label(grpMeterSonde, SWT.NONE);
		lblOk_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false,
				1, 1));
		lblOk_3.setText("OK");

		Button btnInsert = new Button(grpMeterSonde, SWT.NONE);
		btnInsert.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnInsert.setText("INSERT");

	}

	private void meter_termie() {
		grpMeterTermie = new Group(compositeDevices, SWT.NONE);
		grpMeterTermie.setText("Meter Termie");
		compositeDevices.setTabList(new Control[] { grpMeterAcqua,
				grpMeterElettrico, grpMeterGas, grpMeterTermie,
				grpMeterRipartitoreCalore, grpMeterSonde });
	}

}