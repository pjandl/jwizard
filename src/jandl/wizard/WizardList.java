package jandl.wizard;

import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jandl.wizard.pane.ListPane;

/**
 * Classe especializada na cria��o de assistentes para exibi��o de listas
 * de op��es. 
 * Inst�ncias de WizardList s�o encade�veis com outras inst�ncias de 
 * WizardBase, para compor aplica��es capazes de colher dados do usu�rio, 
 * efetuar seu processamento/tratamento e, assim, realizar tarefas simples.
 * � uma customiza��o da classe {@link jandl.wizard.WizardBase}, 
 * que oferece um painel central onde � exibida uma lista de op��es que podem ser
 * selecionadas pelo usu�rio de tr�s modos distintos:
 * <ol>
 * <li>sele��o de um �nico item (<b>padr�o</b>);</li>
 * <li>sele��o de um intervalo cont�nuo de itens;</li>
 * <li>sele��o livre de m�ltiplos itens.</li>
 * </ol>
 * Tamb�m inclui um painel lateral esquerdo para exibi��o de imagem customiz�vel,
 * al�m de um painel inferior contendo dois bot�es para navega��o para frente e 
 * para tr�s numa sequ�ncia encade�vel de assistentes.
 * 
 * @author pjandl
 */
public class WizardList extends WizardBase {
	/**
	 * N�mero de vers�o serial �nico do framework, no formato YYYYMMDD.
	 */
	public static final long serialVersionUID = WizardBase.serialVersionUID;
	/**
	 * Componente interno especializado, respons�vel pela exibi��o da 
	 * lista de itens.
	 */
	private ListPane<?> listPane;

	/**
	 * Construtor parametrizado com <i>tag</i> (identificador da lista de itens),
	 * <i>label</i> (texto de orienta��o para usu�rio) e <i>array</i> de itens da
	 * lista. A <i>tag</i> deve ser �nica na aplica��o.
	 * Usa o t�tulo e imagem padr�es do assistente.
	 * 
	 * @param tag identificador da lista de itens
	 * @param label texto de orienta��o para usu�rio
	 * @param list <i>array</i> de itens da lista
	 */
	public <T> WizardList(String tag, String label, T[] list){
		this("WizardList", null, tag, label, list);
	}

	/**
	 * Construtor parametrizado com t�tulo, <i>tag</i> (identificador da lista de itens),
	 * <i>label</i> (texto de orienta��o para usu�rio) e <i>array</i> de itens da
	 * lista. A <i>tag</i> deve ser �nica na aplica��o.
	 * Usa a imagem padr�o do assistente.
	 * 
	 * @param title t�tulo da janela do assistente
	 * @param tag identificador da lista de itens
	 * @param label texto de orienta��o para usu�rio
	 * @param list <i>array</i> de itens da lista
	 */
	public WizardList(String title, String tag, String label, Object[] list){
		this(title, null, tag, label, list);
	}

	/**
	 * Construtor parametrizado com t�tulo, nome do arquivo da imagem lateral,
	 * <i>tag</i> (identificador da lista de itens),
	 * <i>label</i> (texto de orienta��o para usu�rio) e <i>array</i> de itens da
	 * lista. A <i>tag</i> deve ser �nica na aplica��o.
	 * O nome do arquivo pode ser fornecido de maneira relativa ou absoluta, 
	 * seguindo as conven��es da plataforma em uso.
	 * 
	 * @param title t�tulo da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral
	 * @param tag identificador da lista de itens
	 * @param label texto de orienta��o para usu�rio
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
	 * assistente, no dep�sito de dados automaticamente associado.
	 * No caso, os dados despejados s�o o item selecionado na lista 
	 * e seu �ndice correspondente.
	 * O processamento segue com o tratamento padr�o do acionamento
	 * do bot�o Pr�ximo.
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