package blog.cache.impl;

import blog.cache.KVCache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.HashMap;

/**
 * Created by dengwei on 16/3/4.
 */
public class LocalCache implements KVCache {
	HashMap<Object, Object> map = new HashMap<Object, Object>();

	public void setName(String name) {
		this.name = name;
	}

	private String name;

	public void remove(Object key) {
		map.remove(key);
	}

	public String getName() {
		return name;
	}

	public Object getNativeCache() {
		return map;
	}

	public ValueWrapper get(Object key) {
		return new SimpleValueWrapper(map.get(key));
	}

	public <T> T get(Object key, Class<T> type) {
		return (T) get(key).get();
	}

	public void put(Object key, Object value) {
		map.put(key, value);
	}

	public ValueWrapper putIfAbsent(Object key, Object value) {
		map.putIfAbsent(key, value);
		return new SimpleValueWrapper(value);
	}

	public void evict(Object key) {
		map.remove(key);
	}

	public void clear() {
		map.clear();
	}
}
