package com.rigers.GUI;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.hibernate.Session;

import com.rigers.db.Compartimento;
import com.rigers.persistence.HibernateUtil;

public class ShowCompWindow {
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
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ShowCompWindow window = new ShowCompWindow();
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
		
		List<Compartimento> compList = session.createQuery("from Compartimento").list();
		
		shell = new Shell();
		shell.setSize(300, 400);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group grpCompartimenti = new Group(shell, SWT.NONE);
		grpCompartimenti.setText("Compartimenti");
		grpCompartimenti.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text = new Text(grpCompartimenti, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		text.setText("ID\t Nome\n");
		for (int i = 0; i < compList.size(); i++) {
			text.append(Integer.toString(compList.get(i).getIdCompartimento()) + "\t "+compList.get(i).getNomeCompartimento()+"\n");
		}
		
		
		session.getTransaction().commit();

	}
}
