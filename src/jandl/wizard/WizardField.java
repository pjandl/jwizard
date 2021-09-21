package jandl.wizard;

import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jandl.wizard.pane.FieldPane;

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
public class WizardField extends WizardBase {
	/**
	 * Número de versão serial único do framework, no formato YYYYMMDD.
	 */
	public static final long serialVersionUID = WizardBase.serialVersionUID;
	/**
	 * Componente interno especializado, responsável pela exibição 
	 * do painel de campos do formulário.
	 */
	private FieldPane fieldPane;

	/**
	 * Construtor parametrizado com título do assistente, nome do
	 * arquivo de imagem do painel lateral, <i>array</i> de <i>tags</i>
	 * (identificadores dos campos do formulário), <i>array</i> de 
	 * <i>labels</i> (texto de orientação para usuário) e 
	 * <i>array</i> de dicas para os campos.
	 * A <i>tag</i> deve ser única dentro do formulário.
	 * 
	 * @param title título da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral
	 * @param tag <i>array</i> de identificadores dos campos
	 * @param label <i>array</i> de texto de orientação para usuário
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
	 * Construtor parametrizado com título do assistente, <i>array</i> 
	 * de <i>tags</i> (identificadores dos campos do formulário), 
	 * <i>array</i> de <i>labels</i> (texto de orientação para usuário).
	 * A <i>tag</i> deve ser única dentro do formulário.
	 * Os campos não exibirão dicas para o usuário.
	 * Usa a imagem padrão do assistente.
	 * 
	 * @param title título da janela do assistente
	 * @param tag <i>array</i> de identificadores dos campos
	 * @param label <i>array</i> de texto de orientação para usuário
	 */
	public WizardField(String title, String[] tag, String[] label){
		this(title, null, tag, label, null);
	}

	/**
	 * Construtor parametrizado <i>array</i> de <i>tags</i> 
	 * (identificadores dos campos do formulário) e <i>array</i>   
	 * de <i>labels</i> (texto de orientação para usuário).
	 * A <i>tag</i> deve ser única dentro do formulário.
	 * Os campos não exibirão dicas para o usuário.
	 * Usa o título e imagem padrões do assistente.
	 * 
	 * @param title título da janela do assistente
	 * @param tag <i>array</i> de identificadores dos campos
	 * @param label <i>array</i> de texto de orientação para usuário
	 */
	public WizardField(String[] tag, String[] label){
		this("WizardField", null, tag, label, null);
	}

	/**
	 * Efetua o despejo (<i>dump</i>) dos dados deste 
	 * assistente, no depósito de dados automaticamente associado.
	 * No caso, os dados despejados são conteúdo de todos os campos 
	 * dos formulário, identificados por sua <i>tag</i>.
	 * O processamento segue com o tratamento padrão do acionamento
	 * do botão Próximo.
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