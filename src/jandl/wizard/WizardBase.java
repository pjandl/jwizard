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
 * Classe base para criação de assistentes, janelas padronizadas que
 * podem ser encadeadas para formar uma aplicações capazes de colher
 * dados do usuário, efetuando seu processamento/tratamento, para
 * realização de tarefas simples.
 * É uma customização da class {@link javax.swing.JFrame} , que oferece um 
 * painel lateral esquerdo para exibição de imagem customizável,
 * uma área central que podem conter um componente de entrada
 * especializado ou uma combinação deles, além de um painel inferior
 * contendo dois botões para navegação para frente e para trás numa
 * sequência encadeável de assistentes.
 *
 * @author pjandl
 */
public class WizardBase extends JFrame {
	/**
	 * Número de versão serial único do framework, no formato YYYYMMDD.
	 */
	public static final long serialVersionUID = 20210331L;
	/**
	* Autonumeração dos assistentes (wizards).
	*/
	private static int wizardNumber = 0;
	/**
	 * Rótulo para exibição da imagem lateral esquerda dos assistentes.
	 */
	private JLabel lImage;
	/**
	 * Botão para navegação para assistente anterior.
	 */
	private JButton bBack;
	/**
	 * Botão para navegação para assistente posterior.
	 */
	private JButton bNext;
	/**
	 * Painel de rolagem para suportar conteúdo maior do que a área central
	 * disponível do assistente (que tem dimensões fixas: 640 x 480 pixels).
	 */
	private JScrollPane scrollPane;
	/**
	 * Referência para assistente anterior.
	 */
	private WizardBase backWizard;
	/**
	 * Referência para assistente posterior.
	 */
	private WizardBase nextWizard;
	/**
	 * Lista de objetos pré processadores do assistente.
	 */
	private ArrayList<Consumer<WizardBase>> preProcessorList;
	/**
	 * Lista de objetos pós processadores do assistente.
	 */
	private ArrayList<Consumer<WizardBase>> postProcessorList;
	
	/**
	 * Construtor default. Define título padrão do assistente, com imagem lateral
	 * padrão (que não preenche toda janela). 
	 */
	public WizardBase(){
		this("WizardBase", null, false);
	}

	/**
	 * Construtor parametrizado com título do assistente. Usa imagem lateral
	 * padrão (que não preenche toda janela).
	 * 
	 * @param title título da janela do assistente
	 */
	public WizardBase(String title){
		this(title, null, false);
	}
	
	/**
	 * Construtor parametrizado com título do assistente e nome do arquivo para
	 * imagem lateral (que não preenche toda janela). O nome do arquivo pode ser
	 * fornecido de maneira relativa ou absoluta, seguindo as convenções da 
	 * plataforma em uso.
	 * 
	 * @param title título da janela do assistente
	 * @param imageFile nome do arquivo da imagem lateral.
	 */
	public WizardBase(String title, String imageFile){
		this(title, imageFile, false);
	}

	/**
	 * Construtor parametrizado com título do assistente, nome do arquivo para
	 * imagem lateral e indicação de modo de preenchimento. O nome do arquivo 
	 * pode ser fornecido de maneira relativa ou absoluta, seguindo as convenções
	 * da plataforma em uso. O modo de preenchimento permite definir que a imagem
	 * usada preenche toda a janela.
	 * 
	 * @param title título da janela do assistente
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
		// Panel inferior de botões
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
		// Listas de listeners para pré e pós processamento da janela
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
	 * Adiciona listener de pós processamento à janela. Todos os listener adicionados
	 * serão executados na finalização de uma página, ou seja, após o acionamento de
	 * seu botão Próximo.
	 * Não existe pós-processamento para a janela final (última).
	 * 
	 * @param processor implementação da interface funcional Consumer<WizardBase>.
	 */
	public void addPostProcessor(Consumer<WizardBase> processor) {
		if (processor != null) {
			postProcessorList.add(processor);
		}
	}

	/**
	 * Adiciona listener de pré processamento à janela. Todos os listener adicionados
	 * serão executados na inicialização de uma página, ou seja, antes de sua exibição
	 * em decorrência do acionamento do botão Próximo da página anterior.
	 * Não existe pré-processamento para a janela inicial (primeira).
	 * 
	 * @param processor implementação da interface funcional Consumer<WizardBase>.
	 */
	public void addPreProcessor(Consumer<WizardBase> processor) {
		if (processor != null) {
			preProcessorList.add(processor);
		}
	}

	/**
	 * Define a janela imediatamente anterior na qual esta janela está encadeada.
	 * 
	 * @param link referência para janela anterior
	 * @return referência para janela anterior, permitindo uso encadeado deste método.
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
	 * Programação das ações realizadas em decorrência do acionamento do botão
	 * Anterior:
	 * <ol>
	 * <li>reposicionamento e exibição da janela anterior (se registrada).</li>
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
	 * Método interno para ativação/inativação dos botões de navegação
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
	 * Programação das ações realizadas em decorrência do acionamento do botão
	 * Próximo:
	 * <ol>
	 * <li>execução dos pós-processadores registrados para esta janela;</li>
	 * <li>execução dos pré-processadores registrados para a próxima janela 
	 * (se registrada);</li>
	 * <li>reposicionamento e exibição da próxima janela (se registrada);</li>
	 * <li>encerramento da aplicação se não existe próxima janela registrada.</li>
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
	 * Permite o acionamento programático do botão Anterior.
	 */
	public void doBackClick() {
		bBackClick(null);
	}

	/**
	 * Permite o acionamento programático do botão Próximo.
	 */
	public void doNextClick() {
		bNextClick(null);
	}

	/**
	 * Define a janela imediatamente posterior na qual esta janela está encadeada.
	 * 
	 * @param link referência para janela posterior
	 * @return referência para janela posterior, permitindo uso encadeado deste método.
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
	 * Permite a definição ou redefinição da imagem lateral da janela do assistente.
	 * A imagem é colocada num painel de rolagem, permitindo a exibição de imagens 
	 * maiores do que o espaço efetivamente disponível.
	 *  
	 * @param imageFile nome do arquivo (pode incluir caminho) correspondente à imagem
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