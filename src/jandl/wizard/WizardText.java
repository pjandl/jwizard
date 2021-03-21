package jandl.wizard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class WizardText extends WizardBase {
	/**
	 * serialVersionUID = YYYYMMDDv
	 */
	private static final long serialVersionUID = 202103210L;

	private JTextArea taText;
	
	public WizardText(){
		this("The Wizard with Text");
	}

	public WizardText(String title){
		this(title, null, "./res/wizard-base.png");
	}

	public WizardText(String title, Reader reader){
		this(title, reader, "./res/wizard-base.png");
	}

	public WizardText(String title, Reader reader, String imageFile){
		super(title, imageFile);
		System.out.println("WizardText.<init>(" + title + ")");
		taText = new JTextArea();
		taText.setEditable(false);
		taText.setLineWrap(true);
		taText.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(taText);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
		add(scrollPane, "Center");
		loadTextFrom(reader);
	}


	public void setText(String text) {
		taText.setText(text);
	}
	public void addText(String text) {
		taText.append(text);
	}
	public void loadTextFrom(Reader reader) {
		if (reader == null) return;
		try (BufferedReader br = new BufferedReader(reader)) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine())!=null) {
				sb.append(line);
				sb.append('\n');
			}
			this.setText(sb.toString());
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
}