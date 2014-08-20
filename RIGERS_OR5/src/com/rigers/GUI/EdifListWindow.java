package com.rigers.GUI;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.hibernate.Session;

import com.rigers.API.DataView;
import com.rigers.db.Edificio;
import com.rigers.persistence.HibernateUtil;

public class EdifListWindow {
	private static class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			return new Object[0];
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	protected Shell shell;
	private org.eclipse.swt.widgets.List list;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			EdifListWindow window = new EdifListWindow();
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
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		session.beginTransaction();

//		List<Edificio> ediList = session.createQuery("from Edificio").list();

		shell = new Shell();
		shell.setSize(300, 400);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Group grpEdifici = new Group(shell, SWT.NONE);
		grpEdifici.setText("Edifici");
		grpEdifici.setLayout(new FillLayout(SWT.HORIZONTAL));

		
		list = new org.eclipse.swt.widgets.List(grpEdifici, SWT.BORDER | SWT.V_SCROLL);
//		for (int i = 0; i < ediList.size(); i++) {
//			list.setItem(i, (Integer.toString(ediList.get(i).getIdEdificio())
//					+ "\t "
//					+ ediList.get(i).getCompartimento()
//							.getNomeCompartimento() + "\t "+ediList.get(i).getIndirizzo()+"\n"));
//		}

		list.setItems(DataView.EdifItems());
//		session.getTransaction().commit();

	}
}
