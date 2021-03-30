package jandl.wizard.pane;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import jandl.wizard.Collector;
import jandl.wizard.WizardBase;

public class ComboPane<T> extends BasePane {
	/**
	 * serialVersionUID = YYYYMMDD
	 */
	protected static final long serialVersionUID = WizardBase.serialVersionUID;
	
	private JComboBox<T> cbCombo;
	
	public ComboPane(String tag, String label, T[] list) {
		super(new BorderLayout(0, 5));
		setTag(tag);
		JLabel lLabel = new JLabel(label);
		cbCombo = new JComboBox<>(list);
		cbCombo.setEditable(false);
		add(lLabel, "North");
		add(new JScrollPane(cbCombo), "Center");
	}
	
	@Override
	public void dumpOn(String tag, Collector collector) {
		String key1 = this.getName() + "." + this.getTag() + ".index" ;
		int index = this.getSelectedIndex();
		collector.put(key1, index);
		String key2 = this.getName() + "." + this.getTag() + ".selectedValue" ;
		String value =  this.getSelectedItem().toString();
		collector.put(key2, value);
	}

	public JComboBox<T> getInternalJComboBox() {
		return cbCombo;
	}

	public int getSelectedIndex() {
		return cbCombo.getSelectedIndex();
	}

	@SuppressWarnings("unchecked")
	public T getSelectedItem() {
		return (T) cbCombo.getSelectedItem();
	}

	public void setEditable(boolean mode) {
		cbCombo.setEditable(mode);
	}
}
