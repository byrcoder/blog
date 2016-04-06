package leetcode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 146
 * Created by dengwei on 2016/4/6.
 */
public class LRUCache {

    public static class Node<K, V> {
        K key;
        V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }

    List<Node<Integer, Integer>> nodes = new LinkedList<Node<Integer, Integer>>();
    int capcity;

    public LRUCache(int capacity) {
        this.capcity = capacity;
    }

    public int get(int key) {
        Node<Integer, Integer> target = null;
        Iterator<Node<Integer, Integer>> it = nodes.iterator();
        while(it.hasNext()) {
            Node<Integer, Integer> next = it.next();
            if(next.key == key) {
                it.remove();
                target = next;
            }
        }
        if(target == null)
            return -1;
        nodes.add(0, target);
        return target.value;
    }

    public void set(int key, int value) {
        int searchValue = get(key);
        if(searchValue == -1) {
            nodes.add(0, new Node(key, value));
            return;
        }

        nodes.get(0).value = value;

    }
}
