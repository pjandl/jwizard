package jandl.wizard;

public final class WizardFactory {

	public static WizardBase createBase(String title) {
		return new WizardBase(title, null, true);
	}
	
	public static WizardBase createBase(String title, String imageFile) {
		return new WizardBase(title, imageFile, true);
	}

	public static WizardBase createField(String title, String[] tag,  String[] label, String[] tip) {
		return new WizardField(title, null, tag, label, tip);
	}

	public static <T> WizardBase createList(String title, String tag, String label, T[] list) {
		return new WizardList(title, tag, label, list);
	}

	public static WizardBase createText(String title, String textFile) {
		return new WizardText(title, textFile);
	}

	public static WizardBase createText(String title, String imgFile, boolean empty) {
		return new WizardText(title, imgFile, true);
	}
}
