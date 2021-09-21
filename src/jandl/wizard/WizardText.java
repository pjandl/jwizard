package jandl.wizard;

import java.awt.event.ActionEvent;
import java.io.Reader;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import jandl.wizard.pane.TextPane;

/**
 * Classe especializada na criação de assistentes para exibição de texto.
 * Instâncias de WizardText são encadeáveis com outras instâncias de 
 * WizardBase, para compor aplicações capazes de colher dados do usuário, 
 * efetuar seu processamento/tratamento e, assim, realizar tarefas simples.
 * É uma customização da classe {@link jandl.wizard.WizardBase}, 
 * que oferece um painel central onde:
 * <ol>
 * <li>pode ser exibido um texto, não editável, obtido de um arquivo ou
 * {@link java.io.Reader};</li>
 * <li>podem ser direcionadas mensagens quaisquer geradas pela execução dos
 * pré e pós processares de cada assistente.</li>
 * </ol>
 * Também inclui um painel lateral esquerdo para exibição de imagem customizável,
 * além de um painel inferior contendo dois botões para navegação para frente e 
 * para trás numa sequência encadeável de assistentes.
 * 
 * @author pjandl
 */
public class WizardText extends WizardBase {
	/**
	 * Número de versão serial único do framework, no formato YYYYMMDD.
	 */
	public static final long serialVersionUID = WizardBase.serialVersionUID;
	/**
	 * Componente interno especializado, responsável pela exibição do texto.
	 */
	private TextPane textPane;
	
	/**
	 * Construtor parametrizado com título do assistente. Usa imagem lateral
	 * padrão (que não preenche toda janela).
	 * 
	 * @param title título da janela do assistente
	 */
	public WizardText(String title){
		this(title, null, true);
	}

	/**
	 * Construtor parametrizado com título do assistente e nome do arquivo cujo 
	 * texto será exibido na área central. 
	 * O nome do arquivo pode ser fornecido de maneira relativa ou absoluta, 
	 * seguindo as convenções da plataforma em uso.
	 * A imagem lateral exibida é a padrão dos assistentes.
	 * 
	 * @param title título da janela do assistente
	 * @param textFile nome do arquivo com conteúdo de texto
	 */
	public WizardText(String title, String textFile){
		this(title, null, textFile);
	}
	
	/**
	 * Construtor parametrizado com título do assistente, nome do arquivo da
	 * imagem lateral e uma <i>flag</i> lógica para indicar o que a área de texto
	 * estará inicialmente vazia.
	 * O nome do arquivo pode ser fornecido de maneira relativa ou absoluta, 
	 * seguindo as convenções da plataforma em uso.
	 * 
	 * @param title título da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral
	 * @param empty <i>flag</i> lógica para indicar que área de texto estará vazia
	 */
	public WizardText(String title, String imageFile, boolean empty){
		super(title, imageFile, false);
		init();
	}
	
	/**
	 * Construtor parametrizado com título do assistente, nome do arquivo da
	 * imagem lateral e o Reader do qual o conteúdo de texto de exibição deverá 
	 * ser obtido lógica.
	 * O nome do arquivo pode ser fornecido de maneira relativa ou absoluta, 
	 * seguindo as convenções da plataforma em uso.
	 * 
	 * @param title título da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral
	 * @param Reader reader que constitui a fonte do texto a ser exibido
	 */
	public WizardText(String title, String imageFile, Reader reader){
		super(title, imageFile, false);
		init();
		loadTextFrom(reader);
	}

	/**
	 * Construtor parametrizado com título do assistente, nome do arquivo da
	 * imagem lateral e nome do arquivo do texto de exibição.
	 * O nome do arquivo pode ser fornecido de maneira relativa ou absoluta, 
	 * seguindo as convenções da plataforma em uso.
	 * 
	 * @param title título da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral
	 * @param textFile nome do arquivo com conteúdo de texto
	 */
	public WizardText(String title, String imageFile, String textFile){
		super(title, imageFile, false);
		init();
		loadTextFrom(textFile);
	}
	
	/**
	 * Anexa o texto fornecido ao final do conteúdo de texto exibido na 
	 * área central do assistente.
	 * 
	 * @param text texto a ser anexado ao conteúdo atual do assistente
	 */
	public void append(String text) {
		textPane.append(text);
	}

	/**
	 * Efetua o despejo (<i>dump</i>) dos dados deste 
	 * assistente, no depósito de dados automaticamente associado.
	 * No caso, os dados despejados são o conteúdo de texto 
	 * (carregado ou gerado).
	 * O processamento segue com o tratamento padrão do acionamento
	 * do botão Próximo.
	 * 
	 * @param evt evento gerado pelo processador de eventos Swing
	 */
	@Override
	protected void bNextClick(ActionEvent evt) {
		System.out.printf("%s.bNextClick() @Override\n", this.getName());
		Data data = Data.instance();
		textPane.dumpOn(this.getName(), data);
		super.bNextClick(evt);
	}

	/**
	 * Preparo inicial deste assistente, i.e., instanciação do painel de 
	 * texto, sua adição em painel de rolagem, definição de bordas e 
	 * posicionamento na área central do assistente. 
	 */
	private void init() {
		System.out.println("WizardText.<init>(" + this.getTitle() + ")");
		textPane = new TextPane();
		textPane.setName("textPane0");
		JScrollPane scrollPane = new JScrollPane(textPane); 
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
		add(scrollPane, "Center");
	}
	
	/**
	 * Efetua o carregamento do conteúdo de texto exibido no painel de
	 * texto a partir da fonte indicada (um {@link java.io.Reader}).
	 * O conteúdo de texto anterior é substituído.
	 * 
	 * @param reader fonte para conteúdo de texto exibido pelo assistente
	 */
	public void loadTextFrom(Reader reader) {
		try {
			textPane.loadTextFrom(reader);
		} catch (RuntimeException e) {
			textPane.setText(e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Efetua o carregamento do conteúdo de texto exibido no painel de
	 * texto a partir do arquivo indicado.
	 * O conteúdo de texto anterior é substituído.
	 * 
	 * @param textFile nome do arquivo que contém o texto exibido pelo assistente
	 */
	public void loadTextFrom(String textFile) {
		try {
			textPane.loadTextFrom(textFile);
		} catch (RuntimeException e) {
			textPane.setText(e.getMessage());
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Usa o texto fornecido como  conteúdo de texto exibido na 
	 * área central do assistente.
	 * O conteúdo de texto anterior é substituído.
	 *
	 * @param text texto a ser usado como conteúdo atual do assistente
	 */
	public void setText(String text) {
		textPane.setText(text);
	}

}