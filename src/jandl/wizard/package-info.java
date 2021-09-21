/**
 * Pacote que cont�m a hieraquia de classes dos assistentes do framework JWizard.
 * 
 * <p>Os assistentes (<i>wizards</i>) s�o janelas padronizadas que podem ser 
 * encadeadas para formar aplica��es capazes de colher dados do usu�rio, 
 * efetuar seu processamento/tratamento e, assim, realizar tarefas simples.</p>
 * 
 * <p>A classe {@link jandl.wizard.WizardBase} � a base da hierarquia de assistentes,
 * oferecendo sua infraestrutura comum, compartilhada pelas classes {@link jandl.wizard.WizardText},
 * {@link jandl.wizard.WizardField} e {@link jandl.wizard.WizardList}. 
 * 
 * <p>A classe {@link jandl.wizard.WizardFactory} � uma f�brica de assistentes que
 * pode facilitar sua obten��o (instancia��o b�sica).</p>
 * 
 * <p>A classe {@link jandl.wizard.WizardCustom} � um assistente customiz�vel, 
 * ao qual podem ser adicionados paineis diversos (do pacote {@link jandl.wizard.pane}).</p>
 *  
 */
package jandl.wizard;