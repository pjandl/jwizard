package jandl.wizard;

import java.awt.event.ActionEvent;
import java.io.Reader;

import javax.swing.BorderFactory;

import jandl.wizard.pane.TextPane;

public class WizardText extends WizardBase {
	/**
	 * serialVersionUID = YYYYMMDD
	 */
	public static final long serialVersionUID = WizardBase.serialVersionUID;

	private TextPane textPane;
	
	public WizardText(String title){
		this(title, null, true);
	}

	public WizardText(String title, String textFile){
		this(title, null, textFile);
	}
	
	public WizardText(String title, String imageFile, boolean empty){
		super(title, imageFile, false);
		init();
	}
	
	public WizardText(String title, String imageFile, String textFile){
		super(title, imageFile, false);
		init();
		textPane.loadTextFrom(textFile);
	}

	public WizardText(String title, String imageFile, Reader reader){
		super(title, imageFile, false);
		init();
		textPane.loadTextFrom(reader);
	}
	
	private void init() {
		System.out.println("WizardText.<init>(" + this.getTitle() + ")");
		textPane = new TextPane();
		textPane.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
		add(textPane, "Center");
	}

	public void setText(String text) {
		textPane.getInternalJTextArea().setText(text);
	}

	public void append(String text) {
		textPane.getInternalJTextArea().append(text);
	}
	
	public void loadTextFrom(String textFile) {
		textPane.loadTextFrom(textFile);
	}

	public void loadTextFrom(Reader reader) {
		textPane.loadTextFrom(reader);
	}
	
	@Override
	protected void bNextClick(ActionEvent evt) {
		System.out.println("@Override");
		Data data = Data.instance();
		textPane.dumpOn(this.getName(), data);
		super.bNextClick(evt);
	}

}