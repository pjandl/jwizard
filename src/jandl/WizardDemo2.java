package jandl;

import javax.swing.SwingUtilities;

import jandl.wizard.Data;
import jandl.wizard.WizardBase;
import jandl.wizard.WizardCustom;
import jandl.wizard.WizardFactory;
import jandl.wizard.WizardText;

public class WizardDemo2 {
	public static void main(String[] args) throws Exception {
		// Primeira janela
		WizardBase wb1 = WizardFactory.createBase("Pagina 01");
		// Segunda janela
		WizardCustom wb2 = new WizardCustom("Pagina 02");
		wb2.setImage("./images/tux-darth-maul.png");
		wb2.addListPane("opt", "Opções", new String[]{"Java", "Python", "C#"});
		wb2.addTextPane("loren-ipsun.txt");
		wb2.addVerticalGlue();
		// terceira janela
		WizardBase wb3 = WizardFactory.createText("Pagina 03", 
				"./images/tux-darth-vader.png", true);
		// Encadeamento
		wb1.nextWizard(wb2);
		wb2.nextWizard(wb3);
		// Pre e pos processamento
		wb2.addPostProcessor((wiz) -> wb2PostProcessor(wiz));
		wb3.addPreProcessor((wiz) -> wb3PreProcessor(wiz));
		// Acionamento da aplicacao
		SwingUtilities.invokeLater(() -> wb1.setVisible(true));
	}

	public static void wb2PostProcessor(WizardBase wizard) {
		System.out.println("wb2PostProcessor for " + wizard.getName());
	}
	
	public static void wb3PreProcessor(WizardBase wizard) {
		System.out.println("wb3PreProcessor for " + wizard.getName());
		Data data = Data.instance();
		System.out.println(data);
		WizardText wt = (WizardText) wizard;
		wt.setText(data.toString());
	}
	
}