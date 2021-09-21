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
 * Classe especializada na criação de assistentes para exibição de
 * formulários contendo um ou mais campos. 
 * Instâncias de WizardField são encadeáveis com outras instâncias de 
 * WizardBase, para compor aplicações capazes de colher dados do usuário, 
 * efetuar seu processamento/tratamento e, assim, realizar tarefas simples.
 * É uma customização da classe {@link jandl.wizard.WizardBase}, 
 * que oferece um painel central onde são exibidos um ou mais campos
 * destinados a entrada de dados. Cada campo possui:
 * <ol>
 * <li>Um identificador próprio (<i>tag</i>);</li>
 * <li>Um rótulo para orientação do usuário (<i>label</i>);</li>
 * <li>Um campo para entrada de dados ({@link javax.swing.JTextField});</li>
 * <li>Um texto complementar de descrição ou orientação (<i>tip</i>).</li>
 * </ol>
 * As ações de pós-processamento deste assistente devem, preferencialmente,
 * validar a entrada de dados fornecidos nos campos.
 * Também inclui um painel lateral esquerdo para exibição de imagem customizável,
 * além de um painel inferior contendo dois botões para navegação para frente e 
 * para trás numa sequência encadeável de assistentes.
 * 
 * @author pjandl
 */
public class WizardCustom extends WizardBase {
	/**
	 * Número de versão serial único do framework, no formato YYYYMMDD.
	 */
	public static final long serialVersionUID = WizardBase.serialVersionUID;
	/**
	 * Painel interno para organizar um ou mais componentes especializados
	 * que podem ser adicionados arbitrariamente ao assistente.
	 */
	private JPanel panel;

	/**
	 * Construtor parametrizado com título do assistente.
	 * Usa a imagem padrão do assistente.
	 * 
	 * @param title título da janela do assistente
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
	 * à área central do assistente.
	 * Requer uma <i>tag</i> (identificador da lista de itens),
	 * <i>label</i> (texto de orientação para usuário) e <i>array</i> de itens da
	 * lista.
	 * 
	 * @param tag identificador da lista de itens
	 * @param label texto de orientação para usuário
	 * @param list <i>array</i> de itens da lista
	 */
	public <T> void addListPane(String tag, String label, T[] list) {
		ListPane<T> listPane = new ListPane<>(tag, label, list);
		listPane.setName("ListPane" + panel.getComponentCount());
		panel.add(listPane);
	}
	
	/**
	 * Adiciona um painel de texto {@link jandl.wizard.pane.TextPane}
	 * à área central do assistente.
	 * Requer o nome do arquivo que contém o texto a ser exibido.
	 * 
	 * @param textFile nome do arquivo com conteúdo de texto
	 */
	public void addTextPane(String textFile) {
		TextPane textPane = new TextPane();
		textPane.setName("TextPane" + panel.getComponentCount());
		textPane.loadTextFrom(textFile);
		panel.add(textPane);
	}
	
	/**
	  * Adiciona um espaçador transparente, conveniente para melhorar o
	  * alinhamento dos paineis adicionados à área central do assistente.
	  */
	public void addVerticalGlue() {
		panel.add(Box.createVerticalGlue());
	}
	
	/**
	 * Efetua o despejo (<i>dump</i>) dos dados deste 
	 * assistente, no depósito de dados automaticamente associado.
	 * No caso, os dados despejados são conteúdo de todos os componentes
	 * adicionados à área central do assistente.
	 * O processamento segue com o tratamento padrão do acionamento
	 * do botão Próximo.
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
