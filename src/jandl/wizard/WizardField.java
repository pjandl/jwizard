package jandl.wizard;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class WizardField extends WizardBase {
	/**
	 * serialVersionUID = YYYYMMDDv
	 */
	private static final long serialVersionUID = 202103210L;

	private JLabel[] lLabel;
	private JTextField[] tfField;

	public WizardField( String[] tag, String[] desc){
		this("The Wizard with Fields", tag, desc);
	}

	public WizardField(String title, String[] tag, String[] desc){
		this(title, "./res/wizard-base.png", tag, desc);
	}

	public WizardField(String title, String imageFile, String[] tag, String[] desc){
		super(title, imageFile);
		System.out.println("WizardField.<init>(" + title + ")");
		if (tag.length != desc.length) {
			throw new RuntimeException("tag.length != desc.length");
		}
		lLabel = new JLabel[tag.length];
		tfField = new JTextField[tag.length];
		JPanel panel0 = new JPanel(new GridLayout(2*tag.length, 1, 0, 5));
		for(int i = 0; i < tag.length; i++) {
			char c = (char)(97+i);
			lLabel[i] = new JLabel(c + ". " + desc[i]);
			lLabel[i].setName(tag[i]);
			tfField[i] = new JTextField();
			lLabel[i].setLabelFor(tfField[i]);
			lLabel[i].setDisplayedMnemonic(c);
			panel0.add(lLabel[i]);
			panel0.add(tfField[i]);
		}
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel1.add(panel0);
		panel1.add(Box.createVerticalGlue());
		JScrollPane scrollPane = new JScrollPane(panel1);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
		add(scrollPane, "Center");
	}

	@Override
	protected void bNextClick(ActionEvent evt) {
		System.out.println("@Override");
		Data data = Data.instance();
		for(int i = 0; i < tfField.length; i++) {
			String key = this.getName() + "." + lLabel[i].getName();
			String value =  tfField[i].getText();
			data.put(key, value);
			System.out.println(key + "=" + value);	
		}
		super.bNextClick(evt);
	}

}