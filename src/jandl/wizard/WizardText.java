package jandl.wizard;

import java.awt.event.ActionEvent;
import java.io.Reader;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import jandl.wizard.pane.TextPane;

/**
 * Classe especializada na cria��o de assistentes para exibi��o de texto.
 * Inst�ncias de WizardText s�o encade�veis com outras inst�ncias de 
 * WizardBase, para compor aplica��es capazes de colher dados do usu�rio, 
 * efetuar seu processamento/tratamento e, assim, realizar tarefas simples.
 * � uma customiza��o da classe {@link jandl.wizard.WizardBase}, 
 * que oferece um painel central onde:
 * <ol>
 * <li>pode ser exibido um texto, n�o edit�vel, obtido de um arquivo ou
 * {@link java.io.Reader};</li>
 * <li>podem ser direcionadas mensagens quaisquer geradas pela execu��o dos
 * pr� e p�s processares de cada assistente.</li>
 * </ol>
 * Tamb�m inclui um painel lateral esquerdo para exibi��o de imagem customiz�vel,
 * al�m de um painel inferior contendo dois bot�es para navega��o para frente e 
 * para tr�s numa sequ�ncia encade�vel de assistentes.
 * 
 * @author pjandl
 */
public class WizardText extends WizardBase {
	/**
	 * N�mero de vers�o serial �nico do framework, no formato YYYYMMDD.
	 */
	public static final long serialVersionUID = WizardBase.serialVersionUID;
	/**
	 * Componente interno especializado, respons�vel pela exibi��o do texto.
	 */
	private TextPane textPane;
	
	/**
	 * Construtor parametrizado com t�tulo do assistente. Usa imagem lateral
	 * padr�o (que n�o preenche toda janela).
	 * 
	 * @param title t�tulo da janela do assistente
	 */
	public WizardText(String title){
		this(title, null, true);
	}

	/**
	 * Construtor parametrizado com t�tulo do assistente e nome do arquivo cujo 
	 * texto ser� exibido na �rea central. 
	 * O nome do arquivo pode ser fornecido de maneira relativa ou absoluta, 
	 * seguindo as conven��es da plataforma em uso.
	 * A imagem lateral exibida � a padr�o dos assistentes.
	 * 
	 * @param title t�tulo da janela do assistente
	 * @param textFile nome do arquivo com conte�do de texto
	 */
	public WizardText(String title, String textFile){
		this(title, null, textFile);
	}
	
	/**
	 * Construtor parametrizado com t�tulo do assistente, nome do arquivo da
	 * imagem lateral e uma <i>flag</i> l�gica para indicar o que a �rea de texto
	 * estar� inicialmente vazia.
	 * O nome do arquivo pode ser fornecido de maneira relativa ou absoluta, 
	 * seguindo as conven��es da plataforma em uso.
	 * 
	 * @param title t�tulo da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral
	 * @param empty <i>flag</i> l�gica para indicar que �rea de texto estar� vazia
	 */
	public WizardText(String title, String imageFile, boolean empty){
		super(title, imageFile, false);
		init();
	}
	
	/**
	 * Construtor parametrizado com t�tulo do assistente, nome do arquivo da
	 * imagem lateral e o Reader do qual o conte�do de texto de exibi��o dever� 
	 * ser obtido l�gica.
	 * O nome do arquivo pode ser fornecido de maneira relativa ou absoluta, 
	 * seguindo as conven��es da plataforma em uso.
	 * 
	 * @param title t�tulo da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral
	 * @param Reader reader que constitui a fonte do texto a ser exibido
	 */
	public WizardText(String title, String imageFile, Reader reader){
		super(title, imageFile, false);
		init();
		loadTextFrom(reader);
	}

	/**
	 * Construtor parametrizado com t�tulo do assistente, nome do arquivo da
	 * imagem lateral e nome do arquivo do texto de exibi��o.
	 * O nome do arquivo pode ser fornecido de maneira relativa ou absoluta, 
	 * seguindo as conven��es da plataforma em uso.
	 * 
	 * @param title t�tulo da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral
	 * @param textFile nome do arquivo com conte�do de texto
	 */
	public WizardText(String title, String imageFile, String textFile){
		super(title, imageFile, false);
		init();
		loadTextFrom(textFile);
	}
	
	/**
	 * Anexa o texto fornecido ao final do conte�do de texto exibido na 
	 * �rea central do assistente.
	 * 
	 * @param text texto a ser anexado ao conte�do atual do assistente
	 */
	public void append(String text) {
		textPane.append(text);
	}

	/**
	 * Efetua o despejo (<i>dump</i>) dos dados deste 
	 * assistente, no dep�sito de dados automaticamente associado.
	 * No caso, os dados despejados s�o o conte�do de texto 
	 * (carregado ou gerado).
	 * O processamento segue com o tratamento padr�o do acionamento
	 * do bot�o Pr�ximo.
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
	 * Preparo inicial deste assistente, i.e., instancia��o do painel de 
	 * texto, sua adi��o em painel de rolagem, defini��o de bordas e 
	 * posicionamento na �rea central do assistente. 
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
	 * Efetua o carregamento do conte�do de texto exibido no painel de
	 * texto a partir da fonte indicada (um {@link java.io.Reader}).
	 * O conte�do de texto anterior � substitu�do.
	 * 
	 * @param reader fonte para conte�do de texto exibido pelo assistente
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
	 * Efetua o carregamento do conte�do de texto exibido no painel de
	 * texto a partir do arquivo indicado.
	 * O conte�do de texto anterior � substitu�do.
	 * 
	 * @param textFile nome do arquivo que cont�m o texto exibido pelo assistente
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
	 * Usa o texto fornecido como  conte�do de texto exibido na 
	 * �rea central do assistente.
	 * O conte�do de texto anterior � substitu�do.
	 *
	 * @param text texto a ser usado como conte�do atual do assistente
	 */
	public void setText(String text) {
		textPane.setText(text);
	}

}