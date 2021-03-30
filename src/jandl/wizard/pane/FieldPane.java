package jandl.wizard.pane;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import jandl.wizard.Collector;
import jandl.wizard.WizardBase;

public class FieldPane extends BasePane {
	/**
	 * serialVersionUID = YYYYMMDD
	 */
	protected static final long serialVersionUID = WizardBase.serialVersionUID;
	
	private JLabel[] lLabel;
	private JTextField[] tfField;
	
	public FieldPane(String[] tag, String[] label, String[] tip) {
		super(new GridLayout(2*tag.length, 1, 0, 5));
		if (tag.length != label.length || tag.length != tip.length) {
			throw new RuntimeException(" diferent lengths on tag, label and tip");
		}
		lLabel = new JLabel[tag.length];
		tfField = new JTextField[tag.length];
		for(int i = 0; i < tag.length; i++) {
			char c = (char)(97+i);
			lLabel[i] = new JLabel(c + ". " + tip[i]);
			lLabel[i].setName(tag[i]);
			tfField[i] = new JTextField();
			lLabel[i].setLabelFor(tfField[i]);
			lLabel[i].setDisplayedMnemonic(c);
			if (tip[i] != null) tfField[i].setToolTipText(tip[i]);
			this.add(lLabel[i]);
			this.add(tfField[i]);
		}	
	}
	
	@Override
	public void dumpOn(String tag, Collector collector) {
		for(int i = 0; i < fieldCount(); i++) {
			String key = String.format("%s.%s.%s", 
					tag, this.getName(), getFieldTag(i));
			String value =  getField(i);
			collector.put(key, value);
		}
	}
	
	public int fieldCount() {
		return lLabel.length;
	}
	
	public String getField(int i) {
		return tfField[i].getText();
	}

	public JTextField getInternalJTextField(int i) {
		return tfField[i];
	}

	public JLabel getInternalJLabel(int i) {
		return lLabel[i];
	}

	public String getFieldTag(int i) {
		return lLabel[i].getName();
	}

}
