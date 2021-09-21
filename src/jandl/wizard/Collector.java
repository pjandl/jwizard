package jandl.wizard;

/**
 * Interface que estabele o conjunto mínimo de operações que um coletor
 * de dados deve oferecer.
 * Um coletor de dados deve permitir a retenção de informações entre
 * as diferentes janelas dos assistentes.  
 */
public interface Collector {
	/**
	 * Adiciona um par chave-valor (key-value) ao sistema de armazenamento.
	 * 
	 * @param key a chave é uma String que denomina o valor armazenado
	 * @param value é um objeto associado à chave
	 */
	public void put(String key, Object value);
	
	/**
	 * Recupera o valor da chave (key) informada.
	 * 
	 * @param key a chave é uma String que denomina o valor armazenado
	 * @return o objeto associado à chave (quando existir)
	 */
	public Object get(String key);
}
