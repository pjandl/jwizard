package jandl.wizard;

/**
 * Interface que estabele o conjunto m�nimo de opera��es que um coletor
 * de dados deve oferecer.
 * Um coletor de dados deve permitir a reten��o de informa��es entre
 * as diferentes janelas dos assistentes.  
 */
public interface Collector {
	/**
	 * Adiciona um par chave-valor (key-value) ao sistema de armazenamento.
	 * 
	 * @param key a chave � uma String que denomina o valor armazenado
	 * @param value � um objeto associado � chave
	 */
	public void put(String key, Object value);
	
	/**
	 * Recupera o valor da chave (key) informada.
	 * 
	 * @param key a chave � uma String que denomina o valor armazenado
	 * @return o objeto associado � chave (quando existir)
	 */
	public Object get(String key);
}
