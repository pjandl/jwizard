package jandl.wizard.pane;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import jandl.wizard.Collector;
import jandl.wizard.WizardBase;

public class ListPane<T> extends BasePane {
	/**
	 * serialVersionUID = YYYYMMDD
	 */
	protected static final long serialVersionUID = WizardBase.serialVersionUID;
	
	private JList<T> lbList;
	
	public ListPane(String tag, String label, T[] list) {
		super(new BorderLayout(0, 5));
		setTag(tag);
		JLabel lLabel = new JLabel(label);
		lbList = new JList<>(list);
		lbList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(lLabel, "North");
		add(new JScrollPane(lbList), "Center");
	}
	
	public void dumpOn(String tag, Collector collector) {
		String key1 = String.format("%s.%s.%s.%s", 
				tag, this.getName(), this.getTag(), "indices");
		int[] index = this.getSelectedIndices();
		collector.put(key1, index);
		String key2 = String.format("%s.%s.%s.%s", 
				tag, this.getName(), this.getTag(), "selectedValues");
		String value =  this.getSelectedValuesList().toString();
		collector.put(key2, value);
	}
 	
	public JList<T> getInternalJList() {
		return lbList;
	}

	public int[] getSelectedIndices() {
		return lbList.getSelectedIndices();
	}

	public List<T> getSelectedValuesList() {
		return lbList.getSelectedValuesList();
	}

	public void setSelectionMode(int mode) {
		lbList.setSelectionMode(mode);
	}
}
