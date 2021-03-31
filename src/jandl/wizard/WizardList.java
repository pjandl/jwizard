package jandl.wizard;

import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jandl.wizard.pane.ListPane;

public class WizardList extends WizardBase {
	/**
	 * serialVersionUID = YYYYMMDD
	 */
	public static final long serialVersionUID = WizardBase.serialVersionUID;

	private ListPane<?> listPane;

	public WizardList(String tag, String label, Object[] list){
		this("WizardList", null, tag, label, list);
	}

	public WizardList(String title, String tag, String label, Object[] list){
		this(title, null, tag, label, list);
	}

	public <T> WizardList(String title, String imageFile, String tag, String label, T[] list){
		super(title, imageFile);
		System.out.println("WizardList.<init>(" + title + ")");
		listPane = new ListPane<T>(tag, label, list);
		listPane.setName("listPane0");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(listPane);
		panel.add(Box.createVerticalGlue());
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
		add(scrollPane, "Center");
	}

	@Override
	protected void bNextClick(ActionEvent evt) {
		System.out.printf("%s.bNextClick() @Override\n", this.getName());
		Data data = Data.instance();
		listPane.dumpOn(this.getName(), data);
		super.bNextClick(evt);
	}

}