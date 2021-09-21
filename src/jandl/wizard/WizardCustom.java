package jandl.wizard;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jandl.wizard.pane.BasePane;
import jandl.wizard.pane.ListPane;
import jandl.wizard.pane.TextPane;

/**
 * Classe especializada na cria��o de assistentes para exibi��o de
 * formul�rios contendo um ou mais campos. 
 * Inst�ncias de WizardField s�o encade�veis com outras inst�ncias de 
 * WizardBase, para compor aplica��es capazes de colher dados do usu�rio, 
 * efetuar seu processamento/tratamento e, assim, realizar tarefas simples.
 * � uma customiza��o da classe {@link jandl.wizard.WizardBase}, 
 * que oferece um painel central onde s�o exibidos um ou mais campos
 * destinados a entrada de dados. Cada campo possui:
 * <ol>
 * <li>Um identificador pr�prio (<i>tag</i>);</li>
 * <li>Um r�tulo para orienta��o do usu�rio (<i>label</i>);</li>
 * <li>Um campo para entrada de dados ({@link javax.swing.JTextField});</li>
 * <li>Um texto complementar de descri��o ou orienta��o (<i>tip</i>).</li>
 * </ol>
 * As a��es de p�s-processamento deste assistente devem, preferencialmente,
 * validar a entrada de dados fornecidos nos campos.
 * Tamb�m inclui um painel lateral esquerdo para exibi��o de imagem customiz�vel,
 * al�m de um painel inferior contendo dois bot�es para navega��o para frente e 
 * para tr�s numa sequ�ncia encade�vel de assistentes.
 * 
 * @author pjandl
 */
public class WizardCustom extends WizardBase {
	/**
	 * N�mero de vers�o serial �nico do framework, no formato YYYYMMDD.
	 */
	public static final long serialVersionUID = WizardBase.serialVersionUID;
	/**
	 * Painel interno para organizar um ou mais componentes especializados
	 * que podem ser adicionados arbitrariamente ao assistente.
	 */
	private JPanel panel;

	/**
	 * Construtor parametrizado com t�tulo do assistente.
	 * Usa a imagem padr�o do assistente.
	 * 
	 * @param title t�tulo da janela do assistente
	 */
public WizardCustom(String title) {
		super(title);
		System.out.println("WizardField.<init>(" + title + ")");
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
		add(scrollPane, "Center");
	}
	
	/**
	 * Adiciona um painel de lista {@link jandl.wizard.pane.ListPane}
	 * � �rea central do assistente.
	 * Requer uma <i>tag</i> (identificador da lista de itens),
	 * <i>label</i> (texto de orienta��o para usu�rio) e <i>array</i> de itens da
	 * lista.
	 * 
	 * @param tag identificador da lista de itens
	 * @param label texto de orienta��o para usu�rio
	 * @param list <i>array</i> de itens da lista
	 */
	public <T> void addListPane(String tag, String label, T[] list) {
		ListPane<T> listPane = new ListPane<>(tag, label, list);
		listPane.setName("ListPane" + panel.getComponentCount());
		panel.add(listPane);
	}
	
	/**
	 * Adiciona um painel de texto {@link jandl.wizard.pane.TextPane}
	 * � �rea central do assistente.
	 * Requer o nome do arquivo que cont�m o texto a ser exibido.
	 * 
	 * @param textFile nome do arquivo com conte�do de texto
	 */
	public void addTextPane(String textFile) {
		TextPane textPane = new TextPane();
		textPane.setName("TextPane" + panel.getComponentCount());
		textPane.loadTextFrom(textFile);
		panel.add(textPane);
	}
	
	/**
	  * Adiciona um espa�ador transparente, conveniente para melhorar o
	  * alinhamento dos paineis adicionados � �rea central do assistente.
	  */
	public void addVerticalGlue() {
		panel.add(Box.createVerticalGlue());
	}
	
	/**
	 * Efetua o despejo (<i>dump</i>) dos dados deste 
	 * assistente, no dep�sito de dados automaticamente associado.
	 * No caso, os dados despejados s�o conte�do de todos os componentes
	 * adicionados � �rea central do assistente.
	 * O processamento segue com o tratamento padr�o do acionamento
	 * do bot�o Pr�ximo.
	 * 
	 * @param evt evento gerado pelo processador de eventos Swing
	 */
	@Override
	protected void bNextClick(ActionEvent evt) {
		System.out.printf("%s.bNextClick() @Override\n", this.getName());
		Data data = Data.instance();
		System.out.println(panel.getComponentCount());
		for(int c = 0; c < panel.getComponentCount(); c++) {
			Component component = panel.getComponent(c);
			System.out.println(component.getName());
			if (component instanceof BasePane) {
				BasePane pane = (BasePane) component;
				pane.dumpOn(getName(), data);
			}
		}
		super.bNextClick(evt);
	}

}
