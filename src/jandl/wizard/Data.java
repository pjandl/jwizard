package jandl.wizard;

import java.util.Hashtable;
import java.util.Set;

/**
 * Classe que implementa um reposit�rio de dados compat�vel
 * com a interface {@link jandl.wizard.Collector}, ou seja,
 * que permite que os assistentes possam armazenar valores
 * que podem ser recuperados por outros assistentes sem 
 * qualquer intedepend�ncia em seu c�digo.
 * 
 * Os valores devem ser armazenados na forma de pares chave-valor 
 * (<i>key-value</i>), em um objeto de tipo 
 * {@link java.util.Hashtable}, de maneira que sua recupera��o 
 * � feita com o uso da chave de armazenamento.
 */
public class Data implements Collector {
	private static Data instance;

	private Hashtable<String, Object> table;

	private Data() {
		table = new Hashtable<>();
	} 

	/**
	 * Obt�m uma inst�ncia global do reposit�rio de dados
	 * dos assistentes.
	 * 
	 * @return inst�ncia do reposit�rio de dados
	 */
	public static Data instance() {
		if (instance == null) {
			instance = new Data();
		}
		return instance;
	}
	
	/**
	 * Obt�m um conjunto com as chaves (de tipo String)
	 * existentes no reposit�rio de dados.
	 * 
	 * @return conjunto com as chaves do reposit�rio de dados
	 */
	public Set<String> keys() {
		return table.keySet();
	}

	/**
	 * Recupera o objeto (dado) associado � chave (de tipo String)
	 * informada.
	 * 
	 * @param key chave do valor desejado
	 * @return dado associado � chave ou <b>null</b> caso n�o exista tal chave
	 */
	public Object get(String key) {
		return table.get(key);
	}

	/**
	 * Recupera o dado associado � chave (de tipo String)
	 * informada como um valor de tipo <b>Double</b>.
	 * 
	 * @param key chave do valor desejado
	 * @return dado associado � chave ou <b>null</b> caso n�o exista tal chave
	 */
	public Double getAsDouble(String key) {
		return Double.valueOf((String)table.get(key));
	}

	/**
	 * Recupera o dado associado � chave (de tipo String)
	 * informada como um valor de tipo <b>Integer</b>.
	 * 
	 * @param key chave do valor desejado
	 * @return dado associado � chave ou <b>null</b> caso n�o exista tal chave
	 */
	public Integer getAsInteger(String key) {
		return Integer.valueOf((String)table.get(key));
	}

	/**
	 * Recupera o dado associado � chave (de tipo String)
	 * informada como um valor de tipo <b>String</b>.
	 * 
	 * @param key chave do valor desejado
	 * @return dado associado � chave ou <b>null</b> caso n�o exista tal chave
	 */
	public String getAsString(String key) {
		return (String)table.get(key);
	}

	/**
	 * Armazena um objeto (valor) associado � uma chave 
	 * (de tipo String), permitindo sua recupera��o futura por
	 * qualquer outro assistente que tenha acesso ao reposit�rio
	 * de dados.
	 * @param key chave do valor armazenado
	 * @param value objeto associado � chave informada
	 */
	public void put(String key, Object value) {
		table.put(key, value);
		System.out.println(key + "=" + value);
	}

	/**
	 * Retorna a representa��o textual do reposit�rio de dados,
	 * no caso a representa��o de seu Hashtable interno. 
	 */
	@Override
	public String toString() {
		return table.toString();
	}
}