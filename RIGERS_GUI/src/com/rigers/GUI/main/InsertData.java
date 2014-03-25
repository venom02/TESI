package com.rigers.GUI.main;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.List;

public class InsertData {

	protected Shell shlRigers;
	private Text txtAsd;
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			InsertData window = new InsertData();
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
		shlRigers.setSize(649, 316);
		shlRigers.setText("RIGERS");
		shlRigers.setLayout(new FormLayout());
		
		Composite composite = new Composite(shlRigers, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		FormData fd_composite = new FormData();
		fd_composite.left = new FormAttachment(0);
		fd_composite.top = new FormAttachment(0);
		fd_composite.bottom = new FormAttachment(0, 276);
		fd_composite.right = new FormAttachment(0, 643);
		composite.setLayoutData(fd_composite);
		
		TabFolder tabFolder = new TabFolder(composite, SWT.NONE);
		
		TabItem tbtmViewData = new TabItem(tabFolder, SWT.NONE);
		tbtmViewData.setToolTipText("");
		tbtmViewData.setText("InsertData");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmViewData.setControl(composite_1);
		composite_1.setLayout(new FillLayout(SWT.VERTICAL));
		
		Group grpCompartimento = new Group(composite_1, SWT.NONE);
		grpCompartimento.setText("Compartimento");
		grpCompartimento.setLayout(new GridLayout(7, false));
		
		Label lblId = new Label(grpCompartimento, SWT.NONE);
		lblId.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblId.setText("ID");
		
		Spinner spinner = new Spinner(grpCompartimento, SWT.BORDER);
		GridData gd_spinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_spinner.widthHint = 20;
		spinner.setLayoutData(gd_spinner);
		new Label(grpCompartimento, SWT.NONE);
		
		Label lblNome = new Label(grpCompartimento, SWT.NONE);
		lblNome.setText("Nome");
		
		txtAsd = new Text(grpCompartimento, SWT.BORDER);
		GridData gd_txtAsd = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtAsd.widthHint = 150;
		txtAsd.setLayoutData(gd_txtAsd);
		
		Label lblNewLabel = new Label(grpCompartimento, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		lblNewLabel.setText("OK");
		
		Button btnInsert = new Button(grpCompartimento, SWT.NONE);
		btnInsert.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnInsert.setAlignment(SWT.LEFT);
		btnInsert.setText("INSERT");
		
		Group grpEdificio = new Group(composite_1, SWT.NONE);
		grpEdificio.setText("Edificio");
		grpEdificio.setLayout(new GridLayout(8, false));
		
		Label lblId_1 = new Label(grpEdificio, SWT.NONE);
		lblId_1.setText("ID");
		
		Spinner spinner_1 = new Spinner(grpEdificio, SWT.BORDER);
		GridData gd_spinner_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_spinner_1.widthHint = 20;
		gd_spinner_1.minimumWidth = 20;
		spinner_1.setLayoutData(gd_spinner_1);
		spinner_1.setBounds(0, 0, 440, 79);
		
		Label lblCompartimento = new Label(grpEdificio, SWT.NONE);
		lblCompartimento.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCompartimento.setText("Compartimento");
		
		Combo combo = new Combo(grpEdificio, SWT.NONE);
		combo.setItems(new String[] {"Comp1", "Comp2", "Comp3"});
		GridData gd_combo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_combo.widthHint = 100;
		combo.setLayoutData(gd_combo);
		
		Label lblIndirizzo = new Label(grpEdificio, SWT.NONE);
		lblIndirizzo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblIndirizzo.setText("Indirizzo");
		
		text = new Text(grpEdificio, SWT.BORDER);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_text.widthHint = 200;
		text.setLayoutData(gd_text);
		
		Label lblOk = new Label(grpEdificio, SWT.NONE);
		lblOk.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblOk.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		lblOk.setText("OK");
		
		Button btnInsert_1 = new Button(grpEdificio, SWT.NONE);
		btnInsert_1.setText("INSERT");
		new Label(grpEdificio, SWT.NONE);
		new Label(grpEdificio, SWT.NONE);
		new Label(grpEdificio, SWT.NONE);
		new Label(grpEdificio, SWT.NONE);
		new Label(grpEdificio, SWT.NONE);
		new Label(grpEdificio, SWT.NONE);
		new Label(grpEdificio, SWT.NONE);
		new Label(grpEdificio, SWT.NONE);
		
		TabItem tbtmInsertData = new TabItem(tabFolder, SWT.NONE);
		tbtmInsertData.setText("View Data");
		
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

	}
}
