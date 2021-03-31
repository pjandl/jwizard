package jandl.wizard;

import java.awt.event.ActionEvent;
import java.io.Reader;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

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
	
	public WizardText(String title, String imageFile, Reader reader){
		super(title, imageFile, false);
		init();
		loadTextFrom(reader);
	}

	public WizardText(String title, String imageFile, String textFile){
		super(title, imageFile, false);
		init();
		loadTextFrom(textFile);
	}
	
	public void append(String text) {
		textPane.append(text);
	}

	@Override
	protected void bNextClick(ActionEvent evt) {
		System.out.printf("%s.bNextClick() @Override\n", this.getName());
		Data data = Data.instance();
		textPane.dumpOn(this.getName(), data);
		super.bNextClick(evt);
	}

	private void init() {
		System.out.println("WizardText.<init>(" + this.getTitle() + ")");
		textPane = new TextPane();
		textPane.setName("textPane0");
		JScrollPane scrollPane = new JScrollPane(textPane); 
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
		add(scrollPane, "Center");
	}
	
	public void loadTextFrom(Reader reader) {
		try {
			textPane.loadTextFrom(reader);
		} catch (RuntimeException e) {
			textPane.setText(e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	public void loadTextFrom(String textFile) {
		try {
			textPane.loadTextFrom(textFile);
		} catch (RuntimeException e) {
			textPane.setText(e.getMessage());
			System.out.println(e.getMessage());
		}
	}
	
	public void setText(String text) {
		textPane.setText(text);
	}

}