package jandl.wizard.pane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JTextArea;

import jandl.wizard.Collector;
import jandl.wizard.WizardBase;

public class TextPane extends BasePane {
	/**
	 * serialVersionUID = YYYYMMDD
	 */
	protected static final long serialVersionUID = WizardBase.serialVersionUID;
	
	private JTextArea taText;
	
	public TextPane() {
		taText = new JTextArea();
		taText.setEditable(false);
		taText.setLineWrap(true);
		taText.setWrapStyleWord(true);
		add(taText);
	}
	
	@Override
	public void dumpOn(String tag, Collector collector) {
		String key = String.format("%s.%s.%s", 
				tag, this.getName(), "text");
		String value =  taText.getText().substring(0, 10) + "...";
		collector.put(key, value);
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
