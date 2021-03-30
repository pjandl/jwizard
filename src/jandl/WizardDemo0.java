package jandl;

import javax.swing.SwingUtilities;

import jandl.wizard.Data;
import jandl.wizard.WizardBase;
import jandl.wizard.WizardFactory;
import jandl.wizard.WizardText;

public class WizardDemo0 {
	public static void main(String[] args) throws Exception {
		// Primeira janela
		WizardBase wb1 = WizardFactory.createBase("Pagina 01");
		// Segunda janela
		WizardBase wb2 = WizardFactory.createText("Pagina 02", "loren-ipsun.txt");
		wb2.setImage("C:/Users/pjand/Pictures/bulb-crossed-by-pencil.png");
		// terceira janela
		String[] tag = { "Nome", "SobreNome", "Curso"};
		String[] label = { "Nome", "Sobrenome", "Curso"};
		WizardBase wb3 = WizardFactory.createField("Pagina 03", tag, label, label);
		wb3.setImage("./images/tux-luke-skywalker.png");
		// Quarta janela
		String[] opcoes = {"Sim", "Talvez", "Nao"};
		WizardBase wb4 = WizardFactory.createList("Pagina 04", "opt", "Opcoes", opcoes);
		// Quinta janela
		WizardBase wb5 = WizardFactory.createText("Pagina 05", 
				"./images/tux-darth-vader.png", true);
		// Encadeamento
		wb1.nextWizard(wb2);
		wb2.nextWizard(wb3);
		wb3.nextWizard(wb4);
		wb4.nextWizard(wb5);
		// Pre e Pos processamento
		wb3.addPostProcessor((wiz) -> wb3PostProcessor(wiz));
		wb5.addPreProcessor((wiz) -> wb4PreProcessor(wiz));
		// Acionamento da aplicacao
		SwingUtilities.invokeLater(() -> wb1.setVisible(true));
	}

	public static void wb3PostProcessor(WizardBase wizard) {
		System.out.println("wb3PostProcessor for " + wizard.getName());
		Data data = Data.instance();
		System.out.println(data);
	}
	
	public static void wb4PreProcessor(WizardBase wizard) {
		System.out.println("wb4PreProcessor");
		WizardText wizardText = (WizardText)wizard;
		wizardText.setText("Auto-collected Data\n\n");
		Data data = Data.instance();
		for(String k : data.keys()) {
			wizardText.append(String.format("%s = %s\n", k, data.get(k)));
		}
	}
}