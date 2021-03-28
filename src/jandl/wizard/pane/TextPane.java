package jandl.wizard.pane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import jandl.wizard.WizardBase;

public class TextPane extends JScrollPane {
	/**
	 * serialVersionUID = YYYYMMDDv
	 */
	protected static final long serialVersionUID = WizardBase.serialVersionUID;
	
	private JTextArea taText;
	
	public TextPane() {
		taText = new JTextArea();
		taText.setEditable(false);
		taText.setLineWrap(true);
		taText.setWrapStyleWord(true);
		this.setViewportView(taText);
	}
	
	public JTextArea getInternalJTextArea() {
		return taText;
	}
	
	public void loadTextFrom(String textFile) {
		try {
			loadTextFrom(new FileReader(textFile));
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public void loadTextFrom(Reader reader) {
		try (BufferedReader br = new BufferedReader(reader)) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine())!=null) {
				sb.append(line);
				sb.append('\n');
			}
			taText.setText(sb.toString());
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

}
