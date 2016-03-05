package blog.cache;

import org.springframework.cache.Cache;

/**
 * Created by dengwei on 16/3/4.
 */
public interface KVCache extends Cache {
	void remove(Object key);
}
