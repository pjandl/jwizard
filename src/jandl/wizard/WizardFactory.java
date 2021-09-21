package jandl.wizard;

/**
  * Classe que oferece um conjunto de m�todos-f�brica para facilitar a
  * cria��o de assistentes de tipo b�sico, especializados e customizados.
  */
public final class WizardFactory {

	/**
	 * Cria um assistente b�sico {@link jandl.wizard.WizardBase},
	 * com o t�tulo indicado e a imagem lateral padr�o.
	 *
	 * @param title t�tulo da janela do assistente
	 */
	public static WizardBase createBase(String title) {
		return new WizardBase(title, null, true);
	}
	
	/**
	 * Cria um assistente b�sico {@link jandl.wizard.WizardBase},
	 * com o t�tulo indicado e a imagem lateral cujo nome do arquivo � indicado.
	 *
	 * @param title t�tulo da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral
	 */
	public static WizardBase createBase(String title, String imageFile) {
		return new WizardBase(title, imageFile, true);
	}

	/**
	 * Cria um assistente customiz�vel {@link jandl.wizard.WizardCustom},
	 * com o t�tulo indicado e a imagem lateral padr�o.
	 *
	 * @param title t�tulo da janela do assistente
	 */
	public static WizardBase createCustom(String title) {
		return new WizardCustom(title);
	}

	/**
	 * Cria um assistente de exibição de formulário {@link jandl.wizard.WizardField},
	 * com o título indicado, além do <i>array</i> de <i>tags</i>
	 * (identificadores dos campos do formulário), <i>array</i> de 
	 * <i>labels</i> (texto de orientação para usuário) e 
	 * <i>array</i> de dicas para os campos.
	 * 
	 * @param title título da janela do assistente
	 * @param tag <i>array</i> de identificadores dos campos
	 * @param label <i>array</i> de texto de orientação para usuário
	 * @param tip <i>array</i> de dicas para os campos
	 */
	public static WizardBase createField(String title, String[] tag,  String[] label, String[] tip) {
		return new WizardField(title, null, tag, label, tip);
	}

	/**
	 * Cria um assistente de exibição de lista {@link jandl.wizard.WizardList},
	 * com o título indicado, além da <i>tag</i> (identificador) da lista, 
	 * <i>label</i> (texto de orientação para usuário) e 
	 * <i>array</i> com os itens da lista.
	 * 
	 * @param title título da janela do assistente
	 * @param tag identificador da lista de itens
	 * @param label texto de orientação para usuário
	 * @param list <i>array</i> de itens da lista
	 */
	public static <T> WizardBase createList(String title, String tag, String label, T[] list) {
		return new WizardList(title, tag, label, list);
	}

	/**
	 * Cria um assistente de exibição de texto {@link jandl.wizard.WizardText},
	 * com o título indicado e com o conteúdo de texto obtido do arquivo indicado.
	 * Utiliza a imagem padrão dos assistentes.
	 *
	 * @param title título da janela do assistente
	 * @param textFile nome do arquivo com conteúdo de texto
	 */
	public static WizardBase createText(String title, String textFile) {
		return new WizardText(title, textFile);
	}

	/**
	 * Cria um assistente de exibição de texto {@link jandl.wizard.WizardText},
	 * com o título indicado e com a imagem lateral indicada e com a área de texto
	 * vazia.
	 *
	 * @param title título da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral
	 * @param empty <i>flag</i> lógica para indicar que área de texto estará vazia
	 */
	public static WizardBase createText(String title, String imgFile, boolean empty) {
		return new WizardText(title, imgFile, true);
	}
}
