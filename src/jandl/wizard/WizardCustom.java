package jandl.wizard;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jandl.wizard.pane.BasePane;
import jandl.wizard.pane.ListPane;
import jandl.wizard.pane.TextPane;

public class WizardCustom extends WizardBase {
	/**
	 * serialVersionUID = YYYYMMDD
	 */
	public static final long serialVersionUID = WizardBase.serialVersionUID;
	
	private JPanel panel;

	public WizardCustom(String title) {
		super(title);
		System.out.println("WizardField.<init>(" + title + ")");
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
		add(scrollPane, "Center");
	}
	
	public void addTextPane(String textFile) {
		TextPane textPane = new TextPane();
		textPane.setName("TextPane" + panel.getComponentCount());
		textPane.loadTextFrom(textFile);
		panel.add(textPane);
	}
	
	public <T> void addListPane(String tag, String label, T[] list) {
		ListPane<T> listPane = new ListPane<>(tag, label, list);
		listPane.setName("ListPane" + panel.getComponentCount());
		panel.add(listPane);
	}
	
	public void addVerticalGlue() {
		panel.add(Box.createVerticalGlue());
	}
	
	@Override
	protected void bNextClick(ActionEvent evt) {
		System.out.println("@Override");
		Data data = Data.instance();
		System.out.println(panel.getComponentCount());
		for(int c = 0; c < panel.getComponentCount(); c++) {
			Component component = panel.getComponent(c);
			System.out.println(component.getName());
			if (component instanceof BasePane) {
				BasePane pane = (BasePane) component;
				pane.dumpOn(getName(), data);
			}
		}
		super.bNextClick(evt);
	}

}
