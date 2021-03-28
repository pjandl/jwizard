package jandl;

import java.util.Arrays;

import javax.swing.SwingUtilities;

import jandl.wizard.Data;
import jandl.wizard.WizardBase;
import jandl.wizard.WizardFactory;
import jandl.wizard.WizardText;

public class WizardDemo {
	public static void main(String[] args) throws Exception {
		// Primeira janela
		WizardBase wb1 = WizardFactory.createBase("Pagina 01");
		// Segunda janela
		WizardBase wb2 = WizardFactory.createText("Pagina 02", "loren-ipsun.txt");
		wb2.setImage("C:/Users/pjand/Pictures/bulb-crossed-by-pencil.png");
		// wb2.setImage("/Users/Jandl/Pictures/independence-day.jpg");
		//wb2.setImage("./images/tux-darth-maul.png");
		wb2.addPostProcessor((wiz) -> wb2PostProcessor(wiz));
		// terceira janela
		String[] tag = { "X1", "Y1", "X2", "Y2"};
		String[] label = { "Ponto 1, coordenada x", "Ponto 1, coordenada y", 
				"Ponto 2, coordenada x", "Ponto 2, coordenada y"};
		WizardBase wb3 = WizardFactory.createField("Pagina 03", tag, label, label);
		wb3.setImage("./images/tux-luke-skywalker.png");
		wb3.addPostProcessor((wiz) -> wb3PostProcessor(wiz));
		// Quarta janela
		String[] opcoes = {"Sim", "Talvez", "Nao"};
		WizardBase wb4 = WizardFactory.createList("Pagina 04", "opt", "Opcoes", opcoes);
		// Quinta janela
		WizardBase wb5 = WizardFactory.createText("Pagina 05",  "./images/tux-darth-vader.png", true);
		wb5.addPreProcessor((obj) -> wb4PreProcessor(wb5));
		// Encadeamento
		wb1.nextWizard(wb2);
		wb2.nextWizard(wb3);
		wb3.nextWizard(wb4);
		wb4.nextWizard(wb5);
		// Acionamento da aplicacao
		SwingUtilities.invokeLater(() -> wb1.setVisible(true));
	}

	public static void wb2PostProcessor(WizardBase wizard) {
		System.out.println("wb2PostProcessor for " + wizard.getName());
	}
	
	public static void wb3PostProcessor(WizardBase wizard) {
		System.out.println("wb3PostProcessor");
		Data data = Data.instance();
		double x1 = data.getAsDouble("Wizard3.X1");
		double y1 = data.getAsDouble("Wizard3.Y1");
		double x2 = data.getAsDouble("Wizard3.X2");
		double y2 = data.getAsDouble("Wizard3.Y2");
		double distancia = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
		data.put("distancia", distancia);
		System.out.println(data);
	}
	
	public static void wb4PreProcessor(WizardBase wizard) {
		System.out.println("wb4PreProcessor");
		WizardText wizardText = (WizardText)wizard;
		wizardText.setText(Data.instance().toString());
		wizardText.append("\n\nDistancia = " + Data.instance().get("distancia"));
		int[] index = (int[]) Data.instance().get("Wizard4.opt.indices");
		wizardText.append("\n\nIndices = " + Arrays.toString(index));
		wizardText.append("\n\nSelecao = " + Data.instance().get("Wizard4.opt.selectedValues"));
	}
}