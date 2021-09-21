package jandl.wizard;

import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jandl.wizard.pane.ListPane;

/**
 * Classe especializada na criação de assistentes para exibição de listas
 * de opções. 
 * Instâncias de WizardList são encadeáveis com outras instâncias de 
 * WizardBase, para compor aplicações capazes de colher dados do usuário, 
 * efetuar seu processamento/tratamento e, assim, realizar tarefas simples.
 * É uma customização da classe {@link jandl.wizard.WizardBase}, 
 * que oferece um painel central onde é exibida uma lista de opções que podem ser
 * selecionadas pelo usuário de três modos distintos:
 * <ol>
 * <li>seleção de um único item (<b>padrão</b>);</li>
 * <li>seleção de um intervalo contínuo de itens;</li>
 * <li>seleção livre de múltiplos itens.</li>
 * </ol>
 * Também inclui um painel lateral esquerdo para exibição de imagem customizável,
 * além de um painel inferior contendo dois botões para navegação para frente e 
 * para trás numa sequência encadeável de assistentes.
 * 
 * @author pjandl
 */
public class WizardList extends WizardBase {
	/**
	 * Número de versão serial único do framework, no formato YYYYMMDD.
	 */
	public static final long serialVersionUID = WizardBase.serialVersionUID;
	/**
	 * Componente interno especializado, responsável pela exibição da 
	 * lista de itens.
	 */
	private ListPane<?> listPane;

	/**
	 * Construtor parametrizado com <i>tag</i> (identificador da lista de itens),
	 * <i>label</i> (texto de orientação para usuário) e <i>array</i> de itens da
	 * lista. A <i>tag</i> deve ser única na aplicação.
	 * Usa o título e imagem padrões do assistente.
	 * 
	 * @param tag identificador da lista de itens
	 * @param label texto de orientação para usuário
	 * @param list <i>array</i> de itens da lista
	 */
	public <T> WizardList(String tag, String label, T[] list){
		this("WizardList", null, tag, label, list);
	}

	/**
	 * Construtor parametrizado com título, <i>tag</i> (identificador da lista de itens),
	 * <i>label</i> (texto de orientação para usuário) e <i>array</i> de itens da
	 * lista. A <i>tag</i> deve ser única na aplicação.
	 * Usa a imagem padrão do assistente.
	 * 
	 * @param title título da janela do assistente
	 * @param tag identificador da lista de itens
	 * @param label texto de orientação para usuário
	 * @param list <i>array</i> de itens da lista
	 */
	public WizardList(String title, String tag, String label, Object[] list){
		this(title, null, tag, label, list);
	}

	/**
	 * Construtor parametrizado com título, nome do arquivo da imagem lateral,
	 * <i>tag</i> (identificador da lista de itens),
	 * <i>label</i> (texto de orientação para usuário) e <i>array</i> de itens da
	 * lista. A <i>tag</i> deve ser única na aplicação.
	 * O nome do arquivo pode ser fornecido de maneira relativa ou absoluta, 
	 * seguindo as convenções da plataforma em uso.
	 * 
	 * @param title título da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral
	 * @param tag identificador da lista de itens
	 * @param label texto de orientação para usuário
	 * @param list <i>array</i> de itens da lista
	 */
	public <T> WizardList(String title, String imageFile, String tag, String label, T[] list){
		super(title, imageFile);
		System.out.println("WizardList.<init>(" + title + ")");
		listPane = new ListPane<T>(tag, label, list);
		listPane.setName("listPane0");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(listPane);
		panel.add(Box.createVerticalGlue());
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
		add(scrollPane, "Center");
	}

	/**
	 * Efetua o despejo (<i>dump</i>) dos dados deste 
	 * assistente, no depósito de dados automaticamente associado.
	 * No caso, os dados despejados são o item selecionado na lista 
	 * e seu índice correspondente.
	 * O processamento segue com o tratamento padrão do acionamento
	 * do botão Próximo.
	 * 
	 * @param evt evento gerado pelo processador de eventos Swing
	 */
	@Override
	protected void bNextClick(ActionEvent evt) {
		System.out.printf("%s.bNextClick() @Override\n", this.getName());
		Data data = Data.instance();
		listPane.dumpOn(this.getName(), data);
		super.bNextClick(evt);
	}

}