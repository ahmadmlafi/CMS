package com.Lafi.CMS.CacheAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache {

    final int CACHE_SIZE = 10;
    Entity start, end;
    HashMap<Long, Entity> hashmap;
    static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    LRUCache() {
        hashmap = new HashMap<>();
    }

    private static LRUCache instance = null;
    public static synchronized LRUCache getInstance() {
        if (instance == null) {
            instance = new LRUCache();
        }
        return instance;
    }


    public Object get(long key) {
        lock.readLock().lock();
        if (hashmap.containsKey(key)) {
            Entity entity = hashmap.get(key);
            remove(entity);
            addToTop(entity);
            lock.readLock().unlock();
            return entity.value;
        }
        lock.readLock().unlock();
        return new Object();
    }

    Entity getEntity(long key) {
        if (hashmap.containsKey(key)) {
            get(key);
            lock.readLock().unlock();
            return hashmap.get(key);
        }
        return new Entity();
    }

    public void update(long key, Object value) {
        lock.writeLock().lock();
        if (hashmap.containsKey(key)) // Key Already Exist, just update the value and move it to top
        {
            Entity entity = hashmap.get(key);
            entity.value = value;
            remove(entity);
            addToTop(entity);
        }
        lock.writeLock().unlock();

    }

    public void put(long key, Object value) {
        lock.writeLock().lock();
        if (hashmap.containsKey(key)) // if Key Exist break
            return;

        Entity newNode = new Entity();
        newNode.left = null;
        newNode.right = null;
        newNode.value = value;
        newNode.key = key;
        if (isCacheFull()) {
            hashmap.remove(end.key);
            remove(end);
        }
        addToTop(newNode);
        hashmap.put(key, newNode);
        lock.writeLock().unlock();
    }

    boolean isCacheFull() {
        return (hashmap.size() > CACHE_SIZE); // We have reached max size so need to make room for new element.
    }

    void addToTop(Entity node) {
        node.right = start;
        node.left = null;
        if (start != null)
            start.left = node;
        start = node;
        if (end == null)
            end = start;
    }

    void remove(Entity node) {
        lock.writeLock().lock();
        if (node.left != null) {
            node.left.right = node.right;
        } else {
            start = node.right;
        }

        if (node.right != null) {
            node.right.left = node.left;
        } else {
            end = node.left;
        }
        lock.writeLock().unlock();
    }

    public void delete(Long key){
        lock.writeLock().lock();
        hashmap.remove(key);
        remove(getEntity(key));
        lock.writeLock().unlock();
    }

    public boolean isContains(long key){
        if (hashmap.containsKey(key)){
            return true;
        }
        return false;
    }

    public ArrayList<Long> getKeys (){
        ArrayList<Long> keys = new ArrayList<>();
        keys.addAll(hashmap.keySet());
        return keys;
    }
}