package jandl;

import javax.swing.SwingUtilities;

import jandl.wizard.Data;
import jandl.wizard.WizardBase;
import jandl.wizard.WizardFactory;
import jandl.wizard.WizardText;

public class WizardDemo1 {

	public static void main(String[] args) {
		// Primeira Janela
		WizardBase wb1 = WizardFactory.createText("Distância entre Dois Pontos 1/3",
				"distancia.txt");
		// Segunda Janela
		String[] tag = { "X1", "Y1", "X2", "Y2"};
		String[] label = { "Ponto 1, coordenada x", "Ponto 1, coordenada y", 
				"Ponto 2, coordenada x", "Ponto 2, coordenada y"};
		WizardBase wb2 = WizardFactory.createField("Distância entre Dois Pontos 2/3",
				tag, label, label);
		wb2.setImage("./images/tux-darth-vader.png");
		// terceira janela
		WizardBase wb3 = WizardFactory.createText("Distância entre Dois Pontos 3/3",
				"./images/tux-darth-maul.png", true);
		// Encadeamento
		wb1.nextWizard(wb2);
		wb2.nextWizard(wb3);
		// Pre e Pos processamento
		wb2.addPostProcessor((wiz) -> wb2PostProcessor(wiz));
		wb3.addPreProcessor((wiz) -> wb3PreProcessor(wiz));
		// Acionamento da aplicacao
		SwingUtilities.invokeLater(() -> wb1.setVisible(true));
	}

	public static void wb2PostProcessor(WizardBase wizard) {
		System.out.println("wb2PostProcessor");
		Data data = Data.instance();
		double x1 = data.getAsDouble("Wizard2.fieldPane0.X1");
		double y1 = data.getAsDouble("Wizard2.fieldPane0.Y1");
		double x2 = data.getAsDouble("Wizard2.fieldPane0.X2");
		double y2 = data.getAsDouble("Wizard2.fieldPane0.Y2");
		double distancia = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
		data.put("distancia", distancia);
		System.out.println(data);
	}
	
	public static void wb3PreProcessor(WizardBase wizard) {
		System.out.println("wb3PreProcessor");
		WizardText wizardText = (WizardText)wizard;
		wizardText.setText("Cálculo da Distância\n");
		Data data = Data.instance();
		wizardText.append("\nPonto 1\nx: " + data.get("Wizard2.fieldPane0.X1"));
		wizardText.append("\ny: " + data.get("Wizard2.fieldPane0.Y1"));
		wizardText.append("\nPonto 2\nx: " + data.get("Wizard2.fieldPane0.X2"));
		wizardText.append("\ny: " + data.get("Wizard2.fieldPane0.X2"));
		wizardText.append("\n\nDistancia = " + Data.instance().get("distancia"));
	}
}
