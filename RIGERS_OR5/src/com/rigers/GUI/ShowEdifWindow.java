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

import com.rigers.db.Edificio;
import com.rigers.persistence.HibernateUtil;

public class ShowEdifWindow {
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
	private Text txtIdNome;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ShowEdifWindow window = new ShowEdifWindow();
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
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List<Edificio> ediList = session.createQuery("from Edificio").list();

		shell = new Shell();
		shell.setSize(300, 400);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Group grpEdifici = new Group(shell, SWT.NONE);
		grpEdifici.setText("Edifici");
		grpEdifici.setLayout(new FillLayout(SWT.HORIZONTAL));

		txtIdNome = new Text(grpEdifici, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txtIdNome.setText("ID\t Compartimento\t Indirizzo Edificio\n");
		for (int i = 0; i < ediList.size(); i++) {
			txtIdNome.append(Integer.toString(ediList.get(i).getIdEdificio())
					+ "\t "
					+ ediList.get(i).getCompartimento()
							.getNomeCompartimento() + "\t "+ediList.get(i).getIndirizzo()+"\n");
		}

		session.getTransaction().commit();

	}
}
