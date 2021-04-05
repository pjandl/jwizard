package jandl.wizard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Classe base para cria��o de assistentes, janelas padronizadas que
 * podem ser encadeadas para formar uma aplica��es capazes de colher
 * dados do usu�rio, efetuando seu processamento/tratamento, para
 * realiza��o de tarefas simples.
 * � uma customiza��o da class {@link javax.swing.JFrame} , que oferece um 
 * painel lateral esquerdo para exibi��o de imagem customiz�vel,
 * uma �rea central que podem conter um componente de entrada
 * especializado ou uma combina��o deles, al�m de um painel inferior
 * contendo dois bot�es para navega��o para frente e para tr�s numa
 * sequ�ncia encade�vel de assistentes.
 *
 * @author pjandl
 */
public class WizardBase extends JFrame {
	/**
	 * N�mero de vers�o serial �nico do framework, no formato YYYYMMDD.
	 */
	public static final long serialVersionUID = 20210331L;
	/**
	* Autonumera��o dos assistentes (wizards).
	*/
	private static int wizardNumber = 0;
	/**
	 * R�tulo para exibi��o da imagem lateral esquerda dos assistentes.
	 */
	private JLabel lImage;
	/**
	 * Bot�o para navega��o para assistente anterior.
	 */
	private JButton bBack;
	/**
	 * Bot�o para navega��o para assistente posterior.
	 */
	private JButton bNext;
	/**
	 * Painel de rolagem para suportar conte�do maior do que a �rea central
	 * dispon�vel do assistente (que tem dimens�es fixas: 640 x 480 pixels).
	 */
	private JScrollPane scrollPane;
	/**
	 * Refer�ncia para assistente anterior.
	 */
	private WizardBase backWizard;
	/**
	 * Refer�ncia para assistente posterior.
	 */
	private WizardBase nextWizard;
	/**
	 * Lista de objetos pr� processadores do assistente.
	 */
	private ArrayList<Consumer<WizardBase>> preProcessorList;
	/**
	 * Lista de objetos p�s processadores do assistente.
	 */
	private ArrayList<Consumer<WizardBase>> postProcessorList;
	
	/**
	 * Construtor default. Define t�tulo padr�o do assistente, com imagem lateral
	 * padr�o (que n�o preenche toda janela). 
	 */
	public WizardBase(){
		this("WizardBase", null, false);
	}

	/**
	 * Construtor parametrizado com t�tulo do assistente. Usa imagem lateral
	 * padr�o (que n�o preenche toda janela).
	 * 
	 * @param title t�tulo da janela do assistente
	 */
	public WizardBase(String title){
		this(title, null, false);
	}
	
	/**
	 * Construtor parametrizado com t�tulo do assistente e nome do arquivo para
	 * imagem lateral (que n�o preenche toda janela). O nome do arquivo pode ser
	 * fornecido de maneira relativa ou absoluta, seguindo as conven��es da 
	 * plataforma em uso.
	 * 
	 * @param title t�tulo da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral.
	 */
	public WizardBase(String title, String imageFile){
		this(title, imageFile, false);
	}

