package jandl.wizard;

import java.util.Hashtable;
import java.util.Set;

public class Data {
	private static Data instance;

	private Hashtable<String, Object> table;

	private Data() {
		table = new Hashtable<>();
	} 

	public static Data instance() {
		if (instance == null) {
			instance = new Data();
		}
		return instance;
	}
	
	public Set<String> keys() {
		return table.keySet();
	}

	public Object get(String key) {
		return table.get(key);
	}

	public void put(String key, Object value) {
		table.put(key, value);
	}

	@Override
	public String toString() {
		return table.toString();
	}
}