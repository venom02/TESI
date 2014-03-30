package com.rigers.GUI.main;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.rigers.main.*;

public class GUI {
	private static class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			return new Object[0];
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	protected Shell shlRigers;
	private Text txtCompartimento;
	private Text textIndirizzoEdificio;
	private Table table;
	private Text text_1;
	private Text text_2;
	private Text text_3;
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
	private Combo comboIdCompartimento;
	private Label lblEdifInsert;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GUI window = new GUI();
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

		Menu menu_1 = new Menu(shlRigers, SWT.BAR);
		shlRigers.setMenuBar(menu_1);

		MenuItem mntmNewSubmenu = new MenuItem(menu_1, SWT.CASCADE);
		mntmNewSubmenu.setText("File");

		Menu menu_2 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_2);

		MenuItem mntmNewItem_1 = new MenuItem(menu_2, SWT.NONE);
		mntmNewItem_1.setText("Connect...");

		new MenuItem(menu_2, SWT.SEPARATOR);

		MenuItem mntmNewItem_2 = new MenuItem(menu_2, SWT.NONE);
		mntmNewItem_2.setText("Quit");

		TabFolder tabFolder = new TabFolder(shlRigers, SWT.NONE);

		TabItem tbtmViewData = new TabItem(tabFolder, SWT.NONE);
		tbtmViewData.setToolTipText("");
		tbtmViewData.setText("Inserimento Letture");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmViewData.setControl(composite_1);
		GridLayout gl_composite_1 = new GridLayout(1, false);
		gl_composite_1.marginWidth = 1;
		gl_composite_1.horizontalSpacing = 1;
		gl_composite_1.verticalSpacing = 1;
		gl_composite_1.marginHeight = 1;
		composite_1.setLayout(gl_composite_1);

		final Group grpCompartimento = new Group(composite_1, SWT.NONE);
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

		Group grpEdificio_1 = new Group(composite_1, SWT.NONE);
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

		comboIdCompartimento = new Combo(grpEdificio_1, SWT.NONE);

		
		comboIdCompartimento.setItems(DataView.CompIdItems());

		Label lblIndirizzo = new Label(grpEdificio_1, SWT.NONE);
		lblIndirizzo.setText("Indirizzo");

		textIndirizzoEdificio = new Text(grpEdificio_1, SWT.BORDER);
		GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1,
				1);
		gd_text.widthHint = 240;
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
				if (DataInsert.insertEdificio(spinnerIdEdificio.getText(),
						comboIdCompartimento.getText(),
						textIndirizzoEdificio.getText())) {
					lblEdifInsert.setForeground(SWTResourceManager
							.getColor(SWT.COLOR_DARK_GREEN));
					lblEdifInsert.setText("OK");
					System.out.println("success!");
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

		Group grpLettura = new Group(composite_1, SWT.NONE);
		grpLettura.setLayout(new GridLayout(6, false));
		grpLettura.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				1, 1));
		grpLettura.setText("Lettura");

		Label lblEdificio_1 = new Label(grpLettura, SWT.NONE);
		lblEdificio_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblEdificio_1.setText("Compartimento");

		Combo comboCompInsert = new Combo(grpLettura, SWT.NONE);
		comboCompInsert.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		Label lblEdificio_2 = new Label(grpLettura, SWT.NONE);
		lblEdificio_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblEdificio_2.setText("Edificio");

		Combo comboEdifInsert = new Combo(grpLettura, SWT.NONE);
		GridData gd_comboEdifInsert = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1);
		gd_comboEdifInsert.widthHint = 100;
		comboEdifInsert.setLayoutData(gd_comboEdifInsert);

		Label lblDispositivo_1 = new Label(grpLettura, SWT.NONE);
		lblDispositivo_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblDispositivo_1.setText("Dispositivo");

		final Combo comboDispInsert = new Combo(grpLettura, SWT.NONE);

		comboDispInsert.setItems(new String[] { "Meter Acqua",
				"Meter Elettrico", "Meter Gas", "Meter Termie",
				"Meter Ripartitore Calore", "Meter Sonde" });

		comboDispInsert.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		comboDispInsert.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switch (comboDispInsert.getText()) {
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

		DateTime dateTime_2 = new DateTime(grpLettura, SWT.BORDER);
		new Label(grpLettura, SWT.NONE);
		new Label(grpLettura, SWT.NONE);
		new Label(grpLettura, SWT.NONE);
		new Label(grpLettura, SWT.NONE);

		/**
		 * Composite delle diverse finestre di inserimento
		 */
		compositeDevices = new Composite(composite_1, SWT.NONE);
		compDevLayout = new StackLayout();
		compositeDevices.setLayout(compDevLayout);
		compositeDevices.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));

		Composite blankcomp = new Composite(compositeDevices, SWT.NONE);

		// -- Meter Acqua
		grpMeterAcqua = new Group(compositeDevices, SWT.NONE);
		grpMeterAcqua.setText("Meter Acqua");
		grpMeterAcqua.setLayout(new GridLayout(4, false));

		Label lblCurrentReadoutValue = new Label(grpMeterAcqua, SWT.NONE);
		lblCurrentReadoutValue.setText("Current Readout Value");

		text_1 = new Text(grpMeterAcqua, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_text_1.widthHint = 100;
		text_1.setLayoutData(gd_text_1);

		Label lblDal = new Label(grpMeterAcqua, SWT.NONE);
		lblDal.setText("dal");
		new Label(grpMeterAcqua, SWT.NONE);

		Label lblPeriodicReadoutValue = new Label(grpMeterAcqua, SWT.NONE);
		lblPeriodicReadoutValue.setText("Periodic Readout Value");

		text_2 = new Text(grpMeterAcqua, SWT.BORDER);
		GridData gd_text_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_text_2.widthHint = 100;
		text_2.setLayoutData(gd_text_2);

		Label lblDal_1 = new Label(grpMeterAcqua, SWT.NONE);
		lblDal_1.setText("dal");
		new Label(grpMeterAcqua, SWT.NONE);

		Label lblPeriodicReadingDate = new Label(grpMeterAcqua, SWT.NONE);
		lblPeriodicReadingDate.setText("Periodic Reading Date");

		DateTime dateTime = new DateTime(grpMeterAcqua, SWT.BORDER);
		new Label(grpMeterAcqua, SWT.NONE);
		new Label(grpMeterAcqua, SWT.NONE);
		new Label(grpMeterAcqua, SWT.NONE);
		new Label(grpMeterAcqua, SWT.NONE);

		Label lblOk_1 = new Label(grpMeterAcqua, SWT.NONE);
		lblOk_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false,
				1, 1));
		lblOk_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblOk_1.setText("OK");

		Button btnInsert_2 = new Button(grpMeterAcqua, SWT.NONE);
		btnInsert_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		btnInsert_2.setText("INSERT");

		// -- Meter Elettrico
		grpMeterElettrico = new Group(compositeDevices, SWT.NONE);
		grpMeterElettrico.setText("Meter Elettrico");
		grpMeterElettrico.setLayout(new GridLayout(1, false));

		// -- Meter Ripartitore Calore
		grpMeterRipartitoreCalore = new Group(compositeDevices, SWT.NONE);
		grpMeterRipartitoreCalore.setText("Meter Ripartitore Calore");
		grpMeterRipartitoreCalore.setLayout(new GridLayout(5, false));

		Label lblUnitConsumo = new Label(grpMeterRipartitoreCalore, SWT.NONE);
		lblUnitConsumo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblUnitConsumo.setText("Unita\u00A0 Consumo");
		new Label(grpMeterRipartitoreCalore, SWT.NONE);

		text_3 = new Text(grpMeterRipartitoreCalore, SWT.BORDER);
		GridData gd_text_3 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_text_3.widthHint = 100;
		text_3.setLayoutData(gd_text_3);
		new Label(grpMeterRipartitoreCalore, SWT.NONE);
		new Label(grpMeterRipartitoreCalore, SWT.NONE);
		new Label(grpMeterRipartitoreCalore, SWT.NONE);
		new Label(grpMeterRipartitoreCalore, SWT.NONE);
		new Label(grpMeterRipartitoreCalore, SWT.NONE);

		Label lblOk_2 = new Label(grpMeterRipartitoreCalore, SWT.NONE);
		lblOk_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false,
				1, 1));
		lblOk_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblOk_2.setText("OK");

		Button btnInsert_3 = new Button(grpMeterRipartitoreCalore, SWT.NONE);
		btnInsert_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		btnInsert_3.setText("INSERT");

		// -- Meter Gas
		grpMeterGas = new Group(compositeDevices, SWT.NONE);
		grpMeterGas.setText("Meter Gas");

		// -- Meter Sonde
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
		btnInsert.setText("INSERT");

		// -- Meter Termie
		grpMeterTermie = new Group(compositeDevices, SWT.NONE);
		grpMeterTermie.setText("Meter Termie");
		compositeDevices.setTabList(new Control[] { grpMeterAcqua,
				grpMeterElettrico, grpMeterGas, grpMeterTermie,
				grpMeterRipartitoreCalore, grpMeterSonde });

		/**
		 * 
		 * 
		 * SCHERMATA VISUALIZZAZIONE DATI
		 * 
		 */

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

		Combo comboComp = new Combo(grpFilters, SWT.NONE);
		comboComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Combo comboEdif = new Combo(grpFilters, SWT.NONE);
		comboEdif.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Combo comboDisp = new Combo(grpFilters, SWT.NONE);
		comboDisp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		DateTime dateTimeFrom = new DateTime(grpFilters, SWT.BORDER);

		DateTime dateTimeTo = new DateTime(grpFilters, SWT.BORDER);
		new Label(grpFilters, SWT.NONE);
		new Label(grpFilters, SWT.NONE);
		new Label(grpFilters, SWT.NONE);

		Button btnReset = new Button(grpFilters, SWT.NONE);
		GridData gd_btnReset = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_btnReset.minimumWidth = 100;
		btnReset.setLayoutData(gd_btnReset);
		btnReset.setText("RESET");

		Button btnGo = new Button(grpFilters, SWT.NONE);
		btnGo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1,
				1));
		btnGo.setText("GO");

		Group grpResults = new Group(composite, SWT.NONE);
		grpResults.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));
		grpResults.setText("Risultati");
		grpResults.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite_4 = new Composite(grpResults, SWT.NONE);
		TableColumnLayout tcl_composite_4 = new TableColumnLayout();
		composite_4.setLayout(tcl_composite_4);

		TableViewer tableViewer = new TableViewer(composite_4, SWT.BORDER
				| SWT.FULL_SELECTION);
		tableViewer.setColumnProperties(new String[] {});
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		Composite composite_2 = new Composite(tabFolder, SWT.NONE);

	}
}
