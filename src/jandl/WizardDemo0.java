package jandl;

import java.util.Arrays;

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
		WizardBase wb2 = WizardFactory.createText("Pagina 02", "!/resources/loren-ipsun.txt");
		wb2.setImage("!/resources/tux-c3po.jpg");
		// terceira janela
		String[] tag = { "Nome", "SobreNome", "Curso"};
		String[] label = { "Nome", "Sobrenome", "Curso"};
		WizardBase wb3 = WizardFactory.createField("Pagina 03", tag, label, label);
		wb3.setImage("!/resources/tux-darth-maul.jpg");
		// Quarta janela
		String[] opcoes = {"Sim", "Talvez", "Nao"};
		WizardBase wb4 = WizardFactory.createList("Pagina 04", "opt", "Opcoes", opcoes);
		wb4.setImage("!/resources/tux-darth-vader.jpg");
		// Quinta janela
		WizardBase wb5 = WizardFactory.createText("Pagina 05", 
				"!/resources/tux-luke-skywalker.jpg", true);
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
		String[] key = data.keys().toArray(new String[0]);
		Arrays.sort(key);
		for(String k : key) {
			wizardText.append(String.format("%s = %s\n", k, data.get(k)));
		}
	}
}