	/**
	 * Construtor parametrizado com t�tulo do assistente, nome do arquivo para
	 * imagem lateral e indica��o de modo de preenchimento. O nome do arquivo 
	 * pode ser fornecido de maneira relativa ou absoluta, seguindo as conven��es
	 * da plataforma em uso. O modo de preenchimento permite definir que a imagem
	 * usada preenche toda a janela.
	 * 
	 * @param title t�tulo da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral.
	 * @param fill modo de preenchimento: <b>true</b> indica que imagem preenche janela,
	 *             <b>false</b> indica imagem lateral (30% da janela) 
	 */
	public WizardBase(String title, String imageFile, boolean fill){
		super(title);
		this.setName("Wizard" + (++wizardNumber));
		System.out.println("WizardBase.<init>(" + title + "), " + this.getName());
		// Imagem lateral ou central
		this.setImage(imageFile);
		scrollPane = new JScrollPane(lImage);
		if (fill) {
			scrollPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
			add(scrollPane, "Center");
		} else {
			scrollPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 0), BorderFactory.createEtchedBorder()));
			scrollPane.setPreferredSize(new Dimension(280, 440));
			add(scrollPane, "West");
		}
		// Panel inferior de bot�es
		JPanel bbp = new JPanel(new BorderLayout());
		bbp.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		bBack = new JButton("Anterior");
		bBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				bBackClick(evt);
			}
		});
		bNext = new JButton("Pr\u00f3ximo");
		bNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				bNextClick(evt);
			}
		});
		bbp.add(bBack, "West");
		bbp.add(bNext, "East");
		add(bbp, "South");
		// Listas de listeners para pr� e p�s processamento da janela
		preProcessorList = new ArrayList<>();
		postProcessorList = new ArrayList<>();
		// Ajustes gerais
		setSize(640, 480);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				 Object[] options = { "Sim", "N\u00e3o" };
				 int res = JOptionPane.showOptionDialog(WizardBase.this, 
				 			"Deseja encerrar a aplica\u00e7\u00e3o?",
				 			"Confirma\u00e7\u00e3o",
				             JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
				             null, options, options[1]);
				 if (res==0) {
					 System.exit(0);
				 }
			}
		});
		buttonsLinkCheck();
	}

	/**
	 * Adiciona listener de p�s processamento � janela. Todos os listener adicionados
	 * ser�o executados na finaliza��o de uma p�gina, ou seja, ap�s o acionamento de
	 * seu bot�o Pr�ximo.
	 * N�o existe p�s-processamento para a janela final (�ltima).
	 * 
	 * @param processor implementa��o da interface funcional Consumer<WizardBase>.
	 */
	public void addPostProcessor(Consumer<WizardBase> processor) {
		if (processor != null) {
			postProcessorList.add(processor);
		}
	}

	/**
	 * Adiciona listener de pr� processamento � janela. Todos os listener adicionados
	 * ser�o executados na inicializa��o de uma p�gina, ou seja, antes de sua exibi��o
	 * em decorr�ncia do acionamento do bot�o Pr�ximo da p�gina anterior.
	 * N�o existe pr�-processamento para a janela inicial (primeira).
	 * 
	 * @param processor implementa��o da interface funcional Consumer<WizardBase>.
	 */
	public void addPreProcessor(Consumer<WizardBase> processor) {
		if (processor != null) {
			preProcessorList.add(processor);
		}
	}

	/**
	 * Define a janela imediatamente anterior na qual esta janela est� encadeada.
	 * 
	 * @param link refer�ncia para janela anterior
	 * @return refer�ncia para janela anterior, permitindo uso encadeado deste m�todo.
	 */
	public WizardBase backWizard(WizardBase link) {
		backWizard = link;
		if (link != null) {
			link.nextWizard = this;
			link.buttonsLinkCheck();
		}
		this.buttonsLinkCheck();
		return link;
	}

	/**
	 * Programa��o das a��es realizadas em decorr�ncia do acionamento do bot�o
	 * Anterior:
	 * <ol>
	 * <li>reposicionamento e exibi��o da janela anterior (se registrada).</li>
	 * </ol>
	 * @param evt evento gerado pelo processador de eventos Swing
	 */
	protected void bBackClick(ActionEvent evt) {
		System.out.println(this.getName() + ".bBackClick()");
		backWizard.setLocation(this.getLocation());
		backWizard.setVisible(true);
		this.setVisible(false);
	}

	/**
	 * M�todo interno para ativa��o/inativa��o dos bot�es de navega��o
	 * conforme o encadeamento das janelas.
	 */
	private void buttonsLinkCheck() {
		bBack.setVisible(backWizard != null);
		bNext.setVisible(true);
		if (this.nextWizard == null) {
			bNext.setText("Fim");
		} else {
			bNext.setText("Pr\u00f3ximo");
		}
	}

	/**
	 * Programa��o das a��es realizadas em decorr�ncia do acionamento do bot�o
	 * Pr�ximo:
	 * <ol>
	 * <li>execu��o dos p�s-processadores registrados para esta janela;</li>
	 * <li>execu��o dos pr�-processadores registrados para a pr�xima janela 
	 * (se registrada);</li>
	 * <li>reposicionamento e exibi��o da pr�xima janela (se registrada);</li>
	 * <li>encerramento da aplica��o se n�o existe pr�xima janela registrada.</li>
	 * </ol>
	 * @param evt evento gerado pelo processador de eventos Swing
	 */
	protected void bNextClick(ActionEvent evt) {
		if (this.nextWizard == null) {
			System.out.println("WizardBase.bNextClick() --> End");
			System.exit(0);
		} else {
			System.out.println("WizardBase.bNextClick()");
			try {
				for (Consumer<WizardBase> processor: this.postProcessorList) {
					// processor.accept(evt);
					processor.accept(this);
				}
				for (Consumer<WizardBase> processor: nextWizard.preProcessorList) {
					// processor.accept(evt);
					processor.accept(this.nextWizard);
				}
				nextWizard.setLocation(this.getLocation());
				nextWizard.setVisible(true);
				this.setVisible(false);
			} catch (Exception exc) {
				System.out.println(this.getName() + ":\n" + exc.toString());
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}

	/**
	 * Permite o acionamento program�tico do bot�o Anterior.
	 */
	public void doBackClick() {
		bBackClick(null);
	}

	/**
	 * Permite o acionamento program�tico do bot�o Pr�ximo.
	 */
	public void doNextClick() {
		bNextClick(null);
	}

	/**
	 * Define a janela imediatamente posterior na qual esta janela est� encadeada.
	 * 
	 * @param link refer�ncia para janela posterior
	 * @return refer�ncia para janela posterior, permitindo uso encadeado deste m�todo.
	 */
	public WizardBase nextWizard(WizardBase link) {
		nextWizard = link;
		if (link != null) {
			link.backWizard = this;
			link.buttonsLinkCheck();
		}
		this.buttonsLinkCheck();
		return link;
	}

	/**
	 * Permite a defini��o ou redefini��o da imagem lateral da janela do assistente.
	 * A imagem � colocada num painel de rolagem, permitindo a exibi��o de imagens 
	 * maiores do que o espa�o efetivamente dispon�vel.
	 *  
	 * @param imageFile nome do arquivo (pode incluir caminho) correspondente � imagem
	 * PNG, JPG ou GIF a ser exibida pelo assistente.
	 */
	public void setImage(String imageFile) {
		System.out.println("setImage(" + imageFile + ")");
		if (imageFile == null) {
			URL url = this.getClass().getResource("/resources/wizard-base.png");
			lImage = new JLabel(new ImageIcon(url));
			if (scrollPane != null) scrollPane.setViewportView(lImage);
			return;
		} else if (imageFile.startsWith("!")) {
			URL url = this.getClass().getResource(imageFile.substring(1));
			if (url != null) {
				lImage = new JLabel(new ImageIcon(url));
				if (scrollPane != null) scrollPane.setViewportView(lImage);
				return;
			}
		} else {
			File file = new File(imageFile);
			System.out.println(file + " exists? " + file.exists());
			try {
				URL url = new URL("file:"+imageFile);
				lImage = new JLabel(new ImageIcon(url));
				if (scrollPane != null) scrollPane.setViewportView(lImage);
				return;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
//			if (file.exists()) {
//				lImage = new JLabel(new ImageIcon(imageFile));
//				if (scrollPane != null) scrollPane.setViewportView(lImage);
//				return;
//			}
		}
		String msg = String.format("java.io.FileNotFoundException: %s (No such file or directory)",
				imageFile);
		lImage = new JLabel(msg);
		if (scrollPane != null) scrollPane.setViewportView(lImage);
		System.out.println(msg);
	}

}