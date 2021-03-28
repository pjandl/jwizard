package jandl.wizard.pane;

import java.awt.BorderLayout;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import jandl.wizard.WizardBase;

public class ListPane<T> extends JPanel {
	/**
	 * serialVersionUID = YYYYMMDDv
	 */
	protected static final long serialVersionUID = WizardBase.serialVersionUID;
	
	private String tag;
	private JList<T> lbList;
	
	public ListPane(String tag, String label, T[] list) {
		super(new BorderLayout(0, 5));
		this.tag = tag;
		JLabel lLabel = new JLabel(label);
		lbList = new JList<>(list);
		lbList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(lLabel, "North");
		add(new JScrollPane(lbList), "Center");
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

	public String getTag() {
		return tag;
	}
	
	public void setSelectionMode(int mode) {
		lbList.setSelectionMode(mode);
	}
}
