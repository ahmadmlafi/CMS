package com.Lafi.CMS.CacheAccess;

import com.Lafi.CMS.Models.Students.StudentBasic;
import com.Lafi.CMS.Models.Students.StudentNull;

public class StudentCache extends LRUCache {
    private static StudentCache instance = null;

    public StudentCache() {
        super();
    }

    public static synchronized StudentCache getInstance() {
        if (instance == null) {
            instance = new StudentCache();
        }
        return instance;
    }

    @Override
    public StudentBasic get(long key) {
        lock.readLock().lock();
        if (hashmap.containsKey(key)) {
            Entity entity = hashmap.get(key);
            remove(entity);
            addToTop(entity);
            lock.readLock().unlock();
            return (StudentBasic) entity.value;
        }
        lock.readLock().unlock();
        return new StudentNull();
    }
}
