package jandl.wizard;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class WizardBase extends JFrame {
	/**
	 * serialVersionUID = YYYYMMDDv
	 */
	private static final long serialVersionUID = 202103210L;
	/**
	* autonumbering
	*/
	private static int wizardNumber = 0;

	private JLabel lImage;
	private JButton bBack, bNext;
	private WizardBase backWizard, nextWizard;
	private boolean end;
	private ArrayList<Consumer<Object>> preProcessorList;
	private ArrayList<Consumer<Object>> postProcessorList;
	
	public WizardBase(){
		this("The Wizard Base");
	}

	public WizardBase(String title){
		this(title, "/resources/wizard-base.png");
	}

	public WizardBase(String title, String imageFile){
		super(title);
		this.setName("Wizard" + (++wizardNumber));
		System.out.println("WizardBase.<init>(" + title + "), " + this.getName());
		// Lateral Image
		URL url = this.getClass().getResource(imageFile);
		if (url != null) {
			lImage = new JLabel(new ImageIcon(url));
		} else {
			lImage = new JLabel(new ImageIcon(imageFile));
		}
		lImage.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 0), BorderFactory.createEtchedBorder()));
		add(lImage, "West");
		// Botton Button Panel
		JPanel bbp = new JPanel(new BorderLayout());
		bbp.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		bBack = new JButton("Anterior");
		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				bBackClick(evt);
			}
		});
		bNext = new JButton("Pr\u00f3ximo");
		bNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				bNextClick(evt);
			}
		});
		bbp.add(bBack, "West");
		bbp.add(bNext, "East");
		add(bbp, "South");
		// Ajustes
		end = true;
		setSize(640,480);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				 Object[] options = { "Sim", "N\u00e3o" };
				 int res = JOptionPane.showOptionDialog(WizardBase.this, 
				 			"Deseja encerrar a aplica\u00e7\u00e3o?",
				 			"Confirma\u00e7\u00e3o",
				             JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
				             null, options, options[1]);
				 if (res==0) {
					 System.exit(0);
				 }
			}
		});
		bbpLinkCheck();
		preProcessorList = new ArrayList<>();
		postProcessorList = new ArrayList<>();
	}

	private void bbpLinkCheck() {
		bBack.setVisible(backWizard != null);
		if (this.end) {
			bNext.setVisible(true);
			bNext.setText("Fim");
		} else {
			bNext.setVisible(nextWizard != null);
			bNext.setText("Pr\u00f3ximo");
		}
	}

	public void setImage(String imageFile) {
		lImage.setIcon(new ImageIcon(imageFile));
	}

	public void backWizard(WizardBase link) {
		backWizard = link;
		if (link != null) {
			link.nextWizard = this;
			link.bbpLinkCheck();
		}
		this.bbpLinkCheck();
	}

	public void nextWizard(WizardBase link) {
		nextWizard = link;
		if (link != null) {
			link.backWizard = this;
			link.bbpLinkCheck();
		}
		end = link == null;
		this.bbpLinkCheck();
	}

	public void doBackClick() {
		bBackClick(null);
	}

	public void doNextClick() {
		bNextClick(null);
	}

	public void addPreProcessor(Consumer<Object> processor) {
		if (processor != null) {
			preProcessorList.add(processor);
		}
	}

	public void addPostProcessor(Consumer<Object> processor) {
		if (processor != null) {
			postProcessorList.add(processor);
		}
	}

	protected void bBackClick(ActionEvent evt) {
		System.out.println(this.getName() + ".bBackClick()");
		backWizard.setLocation(this.getLocation());
		backWizard.setVisible(true);
		this.setVisible(false);
	}

	protected void bNextClick(ActionEvent evt) {
		if (end) {
			System.out.println(this.getName() + ".bNextClick() --> End");
			System.exit(0);
		} else {
			System.out.println(this.getName() + ".bNextClick()");
			try {
				for (Consumer<Object> processor: this.postProcessorList) {
					processor.accept(evt);
				}
				for (Consumer<Object> processor: nextWizard.preProcessorList) {
					processor.accept(evt);
				}
			} catch (Exception exc) {
				System.out.println(this.getName() + ":\n" + exc.toString());
			}
			nextWizard.setLocation(this.getLocation());
			nextWizard.setVisible(true);
			this.setVisible(false);
		}
	}

}