package jandl;

import java.io.FileReader;

import javax.swing.SwingUtilities;

import jandl.wizard.Data;
import jandl.wizard.WizardBase;
import jandl.wizard.WizardField;
import jandl.wizard.WizardText;

public class WizardDemo {
	public static void main(String[] args) throws Exception {
		// Primeira janela
		WizardBase wb1 = new WizardBase("Wiz 01");
		// Segunda janela
		WizardText wb2 = new WizardText("Wiz 02");
		wb2.setImage("./images/tux-darth-maul.png");
		wb2.loadTextFrom(new FileReader("habilidades.txt"));
		wb2.addPostProcessor((obj) -> wb2PostProcessor(obj));
		// terceira janela
		String[] tag = { "Latitude", "Longitude", "Lat", "Long", 
				"Lalala", "Lololo", "Latitude2", "Longitude2", "Latitude3", "Longitude4" };
		WizardField wb3 = new WizardField("Wiz 03", tag, tag);
		wb3.setImage("./images/tux-luke-skywalker.png");
		wb3.addPostProcessor((obj) -> wb3PostProcessor(obj));
		// Quarta janela
		WizardText wb4 = new WizardText("Wiz 04", null, "./images/tux-darth-vader.png");
		wb4.addPreProcessor((obj) -> wb4PreProcessor(wb4));
		// Encadeamento
		wb1.nextWizard(wb2);
		wb2.nextWizard(wb3);
		wb3.nextWizard(wb4);
		// Acionamento da aplicação
		SwingUtilities.invokeLater(() -> wb1.setVisible(true));
	}

	public static void wb2PostProcessor(Object obj) {
		System.out.println("wb2PostProcessor");
	}
	public static void wb3PostProcessor(Object obj) {
		System.out.println("wb3PostProcessor");
		Data data = Data.instance();
		int latitude = Integer.parseInt((String)data.get("Wizard3.Latitude"));
		int longitude = Integer.parseInt((String)data.get("Wizard3.Longitude"));
		data.put("TW.total", latitude * longitude);
		System.out.println(data);
	}
	public static void wb4PreProcessor(Object obj) {
		System.out.println("wb4PreProcessor");
		WizardText wizard = (WizardText)obj;
		wizard.setText(Data.instance().toString());
		wizard.addText("\n\nTW.total = " + Data.instance().get("TW.total"));
	}
}