package jandl.wizard;

import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jandl.wizard.pane.FieldPane;

public class WizardField extends WizardBase {
	/**
	 * serialVersionUID = YYYYMMDD
	 */
	public static final long serialVersionUID = WizardBase.serialVersionUID;

	private FieldPane fieldPane;

	public WizardField(String title, String imageFile, String[] tag, String[] label, String[] tip){
		super(title, imageFile);
		System.out.println("WizardField.<init>(" + title + ")");
		fieldPane = new FieldPane(tag, label, tip);
		fieldPane.setName("fieldPane0");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(fieldPane);
		panel.add(Box.createVerticalGlue());
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
		add(scrollPane, "Center");
	}

	public WizardField(String title, String[] tag, String[] desc){
		this(title, null, tag, desc, null);
	}

	public WizardField(String[] tag, String[] desc){
		this("WizardField", null, tag, desc, null);
	}

	@Override
	protected void bNextClick(ActionEvent evt) {
		System.out.printf("%s.bNextClick() @Override\n", this.getName());
		Data data = Data.instance();
		fieldPane.dumpOn(this.getName(), data);
		super.bNextClick(evt);
	}

}