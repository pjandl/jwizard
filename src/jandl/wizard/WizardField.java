package jandl.wizard;

import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jandl.wizard.pane.FieldPane;

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
public class WizardField extends WizardBase {
	/**
	 * N�mero de vers�o serial �nico do framework, no formato YYYYMMDD.
	 */
	public static final long serialVersionUID = WizardBase.serialVersionUID;
	/**
	 * Componente interno especializado, respons�vel pela exibi��o 
	 * do painel de campos do formul�rio.
	 */
	private FieldPane fieldPane;

	/**
	 * Construtor parametrizado com t�tulo do assistente, nome do
	 * arquivo de imagem do painel lateral, <i>array</i> de <i>tags</i>
	 * (identificadores dos campos do formul�rio), <i>array</i> de 
	 * <i>labels</i> (texto de orienta��o para usu�rio) e 
	 * <i>array</i> de dicas para os campos.
	 * A <i>tag</i> deve ser �nica dentro do formul�rio.
	 * 
	 * @param title t�tulo da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral
	 * @param tag <i>array</i> de identificadores dos campos
	 * @param label <i>array</i> de texto de orienta��o para usu�rio
	 * @param tip <i>array</i> de dicas para os campos
	 */
	public WizardField(String title, String imageFile, String[] tag, 
			String[] label, String[] tip){
		super(title, imageFile);
		System.out.println("WizardField.<init>(" + title + ")");
		fieldPane = new FieldPane(tag, label, tip);
		fieldPane.setName("fieldPane0");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(fieldPane);
		panel.add(Box.createVerticalGlue());
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
		add(scrollPane, "Center");
	}

	/**
	 * Construtor parametrizado com t�tulo do assistente, <i>array</i> 
	 * de <i>tags</i> (identificadores dos campos do formul�rio), 
	 * <i>array</i> de <i>labels</i> (texto de orienta��o para usu�rio).
	 * A <i>tag</i> deve ser �nica dentro do formul�rio.
	 * Os campos n�o exibir�o dicas para o usu�rio.
	 * Usa a imagem padr�o do assistente.
	 * 
	 * @param title t�tulo da janela do assistente
	 * @param tag <i>array</i> de identificadores dos campos
	 * @param label <i>array</i> de texto de orienta��o para usu�rio
	 */
	public WizardField(String title, String[] tag, String[] label){
		this(title, null, tag, label, null);
	}

	/**
	 * Construtor parametrizado <i>array</i> de <i>tags</i> 
	 * (identificadores dos campos do formul�rio) e <i>array</i>   
	 * de <i>labels</i> (texto de orienta��o para usu�rio).
	 * A <i>tag</i> deve ser �nica dentro do formul�rio.
	 * Os campos n�o exibir�o dicas para o usu�rio.
	 * Usa o t�tulo e imagem padr�es do assistente.
	 * 
	 * @param title t�tulo da janela do assistente
	 * @param tag <i>array</i> de identificadores dos campos
	 * @param label <i>array</i> de texto de orienta��o para usu�rio
	 */
	public WizardField(String[] tag, String[] label){
		this("WizardField", null, tag, label, null);
	}

	/**
	 * Efetua o despejo (<i>dump</i>) dos dados deste 
	 * assistente, no dep�sito de dados automaticamente associado.
	 * No caso, os dados despejados s�o conte�do de todos os campos 
	 * dos formul�rio, identificados por sua <i>tag</i>.
	 * O processamento segue com o tratamento padr�o do acionamento
	 * do bot�o Pr�ximo.
	 * 
	 * @param evt evento gerado pelo processador de eventos Swing
	 */
	@Override
	protected void bNextClick(ActionEvent evt) {
		System.out.printf("%s.bNextClick() @Override\n", this.getName());
		Data data = Data.instance();
		fieldPane.dumpOn(this.getName(), data);
		super.bNextClick(evt);
	}

}