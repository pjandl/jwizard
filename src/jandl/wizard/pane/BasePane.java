package jandl.wizard.pane;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import jandl.wizard.Collector;
import jandl.wizard.WizardBase;

public abstract class BasePane extends JPanel {
	/**
	 * serialVersionUID = YYYYMMDD
	 */
	protected static final long serialVersionUID = WizardBase.serialVersionUID;
	
	private String tag;
	
	public BasePane() {
		super(new BorderLayout());
	}

	public BasePane(LayoutManager lm) {
		super(lm);
	}
	
	public abstract void dumpOn(String tag, Collector collector);
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		if (tag == null || tag.length()==0) {
			throw new IllegalArgumentException("empty or null tag");
		}
		this.tag = tag;
	}

}
