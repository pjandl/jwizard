package jandl.wizard;

import java.util.Hashtable;
import java.util.Set;

public class Data implements Collector {
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

	public Double getAsDouble(String key) {
		return Double.valueOf((String)table.get(key));
	}

	public Integer getAsInteger(String key) {
		return Integer.valueOf((String)table.get(key));
	}

	public String getAsString(String key) {
		return (String)table.get(key);
	}

	public void put(String key, Object value) {
		table.put(key, value);
		System.out.println(key + "=" + value);
	}

	@Override
	public String toString() {
		return table.toString();
	}
}