/**
 * Pacote que contém a hieraquia de classes dos assistentes do framework JWizard.
 * 
 * <p>Os assistentes (<i>wizards</i>) são janelas padronizadas que podem ser 
 * encadeadas para formar aplicações capazes de colher dados do usuário, 
 * efetuar seu processamento/tratamento e, assim, realizar tarefas simples.</p>
 * 
 * <p>A classe {@link jandl.wizard.WizardBase} é a base da hierarquia de assistentes,
 * oferecendo sua infraestrutura comum, compartilhada pelas classes {@link jandl.wizard.WizardText},
 * {@link jandl.wizard.WizardField} e {@link jandl.wizard.WizardList}. 
 * 
 * <p>A classe {@link jandl.wizard.WizardFactory} é uma fábrica de assistentes que
 * pode facilitar sua obtenção (instanciação básica).</p>
 * 
 * <p>A classe {@link jandl.wizard.WizardCustom} é um assistente customizável, 
 * ao qual podem ser adicionados paineis diversos (do pacote {@link jandl.wizard.pane}).</p>
 *  
 */
package jandl.wizard;