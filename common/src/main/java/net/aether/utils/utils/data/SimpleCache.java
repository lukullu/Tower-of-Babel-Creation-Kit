package net.aether.utils.utils.data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A simple cache, that automatically evicts the oldest entry.<br/>
 * @param <K> the fieldType of keys
 * @param <V> the fieldType of values
 * @author Kilix
 */
public class SimpleCache<K, V> extends LinkedHashMap<K, V> {
	
	private final int size;
	/** @param size the number of elements to cache, sizes smaller than 1 will result in size 1 */
	public SimpleCache(int size) {
		super(Math.max(2, size + 1), 1);
		this.size = Math.max(1, size);
	}
	@Override protected boolean removeEldestEntry(Map.Entry<K, V> eldest) { return size() > size; }
	
}
